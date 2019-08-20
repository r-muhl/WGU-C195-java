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
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import robertmuhlesteinsoftware2.Model.Appointment;
import robertmuhlesteinsoftware2.Model.Query;
import robertmuhlesteinsoftware2.Model.Type;
import robertmuhlesteinsoftware2.Model.User;
import static robertmuhlesteinsoftware2.RobertMuhlesteinSoftware2.time;
import static robertmuhlesteinsoftware2.View.LoginScreenController.currentUser;

/**
 * FXML Controller class
 *
 * @author robmu
 */
public class EditAppointmentScreenController implements Initializable {

    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Label NameLabel;
    @FXML
    private Label IDLabel;
    @FXML
    private Label TitleLabel;
    @FXML
    private Label TypeLabel;
    @FXML
    private Label ConsultantLabel;
    @FXML
    private Label StartTimeLabel;
    @FXML
    private Label EndTimeLabel;
    @FXML
    private Label DescriptionLabel;
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
    private static Appointment editAppointment;
    @FXML
    private TextField TitleTextField;
    @FXML
    private Label NameLabel2;
    @FXML
    private ComboBox<User> ConsultantComboBox;
    
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
    
    
    public EditAppointmentScreenController () {
        editAppointment = AppointmentScreenController.getEditInfo();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // auto populate labels with appointment info
        //int ID = editAppointment.getAppointmentID();
        String description = editAppointment.getDescription();
        String customerName = editAppointment.getCustomer().getCustomerName();
            try {
                String sqlEdit = "select customer.customerName, customer.customerId, appointment.title, appointment.url, appointment.contact, "
                        + "appointment.start, appointment.end, appointment.description"
                        + " from appointment, customer"
                        + " where customer.customerId = appointment.customerId and customer.customerName = \"" + customerName + "\" and appointment.description = \"" + description + "\"";       
                Query.makeQuery(sqlEdit);
            
                ResultSet editResult = Query.getResult();
                while (editResult.next()) {
                    String Title = editResult.getString("appointment.title");
                    String Type = editResult.getString("appointment.url");
                    String Consultant = editResult.getString("appointment.contact");
                    String StartTime = editResult.getString("appointment.start");
                    String EndTime = editResult.getString("appointment.end");
                    String Date = "";
                    String Description = editResult.getString("appointment.description");
                    int ID = editResult.getInt("customer.customerId");
                
                    NameLabel.setText(customerName);
                    NameLabel2.setText(customerName);
                    IDLabel.setText(Integer.toString(ID));
                    TitleLabel.setText(Title);
                    TypeLabel.setText(Type);
                    ConsultantLabel.setText(Consultant);
                    StartTimeLabel.setText(StartTime);
                    EndTimeLabel.setText(EndTime);
                    DescriptionLabel.setText(Description);
                
            } // closes while
            
            } // closes try
            catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            } // closes catch
        
