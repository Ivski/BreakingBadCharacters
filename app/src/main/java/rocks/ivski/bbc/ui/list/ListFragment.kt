package rocks.ivski.bbc.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import rocks.ivski.bbc.R
import rocks.ivski.bbc.utils.Status

class ListFragment : Fragment() {

    private val viewModel: ListVM by viewModel()

    private lateinit var adapter: ListAdapter
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var listener: SelectionListener
    private val seasonListener = object : SeasonFilterListener {
        override fun onFiltersUpdated(selected: List<Int>) {
            viewModel.filterByAppearance(selected)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SelectionListener) {
            listener = context
        } else {
            throw Exception("Parent must implement SelectionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar as Toolbar)

        setUI()
        setObservers()

    }

    private fun setUI() {
        progressBar.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(requireContext())
        list.layoutManager = layoutManager
        adapter = ListAdapter(arrayListOf(), listener)
        list.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )
        list.adapter = adapter

        val seasonLayoutManager = GridLayoutManager(requireContext(), 5)
        seasons.layoutManager = seasonLayoutManager
        seasonAdapter = SeasonAdapter(arrayListOf(), seasonListener)
        seasons.adapter = seasonAdapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.filterCharacters(query)
                }
                return !query.isNullOrEmpty()
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.filterCharacters(newText)
                }
                return !newText.isNullOrEmpty()
            }
        })

        search.findViewById<View>(androidx.appcompat.R.id.search_close_btn).setOnClickListener {
            search.setQuery("", false)
            search.isIconified = true
            search.clearFocus()
            adapter.clearData()
            viewModel.getCharacters()
        }

    }

    private fun setObservers() {
        viewModel.getCharacters().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        adapter.addData(data)
                        adapter.notifyDataSetChanged()
                        seasonAdapter.addData(viewModel.getSeasons())
                        seasonAdapter.notifyDataSetChanged()
                    }
                    progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.filteredResults.observe(viewLifecycleOwner, {
            adapter.clearData()
            adapter.addData(it)
            adapter.notifyDataSetChanged()
        })
    }
}