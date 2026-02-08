package br.com.melissa.literalura.dto;

public enum Idioma {
    PORTUGUES("pt"),
    INGLES("en"),
    FRANCES("fr"),
    ESPANHOL("es");

    private String siglaIdioma;

    Idioma(String siglaIdioma) {
        this.siglaIdioma = siglaIdioma;
    }

    public static Idioma fromSigla(String sigla) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.siglaIdioma.equalsIgnoreCase(sigla)) {
                return idioma;
            }
        }

        System.out.println("Idioma inválido.");
        return null;
    }
}
