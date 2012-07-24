package com.skitscape.spleefultimate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager
{
  private static HashMap<String, YamlConfiguration> loadedConfigs_ = new HashMap();

  public static YamlConfiguration getConfiguration(String filePath)
  {
    if ((filePath == null) || (filePath.isEmpty())) {
      return null;
    }

    if (loadedConfigs_.containsKey(filePath))
    {
      YamlConfiguration existingConfiguration = (YamlConfiguration)loadedConfigs_.get(filePath);

      if (existingConfiguration != null) {
        return existingConfiguration;
      }
      loadedConfigs_.remove(filePath);
    }

    File configFile = getFile(filePath);
    if (!configFile.isFile()) {
      return null;
    }
    YamlConfiguration configuration = new YamlConfiguration();
    try
    {
      configuration.load(configFile);
    }
    catch (Exception e)
    {
      e.printStackTrace();

      return null;
    }

    loadedConfigs_.put(filePath, configuration);

    return configuration;
  }

  public static void saveConfiguration(String filePath, YamlConfiguration configuration)
  {
    if ((filePath == null) || (filePath.isEmpty()))
      return;
    if (configuration == null) {
      return;
    }
    File configFile = getFile(filePath);
    if (!configFile.isAbsolute()) {
      return;
    }
    try
    {
      configuration.save(configFile);
    }
    catch (IOException e)
    {
      e.printStackTrace();

      return;
    }

    loadedConfigs_.put(filePath, configuration);
  }

  private static File getFile(String relativePath)
  {
    String pluginFolderPath = SpleefUltimate.dataFolder.getAbsolutePath();
    String filePath = pluginFolderPath + File.separator + relativePath;

    File file = new File(filePath);

    return file;
  }
}