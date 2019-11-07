package frc.team6854;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Filesystem;

public class CSVFileManager {
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

  public static boolean writeCSVLog (String[][] log) throws IOException {
    File file = new File(Filesystem.getDeployDirectory() + "/logs/log_" + LocalTime.now().toString());

    if (file.isFile()) {
      FileWriter csvWriter = new FileWriter(file);

      csvWriter.append("Class Name");
      csvWriter.append(",");
      csvWriter.append("Message");
      csvWriter.append("\n");

      for (String[] row : log) {
        csvWriter.append(String.join(",", row));
        csvWriter.append("\n");
      }

      csvWriter.flush();
      csvWriter.close();
      
      return true;
    }

    return false;
  }
}
