package com.cleanup.todoc.testDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestDao {
    private static final long PROJECT_ID = 1;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);
    private static final Task TASK_DEMO = new Task(PROJECT_ID, "task test", new Date().getTime());
    private TaskDatabase mTaskDatabase;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.mTaskDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry
                .getInstrumentation().getContext(), TaskDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        mTaskDatabase.close();
    }

    @Test
    public void insertProjects() throws InterruptedException {
        this.mTaskDatabase.projectDao().insertProject(PROJECT_DEMO);
        Project project = LiveDataTestUtil.getValue(this.mTaskDatabase.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void checkTasks() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.mTaskDatabase.taskDao().getTask(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertTasks() throws InterruptedException {
        this.mTaskDatabase.projectDao().insertProject(PROJECT_DEMO);
        this.mTaskDatabase.taskDao().insertTask(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.mTaskDatabase.taskDao().getTask(PROJECT_ID));
        assertEquals(1, tasks.size());
    }

    @Test
    public void deleteTask() throws InterruptedException {
        this.mTaskDatabase.projectDao().insertProject(PROJECT_DEMO);
        this.mTaskDatabase.taskDao().insertTask(TASK_DEMO);

        List<Task> taskList = LiveDataTestUtil.getValue(this.mTaskDatabase.taskDao().getTask(PROJECT_ID));
        assertEquals(1, taskList.size());

        this.mTaskDatabase.taskDao().deleteTask(taskList.get(0).getId());
        List<Task> taskList1 = LiveDataTestUtil.getValue(this.mTaskDatabase.taskDao().getTask(PROJECT_ID));
        assertTrue(taskList1.isEmpty());
    }
}
