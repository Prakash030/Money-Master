package com.example.moneyMaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyMaster.R
import com.example.moneyMaster.adapters.CategoryAdapter.CategoryViewHolder
import com.example.moneyMaster.databinding.SampleCategoryItemBinding
import com.example.moneyMaster.models.Category

class CategoryAdapter(
    var context: Context,
    var categories: ArrayList<Category>,
    var categoryClickListener: CategoryClickListener
) : RecyclerView.Adapter<CategoryViewHolder>() {
    interface CategoryClickListener {
        fun onCategoryClicked(category: Category?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.sample_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.categoryText.text = category.categoryName
        holder.binding.categoryIcon.setImageResource(category.categoryImage)
        holder.binding.categoryIcon.backgroundTintList =
            context.getColorStateList(category.categoryColor)
        holder.itemView.setOnClickListener { c: View? ->
            categoryClickListener.onCategoryClicked(
                category
            )
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SampleCategoryItemBinding

        init {
            binding = SampleCategoryItemBinding.bind(itemView)
        }
    }
}