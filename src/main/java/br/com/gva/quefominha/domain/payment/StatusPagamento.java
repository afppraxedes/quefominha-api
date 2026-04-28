package br.com.gva.quefominha.domain.payment;

public enum StatusPagamento {
    AUTORIZADO("Autorizado"),
    NAO_AUTORIZADO("Não autorizado pela instiuição financeira"),
    CARTAO_INVALIDO("Cartão inválido ou bloqueado");

    String descricao;

    StatusPagamento(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}