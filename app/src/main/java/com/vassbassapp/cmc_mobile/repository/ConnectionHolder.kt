package com.vassbassapp.cmc_mobile.repository

import java.sql.Connection

public interface ConnectionHolder {
    fun getConnection() : Connection
}