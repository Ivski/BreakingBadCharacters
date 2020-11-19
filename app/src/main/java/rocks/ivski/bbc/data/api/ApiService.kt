package rocks.ivski.bbc.data.api

import retrofit2.Response
import rocks.ivski.bbc.data.models.Character

class ApiService(private val api: API) {

    suspend fun getCharacters(): Response<List<Character>> = api.getCharacters()
}