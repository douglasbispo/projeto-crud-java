package service;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<C,E> {
	
	public boolean save(E entity) throws SQLException;
	public boolean edit(E entity) throws SQLException;
	public List<E> show() throws SQLException;
	public boolean remove(C key) throws SQLException;
	public E findClient(C key) throws SQLException;
	public void removeAll() throws SQLException;

}
