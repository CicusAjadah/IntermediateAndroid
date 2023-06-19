package com.tugas.storyappbydavid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugas.storyappbydavid.DetailStoryActivity
import com.tugas.storyappbydavid.apiresponse.ListStoryItem
import com.tugas.storyappbydavid.databinding.ItemStoryBinding

class ListStoryAdapter :
    PagingDataAdapter<ListStoryItem, ListStoryAdapter.ViewHolder>(DIFF_CALLBACK){

    class ViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            Glide.with(binding.ivItemPhoto)
                .load(data.photoUrl)
                .into(binding.ivItemPhoto)
            binding.textView2.text = data.name
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data!= null) {
            viewHolder.bind(data)
            viewHolder.itemView.setOnClickListener {
                val intentDetail = Intent(viewHolder.itemView.context, DetailStoryActivity::class.java)
                intentDetail.putExtra("id", data.id)
                viewHolder.itemView.context.startActivity(intentDetail)
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}