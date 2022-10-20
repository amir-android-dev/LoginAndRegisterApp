package com.ema.loginandregisterapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<MViewHolder> {
    List<User> users;
    Context context;

    public UserAdapter(List<User> users,Context context) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUserName.setText(user.getUsername());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

class MViewHolder extends RecyclerView.ViewHolder {
    TextView tvUserName;

    public MViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUserName = itemView.findViewById(R.id.tv_name_adapter);
    }
}
