package com.vassbassapp.cmc_mobile.repository.control

import com.vassbassapp.cmc_mobile.model.Control

interface ControlRepository {
    fun getByLocalId(id : Int) : Control?
    fun getAll() : List<Control?>
    fun add(control : Control)
    fun deleteByLocalId(id : Int)
    fun set(localId : Int, control : Control)
    fun setServerId(localId: Int, serverId: Int)
    fun clear()
}