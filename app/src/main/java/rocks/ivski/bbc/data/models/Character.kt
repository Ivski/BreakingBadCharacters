package rocks.ivski.bbc.data.models

import com.google.gson.annotations.SerializedName
import rocks.ivski.bbc.utils.NA
import java.io.Serializable

data class Character(
    @SerializedName("char_id")
    val charId: Int? = null,
    val name: String = NA,
    val birthday: String = NA,
    val occupation: List<String> = emptyList(),
    @SerializedName("img")
    val imageUrl: String? = null,
    val status: String = NA,
    val nickname: String = NA,
    val appearance: List<Int> = emptyList(),
    val portrayed: String = NA,
    val category: String = NA,
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int> = emptyList()
): Serializable