package sql;

public class SQL {
	
	private String SQL, database, user, password;
	public SQLCommands commands;
	
	public SQL(String SQL, String database, String user, String password, SQLCommands commands) {
		setSQL(SQL);
		setDatabase(database);
		setUser(user);
		setPassword(password);
		this.commands = commands;
	}

//	public String delete(String tbName, String condition) {
//		//TO DO
//		return "delete from " + tbName + " where id = ?;";
//	}
//	
//	public String select(String tbName, String tbColumn) {
//		return "select " + tbColumn + " from " + tbName + ";";
//	}
//	
//	public String select(String tbName) {
//		return "select * from " + tbName + ";";
//	}
//	
//	public String update(String tbName, String tbColumn) {
//		return "update " + tbName + " SET " + tbColumn + " = ? WHERE codigo = ?;";
//	}
//


	public String getSQL() {
		return SQL;
	}

	public void setSQL(String sQL) {
		SQL = sQL;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "SQL [SQL=" + SQL + ", database=" + database + ", user=" + user + ", password=" + password + "]";
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
