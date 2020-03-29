package Bus.GUI;

import Bus.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;



public class Application extends JFrame {   
    public static final long serialVersionUID = 2;
    private TextField fileLabel;
    private BusContainer buses;
    private JLabel idLabel;
    private TextField[] inputFields;
    private BusExtended currentBus;
    private DefaultListModel<BusExtended> busListModel;
    private GridBagConstraints c;
        


    public Application()
    {
        super("Bus Organizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,400);
        setResizable(false);
        setLayout(new GridBagLayout());
        c = new GridBagConstraints(); 
        c.fill = GridBagConstraints.HORIZONTAL; 
        c.weighty   = 1; 
        c.weightx   = 1; 
        c.ipady=10;
        add(CSVButton(),setGridXY(0,0));
        add(textLabel("File:"),setGridXY(1,0));
        add(fileLabel(),setGridXY(2,0));
        c.ipady=200;
        add(busList(),setGridXY(0,1));
        add(fieldLabels(),setGridXY(1,1));
        add(fieldInputs(),setGridXY(2,1));
        c.ipady=10;
        add(saveJSONButton(),setGridXY(0,2));
        add(logButton(),setGridXY(1,2));
        add(busItemControls(),setGridXY(2,2));
        setVisible(true);
        try{
            buses = new BusContainer();
        }
        catch (Exception e) 
        {

        }
    }

    private GridBagConstraints setGridXY(int x,int y)
    {
        c.gridx=x;
        c.gridy=y;
        if(x==1)
        {
            c.ipadx=10;
        }
        else
        {
            c.ipadx=20;
        }
        return c;
    }

    private GridBagConstraints setGridXY(int x,int y, GridBagConstraints gridBag)
    {
        gridBag.gridx=x;
        gridBag.gridy=y;
        return gridBag;
    }
    private JButton CSVButton()
    {
        JButton btn = new JButton("Load .csv");
        btn.setSize(150,50);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv", "csv");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(filter);
                fileChooser.showOpenDialog(new JFrame());
                try
                {
                    File file = fileChooser.getSelectedFile();
                    fileLabel.setText(file.getAbsolutePath());
                    buses.readCSV(file.getAbsolutePath());
                    int i = 0;
                    for(BusExtended bus: buses)
                        busListModel.add(i++,bus);
                }
                catch(Exception e){}
            }
        });
        return btn;
    }

    private JLabel textLabel(String label)
    {
        JLabel textLabel = new JLabel(label);
        return textLabel;
    }

    private JPanel fieldInputs()
    {
        inputFields = new TextField[4];
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        idLabel = new JLabel("Not Seted");
        inputs.add(idLabel,setGridXY(0, 0, gb));

     
        for(int i = 0; i < inputFields.length;i++)
        {
            inputFields[i] = new TextField(30);
            inputFields[i].setSize(100, 5); 
            inputFields[i].setEditable(false);
            inputs.add(inputFields[i],setGridXY(0, i+1, gb));
        }

        return inputs;
    }

    private void setCurrentBus(BusExtended bus)
    {
        currentBus = bus;
        idLabel.setText(Integer.toString(bus.getID()));
        for(int i = 0; i < inputFields.length;i++)
            inputFields[i].setEditable(true);
        resetFieldsValues();
        
    }

    private void resetFieldsValues()
    {
        inputFields[0].setText(Integer.toString(currentBus.getBusNumber()));
        inputFields[1].setText(Integer.toString(currentBus.getPlaceCount()));
        inputFields[2].setText(Integer.toString(currentBus.getRouteNumber()));
        inputFields[3].setText(currentBus.getDriverSurname());
    }

    private void saveBusValues()
    {
        currentBus.setBusNumber(Integer.parseInt(inputFields[0].getText()));
        currentBus.setPlaceCount(Integer.parseInt(inputFields[1].getText()));
        currentBus.setRouteNumber(Integer.parseInt(inputFields[2].getText()));
        currentBus.setDriverSurname(inputFields[3].getText());
    }

    private TextField fileLabel()
    {
        fileLabel = new TextField(30);
        fileLabel.setEditable(false);
        return fileLabel;
    }

    private JScrollPane busList()
    {
        JPanel p = new JPanel();
        busListModel = new DefaultListModel<>();
        JList<BusExtended> busList = new JList<BusExtended>(busListModel);
        busList.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                setCurrentBus(busList.getSelectedValue());
            }
        });
        p.add(busList);
        return new JScrollPane(p);
    }
    private JPanel busItemControls()
    {
        JPanel p = new JPanel();
        GridBagConstraints gc = new GridBagConstraints();
        p.setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        gc.insets = new Insets(0,20,0,0);
        p.add(saveButton(),setGridXY(0, 0,gc));
        p.add(resetButton(),setGridXY(1, 0,gc));
        return p;
    }
    private JPanel fieldLabels()
    {
        GridBagConstraints gb  = new GridBagConstraints();
        JPanel fieldsLabels = new JPanel();
        gb.fill = GridBagConstraints.HORIZONTAL;
        gb.ipady = 12;
        fieldsLabels.setLayout(new GridBagLayout());
        fieldsLabels.add(textLabel("ID"), setGridXY(0,0,gb));
        fieldsLabels.add(textLabel("Number"),setGridXY(0,1,gb));
        fieldsLabels.add(textLabel("Place Count"),setGridXY(0,2,gb));
        fieldsLabels.add(textLabel("Route"),setGridXY(0,3,gb));
        fieldsLabels.add(textLabel("Driver Surname"),setGridXY(0,4,gb));
        return fieldsLabels;
    }

    private JButton resetButton()
    {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) {
                    resetFieldsValues();
                }
            });
        return resetButton; 
    }

    private JButton logButton()
    {
        JButton resetButton = new JButton("Open Log");
        resetButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) {
                    JFrame logFrame = new JFrame();
                    logFrame.setSize(400,400);
                    JTextArea logText = new JTextArea();
                    BufferedReader reader;
                    try
                    {
                        reader = new BufferedReader(new FileReader(".log"));
                        for(String line = reader.readLine();line != null;line = reader.readLine())
                            logText.append(line+"\n");

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    JScrollPane log = new JScrollPane(logText);
                    logFrame.add(log);
                    logFrame.setVisible(true);

                }
            });
        return resetButton; 
    }

    private JButton saveButton()
    {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) {
                    saveBusValues();
                }
            });
        return saveButton; 
    }
    private JButton saveJSONButton()
    {
        JButton saveJSONButton = new JButton("Save .json");
        saveJSONButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) {
                    JFrame saveFrame = new JFrame();
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setDialogTitle("File Save");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = fileChooser.showSaveDialog(saveFrame);

                    try
                    {
                        buses.writeJSON(fileChooser.getSelectedFile().getAbsolutePath()+".json");
                        if (result == JFileChooser.APPROVE_OPTION )
                            JOptionPane.showMessageDialog(saveFrame, 
                                            "File Saved");
                    } 
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(saveFrame, 
                                          "Save Error");
                    }
                }
            });
        return saveJSONButton;
    }
}