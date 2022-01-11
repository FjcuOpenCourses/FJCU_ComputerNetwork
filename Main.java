import com.github.sh0nk.matplotlib4j.PythonExecutionException;

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
        TestCase.add(new QueueingSimulation(Arrays.asList(0.25, 5.0, 15.0, 25.0, 35.0,45.0),50.0));

        for (QueueingSimulation currentTest:TestCase){
            currentTest.startTest();
        }
    }
}
