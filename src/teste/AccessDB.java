package teste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

public class AccessDB {
	
	
	public void insert(Connection conexao, List<List<? extends Object>> list, String table) throws SQLException {
		
		//Get columns from file - First object in the list
		String columns = getColumnsFile(list);
		String parameters = getValuesParameters(list.get(0).size());
		
		List<Integer> typesColumns = select(conexao, columns, table);
		String insertTableSQL = "INSERT INTO "+ table + " " + columns + " VALUES " + parameters;
		PreparedStatement pstmt = null;

		try{
			//Get values from file - Second object in the list
			for (int i = 1; i < list.size(); i++) {
				try {
				pstmt = conexao.prepareStatement(insertTableSQL);
				
				for (int j = 0; j < list.get(i).size(); j++) {
					setParametersToStatement(typesColumns, list.get(i), pstmt);
				}
				// execute insert SQL
				pstmt.executeUpdate();
				
				} catch (SQLException e) {
					conexao.rollback();
					System.out.println(e.getMessage());
				} 
			}
		
		}finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conexao != null) {
				conexao.close();
			}
		}

	}

	public List<Integer> select(Connection conexao, String columns, String table){
		
		columns = columns.replace("(", "");
		columns = columns.replace(")", "");
		
		List<Integer> listTypes = new ArrayList<Integer>();
		
		Statement statement = null;
		String query = "SELECT " + columns + " FROM "+ table + " WHERE rownum = 1";
		
		try {
			statement = conexao.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			ResultSetMetaData rsmd = resultSet.getMetaData();
			 for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				
	            Integer type = rsmd.getColumnType(i);
	            listTypes.add(type);
	            
		     }
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return listTypes;
	}
	
	public PreparedStatement setParametersToStatement(List<Integer> types, List<? extends Object> list, PreparedStatement state) throws SQLException {
		
		int parameterIndex = 1;
		for (int i = 0; i < types.size(); i++) {
			Integer key = types.get(i);
			switch (key) {
			case Types.VARCHAR:
				state.setString(parameterIndex, Util.clearString(list.get(i).toString()));
				break;
			case Types.CHAR:
				state.setString(parameterIndex, Util.clearString(list.get(i).toString()));
				break;
			case Types.DATE:
				 state.setDate(parameterIndex, Util.formataData(list.get(i).toString()));
				break;
			case Types.INTEGER:
				state.setInt(parameterIndex, (Integer) list.get(i));
				break;
			case Types.DOUBLE:
				 state.setDouble(parameterIndex, (Double) list.get(i));
				 break;
			case Types.NUMERIC:
				 state.setInt(parameterIndex, Integer.parseInt(list.get(i).toString()));
				 break;
			case Types.LONGVARCHAR:
				state.setLong(parameterIndex, Long.parseLong(list.get(i).toString()));
				break;
			case OracleTypes.TIMESTAMPLTZ:
				state.setTimestamp(parameterIndex, Util.convertTimeStamp(list.get(i).toString()));
				break;
			case OracleTypes.TIMESTAMPTZ:
				state.setTimestamp(parameterIndex, Util.convertTimeStamp(list.get(i).toString()));
				break;
			case Types.TIMESTAMP:
				state.setTimestamp(parameterIndex, Util.convertTimeStamp(list.get(i).toString()));
				break;
			default:
				break;
			}
			parameterIndex++;
		}
		return state;
	}
	
	private String getValuesParameters(int size) {
		String value = "(";
		int lastData = size - 1;
		for (int i = 0; i < size; i++) {
			if(i == lastData){
				value += "?";
			}else{
				value += "?" + ",";
			}
		}
		value += ")";
		return value;
	}
	
	private String getColumnsFile(List<List<? extends Object>> list) {
		String columns = "(";
		int lastData = list.get(0).size() - 1;
		
		for (int i = 0; i < list.get(0).size(); i++) {
			if(i == lastData){
				columns += (String) list.get(0).get(i);
			}else{
				columns += (String) list.get(0).get(i) + ",";
			}
		}
		columns += ")";
		return columns;
	}
}
