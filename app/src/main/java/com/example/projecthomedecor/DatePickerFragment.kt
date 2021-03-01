package com.example.projecthomedecor

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            activity!!,
            0,
            this,
            year,
            month,
            day
        )
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int){
        var tv : TextView? = activity?.findViewById(R.id.show_HBD)
        tv!!.text = formatDate(day, month, year)
    }

    override fun onCancel(dialog: DialogInterface) {
        Toast.makeText(activity,"Please select a date.", Toast.LENGTH_SHORT).show()
        super.onCancel(dialog)
    }

    private fun formatDate( day:Int, month:Int,  year:Int):String{
        var calendar: Calendar = Calendar.getInstance();
        calendar.set(year, month, day)
        val chosenDate = calendar.time
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }
}