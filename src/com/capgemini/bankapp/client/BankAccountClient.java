package com.capgemini.bankapp.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {
	
	static final Logger logger = Logger.getLogger(BankAccountClient.class);
	
	public static void main(String[] args) throws LowBalanceException, BankAccountNotFoundException {

		int choice;
		String accountHolderName;
		String accountType;
		double accountBalance;
		long accountId;
		double amount;
		long fromAccount;
		long toAccount;
		BankAccountService bankService = new BankAccountServiceImpl();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("1.Add New BankAccount\n2.Withdraw\n3. deposit\n4. fundTransfer\n"
						+ "5.Delete BankAccount\n6. Display All BankAccount details\n7.Search BankAccount\n"
						+ "8. Check Balance\n9 UpdateBankAccount\n10. Exit\n");

				System.out.println("Enter Your choice:");
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {

				case 1:
					System.out.println("Enter account holder name: ");
					accountHolderName = reader.readLine();
					System.out.println("Enter account type: ");
					accountType = reader.readLine();
					System.out.println("Enter account balance: ");
					accountBalance = Double.parseDouble(reader.readLine());

					BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);

					if (bankService.addNewBankAccount(account))
						System.out.println("Account created successfully\n");
					else
						System.out.println("Failed to create new account\n");
					break;

				case 2:
					System.out.println("Enter account Id\n");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter an  amount");
					amount = Double.parseDouble(reader.readLine());
				
					try {
						double balance = bankService.withdraw(accountId, amount);
						System.out.println("Transaction is successfull,Your current balance:"+balance);
					}
					catch(LowBalanceException e) {
						logger.error(e);
					}
					break;

				case 3:
					System.out.println("Enter account Id");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter amount");
					amount = Double.parseDouble(reader.readLine());
					System.out.println(bankService.deposit(accountId, amount));
					break;

				case 4:
					System.out.println("From  accountId to transfer fund:");
					fromAccount = Long.parseLong(reader.readLine());
					System.out.println("Enter amount that u want to transfer:");
					amount = Double.parseDouble(reader.readLine());
					System.out.println(" To account Id:");
					toAccount = Long.parseLong(reader.readLine());
					System.out.println(bankService.fundTransfer(fromAccount, toAccount, amount));
					break;
     
				case 5:
					System.out.println("Enter account Id\n");
					accountId = Long.parseLong(reader.readLine());
					System.out.println(bankService.deleteBankAmount(accountId));
					break;
					
				case 6:
					System.out.println(bankService.findAllBankAccounts());
					break;
				
				case 7:
					  System.out.println("Enter account Id :");
					  accountId = Long.parseLong(reader.readLine());
					  System.out.println(bankService.searchBankAccount(accountId));
					  break;
					  
				case 8:
					System.out.println("Enter account Id to check balance:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println(bankService.checkBalance(accountId));
					break;
					
				case 9:
					System.out.println("Enter account holder name: ");
					accountHolderName = reader.readLine();
					System.out.println("Enter account type: ");
					accountType = reader.readLine();
					System.out.println("Enter account Id to check balance:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println(bankService.UpdateBankAccountDetails(accountId, accountHolderName, accountType));
					
                     break;
                     
				case 10:
					System.out.println("Thanks for banking with us");
					System.exit(0);
                     break;
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error("Exception:",e);
			
		}
	}
}
