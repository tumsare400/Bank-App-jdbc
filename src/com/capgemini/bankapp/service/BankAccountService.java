package com.capgemini.bankapp.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.util.DbUtil;

public interface BankAccountService {
	
	static final Logger logger = Logger.getLogger(DbUtil.class);

	public double checkBalance(long accountId) throws BankAccountNotFoundException;

	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException;

	public double deposit(long amountId, double amount) throws BankAccountNotFoundException;

	public boolean deleteBankAmount(long accountId) throws BankAccountNotFoundException;

	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException, BankAccountNotFoundException;

	public boolean addNewBankAccount(BankAccount account);

	public List<BankAccount> findAllBankAccounts();

	public List<BankAccount> searchBankAccount(long accountId)throws BankAccountNotFoundException;

	public boolean UpdateBankAccountDetails(long accountId, String accountHolderName, String accountType);
}
