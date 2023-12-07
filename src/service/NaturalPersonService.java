package service;

import java.sql.SQLException;
import java.util.List;

import dao.NaturalPersonDAO;
import model.entity.NaturalPerson;

public class NaturalPersonService implements GenericService<Long, NaturalPerson> {
private NaturalPersonDAO naturalPersonDao;
	
	public NaturalPersonService() throws ClassNotFoundException {
		this.naturalPersonDao = new NaturalPersonDAO();
	}

	@Override
	public boolean save(NaturalPerson naturalPerson) throws SQLException {
		try {
			this.naturalPersonDao.create(naturalPerson);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean edit(NaturalPerson naturalPerson) throws SQLException {
		try {
			this.naturalPersonDao.update(naturalPerson);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public List<NaturalPerson> show() throws SQLException {
		try {
			return this.naturalPersonDao.list();			
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean remove(Long code) throws SQLException {
		try {
			this.naturalPersonDao.delete(code);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public NaturalPerson findClient(Long code) throws SQLException {
		try {
			return this.naturalPersonDao.searchById(code);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public void removeAll() throws SQLException {
		try {
			this.naturalPersonDao.deleteAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkCode(Long code) {
		return this.naturalPersonDao.checkCode(code);
	}
}
