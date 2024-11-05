package com.example.lab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Label errormessage;

    public void login(ActionEvent actionEvent) {
        String uname = username.getText();
        String upass = password.getText();

        if (uname.isEmpty() || upass.isEmpty()) {
            errormessage.setText("Please enter username and password");
        } else {
            String jdbcUrl = "jdbc:mysql://localhost:3306/db_ems";
            String dbUser = "root";
            String dbPassword = "";
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String query = "SELECT * FROM tbl_admin WHERE email = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, uname);
                preparedStatement.setString(2, upass);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    openDashboard();
                } else {
                    errormessage.setText("Invalid username or password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errormessage.setText("Database connection error");
            }
        }
    }

    private void openDashboard() {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Stage secondStage = new Stage();
            secondStage.setTitle("Dashboard");
            secondStage.setScene(new Scene(secondScene));

            Stage firstSceneStage = (Stage) username.getScene().getWindow();
            firstSceneStage.close();

            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errormessage.setText("Error loading dashboard");
        }
    }
}
