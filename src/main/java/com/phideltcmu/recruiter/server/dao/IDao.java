package com.phideltcmu.recruiter.server.dao;


import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.phideltcmu.recruiter.shared.model.Person;

import javax.sql.DataSource;
import java.util.List;


public interface IDao {

    void setDataSource(DataSource ds);

    void create(String firstName, String lastName, String andrewID);

    List<Person> select(String firstname, String lastname);

    List<Person> selectAll();

    void delete(String andrewId, String token);

    boolean add(Person p, AuthUser token);
}
