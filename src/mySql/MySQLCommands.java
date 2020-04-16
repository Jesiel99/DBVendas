package mySql;

import sql.SQLCommands;
import sql.SQLFormat;

public class MySQLCommands extends SQLFormat implements SQLCommands  {
	

	@Override
	public String insert(String table, String columns[], String values[]) {
		String sColumns = putCollons(columns);
		values = putQuotes(values);
		String sValues = putCollons(values);
		return "insert into " + table + "(" + sColumns + ") values (" + sValues + ");";
	}

	@Override
	public String delete(String table, String columns[], String[] values) {
		String conditions = createAndConditions(columns, values);
		return "delete from " + table + " where " + conditions + ";";
	}

	@Override
	public String exist(String table, String[] columns, String[] values) {
		String conditions = createAndConditions(columns, values);
		return "select 1 from " + table + " where " + conditions + ";";
	}

	private String createAndConditions(String[] columns, String[] values) {
		values = putQuotes(values);
		String conditions = columns[0] + " = " + values[0];
		for (int i = 1; i < values.length; i++) {
			conditions += " and " + columns[i] + " = " + values[i] ;
		}
		return conditions;
	}
	
	@Override
	public String select(String table) {
		return "select * from " + table + ";";
	}

	@Override
	public String getIdName(String table) {
		return "SELECT k.column_name\n" + 
				"FROM information_schema.table_constraints t\n" + 
				"JOIN information_schema.key_column_usage k\n" + 
				"USING(constraint_name,table_schema,table_name)\n" + 
				"WHERE t.constraint_type='PRIMARY KEY'\n" + 
				"  AND t.table_schema='mvcsimplesjdbc'\n" + 
				"  AND t.table_name='marca';\n";
	}
	
}
