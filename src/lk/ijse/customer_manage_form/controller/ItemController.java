package lk.ijse.customer_manage_form.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ItemController {

    private static final String URL = "jdbc:mysql://localhost:3306/customeranditem";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    public TextField txtId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQuantity;


    public void onSave(ActionEvent actionEvent) throws SQLException {
        String itamId = txtId.getText();
        String itemName = txtName.getText();
        double itemPrice = Double.parseDouble(txtPrice.getText());
        String itemQuantity = txtQuantity.getText();

        try (Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO item(itemId,itemName,price,quantity)"+
                    "VALUES(?,?,?,?)";

            PreparedStatement prSta = connection.prepareStatement(sql);
            prSta.setString(1,itamId);
            prSta.setString(2,itemName);
            prSta.setString(3, String.valueOf(itemPrice));
            prSta.setString(4,itemQuantity);

            int affectedRows = prSta.executeUpdate();
             if(affectedRows>0){
                 new Alert(Alert.AlertType.CONFIRMATION,"Customer Added!!").show();
             }else {
                 new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Added!!").show();

             }

             txtId.setText("");
             txtName.setText("");
             txtPrice.setText("");
             txtQuantity.setText("");

        }
    }

    public void onDeleat(ActionEvent actionEvent) throws SQLException {
        String deleteid = txtId.getText();
        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "DELETE FROM item WHERE itemid = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,deleteid);

            int affctedRows = pstm.executeUpdate();
            System.out.println(affctedRows > 0 ? "deleate!!" : "not deleate!!");

            if(affctedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION,"deleate!!").show();
            }
        }
        txtId.setText("");

    }

    public void onUpdate(ActionEvent actionEvent) throws SQLException {

        String itamId2 = txtId.getText();
        String itemName2 = txtName.getText();
        double itemPrice2 = Double.parseDouble(txtPrice.getText());
        String itemQuantity2 = txtQuantity.getText();

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE item SET itemId=?,itemName=?,price=?,quantity=? WHERE itemId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,itamId2);
            pstm.setString(2,itemName2);
            pstm.setDouble(3,itemPrice2);
            pstm.setString(4,itemQuantity2);
            pstm.setString(5,itamId2);


            int affectedRows = pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added!").show();

            }
        }

        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    public void idOnAction(ActionEvent actionEvent) throws SQLException {
        String onAction = txtId.getText();
        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql ="SELECT * FROM item WHERE itemId= ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,onAction);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String tepmItId = resultSet.getString(1);
                String tempItName = resultSet.getString(2);
                String tempItPrice = resultSet.getString(3);
                double tempItQuantity = resultSet.getDouble(4);

                txtId.setText(tepmItId);
                txtName.setText(tempItName);
                txtPrice.setText(tempItPrice);
                txtQuantity.setText(String.valueOf(tempItQuantity));
            }
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void onCustemerManageForm(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/customer_manage_form/view/customerForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Items Management Form");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
