import java.sql.*;
import java.util.*;

class DB {
    static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";      
        String pass = "Surya@1234";
        return DriverManager.getConnection(url, user, pass);
    }
}

public class HospitalManagementSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver Error: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n==== HOSPITAL MANAGEMENT SYSTEM ====");
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> patientMenu();
                case 2 -> doctorMenu();
                case 3 -> appointmentMenu();
                case 4 -> {
                    System.out.println("Exiting... Thank You!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    // =================== PATIENT MENU ===================
    public static void patientMenu() {
        while (true) {
            System.out.println("\n=== PATIENT MENU ===");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> viewPatients();
                case 3 -> searchPatient();
                case 4 -> deletePatient();
                case 5 -> { return; }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    public static void addPatient() {
        try (Connection con = DB.getConnection()) {
            System.out.print("Enter Patient ID: ");
            int id = readInt();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = readInt();

            System.out.print("Enter Disease: ");
            String disease = sc.nextLine();

            String sql = "INSERT INTO patients VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, disease);
            ps.executeUpdate();

            System.out.println("Patient Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void viewPatients() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-5s | %-30s |\n", "ID", "Name", "Age", "Disease");
            System.out.println("----------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-5d | %-20s | %-5d | %-30s |\n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("disease"));
            }
            System.out.println("----------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchPatient() {
        try (Connection con = DB.getConnection()) {
            System.out.print("Enter Patient ID: ");
            int id = readInt();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM patients WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Patient Not Found!");
                return;
            }

            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-5s | %-30s |\n", "ID", "Name", "Age", "Disease");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("| %-5d | %-20s | %-5d | %-30s |\n",
                    rs.getInt("id"), rs.getString("name"),
                    rs.getInt("age"), rs.getString("disease"));
            System.out.println("----------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deletePatient() {
        try (Connection con = DB.getConnection()) {
            System.out.print("Enter Patient ID: ");
            int id = readInt();

            PreparedStatement ps = con.prepareStatement("DELETE FROM patients WHERE id=?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Patient Deleted!");
            else
                System.out.println("Patient Not Found!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =================== DOCTOR MENU ===================
    public static void doctorMenu() {
        while (true) {
            System.out.println("\n=== DOCTOR MENU ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> viewDoctors();
                case 3 -> searchDoctor();
                case 4 -> { return; }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    public static void addDoctor() {
        try (Connection con = DB.getConnection()) {
            System.out.print("Enter Doctor ID: ");
            int id = readInt();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Specialization: ");
            String spec = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO doctors VALUES (?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, spec);
            ps.executeUpdate();

            System.out.println("Doctor Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewDoctors() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");

            System.out.println("---------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-20s |\n", "ID", "Name", "Specialization");
            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("| %-5d | %-20s | %-20s |\n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("specialization"));
            }
            System.out.println("---------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchDoctor() {
        try (Connection con = DB.getConnection()) {
            System.out.print("Enter Doctor ID: ");
            int id = readInt();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM doctors WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Doctor Not Found!");
                return;
            }

            System.out.println("---------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-20s |\n", "ID", "Name", "Specialization");
            System.out.println("---------------------------------------------------------------");
            System.out.printf("| %-5d | %-20s | %-20s |\n",
                    rs.getInt("id"), rs.getString("name"), rs.getString("specialization"));
            System.out.println("---------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =================== APPOINTMENT MENU ===================
    public static void appointmentMenu() {
        while (true) {
            System.out.println("\n=== APPOINTMENT MENU ===");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> bookAppointment();
                case 2 -> viewAppointments();
                case 3 -> { return; }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    public static void bookAppointment() {
        try (Connection con = DB.getConnection()) {
            System.out.print("Enter Patient ID: ");
            int pid = readInt();

            System.out.print("Enter Doctor ID: ");
            int did = readInt();

            System.out.print("Enter Appointment Date (DD-MM-YYYY): ");
            String date = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?)");
            ps.setInt(1, pid);
            ps.setInt(2, did);
            ps.setString(3, date);
            ps.executeUpdate();

            System.out.println("Appointment Booked Successfully!");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static void viewAppointments() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");

            System.out.println("---------------------------------------------------------");
            System.out.printf("| %-12s | %-12s | %-15s |\n", "Patient ID", "Doctor ID", "Date");
            System.out.println("---------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("| %-12d | %-12d | %-15s |\n",
                        rs.getInt("patientId"), rs.getInt("doctorId"), rs.getString("date"));
            }
            System.out.println("---------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =================== HELPER METHODS ===================
    public static int readInt() {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine());
                return n;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }
}
