package sample;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        BufferedWriter writer;
        String deLimiter = "[ =]+";
        String fileWriterName = logPathName + "filteredData.txt";
        String timeRegex = "(\\d:\\d\\d:\\d\\d.\\d\\d\\d\\d\\d\\d\\d\\d\\d)";
        // Create a pattern from regex
        Pattern pattern = Pattern.compile(timeRegex);
        Matcher matcher;

        fileReader = new FileReader(logPathName + primaryFileName);
        reader = new BufferedReader(fileReader);

        if (outputFileName.isEmpty()) {
            fileWriterName = logPathName + primaryFileName.substring(0, primaryFileName.length()-4) + "-" + paramName + ".log";
            System.out.printf("Output file name %s%n", fileWriterName);
        }
        fileWriter = new FileWriter(fileWriterName);
        writer = new BufferedWriter(fileWriter);

        String line = reader.readLine();
        while (line != null) {

            if (line.contains(paramName+"=") || line.contains(paramName+" =") || line.contains(paramName+" ") || line.contains(paramName+"  ")) {
                int count = 0;
                long system_ts;
                long durationInNano;
                System.out.println(line);
                //System.out.print(line.indexOf(paramName) + " ");
                // Create a matcher for the input String
                matcher = pattern.matcher(line);

                // Get the subsequence
                // using find() method
                //System.out.println(matcher.find());
                while(matcher.find()) {
                    System.out.printf("matcher: %s\n", matcher.group(1));
                    String time = "0" + matcher.group(1);

                    System.out.println("Time: " + time);
                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
                    LocalTime localTime = LocalTime.parse(time, formatter);
                    //System.out.println("localTime in nanoseconds:      " + localTime.toNanoOfDay());
                    System.out.println("localTime nanoseconds field:   " + localTime.getNano());
                }

//                boolean matches = matcher.matches();
//                System.out.print("matches ");
//                System.out.println(matches);

//                String[] tokens = line.split(paramName);
//                for (String text : tokens) {
//                    if (text.equals(paramName)) {
//                        System.out.print(text + " ");
//                    }
//                }
                writer.write(line + System.lineSeparator());
            }
            line = reader.readLine();
        }

        reader.close();
        fileReader.close();
        writer.close();
        fileWriter.close();

        return true;
    }

}
