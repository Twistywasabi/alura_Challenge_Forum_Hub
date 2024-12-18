package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.topico.DadosCadastroTopico;
import br.com.alura.ForumHub.topico.DadosListagemTopico;
import br.com.alura.ForumHub.topico.Topico;
import br.com.alura.ForumHub.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {

        repository.save(new Topico(dados));
    }

    @GetMapping
    public List<DadosListagemTopico> listar() {
        return repository.findAll().stream().map(DadosListagemTopico::new).toList();
    }

}
