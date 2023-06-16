package com.vassbassapp.cmc_mobile

import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vassbassapp.cmc_mobile.model.Control
import com.vassbassapp.cmc_mobile.repository.control.ControlRepository
import com.vassbassapp.cmc_mobile.repository.control.LocalSqliteControlRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var repository : ControlRepository
    private val list = mutableListOf<String>()
    private lateinit var adapter : ArrayAdapter<String>

    private lateinit var controlName : EditText
    private lateinit var controlList : ListView
    private lateinit var btnAdd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository = LocalSqliteControlRepository(this)

        controlName = findViewById(R.id.controlName)
        controlList = findViewById(R.id.controlList)
        btnAdd = findViewById(R.id.btnAdd)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        controlList.adapter = adapter

        btnAdd.setOnClickListener(addBtnClickListener)
        refreshControlList()
    }

    private fun refreshControlList() {
        list.clear()
        list.addAll(repository.getAll().map { it.toString() } )
        adapter.notifyDataSetChanged()
    }

    private val addBtnClickListener = OnClickListener {
        val name = controlName.text.toString()
        if (name.isNotBlank()) {
            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            val control = Control(null, null, name, date)
            repository.add(control)
            refreshControlList()
            controlName.setText("")
        }
    }
}