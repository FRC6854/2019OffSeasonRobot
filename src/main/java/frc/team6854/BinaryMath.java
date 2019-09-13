package frc.team6854;

public class BinaryMath {

  public static char[] getBinaryform(int number) {

    StringBuilder binaryString = new StringBuilder();
    binaryString.append(Integer.toBinaryString(number));
    char binaryLetterArray[] = binaryString.toString().toCharArray();

    return binaryLetterArray;
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
