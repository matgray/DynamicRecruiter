package com.phideltcmu.recruiter.server.dao;

import com.phideltcmu.recruiter.server.dao.mapper.PersonRowMapper;
import com.phideltcmu.recruiter.shared.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RecruitListDao implements IDao {
    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    @Override
    public void create(String firstName, String lastName, String andrewID) {
        JdbcTemplate insert = new JdbcTemplate(dataSource);
        insert.update(
                "INSERT INTO recruitList.infolist VALUES (?, ?, ?, default, default, default, default, default, default)",
                new Object[]{firstName, lastName, andrewID});
    }

    @Override
    public List<Person> select(String firstname, String lastname) {
        return null;
    }

    @Override
    public List<Person> selectAll() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("SELECT * FROM recruitList.infolist",
                new PersonRowMapper());
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void delete(String firstName, String lastName) {
    }
}
