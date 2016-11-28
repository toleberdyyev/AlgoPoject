package sample;
/**
 * Created by fredcolin079 on 28.11.16.
 */
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        VBox root;
        root = FXMLLoader.load(new URL(Main.class.getResource("sample.fxml").toExternalForm()));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
     static void main(String[] args) {
        launch(args);
    }
}


