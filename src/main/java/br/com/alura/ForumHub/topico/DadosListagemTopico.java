package br.com.alura.ForumHub.topico;


import java.time.LocalDateTime;

public record DadosListagemTopico(Long id, String titulo, String mensagem, String autor, String curso, LocalDateTime data) {

    public DadosListagemTopico(Topico topico) {

        this(topico.getId() , topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso(), topico.getDataCriacao());

    }

}
