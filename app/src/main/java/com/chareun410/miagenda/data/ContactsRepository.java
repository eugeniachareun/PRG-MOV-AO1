package com.chareun410.miagenda.data;

import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.domain.Gender;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    private static List<Contact> list;

    public static List<Contact> getList() {
        if(list == null) {
            initContactList();
        }
        return list;
    }

    private static void initContactList(){
        Contact sample1 = new Contact("Eugenia", "Chareun", "555123", "9 de Julio 123", Gender.FEMALE);
        Contact sample2 = new Contact("Martín", "Pescador", "12355", "Galarza 1000", Gender.MALE);
        Contact sample3 = new Contact("Florencia", "Amarillo", "123456", "Los Tulipanes 500", Gender.FEMALE);

        list.add(sample1);
        list.add(sample2);
        list.add(sample3);
    }
}
