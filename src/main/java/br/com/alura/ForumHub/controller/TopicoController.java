package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        Optional<Topico> topicoJaRegistrado = repository.findByTituloAndMensagemContainingIgnoreCase(dados.titulo(), dados.mensagem());
        if (topicoJaRegistrado.isPresent()) {
            return ResponseEntity.badRequest().body("Já existe tópico com mesmo título e mensagem, cadastre outro título ou mensagem");
        } else {
            var topico = new Topico(dados);
            repository.save(topico);
            var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
        }
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        var page = repository.findAllByStatusTrue(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
            var topico = repository.getReferenceById(dados.id());
            topico.atualizarInformacoes(dados);
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

            var topico = repository.getReferenceById(id);
            topico.excluir();
            return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity detalhamentoTopico(@PathVariable Long id) {
            var topico = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

}
