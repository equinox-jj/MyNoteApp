package com.mynoteapp.view

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mynoteapp.R
import com.mynoteapp.data.model.NoteData
import com.mynoteapp.data.model.NotePriority

class ShareViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(noteData: List<NoteData>) {
        emptyDatabase.value = noteData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    fun verifyDataFromUser(
        title: String,
        description: String
    ): Boolean { // to check the data is empty or not
        return !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): NotePriority {
        return when (priority) {
            "High Priority" -> {
                NotePriority.HIGH
            } // if priority has value High, it will return high
            "Medium Priority" -> {
                NotePriority.MEDIUM
            }
            "Low Priority" -> {
                NotePriority.LOW
            }
            else -> NotePriority.LOW
        }
    }

}