package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Controller control = new Controller("","","","");

        control.filterData();

        View view = new View();
        view.Draw();
        //view.LineChartSample();
        Group root = new Group(view.getLineChart());

        primaryStage.setTitle("Timestamps");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


}
