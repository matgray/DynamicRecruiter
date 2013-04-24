/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.server.dao;

import com.phideltcmu.recruiter.server.dao.mapper.*;
import com.phideltcmu.recruiter.shared.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class RecruitListDao implements IDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate = null;

    private void checkSingleton() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
    }

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    @Override
    public void create(String firstName, String lastName, String andrewID) {
        checkSingleton();
        jdbcTemplate.update(
                "INSERT INTO recruitList.infolist VALUES (?, ?, ?, default, default, default, default, default, default)",
                new Object[]{firstName, lastName, andrewID});
    }

    @Override
    public List<Person> select(String firstname, String lastname) {
        return null;
    }

    @Override
    public List<Person> selectAll(List<Category> desiredCategories) {
        checkSingleton();

        List<String> list = new ArrayList<String>();
        for (Category c : desiredCategories) {
            list.add(c.getValue());
        }

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("categories", list);
        return namedParameterJdbcTemplate.query("SELECT * FROM recruitList.infolist WHERE status IN (:categories)",
                parameters,
                new PersonRowMapper()
        );
    }

    @Override
    public void delete(String andrewId) {
        checkSingleton();
        jdbcTemplate.update("DELETE FROM recruitList.infolist WHERE andrewid=?",
                new Object[]{andrewId});
    }

    @Override
    public boolean register(AuthUser user) {
        List<InternalUser> internalMatches = getInternalUser(user.getId());

        if (internalMatches.size() == 0) {
            jdbcTemplate.update("INSERT INTO recruitList.userList VALUES (default,?,default,?)",
                    new Object[]{user.getFullName(), user.getId()});

            internalMatches = getInternalUser(user.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Person p, AuthUser user) {
        checkSingleton();
        List<Person> matches = jdbcTemplate.query("SELECT * FROM recruitList.infolist WHERE andrewid=?",
                new Object[]{p.getAndrewID()},
                new PersonRowMapper());

        if (matches.size() != 0) {
            System.out.println("Already in DB");
            return false;
        }

        int id = getInternalUser(user.getId()).get(0).getDatabaseID();

        jdbcTemplate.update("INSERT INTO recruitList.infolist VALUES (?, ?, ?, default, ?, ?, default, default, default, ?)",
                new Object[]{p.getLastName(), p.getFirstName(), p.getAndrewID(), p.getMajor(), p.getClassYear(), id});

        return true;
    }

    @Override
    public boolean addCategory(String categoryName) {
        checkSingleton();

        List<Person> matches = jdbcTemplate.query("SELECT * FROM recruitList.statuses WHERE status=?",
                new Object[]{categoryName},
                new PersonRowMapper());

        if (matches.size() != 0) {
            System.out.println("Status is already in DB");
            return false;
        } else {
            jdbcTemplate.update("INSERT INTO recruitList.statuses VALUES (?)",
                    new Object[]{categoryName});
            return true;
        }
    }

    @Override
    public List<Category> getCategories() {
        checkSingleton();
        return jdbcTemplate.query("SELECT * FROM recruitList.statuses",
                new CategoryRowMapper());
    }

    @Override
    public void changeCategory(String andrewID, String newStatus) {
        checkSingleton();
        jdbcTemplate.update("UPDATE recruitList.infolist SET status=? WHERE andrewid=?",
                new Object[]{newStatus, andrewID});
    }

    @Override
    public void saveNotes(String andrewID, String notes) {
        checkSingleton();
        jdbcTemplate.update("UPDATE recruitList.infolist SET notes=? WHERE andrewid=?",
                new Object[]{notes, andrewID});
    }

    @Override
    public void addToReferrals(String andrewid, String fbid) {
        checkSingleton();

        String appendString = (getInternalUser(fbid).get(0).getDatabaseID()) + ",";
        jdbcTemplate.update("UPDATE recruitList.infolist SET additionalReferrals= CONCAT(additionalReferrals,?) WHERE andrewid=?",
                new Object[]{appendString, andrewid});
    }

    private List<InternalUser> getInternalUser(String fbID) {
        return jdbcTemplate.query("SELECT * FROM recruitList.userList WHERE facebookID=?",
                new Object[]{fbID},
                new InternalUserRowMapper());
    }

    @Override
    public String getNameFromInternalID(String internalID) {
        checkSingleton();
        List<String> singletonList = jdbcTemplate.query("SELECT name FROM recruitList.userList WHERE id=?",
                new Object[]{internalID},
                new InternalNameRowMapper());

        if (singletonList.size() > 0) {
            return singletonList.get(0);
        }
        return "ERROR";
    }

    @Override
    public void updateTelephone(String andrewID, String phoneNumber) {
        checkSingleton();
        jdbcTemplate.update("UPDATE recruitList.infolist SET phonenumber=? WHERE andrewid=?",
                new Object[]{phoneNumber, andrewID});
    }

    @Override
    public boolean isAdmin(String fbid) {
        checkSingleton();
        List<Boolean> booleanList = jdbcTemplate.query("SELECT isAdmin FROM recruitList.userList WHERE facebookID = ?",
                new Object[]{fbid}, new IsAdminRowMapper());
        if (booleanList.size() > 0) {
            return booleanList.get(0);
        }
        return false;
    }

    @Override
    public List<InternalUser> getNonAdmins() {
        checkSingleton();
        return jdbcTemplate.query("SELECT * FROM recruitList.userList WHERE isAdmin=0",
                new InternalUserRowMapper());
    }

    @Override
    public List<InternalUser> getAdmins() {
        checkSingleton();
        return jdbcTemplate.query("SELECT * FROM recruitList.userList WHERE isAdmin=1",
                new InternalUserRowMapper());
    }

    @Override
    public void setAdmin(String fbid, Boolean b) {
        checkSingleton();
        jdbcTemplate.update("UPDATE recruitList.userList SET isAdmin=? WHERE facebookID=?",
                new Object[]{b, fbid});
    }

    @Override
    public List<InternalUserStat> getStats() {
        checkSingleton();
        return jdbcTemplate.query("SELECT referredBy, COUNT(*) FROM recruitList.infolist GROUP BY referredBy",
                new StatCountRowMapper());
    }
}
