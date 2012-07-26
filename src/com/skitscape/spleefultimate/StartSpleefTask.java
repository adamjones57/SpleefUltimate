package com.skitscape.spleefultimate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

public class StartSpleefTask extends TimerTask {

	private SpleefGame game;
	private int remaining;
	
	public StartSpleefTask(int timeRemaining, SpleefGame gameToStart) {
		this.game = gameToStart;
		this.remaining = timeRemaining;
	}
	
	public void run() {
		if(remaining < 0) {
		    Logger.getLogger("Minecraft").info("Activating!");
			game.setCounting(Boolean.valueOf(false));
			game.getFormerPlayers_().clear();
			game.getArena().saveState();
			game.setActive(Boolean.valueOf(true));
		} else {
			if(game.getCountdown() == remaining) {
				//First time.
			    //Logger.getLogger("Minecraft").info("First countdown thingy");
				if (game.isActive() || game.getCounting()) {
					return;
				}
				game.setCounting(Boolean.valueOf(true));
			}
		    Logger.getLogger("Minecraft").info("Informing players");
			game.tellActivePlayers(ChatColor.GREEN + "Game starting in " + new Integer(remaining).toString() + " seconds");
			Timer timer = new Timer();
			timer.schedule(new StartSpleefTask(remaining-1, game), 1000);
			if(remaining == 0) {
				start();
			}
		}
	}


	
	void start() {
		game.start();
		game.tellActivePlayers(ChatColor.GREEN + "Game started! Good luck!");
	}
	
	}

