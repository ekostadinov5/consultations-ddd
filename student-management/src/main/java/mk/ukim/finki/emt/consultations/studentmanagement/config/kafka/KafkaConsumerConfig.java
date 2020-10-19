package mk.ukim.finki.emt.consultations.studentmanagement.config.kafka;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.ConsultationSlotDeletedOrCanceledMessage;
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
    public ConsumerFactory<String, ConsultationSlotDeletedOrCanceledMessage>
    consultationSlotDeletedOrCanceledConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(ConsultationSlotDeletedOrCanceledMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConsultationSlotDeletedOrCanceledMessage>
    consultationSlotDeletedOrCanceledKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ConsultationSlotDeletedOrCanceledMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consultationSlotDeletedOrCanceledConsumerFactory());
        return factory;
    }

}
