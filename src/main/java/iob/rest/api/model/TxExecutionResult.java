package iob.rest.api.model;

import lombok.Getter;

@Getter
public class TxExecutionResult {
    private Boolean executed;
    private String txHash;
    private Number blockNumber;
    private SimpleToken.TransferEventResponse event;

    public TxExecutionResult(Boolean executed, String txHash, Number blockNumber, SimpleToken.TransferEventResponse event) {
        this.executed = executed;
        this.txHash = txHash;
        this.blockNumber = blockNumber;
        this.event = event;
    }

    public TxExecutionResult(Boolean executed) {
        this.executed = executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public void setBlockNumber(Number blockNumber) {
        this.blockNumber = blockNumber;
    }

    public void setEvent(SimpleToken.TransferEventResponse event) {
        this.event = event;
    }
}
