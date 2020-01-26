package com.saiton.ccs.scale;

import com.saiton.ccs.base.UserPermission;
import com.saiton.ccs.base.UserSession;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.popup.CustomerIdPopup;
import com.saiton.ccs.popup.ItemInfoPopup;
import com.saiton.ccs.popup.MachinePopup;
import com.saiton.ccs.popup.ServiceInfoPopup;
import com.saiton.ccs.popup.SizePopup;
import com.saiton.ccs.printerservice.ReportPath;
import com.saiton.ccs.salesdao.ServiceDAO;
import com.saiton.ccs.scaledao.ScaleDAO;
import com.saiton.ccs.uihandle.ReportGenerator;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.SerialController;
import com.saiton.ccs.util.SimpleRead;
import com.saiton.ccs.validations.CustomTableViewValidationImpl;
import com.saiton.ccs.validations.CustomTextAreaValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import com.saiton.ccs.validations.Validatable;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.PopOver;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

public class ReelRequisitionController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Initcomponent">
    @FXML
    private Button btnRefreshItemCode;

    @FXML
    private TextField txtItemCode;

    @FXML
    private Button btnClose;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtReelNo;

    @FXML
    private TableColumn<?, ?> tcInventoryDate;

    @FXML
    private Button btnRefreshReturnedWeight;

    @FXML
    private Button btnRePrint;

    @FXML
    private TextField txtReelFb;

    @FXML
    private TextField txtReturnedWeight;

    @FXML
    private TableView<?> tblItemList;

    @FXML
    private TableColumn<?, ?> tcItemCode;

    @FXML
    private TableColumn<?, ?> tcItemDate;

    @FXML
    private TableColumn<?, ?> tcReleased;

    @FXML
    private TextField txtLogDate;

    @FXML
    private Label lblItemId;

    @FXML
    private TableColumn<?, ?> tcReturned;

    @FXML
    private TextField txtGSM;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtIssuedWeight;

    @FXML
    private Button btnLog;

    @FXML
    private TextField txtItemName;

    @FXML
    private Button btnSearchItemCode;

    @FXML
    private TableColumn<?, ?> tcDescription;

//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Key Events">
    @FXML
    private void txtWeightScaleIdOnKeyReleased(KeyEvent event) {

    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Action Events">
    @FXML
    void txtItemCodeOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void btnRefreshItemCodeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchItemCodeOnAction(ActionEvent event) {

    }

    @FXML
    void tblRequestNoteListOnMouseClicked(ActionEvent event) {

    }

    @FXML
    void btnRePrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOnAction(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {

    }

    @FXML
    void txtDescriptionOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtItemNameOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtGSMOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtLogDateOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtIssuedWeightOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtSizeOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtReelFbOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtReelNoOnKeyReleased(ActionEvent event) {

    }

    @FXML
    void txtReturnedWeightOnReleased(ActionEvent event) {

    }

    @FXML
    void btnRefreshReturnedWeightOnAction(ActionEvent event) {

    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Click Events">
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


//</editor-fold>

}
