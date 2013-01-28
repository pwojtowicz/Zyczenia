package pl.netplus.wishesbase.entities;

public class Favorite extends ModelBase {
	
	private String text="¯yczenia dla ka¿dego, uniwersalne i ponadczasowe wiersze i wierszyki – czyli ciep³e, mi³e s³owa, podnosz¹ce na duchu, ¿yczenia idealne dla ka¿dego i na ka¿d¹ okazjê.";

	
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
