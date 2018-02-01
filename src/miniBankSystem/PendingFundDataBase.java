package miniBankSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Qi Chu
 * @Project bank
 * @Comments base class of pending fund data
 * @JDKversionUsed JDK1.8
 * @version: 0.1
 */
public class PendingFundDataBase {
	int id;
	int accountID;
	double fund;
	Date expire;

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public PendingFundDataBase() {
		this.id = 0;
		this.accountID = 0;
		this.fund = 0;
		this.expire = new Date();
	}

	public PendingFundDataBase(PendingFundDataBase pDat) {
		this.id = pDat.getId();
		this.accountID = pDat.getAccountID();
		this.fund = pDat.getFund();
		this.setExpire(pDat.getExpire());
	}

	public PendingFundDataBase(int id, double fund, Date expire) {
		this.set(id, fund, expire);
	}

	public PendingFundDataBase(String s) {
		this.set(s);
	}

	public void set(int id, double fund, Date expire) {
		this.accountID = id;
		this.fund = fund;
		this.expire = expire;
	}

	public void set(String s) {
		if (s.split(",").length == 4) {
			this.id = Integer.parseInt(s.split(",")[0]);
			this.accountID = Integer.parseInt(s.split(",")[1]);
			this.fund = Double.parseDouble(s.split(",")[2]);
			try {
				this.expire = (new SimpleDateFormat("yyyy-MM-dd")).parse(s
						.split(",")[3]);
			} catch (ParseException e) {
		
				e.printStackTrace();
			}
		} else
			System.out.println("Pending Fund Data Format Error!");
	}

	public String toString() {
		return id + "," + accountID + "," + fund + ","
				+ (new SimpleDateFormat("yyyy-MM-dd")).format(expire);
	}

	public void disp() {
		System.out.println(" id = " + id + ", AccountID = " + accountID
				+ ", fund = " + fund + ", Expire Date= "
				+ (new SimpleDateFormat("yyyy-MM-dd")).format(expire));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getFund() {
		return fund;
	}

	public void setFund(double fund) {
		this.fund = fund;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public boolean checkExpire(String s) {
		if (s.equals((new SimpleDateFormat("yyyy-MM-dd")).format(expire)))
			return true;
		else
			return false;

	}

	public boolean setExpire(String s) {
		try {
			if (((new SimpleDateFormat("yyyy-MM-dd")).parse(s).getTime()) > (new Date()).getTime())
				this.expire = (new SimpleDateFormat("yyyy-MM-dd")).parse(s);
			return true;
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		return false;
	}

	public String getExpire() {
		return (new SimpleDateFormat("yyyy-MM-dd")).format(this.expire);
	}
}
