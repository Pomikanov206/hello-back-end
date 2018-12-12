package com.ua.pomikanov.hellobackend.controllers;

import com.ua.pomikanov.hellobackend.JavaPostgreSqlRetrieve;
import com.ua.pomikanov.hellobackend.domain.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class HelloController {
    private static final String template = "Hello, %s!";

    @RequestMapping("/hello/contacts")
    @ResponseBody
    public List<Contact> contacts(@RequestParam(value = "nameFilter", required = false, defaultValue = "World!") String nameFilter) {
        List<Contact> contacts = JavaPostgreSqlRetrieve.getContacts();
        return replaceContacts(contacts, nameFilter);
    }

    private List<Contact> replaceContacts(List<Contact> contacts, String filter) {
        List<Contact> copyContacts = new ArrayList<>();

        Pattern p = Pattern.compile(filter);

        for (Contact contact:
             contacts) {
            Matcher m = p.matcher(contact.getName());
            if (!m.matches())
                copyContacts.add(contact);
        }
        return copyContacts;
    }
}