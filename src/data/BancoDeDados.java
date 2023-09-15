package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Estudante;

public final class BancoDeDados extends Conexao {

	private Boolean isDataChanged = false;
	public final static String DB_NAME = "sgestudantes_db";

	public BancoDeDados(String db, String local, Integer porta, String banco, String user, String senha) {
		super(db, local, porta, banco, user, senha);
		criarDB(user, senha);
		createTable(Estudante.TABLE_NAME, "id SERIAL PRIMARY KEY, " + Estudante.COLUM_NOME + " VARCHAR(100) NOT NULL, "
				+ Estudante.COLUM_CURSO + " VARCHAR(100)");
	}

	public Boolean isDataChanged() {
		return isDataChanged;
	}

	public void setDataChanged(Boolean isDataChanged) {
		this.isDataChanged = isDataChanged;
	}

	public static void criarDB(String user, String senha) {
		String jdbcUrl = "jdbc:postgresql://localhost:5432/";
		try (Connection connection = DriverManager.getConnection(jdbcUrl, user, senha)) {
			if (connection != null) {

				// Criação do banco de dados
				String createDatabaseSQL = "CREATE DATABASE " + DB_NAME;

				try (Statement statement = connection.createStatement()) {
					statement.execute(createDatabaseSQL);
					System.out.println("Banco de dados " + DB_NAME + " criado com sucesso.");
				} catch (SQLException e) {
				}
			}
		} catch (SQLException e) {
		}
	}

	/*
	 * Tabela - Tabela a ser pesquisada Arguments - Argumentos para filtrar. Ex: id
	 * = 1 complementoQuery - Qualquer coisa que venha depois do WHERE. Ex: ORDER BY
	 * id ASC
	 */
	public List<Estudante> query(String tabela, String arguments, String complementoQuery) {
		String args = (arguments == null || arguments == "") ? "" : "WHERE " + arguments + " ";
		if (complementoQuery == null) {
			complementoQuery = "ORDER BY id ASC";
		}
		String query = "SELECT * FROM " + tabela + " " + args + " " + complementoQuery;
		this.conect();
		if (getConexao() != null) {
			try {
//				System.out.println(query);
				ResultSet rs = getStatment().executeQuery(query);
				ArrayList<Estudante> estudantes = new ArrayList<Estudante>();
				while (rs.next()) {
					Estudante e = new Estudante(rs.getInt("id"), rs.getString("nome"), rs.getString("curso"));
					estudantes.add(e);
				}
				rs.close();
				setDataChanged(false);

				return estudantes;
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				this.disconect();
			}
		}
		return null;
	}

	public Boolean deleteDataById(String table_name, Integer id) {
		String query = "DELETE FROM " + table_name + " WHERE id = " + id;
		return executeQuery(query);
	}
	
	public Boolean deleteAllDataById(String table_name) {
		String query = "DELETE FROM " + table_name;
		return executeQuery(query);
	}

	public Boolean editDataById(String table_name, String field, String value, Integer id) {
		String query = "UPDATE " + table_name + " SET " + field + " = " + value + " WHERE id = " + id;
		return executeQuery(query);
	}

	public Boolean createTable(String table_name, String fields) {
		String query = "CREATE TABLE IF NOT EXISTS " + table_name + "(" + fields + ")";

		try (Connection connection = DriverManager.getConnection(getDriverjdbc(), getUser(), getSenha())) {
			if (connection != null) {

				try (Statement statement = connection.createStatement()) {
					statement.execute(query);
					System.out.println("Tabela " + table_name + " criada com sucesso.");
				} catch (SQLException e) {
				}
			}
		} catch (SQLException e) {
		}
		return executeQuery(query);
	}

	public Boolean dropTable(String table_name) {
		String query = "DROP TABLE " + table_name;
		return executeQuery(query);
	}

	public Boolean insertData(String table_name, String fields, String values) {
		// Estudante.TABLE_NAME, "nome, curso", "'"+e.getNome()+"','"+e.getCurso()+"'");
		String query = "INSERT INTO " + table_name + "(" + fields + ") VALUES (" + values + ")";
		return executeQuery(query);
	}

	private Boolean executeQuery(String query) {
		this.conect();
		if (getConexao() != null) {
			try {
				setDataChanged(true);
				return getStatment().execute(query);
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				this.disconect();
			}
		}
		return false;
	}
}
