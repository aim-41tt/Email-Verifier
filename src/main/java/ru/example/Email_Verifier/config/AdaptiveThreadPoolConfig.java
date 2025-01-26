package ru.example.Email_Verifier.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Включение асинхронности
public class AdaptiveThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(AdaptiveThreadPoolConfig.class);

    @Bean(name = "defaultTaskExecutor")
    @Primary // Этот бин будет использоваться по умолчанию
    protected Executor taskExecutor() {
        // Получение доступных ресурсов
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        long maxMemoryMB = Runtime.getRuntime().maxMemory() / (1024 * 1024);

        // Вычисление ёмкости очереди
        int calculatedQueueCapacity = (int) (maxMemoryMB / 1.7);
        int queueCapacity = Math.min(calculatedQueueCapacity, 4000); // Лимит на 4000 задач

        logger.info("Настройка пула потоков: доступно процессоров={}, макс. память={}MB, ёмкость очереди={}",
                availableProcessors, maxMemoryMB, queueCapacity);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(availableProcessors * 2); // Основное количество потоков
        executor.setMaxPoolSize(availableProcessors * 4);  // Максимальное количество потоков
        executor.setQueueCapacity(queueCapacity);          // Размер очереди задач
        executor.setThreadNamePrefix("AdaptivePool-");     // Префикс имени потоков

        // Обработка задач, если очередь переполнена
        executor.setRejectedExecutionHandler((r, exec) -> {
            logger.error("Задача отклонена: {}", r.toString());
        });

        executor.initialize();
        return executor;
    }
}
