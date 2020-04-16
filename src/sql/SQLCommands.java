package sql;

public interface SQLCommands {
	

	
	public String insert(String table, String[] columns, String[] values);
	
	public String delete(String table, String columns[], String values[]);
	
	public String exist(String table, String columns[], String values[]);
	
	public String select(String table);
	
	public String getIdName(String table);
	

}
