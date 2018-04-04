package com.simone.aliyun;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.simone.aliyun.config.Config;
import com.simone.aliyun.config.PropertyManager;
import com.simone.aliyun.config.Secret;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class MNSSample {

    private static Config config;

    private static Secret secret;

    public static void main(String[] args) throws IOException {
        PropertyManager propertyManager = new PropertyManager();
        secret = propertyManager.getSecret();
        config = propertyManager.getConfig();

        CloudAccount account = new CloudAccount(secret.getAccessKeyId(), secret.getAccessKeySecret(), config.getMnsendpoint());
        MNSClient mnsClient = account.getMNSClient();

        Message message = new Message();
        message.setMessageBody("TEST " + UUID.randomUUID().toString());

        try {

            CloudQueue queue = mnsClient.getQueueRef("simone-eu");
            Message putMsg = queue.putMessage(message);
            log.info("Message sent: " + message.getMessageBody());

            Message popMsg = queue.popMessage(30);
            log.info("Message received: " + popMsg.getMessageBody());

            queue.deleteMessage(popMsg.getReceiptHandle());
            log.info("Message deleted");

        } catch (ClientException ce) {
            log.error(ce.getMessage(), ce);
        } catch (ServiceException se) {
            log.error(se.getMessage(), se);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            mnsClient.close();
        }
    }
}
