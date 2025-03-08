package com.suganth.multiselectdatepicker.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.suganth.multiselectdatepicker.R
import com.suganth.multiselectdatepicker.utils.SelectionType
import java.util.Calendar

class MultiSelectDatePickerAdapter(
    private val context: Context,
    private val calendar: Calendar,
    private val initialSelectedDates: MutableSet<Long>,
    private val selectionType: SelectionType,
    private val minDate: Long? = null,
    private val maxDate: Long? = null,
    private val onDateSelected: (Set<Long>) -> Unit
) : RecyclerView.Adapter<MultiSelectDatePickerAdapter.ViewHolder>() {

    private val selectedDates = initialSelectedDates.toMutableSet()
    private val dates = mutableListOf<Long?>()

    init {
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        repeat(firstDayOfWeek) { dates.add(null) }
        repeat(daysInMonth) {
            tempCalendar.set(Calendar.DAY_OF_MONTH, it + 1)
            dates.add(tempCalendar.timeInMillis)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dates[position]
        if (date == null) {
            holder.dateTextView.text = ""
            holder.itemView.isClickable = false
            holder.dateTextView.background = null
            return
        }

        val normalizedDate = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val isSelected = selectedDates.contains(normalizedDate)
        val isDisabled = (minDate != null && date < minDate) || (maxDate != null && date > maxDate)

        holder.dateTextView.text = Calendar.getInstance().apply { timeInMillis = date }.get(Calendar.DAY_OF_MONTH).toString()
        holder.dateTextView.isEnabled = !isDisabled
        holder.itemView.isClickable = !isDisabled

        if (isSelected) {
            holder.dateTextView.setBackgroundResource(R.drawable.selected_date_background)
            holder.dateTextView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        } else {
            holder.dateTextView.background = null
            holder.dateTextView.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }

        holder.itemView.setOnClickListener {
            if (!isDisabled) {
                if (selectionType == SelectionType.SINGLE) {
                    selectedDates.clear()
                    selectedDates.add(normalizedDate)
                } else {
                    if (isSelected) {
                        selectedDates.remove(normalizedDate)
                    } else {
                        selectedDates.add(normalizedDate)
                    }
                }
                notifyDataSetChanged()
                onDateSelected(selectedDates)
            }
        }
    }

    override fun getItemCount(): Int = dates.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.dayTextView)
    }
}