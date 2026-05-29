package com.jbes.aa1;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage splashStage) throws IOException {
        FXMLLoader splashLoader = new FXMLLoader(App.class.getResource("SplashScreen.fxml"));
        Scene splashScene = new Scene(splashLoader.load(), 600, 400);
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setScene(splashScene);
        splashStage.show();

        PauseTransition temporizador = new PauseTransition(Duration.seconds(3));

        temporizador.setOnFinished(event -> {
            try {
                FXMLLoader mainLoader = new FXMLLoader(App.class.getResource("VentanaPrincipal.fxml"));
                Scene mainScene = new Scene(mainLoader.load(), 1000, 800);

                Stage mainStage = new Stage();
                mainStage.setTitle("AA1");
                mainStage.setScene(mainScene);
                mainStage.show();

                splashStage.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        temporizador.play();
    }

}
