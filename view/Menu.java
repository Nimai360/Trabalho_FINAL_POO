package view;

import java.util.ArrayList;
import java.util.List;

import data.BancoDeDados;
import model.Db_TabelaEstudante;
import model.Estudante;

public final class Menu {
	private BancoDeDados db;

	private final MensagemParaUsuario msgUsuario;

	public Menu(MensagemParaUsuario msgUsuario, BancoDeDados db) {
		super();
		this.msgUsuario = msgUsuario;
		this.db = db;
	}

	public void exibirMenu() {
		List<String> menuOptions = new ArrayList<>();
		menuOptions.add("Adicionar Estudante");
		menuOptions.add("Editar Estudante");
		menuOptions.add("Remover Estudante");
		menuOptions.add("Listar Estudante");
		menuOptions.add("Sair");

		boolean sair = false;

		while (!sair) {
			Integer op = null;
			op = mainMenu(menuOptions);

			switch (op) {
			case 1:
				adicionarEstudante();
				break;
			case 2:
				editarEstudante();
				break;
			case 3:
				removerEstudante();
				break;
			case 4:
				listarEstudantes();
				break;
			case 5:
				if (msgUsuario instanceof Console) {
					sair = true;
					exitProgram();
				}
				break;
			default:
			}
		}
	}

	private void exitProgram() {
		if (db != null)
			try {
				db.disconect();
			} catch (Exception e) {
			}
		if (msgUsuario instanceof Console) {
			System.out.println("Encerrando...\n\nAté a próxima!");
		}
		System.exit(0);
	}

	private Integer mainMenu(List<String> menuOptions) {
		String texto = "";
		int i = 0;
		for (String op : menuOptions) {
			i++;
			if (msgUsuario instanceof OptionPane && op == menuOptions.get(menuOptions.size() - 1)) {
				break;
			}
			texto += i + ". " + op + "\n";
		}

		String op;
		do {
			op = msgUsuario.inputInfo(texto, "Gerenciador de Estudantes", true, "Confirmar", "Sair");
			if (op == null) {
				exitProgram();
			}
//			icon the Icon image to displayselectionValues an array of Objects that gives the possible selectionsinitialSelectionValue the value used to initialize the inputfield
		} while (!Util.isInteger(op, 1, i));

		return Integer.parseInt(op);
	}

	private void adicionarEstudante() {
		Integer op = msgUsuario.optionInfo("Como gostaria de adicionar o estudante:", "Cadastro de estudante\n\n",
				new String[] { "Manualmente", "Auto +50 estudantes" });
		if (msgUsuario instanceof Console) {
			op--;
		}
		switch (op) {
		case 0:
			String nome = msgUsuario.inputInfo("Informe o nome do estudante", "Cadastro de Estudante", true, "Avançar",
					"Voltar ao menu");
			if (Util.isString(nome)) {
				String curso = msgUsuario.inputInfo("Informe o curso do estudante", "Cadastro de Estudante", false,
						"Confirmar", "Voltar");
				if (Util.isString(curso)) {
					// Só pra dizer que usamos ENUM
					db.insertData(Db_TabelaEstudante.NOME_TABELA,
							Db_TabelaEstudante.COLUMN_NOME.getNomeColuna() + ", "
									+ Db_TabelaEstudante.COLUMN_CURSO.getNomeColuna(),
							"'" + nome + "', '" + curso + "'");

//					db.insertData(Estudante.TABLE_NAME, Estudante.COLUM_NOME + ", " + Estudante.COLUM_CURSO,
//							"'" + nome + "', '" + curso + "'");
				}
			}
			break;
		case 1:
			Estudante.popularEstudantes(db, 50, true); // mude para true caso queira adicionar ao postgre
			break;
		}
	}

	private void editarEstudante() {
		Integer estudante;
		estudante = msgUsuario.selecionarEstudante(db, msgUsuario);
		List<Estudante> estudantes = Util.listaEstudantes_L(db, null);
		String coluna = null, valor = null;

		if (estudante != null) {
			Integer op = msgUsuario.optionInfo(
					"Qual informação gostaria de alterar de " + estudantes.get(estudante).getNome() + " ?",
					"Editar estudante\n\n", new String[] { "Nome", "Curso" });
			if (msgUsuario instanceof Console) {
				op--;
			}

			switch (op) {
			case 0:
				String nome = msgUsuario.inputInfo("Mudar o nome de " + estudantes.get(estudante).getNome() + " para:",
						"Alterção do nome de cadastro", true, "Confirmar", "Voltar");
				coluna = Estudante.COLUM_NOME;
				valor = nome;
				break;
			case 1:
				String curso = msgUsuario.inputInfo(
						"Mudar o curso de " + estudantes.get(estudante).getCurso() + " para:",
						"Alteração de cadastro do curso", true, "Confirmar", "Voltar");
				coluna = Estudante.COLUM_CURSO;
				valor = curso;
				break;
			}
		}

		if (estudante != null && coluna != null && valor != null && valor.trim() != "") {
			try {
				db.editDataById(Estudante.TABLE_NAME, coluna, "'" + valor + "'", estudantes.get(estudante).getId());
				msgUsuario.messageInfo(
						"Estudante " + estudantes.get(estudante).getNome() + " atualizado com sucesso!\n\n",
						"Atualização realizada\n\n");
			} catch (Exception e) {
				msgUsuario.messageInfo("Erro ao editar o estudante.", "Erro na atualização");
			}
		}
	}

	private void removerEstudante() {
		Integer op = msgUsuario.optionInfo("Quantos estudantes gostaria de remover?", "Remoção de estudante\n\n",
				new String[] { "Apenas 1", "Todos" });
		if (msgUsuario instanceof Console) {
			op--;
		}
		switch (op) {
		case 0:
			Integer estudante = msgUsuario.selecionarEstudante(db, msgUsuario);
			List<Estudante> estudantes = Util.listaEstudantes_L(db, null);

			if (estudante != null) {
				db.deleteDataById(Estudante.TABLE_NAME, (estudantes.get(estudante).getId()));
				msgUsuario.messageInfo(estudantes.get(estudante).getNome() + " deletado com sucesso!\n\n",
						"Atualização realizada\n\n");
			}
			break;
		case 1:
			db.deleteAllDataById(Estudante.TABLE_NAME);
			break;
		}
	}

	private void listarEstudantes() {
		String coluna = null, valor = null, argumento = null;

		Integer op = msgUsuario.optionInfo("Quais estudantes gostaria de visualizar?", "Editar estudante\n\n",
				new String[] { "Todos", "Pesquisar por nome", "Pesquisar por curso" });
		if (msgUsuario instanceof Console) {
			op--;
		}

		switch (op) {
		case 0: // Todos
			argumento = "";
			break;
		case 1: // Pesquisar por nome
			String nome = msgUsuario.inputInfo("Informe o nome do estudante desejado:", "Buscar estudante por nome",
					true, "Confirmar", "Voltar");
			coluna = Estudante.COLUM_NOME;
			valor = nome;
			break;
		case 2: // Pesquisar por curso
			String curso = msgUsuario.inputInfo("Informe o curso desejado:", "Buscar estudante por curso", true,
					"Confirmar", "Voltar");
			coluna = Estudante.COLUM_CURSO;
			valor = curso;
			break;
		}
		if (Util.isString(valor)) {
			argumento = "LOWER(" + coluna + ") LIKE '%" + valor.toLowerCase() + "%'";
		}
		if (argumento != null) {
			msgUsuario.listarEstudantes(db, msgUsuario, argumento);
		}
	}
}