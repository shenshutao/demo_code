# JAVA Spring Boot + Batch + Scheduler + mail


#### HOW TO RUN
- step 1:    
compile:   mvn clean package

- step 2:    
unzip /target/birthdayGreeting.zip

- step 3:    
modify property:    
   * Scheduler setting:    
   according to https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html      
   -schedule.cron.expression=0 0 9 * * *    
   * SMTP setting:    
   -spring.mail.host=    
   -spring.mail.port=    
   -spring.mail.username=     
   -spring.mail.password=     
   -mail.from=     
   * Record CSV file path    
   -record.filename=     

- step 4:    
Run: sh startBirthdayGreetingService.sh


#### HOW TO TEST WITHOUT SEND EMAIL
- use IDE to run the Junit
- Run:  mvn test


