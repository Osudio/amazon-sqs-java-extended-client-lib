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

    /**
     * {@inheritDoc}
     */
    public AddPermissionResult addPermission(String queueUrl, String label, List<String> aWSAccountIds, List<String> actions)
            throws AmazonClientException {
        return amazonSnsToBeExtended.addPermission(queueUrl, label, aWSAccountIds, actions);
    }

    /**
     * {@inheritDoc}
     */
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return amazonSnsToBeExtended.getCachedResponseMetadata(request);
    }

    /**
     * {@inheritDoc}
     */
    public void setEndpoint(String endpoint) throws IllegalArgumentException {
        amazonSnsToBeExtended.setEndpoint(endpoint);
    }

    /**
     * {@inheritDoc}
     */
    public void setRegion(Region region) throws IllegalArgumentException {
        amazonSnsToBeExtended.setRegion(region);
    }

    /**
     * {@inheritDoc}
     */
    public void shutdown() {
        amazonSnsToBeExtended.shutdown();
    }

    /**
     * {@inheritDoc}
     */
    public AddPermissionResult addPermission(AddPermissionRequest addPermissionRequest) {
        return amazonSnsToBeExtended.addPermission(addPermissionRequest);
    }

    /**
     * {@inheritDoc}
     */
    public CheckIfPhoneNumberIsOptedOutResult checkIfPhoneNumberIsOptedOut(CheckIfPhoneNumberIsOptedOutRequest checkIfPhoneNumberIsOptedOutRequest) {
        return amazonSnsToBeExtended.checkIfPhoneNumberIsOptedOut(checkIfPhoneNumberIsOptedOutRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ConfirmSubscriptionResult confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return amazonSnsToBeExtended.confirmSubscription(confirmSubscriptionRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ConfirmSubscriptionResult confirmSubscription(String s, String s1, String s2) {
        return amazonSnsToBeExtended.confirmSubscription(s, s1, s2);
    }

    /**
     * {@inheritDoc}
     */
    public ConfirmSubscriptionResult confirmSubscription(String s, String s1) {
        return amazonSnsToBeExtended.confirmSubscription(s, s1);
    }

    /**
     * {@inheritDoc}
     */
    public CreatePlatformApplicationResult createPlatformApplication(CreatePlatformApplicationRequest createPlatformApplicationRequest) {
        return amazonSnsToBeExtended.createPlatformApplication(createPlatformApplicationRequest);
    }

    /**
     * {@inheritDoc}
     */
    public CreatePlatformEndpointResult createPlatformEndpoint(CreatePlatformEndpointRequest createPlatformEndpointRequest) {
        return amazonSnsToBeExtended.createPlatformEndpoint(createPlatformEndpointRequest);
    }

    /**
     * {@inheritDoc}
     */
    public CreateTopicResult createTopic(CreateTopicRequest createTopicRequest) {
        return amazonSnsToBeExtended.createTopic(createTopicRequest);
    }

    /**
     * {@inheritDoc}
     */
    public CreateTopicResult createTopic(String s) {
        return amazonSnsToBeExtended.createTopic(s);
    }

    /**
     * {@inheritDoc}
     */
    public DeleteEndpointResult deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
        return amazonSnsToBeExtended.deleteEndpoint(deleteEndpointRequest);
    }

    /**
     * {@inheritDoc}
     */
    public DeletePlatformApplicationResult deletePlatformApplication(DeletePlatformApplicationRequest deletePlatformApplicationRequest) {
        return amazonSnsToBeExtended.deletePlatformApplication(deletePlatformApplicationRequest);
    }

    /**
     * {@inheritDoc}
     */
    public DeleteTopicResult deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        return amazonSnsToBeExtended.deleteTopic(deleteTopicRequest);
    }

    /**
     * {@inheritDoc}
     */
    public DeleteTopicResult deleteTopic(String s) {
        return amazonSnsToBeExtended.deleteTopic(s);
    }

    /**
     * {@inheritDoc}
     */
    public GetEndpointAttributesResult getEndpointAttributes(GetEndpointAttributesRequest getEndpointAttributesRequest) {
        return amazonSnsToBeExtended.getEndpointAttributes(getEndpointAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public GetPlatformApplicationAttributesResult getPlatformApplicationAttributes(GetPlatformApplicationAttributesRequest getPlatformApplicationAttributesRequest) {
        return amazonSnsToBeExtended.getPlatformApplicationAttributes(getPlatformApplicationAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public GetSMSAttributesResult getSMSAttributes(GetSMSAttributesRequest getSMSAttributesRequest) {
        return amazonSnsToBeExtended.getSMSAttributes(getSMSAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public GetSubscriptionAttributesResult getSubscriptionAttributes(GetSubscriptionAttributesRequest getSubscriptionAttributesRequest) {
        return amazonSnsToBeExtended.getSubscriptionAttributes(getSubscriptionAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public GetSubscriptionAttributesResult getSubscriptionAttributes(String s) {
        return amazonSnsToBeExtended.getSubscriptionAttributes(s);
    }

    /**
     * {@inheritDoc}
     */
    public GetTopicAttributesResult getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return amazonSnsToBeExtended.getTopicAttributes(getTopicAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public GetTopicAttributesResult getTopicAttributes(String s) {
        return amazonSnsToBeExtended.getTopicAttributes(s);
    }

    /**
     * {@inheritDoc}
     */
    public ListEndpointsByPlatformApplicationResult listEndpointsByPlatformApplication(ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
        return amazonSnsToBeExtended.listEndpointsByPlatformApplication(listEndpointsByPlatformApplicationRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ListPhoneNumbersOptedOutResult listPhoneNumbersOptedOut(ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
        return amazonSnsToBeExtended.listPhoneNumbersOptedOut(listPhoneNumbersOptedOutRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ListPlatformApplicationsResult listPlatformApplications(ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return amazonSnsToBeExtended.listPlatformApplications(listPlatformApplicationsRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ListPlatformApplicationsResult listPlatformApplications() {
        return amazonSnsToBeExtended.listPlatformApplications();
    }

    /**
     * {@inheritDoc}
     */
    public ListSubscriptionsResult listSubscriptions(ListSubscriptionsRequest listSubscriptionsRequest) {
        return amazonSnsToBeExtended.listSubscriptions(listSubscriptionsRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ListSubscriptionsResult listSubscriptions() {
        return amazonSnsToBeExtended.listSubscriptions();
    }

    /**
     * {@inheritDoc}
     */
    public ListSubscriptionsResult listSubscriptions(String s) {
        return amazonSnsToBeExtended.listSubscriptions(s);
    }

    /**
     * {@inheritDoc}
     */
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String s) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(s);
    }

    /**
     * {@inheritDoc}
     */
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String s, String s1) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(s, s1);
    }

    /**
     * {@inheritDoc}
     */
    public ListTopicsResult listTopics(ListTopicsRequest listTopicsRequest) {
        return amazonSnsToBeExtended.listTopics(listTopicsRequest);
    }

    /**
     * {@inheritDoc}
     */
    public ListTopicsResult listTopics() {
        return amazonSnsToBeExtended.listTopics();
    }

    /**
     * {@inheritDoc}
     */
    public ListTopicsResult listTopics(String s) {
        return amazonSnsToBeExtended.listTopics(s);
    }

    /**
     * {@inheritDoc}
     */
    public OptInPhoneNumberResult optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
        return amazonSnsToBeExtended.optInPhoneNumber(optInPhoneNumberRequest);
    }

    /**
     * {@inheritDoc}
     */
    public PublishResult publish(PublishRequest publishRequest) {
        return amazonSnsToBeExtended.publish(publishRequest);
    }

    /**
     * {@inheritDoc}
     */
    public PublishResult publish(String topicArn, String message) {
        return publish(topicArn, message);
    }

    /**
     * {@inheritDoc}
     */
    public PublishResult publish(String topicArn, String message, String subject) {
        final PublishRequest publishRequest = new PublishRequest(topicArn, message);
        return publish(publishRequest);
    }

    /**
     * {@inheritDoc}
     */
    public RemovePermissionResult removePermission(RemovePermissionRequest removePermissionRequest) {
        return amazonSnsToBeExtended.removePermission(removePermissionRequest);
    }

    /**
     * {@inheritDoc}
     */
    public RemovePermissionResult removePermission(String s, String s1) {
        return amazonSnsToBeExtended.removePermission(s, s1);
    }

    /**
     * {@inheritDoc}
     */
    public SetEndpointAttributesResult setEndpointAttributes(SetEndpointAttributesRequest setEndpointAttributesRequest) {
        return amazonSnsToBeExtended.setEndpointAttributes(setEndpointAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public SetPlatformApplicationAttributesResult setPlatformApplicationAttributes(SetPlatformApplicationAttributesRequest setPlatformApplicationAttributesRequest) {
        return amazonSnsToBeExtended.setPlatformApplicationAttributes(setPlatformApplicationAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public SetSMSAttributesResult setSMSAttributes(SetSMSAttributesRequest setSMSAttributesRequest) {
        return amazonSnsToBeExtended.setSMSAttributes(setSMSAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public SetSubscriptionAttributesResult setSubscriptionAttributes(SetSubscriptionAttributesRequest setSubscriptionAttributesRequest) {
        return amazonSnsToBeExtended.setSubscriptionAttributes(setSubscriptionAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public SetSubscriptionAttributesResult setSubscriptionAttributes(String s, String s1, String s2) {
        return amazonSnsToBeExtended.setSubscriptionAttributes(s, s1, s2);
    }

    /**
     * {@inheritDoc}
     */
    public SetTopicAttributesResult setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return amazonSnsToBeExtended.setTopicAttributes(setTopicAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    public SetTopicAttributesResult setTopicAttributes(String s, String s1, String s2) {
        return amazonSnsToBeExtended.setTopicAttributes(s, s1, s2);
    }

    /**
     * {@inheritDoc}
     */
    public SubscribeResult subscribe(SubscribeRequest subscribeRequest) {
        return amazonSnsToBeExtended.subscribe(subscribeRequest);
    }

    /**
     * {@inheritDoc}
     */
    public SubscribeResult subscribe(String s, String s1, String s2) {
        return amazonSnsToBeExtended.subscribe(s, s1, s2);
    }

    /**
     * {@inheritDoc}
     */
    public UnsubscribeResult unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return amazonSnsToBeExtended.unsubscribe(unsubscribeRequest);
    }

    /**
     * {@inheritDoc}
     */
    public UnsubscribeResult unsubscribe(String s) {
        return amazonSnsToBeExtended.unsubscribe(s);
    }
}
