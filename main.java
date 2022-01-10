


class Event {
    double eventTime;
    int	type; // 0 = packet arrival, 1 = packet departure
    public Event(double eventTime,int type){
        this.eventTime = eventTime;
        this.type = type;
    }
}
class Packet {
    double arrTime, svcTime;

    public Packet(double arrTime, double svcTime) {
        this.arrTime = arrTime;
        this.svcTime = svcTime;
    }
}
