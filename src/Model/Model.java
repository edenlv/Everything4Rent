package Model;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Model {
    public static String path = System.getProperty("user.dir");
    public static Connection conn = connect();
    public static String username = null;
    public static final String mailSubject = "You've successfully registered to Everything4Rent!";

    public static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+path+"\\src\\Model\\users.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }


    public static boolean registerUser(String name, String pass, String email) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Please Wait");
        alert.setContentText("Please wait while we are completing your registration...");
        alert.setTitle("Registering...");
        alert.show();

        String sql = "INSERT INTO Users(Username,Password, Email) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, pass);
            pstmt.setString(3,email);
            int ret = pstmt.executeUpdate();
            if (ret!=0) {
                sendConfirmationMail(email,mailSubject, getCustomEmail(name));
                alert.close();
                return true;
            }
            else {
                alert.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        alert.close();
        return false;
    }

    public static String login(String User,String Pass) {

        String sql = "SELECT Users.Username, Users.Password FROM Users WHERE Username = ? and Users.Password= ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, User);
            pstmt.setString(2, Pass);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            // loop through the result set
            if (rs.next()) {
                System.out.println("Success!!");

                count++;
                String user = rs.getString("Username");
                username = user;
                return user;
            }

            //didnt find record
            if (count == 0) {

                //try to find if the user name is correct
                String sql1 = "SELECT Users.Username, Users.Password FROM Users WHERE Username = ? ";
                PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                pstmt1.setString(1, User);
                rs = pstmt1.executeQuery();
                if (rs.next()) {
                    System.out.println("password not found");
                    return null;
                } else {
                    System.out.println("no record");
                    return null;
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;


    }

    /**
     * Insert a new row into the warehouses table
     *
     */

    // insert to product
    public static boolean insert_prod(String ProdName, String catg,String bustype,int cost,String owner,String start_date,String end_date) {

        String sql = "INSERT INTO Product(ProdName,Category,Bussiness_type,Cost,S_date,E_date,Owner) VALUES(?,?,?,?,?,?,?)";
        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, ProdName);
            pstmt.setString(2, catg);
            pstmt.setString(3, bustype);
            pstmt.setInt(4, cost);
            pstmt.setString(5, start_date);
            pstmt.setString(6, end_date);
            pstmt.setString(7, owner);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    //update product
    public static boolean updateProduct(int id ,String ProdName, String catg,String bustype,int cost,String owner,String start_date,String end_date) {
        String sql = "UPDATE Product SET ProdName = ? , "
                + "Category = ?,"
                + "Bussiness_type = ?,"
                + "cost = ?,"
                + "S_date = ?,"
                + "E_date = ?"
                + "WHERE ID = ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, ProdName);
            pstmt.setString(2, catg);
            pstmt.setString(3, bustype);
            pstmt.setInt(4, cost);
            pstmt.setString(5, start_date);
            pstmt.setString(6, end_date);
            pstmt.setInt(7, id);
            // update

            if(pstmt.executeUpdate()!=0)
                return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<String[]> getAllProducts(){
      return productsearch(null,null,null,null,username,null,null);

    }

    //find all product
    public static ArrayList<String[]> productsearch(String ProdName, String catg, String bustype, String cost, String owner, String start_date, String end_date){
        int field =1;
        ArrayList<String[]> ans = new ArrayList<>();
        String sql ="";
        //select by all params
        if(ProdName!=null && catg!=null && bustype!=null && cost != null && owner !=null && start_date !=null && end_date !=null )
            sql = "SELECT ID, Product.ProdName, Category,Bussiness_type,Cost,S_date,E_date,Owner FROM Product WHERE ProdName = ? and Category = ? and bussiness_type = ? and Cost = ?  and S_date = ? and E_date = ?  and Owner = ?  ";  //" +
            //by Owner
        else if(owner !=null)
            sql = "SELECT ID, Product.ProdName, Category,Bussiness_type,Cost,S_date,E_date,Owner FROM Product WHERE Owner = ?  ";

        if(sql.equals(""))
            return null;

        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            if(ProdName!=null) {
                pstmt.setString(field, ProdName);
                field++;
            }
            if(catg!=null) {
                pstmt.setString(field, catg);
                field++;
            }
            if(bustype!=null) {
                pstmt.setString(field, bustype);
                field++;
            }
            if(cost!=null) {
                pstmt.setInt(field, Integer.parseInt(cost));
                field++;
            }
            if(start_date!=null) {
                pstmt.setString(field, start_date);
                field++;
            }
            if(end_date!=null) {
                pstmt.setString(field, end_date);
                field++;
            }
            if(owner!=null) {
                pstmt.setString(field, owner);
            }

            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            // loop through the result set

            while (rs.next()) {
                ArrayList<String> str = new ArrayList<>();
                str.add(rs.getString( "ID"));
                str.add(rs.getString( "ProdName"));
                str.add(rs.getString("Category"));
                str.add(rs.getString("Bussiness_type"));
                str.add(rs.getString("Cost"));
                str.add(rs.getString("S_date"));
                str.add(rs.getString("E_date"));
                String[] strArr = new String[7];
                ans.add(str.toArray(strArr));
                count++;
            }
            //didnt find records
            if(count==0){
                System.out.println("no record");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }

    //delete product
    public static boolean deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE ID = ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            if(pstmt.executeUpdate()!=0)
                return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String[] getProduct(int id){
        String sql = "SELECT ID, Product.ProdName, Category,Bussiness_type,Cost,S_date,E_date,Owner FROM Product WHERE ID = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                ArrayList<String> str = new ArrayList<>();
                str.add(rs.getString( "ID"));
                str.add(rs.getString( "ProdName"));
                str.add(rs.getString("Category"));
                str.add(rs.getString("Bussiness_type"));
                str.add(rs.getString("Cost"));
                str.add(rs.getString("S_date"));
                str.add(rs.getString("E_date"));
                String[] strArr = new String[7];
                str.toArray(strArr);
                return strArr;
            }
        } catch (SQLException e){e.printStackTrace();}
        return null;
    }

    public static void sendConfirmationMail(String toMail, String subject, String msg){
        final String username = "bgu.everything4rent@gmail.com";
        final String password = "sise123456";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toMail));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCustomEmail(String username){
        final String start = "Hello, ";
        final String end = "\n\nWelcome to Everything4Rent. You've completed the registration proccess to our system. We wish you happy renting.\n" +
                "For customer support call 1-800-tamut-kvar" +
                "\n\nRegards,\nEverything4Rent by Idan, Yossi, Zohar and Eden";
        return start+username+end;
    }




}
