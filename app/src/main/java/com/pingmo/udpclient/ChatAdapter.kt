package com.pingmo.udpclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val mData: ArrayList<String>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {
        val context = parent.context
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.recycler_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val txt = mData[position]
        holder.txtChat.text = txt
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    inner class ChatViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var txtChat: TextView

        init {
            txtChat = itemView.findViewById(R.id.txtChat)
        }
    }
}