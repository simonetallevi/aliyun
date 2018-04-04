/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.simone.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.simone.aliyun.config.Config;
import com.simone.aliyun.config.PropertyManager;
import com.simone.aliyun.config.Secret;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class SSOSample {

    private static Config config;

    private static Secret secret;

    private static void uploadFileFromInputStream(OSS client, String fileName, String fileContent) {
        log.info("Trying to upload file with name: {}", fileName);
        PutObjectResult result = client.putObject(config.getBucketName(), fileName, new ByteArrayInputStream(fileContent.getBytes()));
        log.info("Upload Done {}", result);
    }

    private static void deleteFile(OSS client, String fileName) {
        log.info("Trying to delete file with name: {}", fileName);
        client.deleteObject(config.getBucketName(), fileName);
        log.info("Delete Done");
    }

    public static void main(String[] args) throws IOException {
        PropertyManager propertyManager = new PropertyManager();
        secret = propertyManager.getSecret();
        config = propertyManager.getConfig();

        OSS client = new OSSClientBuilder().build(config.getOssendpoint(), secret.getAccessKeyId(), secret.getAccessKeySecret());

        try {

            String fileName = "test.txt";
            uploadFileFromInputStream(client, fileName, "che bello");
            deleteFile(client, fileName);

        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: " + oe.getErrorCode());
            log.error("Error Code:       " + oe.getErrorCode());
            log.error("Request ID:      " + oe.getRequestId());
            log.error("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
}