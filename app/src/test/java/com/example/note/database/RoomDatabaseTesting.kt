package com.example.note.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.note.NoteDatabase
import com.example.note.account_module.data.data_source.AccountDao
import com.example.note.account_module.domain.model.Account
import com.example.note.di.MainCoroutineRule
import com.example.note.note_module.data.data_source.NoteDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import java.util.concurrent.CountDownLatch

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RoomDatabaseTesting : AutoCloseKoinTest() {
    private val database: NoteDatabase by inject()
    private val accountDao: AccountDao by inject()
    private val noteDao: NoteDao by inject()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testRuleModule
        get() =
            module {
                single {
                    Room
                        .inMemoryDatabaseBuilder(
                            get(),
                            NoteDatabase::class.java,
                        ).allowMainThreadQueries()
                        .build()
                }
                single { get<NoteDatabase>().accountDao() }
                single { get<NoteDatabase>().noteDao() }
            }

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            loadKoinModules(listOf(testRuleModule))
        }
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun `create account successfully`() =
        runBlocking {
            val account = Account(0, "Kevin", "1234")
            accountDao.register(account)

            val latch = CountDownLatch(1)
            val job =
                async(Dispatchers.IO) {
                    accountDao.findById(accountId = account.accountId)?.let {
                        assertThat(it.username).isEqualTo(account.username)
                        latch.countDown()
                    }
                }
            latch.await()
            job.cancelAndJoin()
        }
}
