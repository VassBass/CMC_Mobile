package com.vassbassapp.cmc_mobile.model

import java.util.Objects

data class Control(val localId: Int?, val serverId: Int?, val name: String, val date: String) {
    override fun toString(): String {
        val sync = if (Objects.isNull(serverId)) "Не синхронізовано" else serverId
        return "$date\n\"$name\"\n[$localId] ($sync)"
    }
}