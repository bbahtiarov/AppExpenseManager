package com.example.appexpensemanager.utils

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.appexpensemanager.presentation.updateTransaction.UpdateTransactionFragment
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun showDatePicker(context: Context, calendar: Calendar, func:() -> Unit){

    val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            func()
        }

    val datePickerDialog = DatePickerDialog(
        context,
        dateSetListener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    datePickerDialog.show()
}