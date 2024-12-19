package br.com.alura.ForumHub.topico;


public record DadosListagemTopico( Long id, String titulo, String mensagem, String autor, String curso) {

    public DadosListagemTopico(Topico topico) {

        this(topico.getId() , topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso());

    }

}
