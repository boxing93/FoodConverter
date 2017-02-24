package foodconverter.controller;

import foodconverter.bindingModel.Converter;
import foodconverter.service.ConverterService;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditor;
import java.util.*;

/**
 * Created by Iliya on 17.1.2017 г..
 */

@Controller
public class ConverterController {


    @GetMapping("/converter")
    public String convertUnits(ModelMap model, @RequestParam("inputField") String inputField, HttpServletRequest request) {
        String result = request.getParameter("inputField");
        String unit1 = request.getParameter("units");
        String unit2 = request.getParameter("units2");
        if(inputField.isEmpty()){
            model.addAttribute("message2", "Please,fill the empty field!");
            model.addAttribute("view", "home/index");
            return "base-layout";
        }
        if (unit1.equals(unit2) || unit1.equals("GRAM") && unit2.equals("GRAM") || unit1.equals("GRAM") && unit2.equals("ML")
                || unit1.equals("KG") && unit2.equals("LITER") || unit1.equals("ML") && unit2.equals("GRAM")
                || unit1.equals("LITER") && unit2.equals("KG")) {
            double convert = Double.parseDouble(result);
            model.addAttribute("field", convert);
        } else if (unit1.equals("GRAM") && unit2.equals("KG") || unit1.equals("GRAM") && unit2.equals("LITER")
                || unit1.equals("ML") && unit2.equals("KG") || unit1.equals("ML") && unit2.equals("LITER")) {
            double convert = Double.parseDouble(result);
            double convertResult = convert / 1000;
            model.addAttribute("field", convertResult);
        } else if (unit1.equals("KG") && unit2.equals("GRAM") || unit1.equals("KG") && unit2.equals("ML")
                || unit1.equals("LITER") && unit2.equals("GRAMS") || unit1.equals("LITER") && unit2.equals("ML")
                || unit1.equals("LITER") && unit2.equals("GRAM")) {
            double convert = Double.parseDouble(result);
            double convertResult = convert * 1000;
            model.addAttribute("field", convertResult);
        } else if (unit1.equals("NONE") && unit2.equals("NONE2")) {
            model.addAttribute("message", "Must fill the fields!");
        }
        model.addAttribute("result",result);
        model.addAttribute("unit1",unit1);
        model.addAttribute("unit2",unit2);
        model.addAttribute("view", "home/resultPage");
        return "base-layout";
    }

}
