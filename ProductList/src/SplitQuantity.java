import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitQuantity {

	public static void main(String[] args) {

		try {
			String line, outputData;
			//String convertLine, convertOutput;
			File initialFile = new File(
					"C:\\Users\\Elena\\Dropbox\\BIP-TeamB-DataSources\\Nutritional Data\\quantity_txt.txt");
			File cleanFile = new File(
					"C:\\Users\\Elena\\Dropbox\\BIP-TeamB-DataSources\\Nutritional Data\\split_quantity.txt");

			FileReader fr = new FileReader(initialFile);
			BufferedReader br = new BufferedReader(fr);

			FileWriter fw = new FileWriter(cleanFile);
			BufferedWriter bw = new BufferedWriter(fw);

			while ((line = br.readLine()) != null) {
				String line1 = removeAdditionalInfo(line);
				String line2 = setNull(line1);
				String line3 = splitX(line2);
				String line4 = decimalFix(line3);

				outputData = line4.replace(line4, getNumber(line4) + '\t'
						+ getStandardUnitName(line4));
				bw.write(outputData + '\n');
			}
			br.close();
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getNumber(String str) {
		Matcher matcher = Pattern.compile("\\d+(\\.\\d+)?").matcher(str);
		String result = "";
		boolean found = false;
		while (matcher.find() && !found) {
			result = (matcher.group());
			if (!str.contains("rollito"))
			found=true;
		}
		return result;
	}


	public static String getStandardUnitName(String str) {
		String[] lUnits = new String[] { "litro", "litros", "l" };
		String[] gUnits = new String[] { "grs", "gramos", "gram", "g" };
		String[] remainingUnits = new String[] {"lbs", "kg", "cl", "ml"};
		String unit = "";
		// String output = "";

		for (int i = 0; i < lUnits.length; i++) {
			if ((str.toLowerCase().contains(lUnits[i]) && !str.toLowerCase()
					.contains("ml"))
					|| (str.toLowerCase().contains(lUnits[i]) && !str
							.toLowerCase().contains("cl"))) {
				unit = "ltr";
			}
		}

		for (int i = 0; i < gUnits.length; i++) {
			if (str.toLowerCase().contains(gUnits[i])
					&& !str.toLowerCase().contains("kg")) {
				unit = "gr";
			}
		}
		
		for (int i = 0; i < remainingUnits.length; i++) {
			if (str.toLowerCase().contains(remainingUnits[i]))
				unit = remainingUnits[i];
		}

		return unit;
	}

	public static String setNull(String str) {
		String[] quantity = new String[] { "planta", "pieza", "ud.", "unidad",
				"granel", "barrita", "bonne", "no" };
		String output = str;

		for (int i = 0; i < quantity.length; i++) {
			if (str.toLowerCase().contains(quantity[i])) {
				output = "";
			}
		}
		return output;
	}

	public static String removeAdditionalInfo(String str) {
		String output = str;
		if (str.contains("\\+")) {
			System.out.println("with a plus" + str);
			str = str.replaceAll("\\+", "");
		} else if (str.contains("(")) {
			String splitParts[] = str.split("\\(");
			String firstPart = splitParts[0];
			// System.out.println (firstPart);
			output = str.replace(str, firstPart);
		}
		return output;
	}

	public static String splitX(String str) {
		String output = str;
		int number = 0;
		if (str.contains("x")) {
			// System.out.println("with x "+ str);
			String splits[] = str.split("x");
			String firstPart = splits[0];
			String secondPart = splits[1];
			String firstNum = getNumber(firstPart);
			String secondNum = getNumber(secondPart);
			String unit = getStandardUnitName(secondPart);
			number = Integer.parseInt(firstNum) * Integer.parseInt(secondNum);
			output = str.replace(str, String.valueOf(number) + '\t' + unit);
		}
		return output;
	}

	public static String decimalFix(String str) {
		String output = str;
		if (str.contains(",") && str.toLowerCase().contains("ml")) {
			// System.out.println("contains a comma and ml "+ str);
			output = str.replaceAll("\\,", "");
			// System.out.println("remove comma "+ output);
		} else if (str.contains(".") && str.toLowerCase().contains("ml")) {
			// System.out.println("contains a comma and ml "+ str);
			output = str.replaceAll("\\.", "");
			// System.out.println("remove comma "+ output);
		} else if (str.contains(",")) {
			// System.out.println("contains a comma and NOT ml "+ str);
			output = str.replaceAll("\\,", "\\.");
			// System.out.println("replace comma by dot "+ output);
		}

		return output;
	}
	
	public static double convertedValue(String str){
		double value = Double.parseDouble(getNumber(str));
		String unit = getStandardUnitName(str);
		
		if (unit.contains("kg"))
			value = value * 1000;
		else if (unit.contains("lbs"))
			value = value * 453.592;
		else if (unit.contains("cl"))
			value = value * 10;
		else if (unit.contains("ltr"))
			value = value * 1000;
		return value;	
	}
	
	public static String convertedUnit (String str){
		String unit = getStandardUnitName(str);
		
		if (unit.equals("kg") || unit.equals("lbs"))
			unit = "grs";
		if (unit.equals("cl") || unit.equals("ltr"))
			unit = "ml";
		return unit;
	}
}
