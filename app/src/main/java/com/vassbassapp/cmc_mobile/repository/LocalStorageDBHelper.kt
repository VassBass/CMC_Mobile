package com.vassbassapp.cmc_mobile.repository

import java.sql.Connection

class LocalStorageDBHelper : DBHelper {
    companion object {
        private val DB_NAME = "CMC"
        private val DB_VERSION = 1
    }

    override fun getConnection(): Connection {
        TODO("Not yet implemented")
    }
}