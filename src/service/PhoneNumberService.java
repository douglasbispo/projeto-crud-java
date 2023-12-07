package service;

import java.sql.SQLException;
import java.util.List;

import dao.PhoneNumberDAO;
import model.entity.PhoneNumber;

public class PhoneNumberService implements GenericService<Long, PhoneNumber> {
	private PhoneNumberDAO phoneNumberDao;
	
	public PhoneNumberService() throws ClassNotFoundException {
		this.phoneNumberDao = new PhoneNumberDAO();
	}

	@Override
	public boolean save(PhoneNumber phoneNumber) throws SQLException {
		try {
			this.phoneNumberDao.create(phoneNumber);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean edit(PhoneNumber phoneNumber) throws SQLException {
		try {
			this.phoneNumberDao.update(phoneNumber);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public List<PhoneNumber> show() throws SQLException {
		try {
			return this.phoneNumberDao.list();			
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean remove(Long code) throws SQLException {
		try {
			this.phoneNumberDao.delete(code);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public PhoneNumber findClient(Long code) throws SQLException {
		try {
			return this.phoneNumberDao.searchById(code);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public void removeAll() throws SQLException {
		try {
			this.phoneNumberDao.deleteAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean checkCode(String ddd, String number) {
		return this.phoneNumberDao.checkCode(ddd, number);
	}

}
