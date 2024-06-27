
package studentmanagementsystem; 
import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "asaas1234"; 

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            while (true) {
               
           System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
           System.out.println("~ WELCOME TO DEV STUDENT MANAGMENT SYSTEM ~");
           System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

                System.out.println("[1] --> Insert Student");
                System.out.println("[2] --> View Student by ID");
                System.out.println("[3] --> Update Student");
                System.out.println("[4] --> View All Students");
                System.out.println("[5] --> Delete Student");
                System.out.println("[6] --> Exit");
                System.out.print("Choose an option: ");

                int option = scanner.nextInt();
                scanner.nextLine(); 

                switch (option) {
                    case 1:
                        insertStudent(scanner, connection);
                        break;
                    case 2:
                        viewStudentById(scanner, connection);
                        break;
                    case 3:
                        updateStudent(scanner, connection);
                        break;
                    case 4:
                        viewAllStudents(connection);
                        break;
                    case 5:
                        deleteStudent(scanner, connection);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertStudent(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter student grade: ");
        String grade = scanner.nextLine();

        String query = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, grade);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    private static void viewStudentById(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID to view: ");
        int id = scanner.nextInt();

        String query = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String grade = resultSet.getString("grade");
                    System.out.printf("ID: %d, Name: %s, Age: %d, Grade: %s%n", id, name, age, grade);
                } else {
                    System.out.println("Student not found.");
                }
            }
        }
    }

    private static void updateStudent(Scanner scanner, Connection connection) throws SQLException  {
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new student age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter new student grade: ");
        String grade = scanner.nextLine();

        String query = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, grade);
            preparedStatement.setInt(4, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }

    private static void viewAllStudents(Connection connection) throws SQLException {
        String query = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\nAll Students:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String grade = resultSet.getString("grade");
                System.out.printf("ID: %d, Name: %s, Age: %d, Grade: %s%n", id, name, age, grade);
            }
        }
    }

    private static void deleteStudent(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
}
