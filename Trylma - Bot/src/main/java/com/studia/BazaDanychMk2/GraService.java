package com.studia.BazaDanychMk2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class GraService {
    private JdbcTemplate jdbcTemplate;

    // Setter dla JdbcTemplate
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int zapiszGre(GraDB gra) {
        String sql = "INSERT INTO Gry (typ, iloscGraczy) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Wykonanie zapytania z wykorzystaniem JdbcTemplate i przekazanie KeyHolder
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, gra.getTyp());
            ps.setInt(2, gra.getIloscGraczy());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public GraDB pobierzGre(int id) {
        try{
            String sql = "SELECT * FROM Gry WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] {id}, (rs, rowNum) -> {
                GraDB gra = new GraDB();
                gra.setId(rs.getInt("id"));
                gra.setTyp(rs.getString("typ"));
                gra.setIloscGraczy(rs.getInt("iloscGraczy"));
                return gra;
            });
        }catch (Exception e){
            return null;
        }

    }

    public String pobierzWszystkieGry() {
        String sql = "SELECT id, typ, iloscGraczy FROM gry";

        List<String> gry = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> "ID: " + rs.getInt("id") + ", Typ: " + rs.getString("typ") + ", Ilosc Graczy: " + rs.getInt("iloscGraczy")
        );

        return String.join("&", gry);
    }
}