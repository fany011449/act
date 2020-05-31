package com.example.act

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
/*參考網站
https://t.codebug.vip/questions-293274.htm
https://givemepass.blogspot.com/2016/04/android_28.html
https://blog.csdn.net/aqi00/article/details/83241762
以上為loadimage到內部儲存中
https://codertw.com/android-%E9%96%8B%E7%99%BC/22828/

 */

class SaveImageTask(context: Context) : AsyncTask<String, Unit, Unit>() {
    private var mContext: WeakReference<Context> = WeakReference(context)
    @SuppressLint("SdCardPath")
    override fun doInBackground(vararg params: String) {
        val url = params[0]
        val name = params[1]
        val requestOptions = RequestOptions()
            .override(100)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        mContext.get()?.run{
            val file = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$name.png")
//                val bitmap = Glide
//                    .with(this)
//                    .asBitmap()
//                    .load(url)
//                    .apply(requestOptions)
//                    .submit()
//                    .get()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 80, FileOutputStream(file))
//                FileOutputStream(file).flush()
//                FileOutputStream(file).close()
            val openConnection = java.net.URL(url).openConnection()
            val bytes = openConnection.getInputStream().readBytes()
            file.writeBytes(bytes)
            MediaStore.Images.Media.insertImage(this.contentResolver, file.absolutePath, "$name.png", null)
        }
    }
}