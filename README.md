Copyright (c) 2013 Mathew Gray.

This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.

DynamicRecruiter
================

A web application for managing the recruitment of students at Carnegie Mellon University.  Accesses the CMU LDAP directory to make searching students easy, and gives admins easy control as to who is being recruited when.  Authenticates with Facebook, uses GWT, MySQL, Spring, and OpenID-LDAP.

Maven Commands:
----------------

    mvn clean         # delete temporary stuff

    mvn test          # run all the tests (gwt and junit)

    mvn gwt:run       # run development mode

    mvn gwt:compile   # compile to javascript

    mvn package       # generate a .war package ready to deploy

For more information about other available goals, read maven and gwt-maven-plugin
documentation (http://maven.apache.org, http://mojo.codehaus.org/gwt-maven-plugin)
