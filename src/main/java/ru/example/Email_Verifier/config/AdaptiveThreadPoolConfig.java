package ru.example.Email_Verifier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Включение асинхронности
public class AdaptiveThreadPoolConfig {

	@Bean(name = "defaultTaskExecutor")
	@Primary // этот бин будет использоваться по умолчанию
	protected Executor taskExecutor() {
		// доступные процессоры
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		// доступная память (ОЗУ)
		Long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
		int calculatedQueueCapacity = (int) (maxMemory / 1.7);

		int queueCapacity = Math.min(calculatedQueueCapacity, 4000); // Ограничение сверху, до 4000 задач

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(availableProcessors * 2);
		executor.setMaxPoolSize(availableProcessors * 4);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("Thread-");

		// Обработка задач, если очередь переполнена
		executor.setRejectedExecutionHandler((r, exec) -> {
			System.err.println("Задача отклонена: " + r.toString());
		});

		executor.initialize();
		return executor;
	}
}
