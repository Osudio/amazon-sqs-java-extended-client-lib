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

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;

import java.util.List;

abstract class AmazonSNSExtendedClientBase implements AmazonSNS {
    private final AmazonSNS amazonSnsToBeExtended;

    AmazonSNSExtendedClientBase(AmazonSNS snsClient) {
        amazonSnsToBeExtended = snsClient;
    }

    @Override
    public AddPermissionResult addPermission(String queueUrl, String label, List<String> aWSAccountIds, List<String> actions)
            throws AmazonClientException {
        return amazonSnsToBeExtended.addPermission(queueUrl, label, aWSAccountIds, actions);
    }

    @Override
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return amazonSnsToBeExtended.getCachedResponseMetadata(request);
    }

    @Override
    public void setEndpoint(String endpoint) throws IllegalArgumentException {
        amazonSnsToBeExtended.setEndpoint(endpoint);
    }

    @Override
    public void setRegion(Region region) throws IllegalArgumentException {
        amazonSnsToBeExtended.setRegion(region);
    }

    @Override
    public void shutdown() {
        amazonSnsToBeExtended.shutdown();
    }

    @Override
    public AddPermissionResult addPermission(AddPermissionRequest addPermissionRequest) {
        return amazonSnsToBeExtended.addPermission(addPermissionRequest);
    }

    @Override
    public CheckIfPhoneNumberIsOptedOutResult checkIfPhoneNumberIsOptedOut(CheckIfPhoneNumberIsOptedOutRequest checkIfPhoneNumberIsOptedOutRequest) {
        return amazonSnsToBeExtended.checkIfPhoneNumberIsOptedOut(checkIfPhoneNumberIsOptedOutRequest);
    }

    @Override
    public ConfirmSubscriptionResult confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return amazonSnsToBeExtended.confirmSubscription(confirmSubscriptionRequest);
    }

    @Override
    public ConfirmSubscriptionResult confirmSubscription(String topicArn, String token, String authenticateOnUnsubscribe) {
        return amazonSnsToBeExtended.confirmSubscription( topicArn,  token,  authenticateOnUnsubscribe);
    }

    @Override
    public ConfirmSubscriptionResult confirmSubscription(String topicArn, String token) {
        return amazonSnsToBeExtended.confirmSubscription(topicArn, token);
    }

    @Override
    public CreatePlatformApplicationResult createPlatformApplication(CreatePlatformApplicationRequest createPlatformApplicationRequest) {
        return amazonSnsToBeExtended.createPlatformApplication(createPlatformApplicationRequest);
    }

    @Override
    public CreatePlatformEndpointResult createPlatformEndpoint(CreatePlatformEndpointRequest createPlatformEndpointRequest) {
        return amazonSnsToBeExtended.createPlatformEndpoint(createPlatformEndpointRequest);
    }

    @Override
    public CreateTopicResult createTopic(CreateTopicRequest createTopicRequest) {
        return amazonSnsToBeExtended.createTopic(createTopicRequest);
    }

    @Override
    public CreateTopicResult createTopic(String name) {
        return amazonSnsToBeExtended.createTopic(name);
    }

    @Override
    public DeleteEndpointResult deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
        return amazonSnsToBeExtended.deleteEndpoint(deleteEndpointRequest);
    }

    @Override
    public DeletePlatformApplicationResult deletePlatformApplication(DeletePlatformApplicationRequest deletePlatformApplicationRequest) {
        return amazonSnsToBeExtended.deletePlatformApplication(deletePlatformApplicationRequest);
    }

    @Override
    public DeleteTopicResult deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        return amazonSnsToBeExtended.deleteTopic(deleteTopicRequest);
    }

    @Override
    public DeleteTopicResult deleteTopic(String topicArn) {
        return amazonSnsToBeExtended.deleteTopic(topicArn);
    }

    @Override
    public GetEndpointAttributesResult getEndpointAttributes(GetEndpointAttributesRequest getEndpointAttributesRequest) {
        return amazonSnsToBeExtended.getEndpointAttributes(getEndpointAttributesRequest);
    }

    @Override
    public GetPlatformApplicationAttributesResult getPlatformApplicationAttributes(GetPlatformApplicationAttributesRequest getPlatformApplicationAttributesRequest) {
        return amazonSnsToBeExtended.getPlatformApplicationAttributes(getPlatformApplicationAttributesRequest);
    }

    @Override
    public GetSMSAttributesResult getSMSAttributes(GetSMSAttributesRequest getSMSAttributesRequest) {
        return amazonSnsToBeExtended.getSMSAttributes(getSMSAttributesRequest);
    }

    @Override
    public GetSubscriptionAttributesResult getSubscriptionAttributes(GetSubscriptionAttributesRequest getSubscriptionAttributesRequest) {
        return amazonSnsToBeExtended.getSubscriptionAttributes(getSubscriptionAttributesRequest);
    }

    @Override
    public GetSubscriptionAttributesResult getSubscriptionAttributes(String subscriptionArn) {
        return amazonSnsToBeExtended.getSubscriptionAttributes(subscriptionArn);
    }

    @Override
    public GetTopicAttributesResult getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return amazonSnsToBeExtended.getTopicAttributes(getTopicAttributesRequest);
    }

    @Override
    public GetTopicAttributesResult getTopicAttributes(String topicArn) {
        return amazonSnsToBeExtended.getTopicAttributes(topicArn);
    }

    @Override
    public ListEndpointsByPlatformApplicationResult listEndpointsByPlatformApplication(ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
        return amazonSnsToBeExtended.listEndpointsByPlatformApplication(listEndpointsByPlatformApplicationRequest);
    }

    @Override
    public ListPhoneNumbersOptedOutResult listPhoneNumbersOptedOut(ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
        return amazonSnsToBeExtended.listPhoneNumbersOptedOut(listPhoneNumbersOptedOutRequest);
    }

    @Override
    public ListPlatformApplicationsResult listPlatformApplications(ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return amazonSnsToBeExtended.listPlatformApplications(listPlatformApplicationsRequest);
    }

    @Override
    public ListPlatformApplicationsResult listPlatformApplications() {
        return amazonSnsToBeExtended.listPlatformApplications();
    }

    @Override
    public ListSubscriptionsResult listSubscriptions(ListSubscriptionsRequest listSubscriptionsRequest) {
        return amazonSnsToBeExtended.listSubscriptions(listSubscriptionsRequest);
    }

    @Override
    public ListSubscriptionsResult listSubscriptions() {
        return amazonSnsToBeExtended.listSubscriptions();
    }

    @Override
    public ListSubscriptionsResult listSubscriptions(String nextToken) {
        return amazonSnsToBeExtended.listSubscriptions(nextToken);
    }

    @Override
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
    }

    @Override
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String topicArn) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(topicArn);
    }

    @Override
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String topicArn, String nextToken) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(topicArn, nextToken);
    }

    @Override
    public ListTopicsResult listTopics(ListTopicsRequest listTopicsRequest) {
        return amazonSnsToBeExtended.listTopics(listTopicsRequest);
    }

    @Override
    public ListTopicsResult listTopics() {
        return amazonSnsToBeExtended.listTopics();
    }

    @Override
    public ListTopicsResult listTopics(String nextToken) {
        return amazonSnsToBeExtended.listTopics(nextToken);
    }

    @Override
    public OptInPhoneNumberResult optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
        return amazonSnsToBeExtended.optInPhoneNumber(optInPhoneNumberRequest);
    }

    @Override
    public PublishResult publish(PublishRequest publishRequest) {
        return amazonSnsToBeExtended.publish(publishRequest);
    }

    @Override
    public PublishResult publish(String topicArn, String message) {
        return publish(topicArn, message);
    }

    @Override
    public PublishResult publish(String topicArn, String message, String subject) {
        final PublishRequest publishRequest = new PublishRequest(topicArn, message);
        return publish(publishRequest);
    }

    @Override
    public RemovePermissionResult removePermission(RemovePermissionRequest removePermissionRequest) {
        return amazonSnsToBeExtended.removePermission(removePermissionRequest);
    }

    @Override
    public RemovePermissionResult removePermission(String topicArn, String label) {
        return amazonSnsToBeExtended.removePermission(topicArn, label);
    }

    @Override
    public SetEndpointAttributesResult setEndpointAttributes(SetEndpointAttributesRequest setEndpointAttributesRequest) {
        return amazonSnsToBeExtended.setEndpointAttributes(setEndpointAttributesRequest);
    }

    @Override
    public SetPlatformApplicationAttributesResult setPlatformApplicationAttributes(SetPlatformApplicationAttributesRequest setPlatformApplicationAttributesRequest) {
        return amazonSnsToBeExtended.setPlatformApplicationAttributes(setPlatformApplicationAttributesRequest);
    }

    @Override
    public SetSMSAttributesResult setSMSAttributes(SetSMSAttributesRequest setSMSAttributesRequest) {
        return amazonSnsToBeExtended.setSMSAttributes(setSMSAttributesRequest);
    }

    @Override
    public SetSubscriptionAttributesResult setSubscriptionAttributes(SetSubscriptionAttributesRequest setSubscriptionAttributesRequest) {
        return amazonSnsToBeExtended.setSubscriptionAttributes(setSubscriptionAttributesRequest);
    }

    @Override
    public SetSubscriptionAttributesResult setSubscriptionAttributes(String subscriptionArn, String attributeName, String attributeValue) {
        return amazonSnsToBeExtended.setSubscriptionAttributes(subscriptionArn, attributeName, attributeValue);
    }

    @Override
    public SetTopicAttributesResult setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return amazonSnsToBeExtended.setTopicAttributes(setTopicAttributesRequest);
    }

    @Override
    public SetTopicAttributesResult setTopicAttributes(String topicArn, String attributeName, String attributeValue) {
        return amazonSnsToBeExtended.setTopicAttributes(topicArn, attributeName, attributeValue);
    }

    @Override
    public SubscribeResult subscribe(SubscribeRequest subscribeRequest) {
        return amazonSnsToBeExtended.subscribe(subscribeRequest);
    }

    @Override
    public SubscribeResult subscribe(String topicArn, String protocol, String endpoint) {
        return amazonSnsToBeExtended.subscribe(topicArn, protocol, endpoint);
    }

    @Override
    public UnsubscribeResult unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return amazonSnsToBeExtended.unsubscribe(unsubscribeRequest);
    }

    @Override
    public UnsubscribeResult unsubscribe(String s) {
        return amazonSnsToBeExtended.unsubscribe(s);
    }
}
