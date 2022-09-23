package com.bankaccountmanager.util;

import java.math.BigDecimal;

public class ValidationUtil {

    private ValidationUtil() {
       
    }

    public static boolean isNegative(BigDecimal amount) {
        return amount.signum() < 0  ? true : false ;
    }

}
