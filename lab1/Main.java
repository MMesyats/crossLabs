
import java.io.IOException;
import Bus.BusContainer;

class Main {
    public static void main(String[] args) throws IOException {
        BusContainer buses = new BusContainer();
        if(buses.readCSV("./buses.csv"));
            buses.writeJSON("buses.json");
    
    }
}
