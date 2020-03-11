package edu.rice.comp504.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import edu.rice.comp504.model.DispatcherAdapter;

import java.awt.*;

import static spark.Spark.*;

/**
 * Game controller.
 */
public class GameController {


    /**
     * Get Heroku assigned port.
     *
     * @return port number
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    /**
     * Game starts here.
     *
     * @param args no arg
     */
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");

        Gson gson = new Gson();
        DispatcherAdapter dis = new DispatcherAdapter();

        post("/load", (request, response) -> {
            int level = Integer.parseInt(request.queryParams("level"));
            int mode = Integer.parseInt(request.queryParams("mode"));
            return gson.toJson(dis.loadGame(level, mode, "public/map/map.json"));
        });

        post("/update", (request, response) -> {

            // JsonObject object = (JsonObject) new JsonParser().parse(request.body());
            return gson.toJson(dis.update());
        });

        post("/key", (request, response) -> {
            //JsonObject object = (JsonObject) new JsonParser().parse(request.body());
//            int playID = gson.fromJson(object.get("id"), Integer.class);
//            String key = gson.fromJson(object.get("key"), String.class);
            int id = Integer.parseInt(request.queryParams("id"));
            String key = request.queryParams("key");
//            System.out.println(key);
            return gson.toJson(dis.update(id, key));
        });

        post("/reset", (request, response) -> {

            return gson.toJson(dis.reset());
        });
    }
}
