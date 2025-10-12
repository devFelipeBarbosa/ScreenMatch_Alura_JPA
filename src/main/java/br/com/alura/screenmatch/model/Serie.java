    package br.com.alura.screenmatch.model;

    import br.com.alura.screenmatch.service.ConsultaGemini;

    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.OptionalDouble;

    @Entity
    @Table(name = "series")
    public class Serie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String titulo;

        private Integer totalTemporadas;
        private Double avaliacao;

        @Enumerated(EnumType.STRING)
        private Categoria genero;

        private String atores;
        private String poster;
        private String sinopse;

        @OneToMany(mappedBy = "serie")
        private List<Episodio> episodios = new ArrayList<>();

        public Serie(){} // construtor padrão

        public Serie(DadosSerie dadosSerie){
            this.titulo = dadosSerie.titulo();
            this.totalTemporadas = dadosSerie.totalTemporadas();
            this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0); // If melhorado para caso não ache valores retornar Zero
            this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim()); // trim ele corta a palavra removendo espaçamentos e o split pega separa em Lista, os dados por vírgula, sendo retornado apenas o primeiro item da lista [0]
            this.atores = dadosSerie.atores();
            this.poster = dadosSerie.poster();
            this.sinopse = ConsultaGemini.obterTraducao(dadosSerie.sinopse()).trim();

        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<Episodio> getEpisodios() {
            return episodios;
        }

        public void setEpisodios(List<Episodio> episodios) {
            this.episodios = episodios;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public Integer getTotalTemporadas() {
            return totalTemporadas;
        }

        public void setTotalTemporadas(Integer totalTemporadas) {
            this.totalTemporadas = totalTemporadas;
        }

        public Double getAvaliacao() {
            return avaliacao;
        }

        public void setAvaliacao(Double avaliacao) {
            this.avaliacao = avaliacao;
        }

        public Categoria getGenero() {
            return genero;
        }

        public void setGenero(Categoria genero) {
            this.genero = genero;
        }

        public String getAtores() {
            return atores;
        }

        public void setAtores(String atores) {
            this.atores = atores;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getSinopse() {
            return sinopse;
        }

        public void setSinopse(String sinopse) {
            this.sinopse = sinopse;
        }

        @Override
        public String toString() {
            return  "genero=" + genero +
                    ", titulo='" + titulo + '\'' +
                    ", totalTemporadas=" + totalTemporadas +
                    ", avaliacao=" + avaliacao +
                    ", atores='" + atores + '\'' +
                    ", poster='" + poster + '\'' +
                    ", sinopse='" + sinopse + '\'';
        }
    }
