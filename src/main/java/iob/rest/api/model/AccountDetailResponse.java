package iob.rest.api.model;

import java.math.BigInteger;
import java.util.ArrayList;

public class AccountDetailResponse {
    private BigInteger balance;
    private ArrayList<TransferEvent> events;

    public AccountDetailResponse(BigInteger balance, ArrayList<TransferEvent> events) {
        this.balance = balance;
        this.events = events;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public ArrayList<TransferEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<TransferEvent> events) {
        this.events = events;
    }
}
