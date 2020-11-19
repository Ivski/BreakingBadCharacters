package rocks.ivski.bbc.data.api

import retrofit2.Response
import retrofit2.http.GET
import rocks.ivski.bbc.data.models.Character
import rocks.ivski.bbc.utils.ENDPOINT_CHARACTERS

interface API {

    @GET(ENDPOINT_CHARACTERS)
    suspend fun getCharacters(): Response<List<Character>>
}