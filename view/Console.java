package view;

import java.util.List;
import java.util.Scanner;

import data.BancoDeDados;
import model.Estudante;

public class Console implements MensagemParaUsuario {

	Scanner sc;

	@Override
	public Integer optionInfo(String mensagem, String titulo, String[] opcoes) {
		String op = "";
		for (int i = 0; i < opcoes.length; i++) {
			op += (i + 1) + ". " + opcoes[i] + "\n";
		}
		messageInfo(mensagem + "\n\n" + op, "\n" + titulo);
		String entrada;
		do {
			entrada = Util.entrada_S();
		} while (!Util.isInteger(entrada, 1, opcoes.length));
		return Integer.parseInt(entrada);
	}

	@Override
	public String inputInfo(String mensagem, String titulo, String OK, String CANCEL) {
		messageInfo(mensagem, "\n" + titulo + "\n\n");
		return Util.entrada_S();
	}

	@Override
	public void messageInfo(String mensagem, String titulo) {
		System.out.println("\n" + titulo + mensagem);
	}

	@Override
	public void listarEstudantes(BancoDeDados db, String val) {
		List<Estudante> estudantes = Util.listaEstudantes_L(db, val);
		messageInfo(Util.listaEstudantes_S(db, val, estudantes), "Listagem de todos os estudantes\n\n");
	}

	@Override
	public Integer selecionarEstudante(BancoDeDados db) {
		List<Estudante> estudantes = Util.listaEstudantes_L(db, null);
		messageInfo("\nEscolha um estudante:", Util.listaEstudantes_S(db, null, estudantes));
		String entrada;
		do {
			entrada = Util.entrada_S();
		} while (!Util.isInteger(entrada, 1, Util.listaEstudantes_L(db, null).size()));
		return Integer.parseInt(entrada)-1;
	}
}
