package br.com.melissa.literalura.service;

import br.com.melissa.literalura.model.*;
import br.com.melissa.literalura.repository.AutorRepository;
import br.com.melissa.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    @Autowired
    private final LivroRepository livroRepository;
    @Autowired
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Livro salvarLivro(RespostaAPI respostaAPI) {
        DadosLivro dados = respostaAPI.resultados().getFirst();
        DadosAutor dadosAutor = dados.autores().getFirst();

        Autor autor = autorRepository.findByNome(dadosAutor.nome())
                .orElseGet(() -> autorRepository.save(new Autor(dadosAutor)));

        Livro livro = new Livro(dados.titulo(), autor, dados.lingua().getFirst(), dados.downloads());
        livroRepository.save(livro);

        return livro;
    }
}
