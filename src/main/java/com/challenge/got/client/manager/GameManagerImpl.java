package com.challenge.got.client.manager;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.got.client.http.GameServiceClient;
import com.challenge.got.client.http.PlayerServiceClient;
import com.challenge.got.factory.GameFactory;
import com.challenge.got.persist.model.GOTGame;
import com.challenge.got.persist.model.GameStatus;
import com.challenge.got.persist.model.Player;
import com.challenge.got.persist.model.ValueAdded;
import com.challenge.got.rest.response.BaseResponse;

@Component
public class GameManagerImpl implements GameManager {

	@Autowired
	private PlayerServiceClient playerServiceClient;

	@Autowired
	private GameServiceClient gameServiceClient;

	@Override
	public GOTGame createGame(Player player) {
		if (player == null) return null;

		int minNumber = 2;
		int maxNumber = 100000000;
		int initialNumber = new Random().nextInt(maxNumber) + minNumber;
		GameStatus status = GameFactory.createGameStatus(initialNumber, null);
		GOTGame game = GameFactory.createGame(status, initialNumber, player);

		BaseResponse<GOTGame> response = gameServiceClient.addGame(game);
		return getData(response);
	}

	@Override
	public Player createPlayer(Player player) {
		if (player == null) return null;

		BaseResponse<Player> response = playerServiceClient.addPlayer(player);
		return getData(response);
	}

	@Override
	public GOTGame getGame(Integer gameId) {
		if (gameId == null) return null;

		BaseResponse<GOTGame> response = gameServiceClient.getGame(gameId);
		return getData(response);
	}

	@Override
	public GOTGame registerSecondPlayer(GOTGame game, Player player) {
		if (game == null || player == null) return null;
		game.setPlayer2(player);

		BaseResponse<GOTGame> response = gameServiceClient.updateGame(game);
		return getData(response);
	}

	@Override
	public GOTGame addGameMove(Integer gameId, Integer playerId, ValueAdded move) {
		if (gameId == null || playerId == null || move == null) return null;

		BaseResponse<GOTGame> response = gameServiceClient.addValue(gameId, playerId, move);
		return getData(response);
	}

	private <T> T getData(BaseResponse<T> response) {
		return (response != null && response.getStatus().getMessage().equals("Success")) ? response.getData() : null;
	}

}
