package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.EmailAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailSenderDAO extends DAO{

    public List<EmailAccount> fetchAll() {
        String tableName = "email_sender_credentials";
        final String query = String.format("SELECT * FROM %s", tableName);

        List<EmailAccount> senders = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                EmailAccount account = new EmailAccount(rs.getString("email"), rs.getString("pass"));
                senders.add(account);
            }

        } catch (SQLException ignored) {
        }

        return senders;
    }
}
