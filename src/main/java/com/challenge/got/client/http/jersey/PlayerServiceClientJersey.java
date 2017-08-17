package com.challenge.got.client.http.jersey;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.springframework.web.bind.annotation.RequestMethod;

import com.challenge.got.client.http.PlayerServiceClient;
import com.challenge.got.client.model.response.PlayerResponse;
import com.challenge.got.persist.model.Player;
import com.challenge.got.rest.response.BaseResponse;

public class PlayerServiceClientJersey extends AbstractHttpServiceClient implements PlayerServiceClient {

	public PlayerServiceClientJersey(String baseUrl) {
		super(baseUrl);
	}

	@Override
	public BaseResponse<Player> getPlayer(Long playerId) {
		WebTarget webTarget = baseTarget.path(PLAYERS_ENDPOINT_PATH);
		if (playerId != null) webTarget = webTarget.path(Long.toString(playerId));
		return readResponse(webTarget, RequestMethod.GET, PlayerResponse.class);
	}

	@Override
	public BaseResponse<Player> addPlayer(Player player) {
		WebTarget webTarget = baseTarget.path(PLAYERS_ENDPOINT_PATH);
		Entity<Player> entity = Entity.entity(player, super.mediaType);
		return readResponse(webTarget, RequestMethod.POST, entity, PlayerResponse.class);
	}

}
