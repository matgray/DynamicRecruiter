package com.phideltcmu.recruiter.server.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternalUserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        InternalUserSetExtractor extractor = new InternalUserSetExtractor();
        return extractor.extractData(resultSet);
    }
}
