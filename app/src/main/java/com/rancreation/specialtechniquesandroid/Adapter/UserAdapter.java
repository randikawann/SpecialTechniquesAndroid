package com.rancreation.specialtechniquesandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rancreation.specialtechniquesandroid.R;
import com.rancreation.specialtechniquesandroid.model.user.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    List<User> allUsers;


    public UserAdapter(List<User> allUsers) {

        this.allUsers = allUsers;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = mInflater.inflate(R.layout.user_card, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        String name = allUsers.get(position).getName();
        String address = allUsers.get(position).getAddress().toString();


        holder.name.setText(name);
        holder.address.setText(address);



    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView address;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.usename);
            address = itemView.findViewById(R.id.useraddress);


        }
    }
}
