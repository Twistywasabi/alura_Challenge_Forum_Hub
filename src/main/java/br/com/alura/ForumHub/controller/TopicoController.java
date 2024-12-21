package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        Optional<Topico> topicoJaRegistrado = repository.findByTituloAndMensagemContainingIgnoreCase(dados.titulo(), dados.mensagem());
        if (topicoJaRegistrado.isPresent()) {
            System.out.println("Já existe tópico com mesmo título e mensagem, cadastre outro título ou mensagem");
        } else {
            repository.save(new Topico(dados));
        }
    }

    @GetMapping
    public Page<DadosListagemTopico> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        return repository.findAllByStatusTrue(paginacao).map(DadosListagemTopico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        Optional<Topico> topicoExiste =  repository.findById(dados.id());
        if(topicoExiste.isPresent()){
            var topico = repository.getReferenceById(dados.id());
            topico.atualizarInformacoes(dados);
        } else {
            System.out.println("Não existe um tópico com esse ID, tente outro.");
        }

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        Optional<Topico> topicoExiste =  repository.findById(id);
        if(topicoExiste.isPresent()){

        var topico = repository.getReferenceById(id);
        topico.excluir();
        } else {
            System.out.println("Nada foi excluído, não existe um tópico com esse ID, tente outro.");
        }
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoTopico detalhamentoTopico(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        return new DadosDetalhamentoTopico(topico);
    }


}
