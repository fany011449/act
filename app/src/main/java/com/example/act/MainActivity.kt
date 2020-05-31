package com.example.act
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "演員介紹"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vpActor.adapter = ViewPagerAdapter(this)  // viewpager實例化頁面
        vpActor.addOnPageChangeListener(viewPagerAdapterListener)  // 頁面切換時新增動作的函式
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.viewall_adapter, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miSwitch) {
            intent = Intent(this,ActAdapterChange::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.miLogin) {
            intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    private val viewPagerAdapterListener  = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(position: Int) {  // 頁面滑動狀態改變時
            // 三個狀態：0=>靜止，1=>拖曳中，2=>拖曳完成到新的頁面
        }
        override fun onPageScrolled(  // 頁面滾動時觸發
                position: Int,  // 目前頁面的index
                positionOffset: Float, // 頁面滾動時，目前頁面偏移的百分比
                positionOffsetPixels: Int  // 頁面滾動時，目前頁面偏移的像素值
        ) {
            val page_position = position  // position是從0開始
            val jsonFileRes = JsonFileRes().readJsonFile(this@MainActivity)
            tvActorName.text = jsonFileRes.act[page_position].actname
        }
        override fun onPageSelected(position: Int) {  // 當新的頁面被選擇時
            // position的初值會是1
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 23) {
            val REQUEST_CODE_PERMISSION_STORAGE = 100
            val permissions = arrayOf<String>(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            for (str in permissions) {
                if (checkSelfPermission(str!!) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE)
                    return
                }
            }
        }

    }
}
