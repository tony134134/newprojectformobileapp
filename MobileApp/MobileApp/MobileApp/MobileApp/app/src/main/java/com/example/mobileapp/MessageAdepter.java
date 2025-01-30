package com.example.mobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdepter extends RecyclerView.Adapter<MessageAdepter.MyViewHolder> {

    List<Message> messageList;
    public MessageAdepter(List<Message> messageList) {

        this.messageList = messageList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Message message = messageList.get(position);
        if(message.getSendBy().equals(Message.SENT_BY_ME)){
            holder.leftChat.setVisibility(View.GONE);
            holder.rightChat.setVisibility(View.VISIBLE);
            holder.rightText.setText(message.getMessage());
        } else {
            holder.rightChat.setVisibility(View.GONE);
            holder.leftChat.setVisibility(View.VISIBLE);
            holder.leftText.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {

        return messageList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChat, rightChat;
        TextView leftText, rightText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChat = itemView.findViewById(R.id.leftChat);
            rightChat = itemView.findViewById(R.id.rightChat);
            leftText = itemView.findViewById(R.id.leftChatText);
            rightText = itemView.findViewById(R.id.rightChatText);
        }
    }
}
