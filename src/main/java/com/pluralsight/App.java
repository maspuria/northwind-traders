package com.pluralsight;

import java.sql.*;

public class App {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        displayProducts(url, username, password);
        displayCustomers(url, username, password);
    }

    private static void displayProducts(String url, String username, String password) {
        //  define your query
        String query = """
                        SELECT ProductName, ProductID, UnitPrice, UnitsInStock
                        FROM products;
                        """;

        try (// 1. open a connection to the database
             Connection connection = DriverManager.getConnection(url, username, password);
             // create statement
             // the statement is tied to the open connection
             PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet results = statement.executeQuery()) {
                // process the results
                while (results.next()) {
                    String products = results.getString("ProductName");
                    int productID = results.getInt("ProductID");
                    double price = results.getDouble("UnitPrice");
                    int stock = results.getInt("UnitsInStock");

                    System.out.println("Product ID: " + productID);
                    System.out.println("Product Name: " + products);
                    System.out.println("Price: $" + price);
                    System.out.println("Stock: " + stock);
                    System.out.println("-------------------------------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println("There was an error retrieving data. Try again please.");
            e.printStackTrace();
        }
    }

    private static void displayCustomers(String url, String username, String password) {
        String query = """
                SELECT ContactName, CompanyName, City, Country, Phone
                FROM customers
                ORDER BY Country;
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet results = statement.executeQuery()) {
                // process the results
                while (results.next()) {
                    String contactName = results.getString("ContactName");
                    String companyName = results.getString("CompanyName");
                    String city = results.getString("City");
                    String country = results.getString("Country");
                    String phoneNumber = results.getString("Phone");

                    System.out.println("Contact Name: " + contactName);
                    System.out.println("Company Name: " + companyName);
                    System.out.println("City: " + city);
                    System.out.println("Country: " + country);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("************************************************");
                }
            }

        } catch (SQLException e) {
            System.out.println("There was an error retrieving data. Try again please.");
            e.printStackTrace();
        }

    }

}
