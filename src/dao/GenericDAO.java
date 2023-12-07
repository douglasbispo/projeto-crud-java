package dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<C,E> {

	public void create(E entity) throws SQLException;
	public void update(E entity) throws SQLException;
	public List<E> list() throws SQLException;
	public void delete(C key) throws SQLException;
	public E searchById(C key) throws SQLException;
	public void deleteAll() throws SQLException;
	
}
