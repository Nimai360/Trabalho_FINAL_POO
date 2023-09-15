package view;

import data.BancoDeDados;

public interface MensagemParaUsuario {
	public Integer optionInfo(String mensagem, String titulo, String[] opcoes);

	public String inputInfo(String mensagem, String titulo, Boolean exibirTitle, String OK, String CANCEL);

	public void messageInfo(String mensagem, String titulo);

	public Integer selecionarEstudante(BancoDeDados db, MensagemParaUsuario msgUsuario);

	public void listarEstudantes(BancoDeDados db, MensagemParaUsuario msgUsuario, String val);
}
