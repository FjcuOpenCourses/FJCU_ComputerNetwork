import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
public class QueueingSimulation {

    private ArrayList<Double> Nlist;
    private ArrayList<Double> Tlist;
    private   List<Event> event_queue ;
    private   List<Packet> packet_queue ;
    private   List<Double> lambda;
    private   Double micro;
    private Plot plt ;



    public List<Event> getEvent_queue() {
        return event_queue;
    }


    public QueueingSimulation(List<Double>lambda, Double micro){
        this.lambda = lambda;
        this.micro = micro;
        this.event_queue = new ArrayList<Event>();
        this.packet_queue = new ArrayList<Packet>();
        this.plt = Plot.create();
        this.Nlist = new ArrayList<>();
        this.Tlist = new ArrayList<>();
    }
    public void startTest(){
        for (Double lambda:this.lambda){
            simulate(lambda, micro);
            this.event_queue.clear();
            this.packet_queue.clear();
        }
    }

    public void simulate(double a, double b) {
        double   currTime=0.0;
        double   prevTime = 0.0;
        boolean  cpuBusy = false;
        int      numPacketsInSystem = 0;
        double   timePacketProduct = 0.0;
        int      numPacketsServed = 0;
        double   totalSystemTime = 0.0;
        Packet   currPacket = null;
        double ENDTIME = 10000.0;
        //產生下一個packet到達的時間

        this.event_queue.add(new Event(exptime(a),0));

        while (Double.compare(currTime, ENDTIME)<0) { // 這裡ENDTIME設為10000
            try{
                Event e =  this.event_queue.remove(0);//從event queue取出first event;
                prevTime = currTime;
                currTime = e.getEventTime();

                if (e.getType() == 0) {  // 處理packet arrival
                    timePacketProduct += numPacketsInSystem* (currTime-prevTime);
                    Packet p = new Packet(currTime,exptime(b));
                    numPacketsInSystem++;

                    if (cpuBusy == false) { // CPU可以處理packet
                        cpuBusy = true;
                        currPacket = p;
                        Event e2 = new Event(currTime + p.getSvcTime(),1);
                        //將e2 依照它的eventTime插入到event queue中適當位置;
                        this.event_queue.add(e2);
                        Collections.sort(this.event_queue, (o1, o2) -> (int) (o1.getEventTime()-o2.getEventTime()));
                    }
                    else{
                        this.packet_queue.add(p);
                    }  //將p 插入到packet queue的尾巴;
                    //產生下個packet的到達時間
                    Event e3 = new Event(currTime + exptime (a),0);
                    //將e3 依照它的eventTime插入到event queue中適當位置;
                    this.event_queue.add(e3);
                    Collections.sort(this.event_queue, (o1, o2) -> (int) (o1.getEventTime()-o2.getEventTime()));
                }
                else {
                    // 處理packet departure
                    timePacketProduct += numPacketsInSystem*(currTime-prevTime);
                    numPacketsInSystem--;
                    numPacketsServed++;
                    totalSystemTime+=currTime-currPacket.getArrTime();

                    if (this.packet_queue.size()<=0){
                        cpuBusy = false;
                    }
                    else{ // CPU處理下一個packet

                        currPacket =this.packet_queue.remove(0);
                        if(currPacket==null){
                            System.out.println("check");
                        }
                        Event e4 = new Event(currTime + currPacket.getSvcTime(),1);
                        //將e4 依照它的eventTime插入到event queue中適當位置;
                        this.event_queue.add(e4);
                        Collections.sort(this.event_queue, (o1, o2) -> (int) (o1.getEventTime()-o2.getEventTime()));

                    }
                }
            }catch (NullPointerException e){
                System.out.println("ERROR at currentTime:"+currTime);
                System.out.println("compare:"+Double.compare(currTime,ENDTIME));
                e.fillInStackTrace();
            }
        }


//        印出 N = timePacketProduct / ENDTIME;
//        印出 T = totalSystemTime/ numPacketsServed;
        System.out.println("result");
        System.out.println(timePacketProduct / ENDTIME);
        System.out.println(totalSystemTime/ numPacketsServed);
        this.Nlist.add(timePacketProduct / ENDTIME);
        this.Tlist.add(totalSystemTime/ numPacketsServed);

    }
    public void show(String plotTitle,double[] x,double[] y) throws IOException, PythonExecutionException {


        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        // add a line plot to the PlotPanel
        plot.addLinePlot(plotTitle, x, y);

        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
    public static double exptime(double lambda) {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return  -1.0 *  Math.log(randomNumber) / lambda;
    }
    public void showNplot() throws IOException, PythonExecutionException {
        double[] x = ArrayUtils.toPrimitive(this.lambda.stream().toArray(Double[]::new));
        double[] y =   ArrayUtils.toPrimitive(this.Nlist.stream().toArray(Double[]::new));
        this.show("test",x,y);
    }
    public void showTplot() throws IOException, PythonExecutionException {
        double[] x = ArrayUtils.toPrimitive(this.lambda.stream().toArray(Double[]::new));
        double[] y =   ArrayUtils.toPrimitive(this.Tlist.stream().toArray(Double[]::new));
        this.show("test",x,y);
    }

}