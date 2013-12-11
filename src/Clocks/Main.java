package Clocks;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    protected Clock FClock;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FClock = new Clock();
        FClock.Initialize();
        primaryStage = FClock.getStage();
        FClock.ShowStage();
    }

    @Override
    public void stop() throws Exception {
        FClock.Finalize();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
