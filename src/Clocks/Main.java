package Clocks;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    protected ClocksManager FManager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FManager = new ClocksManager(this);
        FManager.Initialize();
        primaryStage = FManager.getStage();
        FManager.ShowController();
    }

    @Override
    public void stop() throws Exception {
        FManager.Finalize();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
