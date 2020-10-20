package StressTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadCsv {

	public static HashMap<Integer, HashMap<String,String>> csvData = new HashMap<Integer, HashMap<String,String>>();
	public static List<String[]> allRows;
	public static HashMap<String,String> thisRowData = new HashMap<String,String>();
	public static ConfigHelper configHelper ;

	public static void ReadTestDataCsv()  throws IOException {
		
		configHelper = ConfigHelper.getInstance();
		fillHashMap(configHelper.CsvFileName);

	}


	public static void fillHashMap(String fileName) throws IOException
	{
		allRows = readAllRows(fileName);
		int noOfCols = allRows.get(0).length ;
		String[] colArr = new String[noOfCols];

		for(int i =0;i<noOfCols;i++)
		{
			colArr[i] = allRows.get(0)[i];
		}


		for(int j=1;j<allRows.size();j++)
		{
			HashMap<String,String> dataForEachRow = new HashMap<String,String>();
			String[] row = allRows.get(j);
			for(int k =0; k<row.length;k++)
			{

				dataForEachRow.put(colArr[k], row[k]);
			}
			csvData.putIfAbsent(j-1, dataForEachRow);
		}




	}

	//Reads all rows into an array List
	public static List<String[]> readAllRows(String fileName) throws IOException { 
		int count = 0;
		List<String[]> content = new ArrayList<>();
		Charset charset = StandardCharsets.UTF_8;
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				content.add(line.split(","));
			}
		} catch (FileNotFoundException e) {
			//Some error logging
		}
		return content;
	}

}
