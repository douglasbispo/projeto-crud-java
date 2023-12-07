package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "postgre";
	
	private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/Crud_BD";
	private static final String JDBC_DRIVER = "org.postgresql.Driver";  
	
	private static ConnectionFactory instance = null;
	
	protected ConnectionFactory(){}
	
	public static ConnectionFactory getInstance() throws ClassNotFoundException{
		if(instance == null){
			Class.forName(JDBC_DRIVER);
			
			instance = new ConnectionFactory();
		}
		return instance;
	}
		
	public Connection createConnetionToPostgreSQL(){		
		Connection conn = null;		
		
		try {	
			conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return conn;
	}	
}
