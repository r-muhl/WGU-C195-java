/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertmuhlesteinsoftware2.View;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import robertmuhlesteinsoftware2.Model.City;
import robertmuhlesteinsoftware2.Model.Customer;
import robertmuhlesteinsoftware2.Model.DBConnection;
import robertmuhlesteinsoftware2.Model.Query;
import static robertmuhlesteinsoftware2.View.LoginScreenController.currentUser;

/**
 * FXML Controller class
 *
 * @author robmu
 */
public class CustomerScreenController implements Initializable {

    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> NameColumn;
    @FXML
    private TableColumn<Customer, String> AddressColumn;
    @FXML
    private TableColumn<Customer, String> PhoneNumberColumn;
    @FXML
    private TextField PhoneNumberTextField;
    @FXML
    private ComboBox<City> CityComboBox;
    @FXML
    private TextField NameTextField;
    @FXML
    private TextField IDTextField;
    @FXML
    private TextField ZIPCodeTextField;
    @FXML
    private TextField CountryTextField;
    @FXML
    private TextField AddressTextField;
       
      
    private boolean addCustomer;
    private boolean activeCustomer;
    
   
    ToggleGroup radioButtonGroup = new ToggleGroup();
    
    
    ObservableList <Customer> customerData = FXCollections.observableArrayList();
    ObservableList <City> cityData = FXCollections.observableArrayList();
       
   
    @FXML
    private RadioButton ActiveRadioButton;
    @FXML
    private RadioButton InactiveRadioButton;
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button EditButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Button ReportButton;
    @FXML
    private Button AppointmentsButton;
    @FXML
    private Button LogOutButton;

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        AddressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getCustomerAddress()));
        PhoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerPhoneNumber()));
        
       
        //ActiveRadioButton.setSelected(true);
        //ToggleGroup radioButtonGroup = new ToggleGroup();
        ActiveRadioButton.setToggleGroup(radioButtonGroup);
        InactiveRadioButton.setToggleGroup(radioButtonGroup);
        
        
        fillCityComboBox();
        CustomerTable.getItems().setAll(fillCustomerTable());
        
        
        EventHandler <ActionEvent> event = (ActionEvent e) -> {
            
            if (CityComboBox.getValue().getCity().equals ("London")){
               CountryTextField.setText("England");
               
           }
            else if (CityComboBox.getValue().getCity().equals("New York")|| CityComboBox.getValue().getCity().equals ("Pheonix")){
                CountryTextField.setText("USA");
                
            }         
        };
        CityComboBox.setOnAction(event);
       
        
        // TODO
    }    

    @FXML
    private void EditButtonHandler(ActionEvent event) {
        // this button should populate the textfields, etc, based on the selected customer. 
        // if none selected, message = select customer
        
        addCustomer = false;
  
        if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Nothing in the table was selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Edit Customer Error");
            alert.setContentText("Please select a customer.");
            alert.showAndWait();
            return;
        } // end if statement
        else {
            System.out.println("Editting the selected customer.");
            fillEditFields();
      
        }
        
    } // closes EditButtonHandler
    
    @FXML
    private void SaveButtonHandler(ActionEvent event) throws SQLException, Exception {   // --------------- NEEDS WORK -----------------
        // Save changes made in the textfields, etc, to the database
        // populates tables with changes
        // reset textfields, etc, to default/blank
        // requires DB  - netbeans communication.
        
        boolean atleastOneAlpha = PhoneNumberTextField.getText().matches(".*[a-zA-Z]+.*");
        
        if (atleastOneAlpha == true  ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Save Customer Error");
            alert.setContentText("Please ensure the phone number field contains numbers only. ");
            alert.showAndWait();
            return;
        }
        
        

        if (IDTextField.getText().isEmpty() || NameTextField.getText().isEmpty() || PhoneNumberTextField.getText().isEmpty() || ZIPCodeTextField.getText().isEmpty() || AddressTextField.getText().isEmpty()   ) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Save Customer Error");
            alert.setContentText("Please ensure all fields are filled in.");
            alert.showAndWait();
            return;
        }
              
        
        if (radioButtonGroup.getSelectedToggle() != null || CityComboBox.getSelectionModel().getSelectedItem() == null) {
        
            if (addCustomer == true) {
                if (activeCustomer == true){
                String addActive = "Insert into customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)" 
                + "VALUES (\""+ IDTextField.getText() +"\" , \"" + NameTextField.getText() + "\" ,\"" + IDTextField.getText() + "\", 1, NOW(),\""+ currentUser +"\",NOW(),\"" + currentUser +"\")";
                Query.makeQuery(addActive);
            
                int ID = Integer.parseInt(IDTextField.getText());
                String name = NameTextField.getText();
                String address = AddressTextField.getText();
                City city = CityComboBox.getSelectionModel().getSelectedItem();
                String country = CountryTextField.getText();
                String zipcode = ZIPCodeTextField.getText();
                String phone = PhoneNumberTextField.getText();
     
                
                String NA = "N/A";
                
                String addActiveAddress = "Insert into address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                + "VALUES (\"" + ID + "\" , \"" + address + "\" , \"" + NA + "\" , \"" +city.getCityID() + "\" , \"" + zipcode + "\" , \"" + phone + "\" , NOW() , \"" + currentUser + "\" , now() , \"" + currentUser + "\" )";
        
                Query.makeQuery(addActiveAddress);
                
                customerData.add(new Customer (ID,name,address,city,country,zipcode,phone));
                CustomerTable.getItems().setAll(fillCustomerTable());
                
                
                NameTextField.clear();
                PhoneNumberTextField.clear();
                IDTextField.clear();
                ZIPCodeTextField.clear();
                CountryTextField.clear();
                AddressTextField.clear();
                radioButtonGroup.selectToggle(null);
                CityComboBox.valueProperty().set(null);
                
                            
                } // closes if activeCustomer = true
            
                else {
                    String addInactive = "Insert into customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
                    + "VALUES (\""+ IDTextField.getText() + "\" , \"" + NameTextField.getText() + "\" ,\"" +IDTextField.getText() + "\", 0, NOW(),\""+ currentUser +"\",NOW(),\"" + currentUser +"\")";
                    Query.makeQuery(addInactive);
                            
                    int ID = Integer.parseInt(IDTextField.getText());
                    String name = NameTextField.getText();
                    String address = AddressTextField.getText();
                    City city = CityComboBox.getSelectionModel().getSelectedItem();
                    String country = CountryTextField.getText();
                    String zipcode = ZIPCodeTextField.getText();
                    String phone = PhoneNumberTextField.getText();
                
                    
                    String NA = "N/A";
                    
                    String addInactiveAddress = "Insert into address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                    + "VALUES (\"" + ID + "\" , \"" + address + "\" , \"" + NA + "\" , \"" +city.getCityID() + "\" , \"" + zipcode + "\" , \"" + phone + "\" , NOW() , \"" + currentUser + "\" , now() , \"" + currentUser + "\" )";
                    
                    Query.makeQuery(addInactiveAddress);
                    
                    customerData.add(new Customer (ID,name,address,city,country,zipcode,phone));
                    CustomerTable.getItems().setAll(fillCustomerTable());
                    
                    
                    NameTextField.clear();
                    PhoneNumberTextField.clear();
                    IDTextField.clear();
                    ZIPCodeTextField.clear();
                    CountryTextField.clear();
                    AddressTextField.clear();
                    radioButtonGroup.selectToggle(null);
                    CityComboBox.valueProperty().set(null);
                    
                }// closes else statement (add inactive)
        
            } //closes if addCustomer = true
            else { 
                if (activeCustomer == true) {             
        
                    String NA = "N/A";
                    
                    String editActive = "update address, customer, city, country\n" +
                    "set customer.customerId = \""+ IDTextField.getText() +"\" , customer.customerName = \""+NameTextField.getText()+"\" , customer.addressId =\""+IDTextField.getText()+"\"  , customer.active = 1 , customer.lastUpdate = now()  , customer.lastUpdateBy = \""+currentUser+"\" , \n" +
                    "address.address = \""+AddressTextField.getText()+"\"  , address.address2 = \""+NA+"\"  , address.cityId = \""+CityComboBox.getValue().getCityID()+"\"  , address.postalCode = \""+ZIPCodeTextField.getText()+"\" , address.phone = \""+PhoneNumberTextField.getText()+"\"  , address.lastUpdate = now()  , address.lastUpdateBy = \""+currentUser+"\"  \n" +
                    "where customer.customerId = \""+IDTextField.getText()+"\"   and customer.addressId = address.addressId and address.cityId = city.cityId and city.countryId = country.countryId";
            
                    Query.makeQuery(editActive);
                    
                    CustomerTable.getItems().setAll(fillCustomerTable());
                   
                    NameTextField.clear();
                    PhoneNumberTextField.clear();
                    IDTextField.clear();
                    ZIPCodeTextField.clear();
                    CountryTextField.clear();
                    AddressTextField.clear();
                    radioButtonGroup.selectToggle(null);
                    CityComboBox.valueProperty().set(null);
                           
                } // closes edit active customer if statement
                else {

                    String NA = "N/A";
                    
                    String editInactive = "update address, customer, city, country\n" +
                        "set customer.customerId = \""+ IDTextField.getText() +"\" , customer.customerName = \""+NameTextField.getText()+"\" , customer.addressId =\""+IDTextField.getText()+"\"  , customer.active = 1 , customer.lastUpdate = now()  , customer.lastUpdateBy = \""+currentUser+"\" , \n" +
                        "address.address = \""+AddressTextField.getText()+"\"  , address.address2 = \""+NA+"\"  , address.cityId = \""+CityComboBox.getValue().getCityID()+"\"  , address.postalCode = \""+ZIPCodeTextField.getText()+"\" , address.phone = \""+PhoneNumberTextField.getText()+"\"  , address.lastUpdate = now()  , address.lastUpdateBy = \""+currentUser+"\"  \n" +
                        "where customer.customerId = \""+IDTextField.getText()+"\"   and customer.addressId = address.addressId and address.cityId = city.cityId and city.countryId = country.countryId";
                                       
                    Query.makeQuery(editInactive);
                
                    CustomerTable.getItems().setAll(fillCustomerTable());
                
                    NameTextField.clear();
                    PhoneNumberTextField.clear();
                    IDTextField.clear();
                    ZIPCodeTextField.clear();
                    CountryTextField.clear();
                    AddressTextField.clear();
                    radioButtonGroup.selectToggle(null);
                    CityComboBox.valueProperty().set(null);
                    
                } // closes else statement (edit inactive)
        
            } // else statement (edit customer)
        } // closes if radioButtonGroup.selectedToggle != null
        else {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Save Customer Error");
            alert.setContentText("Please ensure active/ inactive and a city are selected.");
            alert.showAndWait();
            return;
        } // closes else statement (no radio button selected)
        
        
        CustomerTable.getItems().setAll(fillCustomerTable());
        
    } // closes SaveButtonHandler

    @FXML
    private void AddButtonHandler(ActionEvent event) {
        // click add -> autopopulate IDTextField
        // error message if you click add and any textfield is filled in
       

        addCustomer = true;
        
        String customerName = NameTextField.getText();
        String customerPhNumber = PhoneNumberTextField.getText();
        String customerAddress = AddressTextField.getText();
        String country = CountryTextField.getText();
        String ZIPcode = ZIPCodeTextField.getText();
        
        // Error message if text fields aren't empty
        if (customerName.length() != 0 || customerPhNumber.length() != 0 || customerAddress.length() != 0 || country.length() != 0 || ZIPcode.length() != 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Add Customer Error");
            alert.setContentText("To add a new customer: "
                    + " 1) Ensure all text fields are cleared "
                    + " 2) Click add (this will autopopulate the ID text field with a new customer ID number) "
                    + " 3) Enter the customer's information " 
                    + " 4) Click save ");
            alert.showAndWait();
        } // closes (error) if statement
        else {
            
            int newID = 1;
            String FindNewID = "SELECT customerId FROM customer";
            Query.makeQuery(FindNewID);
            ResultSet ResultID = Query.getResult();
            try {
                while (ResultID.next()) {
                    newID++;
                    
                }// closes while loop
                
                IDTextField.setText(Integer.toString(newID));
                    
            } // closes Try 
            catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } // closes catch
            
        } // closes (no error) else statement
  
    } // closes AddButtonHandler

    @FXML
    private void CancelButtonHandler(ActionEvent event) {
        // set textfields, etc, to default (cleared)
        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to cancel? This will clear all textboxes.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
        
            NameTextField.clear();
            PhoneNumberTextField.clear();
            IDTextField.clear();
            ZIPCodeTextField.clear();
            CountryTextField.clear();
            AddressTextField.clear();
            radioButtonGroup.selectToggle(null);
            CityComboBox.valueProperty().set(null);
        } // closes if (result.get() == ButtonType.OK
        
        
    } // closes CancelButtonHandler

    @FXML
    private void LogOutButtonHandler(ActionEvent event) throws IOException, Exception {
        // return to login screen
        // closes DB interaction
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Log Out");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to logout? All unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            String loginReport = "src/Files/loginReport";
            FileWriter fwriter = new FileWriter(loginReport, true);
            PrintWriter logInReport = new PrintWriter(fwriter);
            LocalDateTime now = LocalDateTime.now();
            Calendar cal = Calendar.getInstance();
            logInReport.print (cal.getTime());
            logInReport.close();
            
            DBConnection.closeConnection();
            Stage stage;
            Parent root;
            stage = (Stage) AddressTextField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show();  
        } // closes if (result.get() == ButtonType.OK
        
    } // closes LogoutButtonHandler


    @FXML
    private void DeleteButtonHandler(ActionEvent event) {
        // delete selected customer from tableview & DB
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete this customer? This will also remove the customer from the database.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
        
            if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
                System.out.println("Nothing in the table was selected");
                Alert delete = new Alert(Alert.AlertType.INFORMATION);
                delete.setTitle("Error");
                delete.setHeaderText("Delete Customer Error");
                delete.setContentText("Please select a customer");
                delete.showAndWait();
                return;
            } // end if statement
            else {
                
                try {
                    String deleteCustomer = "delete customer.*, address.* from customer inner join address\n" +
                                "where address.addressId = customer.addressId and customer.customerId = \""+ CustomerTable.getSelectionModel().getSelectedItem().getCustomerID() +"\" ";
                    Query.makeQuery(deleteCustomer);
                               
                    CustomerTable.getItems().setAll(fillCustomerTable());
                    
                } // closes try
                catch(Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            
            
      
            }// closes else
       
        } // closes if (result.get() == ButtonType.OK
        
    } // closes DeleteButtonHandler

    @FXML
    private void AppointmentsButtonHandler(ActionEvent event) throws IOException {
        // send to AppointmentScreen
        
        Stage stage;
        Parent root;
        stage = (Stage) AddressTextField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene (root);
        stage.setScene (scene);
        stage.show();  
                
    } // closes AppointmentButtonHandler

    @FXML
    private void ActivateRadioButtonHandler(ActionEvent event) {
        activeCustomer = true;
    }

    @FXML
    private void InactiveRadioButtonHandler(ActionEvent event) {
        activeCustomer = false;
    }
    
    
    private void fillCityComboBox() {
       
        
        try {
        String findCities = "SELECT cityId, city FROM city";
        Query.makeQuery(findCities);
        ResultSet result = Query.getResult();
            while (result.next() ) {
                
                cityData.add(new City(result.getInt("city.cityId"),result.getString("city.city")) );
                
            } // closes while loop
        } // closes try
        
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        CityComboBox.setConverter(new StringConverter <City>() {
         
            @Override
            public String toString (City cityData) {
                return cityData.getCity();
            }

            @Override
            public City fromString(String string) {
                return CityComboBox.getItems().stream().filter(ap -> 
                ap.getCity().equals(string)).findFirst().orElse(null);
            }
    
            }); //closes setConverter
        CityComboBox.setItems(cityData);
               
               
    } // close fillCityComboBox()
    
   
    
    private List fillCustomerTable () {
        ObservableList <Customer> customerData = FXCollections.observableArrayList();
        
        try {
            String fillCustomers = "Select customer.customerId, customer.customerName, address.address, address.postalCode, city.cityId, city.city, country.country, address.phone\n" +
                                    "from customer, address, city, country\n" +
                                    "where customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId ";
          
            Query.makeQuery(fillCustomers);
            ResultSet result = Query.getResult();
            
            while (result.next()) {
                int customerID = result.getInt("customer.customerId");
                String customerName = result.getString("customer.customerName");
                String address = result.getString("address.address");
                City city = new City (result.getInt("city.cityId"), result.getString("city.city"));
                String country = result.getString("country.country");
                String zipCode = result.getString("address.postalcode");
                String phoneNum = result.getString("address.phone");
                
                
                customerData.add (new Customer (customerID,customerName,address,city,country,zipCode,phoneNum));
            }// closes while loop
        } // closes try
        catch (Exception e) {
            System.out.println ("Error: " + e.getMessage());
        }
             
        return customerData;
        
    } // closes fillCustomerTable
    
   private void fillEditFields () {
        String CustomerTableSelection = Integer.toString(CustomerTable.getSelectionModel().getSelectedItem().getCustomerID());
        
        try {
       
            String selectedCustomer = "select customer.customerId, customer.customerName,address.phone, address.address, city.city,country.country, address.postalCode, customer.Active\n" +
                                    "from customer, address, city, country\n" +
                                    "where customerId = \""+ Integer.toString(CustomerTable.getSelectionModel().getSelectedItem().getCustomerID())+ "\" and customer.addressId = address.addressId and city.countryId = country.countryId and address.cityId = city.cityId ;";
            Query.makeQuery(selectedCustomer);
            ResultSet result = Query.getResult();
            while (result.next()) {
                int custID = result.getInt("customer.customerId");
                IDTextField.setText(Integer.toString(custID));
                String custName = result.getString ("customer.customerName");
                NameTextField.setText(custName);
                String custNumber = result.getString("address.phone");
                PhoneNumberTextField.setText(custNumber);
                String custAddress = result.getString ("address.address");
                AddressTextField.setText(custAddress);
                String custCountry = result.getString ("country.country");
                CountryTextField.setText(custCountry);
                String zipCode = result.getString("address.postalcode");
                ZIPCodeTextField.setText(zipCode);
                int active = result.getInt("customer.Active");
                
                if (active == 0) {
                    InactiveRadioButton.setSelected(true);
                }
                else if (active == 1) {
                    ActiveRadioButton.setSelected(true);
                }
                
                String cityName = result.getString("city.city");
                
                if (cityName.equalsIgnoreCase("Pheonix")) {
                CityComboBox.getSelectionModel().selectFirst();
                
                }
                else if (cityName.equalsIgnoreCase("New York")) {
                    CityComboBox.getSelectionModel().select(1);
                    
                }
                else if (cityName.equalsIgnoreCase("London")) {
                    CityComboBox.getSelectionModel().selectLast();
                    
                }
        
            } // close while loop
        
        }// close try
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
   } 
   
  
    private void fillCountryTextField (java.awt.event.ActionEvent evt) {
       
        String cityName = CityComboBox.getSelectionModel().getSelectedItem().getCity();
        String countryName = "select country.country ,city.city from city, country where city.city= \""+cityName+"\" and city.countryId = country.countryId";
        Query.makeQuery(countryName);
        ResultSet ResultName = Query.getResult();
        try {
            while (ResultName.next()){
                String setName = ResultName.getString("country");
                CountryTextField.setText(setName);
                }
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
       
   } 

    @FXML
    private void ReportButtonHandler(ActionEvent event) throws IOException {
         
        // --------------------------- DELETE ME -------------------------------------------------
        System.out.println("you clicked the report button");
        // --------------------------- DELETE ME -------------------------------------------------
        
        Stage stage;
        Parent root;
        stage = (Stage) CustomerTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        root = loader.load();
        Scene scene = new Scene (root);
        stage.setScene (scene);
        stage.show(); 
        
    }
   
} // closes CustomerScreenController
