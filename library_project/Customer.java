package library_project;

public class Customer {
    String name;
    String surName;
    String cellNumber; // primary
    //int bookId;
    String date;
    public Customer(String name, String surName, String cellNumber, String date){
        this.name = name;
        this.surName = surName;
        this.cellNumber = cellNumber;
        //this.bookId = bookId;
        this.date = date;
    }
}
