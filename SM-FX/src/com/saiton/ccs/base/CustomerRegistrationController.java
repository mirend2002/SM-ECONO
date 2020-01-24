package com.saiton.ccs.base;

import com.saiton.ccs.basedao.CustomerRegistrationDAO;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.CustomValidatorImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

public class CustomerRegistrationController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

    //<editor-fold defaultstate="collapsed" desc="InitComponents">
    @FXML
    private Label lblSearch;

    @FXML
    private Button btnClose;

    @FXML
    private TableColumn tcCustomerId;

    @FXML
    private TableColumn tcName;

    @FXML
    public TextField txtCustomerId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearchCustomer;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabPaneCustomerDetails;

    @FXML
    private TableView<Item> tblContactDetails;

    @FXML
    private Button btnDelete;

    private ObservableList data;

    private Stage stage;
    private String userId;
    private String userName;

//</editor-fold>
    //Class initializations
    private final ValidationSupport validationSupport = new ValidationSupport();
    private final CustomerRegistrationDAO customerRegistrationDAO
            = new CustomerRegistrationDAO();
    private final FormatAndValidate fav = FormatAndValidate.getInstance();
    private CustomValidatorImpl customValidator;
    private MessageBox mb;

    private Item item = new Item();
    private int profession;
    boolean isCustomerSaved = false;
    boolean isTelephoneNoSaved = false;
    boolean isMobileNoSaved = false;
    boolean isEmailSaved = false;
    boolean isFaxNoSaved = false;
    boolean isDriverSaved = false;

    private ComponentControl componentControl = new ComponentControl();

    LocalDate date = null;

    // initialize-------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mb = SimpleMessageBoxFactory.createMessageBox();
        tcCustomerId.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colCustomerId"));

        tcName.setCellValueFactory(new PropertyValueFactory<Item, String>(
                "colName"));

        data = FXCollections.observableArrayList();
        tblContactDetails.setItems(data);

        // Component initializing--------------------
        //loadProfessionToCombobox();
        validatorInitialization();

        tableDataLoader(txtSearch.getText().trim());

    }

