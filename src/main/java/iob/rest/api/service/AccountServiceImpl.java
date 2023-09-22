package iob.rest.api.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class AccountServiceImpl implements AccountService {
    public Boolean deposit(String from, String to, BigInteger amount) {
        return true;
    }
    public Boolean transfer(String from, String to, BigInteger amount) {
        return true;
    }
    public Boolean getDetails(String account) {
        return true;
    }
}
