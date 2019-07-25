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
    private int start = 1;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        view.Init();
    }

    public boolean filterData()  throws IOException {

        model.retrieveData("", (int index, long value) -> { view.addData(index, value); });

        return true;
    }

    public boolean updateView() {
        view.Draw(start, start+view.getRange());
        view.updateView();
        return true;

    }

    public boolean generateSummary() {
        System.out.println("Receiver Number of Entries:" + model.getNumOfEntries(0));
        System.out.println("Sender   Number of Entries:" + model.getNumOfEntries(1));
        return true;
    }

}
