package com.vassbassapp.cmc_mobile.repository.control

import android.content.Context
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

        if (cursor.moveToFirst()) {
            val serverIdIndex = cursor.getColumnIndex(SERVER_ID_COL)
            val nameIndex = cursor.getColumnIndex(NAME_COL)
            val dateIndex = cursor.getColumnIndex(DATE_COL)

            if (serverIdIndex >= 0 && nameIndex >= 0 && dateIndex >= 0) {
                val serverId = cursor.getString(serverIdIndex)!!.toInt()
                val name = cursor.getString(nameIndex)
                val date = cursor.getString(dateIndex)

                cursor.close()
                return Control(id, serverId, name, date)
            }
        }

        cursor.close()
        return null
    }

    override fun getAll(): List<Control> {
        TODO("Not yet implemented")
    }

    override fun add(control: Control) {
        TODO("Not yet implemented")
    }

    override fun deleteByLocalId(id: Int) {
        TODO("Not yet implemented")
    }

    override fun set(localId: Int, control: Control) {
        TODO("Not yet implemented")
    }
}