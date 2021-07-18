A demo Kafka application

# Getting Started
This is a demo application to show how we can use Kafka in a Spring Boot application. 

# How to Use
- Clone the repository.
- Build the code `gradlew clean build`
- cd ./build/libs
- java -jar kafkademo-0.0.1-SNAPSHOT.jar
- Now Use Postman or Insomnia to send a REST call to endpoint `http://localhost:9000/v1/kafka/send` 
- Sample JSON Payload 
`{
  "name": "Microsoft Inc",
  "type": "INC",
  "noOfemployees" : 43000,
  "ceo": "Satya Nadella",
  "city": "Seattle",
  "state": "Washington"
  }`
- Assuming you were running Kafka Server already and had created a topic called `companies`, 
  this should work as it is.  