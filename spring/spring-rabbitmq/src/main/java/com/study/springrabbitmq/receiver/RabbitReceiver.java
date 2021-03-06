package com.study.springrabbitmq.receiver;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RabbitReceiver {

    /**
     * 接受queue为first的消息
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "first"))
    @RabbitHandler
    public void process1(String message) {

        try {
            //处理完任务后应答给rabbitmq 删除queue
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("RabbitReceiver1: " + message);

    }


    /**
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("second"),
            exchange = @Exchange(value = "myExchange"),
            key = "second-1"))
    @RabbitHandler
    public void process2_1(String message) {

        System.out.println("RabbitReceiver2-1: " + message);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("second"),
            exchange = @Exchange(value = "myExchange", ignoreDeclarationExceptions = "true"),
            key = "second-2"))
    @RabbitHandler
    public void process2_2(String message) {

        System.out.println("RabbitReceiver2-2: " + message);

    }

    /**
     * 所有侦听器的同一交换机类型要一致
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "third-1"),
            exchange = @Exchange(value = "myFanout", type = ExchangeTypes.FANOUT)
    ))
    @RabbitHandler
    public void process3_1(String message) {

        System.out.println("RabbitReceiver3_1: " + message);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "third-2"),
            exchange = @Exchange(value = "myFanout", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    @RabbitHandler
    public void process3_2(String message) {

        System.out.println("RabbitReceiver3_2: " + message);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "four"),
            exchange = @Exchange(value = "myKey", ignoreDeclarationExceptions = "true"),
            key = {"red", "yellow"}
    ))
    @RabbitHandler
    public void process4_1(String message) {

        System.out.println("RabbitReceiver4_1: " + message);

    }


}
