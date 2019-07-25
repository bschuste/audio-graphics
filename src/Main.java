import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        // Retrieve User Parameters: log filename, extraction parameters, view type
        // Optionally provide directory, filename and set of parameters. Could be stored in a config file.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        View view = new View(primaryStage);
        Model model = new Model();

        Controller control = new Controller(view, model);

        control.filterData();

        control.updateView();

        control.generateSummary();
    }


}
