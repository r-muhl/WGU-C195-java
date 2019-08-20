/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertmuhlesteinsoftware2.View;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import static robertmuhlesteinsoftware2.RobertMuhlesteinSoftware2.time;
import robertmuhlesteinsoftware2.Model.User;
import robertmuhlesteinsoftware2.Model.Appointment;
import robertmuhlesteinsoftware2.Model.Customer;
import robertmuhlesteinsoftware2.Model.Query;
import robertmuhlesteinsoftware2.Model.Type;
import static robertmuhlesteinsoftware2.View.LoginScreenController.currentUser;

/**
 * FXML Controller class
 *
 * @author robmu
 */
public class AddAppointmentScreenController implements Initializable {

    @FXML
    private Button SearchButton;
    @FXML
    private TextField SearchTextField;
    @FXML
    private Button SaveButton;
    @FXML
    private TextField TitleTextField;
    @FXML
    private ComboBox<Type> TypeComboBox;
    @FXML
    private ComboBox<String> StartComboBox;
    @FXML
    private ComboBox<String> EndComboBox;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextArea DescriptionTextArea;
    @FXML
    private TableView<Customer> AddAppointmentTable;
    @FXML
    private TableColumn<Customer, String> CustomerNameColumn;
    @FXML
    private TableColumn<Customer, Integer> CustomerIDColumn;
    @FXML
    private Button ClearButton;
    @FXML
    private Button ReturnButton;
    @FXML
    private Label AppointmentLabel;
    @FXML
    private ComboBox <User> ConsultantComboBox;
    
    private final ObservableList <String> setStartTimes = FXCollections.observableArrayList(    "09:00" , "09:15" , "09:30" , "09:45" ,
                                                                                                "10:00" , "10:15" , "10:30" , "10:45" ,
                                                                                                "11:00" , "11:15" , "11:30" , "11:45" ,
                                                                                                "12:00" , "12:15" , "12:30" , "12:45" ,
                                                                                                "13:00" , "13:15" , "13:30" , "13:45" ,
                                                                                                "14:00" , "14:15" , "14:30" , "14:45" ,
                                                                                                "15:00" , "15:15" , "15:30" , "15:45" ,
                                                                                                "16:00" , "16:15" , "16:30" , "16:45" );
    
