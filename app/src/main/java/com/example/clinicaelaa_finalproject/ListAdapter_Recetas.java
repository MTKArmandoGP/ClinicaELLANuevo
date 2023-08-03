package com.example.clinicaelaa_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter_Recetas extends RecyclerView.Adapter<ListAdapter_Recetas.ViewHolder> {

    private List<ListElement_Recetas> mData;
    private LayoutInflater mInflater;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int idReceta);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ListAdapter_Recetas(List<ListElement_Recetas> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element_receta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));

        // Set click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition(); // Obtain the updated position
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    // Get the selected item's idReceta
                    int idReceta = mData.get(position).getIdReceta();
                    // Call the onItemClick method of the listener
                    listener.onItemClick(idReceta);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<ListElement_Recetas> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView PacienteRecetas, FechaRecetas;

        ViewHolder(View itemView) {
            super(itemView);
            PacienteRecetas = itemView.findViewById(R.id.tvPacienteReceta);
            FechaRecetas = itemView.findViewById(R.id.tvFechaReceta);
        }

        void bindData(final ListElement_Recetas item) {
            PacienteRecetas.setText(item.getPacienteRecetas());
            FechaRecetas.setText(item.getFechaRecetas());
        }
    }
}



