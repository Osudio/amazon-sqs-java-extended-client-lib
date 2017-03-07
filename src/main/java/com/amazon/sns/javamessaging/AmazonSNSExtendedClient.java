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
import com.amazon.javamessaging.MessageS3Pointer;
import com.amazon.javamessaging.S3Helper;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

/**
 * Amazon SNS Extended Client extends the functionality of Amazon SNS client.
 * All service calls made using this client are blocking, and will not return
 * until the service call completes.
 * <p>
 * <p>
 * The Amazon SNS extended client enables sending and receiving large messages
 * via Amazon S3. You can use this library to:
 * </p>
 * <p>
 * <ul>
 * <li>Specify whether messages are always stored in Amazon S3 or only when a
 * message size exceeds 256 KB.</li>
 * <li>Send a message that references a single message object stored in an
 * Amazon S3 bucket.</li>
 * <li>Get the corresponding message object from an Amazon S3 bucket.</li>
 * <li>Delete the corresponding message object from an Amazon S3 bucket.</li>
 * </ul>
 */
public class AmazonSNSExtendedClient extends AmazonSNSExtendedClientBase {
    private static final Log LOG = LogFactory.getLog(AmazonSNSExtendedClient.class);

    private final ExtendedClientConfiguration clientConfiguration;
    private final S3Helper s3Helper;

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object.
     * <p>
     * <p>
     * All service calls made using this new client object are blocking, and
     * will not return until the service call completes.
     *
     * @param snsClient The Amazon SNS client to use to connect to Amazon SNS.
     */
    public AmazonSNSExtendedClient(AmazonSNS snsClient) {
        this(snsClient, new ExtendedClientConfiguration());
    }

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object.
     * <p>
     * <p>
     * All service calls made using this new client object are blocking, and
     * will not return until the service call completes.
     *
     * @param snsClient            The Amazon SNS client to use to connect to Amazon SNS.
     * @param extendedClientConfig The extended client configuration options controlling the
     *                             functionality of this client.
     */
    public AmazonSNSExtendedClient(AmazonSNS snsClient, ExtendedClientConfiguration extendedClientConfig) {
        super(snsClient);
        this.clientConfiguration = new ExtendedClientConfiguration(extendedClientConfig);
        this.s3Helper = new S3Helper(this.clientConfiguration);

    }

