package com.vassbassapp.cmc_mobile.repository

import java.sql.Connection

interface DBHelper {
    fun getConnection() : Connection
}