package com.prashanth.sampleapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prashanth.sampleapp.R
import com.prashanth.sampleapp.model.UserModel

class UserModelListAdapter : RecyclerView.Adapter<UserModelListAdapter.ViewHolder>() {

    private var userModelList: ArrayList<UserModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title!!.text = userModelList!![position].title
        holder.body!!.text = userModelList!![position].body
    }

    override fun getItemCount(): Int {
        return if (userModelList == null) 0 else userModelList!!.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title: TextView? = null
        var body: TextView? = null

        init {
            title = itemView.findViewById(R.id.title)
            body = itemView.findViewById(R.id.body)
        }

    }

    fun update(userModelList: ArrayList<UserModel>) {
        this.userModelList = userModelList
        notifyDataSetChanged()
    }

}