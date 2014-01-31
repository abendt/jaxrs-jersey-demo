tomcat-jersey-arquillian
========================
This is a sample maven based project featuring tomcat(web container), jersey(jax-rs implementation) and arquillian(test framework).
Execute "mvn test" to build the application and execute it with arquillian in-container test running embedded tomcat 7.
This project provides just an example how to combine the three components together. Take a look at pom.xml for detailes.
The sample project is inspired from the examples shown in jersey 2.x user guide. http://jersey.java.net/documentation/latest/user-guide.html#running-project. In this sample, Grzilly is replaced with tomcat and test is supported by arquillian.