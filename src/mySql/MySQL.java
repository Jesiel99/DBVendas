package mySql;

import sql.SQL;

public class MySQL extends SQL {
	
	public MySQL(String SQL, String database, String user, String password) {
		super(SQL, database, user, password, new MySQLCommands());
	}
	


}
