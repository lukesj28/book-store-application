package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Application.launch;

public class Main extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage mainStage) throws Exception {
        Main.mainStage = mainStage;
        Login();
    }

    public static void Login() throws IOException {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("viewStage/loginDisplay.fxml"));
            mainStage.setTitle("Bookstore App");
            mainStage.setResizable(false);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
