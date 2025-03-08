package com.suganth.multiselectdatepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suganth.multiselectdatepicker.databinding.ActivityMainBinding
import com.suganth.multiselectdatepicker.utils.DateUtils
import com.suganth.multiselectdatepicker.utils.DateUtils.DATE_FORMAT_yyyyMMddHHmmssZZZZZ
import com.suganth.multiselectdatepicker.utils.DateUtils.SELECTED_DATES
import com.suganth.multiselectdatepicker.utils.SelectionType
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val resultHashMap = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btnClickHere.setOnClickListener {

            val inputText = binding.tvSelectedDates.text.toString()

            val initialSelectedDates = inputText.split(", ")
                .mapNotNull { dateString ->
                    try {
                        DateUtils.getDatePatternddMMyyyy().parse(dateString)?.time
                    } catch (e: java.text.ParseException) {
                        null
                    }
                }

            MultiSelectDatePickerDialog(
                context = this,
                initialSelectedDates = initialSelectedDates,
                selectionType = SelectionType.SINGLE,
                onDateSelected = { selectedDates ->
                    val dateStrings = selectedDates.map { date ->
                        DateUtils.getDateDDMMYYYY().format(Date(date))
                    }
                    binding.tvSelectedDates.text = dateStrings.joinToString(", ")

                    resultHashMap[SELECTED_DATES] = selectedDates.joinToString(",") { date ->
                        DateUtils.getDateString(
                            date,
                            inputFormat = DateUtils.DATE_FORMAT_yyyyMMdd,
                            outputFormat = DATE_FORMAT_yyyyMMddHHmmssZZZZZ
                        )
                    }
                }
            ).show()
        }
    }
}