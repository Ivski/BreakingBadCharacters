package rocks.ivski.bbc.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_list.view.*
import rocks.ivski.bbc.R
import rocks.ivski.bbc.data.models.Character

class ListAdapter(
    private val items: ArrayList<Character>
) :
    RecyclerView.Adapter<ListAdapter.CharacterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        Glide.with(holder.image)
            .load(items[position].imageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(8)))
            .into(holder.image)
        holder.name.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<Character>) {
        items.addAll(list)
    }

    fun clearData() {
        items.clear()
    }

    class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.itemImage
        val name: TextView = itemView.itemName
    }
}