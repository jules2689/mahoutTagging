package models;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.mahout.math.Vector;
import org.tartarus.snowball.ext.EnglishStemmer;

public class Ticket {
	public int rowId;
	public int ticketId;
	public String myShopifyURL;
	public String subject;
	public Date zCreatedAt;
	public Vector featureVector;
	
	public String description;
	public String processedDescription;
	
	private ArrayList<String> tags;

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
		
		preprocessDescription();
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
	
	private void preprocessDescription() {
		String preprocessedText = this.description.replaceAll("\\n", " ").replaceAll("[^a-zA-Z ]", "").toLowerCase();
		preprocessedText = preprocessedText.replace("full chat transcript below all timestamps in utc timezone   ", ""); //Precedes every chat
		String stemmedText = "";
		
		// This will stem the text. EnglishStemmer has a set of default stop words. Will experiment with more later.
		for (String s : preprocessedText.split(" ")) {
			EnglishStemmer stemmer = new EnglishStemmer();
		    stemmer.setCurrent(s);
		    stemmer.stem();
		    if (stemmedText.length() > 0) {
		    	stemmedText += " " + new String(stemmer.getCurrentBuffer());
		    } else {
		    	stemmedText += new String(stemmer.getCurrentBuffer());
		    }
		}
	    
		this.processedDescription = stemmedText;
	}

}
