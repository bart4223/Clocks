package Clocks;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Clock {

    protected Timer FTimer;
    protected TimeZone FTimeZone;
    protected Color FColor;
    protected Color FSecondPointerColor;
    protected int FPosX;
    protected int FPosY;
    protected int FRadius;
    protected int FMinuteStroke;
    protected int FHourStroke;
    protected int FSecondPointerStroke;
    protected int FMinutePointerStroke;
    protected int FHourPointerStroke;
    protected Stage FStage;
    protected ClockStageController FStageController;

    protected void DoTick() {
        FStageController.RenderScene();
        String str = "Welcome to Clocks...\n";
        str = str + "The time in \"" + getTimeZone().getID() + "\" is ";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        dateFormat.setTimeZone(getTimeZone());
        str = str + dateFormat.format(Calendar.getInstance().getTime()) + "\n";
        str = str + "\n(C) by Erik Höltmann MMXIII";
        FStageController.setLog(str);
    }

    protected void CreateStage(){
        FStage = new Stage();
        FXMLLoader lXMLLoader = new FXMLLoader(getClass().getResource("ClockStage.fxml"));
        try {
            lXMLLoader.load();
            FStageController = (ClockStageController)lXMLLoader.getController();
            FStageController.Clock = this;
            Parent lRoot = lXMLLoader.getRoot();
            FStage.setTitle("Clocks");
            FStage.setScene(new Scene(lRoot, 500, 600, Color.LIGHTSKYBLUE));
            FStage.setResizable(false);
            FStageController.Initialize();
        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }

    public Clock() {
        this(TimeZone.getDefault(), Color.BLACK);
    }

    public Clock(TimeZone aTimeZone, Color aColor) {
        FTimeZone = aTimeZone;
        FColor = aColor;
        FSecondPointerColor = Color.RED;
        FPosX = 0;
        FPosY = 0;
        FRadius = 200;
        FMinuteStroke = 10;
        FHourStroke = 30;
        FSecondPointerStroke = 130;
        FMinutePointerStroke = 150;
        FHourPointerStroke = 100;
        FTimer = new Timer();
    }

    public void Initialize() {
        CreateStage();
        TimerTask lTimerTask = new TimerTask() {
            public void run() {
                synchronized (this) {
                    DoTick();
                }
            }
        };
        FTimer.schedule(lTimerTask,100,1000);
    }

    public void Finalize() {
        FTimer.cancel();
        FTimer = null;
        CloseStage();
    }

    public void ShowStage(){
        FStage.show();
    }

    public void CloseStage(){
        FStage.close();
    }

    public void setTimeZone(TimeZone aTimeZone) {
        FTimeZone = aTimeZone;
    }

    public TimeZone getTimeZone() {
        return FTimeZone;
    }

    public void setColor(Color aColor) {
        FColor = aColor;
    }

    public Color getColor() {
        return FColor;
    }

    public Color getSecondPointerColor() {
        return FSecondPointerColor;
    }

    public Integer getHour() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH");
        dateFormat.setTimeZone(FTimeZone);
        return Integer.parseInt(dateFormat.format(cal.getTime())) - 12;
    }

    public Integer getMinute() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("mm");
        dateFormat.setTimeZone(FTimeZone);
        return Integer.parseInt(dateFormat.format(cal.getTime()));
    }

    public Integer getSecond() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ss");
        dateFormat.setTimeZone(FTimeZone);
        return Integer.parseInt(dateFormat.format(cal.getTime()));
    }

    public int getPosX() {
        return FPosX;
    }

    public int getPosY() {
        return FPosY;
    }

    public int getRadius() {
        return FRadius;
    }

    public int getDiameter() {
        return 2*FRadius;
    }

    public int getMinuteStroke() {
        return FMinuteStroke;
    }

    public int getHourStroke() {
        return FHourStroke;
    }

    public int getSecondPointerStroke() {
        return FSecondPointerStroke;
    }

    public int getMinutePointerStroke() {
        return FMinutePointerStroke;
    }

    public int getHourPointerStroke() {
        return FHourPointerStroke;
    }

    public Stage getStage() {
        return FStage;
    }

}
