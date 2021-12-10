package com.cleanup.todoc.database;

import static com.cleanup.todoc.model.Project.getAllProjects;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class SaveDatabase extends RoomDatabase {

    public static volatile SaveDatabase INSTANCE;

    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    public static SaveDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues projectValues = new ContentValues();
                for (Project project : getAllProjects()) {
                projectValues.put("id", project.getId());
                projectValues.put("name", project.getName());
                projectValues.put("color", project.getColor());
                db.insert("Project", OnConflictStrategy.IGNORE, projectValues);
                }
            }
        };
    }
}