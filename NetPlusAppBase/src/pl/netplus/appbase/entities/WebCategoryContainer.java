package pl.netplus.appbase.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class WebCategoryContainer extends WebObjectContener {

	@JsonProperty("Items")
	public Category[] items;

}
