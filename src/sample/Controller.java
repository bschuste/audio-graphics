package sample;

import javafx.scene.Group;
import javafx.scene.Scene;

import java.io.IOException;

//TODO Interaction with User in a loop

public class Controller {

    // Zoom in and out
    // Move forward and backward in the timeline

    // Ask for one or two filenames
    // Ask for which fields need to be displayed

    // Provide a summary upon user request

    private Model model;
    private View view;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        view.Init();
    }

    public boolean filterData()  throws IOException {

        model.retrieveData("", (long value) -> { view.addData(value); });

        return true;
    }

    public boolean updateView() {
        view.Draw();
        view.updateView();
        return true;

    }
    public boolean generateSummary() {
        return true;
    }

}
