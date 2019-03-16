FROM java:8
COPY . /var/www/java
WORKDIR /var/www/java
ENV CLASSPATH=.:mysql-connector-java-5.1.45-bin.jar
RUN javac *.java
