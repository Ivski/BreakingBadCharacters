package rocks.ivski.bbc.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_season.view.*
import rocks.ivski.bbc.R

class SeasonAdapter(private val items: ArrayList<Int>, private val listener: SeasonFilterListener) :
    RecyclerView.Adapter<SeasonAdapter.SeasonHolder>() {

    private val selectionList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonHolder {
        return SeasonHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_season, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SeasonHolder, position: Int) {
        holder.checkbox.text = items[position].toString()
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    selectionList.add(items[position])
                } else {
                    selectionList.remove(items[position])
                }
                listener.onFiltersUpdated(selectionList)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<Int>) {
        items.clear()
        items.addAll(list)
    }

    class SeasonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.checkbox
    }
}