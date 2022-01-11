public class Event {
    private double eventTime;
    private int	type; // 0 = packet arrival, 1 = packet departure

    public double getEventTime() {
        return eventTime;
    }

    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Event(double eventTime, int type){
        this.eventTime = eventTime;
        this.type = type;
    }
}