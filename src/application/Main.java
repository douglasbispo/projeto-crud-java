package application;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.entity.Customer;
import model.entity.NaturalPerson;
import model.entity.PhoneNumber;
import service.CustomerService;
import service.NaturalPersonService;
import service.PhoneNumberService;

public class Main {
	private static Scanner input = new Scanner(System.in);
	private static Customer customer = new Customer();
	private static NaturalPerson naturalPerson = new NaturalPerson();
	private static PhoneNumber phoneNumber = new PhoneNumber();
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		menu();
	}

	public static void menu() throws ClassNotFoundException, SQLException, ParseException {
		try {
			System.out.println("--------- DIGITE UMA OPÇÂO ABAIXO ----------");
			System.out.print("| Opção 1 - Adionar cliente                |\r\n"
					+ "| Opção 2 - Atualizar dados                |\r\n"
					+ "| Opção 3 - Listar todos os dados          |\r\n"
					+ "| Opção 4 - Deletar cliente                |\r\n"
					+ "| Opção 5 - Buscar cliente                 |\r\n"
					+ "| Opção 6 - Deletar todos os clientes      |\r\n"
					+ "| Opção 7 - sair                           |\r\n"
					+ "--------------------------------------------\r\n" + ">> ");
			int option = input.nextInt();
			System.out.println("");

			switch (option) {
			case 1:
				create();
				break;
			case 2:
				update();
				break;
			case 3:
				list();
				break;
			case 4:
				delete();
				break;
			case 5:
				searchById();
				break;
			case 6:
				deleteAll();
				break;
			case 7:
				System.out.println("Programa encerrado");
				System.exit(option);
			default:
				System.out.println("Opção Invalida!\r\n");
				menu();
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Por favor, digite um número!");
		}
	}

	public static void create() throws SQLException, ClassNotFoundException, ParseException {
		CustomerService customerService = new CustomerService();
		NaturalPersonService naturalPersonService = new NaturalPersonService();
		PhoneNumberService phoneNumberService = new PhoneNumberService();

		System.out.println("INSIRA OS DADOS DO CLIENTE");
		System.out.print("CPF: ");
		String cpf = input.nextLine();
		input.nextLine();
		System.out.print("Nome: ");
		String firstName = input.nextLine();
		System.out.print("Sobrenome: ");
		String lastName = input.nextLine();
		System.out.print("Data de nascimento: ");
		Date birthDate = sdf.parse(input.nextLine());
		System.out.println("\nENDEREÇO");
		input.nextLine();
		System.out.print("Cidade: ");
		String city = input.nextLine();
		System.out.print("Rua: ");
		String street = input.nextLine();
		System.out.print("Bairro: ");
		String neighborhood = input.nextLine();
		System.out.print("Número: ");
		Integer number = input.nextInt();
		input.nextLine();

		customer = new Customer(neighborhood, street, city, number);
		customerService.save(customer);

		naturalPerson = new NaturalPerson(cpf, firstName, lastName, birthDate);
		naturalPersonService.save(naturalPerson);

		boolean n = true;
		while (n) {
			PhoneNumber phone = null;

			System.out.println("\nTELEFONE");
			System.out.print("DDD: ");
			String ddd = input.next();
			System.out.print("Número: ");
			String numberPh = input.next();

			phone = new PhoneNumber(ddd, numberPh);
			phoneNumberService.save(phone);

			System.out.print("\nDeseja adicionar mais um número? (S/N): ");
			String morePhone = input.next();
			System.out.println("");
			if (morePhone.toUpperCase().equals("S")) n = true;
			else n = false;
		}

		System.out.println("\nCliente adcionado com sucesso!");
		menu();

	}

	public static void update() throws SQLException, ClassNotFoundException, ParseException {
		try {
			System.out.println("--------- DIGITE UMA OPÇÂO ABAIXO ----------");
			System.out.print("| Opção 1 - Atualizar dados cliente        |\r\n"
					+ "| Opção 2 - Atualizar endereço             |\r\n"
					+ "| Opção 3 - Atualizar telefone             |\r\n"
					+ "| Opção 4 - voltar                         |\r\n"
					+ "--------------------------------------------\r\n" + ">> ");
			int option = input.nextInt();
			System.out.println("");

			switch (option) {
			case 1:
				NaturalPersonService naturalPersonService = new NaturalPersonService();

				System.out.print("Digite o codigo do cliente que deseja atualizar: ");
				long code = input.nextLong();

				if (naturalPersonService.checkCode(code)) {
					System.out.println("INSIRA OS NOVOS DADOS DO CLIENTE");
					input.nextLine();
					System.out.print("Nome: ");
					String firstName = input.nextLine();
					System.out.print("Sobrenome: ");
					String lastName = input.nextLine();
					System.out.print("Data de nascimento: ");
					Date birthDate = sdf.parse(input.nextLine());

					naturalPerson = new NaturalPerson(code, firstName, lastName, birthDate);
					naturalPersonService.edit(naturalPerson);

					System.out.println("\nDados atualizados com socesso!\n");
					menu();
				} else {
					System.out.println("\nCliente não encontrado! Tente novamente!\n");
					menu();
				}
				break;
			case 2:
				CustomerService customerService = new CustomerService();

				System.out.print("Digite o codigo do cliente que deseja atualizar: ");
				code = input.nextLong();

				if (customerService.checkCode(code)) {
					input.nextLine();
					System.out.println("\nINSIRA O NOVO ENDEREÇO");
					System.out.print("Cidade: ");
					String city = input.nextLine();
					System.out.print("Rua: ");
					String street = input.nextLine();
					System.out.print("Bairro: ");
					String neighborhood = input.nextLine();
					System.out.print("Número: ");
					Integer number = input.nextInt();

					customer = new Customer(code, neighborhood, street, city, number);
					customerService.edit(customer);

					System.out.println("\nDados atualizados com socesso!\n");
					menu();
				} else {
					System.out.println("\nCliente não encontrado! Tente novamente!\n");
					menu();
				}
				break;
			case 3:
				PhoneNumberService phoneNumberService = new PhoneNumberService();

				System.out.print("Digite o DDD do telefone que deseja atualizar: ");
				String ddd = input.next();
				System.out.print("Digite o número do telefone que deseja atualizar: ");
				String numberPh = input.next();

				if (phoneNumberService.checkCode(ddd, numberPh)) {
					System.out.println("\nINSIRA O NOVO TELEFONE");
					System.out.print("DDD: ");
					String newDDD = input.next();
					System.out.print("Número: ");
					String newNumberPh = input.next();

					phoneNumber.setCheckDDD(ddd);
					phoneNumber.setCheckNumber(numberPh);
					phoneNumber.setDdd(newDDD);
					phoneNumber.setNumber(newNumberPh);
					phoneNumberService.edit(phoneNumber);

					System.out.println("\nDados atualizados com socesso!\n");
					menu();
				} else {
					System.out.println("\nTelefone não encontrado, Tente novamente!\n");
					menu();
				}
				break;
			case 4:
				menu();
				break;
			default:
				System.out.println("Opção Invalida!\r\n");
				menu();
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Por favor, digite um número!");
		}
	}

	public static void list() throws SQLException, ClassNotFoundException, ParseException {
		CustomerService customerService = new CustomerService();
		PhoneNumberService phoneNumberService = new PhoneNumberService();
		NaturalPersonService naturalPersonService = new NaturalPersonService();

		for (NaturalPerson n : naturalPersonService.show()) {
			System.out.println(n);
			for (PhoneNumber p : phoneNumberService.show()) {
				if (n.getCode() == p.getCode()) {
					System.out.println(p);
				}
			}
			for (Customer c : customerService.show()) {
				if (n.getCode() == c.getCode()) {
					System.out.println(c);
				}
			}
		}
		menu();
	}

	public static void delete() throws SQLException, ClassNotFoundException, ParseException {
		CustomerService customerService = new CustomerService();

		System.out.print("Digite o codigo do cliente que deseja deletar: ");
		Long code = input.nextLong();

		if (customerService.checkCode(code)) {
			customerService.remove(code);
			System.out.println("\nCliente deletado com secesso!\n");
		} else {
			System.out.println("\nCliente não encontrado!\n");
		}

		menu();
	}

	public static void searchById() throws SQLException, ClassNotFoundException, ParseException {
		CustomerService customerService = new CustomerService();
		NaturalPersonService naturalPersonService = new NaturalPersonService();
		PhoneNumberService phoneNumberService = new PhoneNumberService();
		
		
		System.out.print("Digite o codigo do cliente que deseja buscar: ");
		Long code = input.nextLong();
		System.out.println("");
		
		if (naturalPersonService.checkCode(code)) {
			naturalPerson = naturalPersonService.findClient(code);
			System.out.println(naturalPerson);
			for (PhoneNumber p : phoneNumberService.show()) {
				if (naturalPerson.getCode() == p.getCode()) {
					System.out.println(p);
				}
			}
			for (Customer c : customerService.show()) {
				if (naturalPerson.getCode() == c.getCode()) {
					System.out.println(c);
				}
			}
		} else {
			System.out.println("\nCliente não encontrado!\n");
		}

		menu();
	}

	public static void deleteAll() throws SQLException, ClassNotFoundException, ParseException {
		CustomerService customerService = new CustomerService();

		System.out.print("Tem certeza de que deseja deletar todos os clientes? (S/N): ");
		String delete = input.next();
		System.out.println("");
		
		if (delete.toUpperCase().equals("S")) {
			customerService.removeAll();
			System.out.println("Todos os clientes foram deletados com sucesso!\n");
			menu();
		} else {
			menu();			
		}
	}

}
