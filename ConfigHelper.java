package StressTest;

import java.io.FileNotFoundException;
import java.util.Properties;
import java.io.FileInputStream;

public class ConfigHelper {

	private static ConfigHelper configHelper = null; 
	FileInputStream  inputStream;
	
	private ConfigHelper()
    { 
		Properties prop = new Properties();
		try {
			
			String propFileName = System.getProperty("user.dir") + "\\bin\\StressTest\\config.properties";
			inputStream = new FileInputStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			CsvFileName = prop.getProperty("FileName");
			StressTestExeLocation = prop.getProperty("StressTestExeLocation");
			WindowsDriverAddress = prop.getProperty("WindowsDriverAddress");
			StressTestingLogFileName = "StressTestingJarLogs.txt";//prop.getProperty("StressTestingLogFileName");
		}
		catch(Exception ex) 
		{
			
			
		}
		
        
    } 
	
	public static ConfigHelper getInstance() 
    { 
        if (configHelper == null) 
        	configHelper = new ConfigHelper(); 
  
        return configHelper; 
    } 

	public String CsvFileName;
	public String StressTestExeLocation;
	public String WindowsDriverAddress;
	public String StressTestingLogFileName;
}
