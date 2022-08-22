package library_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Book { // primary id
    String title;
    String writer;
    int pageNum;
    String publisher;
    int stockNum;
    public Book()
    {
        System.out.println("Enter title of book");
        Scanner scanned = new Scanner(System.in);
        this.title = scanned.nextLine();
        System.out.println("Enter full name of writer of book");
        this.writer = scanned.nextLine();
        System.out.println("Enter page number of book");
        this.pageNum = scanned.nextInt();
        scanned.nextLine();
        System.out.println("Enter publisher of book");
        this.publisher = scanned.nextLine();
        System.out.println("Enter stock number of book");
        this.stockNum = scanned.nextInt();
        scanned.nextLine();
        scanned.close();
    }
    public void bookInsert(Connection myCon){
        try{
            PreparedStatement preStat = myCon.prepareStatement("INSERT INTO books(title,writer,pageNum,publisher,stockNum) VALUES(?,?,?,?,?)");
            preStat.setString(1, this.title);
            preStat.setString(2, this.writer);
            preStat.setInt(3, this.pageNum);
            preStat.setString(4, this.publisher);
            preStat.setInt(5, this.stockNum);
            int i = preStat.executeUpdate();
            System.out.println( this.stockNum + " copy of " + i + " book has been added");

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
