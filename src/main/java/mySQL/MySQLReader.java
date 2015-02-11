package mySQL;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Ticket;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.mysql.jdbc.*;

public class MySQLReader {

	final private String MY_SQL_JDBC_URL = "jdbc:mysql://localhost:3306/ZendeskTagger_dev";
	final private String MY_SQL_USER = "root";
	final private String MY_SQL_PASSWORD = "";
	private Connection connection;

	public void connectToDatabase() {
		try {
			System.out.println("Connecting database...");
			connection = (Connection) DriverManager.getConnection(MY_SQL_JDBC_URL, MY_SQL_USER, MY_SQL_PASSWORD);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new RuntimeException("Cannot connect the database!", e);
		}
	}

	public void disconnectFromDatabase() {
		try {
			System.out.println("Closing database connection.");
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Ticket[] getTaggedTickets() throws SQLException{
		System.out.println("Fetching all Ticket data.");
		QueryRunner run = new QueryRunner();
		Ticket[] result = run.query(this.connection, SqlQueries.ALL_TAGGED_TICKET_SQL, getTicketResultHandler());
		return result;
	}

	public String[] getAllTags() {
		System.out.println("Fetching all tag data.");
		QueryRunner run = new QueryRunner();
		String[] result = null;

		try {
			result = run.query(this.connection, SqlQueries.ALL_TAGS_SQL, getTagResultHandler());
		} catch (SQLException e) { e.printStackTrace(); }
		return result;
	}

	public void getTagsForTicket(Ticket ticket) {
		String sql = String.format(SqlQueries.ALL_TAGS_FOR_TICKET, ticket.rowId);
		QueryRunner run = new QueryRunner();
		String[] result = null;

		try {
			result = run.query(this.connection, sql, getTagResultHandler());
		} catch (SQLException e) { e.printStackTrace(); }
		ticket.addTags(result);
	}

	private ResultSetHandler<String[]> getTagResultHandler() {
		ResultSetHandler<String[]> handler = new ResultSetHandler<String[]>() {
			public String[] handle(ResultSet rs) throws SQLException {
				ArrayList<String> tags = new ArrayList<String>();
				while (rs.next()) {
					tags.add(rs.getString("name"));
				}
				return tags.toArray(new String[tags.size()]);
			}
		};
		return handler;
	}
	
	private ResultSetHandler<Ticket[]> getTicketResultHandler() {
		ResultSetHandler<Ticket[]> handler = new ResultSetHandler<Ticket[]>() {
			public Ticket[] handle(ResultSet rs) throws SQLException {
				ArrayList<Ticket> tickets = new ArrayList<Ticket>();
				while (rs.next()) {
					Ticket ticket = new Ticket( rs.getInt("id"),
							rs.getInt("ticket_id"),
							rs.getString("permanent_domain"),
							rs.getString("subject"),
							rs.getString("description"),
							rs.getDate("z_created_at"),
							null );
					getTagsForTicket(ticket);
					tickets.add(ticket);
				}
				return tickets.toArray(new Ticket[tickets.size()]);
			}
		};
		return handler;
	}
}
