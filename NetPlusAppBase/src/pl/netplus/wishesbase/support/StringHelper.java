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
}
