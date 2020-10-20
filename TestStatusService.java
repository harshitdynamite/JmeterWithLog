package StressTest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.windows.WindowsDriver;
import junit.framework.TestCase;

public class TestStatusService  {
	
	public static WindowsDriver stressSession = null;
	public static WindowsDriver desktopSession = null;
	LogData logData = null;
	public LogDTO log= null;
	public List<LogDTO> logDataList= null;
	Date date;
	public String Rows = "1,2";
	public static ConfigHelper configHelper ;
	
	
	public TestStatusService()
	{
		configHelper = ConfigHelper.getInstance();
		logData = new LogData(configHelper.StressTestingLogFileName);
		date = new Date();
		logDataList = new ArrayList<LogDTO>();
	}
	
	//public TestStatusService(String abc) {
		//System.out.println("abc=" + abc);
	//}


	@Before
	public void setUp() { try {

		//configHelper = ConfigHelper.getInstance();

		DesiredCapabilities guiCapabilities1 = new DesiredCapabilities();
		guiCapabilities1.setCapability("app",configHelper.StressTestExeLocation); 
		stressSession = new	WindowsDriver(new URL(configHelper.WindowsDriverAddress), guiCapabilities1);
		stressSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		ArrayList<String> list = new ArrayList<String>(stressSession.getWindowHandles());
		stressSession.switchTo().window(list.get(0));

	} catch (Exception e) { e.printStackTrace(); } 
	
	}


	@Test
	public void sendingWallBoardMessage() throws InterruptedException, IOException {

		String[] inputRows=Rows.split(",");
		try {
		


		ReadCsv.ReadTestDataCsv();
		
		if(ReadCsv.csvData.size() < 0) return;
		for(int i =0;i< inputRows.length;i++)
		{
			log = new LogDTO();
			HashMap<String,String> thisRow = ReadCsv.csvData.get(Integer.parseInt(inputRows[i])-1);
			log.csvRowNo = thisRow.get("Id");
			log.msgType = thisRow.get("RxNotification");
			
			if(thisRow.size() < 0) continue;
			stressSession.findElementByXPath("//*[@Name='StatusTx']/following-sibling::*[2]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusTx']/following-sibling::*[2]").sendKeys(thisRow.get("Server") != null ? thisRow.get("Server") :"" );
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[1]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[1]").sendKeys("Op");
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[2]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[2]").sendKeys(thisRow.get("RxClientToCreate"));
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[3]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[3]").sendKeys(thisRow.get("RxNotification"));
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[4]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[4]").sendKeys(thisRow.get("TxWorkStationNamePrefix"));
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[3]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[3]").sendKeys(thisRow.get("TxClientsToCreate"));
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[7]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[7]").sendKeys(thisRow.get("TxNoOfUpdates"));
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[1]").clear();
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[1]").sendKeys(thisRow.get("TxStatusMessage"));
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[2]").clear();
			String modifiedPayloadVal = thisRow.get("Payload") == null ? "" : thisRow.get("Payload").replaceAll("\"\"", "\"");
			stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[2]").sendKeys(modifiedPayloadVal);
			stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[4]").click();
			 stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[6]").click();
			 stressSession.findElementByXPath("//*[@Name='StatusRx']/child::*[5]").click();
			 stressSession.findElementByXPath("//*[@Name='StatusTx']/child::*[5]").click();
			 log.logTime = this.date.getTime(); 
			 
			 logDataList.add(log);
			 
			 			
		}
		logData.InsertLog(logDataList);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}


	@After
	public void tearDown() { int maxattempt = 3; int attempt = 1; try { do {
		if(stressSession != null) {

			stressSession.closeApp(); stressSession.quit(); stressSession = null; break;
		} else { Thread.sleep(1000); } } while(attempt <= maxattempt); }
	catch(Exception ex) { ex.printStackTrace(); }



	}

}
