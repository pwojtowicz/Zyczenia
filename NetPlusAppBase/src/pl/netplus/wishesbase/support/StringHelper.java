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

		if (days <= 0)
			return " dzisiaj";
		if (days == 1)
			return " wczoraj";

		if (days > 1 && days < 30)
			return String.format(" %d dni temu", days);

		if (days >= 30 && days < 365) {
			int months = (int) (days / 30);
			String name = "miesi�c";
			if (months == 1)
				return String.format(" %s temu", name);
			if (months > 1 && months < 5)
				name = "miesiące";
			if (months >= 5)
				name = "miesięcy";
			return String.format(" %d %s temu", months, name);
		}

		return " ponad rok temu";

	}
}
