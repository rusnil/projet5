package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTask();

    @Insert
    void insertTask (Task task);

    @Query("Delete FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);
}