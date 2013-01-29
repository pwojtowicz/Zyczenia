package pl.netplus.appbase.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class WebContentObjectContainer extends ContentObject {

	@JsonProperty("Items")
	public ContentObject[] items;
}
