/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package robertmuhlesteinsoftware2;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import robertmuhlesteinsoftware2.View.LoginScreenController;

/**
 *
 * @author robmu
 */
public class RobertMuhlesteinSoftware2 extends Application {
    public Locale myLocale;
    public static TimeZone timeZone;
    private Stage stage;
    public static String time;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        myLocale = Locale.getDefault();
           
        //----------------------- This will change Locale to China, Chinese ---------------------------------
        //myLocale = new Locale ("zh", "CN");
        
        
        
        time = TimeZone.getDefault().getID().toString();
        
        // Uncomment to change TimeZone to London, England
        //TimeZone.setDefault(TimeZone.getTimeZone("GB"));

        // Must be commented out when TimeZone is changed
        timeZone = TimeZone.getDefault();
        
        
        this.stage = stage;
        loginScreen();

    } // closes public void start
     

    
    public void loginScreen () {
        if (myLocale.getCountry() == "CN") {
            try {

                Parent root = FXMLLoader.load (LoginScreenController.class.getResource("ZHLoginScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                
            } // closes try
            catch (Exception e) {
                System.out.println("Error with ZHLoginScreen: " + e.getMessage());
            } // closes catch
        } // closes if my.locale.getCountry() == "CN"
        else {
            try {
                Parent root = FXMLLoader.load (LoginScreenController.class.getResource("LoginScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } // closes try
            catch (Exception e) {
                System.out.println ("Error with LoginScreen: " + e.getMessage());
            } // closes catch
            
        } // closes else statement
       
    } // closes public void loginScreen
    
    
    
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
} // closes public class RobertMuhlesteinSoftware2
