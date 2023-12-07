package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.entity.Customer;

public class CustomerDAO implements GenericDAO<Long, Customer> {
	private static final String CODE = "codigo_cli";
	private static final String NEIGHBORHOOD = "bairro";
	private static final String STREET = "rua";
	private static final String CITY = "cidade";
	private static final String NUMBER = "numero"; 
	
	private ConnectionFactory connFactory;
	
	public CustomerDAO() throws ClassNotFoundException {
		this.connFactory = ConnectionFactory.getInstance();
	}
	
	
	@Override
	public void create(Customer customer) throws SQLException {
		String sql = "INSERT INTO cliente(bairro, rua, cidade, numero) VALUES (?, ?, ?, ?)";			

		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			// criar conexão com o banco de dados
			conn = connFactory.createConnetionToPostgreSQL();
			
			// criar prepareStatement para executar uma query
			pstm = conn.prepareStatement(sql);
			// adicionar os valores a query
			pstm.setString(1, customer.getNeighborhood());
			pstm.setString(2, customer.getStreet());
			pstm.setString(3, customer.getCity());
			pstm.setInt(4, customer.getNumber());
			
			// executar a query
			int count = pstm.executeUpdate();
			
			if (count == 0) throw new SQLException("Dados não inseridos!");
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			// fechar conexções
			try {
				if (conn != null) conn.close();
				if (pstm != null) pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void update(Customer customer) throws SQLException {
		String sql = "UPDATE cliente SET bairro = ?, rua = ?, cidade = ?, numero = ? "
				+ "WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, customer.getNeighborhood());
			pstm.setString(2, customer.getStreet());
			pstm.setString(3, customer.getCity());
			pstm.setInt(4, customer.getNumber());
			// buscar cliente pelo codigo
			pstm.setLong(5, customer.getCode());
			
			int count = pstm.executeUpdate();
			
			if (count == 0) throw new SQLException("Cliente não encontrado!"); 
		
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (conn != null) conn.close(); 
				if (pstm != null) pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public List<Customer> list() throws SQLException { 
		String sql = "SELECT * FROM cliente";
		
		List<Customer> customers = new ArrayList<Customer>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		// Classe que vai recuperar os dados do banco
		ResultSet rst = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			// ResultSet vai receber o que voltou do banco
			rst = pstm.executeQuery();
			
			// Vai percorrer o ResultSet enquanto tiver registro
			while (rst.next()) {
//				Client client = new Client();
				
				// recuperar dados
//				client.setCode(rst.getInt(CODE));
//				client.setNeighborhood(rst.getString(NEIGHBORHOOD));
//				client.setStreet(rst.getString(STREET));
//				client.setCity(rst.getString(CITY));
//				client.setNumber(rst.getInt(NUMBER));
//
//				clients.add(client);
				
				long code = rst.getLong(CODE);
				String neighborhood = rst.getString(NEIGHBORHOOD);
				String street = rst.getString(STREET);
				String city = rst.getString(CITY);
				Integer number = rst.getInt(NUMBER); 
				
				customers.add(new Customer(code, neighborhood, street, city, number));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
				if (pstm != null) conn.close();
				if (rst != null) rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return customers;
	}
	
	
	@Override
	public void delete(Long code) throws SQLException {
		String sql = "DELETE FROM cliente WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setLong(1, code);
			
			int count = pstm.executeUpdate();
			
			if (count == 0) throw new SQLException("Cliente não encontrado!");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (conn != null) conn.close();
				if (pstm != null) pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public Customer searchById(Long key) throws SQLException {
		String sql = "SELECT * FROM cliente WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rst = null;
		
		Customer customer = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setLong(1, key);
			
			rst = pstm.executeQuery();
			
			while (rst.next()) {
				long code = rst.getLong(CODE);
				String neighborhood = rst.getString(NEIGHBORHOOD);
				String street = rst.getString(STREET);
				String city = rst.getString(CITY);
				Integer number = rst.getInt(NUMBER);
				
				customer = new Customer(code, neighborhood, street, city, number);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
				if (pstm != null) pstm.close();
				if (rst != null) rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return customer;
	}

	
	@Override
	public void deleteAll() throws SQLException {
		String sql = "DELETE FROM cliente";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
				if (pstm != null) pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean checkCode(Long code) {
		String sql = "SELECT * FROM cliente WHERE codigo_cli = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rst = null;
				
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setLong(1, code);
			
			rst = pstm.executeQuery();
			
			if (rst.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
				if (pstm != null) pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
