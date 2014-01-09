package Clocks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ClocksControllerStageController {

    public ClocksManager Manager;

    @FXML
    private TextArea TextArea;

    @FXML
    private Button btnShow;

    @FXML
    private Button btnClose;

    @FXML
    private void handleAddAction(ActionEvent event) {
        Manager.AddItem();
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        Manager.Shutdown();
    }

    @FXML
    private void handleShowAction(ActionEvent event) {
        Manager.ShowClocks();
    }

    @FXML
    private void handleCloseAction(ActionEvent event) {
        Manager.CloseClocks();
    }

    public void DisplayText(String aText) {
        String lStr = TextArea.getText();
        if (lStr.length() == 0) {
            lStr = aText;
        }
        else {
            lStr = lStr + "\n" + aText;
        }
        TextArea.setText(lStr);
    }

    public void UpdateControls() {
        if (Manager.HasItems()) {
            btnShow.setDisable(false);
            btnClose.setDisable(false);
        }
        else {
            btnShow.setDisable(true);
            btnClose.setDisable(true);
        }
    }

}
