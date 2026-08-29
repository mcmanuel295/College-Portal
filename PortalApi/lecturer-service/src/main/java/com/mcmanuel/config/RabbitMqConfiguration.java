package com.mcmanuel.config;

//import org.springframework.context.annotation.Bean;
//
//public class RabbitMqConfiguration {
//    public DirectExchange directExchange(String exchangeName){
//        return new DirectExchange(exchangeName);
//    }
//
//    @Bean
//    public Queue queue(String queueName){
//        return QueueBuilder.durable(queueName).build();
//    }
//
//    @Bean
//    public Binding queueBindinging(String queueName, String exchangeName, String key){
//        return BindingBuilder
//                .bind(queue(queueName)).to(directExchange(exchangeName)).with(key);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
//        RabbitTemplate template= new RabbitTemplate(factory);
//        template.setMessageConverter(converter());
//        return template;
//
//    }
//
//    @Bean
//    public JacksonJsonMessageConverter converter(){
//        return new JacksonJsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin(){
//        return new RabbitAdmin(new RabbitTemplate()) ;
//    }
//}
