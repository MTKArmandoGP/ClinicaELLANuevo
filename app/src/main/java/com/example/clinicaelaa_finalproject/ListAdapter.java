package com.example.clinicaelaa_finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicaelaa_finalproject.ListElement;
import com.example.clinicaelaa_finalproject.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener mListener; // Listener for item click events

    public interface OnItemClickListener {
        void onItemClick(int citaId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && mListener != null) {
                    ListElement item = mData.get(adapterPosition);
                    mListener.onItemClick(item.getIdCita());
                }
            }
        });
    }

    public void setItems(List<ListElement> items) {
        mData = items;
    }

    public ListElement getItem(int position) {
        if (position >= 0 && position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    public void onItemClick(int position) {
        if (mListener != null) {
            ListElement item = getItem(position);
            if (item != null) {
                mListener.onItemClick(item.getIdCita());
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView Doctor, Hora, Fecha;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iconCardView);
            Doctor = itemView.findViewById(R.id.tvCitaDoctor);
            Hora = itemView.findViewById(R.id.tvCitaHora);
            Fecha = itemView.findViewById(R.id.tvFecha);
        }

        void bindData(final ListElement item) {
            Doctor.setText(item.getDoctor());
            Hora.setText(item.getHora());
            Fecha.setText(item.getFecha());
        }
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call a method to delete the selected item
                deleteItem(position);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void deleteItem(int position) {
        // Remove the item from the list
        mData.remove(position);

        // Update the RecyclerView
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }
}

