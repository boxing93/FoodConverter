package foodconverter.bindingModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Iliya on 17.1.2017 г..
 */
public class Converter {

    @NotNull
    private String inputField;

    private String field;


    public Converter() {
    }

    public Converter(String inputField) {
        this.inputField = inputField;
        this.field = field;
    }

    public String getInputField() {

        return inputField;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
