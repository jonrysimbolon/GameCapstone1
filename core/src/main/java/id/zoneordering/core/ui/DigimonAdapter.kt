package id.zoneordering.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.zoneordering.core.R
import id.zoneordering.core.databinding.ItemListDigimonBinding
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.utils.DiffCallback

class DigimonAdapter : RecyclerView.Adapter<DigimonAdapter.ListViewHolder>() {

    private var listData = ArrayList<Digimon>()
    var onItemClick: ((Digimon) -> Unit)? = null

    fun setData(newListData: List<Digimon>?) {
        if (newListData == null) return

        val diffCallback = DiffCallback(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_digimon, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListDigimonBinding.bind(itemView)
        fun bind(data: Digimon) {
            Glide.with(itemView.context)
                .load(data.photo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.ivItemImage)
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}