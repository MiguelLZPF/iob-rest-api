package iob.rest.api.service;

import java.math.BigInteger;

public interface AccountService {
    Boolean deposit(String from, String to, BigInteger amount);
    Boolean transfer(String from, String to, BigInteger amount);
    Boolean getDetails(String account);
}
