package Bus;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.logging.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.*;




public class BusContainer extends ArrayList<BusExtended> {
    static final long serialVersionUID = 0;
    private String[] CSVHeader;
    private static Logger log = Logger.getLogger(BusContainer.class.getName());
    private static String DriverSurnameString = new String("DriverSurname");
    private static List<String> availibleCSVHeaders = 
                                    Arrays
                                    .asList(
                                        "BusNumber", 
                                        "PlaceCount", 
                                        "RouteNumber",
                                        DriverSurnameString);

    
    public BusContainer() throws IOException
    {
        super();
        LogManager
            .getLogManager()
            .readConfiguration(
                BusContainer
                    .class
                    .getResourceAsStream(".log_config"));
        
    }

    private boolean readCSVHeader(String firstLine)
    {
        String[] seperatedCSVHeaders = firstLine.split(",",4);
        this.CSVHeader = new String[4];
        for(int i=0; i<seperatedCSVHeaders.length; i++)
        {
            String tmpHeader = "";
            String[] seperatedHeader = seperatedCSVHeaders[i].toLowerCase().split(" ");
            for(String headerPart: seperatedHeader)
            {
               tmpHeader += headerPart.substring(0, 1).toUpperCase() + headerPart.substring(1);
            }
            try
            {
                if(availibleCSVHeaders.contains(tmpHeader))
                    this.CSVHeader[i] = tmpHeader;
                else
                    throw new Exception("Unavailible CSV Header: "+seperatedCSVHeaders[i]+"\n CSV header must be like bus number | place count | route number | driver surname");
            } 
            catch (Exception e)
            {
                log.log(Level.SEVERE, "Exception: ", e);
                return false;
            }
        }
        return true;
    }
    public boolean readCSV (String pathToFile)
    {
        BufferedReader reader;
		try {
            reader = new BufferedReader(new FileReader(pathToFile));
            if(readCSVHeader(reader.readLine()))
            {
                for(String line = reader.readLine();line != null;line = reader.readLine())
                {
                    try
                    {
                        BusExtended bus = new BusExtended();
                        String[] args = line.split(",",4);
                        for(int i=0; i<args.length; i++)
                        {
                            String methodName = "set"+this.CSVHeader[i];
                            Method calledMethod = BusExtended.class.getMethod(methodName,
                                                                      (this.CSVHeader[i].equals(DriverSurnameString))
                                                                      ?String.class
                                                                      :int.class);
                            try
                            {
                                calledMethod.invoke(bus,
                                                    (this.CSVHeader[i].equals(DriverSurnameString))
                                                    ?args[i]
                                                    :Integer.parseInt(args[i]));
                            }
                            catch (Exception e)
                            {
                                String badObjectDescription = "Bus Object ID:" + Integer.toString(bus.getID());
                                log.log(Level.SEVERE, "\n" + badObjectDescription + "\nException: ", e);
                            }
                        }
                        this.add(bus);
                    }
                    catch(Exception e)
                    {
            			e.printStackTrace();
                    }
                }
            }
            reader.close();
		} catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void writeJSON(String fileName)
    {
        Collections.sort(this);
        BufferedWriter writer;
        try
        {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("[");
            int i = 0;
            for(Bus bus: this)
            {
                writer.write( bus.toJSON()+ (i==this.size()-1 ? "" : ","));
                i++;
            }
            writer.write("]");
            writer.close();
        }catch (IOException e) {
			e.printStackTrace();
        }
    }
       

}
    