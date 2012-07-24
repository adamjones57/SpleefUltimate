package com.skitscape.spleefultimate.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper
{
  private static final Logger _logger = Logger.getLogger("Minecraft");

  public static void log(String logString)
  {
    log(logString, Level.INFO);
  }

  public static void log(String logString, Level level)
  {
    _logger.log(level, "[SpleefExtreme] " + logString);
  }
}