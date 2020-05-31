package word_search_solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
	protected static ArrayList<String> readTextFileAsList(String fname) {
		ArrayList<String> lines = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null)
			{
				lines.add(currentLine);
			}
		}
		catch (IOException e)
		{
			lines.clear();
			lines.add("Error opening file: " + fname + System.lineSeparator());
			lines.add(e.getMessage());
		}
		
		return lines;
	}
	
	/**
	 * Read a text file
	 * @param fname File path and filename e.g. C:\\myfile.txt
	 * @return
	 */
	protected static String readTextFile(String fname)
	{
		StringBuffer lines = new StringBuffer();
		try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null)
			{
				lines.append(currentLine);
			}
		}
		catch (IOException e)
		{
			lines.append(e.getMessage());
		}
		return lines.toString();
	}
}
