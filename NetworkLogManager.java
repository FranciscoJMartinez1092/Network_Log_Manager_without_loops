
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.stream.Stream;

public class NetworkLogManager {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyy HH:mm:ss");

    private final ArrayList<LogEntry> listLogEntries;

    public enum SearchField {
        ID, TIMESTAMP, SOURCE, DESTINATION, PROTOCOL, LENGTH, DESCRIPTION
    }

    public NetworkLogManager() {

        this.listLogEntries = new ArrayList<LogEntry>();
    }

    public boolean loadFile(String fileName)   {

        /*
         * Place a try and catch block for reading the file
         * I create a stream variable that reads from the file given as an argument as has the lines as elements
         * For each line of the file I create a string array made of the strings parsed by ","
         * For each line I create a try and catch block to see if there are duplicate log entries
         * and if not I add I add it to logEntries object
         */
    	
    	Stream<String> stream = null;
        try {
        	 stream = Files.lines(Paths.get(fileName));
            		
            

            
        	 

             stream.forEach((line) -> {String arr[] = line.split(",");
             try {

                 if (!listLogEntries.add(new LogEntry(arr[0].trim(), arr[1].trim(), arr[2].trim(), arr[3].trim(), arr[4].trim(), arr[5].trim(), arr[6].trim())))
                     System.out.printf("Skipping line: %s%n", line);
             }
             catch (IllegalArgumentException ex) {
                 System.out.printf("Skipping line: %s%n", line);
             }
             }
         );
     }
     catch (FileNotFoundException fnfe) {

         return false;
     } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    
       
        return true;
    }

    public String toString() {

        return String.format("NetworkLogManager: there are %,d valid records", listLogEntries.size());
    }

    public List<LogEntry> searchById(String str) {

        return searchBy(str, SearchField.ID);
    }

    public List<LogEntry> searchByTimestamp(String str) {

        return searchBy(str, SearchField.TIMESTAMP);
    }

    public List<LogEntry> searchBySource(String str) {

        return searchBy(str, SearchField.SOURCE);
    }

    public List<LogEntry> searchByDestination(String str) {

        return searchBy(str, SearchField.DESTINATION);
    }

    public List<LogEntry> searchByProtocol(String str) {

        return searchBy(str, SearchField.PROTOCOL);
    }

    public List<LogEntry> searchByLength(String str) {

        return searchBy(str, SearchField.LENGTH);
    }

    public List<LogEntry> searchByDescription(String str) {

        return searchBy(str, SearchField.DESCRIPTION);
    }

    public List<LogEntry> searchByRange(String fromDate, String toDate) throws ParseException {

        /*
         * Convert listLogEntries into a stream of Log Entries and than use for each to iterate each log entry
         * for each log entry you check whether the timestamp is between 0 and greater and then add it to relist if True
         */
        List<LogEntry> retList = new ArrayList<LogEntry>();

        
            Date from = formatter.parse(fromDate);
            Date to = formatter.parse(toDate);

             listLogEntries.stream().forEach(logEnt -> {Date timestamp;
			try {
				timestamp = formatter.parse(logEnt.getTimestamp());
			

             if (timestamp.compareTo(from) >= 0 && timestamp.compareTo(to) <= 0)
                 retList.add(logEnt);}
			catch (ParseException pe) {

	            pe.printStackTrace();}} );
                
            
        
        

        return retList;
    }

    private List<LogEntry> searchBy(String searchVal, SearchField field) {

        /*
         * convert listLogEntry to stream and then for each logEntry search for the field and compare it to the field 
         * in search. If they are the same add it to the return list
         * Then return the return list
         */
        List<LogEntry> retList = new ArrayList<LogEntry>();
        listLogEntries.stream().forEach(logEnt -> {

            String logEntryValue = getSearchValue(logEnt, field);

            if (logEntryValue.equals(searchVal))
                retList.add(logEnt);
        });

        return retList;
    }

    private String getSearchValue(LogEntry logEnt, SearchField field) {

        switch (field) {
            case ID:
                return logEnt.getId();
            case TIMESTAMP:
                return logEnt.getTimestamp();
            case SOURCE:
                return logEnt.getSource();
            case DESTINATION:
                return logEnt.getDestination();
            case DESCRIPTION:
                return logEnt.getDescription();
            case PROTOCOL:
                return logEnt.getProtocol();
            case LENGTH:
                return logEnt.getLength();
            default:
                return null;
        }
    }
}