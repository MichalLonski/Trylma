package com.studia.BazaDanychMk2;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RuchService {
    private JdbcTemplate jdbcTemplate;

    // Setter dla JdbcTemplate
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void zapiszRuch(Ruch ruch) {
        String sql = "INSERT INTO Ruchy (idGry, ruch) VALUES (?, ?)";
        jdbcTemplate.update(sql, ruch.getIdGry(), ruch.getRuch());
    }

    public List<Ruch> pobierzRuchy(int idGry) {
        String sql = "SELECT * FROM Ruchy WHERE idGry = ?";
        return jdbcTemplate.query(sql, new Object[] {idGry}, (rs, rowNum) -> {
            Ruch ruch = new Ruch();
            ruch.setId(rs.getInt("id"));
            ruch.setIdGry(rs.getInt("idGry"));
            ruch.setRuch(rs.getString("ruch"));
            return ruch;
        });
    }
}
