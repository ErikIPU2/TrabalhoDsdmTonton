package empregados;

import dataTonton.Data;

/**
 * O Gerente é um funcionario com um pouco mais de responsabilidades
 */
public class Gerente extends Funcionario {
    private int setor;
    private int nivel;

    /**
     * Cria um Gerente
     * @param name Nome
     * @param password Senha
     * @param dataDeNacimento Data de Nacimentp
     * @param dataDeIngresso Data de ingresso
     * @param banco Numero do banco
     * @param agencia Numero da agencia
     * @param contaCorrente Numero da conta Corrente
     * @param bancoDeHoras Valor de horas trabalhadas
     * @param salario Salario
     * @param nivel Nivel
     * @param setor Setor
     */
    public Gerente(String name, String password, Data dataDeNacimento, Data dataDeIngresso, int banco, int agencia,
                   int contaCorrente, double bancoDeHoras, double salario, int setor, int nivel) {

        super(name, password, dataDeNacimento, dataDeIngresso, banco, agencia, contaCorrente, bancoDeHoras, salario);
        this.setor = setor;
        this.nivel = nivel;
    }

    /**
     * Retorna o valor do setor
     * @return Setor
     */
    public int getSetor() {
        return setor;
    }

    /**
     * Seta um novo valor para o setor
     * @param setor Novo valor para o setor
     */
    public void setSetor(int setor) {
        this.setor = setor;
    }

    /**
     * Pega o nivel desse gerente
     * @return Nivel do gerente
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Seta um novo nivel para esse gerente
     * @param nivel Novo nivel para o gerente
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public double getBonificação() {
        return this.getSalarioAnual() * (5 / 100);
    }
}
