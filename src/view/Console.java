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
	public String inputInfo(String mensagem, String titulo, Boolean exibirTitle, String OK, String CANCEL) {
		String title = exibirTitle ? titulo + "\n\n" : "";
		messageInfo(mensagem, "\n" + title);
		return Util.entrada_S();
	}

	@Override
	public void messageInfo(String mensagem, String titulo) {
		System.out.println("\n" + titulo + mensagem);
	}

	@Override
	public void listarEstudantes(BancoDeDados db, MensagemParaUsuario msgUsuario, String val) {
		List<Estudante> estudantes = Util.listaEstudantes_L(db, val);
		messageInfo(Util.listaEstudantes_S(db, msgUsuario, val, estudantes), "Listagem de todos os estudantes\n\n");
	}

	@Override
	public Integer selecionarEstudante(BancoDeDados db, MensagemParaUsuario msgUsuario) {
		List<Estudante> estudantes = Util.listaEstudantes_L(db, null);
		messageInfo("\nEscolha um estudante:", Util.listaEstudantes_S(db, msgUsuario, null, estudantes));
		String entrada;
		do {
			entrada = Util.entrada_S();
		} while (!Util.isInteger(entrada, 1, estudantes.size()));
		return Integer.parseInt(entrada)-1;
	}
}
