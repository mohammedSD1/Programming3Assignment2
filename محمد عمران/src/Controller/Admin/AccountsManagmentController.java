/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.Accounts;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yahya
 */
public class AccountsManagmentController implements Initializable {

    public static Accounts selectedAccountToUpdate;
    public static Stage updateStage;
    
    
    @FXML
    private Button usersManagmentPageBtn;
    @FXML
    private Button accountsPageBtn;
    @FXML
    private Button operationsPageBtn;
    @FXML
    private Button createNewAccountrBtn;
    @FXML
    private Button showAllAccountsBtn;
    @FXML
    private Button updateSelectedAccountBtn;
    @FXML
    private Button deleteSelectedAccountBtn;
    @FXML
    private Button searchAccountBtn;
    @FXML
    private TextField accontSearchTF;
    @FXML
    private TableColumn<Accounts, Integer> id;
    @FXML
    private TableColumn<Accounts, Integer> anum;
    @FXML
    private TableColumn<Accounts, String> user;
    @FXML
    private TableColumn<Accounts, String> curreny;
    @FXML
    private TableColumn<Accounts, Double> balance;
    @FXML
    private TableColumn<Accounts, String> date;
    @FXML
    private TableView<Accounts> accounttableview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory("id"));
        anum.setCellValueFactory(new PropertyValueFactory("account_number"));
        user.setCellValueFactory(new PropertyValueFactory("username"));
        curreny.setCellValueFactory(new PropertyValueFactory("currency"));
        balance.setCellValueFactory(new PropertyValueFactory("balance"));
        date.setCellValueFactory(new PropertyValueFactory("creation_date"));
    }

    @FXML
    private void showUsersManagmentPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToUsersManagment();
    }

    @FXML
    private void showAccountsPage(ActionEvent event) {

    }

    @FXML
    private void showOperationsPage(ActionEvent event) {

    }

    @FXML
    private void showAccountCreationPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneTocreateaccount();
    }

    @FXML
    private void showAllAccounts(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<Accounts> accountList
                = FXCollections.observableArrayList(Accounts.getAllAccounts());
        accounttableview.setItems(accountList);
    }

    @FXML
    private void updateSelectedAccount(ActionEvent event) throws IOException {
         if (accounttableview.getSelectionModel().getSelectedItem() != null) {
            //store the selected user from the TableView in our global var user selectedUserToUpdate   
            selectedAccountToUpdate = accounttableview.getSelectionModel().getSelectedItem();
            //load update page fxml
            FXMLLoader loaderUpdate = new FXMLLoader(getClass().getResource("/View/AdminFXML/updateaccount.fxml"));
            Parent rootUpdate = loaderUpdate.load();
            Scene updateUserScene = new Scene(rootUpdate);
            updateStage = new Stage();
            updateStage.setScene(updateUserScene);
            updateStage.setTitle("Update account " + selectedAccountToUpdate.getUsername());
            updateStage.show();
        }
    }

   

    @FXML
    private void searchForAnAccount(ActionEvent event) {
    }

    @FXML
    private void deleteAccount(ActionEvent event) {
        if (this.accounttableview.getSelectionModel().getSelectedItem() != null) {
            //store the selected user from the TableView in new user object
            Accounts selectedaccount = accounttableview.getSelectionModel().getSelectedItem();

            //show an confirmation alert and make the deletion on confirm event
            Alert deleteConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmAlert.setTitle("account delete");
            deleteConfirmAlert.setContentText("Are you sure to delete this account ?");
            deleteConfirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        //delete the selected user from database table using delete method in our User model
                        selectedaccount.delete();
                    } catch (SQLException ex) {
                        Logger.getLogger(UsersManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsersManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Alert deletedSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    deletedSuccessAlert.setTitle("account deleted");
                    deletedSuccessAlert.setContentText("account deleted");
                    deletedSuccessAlert.show();
                }
            });
        } else {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Select an account");
            warnAlert.setContentText("Please select an account from the table view");
            warnAlert.show();
        }
    }

}
