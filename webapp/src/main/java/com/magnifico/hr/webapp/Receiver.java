package com.magnifico.hr.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private Random random = new Random();

    @RabbitListener(containerFactory="rabbitContainer", queues = "queue1")
    public void receiveMessage(String message) {
        logger.info("Received from queue 1: " + message);
        System.out.println("Received <" + message + ">");
    }

    @RabbitListener(containerFactory="rabbitContainer", queues = "query-example-2")
    public void worker1(String message) throws InterruptedException {
        logger.info("worker 1 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

    @RabbitListener(containerFactory="rabbitContainer", queues = "query-example-2")
    public void worker2(String message) throws InterruptedException {
        logger.info("worker 2 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }
}
