package org.example;

import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/events";
        String username = "root";
        String password = "Kapsas123";
        Scanner scanner = new Scanner(System.in);
        char again = 'y';

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            System.out.println("Connected to database!");
            while (again == 'y') {
                System.out.println("Choose an option (a,s,l,g,p,t,r,d)");
                System.out.println("a - add event (User access only!)");
                System.out.println("s - search by date (yyyy-mm-dd)");
                System.out.println("l - search by location (Tallinn, Tartu, Else)");
                System.out.println("g - search by genre (Concert, Theater, Cinema, Else)");
                System.out.println("p - search by price range");
                System.out.println("t - search by type of event (Adult, Family, Children)");
                System.out.println("r - get all events");
                System.out.println("d - delete event (User access only)");
                char action = scanner.nextLine().charAt(0);
                if (action == 'a') {
                    System.out.println("Add the event");

                    System.out.println("Username: ");
                    String usernameInput = scanner.nextLine();

                    System.out.println("Password: ");
                    String passwordInput = scanner.nextLine();

                    if (authenticateUser(conn, usernameInput, passwordInput)) {
                        System.out.println("Event name: ");
                        String eventname = scanner.nextLine();
                        System.out.println("Event date (yyyy-mm-dd) : ");
                        String eventdate = scanner.nextLine();
                        System.out.println("Location (Tallinn, Tartu, Else) : ");
                        String location = scanner.nextLine();
                        System.out.println("Genre (Concert, Theater, Cinema, Else) : ");
                        String genre = scanner.nextLine();
                        System.out.println("Ticket price: ");
                        String ticket = scanner.nextLine();
                        System.out.println("Type of event (Adult, Family, Children) : ");
                        String typeofevent = scanner.nextLine();

                        addEvent(conn, usernameInput, passwordInput, eventname, eventdate, location, genre, ticket, typeofevent);
                    } else {
                        System.out.println("User not found. Event addition not allowed.");
                    }
                } else if (action == 's') {
                    System.out.println("Enter the date (yyyy-mm-dd) of the events you want to search");
                    String searchEventDate = scanner.nextLine();
                    searchDataByDate(conn, searchEventDate);
                } else if (action == 'l') {
                    System.out.println("Enter the location of the events you want to search");
                    String searchEventLocation = scanner.nextLine();
                    searchDataByLocation(conn, searchEventLocation);
                } else if (action == 'g') {
                    System.out.println("Enter the genre you want");
                    String searchGenre = scanner.nextLine();
                    searchDataByGenre(conn, searchGenre);
                } else if (action == 'p') {
                    System.out.println("Enter the min price: ");
                    double minPrice = scanner.nextDouble();
                    System.out.println("Enter the max price: ");
                    double maxPrice = scanner.nextDouble();
                    searchDataByPrice(conn, minPrice, maxPrice);
                } else if (action == 't') {
                    System.out.println("Enter the type event you want to search");
                    String searchType = scanner.nextLine();
                    searchDataByType(conn, searchType);
                } else if (action == 'r') {
                    readData(conn);
                } else if (action == 'd') {
                    System.out.println("Delete the event");
                    System.out.println("Username: ");
                    String usernameInput = scanner.nextLine();
                    System.out.println("Password: ");
                    String passwordInput = scanner.nextLine();
                    if (authenticateUser(conn, usernameInput, passwordInput)) {
                        System.out.println("Eventname: ");
                        String eventname = scanner.nextLine();
                        deleteData(conn, usernameInput, passwordInput, eventname);
                    } else {
                        System.out.println("Eventname not found. Nothing to delete.");
                    }
                } else {
                    System.out.println("Invalid input!");
                }
                System.out.println("Do you want to do something more? y/n ");
                again = scanner.next().charAt(0);
                scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchDataByDate(Connection conn, String searchEventDate) throws SQLException {
        String sql = "SELECT eventdate, eventname, location, genre, ticket, typeofevent FROM events WHERE eventdate = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, searchEventDate);

        ResultSet resultSet = statement.executeQuery();

        int count = 0;

        while (resultSet.next()) {
            String eventdate = resultSet.getString(1);
            String eventname = resultSet.getString(2);
            String location = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String ticket = resultSet.getString(5);
            String typeofevent = resultSet.getString(6);

            String output = "Event #%d: %s - %s - %s - %s - %s- %s";
            System.out.println(String.format(output, ++count, eventdate, eventname, location, genre, ticket, typeofevent));
        }
    }

    private static void searchDataByLocation(Connection conn, String searchEventLocation) throws SQLException {
        String sql = "SELECT * FROM events WHERE location LIKE ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%" + searchEventLocation + "%");

        ResultSet resultSet = statement.executeQuery();

        int count = 0;

        while (resultSet.next()) {
            String eventdate = resultSet.getString(1);
            String eventname = resultSet.getString(2);
            String location = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String ticket = resultSet.getString(5);
            String typeofevent = resultSet.getString(6);

            String output = "Event #%d: %s - %s - %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, eventdate, eventname, location, genre, ticket, typeofevent));
        }
    }

    private static void searchDataByType(Connection conn, String searchType) throws SQLException {
        String sql = "SELECT * FROM events WHERE typeofevent LIKE ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%" + searchType + "%");

        ResultSet resultSet = statement.executeQuery();

        int count = 0;

        while (resultSet.next()) {
            String eventdate = resultSet.getString(1);
            String eventname = resultSet.getString(2);
            String location = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String ticket = resultSet.getString(5);
            String typeofevent = resultSet.getString(6);

            String output = "Event #%d: %s - %s - %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, eventdate, eventname, location, genre, ticket, typeofevent));
        }
    }

    private static void searchDataByPrice(Connection conn, double minPrice, double maxPrice) throws SQLException {
        String sql = "SELECT * FROM events WHERE ticket >= ? AND ticket <= ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setDouble(1, minPrice);
        statement.setDouble(2, maxPrice);

        ResultSet resultSet = statement.executeQuery();

        int count = 0;

        while (resultSet.next()) {
            String eventdate = resultSet.getString("eventdate");
            String eventname = resultSet.getString("eventname");
            String location = resultSet.getString("location");
            String genre = resultSet.getString("genre");
            int ticket = resultSet.getInt("ticket");
            String typeofevent = resultSet.getString("typeofevent");

            // Print event details
            String output = "Event #%d: %s - %s - %s - %s - %d - %s";
            System.out.println(String.format(output, ++count, eventdate, eventname, location, genre, ticket, typeofevent));
        }
    }

    private static void searchDataByGenre(Connection conn, String searchGenre) throws SQLException {
        String sql = "SELECT * FROM events WHERE genre LIKE ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%" + searchGenre + "%");

        ResultSet resultSet = statement.executeQuery();

        int count = 0;

        while (resultSet.next()) {
            String eventdate = resultSet.getString(1);
            String eventname = resultSet.getString(2);
            String location = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String ticket = resultSet.getString(5);
            String typeofevent = resultSet.getString(6);

            String output = "Event #%d: %s - %s - %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, eventdate, eventname, location, genre, ticket, typeofevent));
        }
    }

    private static void readData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM events";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int count = 0;

        while (resultSet.next()) {
            String eventdate = resultSet.getString(1);
            String eventname = resultSet.getString(2);
            String location = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String ticket = resultSet.getString(5);
            String typeofevent = resultSet.getString(6);


            String output = "Event #%d: %s - %s - %s - %s - %s- %s";
            System.out.println(String.format(output, ++count, eventdate, eventname, location, genre, ticket, typeofevent));
        }
    }

    private static void addEvent(Connection conn, String username, String password, String eventname, String eventdate, String location, String genre, String ticket, String typeofevent) throws SQLException {
        if (authenticateUser(conn, username, password)) {
            String sql = "INSERT INTO events (eventname, eventdate, location, genre, ticket, typeofevent) "
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, eventname);
            statement.setString(2, eventdate);
            statement.setString(3, location);
            statement.setString(4, genre);
            statement.setString(5, ticket);
            statement.setString(6, typeofevent);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("New event is added");
            } else {
                System.out.println("Something went wrong");
            }
        }
    }
    private static void deleteData(Connection conn, String username, String password, String eventname) throws SQLException {
        if (authenticateUser(conn, username, password)) {
            String sql = "DELETE FROM events WHERE eventname = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, eventname);
            if (statement.executeUpdate() > 0) {
                System.out.println("Event was deleted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        }
    }

    private static boolean authenticateUser(Connection conn, String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }
}

