package org.example;

import java.sql.*;
import java.util.Scanner;

public class UserDataBase {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/event_handler";
        String username = "root";
        String password = "Nurme7";
        Scanner scanner = new Scanner(System.in);
        char again = 'y';

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            System.out.println("Connected to database!");

            while (again == 'y') {
                System.out.println("Choose one option (r, i, d, a, e)");
                System.out.println("r - reading data");
                System.out.println("i - inserting data");
                System.out.println("d - deleting data");

                char action = scanner.nextLine().charAt(0);

                if (action == 'i') {
                    System.out.println("Enter username");
                    String newUsername = scanner.nextLine();

                    System.out.println("Enter password");
                    String newPassword = scanner.nextLine();

                    System.out.println("Enter full name");
                    String newFullName = scanner.nextLine();

                    System.out.println("Enter email");
                    String newEmail = scanner.nextLine();

                    insertData(conn, newUsername, newPassword, newFullName, newEmail);

                } else if (action == 'r') {
                    readData(conn);
                } else if (action == 'd') {
                    System.out.println("Enter username you want to delete");
                    String deleteUser = scanner.nextLine();
                    deleteData(conn, deleteUser);
                } else {
                    System.out.println("Invalid input!");
                }

                System.out.println("Do you want to do something more? y/n");
                again = scanner.nextLine().charAt(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM users";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int count = 0;

        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String fullName = resultSet.getString("fullname");
            String email = resultSet.getString("email");

            String output = "User #%d: %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, username, password, fullName, email));
        }
    }

    private static void insertData(Connection conn, String username, String password, String fullname, String email) throws SQLException {
        String sql = "INSERT INTO users (username, password, fullname, email) "
                + "VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, fullname);
        preparedStatement.setString(4, email);

        int rowInserted = preparedStatement.executeUpdate();

        if (rowInserted > 0) {
            System.out.println("New user inserted");
        } else {
            System.out.println("Error");
        }
    }

    private static void deleteData(Connection conn, String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, username);

        if (preparedStatement.executeUpdate() > 0) {
            System.out.println("User was deleted");
        } else {
            System.out.println("Error");
        }
    }
}