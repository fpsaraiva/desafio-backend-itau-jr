package dev.fpsaraiva.transacao_api.controller.dto;

import java.time.OffsetDateTime;

public record TransacaoDTO(Double valor, OffsetDateTime dataHora) {
}
