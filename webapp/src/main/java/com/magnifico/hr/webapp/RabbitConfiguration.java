package com.magnifico.hr.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {
    private final Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("exchange-example-5");
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("exchange-example-5");
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("queue1");
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("query-example-2");
    }

    @Bean
    Binding binding1() {
        return BindingBuilder.bind(myQueue1()).to(topicExchange()).with("email");
    }

    @Bean
    Binding binding2() {
        return BindingBuilder.bind(myQueue2()).to(topicExchange()).with("notification");
    }

    //объявляем контейнер, который будет содержать листенер для сообщений
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitContainer() {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory());
        container.setMaxConcurrentConsumers(5);
        return container;
    }

    // heroku rabbit
//    @Bean
//    public ConnectionFactory connectionFactory()
//    {
//        //получаем адрес AMQP у провайдера
//        String uri = System.getenv("CLOUDAMQP_URL");
//        if (uri == null) //значит мы запущены локально и нужно подключаться к локальному rabbitmq
//            uri = "amqp://guest:guest@localhost";
//        URI url = null;
//        try
//        {
//            url = new URI(uri);
//        } catch (URISyntaxException e)
//        {
//            e.printStackTrace(); //тут ошибка крайне маловероятна
//        }
//
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(url.getHost());
//        connectionFactory.setUsername(url.getUserInfo().split(":")[0]);
//        connectionFactory.setPassword(url.getUserInfo().split(":")[1]);
//        if (StringUtils.isNotBlank(url.getPath()))
//            connectionFactory.setVirtualHost(url.getPath().replace("/", ""));
//        connectionFactory.setConnectionTimeout(3000);
//        connectionFactory.setRequestedHeartBeat(30);
//        return connectionFactory;
//    }

}
