package rocks.ivski.bbc.data

import rocks.ivski.bbc.utils.NA

data class Character(
    val charId: Int? = null,
    val name: String = NA,
    val birthday: String = NA,
    val occupation: List<String> = emptyList(),
    val imageUrl: String? = null,
    val status: String = NA,
    val nickname: String = NA,
    val appearance: List<Int> = emptyList(),
    val portrayed: String = NA,
    val category: String = NA,
    val betterCallSaulAppearance: List<Int> = emptyList()
)