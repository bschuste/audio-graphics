package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.*;

public class Model {

    // Retrieve the data from one or two sets
    // Read log file and extract fields
    private String logPathName = "/Users/brigitte/Documents/Customers/Brilliant/audio/";
    private String primaryFileName = "jitter-buffer-50ms-receiver.log";
    private String secondaryFileName = "jitter-buffer-50ms-sender.log";
    private String paramName = "pts";
    private String outputFileName;


    public Model(String logPathName, String primaryFileName, String logFileNameSender, String paramName) {
        if (!logPathName.isEmpty()) {
            this.logPathName = logPathName;
        }
        if (!primaryFileName.isEmpty()) {
            this.primaryFileName = primaryFileName;
        }
        if (!secondaryFileName.isEmpty()) {
            this.secondaryFileName = logFileNameSender;
        }
        if (!paramName.isEmpty()) {
            this.paramName = paramName;
        }
    }

    public boolean retrieveData(String outputFileName) throws IOException {

        FileReader fileReader;
        FileWriter fileWriter;
        BufferedReader reader;
        String deLimiter = "[ =]+";
        String fileWriterName = logPathName + "filteredData.txt";
        String timeRegex = "^(\\d\\d:\\d\\d:\\d\\d:\\d\\d\\d\\d\\d\\d\\d\\d\\d)";

        fileReader = new FileReader(logPathName + primaryFileName);
        reader = new BufferedReader(fileReader);

        if (outputFileName.isEmpty()) {
            fileWriterName = logPathName + primaryFileName + "-" + paramName;
        }
        fileWriter = new FileWriter(fileWriterName);

        String line = reader.readLine();;

        while (line != null) {

            if (line.contains(paramName+"=") || line.contains(paramName+" =") || line.contains(paramName+" ") || line.contains(paramName+"  ")) {
                System.out.print(line.indexOf(paramName) + " ");

                String[] tokens = line.split(deLimiter);
                for (String text : tokens) {
                    if (text.equals(paramName)) {
                        System.out.print(text + " ");
                    }
                }
                System.out.printf("%n");
                System.out.println(line);
            }
            line = reader.readLine();
        }

        reader.close();
        fileReader.close();
        fileWriter.close();

        return true;
    }

}
