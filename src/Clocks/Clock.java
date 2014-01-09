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
    protected Boolean FOwnTick;
    protected TimeZone FTimeZone;
    protected Color FColor;
    protected Color FSecondPointerColor;
    protected int FPosX;
    protected int FPosY;
    protected int FRadius;
    protected Stage FStage;
    protected ClockStageController FStageController;
    protected Integer FDirection;

    protected void DoTick() {
        FStageController.RenderScene(false);
    }

    protected void CreateStage(){
        FStage = new Stage();
        FXMLLoader lXMLLoader = new FXMLLoader(getClass().getResource("ClockStage.fxml"));
        try {
            lXMLLoader.load();
            FStageController = (ClockStageController)lXMLLoader.getController();
            FStageController.Clock = this;
            Parent lRoot = lXMLLoader.getRoot();
            FStage.setTitle("Clock");
            FStage.setScene(new Scene(lRoot, 500, 600, Color.LIGHTSKYBLUE));
            FStage.setResizable(false);
            FStageController.Initialize();
        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }

    protected void Finalize() {
        if (FOwnTick) {
            FTimer.cancel();
            FTimer = null;
        }
        CloseStage();
    }

    public Clock() {
        this(true, TimeZone.getDefault(), Color.BLACK);
    }

    public Clock(Boolean aOwnTick) {
        this(aOwnTick, TimeZone.getDefault(), Color.BLACK);
    }

    public Clock(Boolean aOwnTick, TimeZone aTimeZone, Color aColor) {
        FTimeZone = aTimeZone;
        FColor = aColor;
        FSecondPointerColor = Color.RED;
        FPosX = 0;
        FPosY = 0;
        FRadius = 200;
        FOwnTick = aOwnTick;
        if (FOwnTick) {
            FTimer = new Timer();
        }
        FDirection = -1;
    }

    public void Initialize() {
        CreateStage();
        if (FOwnTick) {
            TimerTask lTimerTask = new TimerTask() {
            public void run() {
                    synchronized (this) {
                    DoTick();
                    }
                    }
            };
            FTimer.schedule(lTimerTask,100,1000);
        }
        FStageController.RenderScene(true);
    }

    public void Terminate() {
        Finalize();
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

    public void setRadius(Integer aRadius) {
        FRadius = aRadius;
    }

    public int getDiameter() {
        return 2*FRadius;
    }

    public int getMinuteStroke() {
        return FRadius/20;
    }

    public int getHourStroke() {
        return FRadius/7;
    }

    public int getSecondPointerStroke() {
        return FRadius * 130/200;
    }

    public int getMinutePointerStroke() {
        return FRadius * 150/200;
    }

    public int getHourPointerStroke() {
        return FRadius/2;
    }

    public Stage getStage() {
        return FStage;
    }

    public void Tick() {
        DoTick();
    }

}
