# multidb
This project shows how to connect multiple DB's in a single spring boot application. It works on basis of Multi-tenant architecture.
Where we can just switch the DB's at runtime based on the predefined names given to the DB connections. 
Here I have taken three DB connections 2 connections of MYSQL and 1 of POSTGRESQL that are defined in dbDetails.json file.
When project is executed, we read JSON file and create connections with all the database info provided in dbDetails.json.
