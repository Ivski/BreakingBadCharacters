package rocks.ivski.bbc.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*
import rocks.ivski.bbc.R
import rocks.ivski.bbc.data.models.Character
import rocks.ivski.bbc.utils.ARG_CHARACTER

class DetailsFragment : Fragment() {

    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        character = requireArguments().getSerializable(ARG_CHARACTER) as Character
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar as Toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(image).load(character.imageUrl).into(image)

        name.text = character.name
        var occupations = ""
        if (!character.occupation.isNullOrEmpty()) {
            for (s in character.occupation!!) {
                occupations += s
                if (character.occupation!!.indexOf(s) != character.occupation!!.size - 1) {
                    occupations += ",\n"
                }
            }
            occupation.text = occupations
        } else {
            occupation.text = getString(R.string.not_available)
        }
        status.text = character.status
        nickname.text = character.nickname
        var appearances = ""
        if (!character.appearance.isNullOrEmpty()) {
            for (i in character.appearance!!) {
                appearances += i
                if (character.appearance!!.indexOf(i) != character.appearance!!.size - 1) {
                    appearances += ", "
                }
            }
            seasonAppearance.text = appearances
        } else {
            seasonAppearance.text = getString(R.string.not_available)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}