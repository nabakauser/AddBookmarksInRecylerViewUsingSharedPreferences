package com.example.recyclerviewwithdatastore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(
    var user: MutableList<User>?,
    private val onItemClick: (User?) -> Unit,
    private val onBookmarkClicked: (User?) -> Unit,
) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout,
            parent,
            false
        )
        return MyViewHolder(view)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val response = user?.get(position)
        with(holder) {
            if ((response?.id?.rem(2) ?: 0) == 1) {
                uiCardView.setCardBackgroundColor(R.color.black)
            } else uiCardView.setCardBackgroundColor(R.color.lavender)
            uiTvId.text = response?.id.toString()
            uiTvTitle.text = response?.title
            uiTvBody.text = response?.body
            if (response?.isSaved==true) {
                holder.uiIvBookmark.setImageResource(R.drawable.ic_colored_bookmark)
            } else {
                holder.uiIvBookmark.setImageResource(R.drawable.ic_bookmark)
            }
        }
    }

    override fun getItemCount(): Int {
        return user?.size ?: 0
    }

    fun onUserListChanged(userList: List<User>?) {
        this.user?.clear()
        userList?.let { this.user?.addAll(it) }
        notifyDataSetChanged()
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var uiTvTitle: TextView = itemView.findViewById(R.id.uiTvTitle)
        var uiTvBody: TextView = itemView.findViewById(R.id.uiTvBody)
        var uiCardView: CardView = itemView.findViewById(R.id.uiCardView)
        var uiIvBookmark: ImageView = itemView.findViewById(R.id.uiIvBookmark)
        var uiTvId: TextView = itemView.findViewById(R.id.uiTvId)

        init {
            uiTvTitle.setOnClickListener {
                onItemClick(user?.get(adapterPosition))
            }
            uiIvBookmark.setOnClickListener {
                onBookmarkClicked(user?.get(adapterPosition))
                notifyDataSetChanged()
            }
        }
    }
}