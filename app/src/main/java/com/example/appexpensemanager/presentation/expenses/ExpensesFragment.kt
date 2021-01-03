package com.example.appexpensemanager.presentation.expenses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appexpensemanager.R
import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.di.ContextModule
import com.example.appexpensemanager.di.DaggerAppComponent
import com.example.appexpensemanager.presentation.adapters.MainAdapter
import com.example.appexpensemanager.utils.ext.injectViewModel
import com.example.appexpensemanager.utils.TouchHelper
import com.example.appexpensemanager.utils.factories.ViewModelProviderFactory
import kotlinx.android.synthetic.main.expenses_fragment.*

class ExpensesFragment : Fragment(R.layout.expenses_fragment) {

    private lateinit var mainAdapter: MainAdapter
    private lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var expensesViewModel: ExpensesViewModel
    private lateinit var observerList: Observer<List<TransactionEntry>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.expebses_string)
        initViewModel()
        initObserver()
        setupRecyclerView()
        itemClick()
        setupTouchHelper(view)

        add_fab.setOnClickListener {
            findNavController().navigate(R.id.action_expensesFragment_to_addTransactionFragment)
        }
    }

    private fun initObserver(){
        observerList = Observer {transactions ->
            mainAdapter.differ.submitList(transactions)
        }
        expensesViewModel.transactions.observe(viewLifecycleOwner, observerList)
    }

    private fun itemClick(){
        mainAdapter.setOnItemClickListener {transaction ->
            val bundle = Bundle().apply {
                putSerializable("transaction", transaction)
            }
            findNavController().navigate(R.id.action_expensesFragment_to_updateTransactionFragment, bundle)
        }
    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter()
        transactions_recycler_view.visibility = View.VISIBLE
        transactions_recycler_view.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                transactions_recycler_view.smoothScrollToPosition(mainAdapter.differ.currentList.size)
            }
        }
    }

    private fun initViewModel() {
        val appComponent =
            DaggerAppComponent.builder().contextModule(ContextModule(requireContext())).build()
        viewModelProviderFactory = appComponent.getViewModelFactory()
        expensesViewModel = injectViewModel(viewModelProviderFactory)
    }

    private fun setupTouchHelper(view: View){
        val itemTouchHelperCallback =
            TouchHelper(mainAdapter, expensesViewModel, view)

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(transactions_recycler_view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        expensesViewModel.transactions.removeObserver(observerList)
        transactions_recycler_view.adapter = null
    }
}