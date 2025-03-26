package com.example.e_commercesupercart.view.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercesupercart.databinding.ItemReviewBinding
import com.example.e_commercesupercart.model.product_details.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviews = mutableListOf<Review>()

    fun submitList(newReviews: List<Review>) {
        reviews.clear()
        reviews.addAll(newReviews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.binding.textReviewerName.text = review.fullName
        holder.binding.textReviewTitle.text = review.reviewTitle
        holder.binding.textReviewContent.text = review.review
       holder.binding.textReviewDate.text = review.reviewDate
        holder.binding.ratingBar.rating = review.rating.toFloat()
    }

    override fun getItemCount(): Int = reviews.size

    class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)
}
