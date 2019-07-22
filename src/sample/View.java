package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class View {
    private XYChart.Series series;
    private int curIndex = 0;

    private int xStart;
    private int xEnd;
    private int xIncrement;


    private int yStart = 1000000;
    private int yEnd = 40;
    private int yIncrement = 1; //milliseconds

    private LineChart mLinechart;
    private Stage primaryStage;

    private boolean initChart() {
        for (int i=1; i<100; i++) {

            addData(40*i);
//            series.getData().add(new XYChart.Data(i, 0.040*i));
        }

        return true;
    }

    public View(Stage primaryStage) {
        this.primaryStage = primaryStage;
        System.out.println("Creating a graphics View");
    }

    public boolean updateView() {

        Group root = new Group(getLineChart());

        primaryStage.setTitle("Timestamps");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        return true;
    }

    public void addData(long value) {
        int millis;
        curIndex++;

        millis = (int) (value / (long)1000000);
        series.getData().add(new XYChart.Data(curIndex, millis));

        System.out.println("value: " + value + "  millis: " + millis);
        if (millis > yEnd) {
            yEnd = millis;
        }
        if (millis < yStart) {
            yStart = millis;
        }
        System.out.println("yStart: " + yStart + "   yEnd: " + yEnd);
    }

    public boolean Init () {
        series = new XYChart.Series();
        series.setName("Incoming Data");
        return true;
    }

    public boolean Draw () {

        // Load the data to draw
        // The horizontal axis is always the timeline, right now the increment is 1
        // or the horizontal line could be the sequence number
        // But of course it could vary

        // Draw parameters on timeline (x-axis time, y-axis buffer write pointer)

//        series = new XYChart.Series();
//        series.setName("Incoming Data");

        //Init data only for test right now. The callback from Model should feed the chart
        //initChart();

        //Find Minimum and Maximum value of the series



        //Defining X axis
        xStart = 1;
        xEnd   = curIndex;
        xIncrement = 1;

        System.out.println("xEnd: " + xEnd);

        NumberAxis xAxis = new NumberAxis(xStart, xEnd, xIncrement);
        xAxis.setLabel("Sequence Number");

        //Defining Y axis
        //yStart = 0;
        //yEnd   = 4000;
        //yIncrement = 1;
        NumberAxis yAxis = new NumberAxis(yStart, yEnd, yIncrement);
        xAxis.setLabel("PTS (ms)");

        mLinechart = new LineChart(xAxis, yAxis);
        mLinechart.getData().add(series);

        mLinechart.setCreateSymbols(false); //hide dots

        return true;
    }

    public LineChart getLineChart() {
        return mLinechart;
    }

    public boolean LineChartSample() {
        NumberAxis xAxis = new NumberAxis("Values for X-Axis", 0, 3, 1);
        NumberAxis yAxis = new NumberAxis("Values for Y-Axis", 0, 3, 1);
        ObservableList<XYChart.Series<Double,Double>> lineChartData = FXCollections.observableArrayList(
                new LineChart.Series<Double,Double>("Series 1", FXCollections.observableArrayList(
                        new XYChart.Data<Double,Double>(0.0, 1.0),
                        new XYChart.Data<Double,Double>(1.2, 1.4),
                        new XYChart.Data<Double,Double>(2.2, 1.9),
                        new XYChart.Data<Double,Double>(2.7, 2.3),
                        new XYChart.Data<Double,Double>(2.9, 0.5)
                )),
                new LineChart.Series<Double,Double>("Series 2", FXCollections.observableArrayList(
                        new XYChart.Data<Double,Double>(0.0, 1.6),
                        new XYChart.Data<Double,Double>(0.8, 0.4),
                        new XYChart.Data<Double,Double>(1.4, 2.9),
                        new XYChart.Data<Double,Double>(2.1, 1.3),
                        new XYChart.Data<Double,Double>(2.6, 0.9)
                ))
        );
        mLinechart = new LineChart(xAxis, yAxis, lineChartData);
        return true;
    }


    // Create the line graphics and normalize the representation axis
    /*
    public boolean LineChart(int xLowerBound, int xUpperBound, int yLowerBound, int yUpperBound) {
        int seqNumber;
        NumberAxis xAxis = new NumberAxis("Values for X-Axis", 0, 3, 1);
        NumberAxis yAxis = new NumberAxis("Values for Y-Axis", 0, 3, 1);
        ObservableList<XYChart.Series<Double,Double>> lineChartData = FXCollections.observableArrayList(
                new LineChart.Series<Double,Double>("Series 1", FXCollections.observableArrayList(
        for ()
            new XYChart.Data<Double,Double>(0.0, 1.0),
                    new XYChart.Data<Double,Double>(1.2, 1.4),
                    new XYChart.Data<Double,Double>(2.2, 1.9),
                    new XYChart.Data<Double,Double>(2.7, 2.3),
                    new XYChart.Data<Double,Double>(2.9, 0.5)
                ))
        );
        mLinechart = new LineChart(xAxis, yAxis, lineChartData);
        return true;
    }
    */


}
