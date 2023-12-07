package service;

import java.sql.SQLException;
import java.util.List;

import dao.CustomerDAO;
import model.entity.Customer;

public class CustomerService implements GenericService<Long, Customer>{
	private CustomerDAO customerDao;
	
	public CustomerService() throws ClassNotFoundException {
		this.customerDao = new CustomerDAO();
	}
	
	@Override
	public boolean save(Customer customer) {
		try {
			this.customerDao.create(customer);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean edit(Customer customer) {
		try {
			this.customerDao.update(customer);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
		
	@Override
	public List<Customer> show() {
		try {
			return this.customerDao.list();			
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public boolean remove(Long code) {
		try {
			this.customerDao.delete(code);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Customer findClient(Long code) throws SQLException {
		try {
			return this.customerDao.searchById(code);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public void removeAll() throws SQLException {
		try {
			this.customerDao.deleteAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkCode(Long code) {
		return this.customerDao.checkCode(code);
	}

}
