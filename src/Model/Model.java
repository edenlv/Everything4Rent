package Model;

        import javafx.scene.control.Alert;
        import sun.security.krb5.internal.crypto.Des;

        import javax.mail.*;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.Locale;
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


    //------------ PRODUCT---------------------


    // insert to product
    public static boolean insert_prod(String ProdName, String catg,int cost,String owner,String Description) {

        String sql = "INSERT INTO Product(ProdName,Category,Cost,Owner,Description) VALUES(?,?,?,?,?)";
        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, ProdName);
            pstmt.setString(2, catg);
            pstmt.setInt(3, cost);
            pstmt.setString(4, owner);
            pstmt.setString(5, Description);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    //update product
    public static boolean updateProduct(int id ,String ProdName, String catg,int cost,String Description) {
        String sql = "UPDATE Product SET ProdName = ? , "
                + "Category = ?,"
                + "cost = ?,"
                + "Description=?"
                + "WHERE ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, ProdName);
            pstmt.setString(2, catg);
            pstmt.setInt(3, cost);
            pstmt.setString(4, Description);
            pstmt.setInt(5,id);

            // update

            if(pstmt.executeUpdate()!=0)
                return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<String[]> getAllProducts(){
        return productsearch(null,null,null,null,username,null);

    }


    //find all product
    public static ArrayList<String[]> productsearch(String ID,String ProdName, String catg, String cost, String owner, String Description){
        int field =1;
        ArrayList<String[]> ans = new ArrayList<>();
        String sql = "SELECT ID,ProdName,Category,Cost,Owner,PackageID,Description From Product WHERE ";
        Boolean add = false;
        if(ID!=null && !ID.equals("")){
            sql = sql + "ID = ?";
            add = true;
        }
        if(ProdName!=null && !ProdName.equals("")){
            if(add)
                sql = sql + " and ProdName = ?";
            else
                sql = sql +"ProdName = ?";
            add = true;
        }
        if(catg!=null && !catg.equals("")){
            if(add)
                sql = sql + " and Category = ?";
            else
                sql = sql + " Category = ?";
            add = true;
        }
        if(cost!=null && !cost.equals("")){
            if(add)
                sql = sql + " and cost = ?";
            else
                sql = sql + " cost = ?";
            add = true;
        }
        if(owner!=null && !owner.equals("")){
            if(add)
                sql = sql + " and Owner = ?";
            else
                sql = sql + " Owner = ?";
        }
        if(Description!=null && !Description.equals("")){
            if(add)
                sql = sql + " and Description = ?";
            else
                sql = sql + " Description = ?";
        }

        if(sql.equals(""))
            return null;

        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){

            if (ID!=null && !ID.equals("")){
                pstmt.setInt(1,Integer.parseInt(ID));
                field++;
            }
            // set the value
            if(ProdName!=null && !ProdName.equals("")) {
                pstmt.setString(field, ProdName);
                field++;
            }
            if(catg!=null && !catg.equals("")) {
                pstmt.setString(field, catg);
                field++;
            }

            if(cost!=null && !cost.equals("")) {
                pstmt.setInt(field, Integer.parseInt(cost));
                field++;
            }

            if(owner!=null && !owner.equals("")) {
                pstmt.setString(field, owner);
                field++;
            }
            if(Description!=null && !Description.equals("")) {
                pstmt.setString(field, Description);

            }

            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            // loop through the result set

            while (rs.next()) {
                ArrayList<String> str = new ArrayList<>();
                str.add(rs.getString( "ID"));
                str.add(rs.getString( "ProdName"));
                str.add(rs.getString("Category"));
                str.add(rs.getString("Cost"));
                str.add(rs.getString("owner"));
                str.add(rs.getString("PackageID"));
                str.add(rs.getString("Description"));

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

    //----------------- PACKAGE-------------------

    public static boolean insert_pack(String PackName, String Business_Type,String StartDate,String EndDate,int TotalCost,String owner,String PackageName,String Description,String Status) {

        String sql = "INSERT INTO Product(Business_Type,StartDate,EndDate,TotalCost,Owner,PackageName,Description,Status) VALUES(?,?,?,?,?,?,?,?)";
        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, Business_Type);
            pstmt.setString(2, StartDate);
            pstmt.setString(3, EndDate);
            pstmt.setInt(4, TotalCost);
            pstmt.setString(5, owner);
            pstmt.setString(6, PackageName);
            pstmt.setString(7, Description);
            pstmt.setString(8, Status);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }








    public static String[] getProduct(int id){
            String sql = "SELECT ID,ProdName, Category,Cost,Owner,Description FROM Product WHERE ID = ?";
            try(PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()){
                    ArrayList<String> str = new ArrayList<>();
                    str.add(rs.getString( "ID"));
                    str.add(rs.getString( "ProdName"));
                    str.add(rs.getString("Category"));
                    str.add(rs.getString("Cost"));
                    str.add(rs.getString("Owner"));
                    str.add(rs.getString("Description"));
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


    public static boolean insert_pack(String Business_Type,String StartDate,String EndDate,int TotalCost,String owner,String PackageName,String Description,ArrayList<String> prodID) {

        String sql = "INSERT INTO Package(Business_Type,StartDate,EndDate,TotalCost,Owner,PackageName,Description) VALUES(?,?,?,?,?,?,?)";
        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, Business_Type);
            pstmt.setString(2, StartDate);
            pstmt.setString(3, EndDate);
            pstmt.setInt(4, TotalCost);
            pstmt.setString(5, owner);
            pstmt.setString(6, PackageName);
            pstmt.setString(7, Description);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<String[]> packsearch(String PackID,String PackName,String BusinessType, String StartDate,String Owner, String EndDate,String Description){
        int field =1;
        ArrayList<String[]> ans = new ArrayList<>();
        String sql = "SELECT PackID,Business_Type,StartDate,EndDate,TotalCost,Owner,PackageName,Description From Package WHERE ";
        Boolean add = false;
        if(PackID!=null && !PackID.equals("") ){
            sql = sql + "PackID = ?";
            add = true;
        }
        if(BusinessType!=null && !BusinessType.equals("")){
            if(add)
                sql = sql + " and Business_Type = ?";
            else
                sql = sql +" Business_Type = ?";
            add = true;
        }
        if(StartDate!=null && !StartDate.equals("")){
            if(add)
                sql = sql + " and StartDate = ?";
            else
                sql = sql + " StartDate = ?";
            add = true;
        }
        if(EndDate!=null && !EndDate.equals("")){
            if(add)
                sql = sql + " and EndDate = ?";
            else
                sql = sql + " EndDate = ?";
            add = true;
        }
        if(Owner!=null && !Owner.equals("")){
            if(add)
                sql = sql + " and Owner = ?";
            else
                sql = sql + "Owner = ?";
            add = true;
        }
        if(PackName!=null && !PackName.equals("")){
            if(add)
                sql = sql + " and PackName = ?";
            else
                sql = sql + "PackName = ?";
        }
        if(Description!=null && !Description.equals("")){
            if(add)
                sql = sql + " and Description = ?";
            else
                sql = sql + "Description = ?";
        }

        if(sql.equals(""))
            return null;

        try (
                PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            if(PackID!= null && !PackID.equals("")){
                pstmt.setInt(field, Integer.parseInt(PackID));
                field++;

            }
            if(BusinessType!=null && !BusinessType.equals("")) {
                pstmt.setString(field, BusinessType);
                field++;
            }
            if(StartDate!=null && !StartDate.equals("")) {
                pstmt.setString(field, StartDate);
                field++;
            }

            if(EndDate!=null && !EndDate.equals("")) {
                pstmt.setInt(field, Integer.parseInt(EndDate));
                field++;
            }
            if(Owner!=null && !Owner.equals("")) {
                pstmt.setString(field, Owner);
                field++;
            }
            if(PackName!=null && !PackName.equals(""))
                if(Description!=null && Description.equals("")) {
                    pstmt.setString(field, Description);

                }

            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            // loop through the result set

            while (rs.next()) {
                ArrayList<String> str = new ArrayList<>();
                str.add(rs.getString("PackID"));
                str.add(rs.getString("Business_Type"));
                str.add(rs.getString("StartDate"));
                str.add(rs.getString("EndDate"));
                str.add(rs.getString("TotalCost"));
                str.add(rs.getString("Owner"));
                str.add(rs.getString("PackageName"));
                str.add(rs.getString("Description"));

                String[] strArr = new String[8];
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

    public static ArrayList<String[]> getallpack(){

        return  packsearch("","","","",username,"","");
    }


    public static ArrayList<String[]> product_choose(){
        ArrayList<String[]> prod = getAllProducts();
        ArrayList<String[]> ans = new ArrayList<>();
        for(int i = 0 ; i< prod.size();i++){
            String[] temp = new String[2];
            temp[0] = prod.get(i)[0];
            temp[1] = prod.get(i)[1];
            ans.add(temp);
        }
        return ans;
    }


}