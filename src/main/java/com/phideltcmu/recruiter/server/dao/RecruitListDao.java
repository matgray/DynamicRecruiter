package com.phideltcmu.recruiter.server.dao;

import com.phideltcmu.recruiter.server.dao.mapper.CategoryRowMapper;
import com.phideltcmu.recruiter.server.dao.mapper.InternalUserRowMapper;
import com.phideltcmu.recruiter.server.dao.mapper.PersonRowMapper;
import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.phideltcmu.recruiter.shared.model.Category;
import com.phideltcmu.recruiter.shared.model.InternalUser;
import com.phideltcmu.recruiter.shared.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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
        //TODO FIX CATS
        checkSingleton();

        StringBuilder b = new StringBuilder();
        for (Category c : desiredCategories) {
            b.append("status='" + c.getValue() + "'");
            b.append(" OR ");
        }
        String opts = b.substring(0, b.length() - 4);

        //noinspection unchecked
        return jdbcTemplate.query("SELECT * FROM recruitList.infolist",
                new PersonRowMapper());
    }

    @Override
    public void delete(String andrewId) {
        checkSingleton();
        jdbcTemplate.update("DELETE FROM recruitList.infolist WHERE andrewid=?",
                new Object[]{andrewId});
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

        int id;

        List<InternalUser> internalMatches = getInternalUser(user);

        if (internalMatches.size() == 0) {
            jdbcTemplate.update("INSERT INTO recruitList.userList VALUES (default,?,default,?)",
                    new Object[]{user.getFullName(), user.getId()});

            internalMatches = getInternalUser(user);
        }

        if (internalMatches.size() > 1 || internalMatches.size() == 0) {
            throw new IllegalStateException("Problem adding internal user to DB");
        }

        id = internalMatches.get(0).getDatabaseID();

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
        jdbcTemplate.update("UPDATE recruitList.infolist SET status=? WHERE andrewID=?",
                new Object[]{newStatus, andrewID});
    }

    private List<InternalUser> getInternalUser(AuthUser user) {
        return jdbcTemplate.query("SELECT * FROM recruitList.userList WHERE facebookID=?",
                new Object[]{user.getId()},
                new InternalUserRowMapper());
    }
}
