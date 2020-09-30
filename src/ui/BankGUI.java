package ui;

import model.Bank;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class BankGUI {
	
	@SuppressWarnings("unused")
	private Bank bank;

	public BankGUI(Bank bank) {
		this.bank = bank;
	}
	
	@FXML
	private BorderPane mainPane;
	
	public BorderPane getMainPane() {
		return mainPane;
	}
	
    @FXML
    private TextField txtCCToSearch;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCC;

    @FXML
    private Label labClientDate;

    @FXML
    private TextField txtAccount;

    @FXML
    private TextField txtCreditCard;

    @FXML
    private Label labPaymentDate;

    @FXML
    private TextField txtValue;

    @FXML
    private RadioButton byCash;

    @FXML
    private ToggleGroup payMode;

    @FXML
    private RadioButton byAccount;

    @FXML
    void showCashier(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("cashier.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    }

    @FXML
    void showDataBase(ActionEvent event) {

    }

    @FXML
    void showQueues(ActionEvent event) {
    }
    
    @FXML
    void attendGeneralQueue(ActionEvent event) {

    }

    @FXML
    void attendPrioriryQueue(ActionEvent event) {

    }

    @FXML
    void cancelAccount(ActionEvent event) {

    }

    @FXML
    void consign(ActionEvent event) {

    }

    @FXML
    void payCreditCard(ActionEvent event) {

    }

    @FXML
    void searchClient(ActionEvent event) {

    }

    @FXML
    void showCurrentUser(ActionEvent event) {

    }

    @FXML
    void undo(ActionEvent event) {

    }

    @FXML
    void withDraw(ActionEvent event) {

    }
	
}
