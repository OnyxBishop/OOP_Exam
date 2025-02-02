package service;

import model.Wallet;

public class TransferService {
    public boolean transfer(Wallet from, Wallet to, double amount){
        return from.transfer(to, amount);
    }
}
