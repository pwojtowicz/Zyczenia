package pl.netplus.wishesbase.support;

public class StringHelper {

	public static String removePolishChars(String text) {

		text = text.replace('ê', 'e');
		text = text.replace('ó', 'o');
		text = text.replace('¹', 'a');
		text = text.replace('œ', 's');
		text = text.replace('³', 'l');
		text = text.replace('¿', 'z');
		text = text.replace('Ÿ', 'z');
		text = text.replace('æ', 'c');
		text = text.replace('ñ', 'n');

		text = text.replace('Ê', 'E');
		text = text.replace('Ó', 'O');
		text = text.replace('¥', 'A');
		text = text.replace('Œ', 'S');
		text = text.replace('£', 'L');
		text = text.replace('¯', 'Z');
		text = text.replace('', 'Z');
		text = text.replace('Æ', 'C');
		text = text.replace('Ñ', 'N');
		return text;
	}

	public static String daysCountToString(long days) {

		if (days == 0)
			return "Dodany dzisiaj";
		if (days >= 1 && days < 11)
			return "Dodany wczoraj";

		if (days >= 11 && days < 60)
			return "Dodany 11 dni temu";

		if (days >= 60 && days < 360)
			return "Dodany 2 miesi¹ce temu";

		return "Dodany ponad rok temu";

	}
}
