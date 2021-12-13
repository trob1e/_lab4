import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class GraphicsDisplay extends JPanel {
    private ArrayList<Double[]> graphicsData;
    private ArrayList<Double[]> originalData;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private Font axisFont;
    private Font labelsFont;
    private BasicStroke axisStroke;
    private BasicStroke markerStroke;
    private BasicStroke gridStroke;
    private BasicStroke selectionStroke;

    private boolean showAxis = true;
    private boolean showMarkers = true;

    public GraphicsDisplay() {
        this.setBackground(Color.WHITE);
        this.axisStroke = new BasicStroke(2.0F, 0, 1, 10.0F, null, 0.0F);
        this.markerStroke = new BasicStroke(1.5F, 0, 1, 5.0F, new float[] {30, 30, 10, 10, 10, 10, 20, 20, 20, 20}, 0.0F);
        this.selectionStroke = new BasicStroke(1.0F, 0, 0, 10.0F, new float[]{10.0F, 10.0F}, 0.0F);


        this.gridStroke = new BasicStroke(0.5F, 0, 1, 5.0F, new float[]{5.0F, 5.0F}, 2.0F);
        this.axisFont = new Font("Serif", 1, 36);
        this.labelsFont = new Font("Serif", 0, 10);
    }

    public void showGraphics(ArrayList<Double[]> graphicsData) {
        this.graphicsData = graphicsData;
        this.originalData = new ArrayList<>(graphicsData.size());
        Iterator var3 = graphicsData.iterator();

        while(var3.hasNext()){
            Double[] point = (Double[])var3.next();
            Double[] newPoint = new Double[]{point[0], point[1]};
            this.originalData.add(newPoint);
        }

        this.minX = (graphicsData.get(0))[0];
        this.maxX = (graphicsData.get(graphicsData.size() - 1))[0];
        this.minY = (graphicsData.get(0))[1];
        this.maxY = this.minY;

        for(int i = 1; i < graphicsData.size(); ++i) {
            if ((graphicsData.get(i))[1] < this.minY) {
                this.minY = (graphicsData.get(i))[1];
            }

            if ((graphicsData.get(i))[1] > this.maxY) {
                this.maxY = (graphicsData.get(i))[1];
            }
        }


    }

    public void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);
            out.println("Результаты скорректированых значений");
            Iterator var4 = this.graphicsData.iterator();

            while(var4.hasNext()) {
                Double[] point = (Double[])var4.next();
                out.println(point[0] + " " + point[1]);
            }

            out.close();
        } catch (FileNotFoundException var5) {
        }

    }

    public void reset() {
        this.showGraphics(this.originalData);
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
        this.repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
        this.repaint();
    }
}