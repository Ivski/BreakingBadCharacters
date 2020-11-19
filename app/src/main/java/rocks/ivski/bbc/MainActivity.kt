package rocks.ivski.bbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rocks.ivski.bbc.data.models.Character
import rocks.ivski.bbc.ui.details.DetailsFragment
import rocks.ivski.bbc.ui.list.ListFragment
import rocks.ivski.bbc.ui.list.SelectionListener
import rocks.ivski.bbc.utils.ARG_CHARACTER
import rocks.ivski.bbc.utils.TAG_DETAILS

class MainActivity : AppCompatActivity(), SelectionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ListFragment())
            .commit()
    }

    override fun onCharacterSelected(character: Character) {
        val args = Bundle().apply { putSerializable(ARG_CHARACTER, character) }
        val fragment = DetailsFragment().apply { arguments = args }
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .addToBackStack(
                TAG_DETAILS
            ).commit()

    }
}