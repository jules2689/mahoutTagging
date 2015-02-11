

import java.sql.SQLException;
import java.util.Iterator;

import org.apache.mahout.math.Vector.Element;

import mahout.Vectorizer;
import models.Ticket;
import mySQL.MySQLReader;

public class run {

	public static void main(String[] args) {
		MySQLReader reader = new MySQLReader();
		reader.connectToDatabase();

		Ticket[] tickets = null;
		String[] allTags = null;
		try {
			tickets = reader.getTaggedTickets();
			allTags = reader.getAllTags();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reader.disconnectFromDatabase();
		}

		Vectorizer vectorizer = new Vectorizer(tickets, allTags);
		vectorizer.vectorizeTicketTags();
		printVectorInfo(1,tickets,allTags);
	}

	public static void printVectorInfo(int idx, Ticket[] tickets, String[] allTags) {
		Ticket ticket = tickets[idx];

		System.out.println("\nTicket #" + ticket.rowId + " has the following tags.");
		for (String tag : ticket.getTagsArray()) {
			System.out.println(tag);
		}

		System.out.println("");

		Iterator<Element> vectorIterator = ticket.featureVector.all().iterator();
		while (vectorIterator.hasNext()) {
			Element element = vectorIterator.next();
			System.out.printf("%d. %s -> %f\n", element.index(), allTags[element.index()], element.get());
		}
	}

}
