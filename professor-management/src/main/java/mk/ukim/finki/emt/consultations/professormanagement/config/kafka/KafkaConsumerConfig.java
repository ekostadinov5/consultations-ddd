package mk.ukim.finki.emt.consultations.professormanagement.config.kafka;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentAddedToConsultationSlotMessage;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentRemovedFromConsultationSlotMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, StudentAddedToConsultationSlotMessage>
    studentAddedToConsultationSlotConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(StudentAddedToConsultationSlotMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StudentAddedToConsultationSlotMessage>
    studentAddedToConsultationSlotKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentAddedToConsultationSlotMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(studentAddedToConsultationSlotConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, StudentRemovedFromConsultationSlotMessage>
    studentRemovedFromConsultationSlotConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(StudentRemovedFromConsultationSlotMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StudentRemovedFromConsultationSlotMessage>
    studentRemovedFromConsultationSlotKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentRemovedFromConsultationSlotMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(studentRemovedFromConsultationSlotConsumerFactory());
        return factory;
    }

}
