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
            PreparedStatement preStat;
            String title;
            String writer;
            int i;
            while (input != 0){
                Scanner scanned = new Scanner(System.in);
                menu();
                switch (input){
                    case (1):
                        System.out.println("Enter the customer's cell phone number");
                        // if table exists if customer exists ...
                        showBookInfo(statment);
                        System.out.println("Enter the title of the book you want to borrow");
                        title = scanned.nextLine();
                        System.out.println("Enter the writer of the book you want to borrow");
                        writer = scanned.nextLine();
                        preStat = myCon.prepareStatement("UPDATE books SET stockNum = stockNum - 1 WHERE title = ? and writer = ? ");
                        preStat.setString(1, title);
                        preStat.setString(2, writer);
                        int u = preStat.executeUpdate();
                        System.out.println(u + " borrow has been made");
                        break;
                    case (2):
                        System.out.println("Enter the title of the book you want to return");
                        title = scanned.nextLine();
                        System.out.println("Enter the writer of the book you want to return");
                        writer = scanned.nextLine();
                        preStat = myCon.prepareStatement("UPDATE books SET stockNum = stockNum + 1 WHERE title = ? and writer = ? ");
                        preStat.setString(1, title);
                        preStat.setString(2, writer);
                        i = preStat.executeUpdate();
                        System.out.println(i + " return has been made");
                        break;
                    case (3):
                        showBookInfo(statment);
                        break;
                    case (4): // CHECK IF TABLE EXISTS
                        System.out.println("Enter title of book");
                        title = scanned.nextLine();
                        System.out.println("Enter full name of writer of book");
                        writer = scanned.nextLine();
                        System.out.println("Enter page number of book");
                        int pageNum = scanned.nextInt();
                        System.out.println("Enter publisher of book");
                        String publisher = scanned.nextLine();
                        System.out.println("Enter stock number of book");
                        int stockNum = scanned.nextInt();
                        Book book = new Book(title,writer,pageNum,publisher,stockNum);
                        book.bookInsert(myCon);
                        break;
                    case (5):
                        showLibrarianInfo(statment);
                        System.out.println("Enter id of employee you want to edit");
                        int choice = scanned.nextInt();
                        System.out.println("Enter feature of employee you want to edit");
                        String feature = scanned.next(); // NEXT LINE DOESNT WORK WITH IF SWITCH?
                        
                        switch (feature){
                            case ("salary"):
                                System.out.println("Enter new value of feature");
                                int newSal = scanned.nextInt();
                                preStat = myCon.prepareStatement("UPDATE librarians SET salary = ? WHERE id = ?");
                                preStat.setInt(1, newSal);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " salary has been updated");
                                break;
                            case ("name"):
                                System.out.println("Enter new value for name");
                                String newS = scanned.next();
                                preStat = myCon.prepareStatement("UPDATE librarians SET name = ? WHERE id = ?");
                                preStat.setString(1, newS);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " name has been updated");
                                break;
                            case ("surname"):
                                System.out.println("Enter new value for surname");
                                newS = scanned.next();
                                preStat = myCon.prepareStatement("UPDATE librarians SET surname = ? WHERE id = ?");
                                preStat.setString(1, newS);
                                preStat.setInt(2, choice);
                                i = preStat.executeUpdate();
                                System.out.println(i + " surname has been updated");
                                break;
                            case ("cell"):
                                System.out.println("Enter new value for cell phone");
                                newS = scanned.next();
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
                        
                        break;
                    case (7):
                        System.out.println("Enter first name of librarian");
                        String lName = scanned.next();
                        System.out.println("Enter last name of librarian");
                        String lSurname = scanned.next();
                        System.out.println("Enter cell number of librarian");
                        String cellNumber = scanned.next();
                        System.out.println("Enter salary of librarian");
                        int salary = scanned.nextInt();
                        Librarian lib = new Librarian(lName,lSurname,cellNumber,salary);
                        //add to database
                        break;
                    case (8):
                        showLibrarianInfo(statment);
                        System.out.println("Enter id of a person you want to delete");
                        int id = scanned.nextInt();
                        preStat = myCon.prepareStatement("delete from librarians where id = ?");
                        preStat.setInt(1,id);
                        i = preStat.executeUpdate();
                        System.out.println(i + " removals has been made");
                        break;
                    case (9):
                        showBookInfo(statment);
                        System.out.println("Enter title of book you want to edit");
                        title = scanned.nextLine();
                        System.out.println("Enter writer of book you want to edit");
                        writer = scanned.nextLine(); 
                        System.out.println("Enter feature you want to edit");
                        feature = scanned.next(); // NEXT LINE DOESNT WORK WITH IF SWITCH?
                        
                        switch (feature){
                            case ("pageNum"):
                                System.out.println("Enter new value of pageNum");
                                int newPage = scanned.nextInt();
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
                                preStat = myCon.prepareStatement("UPDATE books SET publisher = ? WHERE title = ? AND writer = ?");
                                preStat.setString(1, newS);
                                preStat.setString(2, title);
                                preStat.setString(3, writer);
                                i = preStat.executeUpdate();
                                System.out.println(i + " publisher has been updated");
                                break;
                            case ("stockNum"):
                                System.out.println("Enter new value for surname");
                                newS = scanned.next();
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
                    case (10):
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
                        //Customer.addCustomer();
                        break;

                }
            }
            myCon.close();
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
        System.out.println("6 - Edit Customer");
        System.out.println("7 - Add Librarian");
        System.out.println("8 - Delete Librarian");
        System.out.println("9 - Edit Book");
        System.out.println("10 - Delete Book");
        System.out.println("0 - End Program");
        input = scanned.nextInt();
    }
    private static void showBookInfo(Statement statment){
        try{
            ResultSet rs = statment.executeQuery("SELECT title,writer,stockNum FROM books WHERE stockNum <> 0");
            while (rs.next()){
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                int num = rs.getInt("stockNum");
                System.out.println(title + " " +writer + " " + num);
            }
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
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    private static boolean tableExists(String name, Connection myCon){
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
        }
    }
}
