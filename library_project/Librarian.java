package library_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;



public class Librarian { //primary -> id
    String name;
    String surName;
    String cellNumber;
    int salary;
    public Librarian(Connection myCon){
        try{
            Statement statment = myCon.createStatement();
            Scanner scanned = new Scanner(System.in);
            System.out.println("Enter first name of librarian");
            String lName = scanned.next();
            scanned.nextLine();
            System.out.println("Enter last name of librarian");
            String lSurname = scanned.next();
            scanned.nextLine();
            System.out.println("Enter cell number of librarian");
            String cellNumber = scanned.next();
            scanned.nextLine();
            System.out.println("Enter salary of librarian");
            int salary = scanned.nextInt();
            scanned.nextLine();
            boolean tableExists = App.tableExists("librarians",myCon);
            if(!tableExists){
                statment.executeUpdate("CREATE TABLE librarians(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), surName VARCHAR(20), cell VARCHAR(20), salary INT)");
            }
            PreparedStatement preStat = myCon.prepareStatement("INSERT INTO librarians(name,surName,cell,salary) VALUES(?,?,?,?)");
            preStat.setString(1, lName);
            preStat.setString(2, lSurname);
            preStat.setString(3, cellNumber);
            preStat.setInt(4, salary);
            preStat.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
