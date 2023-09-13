package view;

import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import data.BancoDeDados;
import model.Estudante;

public class OptionPane implements MensagemParaUsuario {

	@Override
	public Integer optionInfo(String mensagem, String titulo, String[] opcoes) {
		return JOptionPane.showOptionDialog(null, mensagem, titulo, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, opcoes, "default");
	}

	@Override
	public String inputInfo(String mensagem, String titulo, String OK, String CANCEL) {
		UIManager.put("OptionPane.cancelButtonText", CANCEL);
		UIManager.put("OptionPane.okButtonText", OK);
		return JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
	}

	@Override
	public void messageInfo(String mensagem, String titulo) {
		UIManager.put("OptionPane.okButtonText", "Voltar");
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
	}

	public void messageInfo(JScrollPane mensagem, String titulo) {
		UIManager.put("OptionPane.okButtonText", "Voltar");
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
	}

	@Override
	public void listarEstudantes(BancoDeDados db, String val) {
		List<Estudante> estudantes = Util.listaEstudantes_L(db, val);
		if (estudantes.isEmpty()) {
			messageInfo("Não há estudantes cadastrados no sistema", "Listagem de todos os estudantes");
		} else {
			JList<String> list = new JList<>(Util.listaEstudantes_S(db, val, estudantes).split("\n"));
			JScrollPane jscrollpane = new JScrollPane(list);
			messageInfo(jscrollpane, "Listagem de todos os estudantes");
		}
	}

	@Override
	public Integer selecionarEstudante(BancoDeDados db) {
		List<Estudante> estudantes = Util.listaEstudantes_L(db, null);
		if (estudantes.isEmpty()) {
			messageInfo("Não há estudantes cadastrados no sistema", "Listagem de todos os estudantes");
		} else {
			JList<String> list = new JList<>(Util.listaEstudantes_S(db, null, estudantes).split("\n"));
			JScrollPane jscrollpane = new JScrollPane(list);

			// Configurar o JOptionPane personalizado
			UIManager.put("OptionPane.okButtonText", "Selecionar");
			UIManager.put("OptionPane.cancelButtonText", "Voltar");

			Integer estudanteSelecionado = null;
			do {
				try {
					// Exibir o JOptionPane personalizado com a JList
					int escolha = JOptionPane.showOptionDialog(null, jscrollpane, "Escolha um estudante",
							JOptionPane.OK_CANCEL_OPTION, // Usar OK_CANCEL_OPTION para adicionar um botão Cancelar
							JOptionPane.PLAIN_MESSAGE, null, null, null);

					// Verificar a escolha do usuário
					if (escolha == JOptionPane.OK_OPTION) {
						// Obter o estudante selecionado
						estudanteSelecionado = list.getSelectedIndex();
						if (estudanteSelecionado != -1) {
							return estudanteSelecionado;
						}
					}
					if (escolha == JOptionPane.CANCEL_OPTION) {
						return null;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} while (estudanteSelecionado == -1);
		}
		return null;
	}
}
