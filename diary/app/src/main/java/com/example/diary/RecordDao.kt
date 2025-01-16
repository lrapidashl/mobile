package com.example.diary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM record")
    fun getAllAsFlow(): Flow<List<Record>>

    @Insert
    suspend fun insertAll(vararg diaryRecord: Record)

    @Update
    suspend fun updateAll(vararg diaryRecord: Record)

    @Query("SELECT * FROM record " +
            "WHERE title LIKE :title LIMIT 1")
    suspend fun findByTitle(title: String): Record?

    @Delete
    suspend fun delete(record: Record)
}