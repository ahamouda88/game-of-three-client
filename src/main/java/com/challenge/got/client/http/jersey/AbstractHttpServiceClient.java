package com.challenge.got.client.http.jersey;

import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.challenge.got.rest.response.BaseResponse;
import com.google.common.collect.Sets;

public class AbstractHttpServiceClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpServiceClient.class);

	private static final Set<Integer> VALID_STATUS = Sets.newHashSet(HttpStatus.CREATED.value(), HttpStatus.OK.value());

	private final Client client;
	protected final String mediaType;
	protected WebTarget baseTarget;

	public AbstractHttpServiceClient(String baserUrl) {
		this(baserUrl, MediaType.APPLICATION_JSON_VALUE);
	}

	public AbstractHttpServiceClient(String baserUrl, String mediaType) {
		ClientConfig clientConfig = new ClientConfig();
		this.client = ClientBuilder.newClient(clientConfig);
		this.baseTarget = client.target(baserUrl);
		this.mediaType = mediaType;
	}

	protected <T extends BaseResponse<?>> String getErrorLogMessage(T response) {
		StringBuilder builder = new StringBuilder();
		for (String error : response.getStatus().getErrors()) {
			if (builder.length() > 0) builder.append(" | ");
			builder.append(error);
		}
		return builder.toString();
	}

	protected <T extends BaseResponse<?>> T readResponse(WebTarget webTarget, RequestMethod method,
			Class<T> responseType) {
		return readResponse(webTarget, method, null, responseType);
	}

	protected <T extends BaseResponse<?>, E> T readResponse(WebTarget webTarget, RequestMethod method, Entity<E> entity,
			Class<T> responseType) {
		LOGGER.debug("url=[{}]", webTarget.getUri());
		try {
			Builder webRequestBuilder = webTarget.request().accept(mediaType);
			Response wsResponse;
			if (entity == null) wsResponse = webRequestBuilder.method(method.toString());
			else wsResponse = webRequestBuilder.method(method.toString(), entity);

			T response = wsResponse.readEntity(responseType);
			if (!VALID_STATUS.contains(response.getStatus().getCode())) {
				LOGGER.info("request [{}] failed: [{}]", webTarget.getUri(), getErrorLogMessage(response));
			}
			return response;
		} catch (Exception e) {
			LOGGER.error("request [{}] failed: {}", webTarget.getUri(), e.getMessage());
			throw e;
		}
	}
}
