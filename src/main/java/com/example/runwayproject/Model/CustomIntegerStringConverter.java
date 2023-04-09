package com.example.runwayproject.Model;

import javafx.scene.control.Alert;
import javafx.util.converter.IntegerStringConverter;
//class to make sure the value inputted into the table cell while editing is an int if not it will handle and return -1
public class CustomIntegerStringConverter extends IntegerStringConverter {
    private final IntegerStringConverter converter = new IntegerStringConverter();

    @Override
    public String toString(Integer object) {
        try {
            return converter.toString(object);
        } catch (NumberFormatException e) {
            showAlert(e);
        }
        return null;
    }

    private void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error alert");
        alert.setHeaderText("Enter an Integer Value");

    }

    @Override
    public Integer fromString(String string) {
        try {
            return converter.fromString(string);
        } catch (NumberFormatException e) {
            showAlert(e);
        }
        return -1;
    }
}