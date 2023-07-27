package lk.ijse.cuisineCompass;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cuisineCompass.controller.AdminDashboardFormController;
import lk.ijse.cuisineCompass.model.UserModel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

public class launcher extends Application {

    @SneakyThrows
    @Override
    public void start(Stage stage) throws IOException {

        /*ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);

        // Create a task for main program logic
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate main program logic
                Thread.sleep(3000);
                return null;
            }
        };

        // When the task is complete, hide the loading screen and show main program window
        task.setOnSucceeded(e -> {
            progressBar.setVisible(false);
            stage.show();
        });

        // Create a stack pane to hold the progress bar
        StackPane root = new StackPane();
        root.getChildren().add(progressBar);

        // Create a scene with the stack pane
        Scene scene = new Scene(root, 200, 100);

        // Set the scene to the primary stage and show the loading screen
        stage.setScene(scene);
        stage.setTitle("Loading Screen");
        stage.setOnCloseRequest(e -> Platform.exit()); // Close application when loading screen is closed
        stage.show();

        // Start the task for main program logic
        new Thread(task).start();

        */

        /*Parent root = FXMLLoader.load(getClass().getResource("view/loading_form.fxml"));
        stage.setTitle("CUISINE COMPASS - Loading Form");
        Thread.sleep(3000);
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();*/

        Parent root = FXMLLoader.load(getClass().getResource("view/login_form.fxml"));
        stage.setTitle("CUISINE COMPASS - Login Form");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();

        //String txt = UserModel.getTxt(userName);
        //txtUser.setText(userName);

        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }

}
