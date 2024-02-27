package com.transfer.exception;

/**
 * The exception to manage unabalible balance cases
 */
public class InsufficientBalanceManagerException extends BankAccountManagerException {

	public InsufficientBalanceManagerException(String message) {
        super(message);
    }
    private InsufficientBalanceManagerException(String format, Object... parameters) {
        super(format, parameters);
    }

    public static InsufficientBalanceManagerException to(String format, Object... parameters) {
        return new InsufficientBalanceManagerException(format, parameters);
    }

}
