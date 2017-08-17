package com.challenge.got.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.got.client.http.GameServiceClient;
import com.challenge.got.client.http.PlayerServiceClient;
import com.challenge.got.client.manager.GameManager;
import com.challenge.got.persist.model.GOTGame;
import com.challenge.got.persist.model.Player;
import com.challenge.got.rest.response.BaseResponse;

@Controller
public class MainController {

	private GOTGame currentGame = null;

	@Autowired
	private GameManager gameManager;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView start() {
		ModelAndView modelAndView = new ModelAndView();
		if (currentGame == null) {
			modelAndView.setViewName("homePage");
			modelAndView.addObject("player", new Player());
		} else {
			modelAndView.setViewName("gamePage");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/newgame", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView newGame() {
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}

	@RequestMapping(value = "/create-player", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView createPlayer(@ModelAttribute Player player, @ModelAttribute(value = "gameType") String s) {
		ModelAndView modelAndView = new ModelAndView();
		Player insertedPlayer = gameManager.createPlayer(player);
		if (insertedPlayer == null) {
			modelAndView.setViewName("error");
		} else {
			GOTGame game = gameManager.createGame(insertedPlayer);
			modelAndView.addObject("game", game);
			modelAndView.setViewName("gamePage");
		}
		return modelAndView;
	}
}
