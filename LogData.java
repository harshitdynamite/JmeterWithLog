package StressTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;

public class LogData {
	
	public static String logFileName;
	FileWriter fileWriter ;
	
	public LogData()
	{
		
	}
	
	public LogData(String logFileName)
	{
		LogData.logFileName = logFileName;
	}
	
	public void InitLog() throws IOException
	{
		Date date = new Date();
		long currentTimeStamp = date.getTime();
		try 
		{
			if(LogData.logFileName.isBlank() || LogData.logFileName.isEmpty())
			{
				throw new NullPointerException("Give logFileName is empty");
			}
			
			File myObj = new File(FilenameUtils.removeExtension(LogData.logFileName) + currentTimeStamp + "." + FilenameUtils.getExtension(LogData.logFileName));
			
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
			
	}
	
	public void InsertLog(List<LogDTO> log) throws IOException
	{
		try {
			 fileWriter = new FileWriter(LogData.logFileName);
			  Gson gson = new Gson(); 
			  if(log != null)
			  {
				  String json = gson.toJson(log);
				  fileWriter.write(json);
			  }
			  
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			if(this.fileWriter != null)
			{
				this.fileWriter.close();
				//this.fileWriter.flush();
			}
		}
	}

}
