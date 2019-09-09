package frc.team6854;

public class BinaryMath {

  public static boolean[] getBinaryform(int number) {
    /*int array is not needed but makes it easier for me to understand
      - Collin
    */
    
    int container[] = new int[8];
    boolean result[] = new boolean[8];
    int i = 0;
    while (number > 0){
        container[i] = number%2;
        i++;
        number = number/2;
    }
    for (int j = 7; j >= 0; j--) {
      if(container[j] == 1){
        result[j] = true;
      }
      else {
        result[j] = false;
      }
    }
    return result;
  }

  public static int getDecimalForm(boolean[] binary){
    int result = 0;
    StringBuilder binaryString = new StringBuilder();
    for(int i = binary.length - 1; i >= 0; i--){
      if(binary[i] == true){
        binaryString.append("1");
      }
      else {
        binaryString.append("0");
      }
    }
    result = Integer.parseInt(binaryString.toString(), 2);
    return result;
  }
}
