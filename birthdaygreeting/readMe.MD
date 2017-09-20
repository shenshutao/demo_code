<h1>JAVA Spring Boot + Batch + Scheduler + mail<h1>


<h2>HOW TO RUN</h2>
step 1:
compile:   mvn clean package

step2:
unzip /target/birthdayGreeting.zip

step3:
modify property:
Scheduler setting:
   # according to https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
   -schedule.cron.expression=0 0 9 * * *
SMTP setting:
   -spring.mail.host=
   -spring.mail.port=
   -spring.mail.username=
   -spring.mail.password=
   -mail.from=
Record CSV file path
   -record.filename= 

step 4:
Run: sh startBirthdayGreetingService.sh


<h2>HOW TO TEST WITHOUT SEND EMAIL</h2>
1. use IDE to run the Junit
2. Run:  mvn test


