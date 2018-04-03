package com.simone.aliyun;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.simone.aliyun.config.Config;
import com.simone.aliyun.config.PropertyManager;
import com.simone.aliyun.config.Secret;

import java.io.IOException;
import java.util.Properties;

/**
 * @author tallesi001 on 03/04/18.
 */
public class ONSSample {

    private static Config config;

    private static Secret secret;

    public static void main(String[] args) throws IOException {
        PropertyManager propertyManager = new PropertyManager();
        secret = propertyManager.getSecret();
        config = propertyManager.getConfig();

        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, config.getProducerId());
        producerProperties.setProperty(PropertyKeyConst.AccessKey, secret.getAccessKeyId());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, secret.getAccessKeySecret());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, config.getONSAddr());

        Producer producer = ONSFactory.createProducer(producerProperties);

        producer.start();

        Message msg = new Message( //
                // The Topic which has been created on the console, i.e., a Topic name of the message
                "TopicTestMQ",
                // Message Tag,
                // It can be understood as a Tag in Gmail used for reclassifying the message so as to facilitate the consumer to specify a filter condition to implement a filter in the MQ broker
                "TagA",
                // Message Body
                // It may be any data in binary form, and MQ will make no intervention
                // Compatible serialization and deserialization methods that need to be negotiated by the producer and the consumer
                "Hello MQ".getBytes());
        // Set a critical service property representing the message, and try to keep the critical service property globally unique, such that you can query and resend the message via the MQ console when you cannot receive the message normally
        // Note: No setting will affect the normal sending and receiving of messages
        msg.setKey("ORDERID_100");
        // Message sending will succeed as long as an exception is not thrown
        // Print the message ID to facilitate querying the message sending status
        SendResult sendResult = producer.send(msg);
        System.out.println("Send Message success. Message ID is: " + sendResult.getMessageId());

        producer.shutdown();
    }
}
