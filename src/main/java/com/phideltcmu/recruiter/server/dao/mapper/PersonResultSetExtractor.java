package com.phideltcmu.recruiter.server.dao.mapper;


import com.phideltcmu.recruiter.shared.model.Person;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonResultSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setFirstName(rs.getString(1));
        person.setLastName(rs.getString(2));
        person.setAndrewID(rs.getString(3));
        person.setPhoneNumber(rs.getString(4));
        person.setMajor(rs.getString(5));
        person.setClassYear(rs.getString(6));
        person.setNotes(rs.getString(7));
        person.setRecommencedBy(rs.getString(8));
        person.setStatus(rs.getString(9));
        return person;
    }
}