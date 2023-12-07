package model.entity;

public class PhoneNumber {
	private String ddd;
	private String  number;
	private long code;
	private String checkDDD;
	private String checkNumber;
	
	public PhoneNumber() {}
	
	public PhoneNumber(String ddd, String number) {
		this.ddd = ddd;
		this.number = number;
	}

	public PhoneNumber(String ddd, String number, long code) {
		this.ddd = ddd;
		this.number = number;
		this.code = code;
	}

	public String getDdd() {
		return ddd;
	}
	
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}
	
	public String getCheckDDD() {
		return checkDDD;
	}

	public void setCheckDDD(String checkDDD) {
		this.checkDDD = checkDDD;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	@Override
	public String toString() {
		return "\nNÚMERO DE TELEFONE" + 
				"\nDDD: " + this.getDdd() + 
				"\nNúmero: " + this.number;
	}
	
	
	
}
