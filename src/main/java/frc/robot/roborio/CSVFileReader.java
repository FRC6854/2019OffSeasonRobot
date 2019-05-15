package frc.robot.roborio;

import frc.robot.Robot;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {
  public static Double[][] pathLeft(String folder) {
    List<List<Double>> table = new ArrayList<List<Double>>();

    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(folder + "/path_left.csv"));
      String line = reader.readLine();
      while (line != null) {
        List<Double> row = new ArrayList<>();
        String format = line.replace('\n', Character.MIN_VALUE);
        String[] values = format.split(",");
        for (String value : values) {
          row.add(Double.parseDouble(value));
        }
        table.add(row);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // ---------------------------------
    // CONVERT 2D ARRAYLIST TO 2D ARRAY
    // ---------------------------------
    Double[][] array = new Double[table.size()][];
    for (int i = 0; i < table.size(); i++) {
      List<Double> row = table.get(i);
      array[i] = row.toArray(new Double[row.size()]);
    }
    return array;
  }

  public static Double[][] pathRight(String folder) {
    List<List<Double>> table = new ArrayList<List<Double>>();

    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(folder + "/path_right.csv"));
      String line = reader.readLine();
      while (line != null) {
        List<Double> row = new ArrayList<>();
        String format = line.replace('\n', Character.MIN_VALUE);
        String[] values = format.split(",");
        for (String value : values) {
          row.add(Double.parseDouble(value));
        }
        table.add(row);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // ---------------------------------
    // CONVERT 2D ARRAYLIST TO 2D ARRAY
    // ---------------------------------
    Double[][] array = new Double[table.size()][];
    for (int i = 0; i < table.size(); i++) {
      List<Double> row = table.get(i);
      array[i] = row.toArray(new Double[row.size()]);
    }
    return array;
  }
}
