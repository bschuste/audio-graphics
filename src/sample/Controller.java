package sample;

import java.io.IOException;

public class Controller {

    // Zoom in and out
    // Move forward and backward in the timeline

    // Ask for one or two filenames
    // Ask for which fields need to be displayed

    // Provide a summary upon user request

    private Model model;
    private View view;

    public Controller(String logPathName, String primaryFileName, String secondaryFileName, String paramName) {

//        view = new View();
        model = new Model(logPathName, primaryFileName, secondaryFileName, paramName);

    }

    public boolean filterData()  throws IOException {

        model.retrieveData("");

        return true;
    }

    public boolean generateSummary() {
        return true;
    }

}
