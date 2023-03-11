package com.example.restaurantreviewloopj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreviewloopj.databinding.ItemReviewBinding

class ReviewAdapter(private val listReview: List<String>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    class ViewHolder(var binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewAdapter.ViewHolder(
        ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.binding.tvItem.text = listReview[holder.adapterPosition]
    }

    override fun getItemCount(): Int = listReview.size
}