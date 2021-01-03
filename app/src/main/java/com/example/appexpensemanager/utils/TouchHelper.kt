package com.example.appexpensemanager.utils

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appexpensemanager.R
import com.example.appexpensemanager.presentation.adapters.MainAdapter
import com.example.appexpensemanager.presentation.expenses.ExpensesViewModel
import com.google.android.material.snackbar.Snackbar

class TouchHelper(
    private val adapter: MainAdapter,
    private val expensesViewModel: ExpensesViewModel,
    private val view: View
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.absoluteAdapterPosition
        val transactionEntry = adapter.differ.currentList[position]
        expensesViewModel.deleteTransaction(transactionEntry)
        Snackbar.make(view, R.string.delete_transaction, Snackbar.LENGTH_LONG)
            .apply {
                setAction("Undo") {
                    expensesViewModel.saveTransaction(transactionEntry)
                }
                show()
            }
    }
}