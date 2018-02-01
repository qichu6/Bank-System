package miniBankSystem;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountDataBase {
	int id;
	int stat;
	String name;
	String address;
	Date birth;
	// 0:Saver Account 1:Junior Account 2:Current Account
	int type;
	double creditStatus;
	String pin;
	double fund;
	double overdraft;

	public static void main(String[] args) {

	}

	public static String pinToMD5(String pin) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(pin.getBytes());
			byte b[] = md5.digest();
			StringBuffer buf = new StringBuffer("");
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public int getAge() {
		// birth = sdf.parse("2004-02-03");
		Calendar a = Calendar.getInstance();
		int age = a.get(Calendar.YEAR) - birth.getYear() - 1900;
		if ((a.get(Calendar.DATE) - birth.getDate() < 0 && a.get(Calendar.MONTH) + 1 - birth.getMonth() == 0)
				|| a.get(Calendar.MONTH) + 1 - birth.getMonth() < 0)
			age--;
		return age;
	}

	public AccountDataBase(String s) {
		this.set(s);
	}

	public AccountDataBase() {
		this.id = -1;
		this.stat = 0;
		this.name = "";
		this.address = "";
		this.birth = new Date();
		this.creditStatus = 0;
		this.pin = "";
		this.type = -1;
		this.fund = 0;
		this.overdraft = 0;
	}

	public AccountDataBase(int id, int stat, String name, String address, Date birth, double creditStatus, String pin,
			int type) {
		this.set(id, stat, name, address, birth, creditStatus, pin, type);
	}

	public void set(String s) {
		if (s.split(",").length == 10) {
			this.id = Integer.parseInt(s.split(",")[0]);
			this.stat = Integer.parseInt(s.split(",")[1]);
			this.name = s.split(",")[2];
			this.address = s.split(",")[3];
			try {
				this.birth = (new SimpleDateFormat("yyyy-MM-dd")).parse(s.split(",")[4]);
			} catch (ParseException e) {

				e.printStackTrace();
			}
			this.type = Integer.parseInt(s.split(",")[5]);
			this.creditStatus = Double.parseDouble(s.split(",")[6]);
			this.pin = s.split(",")[7];
			this.fund = Double.parseDouble(s.split(",")[8]);
			this.overdraft = Double.parseDouble(s.split(",")[9]);
		} else
			System.out.println("Account Data Format Error!");
	}

	public void set(int id, int stat, String name, String address, Date birth, double creditStatus, String pin,
			int type) {
		this.id = id;
		this.stat = stat;
		this.name = name;
		this.address = address;
		this.birth = birth;
		this.creditStatus = creditStatus;
		this.pin = pin;
		this.type = type;
	}

	public String toString() {
		return id + "," + stat + "," + name + "," + address + "," + (new SimpleDateFormat("yyyy-MM-dd")).format(birth)
				+ "," + type + "," + creditStatus + "," + pin + "," + fund + "," + overdraft;
	}

	public void disp() {
		System.out.println("id=" + id + ", stat: " + stat + ", name=" + name + ", address=" + address + ", birth="
				+ (new SimpleDateFormat("yyyy-MM-dd")).format(birth) + ", type=" + type + ", creditStatus="
				+ creditStatus + ", pin=" + pin + ", fund=" + fund + ", overdraft=" + overdraft);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getType() {
		return type;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public double getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(double creditStatus) {
		this.creditStatus = creditStatus;
	}

	public double getFund() {
		return fund;
	}

	public void setFund(double fund) {
		this.fund = fund;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	public String getMD5Pin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pinToMD5(pin);
	}

	public boolean setBirth(String s) {
		try {
			this.birth = (new SimpleDateFormat("yyyy-MM-dd")).parse(s);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return false;
	}

	public boolean setType(int type) {
		if (type == 1) {
			if (this.getAge() < 16) {
				this.type = type;
			} else {
				System.out.println("Age :" + this.getAge());
				return false;
			}
		} else {
			this.type = type;
		}
		return true;
	}

	public boolean changeFund(double change) {
		if (fund + overdraft + change >= 0) {
			this.fund += change;
			return true;
		} else
			return false;
	}

	public boolean CheackPIN(String pin) {
		if (pinToMD5(pin).equals(this.pin))
			return true;
		else
			return false;
	}

	/*
	 *add new function return true if
	 * amount<=fund+overdraft.
	 */
	public boolean checkOverdraft(double amount) {
		if (this.fund + this.overdraft >= amount)
			return false;
		else
			return true;
	}

	/*
	 * add new function return true if fund==0.
	 */
	public boolean isClear() {
		if (this.fund == 0)
			return true;
		else
			return false;
	}
}
