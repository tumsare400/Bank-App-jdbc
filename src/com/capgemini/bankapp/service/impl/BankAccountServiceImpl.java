package com.capgemini.bankapp.service.impl;

import java.util.List;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao bankAccountDao; // to call the methods of Dao
	private long accountId;

	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}

	@Override
	public double checkBalance(long accountId) throws BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance >= 0)
			return balance;
		throw new BankAccountNotFoundException("Bank Account doesn't exist");
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank Account doesn't exist");
		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else
			throw new LowBalanceException("You don't have sufficient amount.");
	}

	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank account doesn't exist");
		balance = balance + amount;
		bankAccountDao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;
	}

	@Override
	public boolean deleteBankAmount(long accountId) throws BankAccountNotFoundException {
		boolean result = bankAccountDao.deleteBankAccount(accountId);
		if(result) {
			DbUtil.commit();
			return result;
		}
		throw new BankAccountNotFoundException("Bank account doesn't exist");
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount)
			throws LowBalanceException, BankAccountNotFoundException {
		try {
			double newBalance = withdrawForFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			DbUtil.commit();
			return newBalance;
		} catch (LowBalanceException | BankAccountNotFoundException e) {
			logger.error("Exception", e);
			DbUtil.rollback();
			throw e;
		}

	}

	public double withdrawForFundTransfer(long accountId, double amount)
			throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank Account doesn't exist");
		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			return balance;
		} else
			throw new LowBalanceException("You don't have sufficient amount.");
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result =  bankAccountDao.addNewBankAccount(account);
		if(result)
			DbUtil.commit();
			return result;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountDao.findAllBankAccounts();
	}

	@Override
	public List<BankAccount> searchBankAccount(long accountId) throws BankAccountNotFoundException {
	List<BankAccount> account = bankAccountDao.searchBankAccount(accountId);
		if(account !=null)
			return account;
		throw new BankAccountNotFoundException("Bank Account not exist");
			
	}

	@Override
	public boolean UpdateBankAccountDetails(long accountId, String accountHolderName, String accountType) {
		List<BankAccount> account = bankAccountDao.searchBankAccount(accountId);
		boolean result =false;
		if(account!=null)
			result =bankAccountDao.UpdateBankAccountDetails(accountId, accountHolderName, accountType);
	      DbUtil.commit();
	      return result;
	}

}
