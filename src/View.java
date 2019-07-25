import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class View {
    private List<Serie> mSerieList;
    private int curIndex = 0;

    private boolean dots = false;
    private boolean fullRange = false;

    private int minSequenceNumber = 1;
    private int maxSequenceNumber = 1;

    private int xStart;
    private int xEnd;
    private int xIncrement;
    private NumberAxis xAxis;

    private int yStart = Integer.MAX_VALUE;
    private int yEnd = 40;
    private int yIncrement = 40; //milliseconds

    private LineChart mLinechart;
    private Stage primaryStage;


    public View(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mSerieList = new ArrayList<>(2);
        System.out.println("Creating a graphics View");
    }

    static public int getRange() {
        return 25;
    }

    public void toggleDots() {
        dots = !dots;
        mLinechart.setCreateSymbols(dots); //false: hide dots
    }

    public boolean updateView() {

        Button leftButton = new Button("Left");
        leftButton.setLayoutX(180);
        leftButton.setLayoutY(410);
        leftButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                setDeltaStart(-getRange());
                System.out.println("Go left [" + xStart + ".." + xEnd + "]");
            }
        }));

        Button dotsButton = new Button("Dots");
        dotsButton.setLayoutX(230);
        dotsButton.setLayoutY(410);
        dotsButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Toggle dots");
                toggleDots();
            }
        }));


        Button rightButton = new Button("Right");
        rightButton.setLayoutX(285);
        rightButton.setLayoutY(410);
        rightButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                setDeltaStart(+getRange());
                System.out.println("Go right [" + xStart + ".." + xEnd + "]");
            }
        }));
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int region = 1;
                System.out.println("Full Range");
                System.out.println("Mouse at (" + e.getX() + "," + e.getY() + ")");
                if ((e.getX() >= 68.0 && e.getX() <= 485.0)) {
                    region = (int) (1.0 + (e.getX() - 68.0) * (maxSequenceNumber-1) / (485.0 - 68.0));
                    System.out.println("Region " + region + "  " + (1.0 + (e.getX() - 68.0) * (maxSequenceNumber-1) / (485.0 - 68.0)));
                }
                if (!fullRange) {
                    setRange(minSequenceNumber, maxSequenceNumber);
                } else {
                    setRange(region, region + getRange());
                }
                fullRange = !fullRange;
            }
        };
        //Registering the event filter
        mLinechart.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        Group root = new Group(mLinechart, leftButton, dotsButton, rightButton);

        primaryStage.setTitle("Timestamps");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 500, 450));
        primaryStage.show();
        return true;
    }

    public void addData(int indexSeries, long value) {
        int millis;
        // Convert nanos to millis
        millis = (int) (value / (long)1000000);
        if (indexSeries == 1) {
            curIndex++;
        }
        //Find Minimum and Maximum value of the series
        if (millis > yEnd) {
            yEnd = millis;
        }
        if (millis < yStart) {
            yStart = millis;
        }
        maxSequenceNumber = curIndex;
        mSerieList.get(indexSeries).addData(value);
    }

    public boolean Init () {
        String name = "Receiver Data";
        mSerieList.add(new Serie(name));
        name = "Sender Data";
        mSerieList.add(new Serie(name));
        return true;
    }

    public void setFullScreen (boolean value) {
        primaryStage.setFullScreen(value);
    }

    public boolean Draw (int xStart, int xEnd) {

        // Load the data to draw
        // The horizontal axis is always the sequence number (similar to a timeline) which increments monotonically by 1

         //Defining X axis
        this.xStart = xStart;
        if (xEnd < this.curIndex) {
            this.xEnd   = xEnd;
        } else {
            this.xEnd   = this.curIndex;
        }
        xIncrement = 10;


        System.out.println("xStart: " + xStart);
        System.out.println("xEnd:   " + xEnd);

        xAxis = new NumberAxis("Sequence Number", xStart, xEnd, xIncrement);

        //Defining Y axis
        NumberAxis yAxis = new NumberAxis("PTS (ms)", yStart, yEnd + yIncrement, yIncrement);

        mLinechart = new LineChart(xAxis, yAxis);
        for (Serie serie : mSerieList) {
            mLinechart.getData().add(serie.getSerie());
            System.out.println("Slope: " + serie.getSlope());
        }
        mLinechart.setCreateSymbols(dots); //false: hide dots
        return true;
    }

    public boolean setRange(int xStart, int xEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        xAxis.setLowerBound(xStart);
        xAxis.setUpperBound(xEnd);
        return true;
    }

    public boolean setDeltaStart(int delta) {
        System.out.println("minSequenceNumber: " + minSequenceNumber);
        System.out.println("maxSequenceNumber: " + maxSequenceNumber);
        if (xStart + delta >= minSequenceNumber) {
            if (xEnd + delta <= maxSequenceNumber) {
                xStart += delta;
                xEnd += delta;
                xAxis.setLowerBound(xStart);
                xAxis.setUpperBound(xEnd);
            }
            return true;
        }
        return false;
    }
}
