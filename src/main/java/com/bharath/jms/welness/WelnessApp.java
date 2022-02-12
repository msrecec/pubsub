package com.bharath.jms.welness;

import com.bharath.jms.hr.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class WelnessApp {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        Topic topic = (Topic)context.lookup("topic/empTopic");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(); JMSContext jmsContext = cf.createContext()) {
            JMSConsumer consumer = jmsContext.createSharedConsumer(topic, "sharedConsumer");
            JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");

            for(int i = 1; i <= 10; i+= 2) {
                Message message = consumer.receive();
                Employee employee = message.getBody(Employee.class);
                System.out.println("Consumer 1 " + employee.getFirstName());

                Message message2 = consumer2.receive();
                Employee employee2 = message2.getBody(Employee.class);
                System.out.println("Consumer 2 " + employee2.getFirstName());
            }
        }
    }
}
