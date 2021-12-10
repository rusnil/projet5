package com.cleanup.todoc.DI;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    public ViewModelFactory(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        this.mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
         if (modelClass.isAssignableFrom(TaskViewModel.class)) {
             return (T) new TaskViewModel(mProjectRepository, mTaskRepository, mExecutor);
         }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}