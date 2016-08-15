package teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ExtractData {

	
	public static void main(String[] args) {
		Map<String, String> map = Util.readProperties();
		
		Connection conexao = Connector.getConnection(map.get(Constants.DRIVER), map.get(Constants.URL), map.get(Constants.USER), map.get(Constants.PASSWORD));
		
		ImportData data = new ImportData();
		List<List<? extends Object>> list = data.importDataFromFile(map.get(Constants.FILE));
		
		AccessDB dt = new AccessDB();
		if(list != null && !list.isEmpty()){
			try {
				dt.insert(conexao, list, map.get(Constants.TABLE));
				System.out.println(Constants.IMPORT_SUCESSO);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}
}
