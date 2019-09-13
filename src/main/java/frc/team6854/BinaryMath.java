package frc.team6854;

public class BinaryMath {

  public static boolean[] getBinaryform(int number) {
    /*int array is not needed but makes it easier for me to understand
      - Collin
    */
    
    boolean result[] = new boolean[8];

    StringBuilder binaryString = new StringBuilder();
    binaryString.append(Integer.toBinaryString(number)).reverse();
    char binaryLetterArray[] = binaryString.toString().toCharArray();

    for (int i = 0; i < binaryLetterArray.length; i++) {
      if(binaryLetterArray[i] == '0') {
        result[i] = false;
      }
      else if(binaryLetterArray[i] == '1') {
        result[i] = true;
      }
    }
    
    return result;
  }

  public static int getDecimalForm(boolean[] binary){
    int result = 0;
    
    int[] a = new int[binary.length];
    for(int i = 0; i < binary.length; i++) {
      if(binary[i] == true) {
        a[i] = 1;
      }
      else {
        a[i] = 0;
      }
    }

    for(int j = 0; j < 7; j++) {
      result = result | (a[j] << j);
    }

    return result;
  }
}
