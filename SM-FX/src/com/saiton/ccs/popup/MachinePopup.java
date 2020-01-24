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


public class MachinePopup {

 
    public SimpleStringProperty colMachine = new SimpleStringProperty("tcMachine");
    
    public String getColMachine() {
        return colMachine.get();
    }

    

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcMachine = new TableColumn("Machine");
        tcMachine.setMinWidth(500);
        tcMachine.setCellValueFactory(
                new PropertyValueFactory<>("colMachine"));

        
        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcMachine);
   
   
   
   
   return tableView;
   }

   
   
}
