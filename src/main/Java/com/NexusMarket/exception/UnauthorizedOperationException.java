package com.NexusMarket.exception;

public class UnauthorizedOperationException extends BusinessRuleException {
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