        fillUserComboBox();
        fillTypeComboBox();
        fillStartTime();
        fillEndTime();
        
    }    

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws SQLException {
        // save new info to DB
        
        // --------------------------- DELETE ME -------------------------------------------------
        System.out.println("you clicked the save button");
        // --------------------------- DELETE ME -------------------------------------------------
        
       
        String Title = TitleTextField.getText();
        String Type = TypeComboBox.getSelectionModel().getSelectedItem().getTheType();
        String Consultant = ConsultantComboBox.getSelectionModel().getSelectedItem().getUsername();
        String StartTime = StartComboBox.getSelectionModel().getSelectedItem();
        String EndTime = EndComboBox.getSelectionModel().getSelectedItem();
        String Date = DatePicker.getValue().toString();
        String UpdateStartDateTime = Date +" " + StartTime;
        String UpdateEndDateTime = Date + " " + EndTime;
        String Description = DescriptionTextArea.getText();
        String StartDateTime = StartTimeLabel.getText();
        String EndDateTime = EndTimeLabel.getText();
                  
            
        try{    
        
        String Name = NameLabel.getText();
        String Start = StartTimeLabel.getText();
        String End = EndTimeLabel.getText();
        
        int custID = Integer.parseInt(IDLabel.getText());
                    
        String getApptID = "select appointmentID from appointment where customerId = \"" + custID + "\" and start = \"" + Start + "\" and end = \"" + End + "\" ";
        
        Query.makeQuery(getApptID);
        ResultSet ApptIDResult = Query.getResult();
        while(ApptIDResult.next()) {
            int ApptID = ApptIDResult.getInt("appointment.appointmentId");
             
            String contact = ConsultantComboBox.getSelectionModel().getSelectedItem().getUsername(); 
                      
            String checkAppt = "select * from appointment "
                + "where ( \"" + UpdateStartDateTime + "\" between start and end or \"" + UpdateEndDateTime + "\" "
                + "between start and end or \""+ UpdateStartDateTime + "\" < start and \"" + UpdateEndDateTime + "\" > end   ) "
                + "and (contact =  \"" + contact + "\" and appointmentId != \"" + ApptID + "\"   )  ";
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
            
                    String appID = "select appointmentId from appointment where start = \"" + StartDateTime + "\" and end = \"" + EndDateTime + "\"";
                    Query.makeQuery(appID);
                    ResultSet IDResult = Query.getResult();
                    while (IDResult.next()) {
                        int ID = IDResult.getInt("appointmentId");
                
                        String updateAppointment = "update appointment set  title = \""+ Title +"\" , description = \""+ Description +"\" , "
                            + "contact = \""+ Consultant +"\" , url = \""+ Type +"\" , start = \""+ UpdateStartDateTime +"\" , end = \""+ UpdateEndDateTime +"\", lastUpdate = now() , lastUpdateBy = \""+ currentUser +"\" "
                            + "where appointmentId = \""+ ID +"\" ";
                
                        Query.makeQuery(updateAppointment);
                
                    } // closes while
                            
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initModality(Modality.NONE);
                    alert.setHeaderText("Save Successful");
                    alert.setContentText("The appointment was successfully saved! Clicking OK will reset this page and clear all fields.");
                    Optional<ButtonType> result = alert.showAndWait();
            
                    if (result.get() == ButtonType.OK){
                        TitleTextField.clear();
                        TypeComboBox.valueProperty().set(null);
                        ConsultantComboBox.valueProperty().set(null);
                        StartComboBox.valueProperty().set(null);
                        EndComboBox.valueProperty().set(null);
                        DatePicker.setValue(null);
                        DescriptionTextArea.clear();
             
                    } // closese result.get() == ButtonType
                } // closes else
            } // closes checkResult.next() else
            
        } // while (ApptIDResult.next())
        
        } // closes try
        catch (Exception e) {
            checkEmpty();
        }
        
    }

    @FXML
    private void CancelButtonHandle(ActionEvent event) throws IOException {
        //error message
        // return to AppointmentScreen
        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel Confirmation");
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
        
    } // closes CancelButtonHandler

    
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

        if (time.contains("London")) {
            
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
   
    } // close fillStartTime
    
    private void fillEndTime() {
        
        //EndComboBox.setItems(setEndTimes);
        if (time.contains("London")){
            
            EndComboBox.getItems().addAll( "16:15" , "16:30" , "16:45" ,
                                           "17:00" , "17:15" , "17:30" , "17:45" ,
                                           "18:00" , "18:15" , "18:30" , "18:45" ,
                                           "19:00" , "19:15" , "19:30" , "19:45" ,
                                           "20:00" , "20:15" , "20:30" , "20:45" ,
                                           "21:00" , "21:15" , "21:30" , "21:45" ,
                                           "22:00" , "22:15" , "22:30" , "22:45" ,
                                           "23:00" , "23:15" , "23:30" , "23:45" ,
                                           "24:00");
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
        
    } // closes fillEndTime
    
    private String checkEmpty() {
        String errorMessage = "";
        boolean error = false;

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
        }
        return errorMessage;
        
    } // closes checkEmpty
    
} // closes EditAppointmentScreenController
