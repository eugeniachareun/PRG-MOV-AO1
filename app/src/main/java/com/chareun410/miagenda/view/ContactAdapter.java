package com.chareun410.miagenda.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chareun410.miagenda.R;
import com.chareun410.miagenda.databinding.ActivityContactsBinding;
import com.chareun410.miagenda.domain.Contact;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private LayoutInflater inflater;
    protected List<Contact> contactsList;

    public ContactAdapter(Context context, List<Contact> list) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contactsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_contact, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactsList.get(position);
        holder.name.setText(contact.getFullname());
        holder.phone.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_view_nombre_contacto);
            phone = (TextView) itemView.findViewById(R.id.text_view_numero_contacto);
        }
    }


}
