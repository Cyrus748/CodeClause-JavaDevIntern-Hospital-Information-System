import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HospitalInformationSystem {

    private static Connection connectToDatabase() {
        try {
            // Replace with your database details
            String url = "jdbc:mysql://localhost:3306/hospital_db";
            String user = "root";
            String password = "Aditya748@";

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection connection = connectToDatabase();
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database. Exiting.");
            return;
        }

        JFrame frame = new JFrame("Hospital Information System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Patient Records Tab
        JPanel patientPanel = new JPanel(new BorderLayout());
        JTextArea patientTextArea = new JTextArea();
        patientPanel.add(new JScrollPane(patientTextArea), BorderLayout.CENTER);

        JPanel patientControlPanel = new JPanel();
        JButton addPatientButton = new JButton("Add Patient");
        JButton viewPatientsButton = new JButton("View Patients");
        patientControlPanel.add(addPatientButton);
        patientControlPanel.add(viewPatientsButton);
        patientPanel.add(patientControlPanel, BorderLayout.SOUTH);

        tabbedPane.add("Patient Records", patientPanel);

        // Appointments Tab
        JPanel appointmentPanel = new JPanel(new BorderLayout());
        JTextArea appointmentTextArea = new JTextArea();
        appointmentPanel.add(new JScrollPane(appointmentTextArea), BorderLayout.CENTER);

        JPanel appointmentControlPanel = new JPanel();
        JButton addAppointmentButton = new JButton("Add Appointment");
        JButton viewAppointmentsButton = new JButton("View Appointments");
        appointmentControlPanel.add(addAppointmentButton);
        appointmentControlPanel.add(viewAppointmentsButton);
        appointmentPanel.add(appointmentControlPanel, BorderLayout.SOUTH);

        tabbedPane.add("Appointments", appointmentPanel);

        // Event Listeners
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter patient name:");
                String age = JOptionPane.showInputDialog("Enter patient age:");
                String medicalHistory = JOptionPane.showInputDialog("Enter medical history:");

                try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO patients (name, age, medical_history) VALUES (?, ?, ?)");) {
                    stmt.setString(1, name);
                    stmt.setInt(2, Integer.parseInt(age));
                    stmt.setString(3, medicalHistory);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Patient added successfully.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding patient: " + ex.getMessage());
                }
            }
        });

        viewPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM patients");) {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("ID: ").append(rs.getInt("id"))
                              .append(", Name: ").append(rs.getString("name"))
                              .append(", Age: ").append(rs.getInt("age"))
                              .append(", Medical History: ").append(rs.getString("medical_history"))
                              .append("\n");
                    }
                    patientTextArea.setText(result.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving patients: " + ex.getMessage());
                }
            }
        });

        addAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientId = JOptionPane.showInputDialog("Enter patient ID:");
                String date = JOptionPane.showInputDialog("Enter appointment date (YYYY-MM-DD):");
                String time = JOptionPane.showInputDialog("Enter appointment time (HH:MM):");

                try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO appointments (patient_id, date, time) VALUES (?, ?, ?)");) {
                    stmt.setInt(1, Integer.parseInt(patientId));
                    stmt.setString(2, date);
                    stmt.setString(3, time);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Appointment added successfully.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding appointment: " + ex.getMessage());
                }
            }
        });

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");) {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("ID: ").append(rs.getInt("id"))
                              .append(", Patient ID: ").append(rs.getInt("patient_id"))
                              .append(", Date: ").append(rs.getString("date"))
                              .append(", Time: ").append(rs.getString("time"))
                              .append("\n");
                    }
                    appointmentTextArea.setText(result.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving appointments: " + ex.getMessage());
                }
            }
        });

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
