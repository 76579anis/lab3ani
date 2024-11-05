package com.example.lab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    public Button adminbtn;
    public Button employeebtn;
    public Button exitbtn;

    public void adminClick(ActionEvent actionEvent) {
        try {
        Parent secondScene = FXMLLoader.load(getClass().getResource("admin.fxml"));

        Stage secondStage = new Stage();
        secondStage.setTitle("Admin");
        secondStage.setScene(new Scene(secondScene));
        Stage firstSceneStage = (Stage) adminbtn.getScene().getWindow();
        firstSceneStage.close();


        secondStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void employeeClick(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("employee.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("employee");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage) employeebtn.getScene().getWindow();
            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitClick(ActionEvent actionEvent) {
        System.exit( 0);
    }

    public void logoutClick(ActionEvent actionEvent) { try {
        Parent secondScene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

        Stage secondStage = new Stage();
        secondStage.setTitle("login");
        secondStage.setScene(new Scene(secondScene));
        Stage firstSceneStage = (Stage) adminbtn.getScene().getWindow();
        firstSceneStage.close();


        secondStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }


    }
}

