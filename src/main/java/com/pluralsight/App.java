package com.pluralsight;

import java.sql.*;

public class App {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        // 1. open a connection to the database
        Connection connection = DriverManager.getConnection(url,username,password);

        //  define your query
        String query = """
                SELECT ProductName, ProductID, UnitPrice, UnitsInStock
                FROM products
                WHERE ProductName LIKE ? AND UnitPrice < ?;
                """;

//        String query = """
//                SELECT ProductName, ProductID, UnitPrice, UnitsInStock
//                FROM products
//                WHERE UnitPrice BETWEEN ? AND ?;
//                """;

        // create statement
        // the statement is tied to the open connection
        PreparedStatement statement = connection.prepareStatement(query);

        // String searchTerm = "%CHO%"; statement.setString(1,searchTerm);
        // set parameters

        statement.setString(1,"%CHO%");
        statement.setDouble(2, 15.00);

//      statement.setDouble(1, 5.00);
//      statement.setDouble(2, 15.00);

        // 2. Execute your query
        ResultSet results = statement.executeQuery();

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
            System.out.println("************************************************");
        }

        // 3. Close the connection
        results.close();
        statement.close();
        connection.close();

    }
}
