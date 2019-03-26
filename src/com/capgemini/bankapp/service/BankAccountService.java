package com.capgemini.bankapp.service;

import java.util.List;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountService {

	public double checkBalance(long accountId);

	public double withdraw(long accountId, double amount) throws LowBalanceException;

	public double deposit(long amountId, double amount);

	public boolean deleteBankAmount(long accountId);

	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException;

	public boolean addNewBankAccount(BankAccount account);

	public List<BankAccount> findAllBankAccounts();
	
	public List<BankAccount> searchBankAccount(long accountId);
}
