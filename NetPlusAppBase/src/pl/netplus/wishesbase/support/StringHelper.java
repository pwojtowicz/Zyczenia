package pl.netplus.wishesbase.support;

public class StringHelper {

	public static String removePolishChars(String text) {

		text = text.replace('�', 'e');
		text = text.replace('�', 'o');
		text = text.replace('�', 'a');
		text = text.replace('�', 's');
		text = text.replace('�', 'l');
		text = text.replace('�', 'z');
		text = text.replace('�', 'z');
		text = text.replace('�', 'c');
		text = text.replace('�', 'n');

		text = text.replace('�', 'E');
		text = text.replace('�', 'O');
		text = text.replace('�', 'A');
		text = text.replace('�', 'S');
		text = text.replace('�', 'L');
		text = text.replace('�', 'Z');
		text = text.replace('�', 'Z');
		text = text.replace('�', 'C');
		text = text.replace('�', 'N');
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
			return "Dodany 2 miesi�ce temu";

		return "Dodany ponad rok temu";

	}
}
