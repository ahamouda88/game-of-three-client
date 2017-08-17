package com.challenge.got.client.http;

import com.challenge.got.persist.model.Player;
import com.challenge.got.rest.constants.PathConstants;
import com.challenge.got.rest.response.BaseResponse;

public interface PlayerServiceClient {

	public static final String PLAYERS_ENDPOINT_PATH = PathConstants.PLAYERS_FULL_PATH;

	public BaseResponse<Player> getPlayer(Long playerId);

	public BaseResponse<Player> addPlayer(Player player);
}
