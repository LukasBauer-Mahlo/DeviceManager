package de.bs1.landshut.device.util;

import org.jetbrains.annotations.NotNull;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class Logger {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss] ");

  public void log(@NotNull String message) {
    try (
        FileWriter fileWriter = new FileWriter("server.log", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter)
    ) {
      printWriter.println(SIMPLE_DATE_FORMAT.format(System.currentTimeMillis()) + message);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

}
