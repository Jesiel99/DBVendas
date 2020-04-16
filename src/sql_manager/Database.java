package sql_manager;

import sql.SQLConnection;
import sql_manager.DatabaseDAO;
import venda.Marca;

import java.sql.Connection;
import java.util.List;

public class Database<O> {
	
	
	private DatabaseDAO<O> database;
	
	public String getSchemma() {
		return database.getSchemma();
	}

	public void setSchemma(String schemma) {
		database.setSchemma(schemma);
	}

	public Database(SQLConnection connection) {
		database = new DatabaseDAO<O>(connection);
		setSchemma("");
	}
	
    public boolean insert(O object){
        if (exist(object) != true) {
            return database.insert(object);
        }
        return false;
    }
    
    public boolean delete(O object){
        return database.delete(object);
    }
    
    public boolean exist(O object){
        return database.exist(object);
    }
    
    
    public List<O> select(O object){
        return database.select(object);
    }
}