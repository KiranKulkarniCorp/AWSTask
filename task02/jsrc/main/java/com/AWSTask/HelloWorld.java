package com.AWSTask;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
		lambdaName = "hello_world",
		roleName = "hello_world-role",
		isPublishVersion = true,
		aliasName = "${lambdas_alias_name}",
		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	public Map<String, Object> handleRequest(Map<String, Object> request, Context context) {
		// Extracting request path and method
		String path = (String) request.getOrDefault("rawPath", "");
		String method = (String) request.getOrDefault("requestContext.http.method", "");

		// Response map
		Map<String, Object> response = new HashMap<>();

		// Handling valid GET request to /hello
		if ("/hello".equals(path) && "GET".equalsIgnoreCase(method)) {
			response.put("statusCode", 200);
			response.put("message", "Hello from Lambda");
			return response;
		}

		// Handling bad requests
		response.put("statusCode", 400);
		response.put("message", "Bad request syntax or unsupported method. Request path: " + path + ". HTTP method: " + method);
		return response;
	}
}
