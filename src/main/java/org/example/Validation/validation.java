package org.example.Validation;

import java.util.regex.Pattern;

public class validation {


    public static boolean validAccountNumber( String AcNo){

        return AcNo.matches("^\\d{12}$");
    }
}
