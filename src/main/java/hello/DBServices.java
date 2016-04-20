package hello;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arch on 4/16/2016.
 */
public class DBServices {

    static Connection connection = null;

    public static void test(){
        connect();
    }

    private static void connect(){
        if (connection == null)
            getConnection();
    }

    private static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/vlctour",
                            "postgres", "postgres");
            connection.setAutoCommit(true);
            System.out.println("Opened database succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return connection;
    }

    public static void insertRoute(Route route){
        connect();

        Statement statement = null;
        String sql = "";

        try {
            statement = connection.createStatement();

            sql = "INSERT INTO routes (rou_id, rou_username, rou_coord)"
                    + "VALUES ('" + route.getId() + "','" + route.getUsername() + "','" + route.getCoordinates() + "');";
            statement.executeUpdate(sql);
            statement.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(sql);
        }

    }

    public  static void updateRoute(String id, String coord){
        connect();

        Statement statement = null;
        String sql = "";

        try {
            statement = connection.createStatement();

            sql = "UPDATE routes "+
                    "SET rou_coord = '" + coord + "'" +
                    " WHERE rou_id = " + id + ";";
            statement.executeUpdate(sql);
            statement.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(sql);
        }

    }

    public static List<Route> getPopularRoutes(){
        connect();

        Statement statement = null;
        String sql = "";
        ResultSet rs = null;
        List<Route> routes = new ArrayList<Route>();

        try {
            statement = connection.createStatement();

            sql = "SELECT rou_id, rou_username, rou_coord, AVG(sco_value)"+
                    "FROM routes, scores " +
                    "WHERE rou_id = sco_route " +
                    "GROUP BY rou_id, rou_username, rou_coord " +
                    "ORDER BY AVG(sco_value) DESC " +
                    "LIMIT 5;"
            ;
            rs = statement.executeQuery(sql);

            while (rs.next()){
                routes.add(new Route(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }

            statement.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(sql);
        }

        return routes;

    }

    public static void insertScore(long id, int value){
        connect();

        Statement statement = null;
        String sql = "";

        try {
            statement = connection.createStatement();

            sql = "INSERT INTO scores (sco_route, sco_value)"
                    + "VALUES ('" + id + "'," + value + ");";
            statement.executeUpdate(sql);
            statement.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(sql);
        }

    }





}
