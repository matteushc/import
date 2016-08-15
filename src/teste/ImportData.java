package teste;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImportData {

	
	@SuppressWarnings("resource")
	public List<List<? extends Object>> importDataFromFile(String filePath){
		
		File file = new File(filePath);
		FileInputStream inputStream = null;
		
		List<List<? extends Object>> list = new ArrayList<List<? extends Object>>();
		
		try {
			inputStream = new FileInputStream(file);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
 
			String line = null;
			while ((line = reader.readLine()) != null) {

				String aux = line.replaceAll(" ", "%");
				aux = aux.replaceAll("\\s+", ";");
				aux = aux.replaceAll("[\"\']", "");
				
				String var[] = aux.split(";");
				List<Object> obj = new ArrayList<Object>();
				for (int i = 0; i < var.length; i++) {
					obj.add(var[i]);
				}
				list.add(obj);
			}
 
		} catch (IOException e) {
			System.out.println(Constants.ERRO_ABRIR_ARQUIVO);;
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
}
