
import data.BancoDeDados;
import view.Console;
import view.MensagemParaUsuario;
import view.Menu;
import view.OptionPane;
import view.Util;

public class SistemaGerenciamentoEstudantes {
	public static void main(String[] args) {
		MensagemParaUsuario msgUsuario = new Console();
		BancoDeDados db = new BancoDeDados(BancoDeDados.POSTGRE, "localhost", 5432, BancoDeDados.DB_NAME, "postgres",
				"123456");

		Integer op = msgUsuario.optionInfo("Escolha a forma de exibição:", "Modo de Exibição\n\n",
				new String[] { "Console", "Interface" });

		if (op == 2) {
			msgUsuario = new OptionPane();
		}

		Menu m = new Menu(msgUsuario, db);
		m.exibirMenu();
	}
}
