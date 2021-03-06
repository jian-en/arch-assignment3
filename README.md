# How to run the project

### Database Setup
1. Go to root path of the project
2. run `mysql -u root -p`

***Note*** We connect to mysql service with username `root` and password `tmp`. You **must** change them if necessary. Please notify us if you set up a different MySQL username/password pair. -> jianfu@andrew.cmu.edu

3. run `source dbtemplate.sql;` to create databases `ws_orderinfo` and `ms_orderinfo`



### Starting Micro Services
1. compile run: `javac *.java`
2. start services: 
    - `rmiregistry &`
    - `java -cp .:mysql-connector-java-5.1.45-bin.jar DeleteServices`
    - `java -cp .:mysql-connector-java-5.1.45-bin.jar CreateServices`
    - `java -cp .:mysql-connector-java-5.1.45-bin.jar RetrieveServices`
    - `java -cp .:mysql-connector-java-5.1.45-bin.jar AuthenticateServices`
3. run UI: `java OrdersUI`

***Note*** The authentication to access our service: username `test` and password `test`.

### Starting Web Services
1. compile run: `javac *.java`
2. install node packages: `npm install --quiet`
3. start server: `node Server.js`
4. run UI: `java OrdersUI`

***Note*** The authentication to access our service: username `test` and password `test`.

### Three features we added
1. Delete an order info by the id
2. Authenticate the user before the other services will be used
3. Log the services used into log files
