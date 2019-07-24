import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        // Retrieve User Parameters: log filename, extraction parameters, view type
        // Optionally provide directory, filename and set of parameters. Could be stored in a config file.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        View view = new View(primaryStage);
        Model model = new Model();
        Boolean quit = false;
        BufferedReader mReader;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";
        int start = 1, number = 10;

        Controller control = new Controller(view, model);

        control.filterData();

        control.updateView(start, number);

//         do {
//             System.out.print("What do you want to do:  ");
//             try {
//                 choice = mReader.readLine();
//                 choice.trim().toLowerCase();
//                 switch (choice) {
//                     case "range":
//                         start += 10;
//                         control.updateView(start, number);
//                         break;
//                     case "zoom":
//                         number *=2;
//                         break;
//                 }
//             } catch(IOException ioe) {
//                 System.out.println("The user messed with the system input");
//                 ioe.printStackTrace();
//             }
//         } while(!choice.equals("quit"));

    }


}
