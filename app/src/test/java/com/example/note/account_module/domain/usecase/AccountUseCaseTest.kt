package com.example.note.account_module.domain.usecase

import android.content.SharedPreferences
import com.example.note.account_module.domain.model.Account
import com.example.note.account_module.domain.usecase.data.FakeAccountRepository
import com.example.note.account_module.presentation.login.LoginViewModel
import com.example.note.account_module.presentation.register.RegisterUiState
import com.example.note.account_module.presentation.register.RegisterViewModel
import com.example.note.core.sharepreference.SharePreferenceUtil.currentLoginAccountId
import com.example.note.di.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.kotlin.never
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class AccountUseCaseTest : AutoCloseKoinTest() {
    private lateinit var accountUseCase: AccountUseCase
    private lateinit var fakeAccountRepository: FakeAccountRepository
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loginViewModel: LoginViewModel

    @get:Rule
    val koinTestRule =
        KoinTestRule.create {
            modules(testRuleModule)
        }

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val sharedPreferences: SharedPreferences by inject()

    private val testRuleModule
        get() =
            module {
                single<SharedPreferences> { mockk(relaxed = true) }
            }

    @Before
    fun setUp() {
        fakeAccountRepository =
            FakeAccountRepository().apply {
                accounts.add(Account(0, "Kevin", "1234"))
            }
        accountUseCase = AccountUseCase(fakeAccountRepository)
        registerViewModel = RegisterViewModel(accountUseCase)
        loginViewModel = LoginViewModel(accountUseCase)
    }

    @Test
    fun `test login successfully`() {
        loginViewModel.apply {
            enterUsername("Kevin")
            enterPassword("1234")
        }
        every { sharedPreferences.getInt("currentAccountId", -1) } returns 0
        loginViewModel.login()

        val account = fakeAccountRepository.accounts.find { it.username == "Kevin" }

        assertThat(currentLoginAccountId).isEqualTo(0)
        assertThat(account).isNotNull()
        assertThat(account?.username).isEqualTo("Kevin")
        assertThat(account?.password).isEqualTo("1234")
        assertThat(loginViewModel.uiState.value.isLoggedIn).isEqualTo(true)
        assertThat(loginViewModel.uiState.value.isLoggingIn).isEqualTo(false)
        assertThat(loginViewModel.uiState.value.errorMessage).isNull()
    }

    @Test
    fun `test login failure`() {
        loginViewModel.apply {
            enterUsername("Kevin1")
            enterPassword("1234")
        }
        loginViewModel.login()

        val account = fakeAccountRepository.accounts.find { it.username == "Kevin1" }

        assertThat(account).isNull()
        assertThat(loginViewModel.uiState.value.isLoggedIn).isEqualTo(false)
        assertThat(loginViewModel.uiState.value.isLoggingIn).isEqualTo(false)
        assertThat(loginViewModel.uiState.value.errorMessage).isNotNull()
        assertThat(loginViewModel.uiState.value.errorMessage).isEqualTo("Invalid username or password")
    }

    @Test
    fun `test invalid login input`() =
        runTest {
            loginViewModel.apply {
                enterUsername("")
                enterPassword("")
            }
            val spyAccountUseCase = spy(accountUseCase)
            loginViewModel.login()

            assertThat(loginViewModel.uiState.value.errorMessage).isEqualTo("Username or password is not empty")

            verify(spyAccountUseCase, never()).login("", "")
        }

    @Test
    fun `test register successfully`() {
        val username = "Kevin1"
        val password = "Nhd1999@"

        registerViewModel.apply {
            enterUsername(username)
            enterPassword(password)
            enterConfirmPassword(password)
        }

        registerViewModel.register()

        val uiState = registerViewModel.uiState.value
        val account =
            fakeAccountRepository.accounts.find { it.username == username && it.password == password }

        assertThat(account).isNotNull()
        assertThat(uiState.isRegistered).isEqualTo(true)
        assertThat(uiState.isRegistering).isEqualTo(false)
        assertThat(uiState.errorMessage).isNull()
    }

    @Test
    fun `test register failure due to account already exist`() {
        val username = "Kevin"
        val password = "Nhd1999@"

        registerViewModel.apply {
            enterUsername(username)
            enterPassword(password)
            enterConfirmPassword(password)
        }

        registerViewModel.register()

        val uiState = registerViewModel.uiState.value

        assertThat(uiState.isRegistered).isEqualTo(false)
        assertThat(uiState.isRegistering).isEqualTo(false)
        assertThat(uiState.errorMessage).isEqualTo("username is already exist")
    }

    @Test
    fun `test show error when blank username`() =
        runTest {
            registerViewModel.apply {
                enterUsername("")
                enterPassword("password")
                enterConfirmPassword("password")
            }

            registerViewModel.register()

            val spyRegisterViewModel: AccountUseCase = spy(accountUseCase)
            val uiState: RegisterUiState = registerViewModel.uiState.value

            assertThat(uiState.errorMessage).isEqualTo("Username cannot be empty")
            assertThat(uiState.isRegistered).isEqualTo(false)
            verify(spyRegisterViewModel, never()).register("", "password")
        }

    @Test
    fun `test show error when invalid username`() =
        runTest {
            registerViewModel.apply {
                enterUsername("use")
                enterPassword("password")
                enterConfirmPassword("password")
            }

            registerViewModel.register()

            val spyRegisterViewModel: AccountUseCase = spy(accountUseCase)
            val uiState: RegisterUiState = registerViewModel.uiState.value

            assertThat(uiState.errorMessage).isEqualTo("Username must be at least 4 characters long")
            assertThat(uiState.isRegistered).isEqualTo(false)
            verify(spyRegisterViewModel, never()).register("use", "password")
        }

    @Test
    fun `test show error when blank password`() =
        runTest {
            registerViewModel.apply {
                enterUsername("username")
                enterPassword("")
            }

            registerViewModel.register()

            val spyRegisterViewModel: AccountUseCase = spy(accountUseCase)
            val uiState: RegisterUiState = registerViewModel.uiState.value

            assertThat(uiState.errorMessage).isEqualTo("Password cannot be empty")
            assertThat(uiState.isRegistered).isEqualTo(false)
            verify(spyRegisterViewModel, never()).register("username", "")
        }

    @Test
    fun `test show error when invalid password`() =
        runTest {
            val listOfInvalidPassword: List<String> = listOf("123", "pass", "passwrd")
            listOfInvalidPassword.forEach {
                registerViewModel.apply {
                    enterUsername("username")
                    enterPassword(it)
                    enterConfirmPassword(it)
                }

                registerViewModel.register()

                val spyRegisterViewModel: AccountUseCase = spy(accountUseCase)
                val uiState: RegisterUiState = registerViewModel.uiState.value

                assertThat(
                    uiState.errorMessage,
                ).isEqualTo("Password must be at least 8 characters long and contain both letters and numbers")
                assertThat(uiState.isRegistered).isEqualTo(false)
                verify(spyRegisterViewModel, never()).register("username", it)
            }
        }

    @Test
    fun `test show error when mismatch between password and confirm password`() =
        runTest {
            registerViewModel.apply {
                enterUsername("username")
                enterPassword("Nhd1999@")
                enterConfirmPassword("Nhd1998@")
            }

            registerViewModel.register()

            val spyRegisterViewModel: AccountUseCase = spy(accountUseCase)
            val uiState: RegisterUiState = registerViewModel.uiState.value

            assertThat(uiState.errorMessage).isEqualTo("Password and confirm password miss matching")
            assertThat(uiState.isRegistered).isEqualTo(false)
            verify(spyRegisterViewModel, never()).register("username", "password")
        }
}
