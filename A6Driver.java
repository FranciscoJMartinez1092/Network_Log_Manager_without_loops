

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.IntStream;

	public class A6Driver {

	    private static final StringBuilder separator = new StringBuilder();

	    /*
	     * use IntStream to replace the for loop.
	     * I iterate from 0 to 109 inclusive and for each iteration
	     * I append '-' to the string builder variable
	     */
	    
	    static {
	    	IntStream.rangeClosed(0, 109).forEach(i -> separator.append('-'));
	        
	            
	    }

	    public static void main(String[] args) throws ParseException,  FileNotFoundException {

	        NetworkLogManager networkLogManager = new NetworkLogManager();

	        System.out.println(networkLogManager);

	        if (networkLogManager.loadFile("Network.log")) {

	            System.out.println(separator.toString());

	            System.out.println(networkLogManager);

	            System.out.println(separator.toString());

	            System.out.printf("There are %d records with id 1%n", networkLogManager.searchById("1").size());
	            System.out.printf("There are %d records with id 9%n", networkLogManager.searchById("9").size());
	            System.out.printf("There are %d records with protocol TCP %n", networkLogManager.searchByProtocol("TCP").size());
	            System.out.printf("There are %d records with protocol UDP %n", networkLogManager.searchByProtocol("UDP").size());

	            System.out.println(separator.toString());

	            List<LogEntry> list = networkLogManager.searchByRange("Jan 1 2018 00:00:00", "Dec 31 2018 23:59:59");

	            System.out.printf(String.format("There are %,d entries from 2018", list.size()));
	        }
	        else
	            System.err.println("Failed to load file");
	    }
	}

