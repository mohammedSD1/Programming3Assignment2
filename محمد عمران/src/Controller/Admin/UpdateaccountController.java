/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.AdminFXML;

import Model.Accounts;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mohammed
 */
public class UpdateaccountController implements Initializable {

    private Accounts oldAccount;
    @FXML
    private Button saveNewAccountBtn;
    @FXML
    private Button cancelBtn;
  
    @FXML
    private TextField accnumber;
    @FXML
    private TextField username;
    @FXML
    private TextField curreny;
    @FXML
    private TextField date;
    @FXML
    private TextField balance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.oldAccount = Controller.Admin.AccountsManagmentController.selectedAccountToUpdate;
        accnumber.setText(String.valueOf(oldAccount.getAccount_number()));
        username.setText(oldAccount.getUsername());
        curreny.setText(oldAccount.getCurrency());
        date.setText(oldAccount.getCreation_date());
        balance.setText(String.valueOf(oldAccount.getBalance()));
    }

    @FXML
    private void saveaccount(ActionEvent event) throws SQLException, ClassNotFoundException {
        //get the new data from text field's and store it in new user object

        int accnumber = Integer.parseInt(this.accnumber.getText());
        String username = this.username.getText();
        String curreny = this.curreny.getText();
        double balance = Double.parseDouble(this.balance.getText());
        String date = this.date.getText();

        //make an new user object having this data
        Accounts newAccount = new Accounts(accnumber, username, curreny, balance, date);
        //set the new user id the same as the old user
        newAccount.setId(oldAccount.getId());

        // update the user in database by update method
        newAccount.update();

        //close the update stage using our global stage var in UsersManagmentController and show an alert
        Controller.Admin.AccountsManagmentController.updateStage.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("account updated");
        alert.setContentText("account updated");
        alert.showAndWait();
    }

    @FXML
    private void cancelaccount(ActionEvent event) {
        Controller.Admin.AccountsManagmentController.updateStage.close();
    }

}
