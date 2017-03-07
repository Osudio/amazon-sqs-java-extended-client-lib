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

import com.amazonaws.util.VersionInfoUtils;

class SNSExtendedClientConstants {
	public static final String RESERVED_ATTRIBUTE_NAME = "SNSLargePayloadSize";
	public static final int MAX_ALLOWED_ATTRIBUTES = 9;

	static final String USER_AGENT_HEADER = AmazonSNSExtendedClient.class.getSimpleName() + "/" + VersionInfoUtils.getVersion();
}
