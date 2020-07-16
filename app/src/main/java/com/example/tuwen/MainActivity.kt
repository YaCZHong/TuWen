package com.example.tuwen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tuwen.adapter.MainAdapter
import com.example.tuwen.bean.MainRvItemBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mAdapter: MainAdapter
    private val data: MutableList<MainRvItemBean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        mAdapter = MainAdapter(data, this)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = mAdapter
    }

    private fun initData() {
        for (i in 1 until 20) {
            val bean = MainRvItemBean("这里是$i", null, "描述$i")
            data.add(bean)
        }
    }
}
