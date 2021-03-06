package Models.Screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FormField {

    // Data Fields
    private String label;
    private int maxLength = 250;
    private TextField txtField = new TextField();
    private boolean onlyNumber = false;

    // Constructor
    public FormField(String label, int maxLength) {
        this.label = label;
        this.maxLength = maxLength;
    }
    public FormField(String label, int maxLength, boolean onlyNumber) {
        this(label, maxLength);
        this.onlyNumber = onlyNumber;
    }

    // Setter
    public void setFieldText(String str) {
        this.txtField.setText(str);
    }

    // Getter
    public String getEnteredText() {
        return this.txtField.getText();
    }

    // Method
    public Node getNode() {
        GridPane formContainer = new GridPane();
        formContainer.setHgap(10);
        Text label = new Text();
        label.setText(this.label);
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(this.txtField, 1, 0);
        this.txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(this.onlyNumber && newValue.length() > 0) {
                boolean digit = Character.isDigit(newValue.charAt(newValue.length() - 1));
                if(digit){
                    this.txtField.setText(newValue);
                } else if (!digit){
                    this.txtField.setText(oldValue);
                }
            }
        });
        formContainer.getChildren().addAll(label, txtField);
        return formContainer;
    }
    public void setPromptText(String text){
        this.txtField.setPromptText(text);
        this.txtField.setAlignment(Pos.CENTER);
    }
    public void setFont(Font font){
        this.txtField.setFont(font);
    }
}
