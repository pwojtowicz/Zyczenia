package pl.netplus.appbase.entities;

public class ContentObject extends ModelBase {

	private String text;
	private boolean isFavorites;
	private double rating;

	public ContentObject(int id, String value, double rating,
			boolean isFavorites) {
		super.setId(id);
		this.text = value;
		this.rating = rating;
		this.isFavorites = isFavorites;
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
