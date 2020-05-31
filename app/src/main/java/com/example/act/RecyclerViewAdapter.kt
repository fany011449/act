package com.example.act

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_recyclerforact.view.*

class RecyclerViewAdapter(val context: Context
                          , val index : Int
                          , val whatTypeOfJson : String
                          , val jsonFileRes : JsonFileRes.Jtype) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

        private val imageItem : ImageView = itemView.ivProductInRecyclerView
        fun Bind(pos : Int) {
            Glide
                .with(context)
                .load(
                    when(whatTypeOfJson) {
                        "act" -> jsonFileRes.act[pos].actimageurl
                        "product" -> jsonFileRes.act[index].product[pos].imageurl
                        else -> 0
                    }
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageItem)
            imageItem.setOnLongClickListener {
                AlertDialog.Builder(context)
                    .setTitle("通知")
                    .setMessage("是否要儲存圖片?")
                    .setPositiveButton("Ok"){ _, _ ->
                        when (whatTypeOfJson) {
                            "act" -> SaveImageTask(context).execute(jsonFileRes.act[pos].actimageurl,jsonFileRes.act[pos].actname)
                            "product"-> SaveImageTask(context).execute(jsonFileRes.act[index].product[pos].imageurl,jsonFileRes.act[index].product[pos].imagename)
                        }
                    }
                    .setNegativeButton ("Cancel") { dialog, _ -> // 兩個參數 dialogInterface, i
                        dialog.cancel()
                    }
                    .show()
                ;true
            }
            imageItem.setOnClickListener {
                val intent = when(whatTypeOfJson){
                    "product" -> {
                        Intent(context, ProductShowUp::class.java)
                    }
                    "act" -> {
                        Intent(context, ActorShowUp::class.java)
                    }
                    else -> Intent(context, MainActivity::class.java)
                }
                intent.putExtra("index", pos)
                intent.putExtra("image", index)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (whatTypeOfJson) {
            "act" -> {
                val view = LayoutInflater.from(parent.context) // 負責把Layout再變成可用的View
                    .inflate(R.layout.activity_recyclerforact, parent, false) // 是否在RecyclerView生成之前自動把圖檔放入View當中
                ViewHolder(view)
            }
            "product" -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_recycler, parent, false)
                ViewHolder(view)
            }
            else ->{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_recycler, parent, false)
                ViewHolder(view)
            }
        }
    }
    override fun getItemCount(): Int {
        return when (whatTypeOfJson) {
            "act" -> {
                jsonFileRes.act.count()
            }
            "product" -> {
                jsonFileRes.act[index].product.count()
            }
            else -> 0
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Bind(position)
    }
}