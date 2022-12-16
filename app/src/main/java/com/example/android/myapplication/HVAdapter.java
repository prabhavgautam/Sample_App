package com.example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HVAdapter extends RecyclerView.Adapter<HVAdapter.MyViewHolder> {

    Context context;
    ArrayList<Users> usersArrayList;


    public HVAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public HVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.horizontal_view_item,parent,false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull HVAdapter.MyViewHolder holder, int position) {


        Users users = usersArrayList.get(position);

        holder.firstName.setText(users.fName);
        holder.lastName.setText(users.lName);
        holder.Email.setText(users.email);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,userDetails.class);
                intent.putExtra("email", users.getEmail());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, Email;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.horizontal_item_firstName);
            lastName = itemView.findViewById(R.id.horizontal_item_lastName);
            Email = itemView.findViewById(R.id.horizontal_item_Email);
            cardView = itemView.findViewById(R.id.Horizontal_item_cardView);


        }
    }
}
