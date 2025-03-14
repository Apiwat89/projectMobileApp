package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String Database_name = "JavaQuest.db";
    private static final int Database_version = 1;

    // table users
    public static final String Table_Users = "Users";
    public static final String Users_UserID = "UserID";
    public static final String Users_Username = "Username";
    public static final String Users_Password = "Password";
    public static final String Users_Email = "Email";

    // table lessons
    public static final String Table_Lessons = "Lessons";
    public static final String Lessons_LessonID = "LessonID";
    public static final String Lessons_LessonName = "LessonName";

    // table scores
    public static final String Table_Scores = "Scores";
    public static final String Scores_ScoreID = "ScoreID";
    public static final String Scores_UserID = "UserID";
    public static final String Scores_LessonID = "LessonID";
    public static final String Scores_Score = "Score";
    public static final String Scores_Status = "Status";

    public Database (Context context) {
        super(context, Database_name, null, Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");

        String createUsersTable = "CREATE TABLE " + Table_Users + " (" +
                Users_UserID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Users_Username + " TEXT NOT NULL, " +
                Users_Password + " TEXT NOT NULL, " +
                Users_Email + " TEXT UNIQUE NOT NULL" +
                ");";

        String createLessonsTable = "CREATE TABLE " + Table_Lessons + " (" +
                Lessons_LessonID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Lessons_LessonName + " TEXT NOT NULL" +
                ");";

        String createScoresTable = "CREATE TABLE " + Table_Scores + " (" +
                Scores_ScoreID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Scores_UserID + " INTEGER NOT NULL, " +
                Scores_LessonID + " INTEGER NOT NULL, " +
                Scores_Score + " INTEGER NOT NULL, " +
                Scores_Status + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + Scores_UserID + ") REFERENCES " +
                Table_Users + "(" + Users_UserID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + Scores_LessonID + ") REFERENCES " +
                Table_Lessons + "(" + Lessons_LessonID + ") ON DELETE CASCADE" +
                ");";

        sqLiteDatabase.execSQL(createUsersTable);
        sqLiteDatabase.execSQL(createLessonsTable);
        sqLiteDatabase.execSQL(createScoresTable);
        insertLesson();
        testinsertUser();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Scores);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Lessons);
        onCreate(sqLiteDatabase);
    }

    // for table users
    public void testinsertUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Users_Username, "apiwat");
        values.put(Users_Password, "apiwat");
        values.put(Users_Email, "apiwat@gamil.com");
        db.insert(Table_Users, null, values);
        db.close();
    }

    public boolean insertUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Users_Username, username);
        values.put(Users_Password, password);
        values.put(Users_Email, email);
        long result = db.insert(Table_Users, null, values);
        db.close();
        return result == -1 ? false : true;
    }

    public boolean getUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Users + " WHERE " + Users_Email + " = ?", new String[]{email});
        db.close();
        return (res != null && res.getCount() > 0) ? true : false;
    }

    public boolean updatUserPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Users_Password, newPassword);
        int result = db.update(Table_Users, values, Users_Email + " = ?", new String[]{email});
        db.close();
        return result > 0 ? true : false;
    }

    // for table lessons
    public void insertLesson() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String [] lesson = {"DataType", "Variable", "Operator", "Command", "Function"};
        for (int n = 0; n < lesson.length; n++) {
            values.put(Lessons_LessonName, lesson[n]);
            db.insert(Table_Lessons, null, values);
        }
        db.close();
    }
}
