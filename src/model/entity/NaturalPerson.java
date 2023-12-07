package model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NaturalPerson {
	private long code;
	private String cpf;
	private String firstName;
	private String lastName;
	private Date birthDate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public NaturalPerson() {}

	public NaturalPerson(String cpf, String firstName, String lastName, Date birthDate) {
		this.cpf = cpf;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	public NaturalPerson(long code, String firstName, String lastName, Date birthDate) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	public NaturalPerson(long code, String cpf, String firstName, String lastName, Date birthDate) {
		this.code = code;
		this.cpf = cpf;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "------------- DADOS DO CLIENTE -------------" + 
				"\nCódigo: " + this.getCode() +
				"\nCPF: " + this.getCpf() + 
				"\nNome: " + this.getFirstName() + 
				"\nSobrenome: " + this.getLastName() +
				"\nData de nascimento: " + sdf.format(this.getBirthDate());
	}
	
}
