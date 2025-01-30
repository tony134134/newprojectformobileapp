package com.example.mobileapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adepter extends RecyclerView.Adapter<Adepter.ViewHolder> {

    Context context;
    ArrayList<Model> arrayList;
    ActivityResultLauncher<Intent> activityResultLauncher;

    public Adepter(Context context, ArrayList<Model> arrayList, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.arrayList = arrayList;
        this.activityResultLauncher = activityResultLauncher;
    }

    @NonNull
    @Override
    public Adepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adepter.ViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.desc.setText(arrayList.get(position).getDescription());

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Helper helper = new Helper(context);
                                String id = String.valueOf(arrayList.get(position).getId());
                                helper.deleteData(id);

                                arrayList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, arrayList.size());

                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setIcon(R.drawable.baseline_add_alert_24)
                        .show();

                return true;
            }
        });

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("title", arrayList.get(position).getTitle());
            intent.putExtra("description", arrayList.get(position).getDescription());
            intent.putExtra("id", arrayList.get(position).getId());
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
