package entelect.training.incubator.spring.notification.sms.client.impl;

import com.google.gson.Gson;


import entelect.training.incubator.spring.notification.model.MessageDto;
import entelect.training.incubator.spring.notification.sms.client.SmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * A custom implementation of a fictional SMS service.
 */
@Service
public class MoloCellSmsClient implements SmsClient {
    @Autowired
    JmsTemplate jmsTemplate;
    @Override
    public void sendSms(String phoneNumber, String message) {
        System.out.println(String.format("Sending SMS, destination='{}', '{}'", phoneNumber, message));
        MessageDto messageDto = new MessageDto(phoneNumber,message);
        jmsTemplate.convertAndSend("flight","test");
       // sendMessage("flight",message);

    }
    public void sendMessage(final String queueName, final String message) {
        //Map map = new Gson().fromJson(message, Map.class);
//        final String textMessage = "Hello" + map.get("name");
//        System.out.println("Sending message " + textMessage + "to queue - " + queueName);
//        jmsTemplate.send(queueName, session -> {
//            TextMessage message1 = session. createTextMessage(message);
//            return message1;
//        });
    }
//    @JmsListener(destination = "flight")
//    public String receiveMessageFromTopic(final Message jsonMessage) throws JMSException {
//        String messageData = null;
//        System.out.println("Received message " + jsonMessage);
//        String response = null;
//        if(jsonMessage instanceof TextMessage) {
//            TextMessage textMessage = (TextMessage)jsonMessage;
//            messageData = textMessage.getText();
//           // Map map = new Gson().fromJson(messageData, Map.class);
//            response  = "Hello " + messageData;//map.get("name");
//        }
//        return response;
//    }
}
