import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ReportExporter extends JFrame implements ActionListener {
    private JComboBox<String> reportComboBox;
    private JButton exportButton;

    public ReportExporter() {
        setTitle("Report Exporter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Report Label and Combo Box
        JLabel reportLabel = new JLabel("Select Report:");
        String[] reports = {"Report 1", "Report 2", "Report 3"}; // Replace with actual report names
        reportComboBox = new JComboBox<>(reports);

        // Export Button
        exportButton = new JButton("Export Report");
        exportButton.addActionListener(this);

        // Layout
        setLayout(new FlowLayout());
        add(reportLabel);
        add(reportComboBox);
        add(exportButton);

        pack();
        setVisible(true);
        setSize(400,75);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exportButton) {
            String selectedReport = (String) reportComboBox.getSelectedItem();

            switch (selectedReport) { //change to actual report names
                case "Report 1":
                    exportReport1();
                    break;
                case "Report 2":
                    exportReport2();
                    break;
                case "Report 3":
                    exportReport3();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid report selected.");
            }
        }
    }

    private void exportReport1() {
        String reportContent = "This is Report 1 content."; // Replace with actual report content
        exportToFile("Report1.txt", reportContent);
    }

    private void exportReport2() {
        String reportContent = "This is Report 2 content."; // Replace with actual report content
        exportToFile("Report2.txt", reportContent);
    }

    private void exportReport3() {
        String reportContent = "This is Report 3 content."; // Replace with actual report content
        exportToFile("Report3.txt", reportContent);
    }

    private void exportToFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
            JOptionPane.showMessageDialog(this, "Report exported successfully to " + fileName,"", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage(),"", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ReportExporter();
            }
        });
    }
}
