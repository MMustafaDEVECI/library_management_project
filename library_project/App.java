package library_project;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    private static int input = -1;
    public static void main(String[] args)
    {
        try{
            Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase","deveci","password");
            Statement statment = myCon.createStatement();
            Scanner scanned = new Scanner(System.in);
            PreparedStatement preStat;
            String title;
            String writer;
            int i;
            int id = 1;
            String cell;
            boolean tableExists;
            while (input != 0){
                menu();
                switch (input){
                    case (1):
                        System.out.println("Enter the customer's cell phone number");
                        cell = scanned.next();
                        if (tableExists("customers", myCon)){
                            ResultSet rs = statment.executeQuery("SELECT cell FROM customers"); // difference between selecting all and cell
                            boolean custExist = false;
                            while (rs.next())
                            {
                                if (cell.equals(rs.getString("cell"))){
                                    custExist = true;
                                    break;
                                }
                            }
                            if (!custExist){
                                new Customer(myCon, cell);
                            }
                            //rs.close();
                        }
                        else{
                            statment.executeUpdate("CREATE TABLE customers(name VARCHAR(20), surName VARCHAR(20), cell VARCHAR(11) PRIMARY KEY, bookId INT, date VARCHAR(15))");
                            new Customer(myCon, cell);
                        }
                        preStat = myCon.prepareStatement("SELECT bookId FROM customers WHERE cell = ?");
                        preStat.setString(1, cell);
                        ResultSet rs = preStat.executeQuery();
                        while(rs.next()){
                            id = rs.getInt("bookId");
                        }
                        if (id != 0){
                            System.out.println("You need to return your book first to borrow new book");
                        }
                        else{
                            showBookInfo(statment);
                            scanned.nextLine();
                            System.out.println("Enter the title of the book customer wants to borrow");
                            title = scanned.nextLine();
                            System.out.println("Enter the writer of the book customer wants to borrow");
                            writer = scanned.nextLine();
                            System.out.println(title + writer);
                            preStat = myCon.prepareStatement("SELECT bookId FROM books WHERE title = ? AND writer = ?");
                            preStat.setString(1, title);
                            preStat.setString(2, writer);
                            rs = preStat.executeQuery();
                            id = 1;
                            while(rs.next()){
                                id = rs.getInt("bookId");
                            }
                            //rs.close();
                            preStat = myCon.prepareStatement("UPDATE customers SET bookId = ?  WHERE cell = ?");
                            preStat.setInt(1, id);
                            preStat.setString(2, cell);
                            preStat.executeUpdate();
                            preStat = myCon.prepareStatement("UPDATE books SET stockNum = stockNum - 1 WHERE title = ? and writer = ? ");
                            preStat.setString(1, title);
                            preStat.setString(2, writer);

                            i = preStat.executeUpdate();
                            System.out.println(i + " borrow has been made");
                        }
                        break;
                    case (2):
                        System.out.println("Enter the cell of customer");
                        cell =scanned.nextLine();
                        System.out.println("Enter the title of the book you want to return");
                        title = scanned.nextLine();
                        System.out.println("Enter the writer of the book you want to return");
                        writer = scanned.nextLine();
                        preStat = myCon.prepareStatement("SELECT bookId FROM customers WHERE cell = ?");
                        preStat.setString(1, cell);
                        rs = preStat.executeQuery();
                        id = 0;
                        while(rs.next()){
                            id = rs.getInt("bookId");
                        }
                        preStat = myCon.prepareStatement("SELECT bookId FROM books WHERE title = ? AND writer = ?");
                        preStat.setString(1, title);
                        preStat.setString(2, writer);
                        rs = preStat.executeQuery();
                        int id1 = 0;
                        while(rs.next()){
                            id1 = rs.getInt("bookId");
                        }
                        //rs.close();
                        if (id == id1){
                            preStat = myCon.prepareStatement("UPDATE books SET stockNum = stockNum + 1 WHERE title = ? and writer = ? ");
                            preStat.setString(1, title);
                            preStat.setString(2, writer);
                            preStat = myCon.prepareStatement("UPDATE customers SET bookId = 0 WHERE cell = ? ");
                            preStat.setString(1, cell);
                            i = preStat.executeUpdate();
                            System.out.println(i + " return has been made");
                        }
                        else{
                            System.out.println("You don't have this book");
                        }
                        break;
                    case (3):
                        showBookInfo(statment);
                        break;
                    case (4):
                        tableExists = tableExists("books", myCon);
                        if (!tableExists){
                            statment.executeUpdate("CREATE TABLE books(bookId INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(30), writer VARCHAR(30), pageNum INT, publisher VARCHAR(20), stockNum INT)");
                            System.out.println("Table has been created");
                        }
                        Book book = new Book();
                        book.bookInsert(myCon);
                        break;
                    case (5):
                        showLibrarianInfo(statment);
                        System.out.println("Enter id of employee you want to edit");
                        int choice = scanned.nextInt();
                        scanned.nextLine();
                        System.out.println("Enter feature of employee you want to edit");
                        String feature = scanned.next(); 
                        scanned.nextLine();
                        switch (feature){
                            case ("salary"):
                                System.out.println("Enter new value of feature");
                                int newSal = scanned.nextInt();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE librarians SET salary = ? WHERE id = ?");
                                preStat.setInt(1, newSal);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " salary has been updated");
                                break;
                            case ("name"):
                                System.out.println("Enter new value for name");
                                String newS = scanned.next();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE librarians SET name = ? WHERE id = ?");
                                preStat.setString(1, newS);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " name has been updated");
                                break;
                            case ("surname"):
                                System.out.println("Enter new value for surname");
                                newS = scanned.next();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE librarians SET surname = ? WHERE id = ?");
                                preStat.setString(1, newS);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " surname has been updated");
                                break;
                            case ("cell"):
                                System.out.println("Enter new value for cell phone");
                                newS = scanned.next();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE librarians SET cell = ? WHERE id = ?");
                                preStat.setString(1, newS);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " cell has been updated");
                                break;
                            default:
                                System.out.println("Enter a valid input");
                                break;
                        }
                        break;
                    case (6):
                        new Librarian(myCon);
                        break;
                    case (7):
                        showLibrarianInfo(statment);
                        System.out.println("Enter id of a person you want to delete");
                        id = scanned.nextInt();
                        scanned.nextLine();
                        preStat = myCon.prepareStatement("delete from librarians where id = ?");
                        preStat.setInt(1,id);
                        i = preStat.executeUpdate();
                        System.out.println(i + " removals has been made");
                        break;
                    case (8):
                        showBookInfo(statment);
                        System.out.println("Enter title of book you want to edit");
                        title = scanned.nextLine();
                        System.out.println("Enter writer of book you want to edit");
                        writer = scanned.nextLine(); 
                        System.out.println("Enter feature you want to edit");
                        feature = scanned.next();
                        scanned.nextLine();     
                        switch (feature){
                            case ("pageNum"):
                                System.out.println("Enter new value of pageNum");
                                int newPage = scanned.nextInt();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE books SET pageNum = ? WHERE title = ? AND writer = ?");
                                preStat.setInt(1, newPage);
                                preStat.setString(2, title);
                                preStat.setString(3, writer);
                                i = preStat.executeUpdate();
                                System.out.println(i + " pageNum has been updated");
                                break;
                            case ("publisher"):
                                System.out.println("Enter new value for publisher");
                                String newS = scanned.next();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE books SET publisher = ? WHERE title = ? AND writer = ?");
                                preStat.setString(1, newS);
                                preStat.setString(2, title);
                                preStat.setString(3, writer);
                                i = preStat.executeUpdate();
                                System.out.println(i + " publisher has been updated");
                                break;
                            case ("stockNum"):
                                System.out.println("Enter new value for stockNum");
                                newS = scanned.next();
                                scanned.nextLine();
                                preStat = myCon.prepareStatement("UPDATE books SET stockNum = ? WHERE title = ? AND writer = ?");
                                preStat.setString(1, newS);
                                preStat.setString(2, title);
                                preStat.setString(3, writer);
                                i = preStat.executeUpdate();
                                System.out.println(i + " stockNum has been updated");
                                break;
                            default:
                                System.out.println("Enter a valid input");
                                break;
                        }
                        break;
                    case (9):
                        showBookInfo(statment);
                        System.out.println("Enter title of a book you want to delete");
                        title = scanned.nextLine();
                        System.out.println("Enter writer of a book you want to delete");
                        writer = scanned.nextLine();
                        preStat = myCon.prepareStatement("DELETE FROM books WHERE title = ? AND writer = ?");
                        preStat.setString(1,title);
                        preStat.setString(2,writer);
                        i = preStat.executeUpdate();
                        System.out.println(i + " removals has been made");
                        break;
                    case (0):
                        break;
                }
            }
            scanned.close();
            statment.close();
            myCon.close();
            preStat = null;
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    private static void menu(){
        Scanner scanned = new Scanner(System.in);
        System.out.println("Choose what do you want to do");
        System.out.println("1 - Borrow Book");
        System.out.println("2 - Return Book");
        System.out.println("3 - Show Book Info");
        System.out.println("4 - Add Book");
        System.out.println("5 - Edit Librarian");
        System.out.println("6 - Add Librarian");
        System.out.println("7 - Delete Librarian");
        System.out.println("8 - Edit Book");
        System.out.println("9 - Delete Book");
        System.out.println("0 - End Program");
        input = scanned.nextInt();
        scanned.nextLine();
    }
    private static void showBookInfo(Statement statment){
        try{
            ResultSet rs = statment.executeQuery("SELECT title,writer,stockNum,bookId FROM books WHERE stockNum <> 0");
            while (rs.next()){
                int id = rs.getInt("bookId");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                int num = rs.getInt("stockNum");
                System.out.println(id + " " + title + " " +writer + " " + num);
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    private static void showLibrarianInfo(Statement statment){
        try{
            ResultSet rs = statment.executeQuery("SELECT * FROM librarians");
            while (rs.next()){
                int Id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String num = rs.getString("cell");
                int salary = rs.getInt("salary");
                System.out.println( Id + " " + name + " " + surname + " " + num + " " + salary);
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public static boolean tableExists(String name, Connection myCon){
        try{
            DatabaseMetaData dbm = myCon.getMetaData();
            ResultSet rs = dbm.getTables(null, null, name, null);
            boolean exists = false;
            while (rs.next()) {
                String table = rs.getString("TABLE_NAME");
                if (name.equals(table)) {
                    exists = true;
                    break;
                }
            }
            return exists;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
}