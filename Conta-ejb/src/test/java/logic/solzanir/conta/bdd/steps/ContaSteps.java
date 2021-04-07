package logic.solzanir.conta.bdd.steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import logic.solzanir.conta.models.Conta;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ContaSteps {

    private Conta contaDB;
    private Conta contaTest;
    private Double valorSaque;

    @Dado("^que a conta corrente \"(.*?)\" possua saldo positivo de \"(.*?)\"$")
    public void queAContaCorrentePossuaSaldoPositivoDe(String nomeConta, Double saldo) {
        contaDB = new Conta();
        contaDB.setId(1);
        contaDB.setData(new Date());
        contaDB.setNome(nomeConta);
        contaDB.setValor(saldo);
    }

    @Quando("^seleciona a conta \"(.*?)\"$")
    public void selecionaAConta(String nomeConta) {
        if (contaDB.getNome().equalsIgnoreCase(nomeConta)) {
            contaTest = contaDB;

        } else {
            throw new IllegalArgumentException("A conta informada é diferente da mockada");
        }
    }

    @Quando("^informado o valor \"(.*?)\" para saque da conta$")
    public void informadoOValorParaSaqueDaConta(Double valorSaque) {
        this.valorSaque = valorSaque;
    }

    @Quando("^confirmada a transacao$")
    public void confirmadaATransacao() {
        double novoSaldo = contaTest.getValor() - this.valorSaque;
        contaTest.setValor(novoSaldo);
    }

    @Entao("^o saldo da conta e atualizado para \"(.*?)\"$")
    public void oSaldoDaContaEAtualizadoPara(Double saldoFinal) {
        assertEquals(saldoFinal, contaTest.getValor());
    }

    @Entao("^se apresenta a mensagem \"(.*?)\"$")
    public void seApresentaAMensagem(String mensagem) throws Throwable {
        assertEquals("Saldo Insuficiente", mensagem);
    }
}
