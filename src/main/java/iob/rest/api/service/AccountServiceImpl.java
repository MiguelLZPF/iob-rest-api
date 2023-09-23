package iob.rest.api.service;

import iob.rest.api.model.SimpleToken;
import iob.rest.api.model.TxExecutionResult;
import iob.rest.api.model.User;
import iob.rest.api.utils.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private Blockchain blockchain;

    public TxExecutionResult deposit(User from, String to, BigInteger amount) throws Exception {
        SimpleToken simpleToken = this.blockchain.getSimpleToken(blockchain.getCredentials(from));
        TransactionReceipt receipt = simpleToken.mint(to, amount).send();
        // Get event and check execution
        SimpleToken.TransferEventResponse event = simpleToken.transferEventFlowable(
                DefaultBlockParameter.valueOf(receipt.getBlockNumber()),
                DefaultBlockParameter.valueOf(receipt.getBlockNumber())
        ).blockingFirst();
        if (Objects.equals(event.to, to)) {
            return new TxExecutionResult(
                    true,
                    receipt.getTransactionHash(),
                    receipt.getBlockNumber(),
                    event
            );
        } else {
            return new TxExecutionResult(false);
        }
    }

    public Boolean transfer(String from, String to, BigInteger amount) {
        return true;
    }

    public Boolean getDetails(String account) {
        return true;
    }
}
