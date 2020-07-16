package com.example.tuwen.adapter

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tuwen.R
import com.example.tuwen.bean.MainRvItemBean

class MainAdapter(private val data: List<MainRvItemBean>, private val context: Context) :
    RecyclerView.Adapter<MainAdapter.MainRvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_main_rv, parent, false)
        return MainRvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainRvViewHolder, position: Int) {
        val itemBean = data[position]
        itemBean.run {
            when (TextUtils.isEmpty(content)) {
                true -> {
                    holder.etContent.visibility = View.GONE
                }
                false -> {
                    holder.etContent.setText(content)
                    holder.etContent.visibility = View.VISIBLE
                }
            }

            when (TextUtils.isEmpty(imageUrl)) {
                true -> {
                    holder.ivImage.visibility = View.GONE
                }
                false -> {
                    holder.ivImage.setImageResource(R.drawable.ic_launcher_background)
                    holder.ivImage.visibility = View.VISIBLE
                }
            }

            when (TextUtils.isEmpty(imageSketch)) {
                true -> {
                    holder.etImageSketch.visibility = View.GONE
                }
                false -> {
                    holder.etImageSketch.setText(imageSketch)
                    holder.etImageSketch.visibility = View.VISIBLE
                }
            }
        }

        holder.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                data[holder.adapterPosition].content = s.toString()
            }
        })

        holder.etImageSketch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                data[holder.adapterPosition].imageSketch = s.toString()
            }
        })
    }

    override fun getItemCount() = data.size

    class MainRvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var etContent: EditText = itemView.findViewById(R.id.et_content)
        var ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        var etImageSketch: EditText = itemView.findViewById(R.id.et_image_sketch)
    }
}