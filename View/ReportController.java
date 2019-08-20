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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import robertmuhlesteinsoftware2.Model.Appointment;
import robertmuhlesteinsoftware2.Model.AppointmentTypeReport;
import robertmuhlesteinsoftware2.Model.ApptPerDayReport;
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
public class ReportController implements Initializable {

    @FXML
    private TableView<AppointmentTypeReport> ApptTypeTable;
    @FXML
    private TableColumn<AppointmentTypeReport, String> MonthColumn;
    @FXML
    private TableColumn<AppointmentTypeReport, String> TypeColumn;
    @FXML
    private TableColumn<AppointmentTypeReport, String> NumApptColumn;
    @FXML
    private TableView<Appointment> ConsultantScheduleTable;
    @FXML
    private TableColumn<Appointment, String> StartColumn;
    @FXML
    private TableColumn<Appointment, String> EndColumn;
    @FXML
    private TableColumn<Appointment, String> CustomerColumn;
    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> CTypeColumn;
    @FXML
    private Tab ScheduleTabHandler;
    @FXML
    private Tab TypeTabHandler;
    @FXML
    private Tab DetailsTabHandler;
    @FXML
    private TableView<ApptPerDayReport> ApptPerDayTable;
    @FXML
    private TableColumn<ApptPerDayReport, String> DateColumn;
    @FXML
    private TableColumn<ApptPerDayReport, String> ConsultantColumn;
    @FXML
    private TableColumn<ApptPerDayReport, String> ApptsNumColumn;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button ReturnButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getType()));
        NumApptColumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getNum()));
        MonthColumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getMonth()));
        
        
        StartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
        EndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
        CustomerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        DescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        CTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty (cellData.getValue().getType().getTheType()));
        
        
        DateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        ConsultantColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getConsultant()));
        ApptsNumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNum()));
        
        
        
        ApptTypeTable.getItems().setAll(fillApptTypeTable());
        ConsultantScheduleTable.getItems().setAll(fillConsultantScheduleTable());
        ApptPerDayTable.getItems().setAll(fillApptPerDayTable());
        
     
    } // close Override   
    
    
    private List fillApptTypeTable () {
        
        ObservableList <AppointmentTypeReport> apptData = FXCollections.observableArrayList();
                
        try {
            String getApptData = "SELECT MONTHNAME(start) AS Month, url , COUNT(*) as num "
                    + " FROM appointment "
                    + " GROUP BY MONTHNAME(start), url "
                    + " ORDER BY month desc ";
            Query.makeQuery(getApptData);
            ResultSet apptResult = Query.getResult();
            
            while ( apptResult.next() ) {
                
                String month = apptResult.getString("Month");
                String type = apptResult.getString("url");
                String num = apptResult.getString("num");
                
                apptData.add(new AppointmentTypeReport (month, type, num));
            
            } // closes while
         
        }
        catch( Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
           
    
    return apptData;
        
        
    } // closes fillApptTypeTable
   
    
    private List fillConsultantScheduleTable() {
        
        ObservableList <Appointment> tableData = FXCollections.observableArrayList();
        
        try {
            
            String fillTable = "select appointment.contact, appointment.start, appointment.end, appointment.url, appointment.description\n" +
                                ", customer.customerName from appointment, customer where customer.customerId = appointment.customerId and appointment.start > date(now())"
                                + " and appointment.contact = \"" + currentUser+ "\" ";
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

        
    } // closes fillConsultantScheduleTable
    
    private List fillApptPerDayTable() {
        
        ObservableList <ApptPerDayReport> perDayData = FXCollections.observableArrayList();
        
        try {
            
            String getData = "select contact , date_format(start,'%m-%d-%Y') as `DATE`, count(*) as `SUM`"
                    + " from appointment group by contact, date_format(start,'%m-%d-%Y') order by 'DATE'";
            Query.makeQuery(getData);
            ResultSet result = Query.getResult();
            
            while (result.next()) {
                String consultant = result.getString ("appointment.contact");
                String date = result.getString ("DATE");
                String num = result.getString ("SUM");
                
                perDayData.add(new ApptPerDayReport (date, consultant, num) );
              
            }
          
        }
        
        catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
        
        return perDayData;
        
    } // closes fillApptPerDayTable

    @FXML
    private void LogoutButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        
        
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
            stage = (Stage) ApptTypeTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene (root);
            stage.setScene (scene);
            stage.show();  
        } // closes if(result.get() == ButtonType.OK)
        
        
    }

    @FXML
    private void ReturnButtonHandler(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) ApptTypeTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene (root);
        stage.setScene (scene);
        stage.show(); 
                
    }
    
    
    
} //close ReportController
