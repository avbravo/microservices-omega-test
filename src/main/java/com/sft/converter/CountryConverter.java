/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sft.converter;

 
import com.sft.model.Country;
import com.sft.repository.CountryRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@RequestScoped
@Named
public class CountryConverter implements Converter {
    @Inject
    CountryRepository countryRepository;
  @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        try {
           if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Country) {
          return String.valueOf(((Country) modelValue).getIdcountry());
        } else {
            System.out.println("----------->getAsString");
          throw new ConverterException(new FacesMessage(modelValue + " is not a valid from Converter"));
        }
      } catch (Exception e) {
            System.out.println("--------getAsString () "+e.getLocalizedMessage());
            new FacesMessage("Error en converter Country "+e.getLocalizedMessage());
      }

 return "";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        Country a = new Country();
        if (countryRepository == null) {
            System.out.println("Service is nich");
        }

        if (submittedValue == null || submittedValue.isEmpty()) {
            System.out.println("submitted = nil");
            return null;
        }

        try {
            String idCountry =submittedValue;
//            Integer id=Integer.parseInt(submittedValue);
//            Long idCountry= id.longValue();
            Optional<Country> optional = countryRepository.findByPk(idCountry);
            if (optional.isPresent()) {
                a = optional.get();
            }
            return a;
        } catch (Exception e) {
            System.out.println("====================");
            System.out.println("---> getAsObject" +e.getLocalizedMessage());
            System.out.println("====================");
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid selecction from Converter"), e);
        }
    }

}

