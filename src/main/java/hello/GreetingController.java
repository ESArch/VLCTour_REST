package hello;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicInteger counter = new AtomicInteger();
    private static boolean firstTime = true;

    @RequestMapping("/greeting")
    public Route greeting(@RequestParam(value="name", defaultValue="undefined") String name) {
        DBServices.test();

        checkFirstTime();

        int id = counter.incrementAndGet();

        saveCounter(id);

        return new Route(id,name, "asd");
    }

    private void checkFirstTime(){
        if(firstTime){
            try{
                List<String> lines = Files.readAllLines(Paths.get("lastCounter.txt"));
                for(String s : lines)
                    System.out.println("Linea: " + s);
                String lastCounter = lines.get(0);
                counter.set(Integer.parseInt(lastCounter));
            } catch (IOException e) {
                e.printStackTrace();
            }
            firstTime = false;
        }
    }

    private void saveCounter(int id){
        try{
            PrintWriter writer = new PrintWriter("lastCounter.txt");
            writer.write(id+"");
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/popular")
    public List<Route> getPopularRoutes() {
//        List<Route> routes = new ArrayList<Route>();
//        routes.add(new Route(1, "diego", "asd"));
//        routes.add(new Route(2, "pablo", "dsa"));

        return DBServices.getPopularRoutes();
    }

    @RequestMapping("/route")
    public Route insertRoute(@RequestParam(value="user", defaultValue="undefined") String name, @RequestParam(value="coord", defaultValue="undefined") String coord) {

        checkFirstTime();

        int id = counter.incrementAndGet();

        Route route = new Route(
                id,
                name,
                coord
        );
        DBServices.insertRoute(route);

        saveCounter(id);

        return route;
    }

    @RequestMapping("/score")
    public void insertScore(@RequestParam(value="id", defaultValue="undefined") String id, @RequestParam(value="value", defaultValue= "3") String value) {

        DBServices.insertScore(Integer.parseInt(id), Integer.parseInt(value));
    }

    @RequestMapping("/changeroute")
    public void updateScore(@RequestParam(value="id", defaultValue="undefined") String id, @RequestParam(value="coord", defaultValue= "3") String coord) {

        DBServices.updateRoute(id, coord);
    }




}
