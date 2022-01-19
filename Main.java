import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, PythonExecutionException {

        List<QueueingSimulation> TestCase = new ArrayList<>();

        TestCase.add(new QueueingSimulation(Arrays.asList(0.05, 1.0, 3.0, 5.0, 7.0,9.0),10.0));
        TestCase.add(new QueueingSimulation(Arrays.asList(0.1, 2.0, 6.0, 10.0, 14.0,18.0),20.0));
        TestCase.add(new QueueingSimulation(Arrays.asList(0.05, 1.0, 3.0, 5.0, 7.0,9.0),50.0));
        TestCase.add(new QueueingSimulation(Arrays.asList(0.25, 5.0, 15.0, 25.0, 35.0,45.0),50.0,0.02));

        for (QueueingSimulation currentTest:TestCase){
            currentTest.startTest();

        }
        show(TestCase,"T");
        show(TestCase,"N");


    }
    public static void show(List<QueueingSimulation> testData,String title) {

        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();
        // add a line plot to the PlotPanel

        for (QueueingSimulation currentTest:testData){
            String label = currentTest.getServiceTime()==null?"mu="+currentTest.getMicro().toString():"service time="+currentTest.getServiceTime().toString();
            plot.addLinePlot(label,currentTest.getUsageRaito(), title.equals("T")?currentTest.getTlist():currentTest.getNlist());
            plot.addLegend("SOUTH");

        }
        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame(title);
        frame.setContentPane(plot);
        frame.setSize(550,500);
        frame.setVisible(true);
    }
}
