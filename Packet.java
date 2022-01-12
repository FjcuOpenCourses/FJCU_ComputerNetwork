



public  class Packet {
     private double arrTime, svcTime;

    public double getArrTime() {
        return arrTime;
    }

    public double getSvcTime() {
        return svcTime;
    }

    public Packet(double arrTime, double svcTime) {
        this.arrTime = arrTime;
        this.svcTime = svcTime;
    }
}
