package miniBankSystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Bank {
	AccountManagement accountManage;
	PendingFundManagement pendFundManage;

	public static void main(String[] args) {

		Bank m = new Bank();
		Scanner sc = new Scanner(System.in);
		int choice = -1;
		while (choice != 0) {
			choice = m.MainMenu();
			switch (choice) {
			case 0:
				break;
			case 1:
				m.CreateAccount();
				break;
			case 2:
				m.DespositFunds();
				break;
			case 3:
				m.CheckFunds();
				break;
			case 4:
				m.WithdrawFunds();
				break;
			case 5:
				m.SuspendAccount();
				break;
			case 6:
				m.CloseAccount();
				break;
			case 7:
				m.ClearFunds();
				break;
			case 8:
				m.update();
				break;
			}
		}
		sc.close();
		m.SaveDataToFile();
	}

	public int WelcomeMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("/tWelcome! Please Input Your Account Type!");
		System.out.println("/t0:Saver Account 1:Junior Account 2:Current Account.");
		int type = sc.nextInt();
		return type;
	}

	public int MainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Main Menu:");
		System.out.println("1.Create Account");
		System.out.println("2.Desposit Funds");
		System.out.println("3.Check Funds");
		// System.out.println("3.Clear Funds");
		System.out.println("4.Withdraw Funds");
		System.out.println("5.Suspend Account");
		System.out.println("6.Close Account");
		System.out.println("7.Clear Funds");
		System.out.println("8.Update Account");
		System.out.println("0.Exit");
		System.out.println("Please Input the Number of Choice:");
		int choice = sc.nextInt();
		if (choice > 8 || choice < 0) {
			System.out.println("Input Error, Please Input Again.");
			return MainMenu();
		}
		return choice;
	}

	public void CreateAccount() {
		Scanner sc = new Scanner(System.in);
		AccountDataBase aDat = new AccountDataBase();
		String s;
		System.out.println("Create Account:");
		System.out.println("Please Input Your Name: ");
		s = sc.nextLine();
		aDat.setName(s);
		System.out.println("Please Input Your Address: ");
		s = sc.nextLine();
		aDat.setAddress(s);
		System.out.println("Please Input Date Of Birth: \n(eg:yyyy-mm-dd)");
		s = sc.nextLine();
		while (s.length() != 10) {
			System.out.println("Wrong Birth,Please Input Like yyyy-mm-dd");
			s = sc.nextLine();
		}
		aDat.setBirth(s);
		System.out.println("Please Input Account Type: 0:Saver Account 1:Junior Account 2:Current Account");
		int accountType = sc.nextInt();
		s = sc.nextLine();
		while (accountType == 1) {
			if (!aDat.setType(accountType)) {
				System.out.println("You Can't Create Junior Account!\nPlease Input Other One.");
				accountType = sc.nextInt();
				s = sc.nextLine();
			} else {
				break;
			}
		}
		aDat.setType(accountType);
		System.out.println("Please Input Your Personal Identification Number: \n(Six numbers)");
		s = sc.nextLine();
		while (s.length() != 6) {
			System.out.println("Wrong PIN , Please Input Six Numbers.");
			s = sc.nextLine();
		}
		aDat.setPin(s);
		CreditAgency c = new CreditAgency();
		aDat.setCreditStatus(c.GetCreditByData(aDat));
		// aDat.disp();
		if (accountManage.AddNewAccount(aDat) == true)
			System.out.println("Create Account Success! Your Account ID is : " + aDat.getId() + " .");
		else
			System.out.println("Create Account Fail");
	}

	public void SuspendAccount() {
		Scanner sc = new Scanner(System.in);
		int accountID;
		String pin;
		String s;
		System.out.println("Suspend Account:");
		System.out.println("Please Input Your Account ID: ");
		accountID = sc.nextInt();
		s = sc.nextLine();
		if (!accountManage.CheckAccountByAccountID(accountID)) {
			System.out.println("No This Account! Return to Main Menu.");
			return;
		}
		if (accountManage.CheckAccountStatByAccountID(accountID) == 0) {
			System.out.println("This Account has Already been Suspended! Return to Main Menu.");
			return;
		}
		System.out.println("Please Input Your Personal Identification Number:");
		pin = sc.nextLine();
		while (pin.length() != 6) {
			System.out.println("Wrong PIN , Please Input Six Numbers.");
			pin = sc.nextLine();
		}
		if (!accountManage.CheckAccountByAccountIDAndPIN(accountID, pin)) {
			System.out.println("Wrong Pin! Return to Main Menu.");
			return;
		}
		System.out.println("Input 'yes' to Confirm To Suspend Your Account ID = " + accountID + ".");
		s = sc.nextLine();
		s.toLowerCase();
		if ("yes".equals(s)) {
			if (accountManage.SuspendAccountByID(accountID) == true)
				System.out.println("Suspend Account : " + accountID + " Success!");
			else
				System.out.println("Suspend Account : " + accountID + " Fail!");

		}
	}

	public void CloseAccount() {
		Scanner sc = new Scanner(System.in);
		AccountDataBase aDat;
		int accountID;
		String pin;
		String s;
		System.out.println("Close Account:");
		System.out.println("Please Input Your Account ID: ");
		accountID = sc.nextInt();
		s = sc.nextLine();
		if (!accountManage.CheckAccountByAccountID(accountID)) {
			System.out.println("No This Account! Return to Main Menu.");
			return;
		}
		System.out.println("Please Input Your Personal Identification Number:");
		pin = sc.nextLine();
		while (pin.length() != 6) {
			System.out.println("Wrong PIN , Please Input Six Numbers.");
			pin = sc.nextLine();
		}
		if (!accountManage.CheckAccountByAccountIDAndPIN(accountID, pin)
				&& accountManage.CheckAccountStatByAccountID(accountID) == 1) {
			System.out.println("Wrong Pin! Return to Main Menu.");
			return;
		}
		aDat = accountManage.GetAccountByIDAndPIN(accountID, pin);
		System.out.println("Input 'yes' to Confirm To Close Your Account ID = " + accountID + ".");
		s = sc.nextLine();
		s.toLowerCase();
		if ("yes".equals(s)) {
			if (!aDat.isClear()) {
				System.out.println("Funds Balance Has NOT Been Cleared!");
				System.out.println("Please Clear Your Funds.");
			} else if (!pendFundManage.accountIsClear(accountID)) {
				System.out.println("Funds Balance Has NOT Been Cleared!");
				System.out.println("Please Clear Your Pending Funds.");
			} else {
				System.out.println("Funds Balance Has Been Cleared!");
				if (accountManage.CloseAccountByID(accountID) == true)
					System.out.println("Close Account : " + accountID + " Success!");
				else
					System.out.println("Close Account : " + accountID + " Fail!");
			}
		}

	}

	public void DespositFunds() {
		Scanner sc = new Scanner(System.in);
		PendingFundDataBase pDat;
		int accountID;
		int fundType;
		double amount;
		String expire;
		String s;
		System.out.println("Desposit Funds:");
		System.out.println("Please Input Your Account ID: ");
		accountID = sc.nextInt();
		s = sc.nextLine();
		if (!accountManage.CheckAccountByAccountID(accountID)) {
			System.out.println("No This Account! Return to Main Menu.");
			return;
		}
		if (accountManage.CheckAccountStatByAccountID(accountID) == 0) {
			System.out.println("This Account has been Suspended! Return to Main Menu.");
			return;
		}
		System.out.println("Please Input Your Fund Type : \n 1.Cash \n 2.Cheque");
		fundType = sc.nextInt();
		s = sc.nextLine();
		while (fundType != 1 && fundType != 2) {
			System.out.println("Wrong Fund Type , Please Input Again.\n 1.Cash \n 2.Cheque");
			fundType = sc.nextInt();
			s = sc.nextLine();
		}
		switch (fundType) {
		case 1:
			System.out.println("Please Input the Amount of Cash:");
			amount = sc.nextDouble();
			s = sc.nextLine();
			if (amount < 0 || amount > 10000) {
				System.out.println("Wrong Amount! Return to Main Menu.");
				return;
			} else {
				System.out.println("Despositing...");
				if (accountManage.ChangeFundByID(accountID, amount))
					System.out.println("Desposit Success!");
				else
					System.out.println("Desposit Fail!");
			}
			break;
		case 2:
			System.out.println("Please Input the Amount of Cheque:");
			amount = sc.nextDouble();
			s = sc.nextLine();
			if (amount < 0 || amount > 10000) {
				System.out.println("Wrong Amount! Return to Main Menu.");
				return;
			} else {
				System.out.println("Please Input the Date of Cheque:(eg:yyyy-mm-dd)");
				expire = sc.nextLine();
				if (expire.length() != 10) {
					System.out.println("Wrong Date! Return to Main Menu.");
					return;
				} else {
					pDat = new PendingFundDataBase();
					if (!pDat.setExpire(expire)) {
						System.out.println("Wrong Date! Return to Main Menu.");
						return;
					}
					pDat.setAccountID(accountID);
					pDat.setFund(amount);
					if (pendFundManage.AddPendingFund(pDat) == true)
						System.out
								.println("Add Pengding Fund Success! Your Pengding Fund ID is : " + pDat.getId() + ".");
					else
						System.out.println("Add Pengding Fund Fail!");
				}
			}
			break;
		}
	}

	/*
	 * 
	 */

	public void ClearFunds() {
		Scanner sc = new Scanner(System.in);
		String expire;
		Date date = new Date();
		List<PendingFundDataBase> listPendingFund;
		SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Clear the Funds which Expire is " + sfm.format(date) + ".");
		expire = sfm.format(date);
		listPendingFund = pendFundManage.GetPendingFundListByDate(expire);
		for (PendingFundDataBase pDat : listPendingFund) {
			System.out.println("Process on Fund id = " + pDat.getId() + "...");
			if (accountManage.ChangeFundByID(pDat.getAccountID(), pDat.getFund()))
				System.out.println("Credit Success!");
			else
				System.out.println("Credit Fail!");
			if (pendFundManage.DeletePendingFundByID(pDat.getAccountID()) == true)
				System.out.println("Delete Pending Fund Success");
			else
				System.out.println("Delete Pending Fund Fail!");

			System.out.println("Process on Fund id = " + pDat.getId() + " Over!");
		}
		System.out.println("Process of Clear Funds Over!");
	}

	public void WithdrawFunds() {
		Scanner sc = new Scanner(System.in);
		AccountDataBase aDat;
		int accountID;
		double amount;
		double overdraft;
		String pin;
		String s;
		String[] type = { "Saver Account", "Junior Account", "Current Account" };
		System.out.println("Withdraw Funds:");
		System.out.println("Please Input Your Account ID: ");
		accountID = sc.nextInt();
		s = sc.nextLine();
		if (!accountManage.CheckAccountByAccountID(accountID)) {
			System.out.println("No This Account! Return to Main Menu.");
			return;
		}
		if (accountManage.CheckAccountStatByAccountID(accountID) == 0) {
			System.out.println("Your Account" + accountID + " is Suspended! Return to Main Menu.");
			return;
		}
		System.out.println("Please Input Your Personal Identification Number:");
		pin = sc.nextLine();
		while (pin.length() != 6) {
			System.out.println("Wrong PIN , Please Input Six Numbers.");
			pin = sc.nextLine();
		}
		if (!accountManage.CheckAccountByAccountIDAndPIN(accountID, pin)) {
			System.out.println("Wrong Pin! Return to Main Menu.");
			return;
		}
		aDat = accountManage.GetAccountByIDAndPIN(accountID, pin);
		overdraft = aDat.getFund() + aDat.getOverdraft();
		System.out.println("user " + aDat.getName() + " , Your Account :" + aDat.getId() + " is " + type[aDat.getType()]
				+ ".\nYour Credited Funds is " + aDat.getFund() + " .\nOverdraft Limit is " + overdraft);
		System.out.println("Please Input the Amount of Funds you want to Withdaw:");
		amount = sc.nextDouble();
		s = sc.nextLine();
		while (aDat.checkOverdraft(amount)) {
			System.out.println("Over Limit! Please Input again:");
			amount = sc.nextDouble();
			s = sc.nextLine();
		}
		if (accountManage.ChangeFundByID(accountID, -amount) == true) {
			System.out.println("Withdraw Funds Success! Amount: " + amount);
		} else {
			System.out.println("Withdraw Funds Fail!");
		}
	}

	public void CheckFunds() {
		Scanner sc = new Scanner(System.in);
		int accountID;
		String pin;
		String s;
		String[] stat = { "Suspended", "Available", "Closed" };
		AccountDataBase aDat;
		List<PendingFundDataBase> listPendingFund;
		System.out.println("Check Funds:");
		System.out.println("Please Input Your Account ID: ");
		accountID = sc.nextInt();
		s = sc.nextLine();
		if (!accountManage.CheckAccountByAccountID(accountID)) {
			System.out.println("No This Account! Return to Main Menu.");
			return;
		}
		System.out.println("Please Input Your Personal Identification Number:");
		pin = sc.nextLine();
		while (pin.length() != 6) {
			System.out.println("Wrong PIN , Please Input Six Numbers.");
			pin = sc.nextLine();
		}
		if (!accountManage.CheckAccountByAccountIDAndPIN(accountID, pin)) {
			System.out.println("Wrong Pin! Return to Main Menu.");
			return;
		}
		aDat = accountManage.GetAccountByIDAndPIN(accountID, pin);
		listPendingFund = pendFundManage.GetPendingFundListByAccountID(accountID);
		System.out.println("user " + aDat.getName() + " , Your Account :" + aDat.getId() + " is " + stat[aDat.getStat()]
				+ ".\nYour Credited Funds is " + aDat.getFund() + " .");
		if (listPendingFund.size() == 0) {
			System.out.println("You have no Pending Funds!");
		} else {
			System.out.println("You have " + listPendingFund.size() + " Pending Funds!");
			for (PendingFundDataBase pDat : listPendingFund) {
				// pDat.disp();
				System.out.println("ID : " + pDat.getId() + " Funds : " + pDat.getFund() + " Expire Date : "
						+ pDat.getExpire() + ".");
			}
		}
		System.out.println("Return to Main Menu.");
	}

	public void update() {
		accountManage.update();
	}

	public boolean SaveDataToFile() {
		if (accountManage.SaveDataToFile() && pendFundManage.SaveDataToFile())
			return true;
		else
			return false;
	}

	public Bank() {
		accountManage = new AccountManagement();
		pendFundManage = new PendingFundManagement();
	}
}
