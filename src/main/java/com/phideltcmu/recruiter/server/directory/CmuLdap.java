/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.server.directory;

import com.phideltcmu.recruiter.shared.model.Person;
import com.unboundid.ldap.sdk.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CmuLdap {

    /**
     * Get the name and department associated with the AndrewID
     */
    public static String[] fields = {"cn", "cmuDepartment", "cmuAndrewId", "mail", "cmuStudentClass"};

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
        String query = "(&(|(cn=*" + searchText + "*)(cmuAndrewId=" + searchText + "))(cmuStudentLevel=Undergrad))";
        return queryLDAP(query);
    }


    /**
     * Get the fields by only querying the andrewID
     *
     * @param andrewID
     * @return
     * @throws LDAPException
     */
    public static Person getAttributesStrictlyByAndrewID(String andrewID) throws LDAPException {
        String query = "(&(cmuAndrewId=" + andrewID + ")(cmuStudentLevel=Undergrad))";
        List<Person> matches = queryLDAP(query);
        if (matches.size() == 0) {
            return null;
        }
        return matches.get(0);
    }

    private static List<Person> queryLDAP(String query) throws LDAPException {
        List<Person> matchList = new ArrayList<Person>(5);
        /**
         * Create a new connection
         */
        LDAPConnection connection = new LDAPConnection("ldap.andrew.cmu.edu", 389);

        /**
         * For each field, query the server
         */
        SearchResult searchResults = connection.search("ou=ANDREWPERSON,dc=ANDREW,dc=cmu,dc=edu",
                SearchScope.SUB, query, fields[0], fields[1], fields[2], fields[3], fields[4]);

        /**
         * In the case that there are multiple entries for a field,
         * just choose the first one
         */

        Set<String> seenAndrewIDs = new HashSet<String>();

        for (int i = 0; i < searchResults.getEntryCount(); i++) {
            SearchResultEntry entry = searchResults.getSearchEntries().get(i);
            if (!seenAndrewIDs.contains(entry.getAttributeValue("cmuAndrewId"))) {
                Person p = new Person();
                String[] nameSplit = entry.getAttributeValue("cn").split(" ");
                p.setFirstName(nameSplit[0]);
                p.setLastName(nameSplit[nameSplit.length - 1]);
                p.setAndrewID(entry.getAttributeValue("cmuAndrewId"));
                p.setMajor(entry.getAttributeValue("cmuDepartment"));
                p.setClassYear(entry.getAttributeValue("cmuStudentClass"));
                matchList.add(p);
            }
        }
        connection.close();
        return matchList;
    }
}
