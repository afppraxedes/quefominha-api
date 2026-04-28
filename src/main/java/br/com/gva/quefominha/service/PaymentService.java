package br.com.gva.quefominha.service;

import br.com.gva.quefominha.domain.payment.DadosCartao;

public interface PaymentService {
    public String pay(DadosCartao dadosCartao);
}