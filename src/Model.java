import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {

    private String logPathName = "./resources/";
    private String paramName = "pts"; // Key for data extraction
    private List<String> fileNameList;
    private List<Long> summaryList;


    public Model() {
        fileNameList = new ArrayList<>(2);
        fileNameList.add("receiver.log");
        fileNameList.add("sender.log");
        summaryList = new ArrayList<>();
    }

    public Model(String logPathName, String logFileNameReceiver, String logFileNameSender, String paramName) {
        if (!logPathName.isEmpty()) {
            this.logPathName = logPathName;
        }
        if (!logFileNameReceiver.isEmpty()) {
            fileNameList.add(logFileNameReceiver);
        }
        if (!logFileNameSender.isEmpty()) {
            fileNameList.add(logFileNameSender);
        }
        if (!paramName.isEmpty()) {
            this.paramName = paramName;
        }
    }

    public long getNumOfEntries(int index) {

        return summaryList.get(index).longValue();
    }

    // Callback to add each retrieved data point
    public interface addData {
        void op(int index, long value);
    }

    public void retrieveData(String outputFileName, addData operator) throws IOException {

        FileReader fileReader;
        FileWriter fileWriter;
        BufferedReader reader;
        BufferedWriter writer;
        int curFileIndex = 0;
        boolean skip = true;
        String fileWriterName = logPathName + "output.log";
        String timeRegex = "(\\d:\\d\\d:\\d\\d.\\d\\d\\d\\d\\d\\d\\d\\d\\d)";
        // Create a pattern from regex
        Pattern pattern = Pattern.compile(timeRegex);
        Matcher matcher;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

        if (!outputFileName.isEmpty()) {
            fileWriterName = outputFileName;
        }
        System.out.printf("Output file name %s%n", fileWriterName);
        fileWriter = new FileWriter(fileWriterName);
        writer = new BufferedWriter(fileWriter);

        for (String fileName : fileNameList) {
            long numOfEntries = 0;
            fileReader = new FileReader(logPathName + fileName);
            reader = new BufferedReader(fileReader);

            String line = reader.readLine();
            skip = false;
            while (line != null) {
                // Find all lines which hold the delimiter
                if (line.contains(paramName + " ")) {
                    long system_ts;
                    long stream_ts;
                    boolean firstMatch = true;
                    if (curFileIndex == 0) {
                        // Quirk: skip one line over two from the receiver file
                        skip = !skip;
                    }
                    if (!skip) {
//                    System.out.println(line);
                        // Create a matcher for the input String
                        matcher = pattern.matcher(line);

                        // Extract all timestamp values
                        while (matcher.find()) {
//                        System.out.printf("matcher: %s\n", matcher.group(1));
                            // Quirk: fix the hour format mismatch
                            String time = "0" + matcher.group(1);
//                        System.out.println("Time: " + time);
                            LocalTime localTime = LocalTime.parse(time, formatter);
                            if (firstMatch) {
                                system_ts = localTime.toNanoOfDay();
//                            System.out.println("System TS in nanoseconds:   " + system_ts);
                                firstMatch = false;
 //                               operator.op(curFileIndex, system_ts);
                                numOfEntries++;
                            } else {
                                stream_ts = localTime.toNanoOfDay();
//                            System.out.println("Stream TS in nanoseconds:   " + stream_ts);
                                if (stream_ts != 0) {
                                    operator.op(curFileIndex, stream_ts);
                                    numOfEntries++;
                                }
                            }
                        }
                        writer.write(line + System.lineSeparator());
                    }
                }
                line = reader.readLine();
            }

            System.out.println("File " + fileName + ": number of entries: " + numOfEntries);
            summaryList.add(numOfEntries);
            reader.close();
            fileReader.close();
            curFileIndex++;
        }
        writer.close();
        fileWriter.close();

    }

}
