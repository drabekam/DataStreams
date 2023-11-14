import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.IOException;

public class DataStreamsGUI {

    // JTextArea components for displaying the original and filtered text
    private JTextArea originalTextArea;
    private JTextArea filteredTextArea;
    private JTextField searchTextField;

    // Main window frame
    private JFrame frame;


    // Method to initialize and show the main GUI window
    public void createAndShowGUI() {
        frame = new JFrame("DataStreams");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        setupComponents();
        frame.pack();
        frame.setVisible(true);
    }

    // Method to set up the GUI component
    private void setupComponents() {
        originalTextArea = new JTextArea(20, 40);
        filteredTextArea = new JTextArea(20, 40);
        searchTextField = new JTextField(20);
    //Creates scrolling
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(originalTextArea));
        panel.add(new JScrollPane(filteredTextArea));
        frame.add(panel, BorderLayout.CENTER);
    //Buttons
        JPanel controlPanel = new JPanel();
        JButton loadButton = new JButton("Load File");
        JButton searchButton = new JButton("Search File");
        JButton quitButton = new JButton("Quit");

        controlPanel.add(loadButton);
        controlPanel.add(searchTextField);
        controlPanel.add(searchButton);
        controlPanel.add(quitButton);
        frame.add(controlPanel, BorderLayout.SOUTH);
    //setting action listeners for buttons
        loadButton.addActionListener(e -> loadFile());
        searchButton.addActionListener(e -> searchFile());
        quitButton.addActionListener(e -> System.exit(0));
    }

    //method for laoading the file and sidplaying the contents

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            Path filePath = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
            try (Stream<String> stream = Files.lines(filePath)) {
                originalTextArea.setText("");
                stream.forEach(line -> originalTextArea.append(line + "\n"));
            } catch (IOException e) {
                //shows an error if cant read
                JOptionPane.showMessageDialog(frame, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //method for seraching the file
    private void searchFile() {
        String searchText = searchTextField.getText();
        String originalText = originalTextArea.getText();
        filteredTextArea.setText("");

        try (Stream<String> stream = Stream.of(originalText.split("\n"))) {
            stream.filter(line -> line.contains(searchText))
                    .forEach(line -> filteredTextArea.append(line + "\n"));
        } catch (Exception e) {
            //shows error
            JOptionPane.showMessageDialog(frame, "Error processing file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

