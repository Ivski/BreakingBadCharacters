package rocks.ivski.bbc.data.repo

import retrofit2.Response
import rocks.ivski.bbc.data.api.ApiService
import rocks.ivski.bbc.data.models.Character
import java.net.HttpURLConnection

class CharacterRepo(private val api: ApiService) {

    private var data: List<Character> = emptyList()
    private val seasons = mutableSetOf<Int>()

    suspend fun getCharacters(): Response<List<Character>> {
        return if (data.isEmpty()) {
            val result = api.getCharacters()
            if (result.isSuccessful) {
                data = result.body()!!
                for (character in data) {
                    if (!character.appearance.isNullOrEmpty()) {
                        for (s in character.appearance) {
                            seasons.add(s)
                        }
                    }
                }
            }
            return result
        } else {
            Response.success(HttpURLConnection.HTTP_OK, data)
        }
    }

    fun getData(): List<Character> {
        return data
    }

    fun getSeasons(): Set<Int> {
        return seasons
    }
}