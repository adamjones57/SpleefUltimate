package com.skitscape.spleefultimate.util;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigHelper
{
  public static Location readLocation(ConfigurationSection section)
  {
    if (section == null) {
      return null;
    }

    if ((!section.isDouble("x")) || (!section.isDouble("y")) || (!section.isDouble("z")) || 
      (!section.isString("worlduid"))) {
      return null;
    }
    double x = section.getDouble("x");
    double y = section.getDouble("y");
    double z = section.getDouble("z");

    String worldUID = section.getString("worlduid");
    World world = Bukkit.getServer().getWorld(UUID.fromString(worldUID));
    if (world == null) {
      return null;
    }

    if ((!section.isDouble("yaw")) || (!section.isDouble("pitch"))) {
      return new Location(world, x, y, z);
    }
    double yaw = section.getDouble("yaw");
    double pitch = section.getDouble("pitch");

    return new Location(world, x, y, z, (float)yaw, (float)pitch);
  }

  public static void writeLocation(ConfigurationSection section, Location location)
  {
    section.set("x", Double.valueOf(location.getX()));
    section.set("y", Double.valueOf(location.getY()));
    section.set("z", Double.valueOf(location.getZ()));
    section.set("worlduid", location.getWorld().getUID().toString());
    section.set("yaw", Float.valueOf(location.getYaw()));
    section.set("pitch", Float.valueOf(location.getPitch()));
  }
}