//==============================================================================================================================
//==============================================================================================================================
//  Button events ----------------------------------------------
    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        clearInput();
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        validatorInitialization();

        boolean validationSupportResult = false;

        ValidationResult ResultValidationSupport = validationSupport.
                getValidationResult();

        if (ResultValidationSupport != null) {

            validationSupportResult = validationSupport.isInvalid();

            if (validationSupportResult == true) {

                mb.ShowMessage((Stage) txtCustomerId.getScene().getWindow(),
                        ErrorMessages.MandatoryError, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);

            } else{

                boolean isDataAvailable
                        = customerRegistrationDAO.
                        checkingCustomerAvailability(
                                txtCustomerId.
                                getText()); // Checking customer availability

                if (isDataAvailable == false) {

                    isCustomerSaved
                            = customerRegistrationDAO.
                            insertCustomerDetails(
                                    txtCustomerId.getText(),
                                    txtName.getText()
                            );

                    if (isCustomerSaved == true) {
                        mb.ShowMessage(stage,
                                ErrorMessages.SuccesfullyRegistered,
                                "Information",
                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                MessageBox.MessageType.MSG_OK);
                        clearInput();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyRegistered,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }
                } else {

                    isCustomerSaved
                            = customerRegistrationDAO.
                            updateCustomerInfo(
                                    txtCustomerId.getText(),
                                    txtName.getText()
                            );

                    if (isCustomerSaved == true) {
                        mb.ShowMessage(stage,
                                ErrorMessages.SuccesfullyUpdated,
                                "Information",
                                MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                MessageBox.MessageType.MSG_OK);
                        clearInput();
                    } else {
                        mb.ShowMessage(stage,
                                ErrorMessages.NotSuccesfullyUpdated,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
                    }

                }

            }
        }else{
            mb.ShowMessage(stage,
                                ErrorMessages.MandatoryError,
                                "Error",
                                MessageBox.MessageIcon.MSG_ICON_FAIL,
                                MessageBox.MessageType.MSG_OK);
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = false;

        if (customerRegistrationDAO.checkingCustomerAvailability(
                txtCustomerId.getText())) {
            MessageBox.MessageOutput option = mb.ShowMessage(stage,
                    ErrorMessages.Delete, "Information",
                    MessageBox.MessageIcon.MSG_ICON_NONE,
                    MessageBox.MessageType.MSG_YESNO);
            if (option.equals(MessageBox.MessageOutput.MSG_YES)) {

                isDeleted = customerRegistrationDAO.
                        deleteCustomerInfoForUpdate(
                                txtCustomerId.getText());

                if (isDeleted == true) {

                    mb.ShowMessage(stage,
                            ErrorMessages.SuccesfullyDeleted,
                            "Information",
                            MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                            MessageBox.MessageType.MSG_OK);
                    clearInput();

                } else {
                    mb.ShowMessage(stage, ErrorMessages.Error,
                            "Error 01",
                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
                }
            }
        } else {
            mb.ShowMessage(stage, ErrorMessages.InvalidId, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        }
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event
    ) {
        stage.close();
    }

    @FXML
    private void btnSearchCustomerOnAction(ActionEvent event
    ) {
        tableDataLoader(txtSearch.getText().trim());

    }

//  TextField Events-------------------------------------------------
    @FXML
    private void txtNameKeyReleased(KeyEvent event
    ) {

        validatorInitialization();

    }

    @FXML
    private void txtNameOnAction(ActionEvent event
    ) {

    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event
    ) {

        tableDataLoader(txtSearch.getText().trim());

    }

//    Mouse events-------------------------------------------------
    @FXML
    private void tableViewOnMouseClick(javafx.scene.input.MouseEvent mouseEvent
    ) {

        if (mouseEvent.getClickCount() == 2) {
            Item itemData = (Item) tblContactDetails.getSelectionModel().
                    getSelectedItem();
            String customerId = itemData.colCustomerId.get();

            if (customerId != null) {
                customerInformationLoader(customerId);
            }
            btnDelete.setVisible(true);

        }

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {
        isCustomerSaved = false;
        txtName.clear();
        txtCustomerId.clear();
        txtSearch.clear();
        btnDelete.setVisible(false);
        validatorInitialization();
        tableDataLoader(txtSearch.getText().trim());

    }

    @Override
    public void clearValidations() {

    }

//Methods-----------------------------------------------------------------------------------------------------------
    private void tableDataLoader(String keyword) {
        tableCleanner();

        ArrayList<ArrayList<String>> custInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = customerRegistrationDAO.
                searchItemDetails(keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                custInfo.add(list.get(i));
            }

            if (custInfo != null && custInfo.size() > 0) {
                for (int i = 0; i < custInfo.size(); i++) {

                    item = new Item();

                    item.colCustomerId.setValue(custInfo.get(i).get(0));
                    item.colName.setValue(custInfo.get(i).get(1));

                    data.add(item);
                }
            }

        }

    }

    private void tableCleanner() {

        int count = data.size();

        if (count > 0) {

            for (int i = 0; i < count; i++) {

                data.remove(0);
            }

        }

    }

    private void customerInformationLoader(String customerId) {

        ArrayList<String> customerDataList = null;

        if (customerId != null) {

            customerDataList = customerRegistrationDAO.customerInformation(
                    customerId);

            if (customerDataList != null) {

                txtCustomerId.setText(customerId);
                txtName.setText(customerDataList.get(1));
                validatorInitialization();

            }
        }

    }

    private void validatorInitialization() {

        System.gc();

        validationSupport.registerValidator(txtCustomerId,
                new CustomTextFieldValidationImpl(txtCustomerId,
                        !fav.validName(txtCustomerId.getText()),
                        ErrorMessages.InvalidId));

        validationSupport.registerValidator(txtName,
                new CustomTextFieldValidationImpl(txtName,
                        !fav.validName(txtName.getText()),
                        ErrorMessages.InvalidName));
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        btnDelete.setVisible(false);
        stage.setHeight(575.0);
        stage.setWidth(1015.0);
        setUserAccessLevel();
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {

    }

    public class Item {

        public SimpleStringProperty colCustomerId = new SimpleStringProperty(
                "tcCustomerId");
        public SimpleStringProperty colName = new SimpleStringProperty("tcName");

        public String getColCustomerId() {
            return colCustomerId.get();
        }

        public String getColName() {
            return colName.get();
        }

    }

//----------------------------------------------------------------------------------------------------------------------    
//<editor-fold defaultstate="collapsed" desc="Methods">
    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                deactivateSearch();
                btnDelete.setVisible(false);
                break;

            case DELETE:
                disableUi(false);
                break;

            case READ_ONLY:
                disableUi(false);
                btnDelete.setVisible(false);
                break;

            case ALL_BUT_DELETE:
                disableUi(false);
                btnDelete.setVisible(false);
                break;

            case FULL_ACCESS:
                disableUi(false);
                btnDelete.setVisible(false);
                break;

            case NO_ACCESS:
                disableUi(true);
                break;
        }
    }

    private void setUserAccessLevel() {

        userId = UserSession.getInstance().getUserInfo().getEid();
        userName = UserSession.getInstance().getUserInfo().getName();

        String title = stage.getTitle();

        if (!title.isEmpty()) {

            UserPermission userPermission = UserSession.getInstance().
                    findPermission(title);

            if (userPermission.canInsert() == true) {
                insert = true;

            }

            if (userPermission.canDelete() == true) {
                delete = true;

            }

            if (userPermission.canUpdate() == true) {
                update = true;

            }

            if (userPermission.canView() == true) {
                view = true;

            }

            if (insert == true && delete == true && update == true && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == false && delete == true && update == true
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == true && delete == false && update == true
                    && view
                    == true) {

                setUiMode(UiMode.ALL_BUT_DELETE);

            } else if (insert == true && delete == true && update == false
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == true && delete == true && update == true
                    && view
                    == false) {

                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == true
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == false && delete == true && update == false
                    && view
                    == true) {

                setUiMode(UiMode.DELETE);

            } else if (insert == false && delete == true && update == true
                    && view
                    == false) {

                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == true && delete == false && update == false
                    && view
                    == true) {

                setUiMode(UiMode.ALL_BUT_DELETE);

            } else if (insert == true && delete == false && update == true
                    && view
                    == false) {

                setUiMode(UiMode.SAVE);

            } else if (insert == true && delete == true && update == false
                    && view
                    == false) {

                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == false
                    && view
                    == true) {

                setUiMode(UiMode.READ_ONLY);

            } else if (insert == false && delete == false && update == true
                    && view
                    == false) {

                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == false && delete == true && update == false
                    && view
                    == false) {

                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == true && delete == false && update == false
                    && view
                    == false) {

                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == false
                    && view
                    == false) {

                setUiMode(UiMode.NO_ACCESS);

            }
        } else {

            setUiMode(UiMode.NO_ACCESS);

        }

    }

    private void disableUi(boolean state) {

        txtCustomerId.setDisable(state);
        txtCustomerId.setVisible(!state);

        txtName.setDisable(state);
        txtName.setVisible(!state);

        txtSearch.setDisable(state);
        txtSearch.setVisible(!state);

        btnSearchCustomer.setDisable(state);
        btnSearchCustomer.setVisible(!state);

        tblContactDetails.setDisable(state);
        tblContactDetails.setVisible(!state);

        btnDelete.setDisable(state);
        btnDelete.setVisible(!state);

        btnClose.setDisable(state);
        btnClose.setVisible(!state);

    }

    private void deactivateSearch() {
        componentControl.deactivateSearch(lblSearch, txtSearch,
                btnSearchCustomer,
                180.00, 0.00);

    }

//</editor-fold>
}
