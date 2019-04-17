/**
 * This class contains four methods to convert numbers: binary strings to decimal integers,
 * binary strings to hexadecimal strings, decimal integers to binary strings,
 * and hexadecimal strings to binary strings.
 * 11/20/2018
 * @author Matthew
 *
 */
public class Converter extends Object{
	
	private Converter() {}
	
	/**
	 * Converts binary string into a decimal integer of the same value
	 * @param binary The binary string to convert, must start with "0b"
	 * @return The decimal representation of the given binary string
	 * @throws IllegalArgumentException Exception thrown when the given binary string is
	 * improperly formatted, or if it is too large
	 */
	//Wrapper
	public static int binaryToDecimal(String binary) throws IllegalArgumentException{
		if (!properBinaryForm(binary)) 
			throw new IllegalArgumentException(
				"binaryToDecimal(String binary) in class Converter.java requires properly formatted binary string");
		
		//Recursive call
		return binaryToDecimal(binary, binary.length()-1, 1);
	}
	//Helper
	private static int binaryToDecimal(String binary, int index, int power) {
		if (binary.charAt(index) == 'b') 
			return 0;
		if (binary.charAt(index) == '0') 
			return binaryToDecimal(binary, --index, 2*power);
		return ((power == 1)? 1 : power) + binaryToDecimal(binary, --index, 2*power);
	}

	/**
	 * Converts binary string into a hexadecimal string of the same value
	 * @param binary The binary string to convert, must start with "0b"
	 * @return The hexadecimal representation of the given binary string, or
	 * null if the given string is invalid
	 */
	//Wrapper
	public static String binaryToHex(String binary) {
		if (!properBinaryForm(binary)) 
			return null;
		
		//Chop off preceding "0b" to make calculations easier
		String concatonatedBinary = "";
		for (int i = 2; i < binary.length(); i++) {
			concatonatedBinary += binary.charAt(i);
		}
		
		//Pad left with 0's so concatonatedBinary.length() is divisible by 4
		for (int i = 0; i < concatonatedBinary.length()%4; i++) {
			concatonatedBinary = '0' + concatonatedBinary;
		}
		
		//Put the binary number into an array of strings so we can work with 4 digits at a time
		String chunkOfFour = "";
		String[] choppedUpBinary = new String[concatonatedBinary.length()/4];
		for (int i = 0; i < concatonatedBinary.length(); i++) {
			chunkOfFour = chunkOfFour + concatonatedBinary.charAt(i);
			if (chunkOfFour.length()%4 == 0) {
				choppedUpBinary[(i+1)/4 - 1] = chunkOfFour;
				chunkOfFour = "";
			}
		}
		
		//Recursive call
		return "0x" + binaryToHex(choppedUpBinary, 0);
	}
	//Helper
	private static String binaryToHex(String[] choppedUpBinary, int index) {
		if (index == choppedUpBinary.length)
			return "";
		return singularBinaryDigitToHex(choppedUpBinary[index]) + binaryToHex(choppedUpBinary, index+1);
	}
	private static char singularBinaryDigitToHex(String fourBitBin) {
		int digit = (8 * (fourBitBin.charAt(0) - '0')) + 
				(4 * (fourBitBin.charAt(1) - '0')) + 
				(2 * (fourBitBin.charAt(2) - '0')) + 
				(fourBitBin.charAt(3) - '0');
		if (digit < 10)
			return (char) (digit + 48);
		return (char) (digit + 55);
	}
	
	/**
	 * Converts decimal integer into a binary string of the same value
	 * @param decimal The decimal integer to convert, must be nonnegative
	 * @return The binary representation of the given decimal integer
	 * @throws IllegalArgumentException Exception thrown when the given integer is negative
	 */
	//Wrapper
	public static String decimalToBinary(int decimal) throws IllegalArgumentException{
		if (decimal < 0) 
			throw new IllegalArgumentException(
				"decimalToBinary(int decimal) in class Converter.java requires nonnegative argument");
		return "0b" + decimalToBinaryHelper(decimal);
	} 
	//Helper
	private static String decimalToBinaryHelper(int decimal) {
		if (decimal == 0)
			return "0";
		if (decimal == 1)
			return "1";
		if (decimal%2 == 0)
			return decimalToBinaryHelper(decimal/2) + "0";
		return decimalToBinaryHelper(decimal/2) + "1";
	}
	
	/**
	 * Converts hexadecimal string into a binary string of the same value
	 * @param hex the hexadecimal string to convert, must start with "0x"
	 * @return The binary representation of the given hexadecimal string, or
	 * null if the given string is invalid
	 */
	//Wrapper
	public static String hexToBinary(String hex) {
		if (!properHexForm(hex)) 
			return null;
		return "0b" + hexToBinary(hex, hex.length()-1);
	}
	//Helper
	private static String hexToBinary(String hex, int index) {
		if (hex.charAt(index) == 'x') 
			return "";
		return hexToBinary(hex, index-1) + singularHexDigitToBinary(hex.charAt(index));
	}
	private static String singularHexDigitToBinary(char hexDigit) {
		String result = "";
		//hexDigit is a number
		if (hexDigit - '0' < 10) {
			result = decimalToBinaryHelper(hexDigit - '0');
			for (int i = 0; i < (result.length()%4); i++) {
				result = '0' + result;
			}
			return result;
		}
		//hexDigit is a letter
		return decimalToBinaryHelper(hexDigit - 55);
	}
	
	//Checks to make sure given binary string is properly formatted
	private static boolean properBinaryForm(String binary) {
		if (binary == null) 
			return false;
		if (binary.length() < 3 || binary.length() > 33) 
			return false;
		if (binary.charAt(0) != '0') 
			return false;
		if (binary.charAt(1) != 'b') 
			return false;
		String validChars = "01";
		for (int i = 2; i < binary.length(); i++) {
			if (!validChars.contains("" + binary.charAt(i))) 
				return false;
		}
		return true;
	}
	
	//Checks to make sure given hex string is properly formatted
	private static boolean properHexForm(String hex) {
		if (hex == null) 
			return false;
		if (hex.length() < 3|| hex.length() > 10) 
			return false;
		if (hex.charAt(0) != '0') 
			return false;
		if (hex.charAt(1) != 'x') 
			return false;
		if (hex.length() == 10 && hex.charAt(2) > '7')
			return false;
		String validChars = "0123456789ABCDEF";
		for (int i = 2; i < hex.length(); i++) {
			if (!validChars.contains("" + hex.charAt(i))) 
				return false;
		}
		return true;
	}

}
