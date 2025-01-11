package com.example.calculator.controller;

import com.example.calculator.util.CalculatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller class for the Calculator application.
 */
public class CalculatorController {

    @FXML
    private ComboBox<String> comboBox = new ComboBox<>();

    private final ObservableList<String> previousCalculations = FXCollections.observableArrayList();

    private CalculatorUtil calculatorUtil;

    @FXML
    private TextField textField = new TextField();

    public void initialize(){
        textField.setEditable(false);
        comboBox.setItems(previousCalculations);
        comboBox.setOnAction(e -> loadSelectedCalculation());
        calculatorUtil = new CalculatorUtil();
    }

    @FXML
    private void plusButtonAction(){
        textField.appendText("+");
    }

    @FXML
    private void subtractButtonAction(){
        textField.appendText("-");
    }

    @FXML
    private void multiplyButtonAction(){
        textField.appendText("*");
    }

    @FXML
    private void divideButtonAction(){
        textField.appendText("/");
    }

    @FXML
    private void zeroButtonAction(){
        textField.appendText("0");
    }

    @FXML
    private void oneButtonAction(){
        textField.appendText("1");
    }

    @FXML
    private void twoButtonAction(){
        textField.appendText("2");
    }

    @FXML
    private void threeButtonAction(){
        textField.appendText("3");
    }

    @FXML
    private void fourButtonAction(){
        textField.appendText("4");
    }

    @FXML
    private void fiveButtonAction(){
        textField.appendText("5");
    }

    @FXML
    private void sixButtonAction(){
        textField.appendText("6");
    }

    @FXML
    private void sevenButtonAction(){
        textField.appendText("7");
    }

    @FXML
    private void eightButtonAction(){
        textField.appendText("8");
    }

    @FXML
    private void nineButtonAction(){
        textField.appendText("9");
    }

    @FXML
    private void ACButtonAction(){
        textField.setText("");
    }

    @FXML
    private void openBracketButtonAction(){
        textField.appendText("(");
    }

    @FXML
    private void closeBracketButtonAction(){
        textField.appendText(")");
    }

    @FXML
    private void piButtonAction(){
        textField.appendText(String.valueOf(Math.PI));
    }

    @FXML
    private void dotButtonAction(){
        textField.appendText(".");
    }

    @FXML
    private void sqrtButtonAction(){
        textField.appendText("sqrt(");
    }

    @FXML
    private void exponentialButtonAction(){
        textField.appendText("^");
    }

    @FXML
    private void factorialButtonAction(){
        textField.appendText("!");
    }

    @FXML
    private void sinButtonAction(){
        textField.appendText("sin");
    }

    @FXML
    private void cosButtonAction(){
        textField.appendText("cos");
    }

    @FXML
    private void tanButtonAction(){
        textField.appendText("tan");
    }

    @FXML
    private void CEButtonAction(){
        if (textField.getLength() >= 1){
            textField.deleteText(textField.getLength()-1, textField.getLength());
        }
    }

    /**
     * Loads the selected calculation from the ComboBox into the text field.
     */
    private void loadSelectedCalculation() {
        String selectedCalculation = comboBox.getSelectionModel().getSelectedItem();
        if (selectedCalculation != null) {
            String expression = selectedCalculation.split(" = ")[0];
            textField.setText(expression);
        }
    }

    /**
     * Evaluates the current expression and displays the result in an alert dialog.
     * Adds the calculation to the history upon success.
     */
    @FXML
    private void resultButtonAction(){

        String expression = textField.getText();

        if (!calculatorUtil.validateExpression(expression)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Expression");
            alert.setHeaderText(null);
            alert.setContentText("The mathematical expression is invalid. Please check your input");
            alert.showAndWait();
            return;
        }

        double result = calculatorUtil.evaluateMathExpression(expression);

        String resultString = expression + " = " + result;
        previousCalculations.add(resultString);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText(null);
//        alert.setContentText("Expression: " + expression + "\nResult: " + result);
        alert.setContentText("Result: " + result);
        alert.showAndWait();
    }
}