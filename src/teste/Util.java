package teste;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {


	public static java.sql.Date formataData(String data) {   
		 java.sql.Date sql = null;
		 
		 if(data != null && "".equals(data)){
			 SimpleDateFormat format = new SimpleDateFormat(Constants.DD_MM_YY);
		        Date parsed = null;
				try {
					parsed = format.parse(data);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				}
		        sql = new java.sql.Date(parsed.getTime());
		 }
		 return sql;
	 }
	 
	 
	public static Timestamp convertTimeStamp(String date){

		Timestamp timestamp = null;
		if(date != null && !"".equals(date)){
			try{
				date = date.replaceAll("%", " ");
			    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DD_MM_YY_HH_MM_SS);
			    Date parsedDate = dateFormat.parse(date);
			    timestamp = new java.sql.Timestamp(parsedDate.getTime());
			}catch(Exception e){//this generic but you can control another types of exception
				System.out.println(e.getMessage());
			}
		}
		
		return timestamp;
	}
	
	public static String clearString(String value){
		String aux = value.replaceAll("%", " ");
		return aux;
	}
	
	public static Map<String, String> readProperties(){
		Map<String, String> map = new HashMap<String, String>();
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			
			input = loader.getResourceAsStream("config.properties");
			
			prop.load(input);
			map.put(Constants.DRIVER, prop.getProperty(Constants.DRIVER));
			map.put(Constants.URL, prop.getProperty(Constants.URL));
			map.put(Constants.USER, prop.getProperty(Constants.USER));
			map.put(Constants.PASSWORD, prop.getProperty(Constants.PASSWORD));
			map.put(Constants.TABLE, prop.getProperty(Constants.TABLE));
			map.put(Constants.FILE, prop.getProperty(Constants.FILE));
			
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return map;
	}
}

