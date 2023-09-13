package view;

import data.BancoDeDados;

public interface MensagemParaUsuario {
	public Integer optionInfo(String mensagem, String titulo, String[] opcoes);

	public String inputInfo(String mensagem, String titulo, String OK, String CANCEL);

	public void messageInfo(String mensagem, String titulo);

	public Integer selecionarEstudante(BancoDeDados db);

	public void listarEstudantes(BancoDeDados db, String val);
}
