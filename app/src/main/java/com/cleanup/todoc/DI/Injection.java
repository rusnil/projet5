package com.cleanup.todoc.DI;

import android.content.Context;

import com.cleanup.todoc.database.SaveDatabase;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ProjectRepository provideProjectSource(Context context) {
        SaveDatabase database = SaveDatabase.getInstance(context);
        return new ProjectRepository(database.projectDao());
    }

    public static TaskRepository provideTaskSource(Context context) {
        SaveDatabase database = SaveDatabase.getInstance(context);
        return new TaskRepository(database.taskDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ProjectRepository projectRepository = provideProjectSource(context);
        TaskRepository taskRepository = provideTaskSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(projectRepository, taskRepository, executor);
    }
}