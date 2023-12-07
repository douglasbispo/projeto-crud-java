package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.entity.PhoneNumber;

public class PhoneNumberDAO implements GenericDAO<Long, PhoneNumber>{
	private static final String DDD = "ddd";
	private static final String NUMBER = "numero";
	private static final String CODE = "codigo_cli";
	
	private ConnectionFactory connFactory;
	
	public PhoneNumberDAO() throws ClassNotFoundException {
		this.connFactory = ConnectionFactory.getInstance();
	}

	@Override
	public void create(PhoneNumber phoneNumber) throws SQLException {
		String sql = "INSERT INTO telefone_cliente(ddd, numero, codigo_cli) VALUES (?, ?, ?)";	
		String sql1 = "SELECT * FROM cliente ORDER BY codigo_cli DESC LIMIT 1";

		Connection conn = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		
		ResultSet rst = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, phoneNumber.getDdd());
			pstm.setString(2, phoneNumber.getNumber());
			
			
			pstm1 = conn.prepareStatement(sql1);
			rst = pstm1.executeQuery();
			
			long code = 0;
			while (rst.next()) {
				code = rst.getLong(CODE);
			}
			
			pstm.setLong(3, code);
			
			int count = pstm.executeUpdate();
			
			if (count == 0) throw new SQLException("Dados não inseridos!");
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				if (conn != null) conn.close();
				if (pstm != null) pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(PhoneNumber phoneNumber) throws SQLException {
		String sql = "UPDATE telefone_cliente SET ddd = ?, numero = ? "
				+ "WHERE ddd = ? and  numero = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, phoneNumber.getDdd());
			pstm.setString(2, phoneNumber.getNumber());
			// buscar cliente pelo codigo
			pstm.setString(3, phoneNumber.getCheckDDD());
			pstm.setString(4, phoneNumber.getCheckNumber());
			
			int count = pstm.executeUpdate();
			
			if (count == 0) throw new SQLException("Telefone não encontrado!"); 
		
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
	public List<PhoneNumber> list() throws SQLException {
		String sql = "SELECT * FROM telefone_cliente";
		
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			rst = pstm.executeQuery();
			
			while (rst.next()) {			
				String ddd = rst.getString(DDD);
				String number = rst.getString(NUMBER);
				long code = rst.getLong(CODE);
				
				numbers.add(new PhoneNumber(ddd, number, code));
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
		
		return numbers;
	}

	@Override
	public void delete(Long key) throws SQLException {
		String sql = "DELETE FROM telefone_cliente WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setLong(1, key);
			
			int count = pstm.executeUpdate();
			
			if (count == 0) throw new SQLException("Telefone não encontrado!");
			
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
	public PhoneNumber searchById(Long key) throws SQLException {
		String sql = "SELECT * FROM telefone_cliente WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rst = null;
		
		PhoneNumber phoneNumber = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setLong(1, key);
			
			rst = pstm.executeQuery();
			
			while (rst.next()) {
				String ddd = rst.getString(DDD);
				String number = rst.getString(NUMBER);
				long code = rst.getLong(CODE);
				
				phoneNumber = new PhoneNumber(ddd, number, code);
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
		return phoneNumber;
	}

	@Override
	public void deleteAll() throws SQLException {
		String sql = "DELETE FROM telefone_cliente";
		
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
	
	
	public boolean checkCode(String ddd, String number) {
		String sql = "SELECT * FROM telefone_cliente WHERE ddd = ? and numero = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rst = null;
				
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, ddd);
			pstm.setString(2, number);
			
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
