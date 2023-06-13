package com.vassbassapp.cmc_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.EditText
import android.widget.ListView
import android.widget.Button
import android.view.View.OnClickListener

class MainActivity : AppCompatActivity() {

    private val repository : ControlRepository = LocalSqliteControlRepository(this, null)
    private val list = mutableListOf<String>()
    private val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)

    private val controlName : EditText = findViewById(R.id.controlName)
    private val controlList : ListView = findViewById(R.id.controlList)
    private val btnAdd : Button = findViewById(R.id.btnAdd)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controlList.setAdapter(adapter)

        btnAdd.setOnClickListener(addBtnClickListener)
        refreshControlList()
    }

    fun refreshControlList() {
        list.clear()
        list.addAll(repository.getAll().map { it.toString() } )
        adapter.notifyDataSetChanged()
    }

    val addBtnClickListener = object : OnClickListener() {
        override fun onClick(v: View!) {
            val name = controlName.getText().toString()
            if (name.isNotBlank()) {
                val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                Control control = Control(null, null, name, date)
                repository.add(control)
                refreshControlList()
                controlName.setText("")
            }
        }
    }
}