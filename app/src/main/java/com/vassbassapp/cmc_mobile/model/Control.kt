package com.vassbassapp.cmc_mobile.model

data class Control(val localId: Int?, val serverId: Int?, val name: String, val date: String) {
    override fun toString(): String {
        return "$date \"$name\" [$serverId]"
    }
}