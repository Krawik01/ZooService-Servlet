package dbController;

import java.sql.*;


public class DbConnect {
    public Connection connectToDataBase(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoodb?useSSL=false", "root", "krawik");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public String execSql(String query){
        String result = "";

        Connection connection = connectToDataBase();
//        try {
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//
//            ResultSetMetaData metaData = rs.getMetaData();
//            StringBuilder builder = new StringBuilder();
//
//            while(rs.next()) {
//                System.out.println( rs.getString(columnLabel));
//                builder.append(rs.getString(columnLabel) + "\n");
//            }
//
//            st.close();
//            rs.close();
//            connection.close();
//           // result = builder.toString();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder builder = new StringBuilder();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    System.out.println(rs.getString(columnLabel));
                    builder.append(rs.getString(columnLabel) + "   ");
                }
                System.out.println(builder.toString());
                builder.append("\n");
            }

            result = builder.toString();
            st.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }
}

