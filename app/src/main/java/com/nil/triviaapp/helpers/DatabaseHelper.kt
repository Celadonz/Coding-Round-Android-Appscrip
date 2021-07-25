package com.nil.triviaapp.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nil.triviaapp.models.Data

const val DATABASE_NAME = "My Database"
const val TABLE_NAME = "Questions"
const val COL_ID = "id"
const val COL_GAME_ID = "game_id"
const val COL_PLAYER_NAME = "player_name"
const val COL_QUESTION = "question"
const val COL_ANSWERS = "answer"
const val COL_GAME_DATE = "date"


class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        //create the table
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_GAME_ID VARCHAR(256), $COL_PLAYER_NAME VARCHAR(256), $COL_QUESTION VARCHAR(256),$COL_ANSWERS VARCHAR(256), $COL_GAME_DATE VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //...
    }

    //add data to the table
    fun addData(data: Data) {
        //get database
        val db: SQLiteDatabase = writableDatabase

        //prepare data to save to database
        val row: ContentValues = ContentValues()
        row.put(COL_GAME_ID, data.gameID)
        row.put(COL_PLAYER_NAME, data.playerName)
        row.put(COL_QUESTION, data.question)
        row.put(COL_ANSWERS, data.answer)
        row.put(COL_GAME_DATE, data.time)

        //save to database
        db.insert(TABLE_NAME, null, row)

        //close databse
        db.close()
    }

    //get the number of rows in the table
    /*fun getCount(): Long {
        //get database
        val db: SQLiteDatabase = readableDatabase

        //get number of rows for a specific table
        val count = DatabaseUtils.queryNumEntries(db, TABLE_NAME)

        //close database
        db.close()

        //return
        return count
    }*/

    //delete everything from the table
    /*fun truncateTable() {
        //get database
        val db: SQLiteDatabase = readableDatabase

        //prepare sql statement
        val queryString = "DELETE FROM $TABLE_NAME;"

        //execute sql statement
        db.execSQL(queryString)
    }*/

    //get data from database
    fun getData(): ArrayList<Data> {
        val data = ArrayList<Data>()

        //get readable instance on database
        val db: SQLiteDatabase = readableDatabase

        //prepare sql statement
        val queryString = "SELECT * FROM $TABLE_NAME;"

        val cursor: Cursor = db.rawQuery(queryString, null)

        if (cursor.moveToFirst()) {
            do {
                val question = Data(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5))
                data.add(question)
            } while (cursor.moveToNext())
        }

        cursor.close()

        db.close()

        return data
    }
}