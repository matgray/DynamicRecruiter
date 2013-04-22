package com.phideltcmu.recruiter.server.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternalNameRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        InternalNameSetExtractor extractor = new InternalNameSetExtractor();
        return extractor.extractData(resultSet);
    }
}
