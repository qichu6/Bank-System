package miniBankSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PendingFundManagement {
	List<PendingFundDataBase> pendingFundList;
	String filePath = "pendingFund.dat";
	int nextFundID;

	public PendingFundManagement() {
		this.pendingFundList = new ArrayList<PendingFundDataBase>();
		this.GetDataFromFile();
	}

	public PendingFundManagement(String s) {
		filePath=s;
		this.pendingFundList = new ArrayList<PendingFundDataBase>();
		this.GetDataFromFile();
	}

	protected void finalize() {
		this.SaveDataToFile();
	}

	public List<PendingFundDataBase> GetPendingFundListByAccountID(int accountID) {
		List<PendingFundDataBase> l = new ArrayList<PendingFundDataBase>();
		for (PendingFundDataBase pDat : this.pendingFundList) {
			if (pDat.getAccountID() == accountID)
				l.add(pDat);
		}
		return l;
	}

	public List<PendingFundDataBase> GetPendingFundListByDate(String s) {
		List<PendingFundDataBase> l = new ArrayList<PendingFundDataBase>();
		for (PendingFundDataBase pDat : this.pendingFundList) {
			if (pDat.checkExpire(s))
				l.add(pDat);
		}
		return l;
	}

	public PendingFundDataBase GetPendingFundByID(int id) {
		for (PendingFundDataBase pDat : this.pendingFundList) {
			if (pDat.getId() == id)
				return pDat;
		}
		return null;
	}

	public boolean AddPendingFund(PendingFundDataBase pDat) {
		pDat.setId(nextFundID);
		if (this.pendingFundList.add(pDat) == true) {
			nextFundID++;
			return true;
		}
		return false;
	}

	public boolean DeletePendingFundByID(int id) {
		PendingFundDataBase pDat;
		for (int i = 0; i < this.pendingFundList.size(); i++) {
			pDat = this.pendingFundList.get(i);
			if (pDat.getId() == id) {
				this.pendingFundList.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean GetDataFromFile() {
		File pDatFile = new File(this.filePath);
		if (pDatFile.exists() == true) {
			this.pendingFundList = new ArrayList<PendingFundDataBase>();
			FileReader reader;
			try {
				reader = new FileReader(this.filePath);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String line = null;
				PendingFundDataBase pDat = new PendingFundDataBase();
				line = bufferedReader.readLine();
				this.nextFundID = Integer.parseInt(line);
				while ((line = bufferedReader.readLine()) != null) {
					pDat = new PendingFundDataBase(line);
					pendingFundList.add(pDat);
				}
				bufferedReader.close();
				System.out.println("Pending Fund Data Read Success!");
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Pending Fund Data File NOT Exists!");
			return false;
		}
		return false;
	}

	public boolean SaveDataToFile() {
		File pDatFile = new File(this.filePath);
		if (pDatFile.exists() == true) {
			// if file exists , delete it!
			System.out.println("Pending Fund Data File Exists!");
			if (pDatFile.delete() == true)
				System.out.println("Pending Fund Data File Delete Success!");
			else {
				System.out.println("Pending Fund Data File Delete Fail!");
				return false;
			}
		}
		FileWriter Writer;
		try {
			Writer = new FileWriter(this.filePath, true);
			BufferedWriter bufferedWriter = new BufferedWriter(Writer);
			bufferedWriter.write(String.valueOf(this.nextFundID + "\n"));
			for (PendingFundDataBase pDat : this.pendingFundList) {
				bufferedWriter.write(pDat.toString() + '\n');
			}
			bufferedWriter.close();
			System.out.println("Pending Fund Data Save Success!");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<PendingFundDataBase> getPendingFundList() {
		return pendingFundList;
	}

	public boolean accountIsClear(int accountID) {
		if (this.GetPendingFundListByAccountID(accountID).isEmpty())
			return true;
		else
			return false;
	}
}
