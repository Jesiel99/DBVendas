package other;

import mySql.MySQL;
import sql.SQLConnection;
import sql_manager.Database;
import venda.Marca;

public class Main {

	public static void main(String[] args) {			
// Teste Alterar	
//		Conexao.conectar(Conexao.MYSQL, "mvcsimplesjdbc");
		MySQL psql = new MySQL("postgresql", "testdb", "jesiel", "1234");
		SQLConnection conn = new SQLConnection(psql);
		Database psqldb = new Database(conn);
		Marca marca = new Marca("new brand");
		psqldb.setSchemma("mvcsimplesjdbc");
//		psqldb.insert(marca);
		System.out.println(psqldb.select(new Marca()).toString());
//		System.out.println(mysqlmarca.exist(marca));
		
	}
	
}