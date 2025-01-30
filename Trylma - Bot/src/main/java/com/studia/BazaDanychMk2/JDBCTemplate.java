package com.studia.BazaDanychMk2;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JDBCTemplate {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplateObject() {
        return jdbcTemplateObject;
    }
}
