package library_project;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Book { // primary id
    String title;
    String writer;
    int pageNumber;
    String publisher;
    int stockNum;
    public Book(String title, String writer, int pageNumber, String publisher, int stockNum)
    {
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.publisher = publisher;
        this.stockNum = stockNum;
    }
    public void bookInsert(Connection myCon){
        try{
            PreparedStatement preStat = myCon.prepareStatement("INSERT INTO books VALUES(?,?,?,?,?)");
            preStat.setString(1, this.title);
            preStat.setString(2, this.writer);
            preStat.setInt(3, this.pageNumber);
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
