package com.example.act

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_actor_show_up.*

class ActorShowUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "演員簡介"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_show_up)
        val jsonFileRes = JsonFileRes().readJsonFile(this)
        val actImageIndex = intent.getIntExtra("index", 0)
        Glide.with(this).load(jsonFileRes.act[actImageIndex].actimageurl).into(ivActorPhoto)
        ivActorPhoto.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setTitle("通知")
                .setMessage("是否要儲存圖片?")
                .setPositiveButton("Ok"){ _, _ ->
                    SaveImageTask(this).execute(jsonFileRes.act[actImageIndex].actimageurl,jsonFileRes.act[actImageIndex].actname)
                }
                .setNegativeButton ("Cancel") { dialog, _ -> // 兩個參數 dialogInterface, i
                    dialog.cancel()
                }
                .show()
            ;true
        }
        tvActorDescribe.text = jsonFileRes.act[actImageIndex].actdescribe
        rvPdoductCollect.adapter = RecyclerViewAdapter(this, actImageIndex,"product",jsonFileRes)
        rvPdoductCollect.layoutManager = GridLayoutManager(this, 3)
    }
}
