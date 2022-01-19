import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QueueingSimulation {

    private ArrayList<Double> Nlist;



    private ArrayList<Double> Tlist;
    private   List<Event> event_queue ;
    private   List<Packet> packet_queue ;
    private   List<Double> lambda;
    private   Double micro;
    private Double ServiceTime;
    private List<Double> usageRaito;

    public double[] getUsageRaito() {
        return ArrayUtils.toPrimitive(this.usageRaito.stream().toArray(Double[]::new));
    }

    public QueueingSimulation(List<Double>lambda, Double micro){
        this.lambda = lambda;
        this.micro = micro;
        this.event_queue = new ArrayList<Event>();
        this.packet_queue = new ArrayList<Packet>();
        this.usageRaito = this.lambda.stream().map(index->index/this.micro).collect(Collectors.toList());
        this.Nlist = new ArrayList<>();
        this.Tlist = new ArrayList<>();
        this.ServiceTime = null;
    }

    public QueueingSimulation(List<Double>lambda, Double micro,Double ServiceTime){
        this.lambda = lambda;
        this.micro = micro;
        this.usageRaito = this.lambda.stream().map(index->index/this.micro).collect(Collectors.toList());
        this.event_queue = new ArrayList<Event>();
        this.packet_queue = new ArrayList<Packet>();
        this.Nlist = new ArrayList<>();
        this.Tlist = new ArrayList<>();
        this.ServiceTime = ServiceTime;
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
        double ENDTIME = 10000.0;// 這裡ENDTIME設為10000

        //產生下一個packet到達的時間
        this.event_queue.add(new Event(exptime(a),0));

        while (Double.compare(currTime, ENDTIME)<0) {
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
                        //將p 插入到packet queue的尾巴;
                        this.packet_queue.add(p);
                    }
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
                    else{
                        // CPU處理下一個packet
                        currPacket =this.packet_queue.remove(0);
                        //將e4 依照它的eventTime插入到event queue中適當位置;
                        Event e4 = new Event(currTime + currPacket.getSvcTime(),1);
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
        System.out.println("N="+timePacketProduct / ENDTIME);
        System.out.println("T="+totalSystemTime/ numPacketsServed);
        this.Nlist.add(timePacketProduct / ENDTIME);
        this.Tlist.add(totalSystemTime/ numPacketsServed);

    }
    public double exptime(double lambda) {
        if(this.ServiceTime==null){
            Random random = new Random();
            double randomNumber = random.nextDouble();
            return  -1.0 *  Math.log(randomNumber) / lambda;
        }
        else {
            return this.ServiceTime;
        }

    }
    public Double getServiceTime() {
        return ServiceTime;
    }

    public Double getMicro() {
        return micro;
    }

    public double[] getNlist() {
        return ArrayUtils.toPrimitive(this.Nlist.stream().toArray(Double[]::new));
    }


    public double[] getTlist() {
        return ArrayUtils.toPrimitive(this.Tlist.stream().toArray(Double[]::new));
    }


}
