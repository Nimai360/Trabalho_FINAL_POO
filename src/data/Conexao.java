package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	private String local;
	private String user;
	private String senha;
	private Connection conexao;
	private Statement statment;
	private String str_conexao;
	private String driverjdbc;

	public final static String POSTGRE = "PostgreSql";
	public final static String MYSQL = "MySql";

	protected Conexao(String db, String local, Integer porta, String banco, String user, String senha) {

		this.local = local;
		configUser(user, senha);

		switch (db) {
		case POSTGRE:
			setStr_conexao("jdbc:postgresql://" + local + ":" + porta + "/" + banco);
			setDriverjdbc("org.postgresql.Driver");
			break;

		case MYSQL:
			setStr_conexao("jdbc:mysql://" + local + ":" + porta + "/" + banco);
			setDriverjdbc("com.mysql.jdbc.Driver");
			break;
		}
	}

	public void configUser(String user, String senha) {
		setUser(user);
		setSenha(senha);
	}

	public void configLocal(String banco) {
		setLocal(banco);
	}

	// Conexão com o Banco de Dados
	public void conect() {
		try {
			Class.forName(getDriverjdbc());
			setConexao(DriverManager.getConnection(getStr_conexao(), getUser(), getSenha()));
			setStatment(getConexao().createStatement());
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	public void disconect() {
		try {
			getConexao().close();
		} catch (SQLException ex) {
			System.err.println(ex);
			ex.printStackTrace();
		}
	}

	// GETs AND SETs

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection c) {
		this.conexao = c;
	}

	public Statement getStatment() {
		return statment;
	}

	public void setStatment(Statement statment) {
		this.statment = statment;
	}

	public String getStr_conexao() {
		return str_conexao;
	}

	public void setStr_conexao(String str_conexao) {
		this.str_conexao = str_conexao;
	}

	public String getDriverjdbc() {
		return driverjdbc;
	}

	public void setDriverjdbc(String driverjdbc) {
		this.driverjdbc = driverjdbc;
	}
}
