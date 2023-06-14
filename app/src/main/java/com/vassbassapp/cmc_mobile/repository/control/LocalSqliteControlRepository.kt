package com.vassbassapp.cmc_mobile.repository.control

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vassbassapp.cmc_mobile.model.Control
import com.vassbassapp.cmc_mobile.repository.DBConfig.Companion.DB_NAME
import com.vassbassapp.cmc_mobile.repository.DBConfig.Companion.DB_VERSION

class LocalSqliteControlRepository(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    ControlRepository,
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {

    companion object {
        private const val TABLE_NAME = "controls"
        private const val LOCAL_ID_COL = "local_id"
        private const val SERVER_ID_COL = "server_id"
        private const val NAME_COL = "name"
        private const val DATE_COL = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$LOCAL_ID_COL INTEGER PRIMARY KEY, " +
                "$SERVER_ID_COL INTEGER, " +
                "$NAME_COL TEXT, " +
                "$DATE_COL TEXT" +
                ")"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(sql)
        onCreate(db)
    }

    override fun getByLocalId(id: Int): Control? {
        val sql = "SELECT * FROM $TABLE_NAME WHERE $LOCAL_ID_COL = $id"

        val db = this.readableDatabase
        val cursor = db.rawQuery(sql, null)

        var result: Control? = null
        if (cursor.moveToFirst()) {
            result = getControlFromCursor(cursor)
        }

        cursor.close()
        return result
    }

    override fun getAll(): List<Control?> {
        val result = mutableListOf<Control?>()
        val sql = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase
        val cursor = db.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            result.add(getControlFromCursor(cursor))
        }

        cursor.close()
        return result
    }

    private fun getControlFromCursor(cursor: Cursor) : Control? {
        val localIdIndex = cursor.getColumnIndex(LOCAL_ID_COL)
        val serverIdIndex = cursor.getColumnIndex(SERVER_ID_COL)
        val nameIndex = cursor.getColumnIndex(NAME_COL)
        val dateIndex = cursor.getColumnIndex(DATE_COL)

        return if (localIdIndex >= 0 && serverIdIndex >= 0 && nameIndex >= 0 && dateIndex >= 0) {
            val localId = cursor.getString(localIdIndex)!!.toInt()
            val serverId = cursor.getString(serverIdIndex)!!.toInt()
            val name = cursor.getString(nameIndex)
            val date = cursor.getString(dateIndex)

            cursor.close()
            Control(localId, serverId, name, date)
        } else null
    }

    override fun add(control: Control) {
        val sql = "INSERT INTO $TABLE_NAME ($NAME_COL, $DATE_COL) " +
                "VALUES ('${control.name}', '${control.date}')"

        val db = this.writableDatabase
        db.execSQL(sql)
    }

    override fun deleteByLocalId(id: Int) {
        val sql = "DELETE FROM $TABLE_NAME WHERE $LOCAL_ID_COL = $id"

        val db = this.writableDatabase
        db.execSQL(sql)
    }

    override fun set(localId: Int, control: Control) {
        val sql = "UPDATE $TABLE_NAME " +
                "SET $SERVER_ID_COL = ${control.serverId}, " +
                "$NAME_COL = '${control.name}', " +
                "$DATE_COL = '${control.date}' " +
                "WHERE $LOCAL_ID_COL = $localId"

        val db = this.writableDatabase
        db.execSQL(sql)
    }

    override fun setServerId(localId: Int, serverId: Int) {
        val sql = "UPDATE $TABLE_NAME " +
                "SET $SERVER_ID_COL = $serverId " +
                "WHERE $LOCAL_ID_COL = $localId"

        val db = this.writableDatabase
        db.execSQL(sql)
    }

    override fun clear() {
        val sql = "DELETE FROM $TABLE_NAME"

        val db = this.writableDatabase
        db.execSQL(sql)
    }
}