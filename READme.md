# SalEats

PA2 for CSCI201
## Setup

Eclipse webproject should be titled PA2 in order for tomcat link and paths to work properly. (Had to write some absolute paths rather than relative to mitigate errors)

Run tables.sql in MYSQL to create PA2 database and neccesary tables.

My system required my password to be Jtorres00! and not root, and I believe that I changed everything to Constant.DBPassword, but had some issues with that while testing. Changing Constant.DBPassword to "root" should prevent errors if database password is root.

Jars are located in PA2/src/main/webapp/WEB-INF/lib/ and include gson, jstl and sqlconnector.
Restaurant json should be placed in PA2/src/main/java/Util/ in order to be accessed.

Google sign-in only works with USC accounts because of restrictions on Google Cloud education accounts.