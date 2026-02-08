package br.com.melissa.literalura.principal;

import br.com.melissa.literalura.model.Autor;
import br.com.melissa.literalura.model.Livro;
import br.com.melissa.literalura.model.RespostaAPI;
import br.com.melissa.literalura.repository.AutorRepository;
import br.com.melissa.literalura.repository.LivroRepository;
import br.com.melissa.literalura.service.ConsumoAPI;
import br.com.melissa.literalura.service.Conversor;
import br.com.melissa.literalura.service.LivroService;

import java.util.Optional;
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
                    //listarAutoresRegistrados();
                    break;
                case 4:
                    //listarAutoresVivosEmAno();
                    break;
                case 5:
                    //listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("\nEncerrando aplicação...");
                    break;
                default:
                    System.out.println("\nOpção inválida!");
            }
        }
    }

    private void buscarLivrosPorTitulo() {
        System.out.println("Titulo:");
        var titulo = scanner.nextLine();
        var json = consumoAPI.obterDados(endereco + titulo.trim().toLowerCase().replace(" ", "+"));
        var resultado = conversor.converte(json, RespostaAPI.class);
        var livro = livroService.salvarLivro(resultado);

        System.out.println(livro);
    }

    private void listarLivrosRegistrados() {
        var livrosRegistrados = livroRepositorio.findAll();
        livrosRegistrados.forEach(System.out::println);
    }
}
