package mk.ukim.finki.emt.consultations.studentmanagement.config.kafka;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentAddedToConsultationSlotMessage;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentRemovedFromConsultationSlotMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, StudentAddedToConsultationSlotMessage>
    studentAddedToConsultationSlotProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, StudentAddedToConsultationSlotMessage> studentAddedToConsultationSlotKafkaTemplate() {
        return new KafkaTemplate<>(studentAddedToConsultationSlotProducerFactory());
    }

    @Bean
    public ProducerFactory<String, StudentRemovedFromConsultationSlotMessage>
    studentRemovedFromConsultationSlotProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, StudentRemovedFromConsultationSlotMessage>
    studentRemovedFromConsultationSlotKafkaTemplate() {
        return new KafkaTemplate<>(studentRemovedFromConsultationSlotProducerFactory());
    }

}
