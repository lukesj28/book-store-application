package sample.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Main;
import sample.base.Book;
import sample.base.Cust;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CustCostControl implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label currPointsLabel;

    @Override
    public void initialize(URL location, ResourceBundle src) {
        try {
            int total = 0;
            for (Book b : CustDisplayControl.purchasedBooks) {
                total += Integer.parseInt(b.getPrice());
            }
            totalCostLabel.setText("Total Cost: $" + total + " CAD");
            String tempVal = "Gold";
            if (LoginDisplayControl.cust.getPoints() <= 1000) {
                tempVal = "Silver";
            }
            currPointsLabel.setText(LoginDisplayControl.cust.getUsername() + ". You have " + LoginDisplayControl.cust.getPoints() + " points.\nYour status is " + tempVal + ".");

            logoutButton.setOnAction(val -> {
                try {
                    CustDisplayControl.costStage.close();
                    LoginDisplayControl.custStage.close();
                    Main.Login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            backButton.setOnAction(val -> {
                try {
                    CustDisplayControl.costStage.close();
                    LoginDisplayControl.custStage.close();
                    CustDisplayControl.setCustMainDisplay();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Cust customer = LoginDisplayControl.cust;
            customer.setPoints(customer.getPoints() + total * 10);
            ArrayList<Cust> tempData = new ArrayList<>();
            Scanner scanner = new Scanner(new FileReader("customers.txt"));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                if (!(customer.getUsername().equals(line[0]) && customer.getPassword().equals(line[1]))) {
                    Cust c = new Cust();
                    c.setUsername(line[0]);
                    c.setPassword(line[1]);
                    c.setPoints(Integer.parseInt(line[2]));
                    tempData.add(c);
                }
            }
            tempData.add(customer);
            FileWriter writer = new FileWriter("customers.txt");
            for (Cust c : tempData) {
                writer.write(c.getUsername() + "," + c.getPassword() + "," + c.getPoints() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
