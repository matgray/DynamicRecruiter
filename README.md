Dynamic Recruiter
================

Copyright (c) 2013 Mathew Gray.

This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.

What it is:
-------------

A web application for managing the recruitment of students at Carnegie Mellon University.  
Allows for easy collaboration between recruiters to ensure that the organization is on the same page when
it comes to who is being recruited.
Accesses the CMU LDAP directory to make searching students eas.  Authenticates with Facebook OAuth.  Uses GWT, MySQL, Spring, and OpenID-LDAP.

Maven commands:
----------------

    mvn clean         # delete temporary stuff

    mvn test          # run all the tests (gwt and junit)

    mvn gwt:run       # run development mode

    mvn gwt:compile   # compile to javascript

    mvn package       # generate a .war package ready to deploy

For more information about other available goals, read maven and gwt-maven-plugin
documentation (http://maven.apache.org, http://mojo.codehaus.org/gwt-maven-plugin)
