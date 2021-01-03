package com.example.appexpensemanager.presentation.updateTransaction

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appexpensemanager.R
import com.example.appexpensemanager.data.models.TransactionEntry
import com.example.appexpensemanager.databinding.UpdateTransactionFragmentBinding
import com.example.appexpensemanager.di.ContextModule
import com.example.appexpensemanager.di.DaggerAppComponent
import com.example.appexpensemanager.utils.ext.injectViewModel
import com.example.appexpensemanager.utils.showDatePicker
import com.example.appexpensemanager.utils.factories.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.toString as toString1

class UpdateTransactionFragment : Fragment(R.layout.update_transaction_fragment) {

    private lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var updateViewModel: UpdateTransactionViewModel
    private val args: UpdateTransactionFragmentArgs by navArgs()
    private lateinit var binding: UpdateTransactionFragmentBinding

    @RequiresApi(Build.VERSION_CODES.N)
    private val myCalendar = Calendar.getInstance(Locale(Locale.ENGLISH.toString()))

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UpdateTransactionFragmentBinding.bind(view)
        requireActivity().title = getString(R.string.update_transaction_expenses)
        initViewModel()
        setupFields()
        updateTransaction()
        backToFragment()

        binding.dateButtonUpdate.setOnClickListener {
            showDatePicker(
                requireContext(),
                myCalendar
            ) { updateDate() }
        }
    }

    override fun onStart() {
        super.onStart()
        initSpinner()
    }

    private fun setupFields() {
        val transaction = args.transaction
        binding.amountEditTextUpdate.setText(transaction.amount.toString1(), TextView.BufferType.EDITABLE)
        binding.descriptionEditTextUpdate.setText(transaction.description.toString1(), TextView.BufferType.NORMAL)
        binding.categorySpinnerUpdate.setText(transaction.category.toString1(), TextView.BufferType.SPANNABLE)
        binding.dateButtonUpdate.text = transaction.date.toString1()
    }

    private fun updateTransaction() {
        binding.updateButton.setOnClickListener {
            if (!validateAmount() or !validateCategory() or !validateDescription()) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.chech_all_fields),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val amountSting = binding.amountEditTextUpdate.text.toString1()
                val amount = amountSting.toInt()
                val description = binding.descriptionEditTextUpdate.text.toString1()
                val category = binding.categorySpinnerUpdate.text.toString()
                val date =  binding.dateButtonUpdate.text.toString()
                updateViewModel.update(
                    TransactionEntry(
                        id = args.transaction.id,
                        amount = amount,
                        description = description,
                        category = category,
                        date = date
                    )
                )
                findNavController().navigate(R.id.action_updateTransactionFragment_to_expensesFragment)

            }
        }
    }

    private fun backToFragment() {
        binding.backButtonUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_updateTransactionFragment_to_expensesFragment)
        }
    }

    private fun initViewModel() {
        val appComponent =
            DaggerAppComponent.builder().contextModule(ContextModule(requireContext())).build()
        viewModelProviderFactory = appComponent.getViewModelFactory()
        updateViewModel = injectViewModel(viewModelProviderFactory)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateDate() {
        val date =
            DateUtils.formatDateTime(
                requireContext(),
                myCalendar.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE
            )
        binding.dateButtonUpdate.text = date
    }

    private fun initSpinner() {
        val categories = resources.getStringArray(R.array.filter_options)
        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            categories
        )
        binding.categorySpinnerUpdate.setAdapter(adapter)
    }

    private fun validateAmount(): Boolean {
        val amountInput: String = binding.amountEditTextUpdate.text.toString1()
        return when {
            amountInput.isEmpty() -> {
                binding.amountTextInputUpdate.error = getString(R.string.add_amount_string)
                false
            }
            else -> {
                binding.amountTextInputUpdate.error = ""
                true
            }
        }
    }

    private fun validateDescription(): Boolean {
        val descriptionInput: String = binding.descriptionEditTextUpdate.text.toString1()
        return when {
            descriptionInput.isEmpty() -> {
                binding.descriptionTextInputUpdate.error = getString(R.string.add_description_string)
                false
            }
            else -> {
                binding.descriptionTextInputUpdate.error = ""
                true
            }
        }
    }

    private fun validateCategory(): Boolean {
        val categoryInput: String = binding.categorySpinnerUpdate.text.toString()
        return when {
            categoryInput.isEmpty() -> {
                binding.categoryInputLayoutUpdate.error = getString(R.string.add_catergory_string)
                false
            }
            else -> {
                binding.categoryInputLayoutUpdate.error = ""
                true
            }
        }
    }
}