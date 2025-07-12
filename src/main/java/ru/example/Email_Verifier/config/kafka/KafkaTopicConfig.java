package ru.example.Email_Verifier.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	private final int concurrency;
	
	public KafkaTopicConfig(@Value("${KAFKA_LISTENER_CONCURRENCY}") int concurrency) {
		this.concurrency = concurrency;
	}

	@Bean
	protected NewTopic emailVerifierTopic(@Value("${kafka.topic.name}")String topicName) {
		return TopicBuilder.name(topicName)
				.partitions(concurrency)
				.replicas(1)
				.build();
	}

}