# bbofxenhander

Baseado no excelente bbvisa2ofx (http://bbvisa2ofx.josecoelho.com/) do @josecoelho.

Este software manipula o OFX exportado para cartes de crédito Banco Brasil
e alterada transaçes de compras parceladas para atualização da data da transaço.

Exemplo:

Compra realizada em 10/01 em 2x, virá nas faturas seguintes da seguinte forma:
 - Fatura de fevereiro: 10/01 - COMPRA PARC 01/02
 - Fatura de março: 10/01 - COMPRA PARC 02/02
 
 Com a utilização do bbofxenhancer, os lançamentos serão transformados para:
  - Fatura de fevereiro: 10/01 - COMPRA PARC 01/02
  - Fatura de março: 10/02 - COMPRA PARC 02/02
 
A ideia é auxiliar quem utiliza programas de controle financeiro (GnuCash, Money, etc.)
a manter um controle de seus gastos mensais.


## Utilização

 1. git clone https://github.com/jfneis/bbofxenhancer.git
 2. cd bbofxenhancer
 3. mvn clean package
 4. java -jar target/bbofxenhancer-0.0.1-SNAPSHOT.jar /tmp/seuofx.ofx
