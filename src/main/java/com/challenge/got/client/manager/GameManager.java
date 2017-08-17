package com.challenge.got.client.manager;

import com.challenge.got.persist.model.GOTGame;
import com.challenge.got.persist.model.Player;
import com.challenge.got.persist.model.ValueAdded;

public interface GameManager {

	public GOTGame createGame(Player player);

	public Player createPlayer(Player player);

	public GOTGame getGame(Integer gameId);

	public GOTGame registerSecondPlayer(GOTGame game, Player player);

	public GOTGame addGameMove(Integer gameId, Integer playerId, ValueAdded move);
}
