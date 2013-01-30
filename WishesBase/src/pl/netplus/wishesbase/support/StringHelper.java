package pl.netplus.wishesbase.support;

public class StringHelper {

	public static String removePolishChars(String text) {

		text = text.replace('Í', 'e');
		text = text.replace('Û', 'o');
		text = text.replace('π', 'a');
		text = text.replace('ú', 's');
		text = text.replace('≥', 'l');
		text = text.replace('ø', 'z');
		text = text.replace('ü', 'z');
		text = text.replace('Ê', 'c');
		text = text.replace('Ò', 'n');

		text = text.replace(' ', 'E');
		text = text.replace('”', 'O');
		text = text.replace('•', 'A');
		text = text.replace('å', 'S');
		text = text.replace('£', 'L');
		text = text.replace('Ø', 'Z');
		text = text.replace('è', 'Z');
		text = text.replace('∆', 'C');
		text = text.replace('—', 'N');
		return text;
	}
}
