package sample.control;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class HomeDisplayControl implements Initializable {

    @FXML
    private Button bookButton;
    @FXML
    private Button custButton;
    @FXML
    private Button logoutButton;
    public static Stage bookStage;

    @Override
    public void initialize(URL location, ResourceBundle src) {
        try {
            bookButton.setOnAction(val -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../viewStage/bookStart.fxml"));
                    BorderPane pane = loader.load();
                    setStage(pane, "Books");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            logoutButton.setOnAction(val -> {
                try {
                    LoginDisplayControl.stage.hide();
                    Main.Login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            custButton.setOnAction(val -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../viewStage/custMain.fxml"));
                    BorderPane pane = loader.load();
                    setStage(pane, "Customers");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setStage(BorderPane tempPane, String title) {
        try {
            bookStage = new Stage();
            bookStage.setTitle(title);
            bookStage.setScene(new Scene(tempPane));
            bookStage.show();
            LoginDisplayControl.stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
