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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import robertmuhlesteinsoftware2.Model.Appointment;
import robertmuhlesteinsoftware2.Model.Customer;
import robertmuhlesteinsoftware2.Model.DBConnection;
import robertmuhlesteinsoftware2.Model.Query;
import robertmuhlesteinsoftware2.Model.Type;
import static robertmuhlesteinsoftware2.View.LoginScreenController.currentUser;

/**
 * FXML Controller class
 *
 * @author robmu
 */
public class AppointmentScreenController implements Initializable {

    @FXML
    private RadioButton WeekAppointmentRadioButton;
    @FXML
    private RadioButton MonthAppointmentRadioButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button EditButton;
    @FXML
    private Button AddButton;
    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Appointment, String> ConsultantColumn;
    @FXML
    private TableColumn<Appointment, String> StartColumn;
    @FXML
    private TableColumn<Appointment, String> EndColumn;
    @FXML
    private TableColumn<Appointment, String> CustomerColumn;
    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;
    @FXML
    private Button CustomerButton;
    @FXML
    private Button LogOutButton;
    @FXML
    private TableColumn<Appointment, String> TypeColumn;
    
    private static Appointment edittingInfo;
    @FXML
    private Button ReportButton;
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ConsultantColumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getUser()));
        StartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
        EndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
        CustomerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        DescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getType().getTheType()));
        
        
        ToggleGroup radioButtonGroup = new ToggleGroup();
        WeekAppointmentRadioButton.setToggleGroup(radioButtonGroup);
        MonthAppointmentRadioButton.setToggleGroup(radioButtonGroup);
        
        AppointmentTable.getItems().setAll(fillTable());
                
        upcomingAppointment();
        
        
        // TODO
    }    

    @FXML
    private void WeekAppointmentRadioButtonHandler(ActionEvent event) {
        // View upcoming weekly appointments
        
        ObservableList <Appointment> weeklyData = FXCollections.observableArrayList();
        
        try {
            String weeklyAppts = "SELECT appointment.contact, appointment.start, appointment.end, customer.customerName, appointment.url,"
                    + " appointment.description FROM customer, appointment WHERE start >= current_date() and start <= date_add(current_date(), interval 7 day) "
                    + " and customer.customerId = appointment.customerId";
            Query.makeQuery(weeklyAppts);
            ResultSet weeklyResult = Query.getResult();
            
            while (weeklyResult.next()) {
                
                String consultant = weeklyResult.getString("appointment.contact");
                String startTime = weeklyResult.getString("appointment.start");
                String endTime = weeklyResult.getString("appointment.end");
                Customer customer = new Customer();
                customer.setCustomerName(weeklyResult.getString("customer.customerName"));
                Type type = new Type();
                type.setTheType(weeklyResult.getString ("appointment.url"));
                String description = weeklyResult.getString("appointment.description");
                weeklyData.add(new Appointment (customer, type, description, startTime, endTime, consultant ));
                
                
            }
            
            //AppointmentTable.getItems().setAll(weeklyData);
            
            
        }
        catch( Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        AppointmentTable.getItems().setAll(weeklyData);
        
    }  // closes WeekAppointmentRadioButtonHandler

    
    @FXML
    private void MonthAppointmentRadioButtonHandler(ActionEvent event) {
        //view upcoming monthly appointments
        
        
        ObservableList <Appointment> monthlyData = FXCollections.observableArrayList();
        
        try {
            String monthlyAppts = "SELECT appointment.contact, appointment.start, appointment.end, customer.customerName, appointment.url,"
                    + " appointment.description FROM customer, appointment WHERE start >= current_date() and start <= date_add(current_date(), interval 1 month) "
                    + " and customer.customerId = appointment.customerId";
            Query.makeQuery(monthlyAppts);
            ResultSet monthlyResult = Query.getResult();
            
            while (monthlyResult.next()) {
                
                String consultant = monthlyResult.getString("appointment.contact");
                String startTime = monthlyResult.getString("appointment.start");
                String endTime = monthlyResult.getString("appointment.end");
                Customer customer = new Customer();
                customer.setCustomerName(monthlyResult.getString("customer.customerName"));
                Type type = new Type();
                type.setTheType(monthlyResult.getString ("appointment.url"));
                String description = monthlyResult.getString("appointment.description");
                monthlyData.add(new Appointment (customer, type, description, startTime, endTime, consultant ));
                
                
            }
            
        }
        catch( Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        AppointmentTable.getItems().setAll(monthlyData); 
        
    } // closes MonthAppointmentRadioButtonHandler

    
    @FXML
    private void DeleteButtonHandler(ActionEvent event) throws SQLException {
        // delete appointment
        // confirmation message
        
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Deletion Error");
            alert.setContentText ("Select an appointment you'd like to delete. ");
            alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("Deletion Confirmation");
            alert.setContentText("Are you sure you want to delete this appointment? This will delete the appointment from the database as well. ");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK){
                String start = AppointmentTable.getSelectionModel().getSelectedItem().getStartTime();
                String end = AppointmentTable.getSelectionModel().getSelectedItem().getEndTime();
                String select = "select * from appointment where start = \"" + start + "\" and \"" + end + "\"  ";
                Query.makeQuery(select);
                ResultSet deleteResult = Query.getResult();
                
                while (deleteResult.next()) {
                    String appointmentID = deleteResult.getString("appointment.appointmentId");
                    System.out.println("AppointmentID: " + appointmentID);
                    String deleteAppointment = "delete from appointment where appointmentId = \"" + appointmentID + "\" ";
                    Query.makeQuery(deleteAppointment);
                    AppointmentTable.getItems().setAll(fillTable());
                    
                } // closes while
            
            } // closes if ButtonType.OK
              
        } // closes else statement
               
    } // closes DeleteButtonHandler

   
    
    
    @FXML
    private void EditButtonHandler(ActionEvent event) throws IOException {
        // edit selected appointment
        
        setEditInfo(AppointmentTable.getSelectionModel().getSelectedItem());
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle ("Error");
            alert.setHeaderText("Editting Appointment");
            alert.setContentText("Please select an appointment to edit.");
            alert.showAndWait();
        }
        else {    
            setEditInfo(edittingInfo);
            Stage stage;
            Parent root;
            stage = (Stage) AppointmentTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointmentScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show();
        }
  
    } // Closes EditButtonHandler

    
    @FXML
    private void AddButtonHandler(ActionEvent event) throws IOException {
        // send to AddAppointmentScreen
        
            Stage stage;
            Parent root;
            stage = (Stage) AppointmentTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointmentScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show();
        
        
    } // closes AddButtonHandler
    

    @FXML
    private void CustomerButtonHandler(ActionEvent event) throws IOException {
        // send to CustomerScreen
        
            Stage stage;
            Parent root;
            stage = (Stage) AddButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show();  
        
    } // closes CustomerButtonHandler

    @FXML
    private void LogOutButtonHandler(ActionEvent event) throws IOException, Exception {
        // return to login screen
        // closes DB connection
        
        
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
            stage = (Stage) AppointmentTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show();  
        } // closes if(result.get() == ButtonType.OK)
        
        
    } // closes LougoutButtonHandler
    
    private List fillTable() {
                
        ObservableList <Appointment> tableData = FXCollections.observableArrayList();
        
        try {
            
            String fillTable = "select appointment.contact, appointment.start, appointment.end, appointment.url, appointment.description\n" +
                                ", customer.customerName from appointment, customer where customer.customerId = appointment.customerId and start >= current_date() and start < date_add(current_date(), interval 1 day)";
            Query.makeQuery(fillTable);
            ResultSet result = Query.getResult();
            
            while (result.next()) {
                
                String consultant = result.getString("appointment.contact");
                String startTime = result.getString("appointment.start");
                String endTime = result.getString("appointment.end");
                Customer customer = new Customer();
                customer.setCustomerName(result.getString("customer.customerName"));
                Type type = new Type();
                type.setTheType(result.getString ("appointment.url"));
                String description = result.getString("appointment.description");
                tableData.add(new Appointment (customer, type, description, startTime, endTime, consultant ));
            }
            
        } // closes try
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return tableData;
        
    } // closes fillTable()
    
    public void setEditInfo (Appointment editInfo) {
        AppointmentScreenController.edittingInfo = editInfo;
    }
    
    public static Appointment getEditInfo () {
        return edittingInfo;
    }
    
    public void checkAppointmentTimes () throws SQLException {
        String appointmentTimes = "select start, end from appointment";
        Query.makeQuery(appointmentTimes);
        ResultSet timeResult = Query.getResult();
        while (timeResult.next() ) {
            String starting = timeResult.getString("start");
            String ending = timeResult.getString ("end");
            System.out.println("Start: "+ starting + "End: "+ ending);
        }
    } // closes checkAppointmentTimes()
    
    
    public void upcomingAppointment () {
        
        try{
            String upcomingAppt = "select customer.customerName , appointment.url, appointment.start "
                    + " from customer, appointment where customer.customerId = appointment.customerId "
                    + " and appointment.contact = \"" + currentUser + "\" and start >= now() and start < date_add(now(), interval 15 minute) ";
            
        Query.makeQuery(upcomingAppt);
        ResultSet apptResult = Query.getResult();
        
        if (apptResult.next()) {
            
            String name = apptResult.getString("customer.customerName");
            String type = apptResult.getString("appointment.url");
            String time = apptResult.getString("appointment.start");
                
            Alert appt = new Alert(Alert.AlertType.INFORMATION);
            appt.setHeaderText("Upcoming Appointment");
            appt.setContentText("You have a(n) \"" + type + "\" appointment with \"" + name + "\" in less than 15 mins.  ");
            appt.showAndWait();
            
            } // closes if
        
        } // closes try
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
                
    } // closes upcomingAppointment()

    @FXML
    private void ReportButtonHandler(ActionEvent event) throws IOException {
         
        
        Stage stage;
        Parent root;
        stage = (Stage) AppointmentTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        root = loader.load();
        Scene scene = new Scene (root);
        stage.setScene (scene);
        stage.show(); 
        
        
    } // close ReportButtonHandler
    
    
} // Closes AppointmentScreenController
