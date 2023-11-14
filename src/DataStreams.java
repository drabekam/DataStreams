import javax.swing.SwingUtilities;

public class DataStreams {

    // Main method
    public static void main(String[] args) {
        //helps with thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Creating an instance of the DataStreamsGUI class and setting up the GUI
                new DataStreamsGUI().createAndShowGUI();
            } catch (Exception e) {
                // Printing the stack trace of any exception that occurs during the initialization of the GUI
                e.printStackTrace();
            }
        });
    }
}
