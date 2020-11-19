package rocks.ivski.bbc.ui.list

import rocks.ivski.bbc.data.models.Character

interface SelectionListener {

    fun onCharacterSelected(character: Character)
}