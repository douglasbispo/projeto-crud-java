package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.entity.NaturalPerson;

public class NaturalPersonDAO implements GenericDAO<Long, NaturalPerson> {
	private static final String CODE = "codigo_cli";
	private static final String CPF = "cpf";
	private static final String FIRST_NAME = "primeiro_nome";
	private static final String LAST_NAME = "sobrenome";
	private static final String BIRTH_DATE = "data_nascimento";
	
	private ConnectionFactory connFactory;
	
	public NaturalPersonDAO() throws ClassNotFoundException {
		this.connFactory = ConnectionFactory.getInstance();
	}

	@Override
	public void create(NaturalPerson naturalPerson) throws SQLException {
		String sql = "INSERT INTO pessoa_fisica(codigo_cli, cpf, primeiro_nome, "
				+ "sobrenome, data_nascimento) VALUES (?, ?, ?, ?, ?)";
		String sql1 = "SELECT * FROM cliente ORDER BY codigo_cli DESC LIMIT 1";

		Connection conn = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		
		ResultSet rst;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			pstm1 = conn.prepareStatement(sql1);
			
			rst = pstm1.executeQuery();
			
			long code = 0;
			while (rst.next()) {
				code = rst.getLong(CODE);
			}
			
			pstm.setLong(1, code);
			pstm.setString(2, naturalPerson.getCpf());
			pstm.setString(3, naturalPerson.getFirstName());
			pstm.setString(4, naturalPerson.getLastName());
			pstm.setDate(5, new Date(naturalPerson.getBirthDate().getTime()));
			
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
	public void update(NaturalPerson naturalPerson) throws SQLException {
		String sql = "UPDATE pessoa_fisica SET primeiro_nome = ?, sobrenome = ?, "
				+ "data_nascimento = ? WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, naturalPerson.getFirstName());
			pstm.setString(2, naturalPerson.getLastName());
			pstm.setDate(3, new Date(naturalPerson.getBirthDate().getTime()));
			
			pstm.setLong(4, naturalPerson.getCode());
			
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
	public List<NaturalPerson> list() throws SQLException {
		String sql = "SELECT * FROM pessoa_fisica";
		
		List<NaturalPerson> people = new ArrayList<NaturalPerson>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			rst = pstm.executeQuery();
			
			while (rst.next()) {
				long code = rst.getLong(CODE);
				String cpf = rst.getString(CPF);
				String firstName = rst.getString(FIRST_NAME);
				String lastName = rst.getString(LAST_NAME);
				Date birthDate = rst.getDate(BIRTH_DATE); 
				
				people.add(new NaturalPerson(code, cpf, firstName, lastName, birthDate));
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
		
		return people;
	}

	@Override
	public void delete(Long code) throws SQLException {
		String sql = "DELETE FROM pessoa_fisica WHERE codigo_cli = ?";
		
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
	public NaturalPerson searchById(Long key) throws SQLException {
		String sql = "SELECT * FROM pessoa_fisica WHERE codigo_cli = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rst = null;
		
		NaturalPerson people = null;
		
		try {
			conn = connFactory.createConnetionToPostgreSQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setLong(1, key);
			
			rst = pstm.executeQuery();
			
			while (rst.next()) {
				long code = rst.getLong(CODE);
				String cpf = rst.getString(CPF);
				String firstName = rst.getString(FIRST_NAME);
				String lastName = rst.getString(LAST_NAME);
				Date birthDate = rst.getDate(BIRTH_DATE);
				
				people = new NaturalPerson(code, cpf, firstName, lastName, birthDate);
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
		return people;
	}

	@Override
	public void deleteAll() throws SQLException {
		String sql = "DELETE FROM pessoa_fisica";
		
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
		String sql = "SELECT * FROM pessoa_fisica WHERE codigo_cli = ?";

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
