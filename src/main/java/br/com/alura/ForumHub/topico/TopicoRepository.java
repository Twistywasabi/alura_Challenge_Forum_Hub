package br.com.alura.ForumHub.topico;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findAllByStatusTrue(Pageable paginacao);


    Optional<Topico> findByTituloAndMensagemContainingIgnoreCase(@NotBlank String titulo, @NotBlank String mensagem);
}
