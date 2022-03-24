package br.com.ilia.digital.folhadeponto.objects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Objecto de transação de dados para enviar menssagens de erro da API
 * @since 2022-03-24 18:37
 */

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private String mensagem;
}
