package com.example.appexpensemanager.presentation.addTransaction

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appexpensemanager.R
import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.di.ContextModule
import com.example.appexpensemanager.di.DaggerAppComponent
import com.example.appexpensemanager.utils.ext.injectViewModel
import com.example.appexpensemanager.utils.showDatePicker
import com.example.appexpensemanager.utils.factories.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_transaction_fragment.*
import java.util.*


class AddTransactionFragment : Fragment(R.layout.add_transaction_fragment) {

    @RequiresApi(Build.VERSION_CODES.N)
    private val myCalendar = Calendar.getInstance(Locale(Locale.ENGLISH.toString()))

    private lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var addViewModel: AddTransactionViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.add_expenses_string)
        initViewModel()
        initSpinner()
        updateDate()
        addTransaction()
        backToFragment()

        date_button.setOnClickListener {
            showDatePicker(
                requireContext(),
                myCalendar
            ) { updateDate() }
        }
    }

    private fun addTransaction() {
        add_button.setOnClickListener {
            if (!validateAmount() or !validateCategory() or !validateDescription()) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.chech_all_fields),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val amountString = amount_edit_text.text.toString()
                val amount = amountString.toInt()
                val description = description_edit_text.text.toString()
                val category = category_spinner.text.toString()
                val date = date_button.text.toString()
                addViewModel.insert(
                    TransactionEntry(
                        amount = amount,
                        description = description,
                        category = category,
                        date = date
                    )
                )
                findNavController().navigate(R.id.action_addTransactionFragment_to_expensesFragment2)
            }
        }
    }

    private fun backToFragment() {
        back_button.setOnClickListener {
            findNavController().navigate(R.id.action_addTransactionFragment_to_expensesFragment2)
        }
    }

    private fun initViewModel() {
        val appComponent =
            DaggerAppComponent.builder().contextModule(ContextModule(requireContext())).build()
        viewModelProviderFactory = appComponent.getViewModelFactory()
        addViewModel = injectViewModel(viewModelProviderFactory)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateDate() {
        val date =
            DateUtils.formatDateTime(
                requireContext(),
                myCalendar.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE
            )
        date_button.text = date
    }

    private fun initSpinner() {
        val categories = resources.getStringArray(R.array.filter_options)
        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            categories
        )
        category_spinner.setAdapter(adapter)
    }

    private fun validateAmount(): Boolean {
        val amountInput: String = amount_edit_text.text.toString()
        return when {
            amountInput.isEmpty() -> {
                amount_text_input.error = getString(R.string.add_amount_string)
                false
            }
            else -> {
                amount_text_input.error = ""
                true
            }
        }
    }

    private fun validateDescription(): Boolean {
        val descriptionInput: String = description_edit_text.text.toString()
        return when {
            descriptionInput.isEmpty() -> {
                description_text_input.error = getString(R.string.add_description_string)
                false
            }
            else -> {
                description_text_input.error = ""
                true
            }
        }
    }

    private fun validateCategory(): Boolean {
        val categoryInput: String = category_spinner.text.toString()
        return when {
            categoryInput.isEmpty() -> {
                category_input_layout.error = getString(R.string.add_catergory_string)
                false
            }
            else -> {
                category_input_layout.error = ""
                true
            }
        }
    }
}
