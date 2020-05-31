package com.example.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_act_adapter_change.*
import kotlinx.android.synthetic.main.activity_actor_show_up.*

class ActAdapterChange : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_adapter_change)
        this.title = "演員介紹"
        val jsonFileRes = JsonFileRes().readJsonFile(this)
        val actImageIndex = intent.getIntExtra("index", 0)
        rvActCollect.adapter = RecyclerViewAdapter(this, actImageIndex,"act",jsonFileRes)
        rvActCollect.layoutManager = GridLayoutManager(this, 3)
    }
}
