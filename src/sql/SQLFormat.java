package sql;

public class SQLFormat {

	public String putCollons(String[] text) {
		String s = text[0];
		for (int i = 1; i < text.length; i++) {
			s += "," + text[i];
		}
		return s;
	}
	
	
	public String[] putQuotes(String[] text) {
		for (int i = 0; i < text.length; i++) {
			text[i] = "'" + text[i] + "'";
		}
		return text;
	}
}
