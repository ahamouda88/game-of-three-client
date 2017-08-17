package com.challenge.got.client.http.jersey;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.springframework.web.bind.annotation.RequestMethod;

import com.challenge.got.client.http.GameServiceClient;
import com.challenge.got.client.model.response.GameResponse;
import com.challenge.got.persist.model.GOTGame;
import com.challenge.got.persist.model.ValueAdded;
import com.challenge.got.rest.constants.PathConstants;
import com.challenge.got.rest.response.BaseResponse;

public class GameServiceClientJersey extends AbstractHttpServiceClient implements GameServiceClient {

	public GameServiceClientJersey(String baserUrl) {
		super(baserUrl);
	}

	@Override
	public BaseResponse<GOTGame> addValue(Integer gameId, Integer playerId, ValueAdded move) {
		//@formatter:off
		WebTarget webTarget = baseTarget.path(GAMES_ENDPOINT_PATH)
										.path(Integer.toString(gameId))
										.path(PathConstants.PLAYERS_PATH)
										.path(Integer.toString(playerId))
											.queryParam("add", move.value());
		//@formatter:on
		return readResponse(webTarget, RequestMethod.GET, GameResponse.class);
	}

	@Override
	public BaseResponse<GOTGame> getGame(Integer gameId) {
		WebTarget webTarget = baseTarget.path(GAMES_ENDPOINT_PATH);
		if (gameId != null) webTarget = webTarget.path(Long.toString(gameId));
		return readResponse(webTarget, RequestMethod.GET, GameResponse.class);
	}

	@Override
	public BaseResponse<GOTGame> addGame(GOTGame game) {
		WebTarget webTarget = baseTarget.path(GAMES_ENDPOINT_PATH);
		Entity<GOTGame> entity = Entity.entity(game, super.mediaType);
		return readResponse(webTarget, RequestMethod.POST, entity, GameResponse.class);
	}

	@Override
	public BaseResponse<GOTGame> updateGame(GOTGame game) {
		WebTarget webTarget = baseTarget.path(GAMES_ENDPOINT_PATH);
		Entity<GOTGame> entity = Entity.entity(game, super.mediaType);
		return readResponse(webTarget, RequestMethod.PUT, entity, GameResponse.class);
	}
}
