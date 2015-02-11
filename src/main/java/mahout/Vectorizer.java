package mahout;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import models.Ticket;

public class Vectorizer {
	private Ticket[] tickets;
	private String[] tags;

	public Vectorizer(Ticket[] tickets, String[] tags) {
		this.tickets = tickets;
		this.tags = tags;
	}

	public void vectorizeTicketTags() {
		for (Ticket ticket : this.tickets) {
			Vector vector = new DenseVector(this.tags.length);
			for (int i = 0; i < this.tags.length; i++) {
				String tag = this.tags[i];
				double value = ticket.getTags().contains(tag) ? 1.0 : 0.0;
				vector.set(i,  value);
			}
			ticket.featureVector = vector;
		}		
	}

}
