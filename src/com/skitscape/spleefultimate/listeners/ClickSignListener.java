package com.skitscape.spleefultimate.listeners;

import java.util.logging.Logger;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.skitscape.spleefultimate.GameManager;
import com.skitscape.spleefultimate.Messages;
import com.skitscape.spleefultimate.SpleefUltimate;


public class ClickSignListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!(e.getAction()==Action.RIGHT_CLICK_BLOCK || e.getAction()==Action.LEFT_CLICK_BLOCK)) return;
		Block clickedBlock = e.getClickedBlock(); 
		if(!(clickedBlock.getType()==Material.SIGN || clickedBlock.getType()==Material.SIGN_POST || clickedBlock.getType()==Material.WALL_SIGN)) return;
		Sign thisSign = (Sign) clickedBlock.getState();
		String[] lines = thisSign.getLines();
		if(lines.length<1) return;
		if(lines[0].equalsIgnoreCase("[SPLEEF]")) {
			e.setCancelled(true);
			String defGame = GameManager.getDefaultGameName();
			if(defGame!=null) {
				e.getPlayer().performCommand("spleef join " + defGame);
			} else {
				e.getPlayer().sendMessage(Messages.getMessage("error-sign_nodefaultset"));
			}
			//TODO: using performCommand() is not as efficient as actually instantiating the join class.
			//TODO: permissions (for placing signs with [SPLEEF], and for clicking on them).
		}
	}
}
