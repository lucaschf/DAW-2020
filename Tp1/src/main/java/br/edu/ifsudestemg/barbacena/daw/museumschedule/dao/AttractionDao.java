package br.edu.ifsudestemg.barbacena.daw.museumschedule.dao;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.Attraction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDao extends DAO {

    private final String tableName = "attractions";
    private final MuseumDAO museumDao = new MuseumDAO();

    public boolean add(Attraction attraction) {

        final String query = String.format("INSERT INTO %s (title, details, cover_url, museum_id, beginning_exhibition," +
                " end_exhibition) VALUES (?, ?, ?, ?, ?, ?)", tableName);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {

            statement.setString(1, attraction.getTitle());
            statement.setString(2, attraction.getDetails());
            statement.setString(3, attraction.getCoverUrl());
            statement.setLong(4, attraction.getMuseum().getId());
            statement.setDate(5, Date.valueOf(attraction.getBeginningExhibition()));
            statement.setDate(6, Date.valueOf(attraction.getEndExhibition()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Attraction> fetchAll() {

        final String query = String.format("SELECT * FROM %s", tableName);

        final ArrayList<Attraction> attractions = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            var rs = statement.executeQuery();

            while (rs.next()) {
                attractions.add(extractAttraction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }

    public Attraction fetchById(int id) {

        final String query = String.format("SELECT * FROM %s WHERE id = %d", tableName, id);

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            var rs = statement.executeQuery();

            if (rs.next()) {
                return extractAttraction(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Attraction extractAttraction(ResultSet rs) throws SQLException {
        Attraction attraction = new Attraction();
        attraction.setId(rs.getInt("id"));
        attraction.setTitle(rs.getString("title"));
        attraction.setDetails(rs.getString("details"));
        attraction.setCoverUrl(rs.getString("cover_url"));
        attraction.setMuseum(museumDao.fetchById(rs.getLong("museum_id")));
        attraction.setBeginningExhibition(rs.getDate("beginning_exhibition").toLocalDate());
        attraction.setEndExhibition(rs.getDate("end_exhibition").toLocalDate());

        return attraction;
    }
}
