package view;

import java.util.List;
import java.util.Scanner;

import data.BancoDeDados;
import model.Estudante;

public abstract class Util {
	private Util() {
	}

	static Scanner sc = new Scanner(System.in);

	/**
	 * Retorna uma String válida (diferente de null, de espaços em branco e de
	 * vazio)
	 */
	public static String entrada_S() {
		String value = null;
		Boolean passou = false;

		while (!passou) {
			if (sc.hasNext()) {
				value = sc.nextLine();
				if (isString(value)) {
					passou = true;
				}
			}
		}
		return value;
	}

	/**
	 * Retorna true se o valor informado (String) é um número inteiro válido
	 */
	public static Boolean isInteger(String num, Integer min, Integer max) {
		try {
			if (num.matches("[0-9]*$") && (Integer.parseInt(num) >= min && Integer.parseInt(num) <= max)) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * Retorna true se o valor informado (String) é um texto válido
	 */
	public static Boolean isString(String texto) {
		if (texto != null && !texto.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public static Boolean isNotnum(String texto) {
		if (!texto.trim().equals("") && texto.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$")) {
			return true;
		}
		return false;
	}

	public static String listaEstudantes_S(BancoDeDados db, MensagemParaUsuario msgUsuario, String arguments,
			List<Estudante> estudantes) {
		Integer cont = 1;
		String listaEstudantes = "";

		for (Estudante e : estudantes) {
			String spaces = formatacaoListaNumeracao(msgUsuario, estudantes, cont);
			listaEstudantes += spaces + cont + ". " + e.getNome() + " - " + e.getCurso() + "\n";
			cont++;
		}
		return listaEstudantes;
	}

	private static String formatacaoListaNumeracao(MensagemParaUsuario msgUsuario, List<Estudante> estudantes,
			Integer cont) {
		StringBuilder espacos = new StringBuilder();
		Integer qtDigitos = (estudantes.size() + "").length() - (cont + "").length();
		for (int i = 0; i < qtDigitos; i++) {
			if (msgUsuario instanceof Console) {
				espacos.append(" ");
			} else {
				espacos.append("  ");
			}
		}
		return espacos.toString();
	}

	public static List<Estudante> listaEstudantes_L(BancoDeDados db, String arguments) {
		return db.query(Estudante.TABLE_NAME, arguments, "ORDER BY " + Estudante.COLUM_NOME + " ASC");
	}
}