package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public LiveData<List<Task>> getTask() {
        return this.mTaskDao.getTask();
    }

    public void insertTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void deleteTask(long taskId) {
        mTaskDao.deleteTask(taskId);
    }
}