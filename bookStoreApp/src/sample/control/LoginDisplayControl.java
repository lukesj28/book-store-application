package sample.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Main;
import sample.base.Cust;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.control.Alert;
import sample.base.State;

public class LoginDisplayControl implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public static Stage stage;
    public static State state;
    public static Stage custStage;
    public static Cust cust;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(val -> {
            if (username.getText().equals("admin") && password.getText().equals("admin")) {
                setHome();
            } else if (username.getText().equals("") && password.getText().equals("")) {
                Alert tempAlert = new Alert(Alert.AlertType.ERROR);
                tempAlert.setTitle("User Error");
                tempAlert.setHeaderText("One of the input fields are Blank. Please try again.");
                tempAlert.show();
            } else {
                String tempUsername = username.getText();
                String tempPassword = password.getText();
                try {
                    Scanner scanner = new Scanner(new FileReader("customers.txt"));
                    while (scanner.hasNext()) {
                        String[] line = scanner.nextLine().split(",");
                        if (tempUsername.equals(line[0]) && tempPassword.equals(line[1])) {
                            cust = new Cust();
                            cust.setUsername(tempUsername);
                            cust.setPassword(tempPassword);
                            cust.setPoints(Integer.parseInt(line[2]));
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(LoginDisplayControl.class.getResource("../viewStage/custDisplay.fxml"));
                                BorderPane pane = loader.load();
                                custStage = new Stage();
                                custStage.setTitle("Customer");
                                custStage.setScene(new Scene(pane));
                                custStage.show();
                                Main.mainStage.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setHome() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginDisplayControl.class.getResource("../viewStage/homeDisplay.fxml"));
            BorderPane pane = loader.load();
            stage = new Stage();
            stage.setTitle("Book Store");
            stage.setScene(new Scene(pane));
            stage.show();
            Main.mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
