package pacman.entries.pacman.EvolutionaryAlgorithm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DataManager 
{
	public static final String DIRECTORY="myData/";
	
	/**
	 * Save CSV File in the specified directory (It must exist in the project)
	 * @param filename Name of file to be created/written on.
	 * @param content Content of the csv filled with the generational statistics
	 * @param append If you wish to append to an existing file or override it
	 * @return true if it could create and save the file, false otherwise
	 */
	public static boolean SaveCSVFile(String filename, String content, boolean append)
	{
		 try 
        {
            FileOutputStream outS=new FileOutputStream(DIRECTORY+filename,append);
            PrintWriter pw=new PrintWriter(outS);

            pw.println(content);
            pw.flush();
            outS.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        
        return true;
	}
}
