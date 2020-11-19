package rocks.ivski.bbc.data.repo

import retrofit2.Response
import rocks.ivski.bbc.data.api.ApiService
import rocks.ivski.bbc.data.models.Character
import java.net.HttpURLConnection

class CharacterRepo(private val api: ApiService) {

    private var data: List<Character> = emptyList()

    suspend fun getCharacters(): Response<List<Character>> {
        return if (data.isEmpty()) {
            val result = api.getCharacters()
            if (result.isSuccessful) {
                data = result.body()!!
            }
            return result
        } else {
            Response.success(HttpURLConnection.HTTP_OK, data)
        }
    }

    fun getData(): List<Character> {
        return data
    }
}