package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import miniBankSystem.AccountDataBase;
import miniBankSystem.AccountManagement;

import org.junit.Before;
import org.junit.Test;

public class AccountManagementTest {
	private static AccountManagement aManage;
	String filePath = "testAccount.dat";
	private AccountDataBase aDat;
	private File aDatFile;

	@Before
	public void setUp() throws Exception {
		this.aDat = new AccountDataBase();
		this.aDat.setId(1);
		this.aDat.setType(2);
		this.aDat.setName("Lily");
		this.aDat.setAddress("1600S Joyce St");
		this.aDat.setBirth("2005-11-16");
		this.aDat.setType(1);
		this.aDat.setCreditStatus(0.3);
		this.aDat.setPin("123456");
		this.aDat.setFund(0);
		this.aDat.setStat(1);
		this.aDat.setOverdraft(50);
		this.aDatFile = new File(filePath);
		this.aDatFile.delete();
		aManage = new AccountManagement(filePath);
		aManage.getAccountList().clear();
	}

	@Test
	public void testCheckAccountByAccountID() {
		aManage.getAccountList().add(aDat);
		assertEquals(true, aManage.CheckAccountByAccountID(aDat.getId()));
	}

	@Test
	public void testCheckAccountStatByAccountID() {
		aManage.getAccountList().add(aDat);
		assertEquals(1, aManage.CheckAccountStatByAccountID(aDat.getId()));
		aDat.setStat(2);
		assertEquals(2, aManage.CheckAccountStatByAccountID(aDat.getId()));
		aManage.getAccountList().remove(aDat);
		assertEquals(-1, aManage.CheckAccountStatByAccountID(aDat.getId()));
	}

	@Test
	public void testCheckAccountByAccountIDAndPIN() {
		aManage.getAccountList().add(aDat);
		assertEquals(true, aManage.CheckAccountByAccountIDAndPIN(aDat.getId(), "123456"));
		assertEquals(false, aManage.CheckAccountByAccountIDAndPIN(aDat.getId(), "123466"));
	}

	@Test
	public void testAddNewAccount() {
		aManage.AddNewAccount(aDat);
		assertEquals(1, aManage.getAccountList().size());
	}

	@Test
	public void testCloseAccountByID() {
		aManage.getAccountList().add(aDat);
		aManage.CloseAccountByID(aDat.getId());
		assertEquals(2, aManage.CheckAccountStatByAccountID(aDat.getId()));
	}

	@Test
	public void testSuspendAccountByID() {
		aManage.getAccountList().add(aDat);
		aManage.SuspendAccountByID(aDat.getId());
		assertEquals(0, aManage.CheckAccountStatByAccountID(aDat.getId()));
	}

	@Test
	public void testChangeFundByID() {
		aManage.getAccountList().add(aDat);
		aManage.ChangeFundByID(aDat.getId(), 50);
		assertEquals(50, aDat.getFund(), 0.01F);
		aManage.ChangeFundByID(aDat.getId(), -100);
		assertEquals(-50, aDat.getFund(), 0.01F);
	}

	@Test
	public void testGetDataFromFile() throws Exception {
		if (aDatFile.exists() == true) {
			// if file exists , delete it!
			aDatFile.delete();
		}
		FileWriter Writer;
		try {
			Writer = new FileWriter(filePath, true);
			BufferedWriter bufferedWriter = new BufferedWriter(Writer);
			bufferedWriter.write(1 + "\n");
			bufferedWriter.write(aDat.toString() + '\n');
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		aManage.GetDataFromFile();
		assertEquals(1, aManage.getAccountList().size());
	}

	@Test
	public void testSaveDataToFile() {
		aManage.AddNewAccount(aDat);
		aManage.SaveDataToFile();

		if (aDatFile.exists() == true) {
			FileReader reader;
			try {
				reader = new FileReader(filePath);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String line = null;
				line = bufferedReader.readLine();
				assertEquals("1", line);
				line = bufferedReader.readLine();
				assertEquals(aDat.toString(), line);
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
