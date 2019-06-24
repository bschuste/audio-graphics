package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class View {
    private int xStart;
    private int xEnd;
    private int xIncrement;


    private int yStart;
    private int yEnd;
    private int yIncrement;

    private LineChart mLinechart;

    public View() {
        System.out.println("Creating a graphics View");
    }

    public boolean Draw () {

        // Load the data to draw
        // The horizontal axis is always the timeline, right now the increment is 1
        // or the horizontal line could be the sequence number
        // But of course it could vary

        // Draw parameters on timeline (x-axis time, y-axis buffer write pointer)

        XYChart.Series series = new XYChart.Series();
        series.setName("Incoming Data");

        for (int i=1; i<1500; i++) {

            series.getData().add(new XYChart.Data(i, 0.040*i));
        }
        //Find Minimum and Maximum value of the series

        xStart = 1;
        xEnd   = 100;
        xIncrement = 1;

        //Defining X axis
        NumberAxis xAxis = new NumberAxis(xStart, xEnd, xIncrement);
        xAxis.setLabel("Sequence Number");

        yStart = 0;
        yEnd   = 4;
        yIncrement = 1;
        //Defining Y axis
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

}
