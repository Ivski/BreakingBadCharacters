package rocks.ivski.bbc.ui

import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import rocks.ivski.bbc.data.models.Character
import rocks.ivski.bbc.data.repo.CharacterRepo
import rocks.ivski.bbc.ui.list.ListVM
import rocks.ivski.bbc.utils.NetworkUtil

class ListVMTest {

    private var repo = mock(CharacterRepo::class.java)
    private var util = mock(NetworkUtil::class.java)
    private lateinit var viewModel: ListVM

    private val fakeCharacter1 = Character(name = "Mike", appearance = listOf(1))
    private val fakeCharacter2 = Character(name = "Bob", appearance = listOf(1, 2))
    private val fakeCharacter3 = Character(name = "Mel", appearance = listOf(3))

    @Test
    fun `when no network available no api call is made`() {
        `when`(util.isNetworkConnected()).thenReturn(false)
        viewModel = ListVM(repo, util)

        runBlocking {
            viewModel.getCharacters()
            verify(repo, never()).getCharacters()
        }
    }

    @Test
    fun `when internet is available api call is made`() {
        `when`(util.isNetworkConnected()).thenReturn(true)
        runBlocking {
            `when`(repo.getCharacters()).thenReturn(Response.success(200, listOf(fakeCharacter1)))
            viewModel = ListVM(repo, util)
            viewModel.getCharacters()
            verify(repo, times(1)).getCharacters()
        }
    }

    @Test
    fun `when api returns error characters list is empty`() {
        `when`(util.isNetworkConnected()).thenReturn(true)
        runBlocking {
            `when`(repo.getCharacters()).thenReturn(
                Response.error(
                    400,
                    "{\"error\":[\"BAD REQUEST\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
            viewModel = ListVM(repo, util)
            viewModel.getCharacters()
            assertTrue(repo.getData().isEmpty())
        }
    }

    @Test
    fun `if no names start with keyword return empty list`() {
        `when`(repo.getData()).thenReturn(listOf(fakeCharacter2))
        viewModel = ListVM(repo, util)
        assert(viewModel.filterCharacters("M").isEmpty())
    }

    @Test
    fun `return only names starting with keyword`() {
        `when`(repo.getData()).thenReturn(
            listOf(
                fakeCharacter1,
                fakeCharacter2,
                fakeCharacter3
            )
        )
        viewModel = ListVM(repo, util)
        assert(viewModel.filterCharacters("M").size == 2)
    }

    @Test
    fun `search filtering is case insensitive`() {
        `when`(repo.getData()).thenReturn(listOf(fakeCharacter2))
        viewModel = ListVM(repo, util)
        assert(viewModel.filterCharacters("b").size == 1)
    }

    @Test
    fun `when filter by season show only results appearing in all selected seasons`() {
        `when`(repo.getData()).thenReturn(
            listOf(
                fakeCharacter1,
                fakeCharacter2,
                fakeCharacter3
            )
        )
        viewModel = ListVM(repo, util)
        assert(viewModel.filterByAppearance(listOf(1, 2)).size == 1)
    }

    @Test
    fun `when filter by season has no matches no results are displayed`() {
        `when`(repo.getData()).thenReturn(
            listOf(
                fakeCharacter1,
                fakeCharacter2,
                fakeCharacter3
            )
        )
        viewModel = ListVM(repo, util)
        assert(viewModel.filterByAppearance(listOf(5)).isEmpty())
    }

    @Test
    fun `when filter by season has more than one match return all matches`() {
        `when`(repo.getData()).thenReturn(
            listOf(
                fakeCharacter1,
                fakeCharacter2,
                fakeCharacter3
            )
        )
        viewModel = ListVM(repo, util)
        assert(viewModel.filterByAppearance(listOf(1)).size == 2)
    }

    @Test
    fun `get seasons returns repo data`() {
        `when`(repo.getData()).thenReturn(
            listOf(
                fakeCharacter1,
                fakeCharacter2,
                fakeCharacter3
            )
        )
        viewModel = ListVM(repo, util)
        assertTrue(viewModel.getSeasons() == repo.getSeasons().toList())
    }

}