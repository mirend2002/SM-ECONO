/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.popup;

import com.saiton.ccs.base.CustomerRegistrationController;
import com.saiton.ccs.basedao.CustomerRegistrationDAO;
import com.saiton.ccs.uihandle.StagePassable;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class CustomerIdPopup {

    public SimpleStringProperty colCustomerCode = new SimpleStringProperty("tcCustomerCode");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    

    public String getColCustomerCode() {
        return colCustomerCode.get();
    }
    
    public String getColCustomerName() {
        return colCustomerName.get();
    }
    

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcCustomerCode = new TableColumn("Code");
        tcCustomerCode.setMinWidth(100);
        tcCustomerCode.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerCode"));
        
        TableColumn tcCustomerName = new TableColumn("Name");
        tcCustomerName.setMinWidth(400);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));             

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcCustomerCode, tcCustomerName);

        return tableView;
    }

}
