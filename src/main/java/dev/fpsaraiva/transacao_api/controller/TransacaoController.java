package dev.fpsaraiva.transacao_api.controller;

import dev.fpsaraiva.transacao_api.application.service.TransacaoService;
import dev.fpsaraiva.transacao_api.controller.dto.TransacaoDTO;
import dev.fpsaraiva.transacao_api.exceptions.ApiErroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> receberTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        if (transacaoDTO == null || transacaoDTO.valor() == null || transacaoDTO.dataHora() == null) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Ambos os campos do DTO são obrigatórios.");
        }

        if (transacaoDTO.dataHora().isAfter(OffsetDateTime.now())) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "A dataHora deve estar no futuro.");
        }

        if (transacaoDTO.valor() < 0) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor deve ser igual ou maior a zero");
        }

        transacaoService.receberTransacao(transacaoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTransacoes() {
        transacaoService.limparTransacoes();
        return ResponseEntity.ok().build();
    }
}
