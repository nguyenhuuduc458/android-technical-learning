package com.example.note.note_module.domain.usecase

import com.example.note.di.MainCoroutineRule
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.usecase.data.FakeNoteRepository
import com.example.note.note_module.domain.util.NoteOrder
import com.example.note.note_module.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.util.Date

class NoteUseCaseTest {
    private lateinit var noteUseCase: NoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private val kevinId = 1
    private val jackId = 2

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        noteUseCase = NoteUseCase(fakeNoteRepository)

        val notes = mutableListOf<Note>()

        (1..11).forEach { it ->
            notes.add(
                Note(
                    noteId = it,
                    title = "title $it",
                    userId = if (it % 2 == 0) kevinId else jackId,
                    description = "content $it",
                    createdAt = Date.from(Date().toInstant().plus(Duration.ofHours(it.toLong()))),
                    color = it,
                ),
            )
        }
        notes.shuffle()
        fakeNoteRepository.notes.addAll(notes)
    }

    @Test
    fun `test get correct note list for specific user`() {
        val kevinNote = noteUseCase.getNotesWithOrder(kevinId)
        val jackNote = noteUseCase.getNotesWithOrder(jackId)

        runBlocking {
            kevinNote.collectLatest { assertThat(it.size).isEqualTo(5) }
            jackNote.collectLatest { assertThat(it.size).isEqualTo(6) }
        }
    }

    @Test
    fun `Order note by title ascending, correct order`() =
        runBlocking {
            noteUseCase.getNotesWithOrder(kevinId, NoteOrder.Title(OrderType.Ascending)).collectLatest {
                for (i in 0 until it.size - 2) {
                    assertThat(it[i].title).isLessThan(it[i + 1].title)
                }
            }
        }

    @Test
    fun `Order note by title descending, correct order`() =
        runBlocking {
            noteUseCase.getNotesWithOrder(kevinId, NoteOrder.Title(OrderType.Descending)).collectLatest {
                for (i in 0 until it.size - 2) {
                    assertThat(it[i].title).isGreaterThan(it[i + 1].title)
                }
            }
        }

    @Test
    fun `Order note by date ascending, correct order`() =
        runBlocking {
            noteUseCase.getNotesWithOrder(kevinId, NoteOrder.Date(OrderType.Ascending)).collectLatest {
                for (i in 0 until it.size - 2) {
                    assertThat(it[i].createdAt).isLessThan(it[i + 1].createdAt)
                }
            }
        }

    @Test
    fun `Order note by date descending, correct order`() =
        runBlocking {
            noteUseCase.getNotesWithOrder(kevinId, NoteOrder.Date(OrderType.Descending)).collectLatest {
                for (i in 0 until it.size - 2) {
                    assertThat(it[i].createdAt).isGreaterThan(it[i + 1].createdAt)
                }
            }
        }

    @Test
    fun `Order note by color ascending, correct order`() =
        runBlocking {
            noteUseCase.getNotesWithOrder(kevinId, NoteOrder.Color(OrderType.Ascending)).collectLatest {
                for (i in 0 until it.size - 2) {
                    assertThat(it[i].color).isLessThan(it[i + 1].color)
                }
            }
        }

    @Test
    fun `Order note by color descending, correct order`() =
        runBlocking {
            noteUseCase.getNotesWithOrder(kevinId, NoteOrder.Title(OrderType.Descending)).collectLatest {
                for (i in 0 until it.size - 2) {
                    assertThat(it[i].color).isGreaterThan(it[i + 1].color)
                }
            }
        }

    @Test
    fun `Delete note successfully`() {
        val note = fakeNoteRepository.notes[0]
        runBlocking {
            noteUseCase.deleteNote(note)
            assertThat(fakeNoteRepository.notes.contains(note)).isFalse()
        }
    }

    @Test
    fun `Insert note successfully`() {
        val note =
            Note(
                noteId = 15,
                userId = kevinId,
                title = "Title",
                description = "Description",
                createdAt = Date(),
                color = 1,
            )
        runBlocking {
            noteUseCase.insertNote(note)
            assertThat(fakeNoteRepository.notes.contains(note)).isTrue()
        }
    }

    @Test
    fun `Failed to insert note due to blank title or description`() =
        runBlocking {
            val note1 =
                Note(
                    noteId = 12,
                    userId = kevinId,
                    title = "",
                    description = "Description",
                    createdAt = Date(),
                    color = 1,
                )
            val note2 =
                Note(
                    noteId = 13,
                    userId = kevinId,
                    title = "title",
                    description = "",
                    createdAt = Date(),
                    color = 1,
                )
            val exception1 =
                assertThrows(java.lang.IllegalStateException::class.java) {
                    runBlocking {
                        noteUseCase.insertNote(note1)
                    }
                }
            val exception2 =
                assertThrows(java.lang.IllegalStateException::class.java) {
                    runBlocking {
                        noteUseCase.insertNote(note2)
                    }
                }
            assertThat(exception1.message).contains("Title is not null or empty")
            assertThat(exception2.message).contains("Description is not null or empty")
            assertThat(fakeNoteRepository.notes.contains(note1)).isFalse()
            assertThat(fakeNoteRepository.notes.contains(note2)).isFalse()
        }

    @Test
    fun `Update note successfully`() =
        runBlocking {
            val note =
                Note(
                    noteId = 17,
                    userId = kevinId,
                    title = "Title",
                    description = "Description",
                    createdAt = Date(),
                    color = 1,
                )
            noteUseCase.insertNote(note)

            val noteToBeUpdate = noteUseCase.findNoteById(note.noteId)
            val newNote = noteToBeUpdate?.copy(title = "new Title")
            if (newNote != null) {
                noteUseCase.insertNote(newNote)
            }
            assertThat(noteUseCase.findNoteById(note.noteId)?.title).isEqualTo("new Title")
        }

    @Test
    fun `Failed to update note due to blank title or description`() =
        runBlocking {
            val note =
                Note(
                    noteId = 12,
                    userId = kevinId,
                    title = "Title",
                    description = "Description",
                    createdAt = Date(),
                    color = 1,
                )
            noteUseCase.insertNote(note)

            val noteToBeUpdate = noteUseCase.findNoteById(note.noteId)
            val newNote = noteToBeUpdate?.copy(title = "")
            if (newNote != null) {
                val exception1 =
                    assertThrows(java.lang.IllegalStateException::class.java) {
                        runBlocking {
                            noteUseCase.insertNote(newNote)
                        }
                    }
                assertThat(exception1.message).contains("Title is not null or empty")
                assertThat(noteUseCase.findNoteById(note.noteId)?.title).isEqualTo("Title")
            }
        }
}
