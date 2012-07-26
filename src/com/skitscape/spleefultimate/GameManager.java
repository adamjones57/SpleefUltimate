package com.skitscape.spleefultimate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class GameManager
{
  private static HashMap<String, SpleefGame> games_ = new HashMap<String, SpleefGame>();
  private static SpleefGame defaultGame = null; 
  private static String defaultGameName = null;

  public static SpleefGame getGame(String id)
  {
    if (games_.containsKey(id)) {
      return (SpleefGame)games_.get(id);
    }
    return null;
  }

  public static SpleefGame getGame(Player player)
  {
    for (SpleefGame game : games_.values())
    {
      if (game.hasPlayer(player)) {
        return game;
      }
    }
    return null;
  }

  public static boolean hasGame(String id)
  {
    return games_.containsKey(id);
  }

  public static void addGame(String id)
  {
    if (hasGame(id)) {
      return;
    }
    games_.put(id, new SpleefGame());
  }

  public static void removeGame(String id)
  {
    if (!hasGame(id)) {
      return;
    }
    removeDefaultGame(id);
    games_.remove(id);
  }

  public static void renameGame(String oldId, String newId)
  {
    if (!hasGame(oldId))
      return;
    if (hasGame(newId)) {
      return;
    }
    if(defaultGameName!=null && defaultGameName.equalsIgnoreCase(oldId)) {
    	defaultGameName = newId;
    }
    games_.put(newId, getGame(oldId));
    games_.remove(oldId);
  }

  @SuppressWarnings("unused")
public static void loadGames(ConfigurationSection parentSection)
  {
    if (parentSection == null) {
      return;
    }

    Set<String> gameNames = parentSection.getKeys(false);

    if ((gameNames == null) || (gameNames.size() == 0)) {
      return;
    }
    for (Object name : gameNames)
    {
      SpleefGame game = new SpleefGame();

      game.load(parentSection.getConfigurationSection((String) name));

      games_.put((String) name, game);
	  if (parentSection == null) {
	      }

	      Map<String, Object> gameName = parentSection.getValues(false);

	      if ((gameName == null) || (gameName.size() != 1)) {
	        return;
	      }
	      if(games_.containsKey(gameName.get("name"))) {
	    	  defaultGame = games_.get((String) gameName.get("name"));
	    	  defaultGameName = (String) gameName.get("name");
	      }
	  }
    }

  public static void saveGames(ConfigurationSection parentSection)
  {
    for (String gameName : games_.keySet())
    {
      ConfigurationSection gameSection = parentSection.createSection(gameName);

      SpleefGame game = (SpleefGame)games_.get(gameName);

      game.save(gameSection);
    }
    
  }
  
  public static SpleefGame getDefaultGame() {
	return defaultGame;
  }
  
  public static boolean setDefaultGame(String gameName) {
	  if(games_.get(gameName)!=null) {
			defaultGame = games_.get(gameName);
			defaultGameName = gameName;
			return true;
	  } else {
		  	return false;
	  }
	}
  
  public static void loadDefaultGame(ConfigurationSection parentSection) {
	  if (parentSection == null) {
        return;
      }

      Map<String, Object> gameNames = parentSection.getValues(false);

      if ((gameNames == null) || (gameNames.size() != 1)) {
        return;
      }
      if(games_.containsKey(gameNames.get("name"))) {
    	  defaultGame = games_.get((String) gameNames.get("name"));
    	  defaultGameName = (String) gameNames.get("name");
      }
  }
  public static void saveDefaultGame(ConfigurationSection parentSection)
  {
	      ConfigurationSection gameSection = parentSection.createSection(defaultGameName);

	      SpleefGame game = (SpleefGame)games_.get(defaultGameName);

	      game.save(gameSection);
      parentSection.set("name", defaultGameName);
  }
  public static String getDefaultGameName() {
	  return defaultGameName;
  }

	public static void removeDefaultGame(Object object) {
		defaultGame = null;
		defaultGameName = null;
	}
}