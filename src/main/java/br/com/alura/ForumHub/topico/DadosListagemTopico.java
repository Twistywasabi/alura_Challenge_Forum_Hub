package br.com.alura.ForumHub.topico;


public record DadosListagemTopico( String titulo, String mensagem, String autor, String curso) {

    public DadosListagemTopico(Topico topico) {

        this(topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso());

    }

}
