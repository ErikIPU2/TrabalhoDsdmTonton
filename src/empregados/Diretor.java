package empregados;

import dataTonton.Data;

/**
 * O Diretor é um Gerente com mais responsabilidades ainda
 */
public class Diretor extends Gerente {
    private int departamento;
    private double participaoLucros;

    /**
     * Cria um Diretor
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
     * @param departamento Departamento
     * @param participaoLucros Valor das participações dos lucros
     */
    public Diretor(String name, String password, Data dataDeNacimento, Data dataDeIngresso, int banco, int agencia,
                   int contaCorrente, double bancoDeHoras, double salario, int setor, int nivel, int departamento,
                   double participaoLucros) {
        super(name, password, dataDeNacimento, dataDeIngresso, banco, agencia, contaCorrente, bancoDeHoras, salario,
                setor, nivel);
        this.departamento = departamento;
        this.participaoLucros = participaoLucros;
    }

    /**
     * Retorna o valor do departamento
     * @return Valor do departamento
     */
    public int getDepartamento() {
        return departamento;
    }

    /**
     * Seta um novo valor para o departamento
     * @param departamento Novo valor para o departamento
     */
    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    /**
     * Retorna a participação nos lucros
     * @return Pariticipação nos lucros
     */
    public double getParticipaoLucros() {
        return participaoLucros;
    }

    /**
     * Seta um novo valor para a participação nos lucros
     * @param participaoLucros Novo valor para a participação nos luv
     */
    public void setParticipaoLucros(double participaoLucros) {
        this.participaoLucros = participaoLucros;
    }

    @Override
    public double getBonificação() {
        return (this.getSalarioAnual() * (8 / 100)) + this.participaoLucros;
    }
}
