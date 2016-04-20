package hello;

/**
 * Created by Arch on 4/16/2016.
 */
public class Route {

    private final int id;
    private final String username;
    private final String coordinates;


    public Route(int id,String username, String coordinates) {
        this.id = id;
        this.username = username;
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCoordinates() {
        return coordinates;
    }

}
