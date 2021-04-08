#language: pt
Funcionalidade: Inclusão do cônjuge no cadastro do cliente
  Como Analista comercial
  Gostaria de atualizar um cadastro de cliente, informando o seu cônjuge
  Para que seja contemplada lei estadual Nº 3715/2020 do estado do Tocantins

  Cenario: sacar valor de conta corrente com saldo positivo
    Dado que a conta corrente "8765-4" possua saldo positivo de "150"
    Quando seleciona a conta "8765-4"
    E informado o valor "100" para saque da conta
    E confirmada a transacao
    Entao o saldo da conta e atualizado para "50"

  Cenario: sacar valor de conta corrente com saldo menor que valor desejado
    Dado que a conta corrente "8765-4" possua saldo positivo de "50"
    Quando seleciona a conta "8765-4"
    E informado o valor "100" para saque da conta
    E confirmada a transacao
    Entao se apresenta a mensagem "Saldo Insuficiente"