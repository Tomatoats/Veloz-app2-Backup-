package baseline;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Alexys Veloz
 */
public class InventoryManagementApplication extends  javafx.application.Application{
    Map<String, Scene> sceneMap = new HashMap<>();

    public void start(Stage stage) throws Exception {
        addScenes();
        //there used to be a lot more scenes but I condensed
        Scene scene = sceneMap.get("Manager");
        //set up the stage for the user to see
        stage.setTitle("InveX");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }

    public void addScenes() throws IOException {
        //create a map and add all fxml files to it
        Map<String,Scene> addmap = new HashMap<>();
        Parent add = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Manager.fxml")));
        Scene toAdd = new Scene(add);
        addmap.put("Manager",toAdd);
        add = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/About.fxml")));
        toAdd = new Scene(add);
        addmap.put("About",toAdd);
        this.sceneMap = addmap;
    }


    // tab is \t
    //html needs header and body
}
