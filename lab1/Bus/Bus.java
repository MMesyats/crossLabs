package Bus;

public class Bus implements Comparable<Bus> {
    protected int busNumber;
    protected int placeCount;
    protected int routeNumber;
    protected String driverSurname;

    public Bus(){
        this.busNumber = 0;
        this.placeCount = 0;
        this.routeNumber = 0;
        this.driverSurname = "";
    }

    public Bus(int busNumber, int placeCount, int routeNumber, String driverSurname) {
        this.busNumber = busNumber;
        this.placeCount = placeCount;
        this.routeNumber = routeNumber;
        this.driverSurname = driverSurname;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }
    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }
    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }
    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public int getBusNumber() {
        return this.busNumber;
    }
    public int getPlaceCount() {
        return this.placeCount;
    }
    public int getRouteNumber() {
        return this.routeNumber;
    }
    public String getDriverSurname() {
        return this.driverSurname;
    }

    public int compareTo(Bus notThis)
    {
        if(this.placeCount == notThis.placeCount)
            return 0;
        else if (this.placeCount < notThis.placeCount )
            return -1;
        else 
            return 1;
    }

    public String toJSON()
    {
        return String.format(
            "{"+
                "\"number\":%d,"+
                "\"placeCount\":%d," +
                "\"routeNumber\":%d," +
                "\"driverSurname\":%s" +
            "}",
            this.busNumber,
            this.placeCount,
            this.routeNumber,
            this.driverSurname);
    }

    @Override
    public String toString() {
        return String.format(
            "Bus: \n"+
            "Number: %d \n"+
            "Place count: %d \n"+
            "Route: %d \n"+
            "Driver: %s \n",
            this.busNumber,
            this.placeCount,
            this.routeNumber,
            this.driverSurname);
    }

}