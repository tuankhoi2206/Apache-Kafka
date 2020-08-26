package org.khoivu.kafkachat.configuration;

import java.util.Collections;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicCreator {

  private Properties properties;
  private IActionConsumer actionConsumer;

  public TopicCreator(Properties properties) {
    this.properties = properties;
  }

  public void createTopic(String topicName, int numPartitions) throws Exception {
    Properties config = new Properties();
    AdminClient admin = AdminClient.create(config);

    boolean alreadyExists = admin.listTopics().names().get().stream()
        .anyMatch(existingTopicName -> existingTopicName.equals(topicName));
    if (!alreadyExists) {
      NewTopic newTopic = new NewTopic(topicName, numPartitions, (short) 1);
      try {
        admin.createTopics(Collections.singleton(newTopic)).all().get();
        log.info("Created topic " + topicName);
      } catch (Exception e) {
        log.error("Failed to create topic" + topicName + e.getMessage());
      }
    }

    admin.describeTopics(Collections.singleton(topicName)).all().get().forEach((topic, desc) -> {
      log.info("Topic: " + topic);
      log.info("Partitions: %s, partition ids: %s%n", //
          desc.partitions().size(), //
          desc.partitions().stream().map(p -> Integer.toString(p.partition()))
              .collect(Collectors.joining(",")));
    });
    admin.close();
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
