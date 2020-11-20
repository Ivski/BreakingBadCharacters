package rocks.ivski.bbc.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rocks.ivski.bbc.data.models.Character
import rocks.ivski.bbc.data.repo.CharacterRepo
import rocks.ivski.bbc.utils.ApiResult
import rocks.ivski.bbc.utils.NETWORK_ERROR
import rocks.ivski.bbc.utils.NetworkUtil
import java.util.*

class ListVM(private val repo: CharacterRepo, private val networkUtil: NetworkUtil) : ViewModel() {

    private val data = MutableLiveData<ApiResult<List<Character>>>()
    val filteredResults = MutableLiveData<List<Character>>()

    fun getCharacters(): MutableLiveData<ApiResult<List<Character>>> {
        if (networkUtil.isNetworkConnected()) {
            viewModelScope.launch {
                data.postValue(ApiResult.loading(null))
                repo.getCharacters().let {
                    if (it.isSuccessful) {
                        data.postValue(ApiResult.success(it.body()!!))
                    } else {
                        data.postValue(ApiResult.error(it.errorBody().toString(), null))
                    }
                }
            }
        } else {
            data.postValue(
                ApiResult.error(
                    NETWORK_ERROR,
                    null
                )
            )
        }
        return data
    }

    fun filterCharacters(keyword: String): List<Character> {
        val result = repo.getData().filter {
            it.name.toLowerCase(Locale.ROOT).startsWith(
                keyword.toLowerCase(
                    Locale.ROOT
                )
            )
        }
        filteredResults.postValue(result)
        return result
    }

    fun filterByAppearance(filter: List<Int>): List<Character> {
        val result = repo.getData().filter {
            !it.appearance.isNullOrEmpty() && it.appearance.containsAll(filter)
        }
        filteredResults.postValue(result)
        return result
    }

    fun getSeasons(): List<Int> {
        return repo.getSeasons().toList()
    }
}