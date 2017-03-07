package com.amazon.javamessaging;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by PetervanGestel on 7-3-2017.
 */
public class S3Helper {

    private static final Log LOG = LogFactory.getLog(S3Helper.class);
    private ExtendedClientConfiguration clientConfiguration;

    public S3Helper(ExtendedClientConfiguration config) {
        this.clientConfiguration = config;
    }

    public void deleteMessagePayloadFromS3(String receiptHandle) {
        String s3MsgBucketName = getFromReceiptHandleByMarker(receiptHandle,
                ExtendedClientConstants.S3_BUCKET_NAME_MARKER);
        String s3MsgKey = getFromReceiptHandleByMarker(receiptHandle, ExtendedClientConstants.S3_KEY_MARKER);
        try {
            clientConfiguration.getAmazonS3Client().deleteObject(s3MsgBucketName, s3MsgKey);
        } catch (AmazonServiceException e) {
            String errorMessage = "Failed to delete the S3 object which contains the SQS message payload. SQS message was not deleted.";
            LOG.error(errorMessage, e);
            throw new AmazonServiceException(errorMessage, e);
        } catch (AmazonClientException e) {
            String errorMessage = "Failed to delete the S3 object which contains the SQS message payload. SQS message was not deleted.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        LOG.info("S3 object deleted, Bucket name: " + s3MsgBucketName + ", Object key: " + s3MsgKey + ".");
    }

    public String embedS3PointerInReceiptHandle(String receiptHandle, String s3MsgBucketName, String s3MsgKey) {
        String modifiedReceiptHandle = ExtendedClientConstants.S3_BUCKET_NAME_MARKER + s3MsgBucketName
                + ExtendedClientConstants.S3_BUCKET_NAME_MARKER + ExtendedClientConstants.S3_KEY_MARKER
                + s3MsgKey + ExtendedClientConstants.S3_KEY_MARKER + receiptHandle;
        return modifiedReceiptHandle;
    }

    public MessageS3Pointer readMessageS3PointerFromJSON(String messageBody) {
        MessageS3Pointer s3Pointer = null;
        try {
            JsonDataConverter jsonDataConverter = new JsonDataConverter();
            s3Pointer = jsonDataConverter.deserializeFromJson(messageBody, MessageS3Pointer.class);
        } catch (Exception e) {
            String errorMessage = "Failed to read the S3 object pointer from an SNS message. Message was not received.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        return s3Pointer;
    }

    public String getOrigReceiptHandle(String receiptHandle) {
        int secondOccurence = receiptHandle.indexOf(ExtendedClientConstants.S3_KEY_MARKER,
                receiptHandle.indexOf(ExtendedClientConstants.S3_KEY_MARKER) + 1);
        return receiptHandle.substring(secondOccurence + ExtendedClientConstants.S3_KEY_MARKER.length());
    }

    public String getFromReceiptHandleByMarker(String receiptHandle, String marker) {
        int firstOccurence = receiptHandle.indexOf(marker);
        int secondOccurence = receiptHandle.indexOf(marker, firstOccurence + 1);
        return receiptHandle.substring(firstOccurence + marker.length(), secondOccurence);
    }

    public boolean isS3ReceiptHandle(String receiptHandle) {
        return receiptHandle.contains(ExtendedClientConstants.S3_BUCKET_NAME_MARKER)
                && receiptHandle.contains(ExtendedClientConstants.S3_KEY_MARKER);
    }

    public String getTextFromS3(String s3BucketName, String s3Key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(s3BucketName, s3Key);
        String embeddedText = null;
        S3Object obj = null;
        try {
            obj = clientConfiguration.getAmazonS3Client().getObject(getObjectRequest);
        } catch (AmazonServiceException e) {
            String errorMessage = "Failed to get the S3 object which contains the message payload. Message was not received.";
            LOG.error(errorMessage, e);
            throw new AmazonServiceException(errorMessage, e);
        } catch (AmazonClientException e) {
            String errorMessage = "Failed to get the S3 object which contains the message payload. Message was not received.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        try {
            InputStream objContent = obj.getObjectContent();
            java.util.Scanner objContentScanner = new java.util.Scanner(objContent, "UTF-8");
            objContentScanner.useDelimiter("\\A");
            embeddedText = objContentScanner.hasNext() ? objContentScanner.next() : "";
            objContentScanner.close();
            objContent.close();
        } catch (IOException e) {
            String errorMessage = "Failure when handling the message which was read from S3 object. Message was not received.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        return embeddedText;
    }



    public String getJSONFromS3Pointer(MessageS3Pointer s3Pointer) {
        String s3PointerStr = null;
        try {
            JsonDataConverter jsonDataConverter = new JsonDataConverter();
            s3PointerStr = jsonDataConverter.serializeToJson(s3Pointer);
        } catch (Exception e) {
            String errorMessage = "Failed to convert S3 object pointer to text. Message was not sent.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        return s3PointerStr;
    }

    public void storeTextInS3(String s3Key, String messageContentStr, Long messageContentSize) {
        InputStream messageContentStream = new ByteArrayInputStream(messageContentStr.getBytes(StandardCharsets.UTF_8));
        ObjectMetadata messageContentStreamMetadata = new ObjectMetadata();
        messageContentStreamMetadata.setContentLength(messageContentSize);
        PutObjectRequest putObjectRequest = new PutObjectRequest(clientConfiguration.getS3BucketName(), s3Key,
                messageContentStream, messageContentStreamMetadata);
        try {
            clientConfiguration.getAmazonS3Client().putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            String errorMessage = "Failed to store the message content in an S3 object. SNS message was not sent.";
            LOG.error(errorMessage, e);
            throw new AmazonServiceException(errorMessage, e);
        } catch (AmazonClientException e) {
            String errorMessage = "Failed to store the message content in an S3 object. SNS message was not sent.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
    }

    public static long getStringSizeInBytes(String str) {
        CountingOutputStream counterOutputStream = new CountingOutputStream();
        try {
            Writer writer = new OutputStreamWriter(counterOutputStream, "UTF-8");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            String errorMessage = "Failed to calculate the size of message payload.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        return counterOutputStream.getTotalSize();
    }
}
