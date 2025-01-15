package ru.example.Email_Verifier.config.kafka;

import org.apache.kafka.clients.ClientRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	private final ConsumerFactory<String, ClientRequest> consumerFactory;
	private final int concurrency;

	public KafkaConsumerConfig(ConsumerFactory<String, ClientRequest> consumerFactory,
			@Value("${KAFKA_LISTENER_CONCURRENCY}") int concurrency) {
		this.consumerFactory = consumerFactory;
		this.concurrency = concurrency;
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ClientRequest> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ClientRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(concurrency);
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

		return factory;
	}
}