import javafx.scene.chart.XYChart;

public class Serie {
    private int curIndex = 0;
    private XYChart.Series mSerie;
    private long minValue = 1;
    private long maxValue = 1;
    private long minSequenceNumber = 1;
    private long maxSequenceNumber = 1;

    public Serie(String name) {
        mSerie = new XYChart.Series();
        mSerie.setName(name);
    }

    public void addData(long value) {
        long millis;
        curIndex++;

        millis = value / (long)1000000;
        mSerie.getData().add(new XYChart.Data(curIndex, millis));
        //System.out.println("value: " + value + "  millis: " + millis);
        //Find Minimum and Maximum value of the series
        if (millis > maxValue) {
            maxValue = millis;
        }
        if (millis < minValue) {
            minValue = millis;
        }
        //System.out.println("yStart: " + minValue + "   yEnd: " + maxValue);
        maxSequenceNumber = curIndex;
    }

    public XYChart.Series getSerie() {

        return mSerie;
    }

    public double getSlope() {
        return (maxValue - minValue ) / (maxSequenceNumber - minSequenceNumber);
    }
}
