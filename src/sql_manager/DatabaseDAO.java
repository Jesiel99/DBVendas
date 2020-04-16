package sql_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sql.SQLCommands;
import sql.SQLConnection;
import venda.Marca;
import sql.SQL;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatabaseDAO<O> {

	private final SQLConnection SQL_CONNECTION;
	private SQLCommands commands;
	private String schemma;
	
	

	public String getSchemma() {
		return schemma;
	}

	public void setSchemma(String schemma) {
		this.schemma = schemma + ".";
	}

	public DatabaseDAO(SQLConnection SQLconnection) {
		this.SQL_CONNECTION = SQLconnection;
		this.commands = SQLconnection.sql.commands;
	}

	public boolean insert(O object) {
		try {
			String table = getLastNameOfClass(object);
			String[] columns = getNameOfNotIdColumns(object);
			String[] values = getFieldsValues(object);
			// Create insert string from object attributes
			String insert = commands.insert(schemma + table, columns, values);
			Connection conn = this.SQL_CONNECTION.connect();
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.executeUpdate();
			ps.close();
			conn.close();
			System.out.println(insert);
			return true;
		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(O object) {
		try {

			String table = getLastNameOfClass(object);
			String[] columns = getNameOfNotIdColumns(object);
			String[] values = getFieldsValues(object);
			String delete = commands.delete(schemma + table, columns, values);
			Connection connection = SQL_CONNECTION.connect();
			PreparedStatement ps = connection.prepareStatement(delete);
			ps.executeUpdate();
			ps.close();
			connection.close();
			System.out.println(delete);
			return true;
		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
			e.printStackTrace();
			return false;
		}
	}

	public boolean exist(O object) {
		try {
			String table = getLastNameOfClass(object);
			String[] columns = getNameOfNotIdColumns(object);
			String[] values = getFieldsValues(object);
			String exist = commands.exist(schemma + table, columns, values);
			Connection conn = SQL_CONNECTION.connect();
			PreparedStatement ps = conn.prepareStatement(exist);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ps.close();
				rs.close();
				conn.close();
				System.out.println(exist);
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public List<O> select(O object) {
		try {
			String select = commands.select(schemma + getLastNameOfClass(object));
			Connection conn = this.SQL_CONNECTION.connect();
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			List<O> listObj = getList(rs, object);
			System.out.println(select);
			return listObj;
		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<O> getList(ResultSet rs, O object) {
		List<O> list = new ArrayList<O>();
		try {
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i);
					String secondMethodName = name.substring(0, 1).toUpperCase() + name.substring(1);
					Method get = object.getClass().getMethod("get" + secondMethodName);
					System.out.println(get.getReturnType());
					Method set = object.getClass().getMethod("set" + secondMethodName, get.getReturnType());
					set.invoke(object, rs.getObject(i));
				}

//				 methods[i].invoke(object, rs.getInt(1));
//				Method[] methods = object.getClass().getDeclaredMethods();
//				for (Method method : methods) {
//					if (method.getName().matches("set.*")) {
//						for (int i = 0; i < methods.length; i++) {

//							object.setCodigo(rs.getInt(1));
//							object.setDescricao(rs.getString(2));
//						}
//					}
//				}
				list.add(object);
			}
			return list;
		} catch (

		Exception e) {
			System.err.println("Erro: " + e.toString());
			e.printStackTrace();
			return null;
		}
	}

	private String getIdName(String tbName) {
		try {
			String sql = commands.getIdName(tbName);
			Connection conn = this.SQL_CONNECTION.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String id = rs.getString(1);
				ps.close();
				rs.close();
				conn.close();
				return id;
			}
		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private String getLastNameOfClass(O object) {
		String table = object.getClass().getName().toLowerCase();
		String[] tableNames = table.split("\\.");
		return tableNames[tableNames.length - 1];
	}

	private String[] getNameOfNotIdColumns(O object) {
		// Bring methods from object
		Method[] methods = object.getClass().getMethods();
		// Bring the names of columns retrieved from those methods
		List<String> columns = new ArrayList<String>();
		// Get Name of class and assume that it is also a table
		String table = getLastNameOfClass(object);
		for (Method method : methods) {
			if (method.getName().matches("get.*") && !method.getName().matches("getClass") &&
			// Verify if is not an id attribute
					!method.getName().matches(
							"get" + getIdName(table).substring(0, 1).toUpperCase() + getIdName(table).substring(1))) {
				columns.add(method.getName().substring(3).toLowerCase());
			}
		}
		return columns.toArray(new String[columns.size()]);
	}

//	private String getIdFromMethod(O object, String pkName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Method method = object.getClass().getDeclaredMethod("get" + pkName.substring(0, 1).toUpperCase() + pkName.substring(1));  
//        return String.valueOf(method.invoke(object)); 
//	}

	private String[] getFieldsValues(O object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Bring methods from object
		Method[] methods = object.getClass().getMethods();
		// Bring to lists values retrieved from those methods
		List<String> values = new ArrayList<String>();
		// Get Name of class and assume that it is also a table
		String table = getLastNameOfClass(object);
		// filter values
		for (Method method : methods) {
			if (method.getName().matches("get.*") && !method.getName().matches("getClass") &&
			// Verify if is not an id attribute
					!method.getName().matches(
							"get" + getIdName(table).substring(0, 1).toUpperCase() + getIdName(table).substring(1))) {
				System.out.println(method.getName());
				values.add(String.valueOf(method.invoke(object)));
			}
		}
		return values.toArray(new String[values.size()]);
	}

}