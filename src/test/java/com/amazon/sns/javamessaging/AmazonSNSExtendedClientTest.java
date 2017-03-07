/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.sns.javamessaging;

import com.amazon.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests the AmazonSNSExtendedClient class.
 */
public class AmazonSNSExtendedClientTest {

    private AmazonSNS sns;
    private AmazonS3 s3;
    private static final String S3_BUCKET_NAME = "test-bucket-name";
    private static final String SNS_TOPIC_ARN = "test-topic-arn";
    private static final int SQS_SIZE_LIMIT = 262144;

    @Before
    public void setupClient() {
        s3 = mock(AmazonS3.class);
        when(s3.putObject(isA(PutObjectRequest.class))).thenReturn(null);

        ExtendedClientConfiguration extendedClientConfiguration = new ExtendedClientConfiguration()
                .withLargePayloadSupportEnabled(s3, S3_BUCKET_NAME);

        sns = spy(new AmazonSNSExtendedClient(mock(AmazonSNSClient.class), extendedClientConfiguration));

    }

	@Test
	public void testSendLargeMessage() {

		int messageLength = SQS_SIZE_LIMIT + 1;
		String messageBody = generateString(messageLength);

		PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);

		sns.publish(publishRequest);
		verify(s3, times(1)).putObject(isA(PutObjectRequest.class));
	}

	@Test
	public void testSendSmallMessage() {
		int messageLength = SQS_SIZE_LIMIT;
		String messageBody = generateString(messageLength);

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
		sns.publish(publishRequest);
		verify(s3, never()).putObject(isA(PutObjectRequest.class));
	}

    @Test
    public void testSendMessageWithLargePayloadSupportDisabled() {
        int messageLength = 300000;
        String messageBody = generateString(messageLength);

        ExtendedClientConfiguration extendedClientConfiguration = new ExtendedClientConfiguration()
                .withLargePayloadSupportDisabled();

        AmazonSNS snsExtended = spy(new AmazonSNSExtendedClient(mock(AmazonSNSClient.class), extendedClientConfiguration));

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        snsExtended.publish(publishRequest);
        verify(s3, never()).putObject(isA(PutObjectRequest.class));
    }

    @Test
    public void testSendMessageWithAlwaysThroughS3() {
        int messageLength = 3;
        String messageBody = generateString(messageLength);

        ExtendedClientConfiguration extendedClientConfiguration = new ExtendedClientConfiguration()
                .withLargePayloadSupportEnabled(s3, S3_BUCKET_NAME).withAlwaysThroughS3(true);

        AmazonSNS snsExtended = spy(new AmazonSNSExtendedClient(mock(AmazonSNSClient.class), extendedClientConfiguration));

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        snsExtended.publish(publishRequest);
        verify(s3, times(1)).putObject(isA(PutObjectRequest.class));
    }

    @Test
    public void testSendMessageWithSetMessageSizeThreshold() {
        int messageLength = 1000;
        String messageBody = generateString(messageLength);

        ExtendedClientConfiguration extendedClientConfiguration = new ExtendedClientConfiguration()
                .withLargePayloadSupportEnabled(s3, S3_BUCKET_NAME).withMessageSizeThreshold(500);

        AmazonSNS snsExtended = spy(new AmazonSNSExtendedClient(mock(AmazonSNSClient.class), extendedClientConfiguration));

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        snsExtended.publish(publishRequest);
        verify(s3, times(1)).putObject(isA(PutObjectRequest.class));
    }

	private String generateString(int messageLength) {
		char[] charArray = new char[messageLength];
		Arrays.fill(charArray, 'x');
		return new String(charArray);
	}

}
