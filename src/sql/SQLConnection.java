package sql;


import java.sql.DriverManager;
import java.sql.Connection;

public class SQLConnection {
	
//	private String SQL, database;
	
	public SQL sql;
	
	
    public SQLConnection(SQL sql) {
    	this.sql = sql;
    }


	public Connection connect() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:" + sql.getSQL() + "://localhost/" + sql.getDatabase() + "?characterEncoding=latin1&useConfigs=maxPerformance";
            System.out.println(url);
            return DriverManager.getConnection(url, sql.getUser(), sql.getPassword());
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }
    
}