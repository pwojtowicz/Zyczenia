package pl.netplus.wishesbase.entities;

public class Favorite extends ModelBase {
	
	private String text="�yczenia dla ka�dego, uniwersalne i ponadczasowe wiersze i wierszyki � czyli ciep�e, mi�e s�owa, podnosz�ce na duchu, �yczenia idealne dla ka�dego i na ka�d� okazj�.";

	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public String getShortText() {
		return String.format("%s (...)", text.substring(0, 50).toString());
	}

}
