package com.example.homeworkmvc.controller;

import com.example.homeworkmvc.model.ConversionRequest;
import com.example.homeworkmvc.model.NewCurrencyRequest;
import com.example.homeworkmvc.service.ConversionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @GetMapping("/convert")
    public String showForm(Model model) {
        model.addAttribute("conversionRequest", new ConversionRequest());
        model.addAttribute("newCurrencyRequest", new NewCurrencyRequest());
        return "conversion";
    }

    @PostMapping("/convert")
    public String convert(@ModelAttribute("conversionRequest") ConversionRequest request, Model model) {
        double result = 0;
        try {
            result = conversionService.convert(
                request.getAmount(),
                request.getSourceCurrency(),
                request.getTargetCurrency()
            );
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "conversion";
        }
        model.addAttribute("conversionResult", result);
        return "conversion";
    }

    @PostMapping("/addCurrency")
    public String addCurrency(@ModelAttribute("newCurrencyRequest") NewCurrencyRequest request, Model model) {
        try {
            conversionService.addCurrency(request.getSourceCurrency(), request.getAmount());
        } catch (Exception e) {
            model.addAttribute("message", "Error adding currency: " + e.getMessage());
            return "conversion";
        }
        model.addAttribute("message", "Currency added successfully!");
        return "conversion";
    }

}
