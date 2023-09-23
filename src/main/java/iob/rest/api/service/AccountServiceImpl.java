package iob.rest.api.service;

import io.reactivex.Flowable;
import iob.rest.api.model.*;
import iob.rest.api.utils.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private Environment env;
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

    public TxExecutionResult transfer(User from, String to, BigInteger amount) throws Exception {
        SimpleToken simpleToken = this.blockchain.getSimpleToken(blockchain.getCredentials(from));
        TransactionReceipt receipt = simpleToken.transfer(to, amount).send();
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

    public AccountDetailResponse getDetails(User from, String account) throws Exception {
        // yes, anyone can see each other account
        Web3j web3j = blockchain.getProvider();
        SimpleToken simpleToken = this.blockchain.getSimpleToken(blockchain.getCredentials(from));
        BigInteger balance = simpleToken.balanceOf(account).send();
        // Get Transfer events since SimpleToken was deployed
        Optional<Transaction> deployedTxOpt = web3j.ethGetTransactionByHash(
                env.getRequiredProperty("contracts.SimpleToken.deployTxHash")
        ).send().getTransaction();
        DefaultBlockParameter deployedBlock;
        if (deployedTxOpt.isEmpty()) {
            deployedBlock = DefaultBlockParameter.valueOf(BigInteger.valueOf(0));
        } else {
            deployedBlock = DefaultBlockParameter.valueOf(deployedTxOpt.get().getBlockNumber());
        }
        Flowable<SimpleToken.TransferEventResponse> events = simpleToken.transferEventFlowable(
                deployedBlock,
                DefaultBlockParameterName.LATEST
        );
        ArrayList<TransferEvent> myEvents = new ArrayList<TransferEvent>();
        events.forEach(event -> {
            if (Objects.equals(event.to, account) || Objects.equals(event.from, account)) {
                myEvents.add(new TransferEvent(event.from, event.to, event.value));
            }
        });

        return new AccountDetailResponse(balance, myEvents);
    }
}
