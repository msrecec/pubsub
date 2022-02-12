package com.bharath.jms.hr;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class HRApp {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        Topic topic = (Topic)context.lookup("topic/empTopic");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(); JMSContext jmsContext = cf.createContext()) {
            Employee employee = new Employee();
            employee.setId(123);
            employee.setFirstName("Bharath");
            employee.setLastName("Thippireddy");
            employee.setDesignation("Software Architect");
            employee.setEmail("12345");

            for(int i = 1; i <= 10; i++) {
                jmsContext.createProducer().send(topic, employee);
            }
            System.out.println("Message Sent");
        }
    }
}