    /**
     * <p>
     * Delivers a message to the specified topic and uploads the message payload
     * to Amazon S3 if necessary.
     * </p>
     * <p>
     * <b>IMPORTANT:</b> The following list shows the characters (in Unicode)
     * allowed in your message, according to the W3C XML specification. For more
     * information, go to http://www.w3.org/TR/REC-xml/#charsets If you send any
     * characters not included in the list, your request will be rejected. #x9 |
     * #xA | #xD | [#x20 to #xD7FF] | [#xE000 to #xFFFD] | [#x10000 to #x10FFFF]
     * </p>
     * <p>
     * <b>IMPORTANT:</b> The input object may be modified by the method. </p>
     *
     * @param publishRequest Container for the necessary parameters to execute the
     *                       publish service method on AmazonSNS.
     * @return The response from the SendMessage service method, as returned by
     * AmazonSNS.
     * @throws com.amazonaws.services.sqs.model.InvalidMessageContentsException
     * @throws UnsupportedOperationException
     * @throws AmazonClientException                                            If any internal errors are encountered inside the client
     *                                                                          while attempting to make the request or handle the response.
     *                                                                          For example if a network connection is not available.
     * @throws AmazonServiceException                                           If an error response is returned by AmazonSNS indicating
     *                                                                          either a problem with the data in the request, or a server
     *                                                                          side issue.
     */
    @Override
    public PublishResult publish(PublishRequest publishRequest) {
        if (publishRequest == null) {
            String errorMessage = "publishRequest cannot be null.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        publishRequest.getRequestClientOptions().appendUserAgent(SNSExtendedClientConstants.USER_AGENT_HEADER);

        if (!clientConfiguration.isLargePayloadSupportEnabled()) {
            return super.publish(publishRequest);
        }

        if (publishRequest.getMessage() == null || "".equals(publishRequest.getMessage())) {
            String errorMessage = "messageBody cannot be null or empty.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        if (clientConfiguration.isAlwaysThroughS3() || isLarge(publishRequest)) {
            publishRequest = storeMessageInS3(publishRequest);
        }
        return super.publish(publishRequest);
    }


    @Override
    public PublishResult publish(String topicArn, String message) {
        final PublishRequest publishRequest = new PublishRequest(topicArn, message);
        return publish(publishRequest);
    }

    @Override
    public PublishResult publish(String topicArn, String message, String subject) {
        final PublishRequest publishRequest = new PublishRequest(topicArn, message);
        return super.publish(publishRequest);
    }

    /**
     * <p>
     * Retrieves one or more messages, with a maximum limit of 10 messages, from
     * the specified queue. Downloads the message payloads from Amazon S3 when
     * necessary. Long poll support is enabled by using the
     * <code>WaitTimeSeconds</code> parameter. For more information, see <a
     * href=
     * "http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SNSDeveloperGuide/sns-long-polling.html"
     * > Amazon SNS Long Poll </a> in the <i>Amazon SNS Developer Guide</i> .
     * </p>
     * <p>
     * Short poll is the default behavior where a weighted random set of
     * machines is sampled on a <code>ReceiveMessage</code> call. This means
     * only the messages on the sampled machines are returned. If the number of
     * messages in the queue is small (less than 1000), it is likely you will
     * get fewer messages than you requested per <code>ReceiveMessage</code>
     * call. If the number of messages in the queue is extremely small, you
     * might not receive any messages in a particular
     * <code>ReceiveMessage</code> response; in which case you should repeat the
     * request.
     * </p>
     * <p>
     * For each message returned, the response includes the following:
     * </p>
     * <p>
     * <ul>
     * <li>
     * <p>
     * Message body
     * </p>
     * </li>
     * <li>
     * <p>
     * MD5 digest of the message body. For information about MD5, go to <a
     * href="http://www.faqs.org/rfcs/rfc1321.html">
     * http://www.faqs.org/rfcs/rfc1321.html </a> .
     * </p>
     * </li>
     * <li>
     * <p>
     * Message ID you received when you sent the message to the queue.
     * </p>
     * </li>
     * <li>
     * <p>
     * Receipt handle.
     * </p>
     * </li>
     * <li>
     * <p>
     * Message attributes.
     * </p>
     * </li>
     * <li>
     * <p>
     * MD5 digest of the message attributes.
     * </p>
     * </li>
     * <p>
     * </ul>
     * <p>
     * The receipt handle is the identifier you must provide when deleting the
     * message. For more information, see <a href=
     * "http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SNSDeveloperGuide/ImportantIdentifiers.html"
     * > Queue and Message Identifiers </a> in the <i>Amazon SNS Developer
     * Guide</i> .
     * </p>
     * <p>
     * You can provide the <code>VisibilityTimeout</code> parameter in your
     * request, which will be applied to the messages that Amazon SNS returns in
     * the response. If you do not include the parameter, the overall visibility
     * timeout for the queue is used for the returned messages. For more
     * information, see <a href=
     * "http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SNSDeveloperGuide/AboutVT.html"
     * > Visibility Timeout </a> in the <i>Amazon SNS Developer Guide</i> .
     * </p>
     * <p>
     * <b>NOTE:</b> Going forward, new attributes might be added. If you are
     * writing code that calls this action, we recommend that you structure your
     * code so that it can handle new attributes gracefully.
     * </p>
     *
     * @param receiveMessageRequest Container for the necessary parameters to execute the
     *                              ReceiveMessage service method on AmazonSNS.
     * @return The response from the ReceiveMessage service method, as returned
     * by AmazonSNS.
     * @throws OverLimitException
     * @throws AmazonClientException  If any internal errors are encountered inside the client
     *                                while attempting to make the request or handle the response.
     *                                For example if a network connection is not available.
     * @throws AmazonServiceException If an error response is returned by AmazonSNS indicating
     *                                either a problem with the data in the request, or a server
     *                                side issue.
     */
//    public ReceiveMessageResult receiveMessage(ReceiveMessageRequest receiveMessageRequest) {
//
//        if (receiveMessageRequest == null) {
//            String errorMessage = "receiveMessageRequest cannot be null.";
//            LOG.error(errorMessage);
//            throw new AmazonClientException(errorMessage);
//        }
//
//        receiveMessageRequest.getRequestClientOptions().appendUserAgent(SNSExtendedClientConstants.USER_AGENT_HEADER);
//
//        if (!clientConfiguration.isLargePayloadSupportEnabled()) {
//            return super.receiveMessage(receiveMessageRequest);
//        }
//
//        receiveMessageRequest.getMessageAttributeNames().add(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
//
//        ReceiveMessageResult receiveMessageResult = super.receiveMessage(receiveMessageRequest);
//
//        List<Message> messages = receiveMessageResult.getMessages();
//        for (Message message : messages) {
//
//            // for each received message check if they are stored in S3.
//            MessageAttributeValue largePayloadAttributeValue = message.getMessageAttributes().get(
//                    SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
//            if (largePayloadAttributeValue != null) {
//                String messageBody = message.getBody();
//
//                // read the S3 pointer from the message body JSON string.
//                MessageS3Pointer s3Pointer = readMessageS3PointerFromJSON(messageBody);
//
//                String s3MsgBucketName = s3Pointer.getS3BucketName();
//                String s3MsgKey = s3Pointer.getS3Key();
//
//                String origMsgBody = getTextFromS3(s3MsgBucketName, s3MsgKey);
//                LOG.info("S3 object read, Bucket name: " + s3MsgBucketName + ", Object key: " + s3MsgKey + ".");
//
//                message.setBody(origMsgBody);
//
//                // remove the additional attribute before returning the message
//                // to user.
//                message.getMessageAttributes().remove(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
//
//                // Embed s3 object pointer in the receipt handle.
//                String modifiedReceiptHandle = embedS3PointerInReceiptHandle(message.getReceiptHandle(),
//                        s3MsgBucketName, s3MsgKey);
//
//                message.setReceiptHandle(modifiedReceiptHandle);
//            }
//        }
//        return receiveMessageResult;
//    }


    /**
     * <p>
     * Deletes the specified message from the specified queue and deletes the
     * message payload from Amazon S3 when necessary. You specify the message by
     * using the message's <code>receipt handle</code> and not the
     * <code>message ID</code> you received when you sent the message. Even if
     * the message is locked by another reader due to the visibility timeout
     * setting, it is still deleted from the queue. If you leave a message in
     * the queue for longer than the queue's configured retention period, Amazon
     * SNS automatically deletes it.
     * </p>
     * <p>
     * <b>NOTE:</b> The receipt handle is associated with a specific instance of
     * receiving the message. If you receive a message more than once, the
     * receipt handle you get each time you receive the message is different.
     * When you request DeleteMessage, if you don't provide the most recently
     * received receipt handle for the message, the request will still succeed,
     * but the message might not be deleted.
     * </p>
     * <p>
     * <b>IMPORTANT:</b> It is possible you will receive a message even after
     * you have deleted it. This might happen on rare occasions if one of the
     * servers storing a copy of the message is unavailable when you request to
     * delete the message. The copy remains on the server and might be returned
     * to you again on a subsequent receive request. You should create your
     * system to be idempotent so that receiving a particular message more than
     * once is not a problem.
     * </p>
     *
     * @param deleteTopicRequest Container for the necessary parameters to execute the
     *                           DeleteMessage service method on AmazonSNS.
     * @throws com.amazonaws.services.sqs.model.ReceiptHandleIsInvalidException
     * @throws com.amazonaws.services.sqs.model.InvalidIdFormatException
     * @throws AmazonClientException                                            If any internal errors are encountered inside the client
     *                                                                          while attempting to make the request or handle the response.
     *                                                                          For example if a network connection is not available.
     * @throws AmazonServiceException                                           If an error response is returned by AmazonSNS indicating
     *                                                                          either a problem with the data in the request, or a server
     *                                                                          side issue.
     */
    public DeleteTopicResult deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        LOG.warn("Calling deleteTopic deletes SNS messages without deleting their payload from S3.");

        if (deleteTopicRequest == null) {
            String errorMessage = "deleteTopicRequest cannot be null.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        deleteTopicRequest.getRequestClientOptions().appendUserAgent(SNSExtendedClientConstants.USER_AGENT_HEADER);

        return super.deleteTopic(deleteTopicRequest);
    }

    public DeleteTopicResult deleteTopic(String topicArn) {
        DeleteTopicRequest deleteMessageRequest = new DeleteTopicRequest(topicArn);
        return deleteTopic(deleteMessageRequest);
    }

    private void checkMessageAttributes(Map<String, MessageAttributeValue> messageAttributes) {
        int msgAttributesSize = getMsgAttributesSize(messageAttributes);
        if (msgAttributesSize > clientConfiguration.getMessageSizeThreshold()) {
            String errorMessage = "Total size of Message attributes is " + msgAttributesSize
                    + " bytes which is larger than the threshold of " + clientConfiguration.getMessageSizeThreshold()
                    + " Bytes. Consider including the payload in the message body instead of message attributes.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        int messageAttributesNum = messageAttributes.size();
        if (messageAttributesNum > SNSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES) {
            String errorMessage = "Number of message attributes [" + messageAttributesNum
                    + "] exceeds the maximum allowed for large-payload messages ["
                    + SNSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + "].";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        MessageAttributeValue largePayloadAttributeValue = messageAttributes
                .get(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
        if (largePayloadAttributeValue != null) {
            String errorMessage = "Message attribute name " + SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME
                    + " is reserved for use by SNS extended client.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

    }

    private boolean isLarge(PublishRequest publishRequest) {
        int msgAttributesSize = getMsgAttributesSize(publishRequest.getMessageAttributes());
        long msgBodySize = s3Helper.getStringSizeInBytes(publishRequest.getMessage());
        long totalMsgSize = msgAttributesSize + msgBodySize;
        return (totalMsgSize > clientConfiguration.getMessageSizeThreshold());
    }

    private PublishRequest storeMessageInS3(PublishRequest publishRequest) {

        checkMessageAttributes(publishRequest.getMessageAttributes());

        String s3Key = UUID.randomUUID().toString();

        // Read the content of the message from message body
        String messageContentStr = publishRequest.getMessage();

        Long messageContentSize = s3Helper.getStringSizeInBytes(messageContentStr);

        // Add a new message attribute as a flag
        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.setDataType("Number");
        messageAttributeValue.setStringValue(messageContentSize.toString());
        publishRequest.addMessageAttributesEntry(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME,
                messageAttributeValue);

        // Store the message content in S3.
        s3Helper.storeTextInS3(s3Key, messageContentStr, messageContentSize);
        LOG.info("S3 object created, Bucket name: " + clientConfiguration.getS3BucketName() + ", Object key: " + s3Key
                + ".");

        // Convert S3 pointer (bucket name, key, etc) to JSON string
        MessageS3Pointer s3Pointer = new MessageS3Pointer(clientConfiguration.getS3BucketName(), s3Key);

        String s3PointerStr = s3Helper.getJSONFromS3Pointer(s3Pointer);

        // Storing S3 pointer in the message body.
        publishRequest.setMessage(s3PointerStr);

        return publishRequest;
    }

    private int getMsgAttributesSize(Map<String, MessageAttributeValue> msgAttributes) {
        int totalMsgAttributesSize = 0;
        for (Map.Entry<String, MessageAttributeValue> entry : msgAttributes.entrySet()) {
            totalMsgAttributesSize += s3Helper.getStringSizeInBytes(entry.getKey());

            MessageAttributeValue entryVal = entry.getValue();
            if (entryVal.getDataType() != null) {
                totalMsgAttributesSize += s3Helper.getStringSizeInBytes(entryVal.getDataType());
            }

            String stringVal = entryVal.getStringValue();
            if (stringVal != null) {
                totalMsgAttributesSize += s3Helper.getStringSizeInBytes(entryVal.getStringValue());
            }

            ByteBuffer binaryVal = entryVal.getBinaryValue();
            if (binaryVal != null) {
                totalMsgAttributesSize += binaryVal.array().length;
            }
        }
        return totalMsgAttributesSize;
    }


}
