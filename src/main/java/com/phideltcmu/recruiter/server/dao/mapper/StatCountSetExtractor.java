package com.phideltcmu.recruiter.server.dao.mapper;


import com.phideltcmu.recruiter.shared.model.InternalUserStat;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatCountSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        InternalUserStat internalUserStat = new InternalUserStat();
        internalUserStat.setInternalID(rs.getInt("referredBy"));
        internalUserStat.setUniqueAdditions(rs.getInt("COUNT(*)"));
        return internalUserStat;
    }
}