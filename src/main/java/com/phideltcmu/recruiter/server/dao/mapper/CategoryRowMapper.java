package com.phideltcmu.recruiter.server.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int line) throws SQLException {
        CategoryResultSetExtractor extractor = new CategoryResultSetExtractor();
        return extractor.extractData(rs);
    }

}