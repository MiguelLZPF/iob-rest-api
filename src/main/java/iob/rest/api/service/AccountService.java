package iob.rest.api.service;

import iob.rest.api.model.AccountDetailResponse;
import iob.rest.api.model.TxExecutionResult;
import iob.rest.api.model.User;

import java.math.BigInteger;

public interface AccountService {
    TxExecutionResult deposit(User from, String to, BigInteger amount) throws Exception;

    TxExecutionResult transfer(User from, String to, BigInteger amount) throws Exception;

    AccountDetailResponse getDetails(User user, String account) throws Exception;
}
