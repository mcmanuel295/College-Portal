//package com.mcmanuel.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.core.*;
//
//@Configuration
//@RequiredArgsConstructor
//public class RabbitMqConfiguration {
//    private final ApplicationConfiguration appConfig;
//    public static final String EXCHANGE_NAME = "notification.topic";
//    public static final String QUEUE_NAME = "student-notifications-queue";
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
//        RabbitTemplate template= new RabbitTemplate(factory);
//        template.setMessageConverter(converter());
//        return template;
//    }
//
//    @Bean
//    public JacksonJsonMessageConverter converter(){
//        return new JacksonJsonMessageConverter();
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory factory){
//        return new RabbitAdmin(rabbitTemplate(factory)) ;
//    }
//
//
//    @Bean
//    public TopicExchange notificationExchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Queue studentNotificationsQueue() {
//        return new Queue(QUEUE_NAME, true);
//    }
//
//    @Bean
//    public Binding bindAllMessages(Queue studentNotificationsQueue, TopicExchange notificationExchange) {
//        return BindingBuilder.bind(studentNotificationsQueue)
//                .to(notificationExchange)
//                .with("#");
//    }
//}
//
