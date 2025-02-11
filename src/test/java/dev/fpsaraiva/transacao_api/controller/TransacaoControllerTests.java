package dev.fpsaraiva.transacao_api.controller;

import dev.fpsaraiva.transacao_api.application.service.TransacaoService;
import dev.fpsaraiva.transacao_api.controller.dto.TransacaoDTO;
import dev.fpsaraiva.transacao_api.exceptions.ApiErroException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTests {

    private final String EXCEPTION_REASON_CAMPOS_OBRIGATORIOS = "Ambos os campos do DTO são obrigatórios.";
    private final String EXCEPTION_REASON_CAMPO_VALOR = "O valor deve ser igual ou maior a zero";
    private final String EXCEPTION_REACON_CAMPO_DATAHORA = "A dataHora deve estar no futuro.";

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private TransacaoController transacaoController;

    @Test
    void deveCriarTransacaoComSucesso() {
        TransacaoDTO dtoValido = new TransacaoDTO(100.0, OffsetDateTime.now());

        ResponseEntity<Void> resposta = transacaoController.receberTransacao(dtoValido);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        verify(transacaoService, times(1)).receberTransacao(dtoValido);
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoDTONulo() {
        Executable action = () -> transacaoController.receberTransacao(null);
        ApiErroException ex = assertThrows(ApiErroException.class, action);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getHttpStatus());
        assertEquals(EXCEPTION_REASON_CAMPOS_OBRIGATORIOS, ex.getReason());
    }

    @Test
    void deveLancarExcecaoQuandoValorForNulo() {
        TransacaoDTO dtoComValorNulo = new TransacaoDTO(null, OffsetDateTime.now());

        Executable action = () -> transacaoController.receberTransacao(dtoComValorNulo);

        ApiErroException ex = assertThrows(ApiErroException.class, action);
        assertEquals(EXCEPTION_REASON_CAMPOS_OBRIGATORIOS, ex.getReason());
    }

    @Test
    void deveLancarExcecaoQuandoDataHoraForNulo() {
        TransacaoDTO dtoComDataHoraNula = new TransacaoDTO(10.0, null);

        Executable action = () -> transacaoController.receberTransacao(dtoComDataHoraNula);

        ApiErroException ex = assertThrows(ApiErroException.class, action);
        assertEquals(EXCEPTION_REASON_CAMPOS_OBRIGATORIOS, ex.getReason());
    }

    @Test
    void deveLancarExcecaoQuandoValorForNegativo() {
        TransacaoDTO dtoNegativo = new TransacaoDTO(-5.0, OffsetDateTime.now());

        Executable action = () -> transacaoController.receberTransacao(dtoNegativo);

        ApiErroException ex = assertThrows(ApiErroException.class, action);
        assertEquals(EXCEPTION_REASON_CAMPO_VALOR, ex.getReason());
    }

    @Test
    void deveLancarExcecaoQuandoDataHoraEstiverNoFuturo() {
        TransacaoDTO dtoFuturo = new TransacaoDTO(10.0, OffsetDateTime.now().plusDays(1));

        Executable action = () -> transacaoController.receberTransacao(dtoFuturo);

        ApiErroException ex = assertThrows(ApiErroException.class, action);
        assertEquals(EXCEPTION_REACON_CAMPO_DATAHORA, ex.getReason());
    }
}
