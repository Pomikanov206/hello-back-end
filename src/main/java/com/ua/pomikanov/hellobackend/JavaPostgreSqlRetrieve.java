package com.ua.pomikanov.hellobackend;

import com.ua.pomikanov.hellobackend.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class JavaPostgreSqlRetrieve {

    @Autowired
    private ConnectionSettings connectionSettings;

    //private final static String url = "jdbc:postgresql://localhost:5432/contacts";
    //private final static String user = "client";
    //private final static String password = "qwerty";
    public List<Contact> getContacts() {

        List<Contact> contacts = new ArrayList<>();

        String url = connectionSettings.getJdbcString();
        String user = connectionSettings.getJdbcUser();
        String password = connectionSettings.getJdbcPassword();

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement("SELECT * FROM contacts");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                contacts.add(new Contact(rs.getInt(1),rs.getString(2)));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgreSqlRetrieve.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return contacts;
    }
}