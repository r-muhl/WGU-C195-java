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
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import robertmuhlesteinsoftware2.Model.DBConnection;
import robertmuhlesteinsoftware2.Model.Query;


/**
 * FXML Controller class
 *
 * @author robmu
 */
public class LoginScreenController implements Initializable {

    @FXML
    private Label LogInLabel;
    @FXML
    private Button SignInButton;
    @FXML
    private Label UsernameLabel;
    @FXML
    private Label PasswordLabel;
    @FXML
    private Button ExitButton;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private TextField PasswordTextField;   
   
      public static String currentUser;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SignInButtonHandler(ActionEvent event) throws IOException {
        // add to file details of who logged on (userId, userName, date/time login, time logout??)
        
               
               
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();  
        
        
        currentUser = username;
        
      
        
        
        try {
            if (username.length() == 0 || password.length() == 0) {
                Alert empty = new Alert(Alert.AlertType.INFORMATION);
                empty.setTitle("Error");
                empty.setHeaderText("Incorrect username/ password");
                empty.setContentText("The username or password field is empty. Please try again.");
                empty.showAndWait();
            }
            else {
                DBConnection.makeConnection();
                String sqlStatement = "SELECT * FROM user" ;
                Query.makeQuery(sqlStatement);
                ResultSet result = Query.getResult();
                while (result.next() ) {
                    if (result.getString("userName").equals(username) && result.getString("password").equals (password)){
                        String loginReport = "src/Files/LoginReport";
                        FileWriter fwriter = new FileWriter(loginReport, true);
                        PrintWriter logInReport = new PrintWriter(fwriter);
                        int userID = result.getInt("userId");
                        LocalDateTime now = LocalDateTime.now();
                        Calendar cal = Calendar.getInstance();
                        logInReport.println("");
                        logInReport.print ("  " + userID + ",      ");
                        logInReport.print(username + ",        ");
                        logInReport.print (cal.getTime() +",       ");
                        logInReport.close();
                        
                        
                        Stage stage;
                        Parent root;
                        stage = (Stage) LogInLabel.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
                        root = loader.load();
                        Scene scene = new Scene (root);
                        stage.setScene (scene);
                        stage.show();
                        return;
                    } // closes if statement         
                } // closes while loop
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect username/ password");
                alert.setContentText("The username or password is incorrect. Please try again.");
                alert.showAndWait();    
            } // closes else
            DBConnection.closeConnection();
        } // closes try
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect username/ password");
            alert.setContentText("The username or password you have entered in incorrect. Please try again.");
            UsernameTextField.setText("");
            PasswordTextField.setText("");
        } // closes catch        
    } // closes SignInButtonHandler

    @FXML
    private void ExitButtonHandler(ActionEvent event) throws Exception {
                
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Exit App");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
        
    } // closes ExitButtonHandler
  
    @FXML
    private void ChineseSignInButtonHandler(ActionEvent event) throws IOException {
        // add to file details of who logged on (userId, userName, date/time login, time logout??)
               
        String username = UsernameTextField.getText();
        String password = PasswordTextField.getText();  
        
        try {
            if (username.length() == 0 || password.length() == 0) {
                Alert empty = new Alert(Alert.AlertType.INFORMATION);
                empty.setTitle("故障");
                empty.setHeaderText("用户名/密码不正确");
                empty.setContentText("用户名或密码字段为空。请再试一次。");
                empty.showAndWait();
            }
            else {
                DBConnection.makeConnection();
                String sqlStatement = "SELECT * FROM user" ;
                Query.makeQuery(sqlStatement);
                ResultSet result = Query.getResult();
                while (result.next() ) {
                    if (result.getString("userName").equals(username) && result.getString("password").equals (password)){
                        String loginReport = "src/Files/LoginReport";
                        FileWriter fwriter = new FileWriter(loginReport, true);
                        PrintWriter logInReport = new PrintWriter(fwriter);
                        int userID = result.getInt("userId");
                        LocalDateTime now = LocalDateTime.now();
                        Calendar cal = Calendar.getInstance();
                        logInReport.println("");
                        logInReport.print (userID + ", ");
                        logInReport.print(username + ", ");
                        logInReport.print (cal.getTime() +", ");
                        logInReport.close();
                        
                        
                        Stage stage;
                        Parent root;
                        stage = (Stage) LogInLabel.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
                        root = loader.load();
                        Scene scene = new Scene (root);
                        stage.setScene (scene);
                        stage.show();
                        return;
                    } // closes if statement         
                } // closes while loop
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("故障");
                alert.setHeaderText("用户名/密码不正确");
                alert.setContentText("用户名或密码不正确。请再试一次");
                alert.showAndWait();    
            } // closes else
            DBConnection.closeConnection();
        } // closes try
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("故障");
            alert.setHeaderText("用户名/密码不正确");
            alert.setContentText("用户名或密码不正确。请再试一次");
            UsernameTextField.setText("");
            PasswordTextField.setText("");
        } // closes catch        
    } // closes SignInButtonHandler
    
    
} // closes LoginScreenController
