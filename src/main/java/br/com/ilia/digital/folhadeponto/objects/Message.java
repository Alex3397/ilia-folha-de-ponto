package br.com.ilia.digital.folhadeponto.objects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    /**
     * Mensagem de erro a ser enviada
     * @since 2022-03-23 17:30
     */
    private String mensagem;
}
