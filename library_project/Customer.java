package library_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Customer {
    String name;
    String surName;
    String cellNumber; // primary
    String date;
    public Customer(Connection myCon, String cell){
        Scanner scanned = new Scanner(System.in);
        System.out.println("Enter customer's name");
        String name = scanned.nextLine();
        System.out.println("Enter customer's surname");
        String surName = scanned.nextLine();
        try {
            PreparedStatement preStat = myCon.prepareStatement("INSERT INTO customers(name,surname,cell,bookId) VALUES(?,?,?,0)");
            preStat.setString(1, name);
            preStat.setString(2, surName);
            preStat.setString(3, cell);
            int i = preStat.executeUpdate();
            System.out.println(i + " customer has been created");
            preStat.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
