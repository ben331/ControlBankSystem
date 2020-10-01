package ui;

import model.Bank;
import model.Client;
import model.Priority;
import model.User;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class BankGUI {
	
	@SuppressWarnings("unused")
	private Bank bank;

	public BankGUI(Bank bank) {
		this.bank = bank;
	}
	
	///////////////////////////////////////////////////// BANK /////////////////////////////////////////////////

	
	@FXML
	private BorderPane mainPane;
	
	public BorderPane getMainPane() {
		return mainPane;
	}
	
	@FXML
    void showCashier(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("cashier.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    }

    @FXML
    void showDataBase(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("clientsSort.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    	initializeTable();
    }

    @FXML
    void showQueues(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("queue.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    	initializeGeneralQueue();
    	initializePriorityQueue();
    }
	
	///////////////////////////////////////////////////// Cashier /////////////////////////////////////////////////

	
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
    void attendGeneralQueue(ActionEvent event) {
    	bank.attendUser(true);
    }

    @FXML
    void attendPrioriryQueue(ActionEvent event) {
    	bank.attendUser(true);
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
    
    ///////////////////////////////////////////////////// Queue General & priority /////////////////////////////////////////////////
    
    @FXML
    private TableView<User> tableGeneral;

    @FXML
    private TableColumn<User, String> ColumnGeneral;

    @FXML
    private TableView<User> tablePriority;

    @FXML
    private TableColumn<User, String> columnPriority;

    @FXML
    private TextField txtCedulaCiudadania;

    @FXML
    private TextField txtNameAdd;

    @FXML
    private Button btAddGeneralRow;

    @FXML
    private Button btAddPriorityRow;

    @FXML
    void addGeneralRow(ActionEvent event) {
    	bank.registerIncome(txtNameAdd.getText(), txtCedulaCiudadania.getText(), null);
    }

    @FXML
    void addPriorityRow(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("beacusePriority.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    }
    
    ///////////////////////////////////////////////// Because Priority /////////////////////////////////////////////////
    
    @FXML
    private Button btReady;

    @FXML
    private RadioButton rbDisabled;

    @FXML
    private ToggleGroup grupPriority;

    @FXML
    private RadioButton rbPregnancy;

    @FXML
    private RadioButton rbSeniorAdult;

    @FXML
    private RadioButton rbYounger;

    @FXML
    void ready(ActionEvent event) throws IOException {
    	Priority priority = new Priority(rbSeniorAdult.isSelected(), rbYounger.isSelected(), rbPregnancy.isSelected(), rbDisabled.isSelected());
    	bank.registerIncome(txtNameAdd.getText(), txtCedulaCiudadania.getText(), priority);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("queue.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    	initializeGeneralQueue();
    	initializePriorityQueue();
    }
    
    ///////////////////////////////////////////////// clients sort /////////////////////////////////////////////////
    
    @FXML
    private TableView<Client> table;

    @FXML
    private TableColumn<Client, String> tableColumnName;

    @FXML
    private TableColumn<Client, String> tableColumnCC;

    @FXML
    private TableColumn<Client, LocalDate> tableColumnTime;

    @FXML
    private TableColumn<Client, Double> tableColumnBalance;

    @FXML
    private RadioButton rbClientName;

    @FXML
    private ToggleGroup sortBy;

    @FXML
    private RadioButton rbCC;

    @FXML
    private RadioButton rbTime;

    @FXML
    private RadioButton rbBalance;

    @FXML
    private Button buttomReady;

    @FXML
    void readySort(ActionEvent event) {

    }
    
    ////////////////////////////////////////////////// Initializers /////////////////////////////
    private void initializeTable() {
    	ObservableList<Client> clients = FXCollections.observableArrayList(bank.getOrderedClientsToShow());
    	table.setItems(clients);
    	tableColumnName.setCellValueFactory(new PropertyValueFactory<Client,String>("name"));
    	tableColumnCC.setCellValueFactory(new PropertyValueFactory<Client,String>("CC"));
    	tableColumnTime.setCellValueFactory(new PropertyValueFactory<Client,LocalDate>("incorporationDate"));
    	tableColumnBalance.setCellValueFactory(new PropertyValueFactory<Client,Double>("balance"));
    }
    
    private void initializeGeneralQueue() {
    	ObservableList<User> users = FXCollections.observableArrayList(bank.getGeneral());
    	tableGeneral.setItems(users);
    	ColumnGeneral.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
    }
    
    private void initializePriorityQueue() {
    	ObservableList<User> users = FXCollections.observableArrayList(bank.getPriority());
    	tableGeneral.setItems(users);
    	ColumnGeneral.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
    }
    
}
