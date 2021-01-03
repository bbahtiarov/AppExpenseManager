package com.example.appexpensemanager.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appexpensemanager.R
import com.example.appexpensemanager.data.models.TransactionEntry
import kotlinx.android.synthetic.main.expenses_item.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.TransactionHolder>() {

    inner class TransactionHolder(view: View) : RecyclerView.ViewHolder(view)

    private val differCallback = object : DiffUtil.ItemCallback<TransactionEntry>() {
        override fun areItemsTheSame(
            oldItem: TransactionEntry,
            newItem: TransactionEntry
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionEntry,
            newItem: TransactionEntry
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((TransactionEntry) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        return TransactionHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.expenses_item, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = differ.currentList[position]
        holder.itemView.apply {
            card_view.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

            category_text_view.text = transaction.category
            amount_text_view.text = transaction.amount.toString()
            date_text_view.text = transaction.date.toString()

            setOnClickListener {
                onItemClickListener?.let { it(transaction) }
            }
        }
    }

    fun setOnItemClickListener(listener: (TransactionEntry) -> Unit) {
        onItemClickListener = listener
    }
}