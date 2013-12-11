package Clocks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimeZone;
import static java.lang.Math.*;

public class ClockStageController implements Initializable {

    @FXML
    private Canvas Layer0;

    @FXML
    private Canvas Layer1;

    @FXML
    private Canvas Layer2;

    @FXML
    private ComboBox TimeZoneList;

    @FXML
    private TextArea Log;

    @FXML
    protected void handleComboBox(ActionEvent Event){
        Clock.setTimeZone(TimeZone.getTimeZone(TimeZoneList.getValue().toString()));
    }

    protected GraphicsContext gc0;
    protected GraphicsContext gc1;
    protected GraphicsContext gc2;

    protected void Initialize() {
        RenderLayer0();
        RenderLayer1();
        RenderLayer2();
        TimeZoneList.setValue(Clock.getTimeZone().getID());
    }

    protected void RenderLayer2() {
        // Innen-Kreis
        gc2.setStroke(Clock.getColor());
        gc2.fillOval(Clock.getPosX() + Layer0.getWidth() / 2 - 10, Layer0.getHeight() / 2 - 10 - Clock.getPosY(), 20, 20);
    }

    protected void RenderLayer0() {
        double P1X = 0.0;
        double P1Y = 0.0;
        double P2X = 0.0;
        double P2Y = 0.0;
        int LineWidth = 0;
        // Aussen-Kreis
        gc0.setStroke(Clock.getColor());
        gc0.setLineWidth(4);
        gc0.strokeOval(Clock.getPosX() + Layer0.getWidth() / 2 - Clock.getRadius(), Layer0.getHeight() / 2 - Clock.getRadius() - Clock.getPosY(), Clock.getDiameter(), Clock.getDiameter());
        // Stunden & Minuten-Striche
        for (int i = 0; i < 360; i = i + 6) {
            if (i%30 == 0) {
                LineWidth = 4;
                P1X = cos(i*2*PI/360) * (Clock.getRadius()-Clock.getHourStroke()) + Clock.getPosX();
                P1Y = sin(i*2*PI/360) * (Clock.getRadius()-Clock.getHourStroke()) - Clock.getPosY();
            }
            else {
                LineWidth = 2;
                P1X = cos(i*2*PI/360) * (Clock.getRadius()-Clock.getMinuteStroke()) + Clock.getPosX();
                P1Y = sin(i*2*PI/360) * (Clock.getRadius()-Clock.getMinuteStroke()) - Clock.getPosY();
            }
            P2X = cos(i*2*PI/360) * Clock.getRadius() + Clock.getPosX();
            P2Y = sin(i*2*PI/360) * Clock.getRadius() - Clock.getPosY();
            gc0.beginPath();
            gc0.moveTo(P1X + Layer0.getWidth() / 2, P1Y + Layer0.getHeight() / 2);
            gc0.lineTo(P2X + Layer0.getWidth() / 2, P2Y + Layer0.getHeight() / 2);
            gc0.setStroke(Clock.getColor());
            gc0.setLineWidth(LineWidth);
            gc0.stroke();
            gc0.closePath();
        }
    }

    protected void RenderLayer1() {
        int i;
        double P1X = 0.0;
        double P1Y = 0.0;
        // Löschen
        gc1.clearRect(0, 0, Layer1.getWidth(), Layer1.getHeight());
        // Stunden-Zeiger
        i = (Clock.getHour() * 360 / 12) - 90;
        i = i + Clock.getMinute() * 30 / 60;
        P1X = cos(i*2*PI/360) * (Clock.getHourPointerStroke());
        P1Y = sin(i*2*PI/360) * (Clock.getHourPointerStroke());
        gc1.beginPath();
        gc1.moveTo(Layer1.getWidth() / 2 + Clock.getPosX(), Layer1.getHeight() / 2  - Clock.getPosY());
        gc1.lineTo(P1X + Layer1.getWidth() / 2 + Clock.getPosX(), P1Y + Layer1.getHeight() / 2 - Clock.getPosY());
        gc1.setStroke(Clock.getColor());
        gc1.setLineWidth(6);
        gc1.stroke();
        gc1.closePath();
        // Minuten-Zeiger
        i = (Clock.getMinute() * 360 / 60) - 90;
        i = i + Clock.getSecond() / 10;
        P1X = cos(i*2*PI/360) * (Clock.getMinutePointerStroke());
        P1Y = sin(i*2*PI/360) * (Clock.getMinutePointerStroke());
        gc1.beginPath();
        gc1.moveTo(Layer1.getWidth() / 2 + Clock.getPosX(), Layer1.getHeight() / 2 - Clock.getPosY());
        gc1.lineTo(P1X + Layer1.getWidth() / 2 + Clock.getPosX(), P1Y + Layer1.getHeight() / 2 - Clock.getPosY());
        gc1.setStroke(Clock.getColor());
        gc1.setLineWidth(6);
        gc1.stroke();
        gc1.closePath();
        // Sekunden-Zeiger
        i = (Clock.getSecond() * 360 / 60) - 90;
        P1X = cos(i*2*PI/360) * (Clock.getSecondPointerStroke());
        P1Y = sin(i*2*PI/360) * (Clock.getSecondPointerStroke());
        gc1.beginPath();
        gc1.moveTo(Layer1.getWidth() / 2 + Clock.getPosX(), Layer1.getHeight() / 2 - Clock.getPosY());
        gc1.lineTo(P1X + Layer1.getWidth() / 2 + Clock.getPosX(), P1Y + Layer1.getHeight() / 2 - Clock.getPosY());
        gc1.setStroke(Clock.getSecondPointerColor());
        gc1.setLineWidth(2);
        gc1.stroke();
        gc1.closePath();
        gc1.setFill(Clock.getSecondPointerColor());
        gc1.fillOval(P1X + Layer1.getWidth() / 2 + Clock.getPosX() - 5, P1Y + Layer1.getHeight() / 2 - Clock.getPosY() - 5, 10, 10);
    }

    public Clock Clock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc0 = Layer0.getGraphicsContext2D();
        gc1 = Layer1.getGraphicsContext2D();
        gc2 = Layer2.getGraphicsContext2D();
        String[] ssIDs = TimeZone.getAvailableIDs();
        for( int i = 0; i < ssIDs.length; i++ )
        {
            TimeZoneList.getItems().add(ssIDs[i]);
        }
    }

    public void RenderScene() {
        RenderLayer1();
    }

    public void setLog(String aText) {
        Log.setText(aText);
    }

}
