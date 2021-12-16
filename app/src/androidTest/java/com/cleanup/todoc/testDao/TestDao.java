package com.cleanup.todoc.testDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.SaveDatabase;
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

    private SaveDatabase mSaveDatabase;
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(1L,"test project", 0xFFEADAD1);
    private static Task TASK_DEMO = new Task(PROJECT_ID,"task test", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.mSaveDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
            SaveDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        mSaveDatabase.close();
    }

    @Test
    public void insertProjects() throws InterruptedException {
        this.mSaveDatabase.projectDao().insertProject(PROJECT_DEMO);
        Project project = LiveDataTestUtils.getValue(this.mSaveDatabase.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void insertTasks() throws InterruptedException {
        this.mSaveDatabase.projectDao().insertProject(PROJECT_DEMO);
        this.mSaveDatabase.taskDao().insertTask(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtils.getValue(this.mSaveDatabase.taskDao().getTask(PROJECT_ID));
        assertEquals(1, tasks.size());
    }

    @Test
    public void deleteTask() throws InterruptedException {
        this.mSaveDatabase.projectDao().insertProject(PROJECT_DEMO);
        this.mSaveDatabase.taskDao().insertTask(TASK_DEMO);

        List<Task> taskList = LiveDataTestUtils.getValue(this.mSaveDatabase.taskDao().getTask(PROJECT_ID));
        assertEquals(1, taskList.size());

        this.mSaveDatabase.taskDao().deleteTask(taskList.get(0).getId());
        List<Task> taskList1 = LiveDataTestUtils.getValue(this.mSaveDatabase.taskDao().getTask(PROJECT_ID));
        assertTrue(taskList1.isEmpty());
    }
}
