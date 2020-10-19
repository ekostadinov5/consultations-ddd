package mk.ukim.finki.emt.consultations.professormanagement.application.kafka.senders;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.ConsultationSlotDeletedOrCanceledMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConsultationSlotDeletedOrCanceledSender {

    private final KafkaTemplate<String, ConsultationSlotDeletedOrCanceledMessage> kafkaTemplate;

    public ConsultationSlotDeletedOrCanceledSender(KafkaTemplate<String, ConsultationSlotDeletedOrCanceledMessage>
                                                           kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, ConsultationSlotDeletedOrCanceledMessage message) {
        kafkaTemplate.send(topicName, message);
    }

}
