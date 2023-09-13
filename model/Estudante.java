package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.BancoDeDados;

public class Estudante {
	private Integer id;
	private String nome;
	private String curso;
	public final static String TABLE_NAME = "estudantes";
	public final static String COLUM_NOME = "nome";
	public final static String COLUM_CURSO = "curso";
	
//	private static String estudantesListaNomeCurso = null; 
//	private static List<Estudante> estudantes;

	public Estudante(Integer id, String nome, String curso) {
		super();
		this.id = id;
		this.nome = nome;
		this.curso = curso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "id = " + id + " // nome = " + nome + " // curso = " + curso;
	}

	public static ArrayList<Estudante> popularEstudantes(BancoDeDados db, Integer qtEstudantes, Boolean inserirDB) {
		ArrayList<Estudante> estudantes = new ArrayList<>();

		String[] nomes = {"Ana","Luís","Maria","João","Mariana","Carlos","Isabel","Rafael","Sofia","Pedro","Clara","António","Lara","Tiago","Beatriz","Gustavo","Lúcia","Rui","Laura","Miguel","Teresa","André","Catarina","Fernando","Inês","Manuel","Rita","Paulo","Camila","Joaquim","Carolina","Vasco","Marta","Ricardo","Diana","Daniel","Bianca","Hugo","Raquel","Francisco","Vitória","Leonardo","Eva","Gilberto","Cláudia","Simão","Gabriela","Hélder","Amanda","Guilherme"};
		String[] sobrenomes = {"Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Almeida", "Costa", "Pereira", "Carvalho", "Gomes", "Martins", "Araújo", "Rocha", "Ribeiro", "Reis", "Lima", "Fernandes", "Sousa", "Barbosa", "Marques", "Monteiro", "Mendes", "Nunes", "Moreira", "Cardoso", "Teixeira", "Correia", "Dias", "Cruz", "Cavalcante", "Fonseca", "Machado", "Vieira", "Coelho", "Neves", "Pinto", "Freitas", "Moura", "Vargas", "Lopes", "Borges", "Gonçalves", "Pacheco", "Cunha", "Sampaio", "Azevedo"};
		String[] cursos = {"Administração de Empresas","Engenharia Civil","Psicologia","Medicina","Arquitetura e Urbanismo","Direito","Ciências da Computação","Enfermagem","Marketing","Contabilidade","Design Gráfico","Economia","Educação Física","Nutrição","Jornalismo","Biologia","Matemática","Física","Química","História","Geografia","Letras","Ciências Sociais","Filosofia","Sociologia","Pedagogia","Engenharia Elétrica","Engenharia Mecânica","Engenharia de Produção","Administração Pública","Gestão de Recursos Humanos","Farmácia","Odontologia","Ciências da Comunicação","Ciências Ambientais","Artes Cênicas","Música","Dança","Gestão de Projetos","Fotografia","Ciência Política","Relações Internacionais","Teatro","Design de Moda","Engenharia Química","Medicina Veterinária","Gestão de Marketing Digital","Gastronomia","Hotelaria e Turismo","Engenharia de Software"};

		Random r = new Random();

		for (int i = 0; i < qtEstudantes; i++) {
			estudantes.add(new Estudante(i + 1, nomes[r.nextInt(nomes.length)] + " " + sobrenomes[r.nextInt(sobrenomes.length)], cursos[r.nextInt(cursos.length)]));
		}
		
		if(inserirDB) {
			for (Estudante e : estudantes) {
				db.insertData(Estudante.TABLE_NAME, "nome, curso", "'" + e.getNome() + "','" + e.getCurso() + "'");
			}
		}

		return estudantes;
	}

}
