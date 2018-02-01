package miniBankSystem;

import java.util.Date;

public class CreditAgency {

	public static void main(String[] args) {
		CreditAgency c=new CreditAgency();
		AccountDataBase aDat=new AccountDataBase();
		System.out.println(c.GetCreditByData("123","123",new Date()));
		System.out.println(c.GetCreditByData(aDat));
	}

	public double GetCreditByData(String name,String address,Date birth){
		return 0.3;
	}
	
	public double GetCreditByData(AccountDataBase aDat){
		return 0.5;
	}
	
}
