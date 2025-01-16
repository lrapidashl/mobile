package com.example.diary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diary.databinding.FragmentRecordsBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class FragmentRecords : Fragment(R.layout.fragment_records) {
    private val viewModel: RecordsViewModel by activityViewModels()
    private lateinit var binding: FragmentRecordsBinding
    private lateinit var recordsAdapter: RecordsAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecordsBinding.bind(view)
        recordsAdapter = RecordsAdapter(
            deleteDiaryRecord = { diaryRecord: Record ->
                viewModel.deleteDiaryRecord(diaryRecord)
            },
            gotoEditorFn = { args: Bundle ->
                findNavController().navigate(R.id.action_fragmentRecords_to_fragmentEditor, args)
            }
        )
        binding.recycleView.adapter = recordsAdapter

        binding.plusButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecords_to_fragmentEditor)
        }

        binding.createButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecords_to_fragmentEditor)
        }

        lifecycleScope.launch {
            viewModel.state.collect { diaryRecords ->
                recordsAdapter.diaryRecordList = diaryRecords.filteredRecords
                recordsAdapter.notifyDataSetChanged()
                updateVisibility(diaryRecords.filteredRecords.isEmpty())

                binding.filter.setOnClickListener {
                    if (diaryRecords.isFiltered) {
                        resetFilter()
                    } else {
                        showDatePicker()
                    }
                }
            }
        }
    }

    private fun updateVisibility(isEmpty: Boolean) {
        binding.recordsField.visibility = View.GONE
        binding.emptyDiary.visibility = View.GONE
        if (isEmpty) {
            binding.emptyDiary.visibility = View.VISIBLE
        } else {
            binding.recordsField.visibility = View.VISIBLE
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            viewModel.filterRecordsByDate(selectedDate.time)
            binding.filter.setColorFilter(Color.argb(255, 255, 0, 0))
        }, year, month, day).show()
    }

    private fun resetFilter() {
        binding.filter.setColorFilter(Color.argb(255, 0, 0, 0))
        viewModel.filterRecordsByDate(null)
    }

}