package br.com.melissa.literalura.repository;

import br.com.melissa.literalura.dto.Autor;
import br.com.melissa.literalura.dto.Idioma;
import br.com.melissa.literalura.dto.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT l FROM Livro l WHERE l.idioma = :idioma")
    List<Livro> livrosPorIdioma(Idioma idioma);

    List<Livro> findByAutor(Autor autor);
}
