package dev.fpsaraiva.transacao_api.controller.dto;

public record EstatisticasResponseDTO(Long count,
                                      Double sum,
                                      Double avg,
                                      Double min,
                                      Double max) {
}
