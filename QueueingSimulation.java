import java.util.ArrayList;
import java.util.List;

public class QueueingSimulation { //以為例

    public static List<Event> event_queue = new ArrayList<>();
    public static List<Packet> packet_queue = new ArrayList<>();

    public static void main(String[] args) {
        simulate(0.05, 10);
        for (int i=0; i<5; i++) {
            double a = 1.0 +  i*2.0;
            double b= 10;
            simulate(a,b);
        }
    }
    public static void simulate(double a, double b) {
        double   currTime=0.0;
        double   prevTime = 0.0;
        boolean  cpuBusy = false;
        int      numPacketsInSystem = 0;
        double   timePacketProduct = 0.0;
        int      numPacketsServed = 0;
        double   totalSystemTime = 0.0;
        Packet   currPacket;
        int ENDTIME = 10000;
//產生下一個packet到達的時間

        event_queue.add(new Event(exptime(a),0));

        while (currTime < ENDTIME) { // 這裡ENDTIME設為10000
            Event e =  event_queue.get(0);//從event queue取出first event;
            prevTime = currTime;
            currTime = e.eventTime;
            if (e.type == 0) {  // 處理packet arrival
                timePacketProduct += numPacketsInSystem* (currTime-prevTime);
                Packet p = new Packet(currTime,exptime(b));
                numPacketsInSystem++;
                if (cpuBusy == false) { // CPU可以處理packet
                    cpuBusy = true;
                    currPacket = p;
                    Event e2 = new Event(currTime + p.svcTime,1);
                    //將e2 依照它的eventTime插入到event queue中適當位置;
                    event_queue.add(e2);
                }
                else{
                }  //將p 插入到packet queue的尾巴;
                //產生下個packet的到達時間
                Event e3 = new Event(currTime + exptime (a),0);
                //將e3 依照它的eventTime插入到event queue中適當位置;
            }
            else { // 處理packet departure
                timePacketProduct += numPacketsInSystem*(currTime–prevTime);
                numPacketsInSystem--;
                numPacketsServed++;
                totalSystemTime +=  (currTime–currPacket.arrTime);

                if (packet_queue.size()==0){
                    cpuBusy = false;
                }
                else{ // CPU處理下一個packet
                    currPacket =packet_queue.get(0);
                    Event e4 = new Event(currTime + currPacket.svcTime,1);
                    //將e4 依照它的eventTime插入到event queue中適當位置;
                }
            }
        }
        印出 N = timePacketProduct / ENDTIME;
        印出 T = totalSystemTime/ numPacketsServed;
    }
    public static double exptime(double lambda){
        return  -1.0 *  Math.log((random_number()) / lambda);
    }
}