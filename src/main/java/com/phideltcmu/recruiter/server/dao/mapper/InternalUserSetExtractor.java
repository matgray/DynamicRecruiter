package com.phideltcmu.recruiter.server.dao.mapper;

import com.phideltcmu.recruiter.shared.model.InternalUser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternalUserSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        InternalUser user = new InternalUser();
        user.setDatabaseID(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setAdmin(resultSet.getBoolean(3));
        return user;
    }
}
