package bak85_SpotifyKnockoff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ErrorLogger {

	public static void log(String errorMessage) {

		try{
			FileWriter writer = new FileWriter("errorlog.txt", true);
			BufferedWriter bw = new BufferedWriter(writer);
			PrintWriter out = new PrintWriter(bw);	

			Date currentDate = new Date();
			out.println(errorMessage + " , " + currentDate.toString());
			out.close();
			
		} catch (IOException e) {

		}
		
	}
	
}
