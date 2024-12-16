package br.com.alura.ForumHub.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @PostMapping
    public void cadastrar(@RequestBody String json) {
        System.out.println(json);
    }

}
