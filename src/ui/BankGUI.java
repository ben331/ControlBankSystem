package ui;

import model.Bank;
import model.Client;
import model.Priority;
import model.User;

import java.io.IOException;
import java.time.LocalDate;

import customexception.FullStructureException;
import customexception.InsufficientBalanceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BankGUI {
	
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
    	refreshPersonalData();
    }

    @FXML
    void showDataBase(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("clientsSort.fxml"));
    	loader.setController(this);
    	Parent scene = loader.load();
    	mainPane.setCenter(scene);
    	bank.refreshList();
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
	
    @FXML
    void saveData(ActionEvent event) throws IOException {
    	bank.saveData();
    }
	///////////////////////////////////////////////////// Cashier /////////////////////////////////////////////////

    @FXML
    private HBox HBoxOperations;
    
    @FXML
    private TextField txtCCToSearch;

    @FXML
    private Button btRegister;
    
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
    	refreshPersonalData();
    }

    @FXML
    void attendPrioriryQueue(ActionEvent event) {
    	bank.attendUser(false);
    	refreshPersonalData();
    }

    @FXML
    void cancelAccount(ActionEvent event) {
    	try {
			bank.cancelAccountOfClient();
			refreshPersonalData();
		} catch (FullStructureException e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
		}
    }

    @FXML
    void consign(ActionEvent event) {
    	try {
    		bank.consingn(Double.parseDouble(txtValue.getText()));
    		refreshPersonalData();
    	}catch(Exception e) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
    	}
    }

    @FXML
    void payCreditCard(ActionEvent event) {
    	try {
			bank.payCreditCard(byCash.isSelected());
			refreshPersonalData();
		} catch (InsufficientBalanceException e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
		}
    }

    @FXML
    void searchClient(ActionEvent event) {
    	bank.searchClient(txtCCToSearch.getText());
    	Client client = (Client) bank.getSearchedClient();
		
    	if(client!=null) {
    		txtName.setText(client.getName());
        	txtCC.setText(client.getCC());
    		labClientDate.setText((client).getIncorporationDate().toString());
    		txtAccount.setText((client).getBalance()+"");
    		txtCreditCard.setText((client).getCreditCard().getValue()+"");
    		LocalDate p = (client).getCreditCard().getLastPaymentDate();
    		if(p!=null) {
    			labPaymentDate.setText(p.toString());
    		}else {
    			labPaymentDate.setText("----");
    		}
    		btCurrent.setDisable(false);
    		btRegister.setDisable(true);
    		HBoxOperations.setDisable(true);
    	}else {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText("Client Not Found");
    		alert.showAndWait();
    	}
		
    }
    
    @FXML
    void registerNewClient(ActionEvent event) {
    	try {
			bank.registerClient();
			btRegister.setDisable(true);
		} catch (FullStructureException e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
		}
    	refreshPersonalData();
    }

    @FXML
    void showCurrentUser(ActionEvent event) {
    	refreshPersonalData();
    	btCurrent.setDisable(true);
    }

    @FXML
    void undo(ActionEvent event) {
    	try {
			bank.undo();
			refreshPersonalData();
		} catch (FullStructureException | InsufficientBalanceException e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
		}
    }

    @FXML
    void withDraw(ActionEvent event) {
    	try {
			bank.withDraw(Double.parseDouble(txtValue.getText()));
			refreshPersonalData();
		} catch (NumberFormatException | InsufficientBalanceException e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
		}
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
    	txtNameAdd.setText("");
    	txtCedulaCiudadania.setText("");
    	initializeGeneralQueue();
    }

    @FXML
    void addPriorityRow(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("becausePriority.fxml"));
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
    private Button btCurrent;
    
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
    	
    	if(rbClientName.isSelected()) {
    		bank.setOrderCriterion(Bank.SORT_BY_NAME);
    	}else if(rbCC.isSelected()){
    		bank.setOrderCriterion(Bank.SORT_BY_CC);
    	}else if(rbTime.isSelected()) {
    		bank.setOrderCriterion(Bank.SORT_BY_DATE);
    	}else {
    		bank.setOrderCriterion(Bank.SORT_BY_BALANCE);
    	}
    	
    	bank.sortDataBase();
    	bank.refreshList();
    	initializeTable();
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
    	tablePriority.setItems(users);
    	columnPriority.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
    }
    
    public void refreshPersonalData() {
    	User user = bank.getCurrentUser();
    	if(user!=null) {
    		txtName.setText(user.getName());
        	txtCC.setText(user.getCC());
        	
        	if(user instanceof Client) {
        		btRegister.setDisable(true);
        		HBoxOperations.setDisable(false);
        		user = (Client) user;
        		
        		labClientDate.setText(((Client) user).getIncorporationDate().toString());
        		txtAccount.setText(((Client) user).getBalance()+"");
        		txtCreditCard.setText(((Client) user).getCreditCard().getValue()+"");
        		LocalDate d = ((Client) user).getCreditCard().getLastPaymentDate();
        		if(d!=null) {
        			labPaymentDate.setText(d.toString());
        		}
        	}else {
        		btRegister.setDisable(false);
        		HBoxOperations.setDisable(true);
        		labClientDate.setText("- - - - -");
        		txtAccount.setText("- - - - -");
        		txtCreditCard.setText("- - - - -");
        		labPaymentDate.setText("- - - - -");
        	}
    	}else {
    		txtName.setText("");
        	txtCC.setText("");
        	btRegister.setDisable(true);
    		HBoxOperations.setDisable(true);
    		labClientDate.setText("- - - - -");
    		txtAccount.setText("- - - - -");
    		txtCreditCard.setText("- - - - -");
    		labPaymentDate.setText("- - - - -");
    	}
    }
}
