package rocks.ivski.bbc.data.repo

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import rocks.ivski.bbc.data.api.ApiService
import rocks.ivski.bbc.data.models.Character

class CharacterRepoTest {

    private val service: ApiService = mock(ApiService::class.java)
    lateinit var repo: CharacterRepo

    @Test
    fun `when no characters available make api call`() {
        runBlocking {
            `when`(service.getCharacters()).thenReturn(Response.success(200, emptyList()))
            repo = CharacterRepo(service)
            assertTrue(repo.getData().isEmpty())
            repo.getCharacters()
            verify(service, times(1)).getCharacters()
        }
    }

    @Test
    fun `when characters available do not make api call`() {
        runBlocking {
            `when`(service.getCharacters()).thenReturn(Response.success(200, listOf(Character())))
            repo = CharacterRepo(service)
            // initial getCharacters() call
            repo.getCharacters()
            verify(service, times(1)).getCharacters()
            // subsequent getCharacters() call
            repo.getCharacters()
            verify(service, times(1)).getCharacters()
        }
    }

}