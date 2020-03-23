package Bus;

import Bus.Bus;

public class BusExtended extends Bus
{
    private int id=0;
    private static int count = 0;

    public BusExtended()
    {
        this.id = count++;
    }

    public BusExtended(int busNumber, int placeCount, int routeNumber, String driverSurname) {
        super(busNumber,placeCount,routeNumber, driverSurname);
        this.id = count++;
    }

    public int getID() 
    {
        return this.id;
    }

    @Override
    public String toJSON()
    {
        return String.format(
            "{"+
                "\"id\":%d,"+
                "\"number\":%d,"+
                "\"placeCount\":%d," +
                "\"routeNumber\":%d," +
                "\"driverSurname\":\"%s\"" +
            "}",
            this.id,
            this.busNumber,
            this.placeCount,
            this.routeNumber,
            this.driverSurname);
    }

}