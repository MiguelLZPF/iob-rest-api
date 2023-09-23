package iob.rest.api.service;

import iob.rest.api.model.SimpleToken;
import iob.rest.api.model.User;
import iob.rest.api.utils.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private Environment env;
    @Autowired
    private Blockchain blockchain;
    public Boolean deposit(User from, String to, BigInteger amount) throws Exception {
        SimpleToken simpleToken = this.blockchain.getSimpleToken(blockchain.getCredentials(from));
        TransactionReceipt receipt = simpleToken.mint(to, amount).send();
        System.out.println(receipt.getTransactionHash());
        return true;
    }
    public Boolean transfer(String from, String to, BigInteger amount) {
        return true;
    }
    public Boolean getDetails(String account) {
        return true;
    }
}
