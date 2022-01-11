



public  class Packet {
     private double arrTime, svcTime;

    public double getArrTime() {
        return arrTime;
    }

    public void setArrTime(double arrTime) {
        this.arrTime = arrTime;
    }

    public double getSvcTime() {
        return svcTime;
    }

    public void setSvcTime(double svcTime) {
        this.svcTime = svcTime;
    }

    public Packet(double arrTime, double svcTime) {
        this.arrTime = arrTime;
        this.svcTime = svcTime;
    }
}
