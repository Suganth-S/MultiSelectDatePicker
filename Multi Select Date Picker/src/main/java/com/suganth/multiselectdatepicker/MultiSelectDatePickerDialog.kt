package com.suganth.multiselectdatepicker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.suganth.multiselectdatepicker.databinding.DialogMultiDatePickerBinding
import com.suganth.multiselectdatepicker.ui.MultiSelectDatePickerAdapter
import com.suganth.multiselectdatepicker.utils.SelectionType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MultiSelectDatePickerDialog(
    context: Context,
    private val initialSelectedDates: List<Long>,
    private val minDate: Long? = null,
    private val maxDate: Long? = null,
    private val selectionType: SelectionType,
    private val onDateSelected: (List<Long>) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogMultiDatePickerBinding
    private val selectedDates = initialSelectedDates.toMutableSet()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogMultiDatePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        updateUI()
    }

    private fun initView() {
        binding.cancelButton.setOnClickListener { dismiss() }
        binding.okButton.setOnClickListener {
            onDateSelected(selectedDates.toList())
            dismiss()
        }
        binding.prevMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateUI()
        }
        binding.nextMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateUI()
        }

        binding.calendarRecyclerView.layoutManager = GridLayoutManager(context, 7)
        binding.calendarRecyclerView.adapter = MultiSelectDatePickerAdapter(context, calendar, selectedDates, selectionType, minDate, maxDate) {
            selectedDates.clear()
            selectedDates.addAll(it)
            binding.selectedDateTextView.text = formatSelectedDate()
        }
    }

    private fun updateUI() {
        binding.monthYearTextView.text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.time)
        binding.calendarRecyclerView.adapter = MultiSelectDatePickerAdapter(context, calendar, selectedDates, selectionType, minDate, maxDate) {
            selectedDates.clear()
            selectedDates.addAll(it)
        }
    }

    private fun formatSelectedDate(): String {
        return if (selectedDates.isNotEmpty()) {
            selectedDates.sorted().joinToString(", ") { date ->
                SimpleDateFormat("EEE, MMM dd", Locale.getDefault()).format(date)
            }
        } else "Select a Date"
    }
}