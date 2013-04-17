package com.phideltcmu.recruiter.client.comparator;

import com.phideltcmu.recruiter.shared.model.Person;

import java.util.Comparator;

public class PersonComparators {

    public static Comparator<Person> lastNameComparator = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getLastName().compareTo(o2.getLastName());
        }
    };
}
