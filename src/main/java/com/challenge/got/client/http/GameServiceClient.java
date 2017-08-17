package com.challenge.got.client.http;

import com.challenge.got.persist.model.GOTGame;
import com.challenge.got.persist.model.ValueAdded;
import com.challenge.got.rest.constants.PathConstants;
import com.challenge.got.rest.response.BaseResponse;

public interface GameServiceClient {

	public static final String GAMES_ENDPOINT_PATH = PathConstants.GAMES_FULL_PATH;

	public BaseResponse<GOTGame> getGame(Integer gameId);

	public BaseResponse<GOTGame> addValue(Integer gameId, Integer playerId, ValueAdded move);

	public BaseResponse<GOTGame> addGame(GOTGame game);

	public BaseResponse<GOTGame> updateGame(GOTGame game);
}
