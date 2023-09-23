package iob.rest.api.service;

import iob.rest.api.model.TxExecutionResult;
import iob.rest.api.model.User;

import java.math.BigInteger;

public interface AccountService {
    TxExecutionResult deposit(User from, String to, BigInteger amount) throws Exception;

    Boolean transfer(String from, String to, BigInteger amount);

    Boolean getDetails(String account);
}
