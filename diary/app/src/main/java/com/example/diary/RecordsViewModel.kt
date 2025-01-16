package com.example.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class RecordsState(
    val allRecords: List<Record>,
    val filteredRecords: List<Record>,
    val isFiltered: Boolean
)

class RecordsViewModel : ViewModel() {
    private val _state = MutableStateFlow(RecordsState(emptyList(), emptyList(), false))
    val state: StateFlow<RecordsState> = _state

    private var currentFilterDate: Date? = null

    init {
        viewModelScope.launch {
            StorageApp.db.diaryRecordDao().getAllAsFlow().collect { allRecords ->
                _state.value = _state.value.copy(
                    allRecords = allRecords,
                    filteredRecords = applyCurrentFilter(allRecords),
                    isFiltered = currentFilterDate != null
                )
            }
        }
    }

    fun filterRecordsByDate(date: Date?) {
        currentFilterDate = date
        _state.value = _state.value.copy(
            filteredRecords = applyCurrentFilter(_state.value.allRecords),
            isFiltered = date != null
        )
    }

    private fun applyCurrentFilter(records: List<Record>): List<Record> {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return if (currentFilterDate == null) {
            records
        } else {
            records.filter { diaryRecord ->
                formatter.format(diaryRecord.createdAt) == currentFilterDate?.let {
                    formatter.format(
                        it
                    )
                }
            }
        }
    }

    fun createDiaryRecord(title: String, content: String) {
        viewModelScope.launch {
            val diaryRecordDao = StorageApp.db.diaryRecordDao()
            if (diaryRecordDao.findByTitle(title) != null) {
                return@launch
            }

            val randomUid = UUID.randomUUID().toString()
            val newDiaryRecord = Record(
                randomUid,
                title,
                content,
                System.currentTimeMillis(),
            )

            diaryRecordDao.insertAll(newDiaryRecord)
        }
    }

    fun updateDiaryRecord(uid: String, title: String, content: String, date: Long) {
        viewModelScope.launch {
            val diaryRecordDao = StorageApp.db.diaryRecordDao()

            val diaryRecord = Record(
                uid,
                title,
                content,
                date,
            )

            diaryRecordDao.updateAll(diaryRecord)
        }
    }

    fun deleteDiaryRecord(diaryRecord: Record) {
        viewModelScope.launch {
            val diaryRecordDao = StorageApp.db.diaryRecordDao()
            diaryRecordDao.delete(diaryRecord)
        }
    }

}