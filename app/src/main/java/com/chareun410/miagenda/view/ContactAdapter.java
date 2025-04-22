package com.chareun410.miagenda.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chareun410.miagenda.R;
import com.chareun410.miagenda.data.ContactsRepository;
import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.domain.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private LayoutInflater inflater;
    protected List<Contact> contactsList;
    private static final String LOG_TAG = ContactAdapter.class.getSimpleName();

    public ContactAdapter(Context context, List<Contact> list) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contactsList = new ArrayList<>();
        this.contactsList.addAll(list);
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
        holder.contactId = contact.getId();
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void buscar(final String textoBuscar) {
        int longitud = textoBuscar.length();
        if (longitud == 0) {
            this.contactsList = new ArrayList<>();
            this.contactsList.addAll(ContactsRepository.getList());
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Contact> resultado = contactsList.stream()
                        .filter(i -> i.getFullname().toLowerCase().contains(textoBuscar.toLowerCase()))
                        .collect(Collectors.toList());

                // No es lo más eficiente, pero así evitamos vaciar la lista de ContactsRepository
                this.contactsList = new ArrayList<>();
                contactsList.addAll(resultado);
            } else {
                for (Contact c : contactsList) {
                    if (c.getFullname().toLowerCase().contains(textoBuscar.toLowerCase())) {
                        contactsList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void save(String name, String lastName, String phone, String address, Gender gender, Contact contact, View view) {
        // Si el contacto NO es nulo, se está editando un contacto
        boolean isEditing = contact != null;
        if (!isEditing) {
            contact = new Contact();
            contact.setName(name);
            contact.setLastName(lastName);
            contact.setPhone(phone);
            contact.setAddress(address);
            contact.setGender(gender);
            ContactsRepository.getList().add(contact);
        } else {
            List<Contact> lista = ContactsRepository.getList();
            for (Contact existent: lista) {
                if (existent.getId().equals(contact.getId())) {
                    existent.setName(name);
                    existent.setLastName(lastName);
                    existent.setPhone(phone);
                    existent.setAddress(address);
                    existent.setGender(gender);
                    break;
                }
            }
        }

        this.contactsList = new ArrayList<>();
        this.contactsList.addAll(ContactsRepository.getList());

        notifyDataSetChanged();
        Context context = view.getContext();
        Toast.makeText(context, "Contacto guardado", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone;

        private Long contactId;

        public Long getContactId() {
            return contactId;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_view_nombre_contacto);
            phone = (TextView) itemView.findViewById(R.id.text_view_numero_contacto);
            ImageButton callButton = itemView.findViewById(R.id.callButton);
            ImageButton editButton = itemView.findViewById(R.id.editButton);

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String phone = contactsList.get(getAdapterPosition()).getPhone();
                    intent.setData(Uri.parse("tel:" + phone));
                    context.startActivity(intent);
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, FormActivity.class);
                    Contact contact = contactsList.get(getAdapterPosition());
                    intent.putExtra("contact", contact);
                    context.startActivity(intent);
                }
            });
        }
    }


}
