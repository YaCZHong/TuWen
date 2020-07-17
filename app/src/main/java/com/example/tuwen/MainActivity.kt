package com.example.tuwen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tuwen.adapter.MainAdapter
import com.example.tuwen.bean.MainRvItemBean
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.util.Log
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.provider.MediaStore.MediaColumns
import android.content.ContentResolver
import android.net.Uri


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 10001
        const val TAG = "MainActivity"
    }

    lateinit var mAdapter: MainAdapter
    private val data: MutableList<MainRvItemBean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bean = MainRvItemBean(
            "Matisse", null,
            null
        )
        data.add(bean)
        mAdapter = MainAdapter(data, this)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = mAdapter
        tv_add_img.setOnClickListener {
            selectPicture()
        }
    }

    private fun selectPicture() {
        Matisse.from(this)
            .choose(MimeType.ofImage())
            .countable(true)
            .maxSelectable(1)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .forResult(REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            val mSelected = Matisse.obtainResult(data)
            Log.d(TAG, "mSelected: $mSelected")

            if (this.data.size != 0 && this.data[this.data.size - 1].imageUrl == null) {
                this.data[this.data.size - 1].imageUrl =
                    getFilePathFromContentUri(mSelected[0], contentResolver)
                this.data[this.data.size - 1].imageSketch = "请输入简介"
                mAdapter.notifyDataSetChanged()
            } else {
                addItem(mSelected[0])
            }
        }
    }


    fun getFilePathFromContentUri(selectedVideoUri: Uri, contentResolver: ContentResolver): String {
        val filePath: String
        val filePathColumn = arrayOf(MediaColumns.DATA)

        val cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null)
        //	    也可用下面的方法拿到cursor
        //	    Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }

    fun addItem(ImageUri: Uri) {
        val bean = MainRvItemBean(
            "Matisse",
            getFilePathFromContentUri(ImageUri, contentResolver),
            "描述"
        )
        this.data.add(bean)
        mAdapter.notifyItemInserted(this.data.size - 1)
    }
}
