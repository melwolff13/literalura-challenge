package br.com.melissa.literalura.principal;

import br.com.melissa.literalura.dto.Autor;
import br.com.melissa.literalura.dto.Idioma;
import br.com.melissa.literalura.dto.Livro;
import br.com.melissa.literalura.model.RespostaAPI;
import br.com.melissa.literalura.repository.AutorRepository;
import br.com.melissa.literalura.repository.LivroRepository;
import br.com.melissa.literalura.service.ConsumoAPI;
import br.com.melissa.literalura.service.Conversor;
import br.com.melissa.literalura.service.LivroService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    private AutorRepository autorRepositorio;
    private LivroRepository livroRepositorio;
    private LivroService livroService;

    private String endereco = "https://gutendex.com/books/?search=";
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Conversor conversor = new Conversor();

    public Principal() {}

    public Principal(AutorRepository autorRepositorio, LivroRepository livroRepositorio, LivroService livroService) {
        this.autorRepositorio = autorRepositorio;
        this.livroRepositorio = livroRepositorio;
        this.livroService = livroService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    
                    ----------------------------
                    Digite o número da sua opção:
                    1 - buscar livros pelo titulo
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    
                    0 - sair
                    """);
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        buscarLivrosPorTitulo();
                        break;
                    case 2:
                        listarLivrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEmAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("\nEncerrando aplicação...\n");
                        break;
                    default:
                        System.err.println("\nEntrada inválida. Digite o código de uma das opções listadas.\n");
                }
            } catch (InputMismatchException e) {
                System.err.println("\nEntrada inválida. Digite o código de uma das opções listadas.\n");
                scanner.nextLine();
            }
        }
    }

    private RespostaAPI getDadosLivro() {
        System.out.println("\nTitulo:");
        var titulo = scanner.nextLine();
        var url = endereco + titulo.trim().toLowerCase().replace(" ", "+");
        var json = consumoAPI.obterDados(url);
        var resultado = conversor.converte(json, RespostaAPI.class);
        if (!resultado.resultados().isEmpty()) {
            return resultado;
        }
        System.out.printf("\nNenhum livro '%s' encontrado.\n", (titulo));
        return null;
    }

    private void buscarLivrosPorTitulo() {
        var resultado = getDadosLivro();
        if (resultado != null) {
            var livro = livroService.salvarLivro(resultado);
            if (livro != null) {
                System.out.println(livro);
            }
        }
    }

    private void listarLivrosRegistrados() {
        System.out.println("\n----- Livros registrados -----");
        var livrosRegistrados = livroRepositorio.findAll();
        if (!livrosRegistrados.isEmpty()) {
            for (Livro livro : livrosRegistrados) {
                System.out.println("• " + livro);
            }
        } else {
            System.out.println("\nNenhum livro registrado");
        }
    }

    private void listarLivrosPorAutor(Autor autor) {
        var livros = livroRepositorio.findByAutor(autor);
        for (Livro livro : livros) {
            System.out.println("  • " + livro.getTitulo());
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n----- Autores registrados -----");
        var autoresRegistrados = autorRepositorio.findAll();
        if (!autoresRegistrados.isEmpty()) {
            for (Autor autor : autoresRegistrados) {
                System.out.println("► " + autor);
                listarLivrosPorAutor(autor);
            }
        } else {
            System.out.println("\nNenhum autor registrado.");
        }
    }

    private void listarAutoresVivosEmAno() {
        System.out.println("\nAno:");
        try {
            var ano = scanner.nextInt();
            scanner.nextLine();
            var autores = autorRepositorio.listarAutoresVivosEm(ano);

            if (!autores.isEmpty()) {
                System.out.printf("\n----- Autores vivos em %d -----\n", (ano));
                for (Autor autor : autores) {
                    System.out.println("► " + autor);
                }
            } else {
                System.out.println("\nNenhum autor encontrado.");
            }
        } catch (InputMismatchException e) {
            System.err.println("\nEntrada inválida.");
            scanner.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                \nDigite a sigla do idioma:
                
                pt - português
                en - inglês
                es - espanhol
                fr - francês
                """);
        String sigla = scanner.nextLine();
        Idioma idioma = Idioma.fromSigla(sigla);
        if (idioma != null) {
            var livros = livroRepositorio.livrosPorIdioma(idioma);
            if (!livros.isEmpty()) {
                System.out.printf("\n--- Livros em %s ---\n", (idioma));
                for (Livro livro : livros) {
                    System.out.println("• " + livro);
                }
            } else {
                System.out.println("\nNenhum livro em " + idioma + " registrado.");
            }
        }
    }
}


