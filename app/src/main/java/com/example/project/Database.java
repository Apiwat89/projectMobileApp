package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;

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

        // for table lessons ///////////////////////////////////////////////////////////////////////
        String insertLessons = "INSERT INTO " + Table_Lessons + " (" + Lessons_LessonName + ") VALUES " +
                "('DataType'), ('Variable'), ('Operator'), ('Command'), ('Function');";
        sqLiteDatabase.execSQL(insertLessons);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Scores);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Lessons);
        onCreate(sqLiteDatabase);
    }

    // for table users /////////////////////////////////////////////////////////////////////////////
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

//    public Cursor getAllUser() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM "+ Table_Users, null);
//
//        return res;
//    }

    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + Users_UserID + " FROM "+ Table_Users + " WHERE " +
                Users_Username + " = ? AND " + Users_Password + " = ?", new String[]{username, password});

        return (res != null && res.moveToFirst()) ? res : null;
    }

    public boolean getUserUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Users + " WHERE " + Users_Username + " = ?",
                new String[]{username});

        return (res != null && res.moveToFirst()) ? true : false;
    }

    public boolean getUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Users + " WHERE " + Users_Email + " = ?",
                new String[]{email});

        return (res != null && res.moveToFirst()) ? true : false;
    }

    public boolean updateUserPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Users_Password, newPassword);

        int result = db.update(Table_Users, values, Users_Email + " = ?",
                new String[]{email});

        return result > 0 ? true : false;
    }

    // for table scores ////////////////////////////////////////////////////////////////////////////
    public ArrayList<HashMap<String, Integer>> getLessonsLearned(String userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + Scores_LessonID + ", " + Scores_Score +
                        " FROM " + Table_Scores + " WHERE " + Scores_UserID + " = ?",
                new String[]{userID});

        ArrayList<HashMap<String, Integer>> lessons = new ArrayList<>();
        if (res != null) {
            while (res.moveToNext()) {
                HashMap<String, Integer> lessonData = new HashMap<>();
                lessonData.put("lessonID", res.getInt(0));
                lessonData.put("score", res.getInt(1));
                lessons.add(lessonData);
            }
            res.close();
        }
        return lessons;
    }

    public boolean getScoreUser(String lessonID, String userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Scores + " WHERE " +
                Scores_LessonID + " = ? AND " + Scores_UserID + " = ?",
                new String[]{lessonID, userID});

        return (res != null && res.getCount() > 0) ? true : false;
    }

    public boolean insertScore(String userID, String lessonID, int score, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Scores_UserID, userID);
        values.put(Scores_LessonID, lessonID);
        values.put(Scores_Score, score);
        values.put(Scores_Status, status);

        long result = db.insert(Table_Scores, null, values);
        db.close();

        return result == -1 ? false : true;
    }

    public boolean updateScore(String userID, String lessonID, int score, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Scores_Score, score);
        values.put(Scores_Status, status);

        int result = db.update(Table_Scores, values,
                Scores_UserID + " = ? AND " + Scores_LessonID + " = ?",
                new String[]{userID, lessonID});
        return result > 0;
    }

//    public Cursor getAllScore() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM "+ Table_Scores, null);
//
//        return res;
//    }
}