    private final ObservableList <String> setEndTimes = FXCollections.observableArrayList(      "09:15" , "09:30" , "09:45" ,
                                                                                                "10:00" , "10:15" , "10:30" , "10:45" ,
                                                                                                "11:00" , "11:15" , "11:30" , "11:45" ,
                                                                                                "12:00" , "12:15" , "12:30" , "12:45" ,
                                                                                                "13:00" , "13:15" , "13:30" , "13:45" ,
                                                                                                "14:00" , "14:15" , "14:30" , "14:45" ,
                                                                                                "15:00" , "15:15" , "15:30" , "15:45" ,
                                                                                                "16:00" , "16:15" , "16:30" , "16:45" ,
                                                                                                "17:00" );
    
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    
    private final ZoneId ZoneID = ZoneId.systemDefault();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
        CustomerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        CustomerIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerID()).asObject());
        AddAppointmentTable.getItems().setAll(fillTable());
        fillTypeComboBox();
        fillUserComboBox();
        fillStartTime();
        fillEndTime();
        DatePicker.setValue(LocalDate.now());
       
        
    }   
    
    
    @FXML
    private void SaveButtonHandler(ActionEvent event) throws SQLException {
        // save info to db
      
        
        try {
        
        String AppointmentID = "select appointmentId from appointment";
        Query.makeQuery(AppointmentID);
        ResultSet IDResult = Query.getResult();
        int newID = 1;
        while (IDResult.next()) {
            int ID = IDResult.getInt("appointment.appointmentId");
            newID ++;
        }
        
        String CustomerName = AddAppointmentTable.getSelectionModel().getSelectedItem().getCustomerName();
        int CustomerID = AddAppointmentTable.getSelectionModel().getSelectedItem().getCustomerID();
        String Title = TitleTextField.getText();
        String Type = TypeComboBox.getSelectionModel().getSelectedItem().getTheType();
        String Consultant = ConsultantComboBox.getSelectionModel().getSelectedItem().getUsername();
        String StartTime = StartComboBox.getSelectionModel().getSelectedItem();
        String EndTime = EndComboBox.getSelectionModel().getSelectedItem();
        String Date = DatePicker.getValue().toString();
        String StartDateTime = Date +" " + StartTime;
        String EndDateTime = Date + " " + EndTime;
        String Description = DescriptionTextArea.getText();
        String location = "????";
          
        
        String contact = ConsultantComboBox.getSelectionModel().getSelectedItem().getUsername(); 
        
        try {
            String checkAppt = "select * from appointment "
                + "where ( \"" + StartDateTime + "\" between start and end or \"" + EndDateTime + "\" "
                + "between start and end or \""+ StartDateTime + "\" < start and \"" + EndDateTime + "\" > end   ) "
                + "and (contact =  \"" + contact + "\" and appointmentId != \"" + newID + "\"   )  ";
            Query.makeQuery(checkAppt);
            ResultSet checkResult = Query.getResult();
            if (checkResult.next() ) {
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Save Customer Error");
                alert.setContentText("Conflicting appointment. Please compare the start/ end times to the selected consultant's schedule. ");
                alert.showAndWait();
                return;
          
            } //  closes checkResult if statement                
                
            else { // checkResult.next()  
                
                int startIndex = StartComboBox.getSelectionModel().getSelectedIndex();
                int endIndex = EndComboBox.getSelectionModel().getSelectedIndex();
            
                if (startIndex-1 == endIndex || endIndex < startIndex) {
                    Alert alertIndex = new Alert(Alert.AlertType.ERROR);
                    alertIndex.setTitle("Error");
                    alertIndex.setHeaderText("Save Customer Error");
                    alertIndex.setContentText("End time should be after Start time. ");
                    alertIndex.showAndWait();
                    return;
                }
                Date selectedDate = java.sql.Date.valueOf(DatePicker.getValue());
                
                if (selectedDate.before(new Date())  )  {
                    Alert alertIndex = new Alert(Alert.AlertType.ERROR);
                    alertIndex.setTitle("Error");
                    alertIndex.setHeaderText("Save Customer Error");
                    alertIndex.setContentText("The date you have selected is invalid. Please select a date that's after today. ");
                    alertIndex.showAndWait();
                    return;
                    
                }
                
                
                
                else {
                    String addAppointment = "insert into appointment (appointmentId, customerId, title, description, location, contact, "
                            + "url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
                            + "values ( \"" + newID + "\" , \"" + CustomerID + "\" , \"" + Title + "\" , \"" + Description + "\" , \"" + location + "\" , \"" + Consultant + "\" ,"
                            + " \"" + Type + "\" , \"" + StartDateTime + "\" , \"" + EndDateTime + "\" , now() , \"" + currentUser + "\" , now() , \"" + currentUser + "\" ) ";
                    Query.makeQuery(addAppointment);
        
            
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Save Successful");
                    alert.setContentText("The appointment was successfully saved! Clicking OK will reset this page and clear all fields.");
                    Optional<ButtonType> result = alert.showAndWait();
            
                    if (result.get() == ButtonType.OK){
                        SearchTextField.clear();
                        AddAppointmentTable.getItems().setAll(fillTable());
                        TitleTextField.clear();
                        TypeComboBox.valueProperty().set(null);
                        ConsultantComboBox.valueProperty().set(null);
                        StartComboBox.valueProperty().set(null);
                        EndComboBox.valueProperty().set(null);
                        DatePicker.setValue(null);
                        DescriptionTextArea.clear();
             
                    } // closes result.get() == ButtonType
                
                
                } // closes else    
              
            } //closes checkResult.next()
            
        } // closes inner try
        catch(Exception e) {
            System.out.println("Error1: "+e.getMessage());
        }
        
        } // closes closes try
        catch (Exception e) {
            System.out.println("Error2: " +e.getMessage());
            checkEmpty();
        }
        
        
    } // closes SaveButtonHandler


    @FXML
    private void SearchButtonHandler(ActionEvent event) {
        // search AddAppointmentTable for ID or CustomerName
        
        ObservableList <Customer> searchList = FXCollections.observableArrayList();
        String name = SearchTextField.getText();
        boolean found = false;
                       
        try {
            String searchName = "select customerName, customerId from customer";
            Query.makeQuery(searchName);
            ResultSet result = Query.getResult();
            while (result.next()) {
                String foundName = result.getString("customer.customerName");
                int foundID = result.getInt("customer.customerId");
                if (name.equals(foundName)) {
                    found = true;
                    searchList.add (new Customer (foundID, foundName));
                } // closes if statement
 
            } // closes while loop
  
            if (found == true) {
                AddAppointmentTable.setItems(searchList);
            }
            else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setHeaderText("Customer not found");
            alert.setContentText ("There are no customers that match that name. Please check spelling and enter the full name. ");
            alert.showAndWait();
            }
        } // closes try
        
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        
        
    } // closes SearchButtonHandler

    @FXML
    private void ClearButtonHandler(ActionEvent event) {
        // clears SearchTextField
        // reset AddAppointmentTable
        
        SearchTextField.clear();
        AddAppointmentTable.getItems().setAll(fillTable());
   
    } // closes ClearButtonHandler

    @FXML
    private void ReturnButtonHandler(ActionEvent event) throws IOException {
        // alert "are you sure? all unsaved appointments will be lost"
        // clears all fields, comboboxes, areas
        // returns to AppointmentScreen
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Return");
        alert.setHeaderText("Return Confirmation");
        alert.setContentText("Are you sure you want to return to the Appointment Screen? "
                + "All unsaved appointments will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            Stage stage;
            Parent root;
            stage = (Stage) SaveButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show(); 
            
        } // closes if statement

    } // closes ReturnButtonHandler
    
    private List fillTable () {
        ObservableList <Customer> customerData = FXCollections.observableArrayList();
        
        try {
            String fillCustomer = "select customerId, customerName from customer";
            Query.makeQuery(fillCustomer);
            ResultSet result = Query.getResult();
            
            while (result.next()) {
                int customerID = result.getInt("customer.customerId");
                String customerName = result.getString ("customer.customerName");
                customerData.add (new Customer (customerID, customerName));
            }
            
        } // closes try
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return customerData;
        
    }// closes fillTable
    
    private void fillTypeComboBox () {
        ObservableList <Type> fillType = FXCollections.observableArrayList();
        
        try {
            String fillComboBox = "select incrementTypeDescription from incrementtypes";
            Query.makeQuery(fillComboBox);
            ResultSet result = Query.getResult();
            
            while (result.next()) {
                fillType.add (new Type (result.getString("incrementtypes.incrementTypeDescription")));
            } // closes while loop
     
        } // closes try
        
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        TypeComboBox.setConverter(new StringConverter <Type> () {
            @Override
            public String toString (Type fillType ) {
                return fillType.getTheType();
            }
            @Override
            public Type fromString (String string) {
                return TypeComboBox.getItems().stream().filter(ap ->
                ap.getTheType().equals(string)).findFirst().orElse(null);
            }
            
        });
        TypeComboBox.setItems(fillType);
        
    } // closes fillTypeComboBox
    
    private void fillUserComboBox() {
        ObservableList <User> user = FXCollections.observableArrayList();
        try {
            String fillUser = "select userName from user";
            Query.makeQuery(fillUser);
            ResultSet userResult = Query.getResult();
            while (userResult.next()) {
                user.add(new User (userResult.getString("userName")));
            }
                        
        } // closes try
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            
        }
        ConsultantComboBox.setConverter(new StringConverter <User> () {
            @Override
            public String toString (User fillUser ) {
                return fillUser.getUsername();
            }
            @Override
            public User fromString (String string) {
                return ConsultantComboBox.getItems().stream().filter(ap ->
                ap.getUsername().equals(string)).findFirst().orElse(null);
            }
            
        });
        ConsultantComboBox.setItems (user);
        
    } // closes fillUserComboBox
  

    private void fillStartTime() {
        
        //StartComboBox.setItems(setStartTimes);
        
        if (time.contains ("London")) {
            
            StartComboBox.getItems().addAll( "16:00" , "16:15" , "16:30" , "16:45" ,
                                             "17:00" , "17:15" , "17:30" , "17:45" ,
                                             "18:00" , "18:15" , "18:30" , "18:45" ,
                                             "19:00" , "19:15" , "19:30" , "19:45" ,
                                             "20:00" , "20:15" , "20:30" , "20:45" ,
                                             "21:00" , "21:15" , "21:30" , "21:45" ,
                                             "22:00" , "22:15" , "22:30" , "22:45" ,
                                             "23:00" , "23:15" , "23:30" , "23:45" );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appointment Time Info");
            alert.setContentText("These times reflect 0900 - 1645 for our Headquarters in Pheonix, AZ.");
            alert.showAndWait();
            
            
        }
        if (time.contains("New_York")) {
            
            StartComboBox.getItems().addAll( "11:00" , "11:15" , "11:30" , "11:45" ,
                                             "12:00" , "12:15" , "12:30" , "12:45" ,
                                             "13:00" , "13:15" , "13:30" , "13:45" ,
                                             "14:00" , "14:15" , "14:30" , "14:45" ,
                                             "15:00" , "15:15" , "15:30" , "15:45" ,
                                             "16:00" , "16:15" , "16:30" , "16:45" ,
                                             "17:00" , "17:15" , "17:30" , "17:45" ,
                                             "18:00" , "18:15" , "18:30" , "18:45" );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appointment Time Info");
            alert.setContentText("These times reflect 0900 - 1645 for our Headquarters in Pheonix, AZ.");
            alert.showAndWait();
            
        }
        else {
            
            StartComboBox.getItems().addAll( "09:00" , "09:15" , "09:30" , "09:45" ,
                                             "10:00" , "10:15" , "10:30" , "10:45" ,
                                             "11:00" , "11:15" , "11:30" , "11:45" ,
                                             "12:00" , "12:15" , "12:30" , "12:45" ,
                                             "13:00" , "13:15" , "13:30" , "13:45" ,
                                             "14:00" , "14:15" , "14:30" , "14:45" ,
                                             "15:00" , "15:15" , "15:30" , "15:45" ,
                                             "16:00" , "16:15" , "16:30" , "16:45" );
        }
        
    }

    
    private void fillEndTime() {
        
        //EndComboBox.setItems(setEndTimes);
        
        if (time.contains("London")) {
            
            EndComboBox.getItems().addAll( "16:15" , "16:30" , "16:45" ,
                                           "17:00" , "17:15" , "17:30" , "17:45" ,
                                           "18:00" , "18:15" , "18:30" , "18:45" ,
                                           "19:00" , "19:15" , "19:30" , "19:45" ,
                                           "20:00" , "20:15" , "20:30" , "20:45" ,
                                           "21:00" , "21:15" , "21:30" , "21:45" ,
                                           "22:00" , "22:15" , "22:30" , "22:45" ,
                                           "23:00" , "23:15" , "23:30" , "23:45" ,
                                           "24:00" );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appointment Time Info");
            alert.setContentText("These times reflect 0915 - 1700 for our Headquarters in Pheonix, AZ.");
            alert.showAndWait();
            
        }
        if (time.contains ("New_York")) {
            StartComboBox.getItems().addAll( "11:15" , "11:30" , "11:45" ,
                                             "12:00" , "12:15" , "12:30" , "12:45" ,
                                             "13:00" , "13:15" , "13:30" , "13:45" ,
                                             "14:00" , "14:15" , "14:30" , "14:45" ,
                                             "15:00" , "15:15" , "15:30" , "15:45" ,
                                             "16:00" , "16:15" , "16:30" , "16:45" ,
                                             "17:00" , "17:15" , "17:30" , "17:45" ,
                                             "18:00" , "18:15" , "18:30" , "18:45" ,
                                             "19:00");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appointment Time Info");
            alert.setContentText("These times reflect 0915 - 1700 for our Headquarters in Pheonix, AZ.");
            alert.showAndWait();
        }
        else {
            
            EndComboBox.getItems().addAll( "09:15" , "09:30" , "09:45" ,
                                           "10:00" , "10:15" , "10:30" , "10:45" ,
                                           "11:00" , "11:15" , "11:30" , "11:45" ,
                                           "12:00" , "12:15" , "12:30" , "12:45" ,
                                           "13:00" , "13:15" , "13:30" , "13:45" ,
                                           "14:00" , "14:15" , "14:30" , "14:45" ,
                                           "15:00" , "15:15" , "15:30" , "15:45" ,
                                           "16:00" , "16:15" , "16:30" , "16:45" ,
                                           "17:00" );
        }
        

        
    }
    
    private String checkEmpty() {
        String errorMessage = "";
        boolean error = false;
        LocalDate date = DatePicker.getValue();
        LocalTime startingTime = LocalTime.parse(StartComboBox.getSelectionModel().getSelectedItem(), timeDTF);
        LocalTime endingTime = LocalTime.parse(EndComboBox.getSelectionModel().getSelectedItem(), timeDTF);
        
        LocalDateTime startDT = LocalDateTime.of(date, startingTime);
        LocalDateTime endDT = LocalDateTime.of(date,endingTime);
        
        ZonedDateTime startGMT = startDT.atZone(ZoneID).withZoneSameInstant(ZoneID.of("GMT"));
        ZonedDateTime endGMT = endDT.atZone(ZoneID).withZoneSameInstant(ZoneID.of("GMT"));
        
        String starting = StartComboBox.getSelectionModel().getSelectedItem();
        String ending = EndComboBox.getSelectionModel().getSelectedItem();
        
        int startIndex = StartComboBox.getSelectionModel().getSelectedIndex();
        int endIndex = EndComboBox.getSelectionModel().getSelectedIndex();
        
        
        
        if (AddAppointmentTable.getSelectionModel().isEmpty()) {
            errorMessage = errorMessage + "Please select a customer in the table. ";
            error = true;
        }
        if (TitleTextField.getText().isEmpty()) {
            errorMessage = errorMessage + "Please enter a title. ";
            error = true;
        }
        if (TypeComboBox.getSelectionModel().isEmpty()) {
            errorMessage = errorMessage + "Please select a type. ";
            error = true;
        }
        if (ConsultantComboBox.getSelectionModel().isEmpty()) {
            errorMessage = errorMessage + "Please enter a consultant. ";
            error = true;
        }
        if (StartComboBox.getSelectionModel().isEmpty()) {
            errorMessage = errorMessage + "Please select a start time. ";
            error = true;
        }
        if (EndComboBox.getSelectionModel().isEmpty()) {
            errorMessage = errorMessage + "Please select an end time. ";
            error = true;
        }
        if (DescriptionTextArea.getText().isEmpty()) {
            errorMessage = errorMessage + "Please enter a description. ";
            error = true;
        }
       
        if (error == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Save Customer Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            //return false;
        }
       
        return errorMessage;
    } // closes checkEmpty
    
    
    
    
} // closes AddAppointmentScreenController
