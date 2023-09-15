package model;

public enum Db_TabelaEstudante {
	COLUMN_NOME(1, "nome"), COLUMN_CURSO(2, "curso");

	private final Integer numColuna;
	private final String nomeColuna;
	public final static String NOME_TABELA = "estudantes";

	private Db_TabelaEstudante(int numColuna, String nomeColuna) {
		this.numColuna = numColuna;
		this.nomeColuna = nomeColuna;
	}

	public Integer getNumColuna() {
		return numColuna;
	}

	public String getNomeColuna() {
		return nomeColuna;
	}

	public static Db_TabelaEstudante obterNomeColuna(Integer numeroColuna) {
		for (Db_TabelaEstudante c : Db_TabelaEstudante.values()) {
			if (numeroColuna == c.getNumColuna()) {
				return c;
			}
		}
		return null;
	}
}
