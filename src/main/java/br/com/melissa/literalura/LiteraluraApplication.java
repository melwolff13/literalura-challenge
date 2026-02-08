package br.com.melissa.literalura;

import br.com.melissa.literalura.principal.Principal;
import br.com.melissa.literalura.repository.AutorRepository;
import br.com.melissa.literalura.repository.LivroRepository;
import br.com.melissa.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private AutorRepository autorRepositorio;
	@Autowired
	private LivroRepository livroRepositorio;
	@Autowired
	private LivroService livroService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepositorio, livroRepositorio, livroService);
		principal.exibeMenu();
	}
}
