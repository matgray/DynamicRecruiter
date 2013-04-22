package com.phideltcmu.recruiter.server.dao.mapper;


import com.phideltcmu.recruiter.shared.model.Category;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryResultSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        return new Category(rs.getString(1));
    }
}