package pl.netplus.appbase.entities;

public class ContentObject extends ModelBase {

	private String text;
	private boolean isFavorites;
	private Double rating;

	public ContentObject(String value) {
		this.text = value;
	}

	public ContentObject() {

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isFavorites() {
		return isFavorites;
	}

	public void setFavorites(boolean isFavorites) {
		this.isFavorites = isFavorites;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
