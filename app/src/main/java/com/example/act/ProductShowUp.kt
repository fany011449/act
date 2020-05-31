package com.example.act

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_actor_show_up.*
import kotlinx.android.synthetic.main.activity_product_show_up.*

class ProductShowUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_show_up)
        val jsonFileRes = JsonFileRes().readJsonFile(this)
        val productImageIndex = intent.getIntExtra("index", 0)
        val actImageIndex = intent.getIntExtra("image", 0)
        Glide
            .with(this)
            .load(jsonFileRes.act[actImageIndex].product[productImageIndex].imageurl)
            .into(ivProduct)
        ivProduct.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setTitle("通知")
                .setMessage("是否要儲存圖片?")
                .setPositiveButton("Ok"){ _, _ ->
                    SaveImageTask(this).execute(jsonFileRes.act[actImageIndex].actimageurl,jsonFileRes.act[productImageIndex].actname)
                }
                .setNegativeButton ("Cancel") { dialog, _ -> // 兩個參數 dialogInterface, i
                    dialog.cancel()
                }
                .show()
            ;true
        }
        tvProductDescribe.text = jsonFileRes.act[actImageIndex].product[productImageIndex].describe
    }
}
