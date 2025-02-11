package dev.fpsaraiva.transacao_api.controller;

import dev.fpsaraiva.transacao_api.application.service.TransacaoService;
import dev.fpsaraiva.transacao_api.controller.dto.TransacaoDTO;
import dev.fpsaraiva.transacao_api.exceptions.ApiErroException;
import dev.fpsaraiva.transacao_api.exceptions.ErroPadronizado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    @Operation(
            summary = "Cadastrar uma transação",
            description = "Cadastrar uma transação recebida."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Transação possui JSON inválido.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErroPadronizado.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "Transação possui erro de validação.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErroPadronizado.class)))
    })
    @PostMapping
    public ResponseEntity<Void> receberTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        if (transacaoDTO == null || transacaoDTO.valor() == null || transacaoDTO.dataHora() == null) {
            logger.error("ERRO: JSON de transação inválido.");
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "Ambos os campos do DTO são obrigatórios.");
        }

        if (transacaoDTO.dataHora().isAfter(OffsetDateTime.now())) {
            logger.error("ERRO: a dataHora da transação está no futuro.");
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "A dataHora deve estar no passado.");
        }

        if (transacaoDTO.valor() < 0) {
            logger.error("ERRO: o valor da transação é negativo.");
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor deve ser igual ou maior a zero");
        }

        transacaoService.receberTransacao(transacaoDTO);

        logger.info("Transação recebida com SUCESSO!");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Deletar transações",
            description = "Deletar todas as transações."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações removidas com sucesso.")
    })
    @DeleteMapping
    public ResponseEntity<Void> deletarTransacoes() {
        transacaoService.limparTransacoes();

        logger.info("Transações deletadas com sucesso!");

        return ResponseEntity.ok().build();
    }
}
