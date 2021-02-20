package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class EmailAccount {

    private final String address;
    private final String password;
    private final Properties properties;
    private final Session session;

    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;

        properties = new Properties();
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");

        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public String toString() {
        return address;
    }

    public String getOutgoingHost() {
        return getProperties().getProperty("outgoingHost");
    }
}