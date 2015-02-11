package models;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.mahout.math.Vector;

public class Ticket {
	public int rowId;
	public int ticketId;
	public String myShopifyURL;
	public String subject;
	public String description;
	public Date zCreatedAt;
	private ArrayList<String> tags;
	public Vector featureVector;

	public Ticket(int rowId, int ticketId, String myShopifyURL, String subject, String description, Date zCreatedAt, String[] tags) {
		this.rowId = rowId;
		this.ticketId = ticketId;
		this.myShopifyURL = myShopifyURL;
		this.subject = subject;
		this.description = description;
		this.zCreatedAt = zCreatedAt;
		this.tags = new ArrayList<String>();
		if (tags != null) {
			for (String tag : tags) {
				addTag(tag);
			}
		}
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public String[] getTagsArray() {
		return tags.toArray(new String[tags.size()]);
	}

	public void addTags(String[] tags) {
		if (tags != null) {
			for (String tag : tags) {
				this.tags.add(tag);
			}
		}
	}

	public void addTag(String tag) {
		this.tags.add(tag);
	}

}
