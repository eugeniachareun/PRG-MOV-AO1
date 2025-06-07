package com.chareun410.miagenda.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.domain.Gender;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {

    private static final String LOG_TAG = ContactsRepository.class.getSimpleName();
    private final DbHelper dbContacts;

    public ContactsRepository(Context context) {
        dbContacts = new DbHelper(context);
    }

    public long insert(Contact contact) {
        long id = 0;
        try{
            SQLiteDatabase db = dbContacts.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", contact.getName());
            values.put("phone", contact.getPhone());
            values.put("lastName", contact.getLastName());
            values.put("address", contact.getAddress());
            values.put("gender", contact.getGender().name());

            id = db.insert(dbContacts.TABLE_CONTACTOS, null, values);
            db.close();
        } catch (Exception ex) {
            Log.e(LOG_TAG, "ERROR inserting contact: " + ex);
        }
        return id;
    }

    public int update(long id, Contact contact) {
        int rows = 0;
        try {
            SQLiteDatabase db = dbContacts.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", contact.getName());
            values.put("phone", contact.getPhone());
            values.put("lastName", contact.getLastName());
            values.put("address", contact.getAddress());
            values.put("gender", contact.getGender().name());

            rows = db.update(
                    dbContacts.TABLE_CONTACTOS,
                    values,
                    "_id=?",
                    new String[]{ String.valueOf(id) }
            );

            db.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "ERROR updating contact: " + e);
        }

        return rows;
    }

    public List<Contact> getAll() {
        List<Contact> list = new ArrayList<>();

        try {
            SQLiteDatabase db = dbContacts.getReadableDatabase();

            Cursor c = db.query(
                    dbContacts.TABLE_CONTACTOS,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndexOrThrow("_id"));
                String name  = c.getString(c.getColumnIndexOrThrow("name"));
                String phone= c.getString(c.getColumnIndexOrThrow("phone"));
                String lastName= c.getString(c.getColumnIndexOrThrow("lastName"));
                String address   = c.getString(c.getColumnIndexOrThrow("address"));
                Gender gender  = Gender.valueOf(c.getString(c.getColumnIndexOrThrow("gender")));

                Contact contact = new Contact(name, lastName, phone, address, gender);
                contact.setId(id);
                list.add(contact);
            }
            c.close();
            db.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "ERROR getting contacts: " + e);
        }

        return list;
    }

    public Contact getById(long id) {
        Contact result = null;
        try {
            SQLiteDatabase db = dbContacts.getReadableDatabase();
            Cursor c = db.query(
                    dbContacts.TABLE_CONTACTOS,
                    null,
                    "_id=?",
                    new String[]{ String.valueOf(id) },
                    null,
                    null,
                    null
            );

            if (c.moveToFirst()) {
                long _id = c.getLong(c.getColumnIndexOrThrow("_id"));
                String name  = c.getString(c.getColumnIndexOrThrow("name"));
                String phone= c.getString(c.getColumnIndexOrThrow("phone"));
                String lastName= c.getString(c.getColumnIndexOrThrow("lastName"));
                String address   = c.getString(c.getColumnIndexOrThrow("address"));
                Gender gender  = Gender.valueOf(c.getString(c.getColumnIndexOrThrow("gender")));

                result = new Contact(name, lastName, phone, address, gender);
                result.setId(_id);
            }
            c.close();
            db.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "ERROR getting contact: " + e);
        }

        return result;
    }

    public int delete(long id) {
        int rows = 0;
        try {
            SQLiteDatabase db = dbContacts.getWritableDatabase();
            rows = db.delete(
                    dbContacts.TABLE_CONTACTOS,
                    "_id=?",
                    new String[]{ String.valueOf(id) }
            );
            db.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "ERROR deleting contact: " + e);
        }
        return rows;
    }
}
