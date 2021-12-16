package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORY
    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    public TaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        this.mExecutor = executor;
    }

    // FOR PROJECT
    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProject();
    }

    // FOR TASK
    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTask();
    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.insertTask(task));
    }

    public void deleteTask(long taskId) {
        mExecutor.execute(() -> mTaskRepository.deleteTask(taskId));
    }
}