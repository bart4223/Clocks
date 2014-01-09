package Clocks;

import Uniwork.Base.TickGenerator;
import Uniwork.Base.TickListener;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ClocksManager implements TickListener {

    protected ArrayList<Clock> FItems;
    protected Main FApplication;
    protected Stage FStage;
    protected ClocksControllerStageController FStageController;
    protected TickGenerator FTickGenerator;

    protected void CreateStage(){
        FStage = new Stage();
        FXMLLoader lXMLLoader = new FXMLLoader(getClass().getResource("ClocksControllerStage.fxml"));
        try {
            lXMLLoader.load();
            FStageController = (ClocksControllerStageController)lXMLLoader.getController();
            FStageController.Manager = this;
            Parent lRoot = lXMLLoader.getRoot();
            FStage.setTitle("Clocks-Controller");
            FStage.setScene(new Scene(lRoot, 450, 128, Color.GRAY));
            FStage.setResizable(false);
        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }

    protected void InitTickGenerator() {
        FTickGenerator.Initialize();
        FTickGenerator.NewItem("MAIN",100);
        FTickGenerator.addListener("MAIN",this);
        FTickGenerator.SetItemEnabled("MAIN",true);
    }

    protected void Terminate() {
        FTickGenerator.SetItemEnabled("MAIN",false);
        Iterator lItr = FItems.iterator();
        while(lItr.hasNext())  {
            ((Clock)lItr.next()).Terminate();
        }
    }

    protected void DoTick() {
        Iterator lItr = FItems.iterator();
        while(lItr.hasNext())  {
            ((Clock)lItr.next()).Tick();
        }
    }

    protected void UpdateController() {
        FStageController.UpdateControls();
    }

    protected void WriteLog(String aText) {
        FStageController.DisplayText(aText);
    }

    public ClocksManager(Main aApplication) {
        FItems = new ArrayList<Clock>();
        FTickGenerator = new TickGenerator();
        FApplication = aApplication;
    }

    public void Initialize() {
        CreateStage();
        InitTickGenerator();
        WriteLog(LoadResourceFileContent("texts/welcome.txt"));
    }

    public void Finalize() {
        FTickGenerator.Finalize();
        Terminate();
    }

    public Stage getStage() {
        return FStage;
    }

    public void ShowController() {
        FStage.show();
    }

    public Clock AddItem() {
        Clock lClock = new Clock(false);
        FItems.add(lClock);
        lClock.Initialize();
        lClock.ShowStage();
        UpdateController();
        return lClock;
    }

    public void ShowClocks() {
        Iterator lItr = FItems.iterator();
        while(lItr.hasNext())  {
            ((Clock)lItr.next()).ShowStage();
        }
    }

    public void CloseClocks() {
        Iterator lItr = FItems.iterator();
        while(lItr.hasNext())  {
            ((Clock)lItr.next()).CloseStage();
        }
    }

    @Override
    public void handleTick(Uniwork.Base.TickEvent e) {
        DoTick();
    }

    public void Shutdown() {
        Platform.exit();
    }

    public Boolean HasItems() {
        return FItems.size() > 0;
    }

    public String LoadResourceFileContent(String aFilename) {
        String lResult = "";
        try {
            InputStream lFileStream = new FileInputStream("resources/"+aFilename);
            if (lFileStream != null) {
                int lContent;
                while ((lContent = lFileStream.read()) != -1) {
                    lResult = lResult + (char)lContent;
                }
            }
        } catch (Exception e) {
        }
        return lResult;
    }

}
