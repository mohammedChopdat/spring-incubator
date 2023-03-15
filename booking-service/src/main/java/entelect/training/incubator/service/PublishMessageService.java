package entelect.training.incubator.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Map;

@Component
public class PublishMessageService {
    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(final String queueName, final String message) {
    Map map = new Gson().fromJson(message, Map.class);
    final String textMessage = "Hello" + map.get("message");
    System.out.println("Sending message " + textMessage + "to queue - " + queueName);
    jmsTemplate.send(queueName, new MessageCreator() {

        public Message createMessage(Session session) throws JMSException {
            TextMessage message = session.createTextMessage(textMessage);
            return message;
        }
    });
}
}
