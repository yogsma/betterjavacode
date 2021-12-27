const { PubSub } = require('@google-cloud/pubsub');
require('dotenv').config();

const pubsubClient = new PubSub();

const data = JSON.stringify({
  "userId": "50001",
  "companyId": "acme",
  "companyName": "Acme Company",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@acme.com",
  "country": "US",
  "city": "Austin",
  "status": "Active",
  "effectiveDate": "11/11/2021",
  "department": "sales",
  "title": "Sales Lead"
});
const topicName = "PubSubExample";

async function createTopic() {
  // Creates a new topic
  await pubsubClient.createTopic(topicName);
  console.log(`Topic ${topicName} created.`);
}

async function doesTopicExist() {
  const topics = await pubsubClient.getTopics();
  const topicExists = topics.find((topic) => topic.name === topicName);
  return (topics && topicExists);
}

if(!doesTopicExist()) {
  createTopic();
}

async function publishMessage() {
    const dataBuffer = Buffer.from(data);

    try {
      const messageId = await pubsubClient.topic(topicName).publish(dataBuffer);
      console.log(`Message ${messageId} published`);
    } catch(error) {
      console.error(`Received error while publishing: ${error.message}`);
      process.exitCode = 1;
    }
}

publishMessage();
