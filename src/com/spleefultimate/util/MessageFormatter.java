package com.spleefultimate.util;

public class MessageFormatter
{
  public static String format(String message, String constant, String value)
  {
    return message.replace(constant, value);
  }

  public static String format(String message, String[] constants, String[] values)
  {
    if ((message == null) || (constants == null) || (values == null)) {
      return message;
    }
    if (message.isEmpty()) {
      return message;
    }
    String result = message;

    for (int i = 0; i < constants.length; i++)
    {
      if (i >= values.length) {
        return result;
      }
      result = result.replace(constants[i], values[i]);
    }

    return result;
  }
}