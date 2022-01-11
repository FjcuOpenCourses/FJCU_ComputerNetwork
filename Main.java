import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, PythonExecutionException {

        List<List<Double>> lamdaList = Arrays.asList(Arrays.asList(0.05, 1.0, 3.0, 5.0, 7.0,9.0),
                                                    Arrays.asList(0.1, 2.0, 6.0, 10.0, 14.0,18.0),
                                                    Arrays.asList(0.05, 1.0, 3.0, 5.0, 7.0,9.0),
                                                    Arrays.asList(0.25, 5.0, 15.0, 25.0, 35.0,45.0));

        List<Double> microList = Arrays.asList(10.0,20.0,50.0,50.0);

        for (int i =0 ;i<lamdaList.size();i++){
            QueueingSimulation currentTest = new QueueingSimulation(lamdaList.get(i),microList.get(i));
            currentTest.startTest();
            currentTest.showNplot();
            currentTest.showTplot();
        }
    }
}
