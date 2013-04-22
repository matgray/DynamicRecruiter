package com.phideltcmu.recruiter.server.directory;

import com.phideltcmu.recruiter.shared.model.Person;
import com.unboundid.ldap.sdk.*;

import java.util.ArrayList;
import java.util.List;

public class CmuLdap {

    /**
     * Get the name and department associated with the AndrewID
     */
    public static String[] fields = {"cn", "cmuDepartment", "cmuAndrewId", "mail"};

    /**
     * Get attributes associated with the searchText (Currently just the full
     * name and departement of the person
     *
     * @param searchText the andrew id of the player
     * @return a list where the 0th element is the name of the person and the
     *         1st element is the department of the person
     * @throws LDAPException if there is a problem connecting to the CMU LDAP
     *                       server
     */
    public static List<Person> getAttributes(String searchText) throws
            LDAPException {

        List<Person> matchList = new ArrayList<Person>(5);
        /**
         * Create a new connection
         */
        LDAPConnection connection = new LDAPConnection("ldap.andrew.cmu.edu", 389);

        /**
         * For each field, query the server
         */
        SearchResult searchResults = connection.search("ou=ANDREWPERSON,dc=ANDREW,dc=cmu,dc=edu",
                SearchScope.SUB, "(cn~=" + searchText + ")", fields[0], fields[1], fields[2], fields[3]);

        /**
         * In the case that there are multiple entries for a field,
         * just choose the first one
         */
        for (int i = 0; i < searchResults.getEntryCount(); i++) {
            SearchResultEntry entry = searchResults.getSearchEntries().get(i);
            Person p = new Person();
            String[] nameSplit = entry.getAttributeValue("cn").split(" ");
            p.setFirstName(nameSplit[0]);
            p.setLastName(nameSplit[nameSplit.length - 1]);
            p.setAndrewID(entry.getAttributeValue("cmuAndrewId"));
            p.setMajor(entry.getAttributeValue("cmuDepartment"));
            matchList.add(p);
        }
        connection.close();
        return matchList;
    }
}
