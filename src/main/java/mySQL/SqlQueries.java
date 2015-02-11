package mySQL;

public class SqlQueries {

	public final static String ALL_TAGS_SQL = "SELECT name from tags where "
			+ "id in (SELECT distinct `taggings`.tag_id "
			+ "FROM `taggings`  WHERE `taggings`.`context` = 'tags');";

	public final static String ALL_TAGGED_TICKET_SQL = "SELECT * FROM tickets WHERE user_email IS NOT NULL";

	public final static String ALL_TAGS_FOR_TICKET = "SELECT `tags`.* FROM `tags` "
			+ "INNER JOIN `taggings` ON `tags`.`id` = `taggings`.`tag_id` "
			+ "WHERE `taggings`.`taggable_id` = %d AND "
			+ "`taggings`.`taggable_type` = 'Ticket' AND `taggings`.`context` = 'tags' ORDER BY taggings.id";
}
