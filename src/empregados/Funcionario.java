package empregados;

import dataTonton.Data;
import empregados.control.AutoId;
import empregados.control.EmpregadosKeeper;
import empregados.control.PasswordCypher;

/**
 * Classe Funcionario
 * Serve para tudo que é empregado
 */
public class Funcionario {
    private String name;
    private String password;
    private Data dataDeNacimento;
    private Data dataDeIngresso;
    private int banco;
    private int agencia;
    private int contaCorrente;
    private double bancoDeHoras;
    private double salario;
    private int id;

    /**
     * Cria um funcionario
     * @param name Nome
     * @param password Senha
     * @param dataDeNacimento Data de Nacimentp
     * @param dataDeIngresso Data de ingresso
     * @param banco Numero do banco
     * @param agencia Numero da agencia
     * @param contaCorrente Numero da conta Corrente
     * @param bancoDeHoras Valor de horas trabalhadas
     * @param salario Salario
     */
    public Funcionario(String name, String password, Data dataDeNacimento, Data dataDeIngresso, int banco, int agencia,
                       int contaCorrente, double bancoDeHoras, double salario) {
        this.name = name;
        this.password = password;
        this.dataDeNacimento = dataDeNacimento;
        this.dataDeIngresso = dataDeIngresso;
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
        this.bancoDeHoras = bancoDeHoras;
        this.salario = salario;

        //Usa a Classe AutoId para pegar sempre um valor de ID unico
        this.id = AutoId.getNewId();
        //Todos os Funcionarios devem ser adicionados no Keeper, para se poder ter um controle sobre ele
        EmpregadosKeeper.add(this);
    }

    /**
     * Retorna nome do funcionario
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Seta o nome do funcionario
     * @param name novo nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna a data de nacimento
     * @return Data de nacimento
     */
    public Data getDataDeNacimento() {
        return dataDeNacimento;
    }

    /**
     * Seta a data de Nacimento
     * @param dataDeNacimento Nova data de nacimento
     */
    public void setDataDeNacimento(Data dataDeNacimento) {
        this.dataDeNacimento = dataDeNacimento;
    }

    /**
     * Retorna a data de ingresso
     * @return Data de ingresso
     */
    public Data getDataDeIngresso() {
        return dataDeIngresso;
    }

    /**
     * Seta a data de ingresso
     * @param dataDeIngresso Nova data de ingresso
     */
    public void setDataDeIngresso(Data dataDeIngresso) {
        this.dataDeIngresso = dataDeIngresso;
    }

    /**
     * Retorna o numero do banco
     * @return Numero do banco
     */
    public int getBanco() {
        return banco;
    }

    /**
     * Seta um novo numero do banco
     * @param banco Novo numero do banco
     */
    public void setBanco(int banco) {
        this.banco = banco;
    }

    /**
     * Retorna o numero agencia
     * @return Numero da agencia
     */
    public int getAgencia() {
        return agencia;
    }

    /**
     * Define um novo numero da agencia
     * @param agencia Novo numero da agencia
     */
    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    /**
     * Retorna o numero da Conta Corrente
     * @return Numero da conta corrente
     */
    public int getContaCorrente() {
        return contaCorrente;
    }

    /**
     * Seta um novo numero da conta corrente
     * @param contaCorrente Novo numero da conta corrente
     */
    public void setContaCorrente(int contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    /**
     * Retorna o valor do banco de horas
     * @return Valor do banco de horas
     */
    public double getBancoDeHoras() {
        return bancoDeHoras;
    }

    /**
     * Seta um novo valor para o banco de horas
     * @param bancoDeHoras Novo valor para o banco de horas
     */
    public void setBancoDeHoras(double bancoDeHoras) {
        this.bancoDeHoras = bancoDeHoras;
    }

    /**
     * Retorna o valor do salario
     * @return Salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * Seta o novo valor do salario
     * @param salario Novo valor do salario
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * Retorna o ID desse funcionario
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Verifica se uma senha mandada como parametro fecha com a senha do funcionario (Permite brechas)
     * @param password Senha a ser verificada
     * @return true = senha batem, false = senhas diferentes
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Define uma nova senha
     * É preciso informar a senha antiga corretamente para poderem ser trocadas
     * @param oldPassword Senha antiga
     * @param password Senha nova
     * @return true = trocado com sucesso, false = senha antiga incorreta
     */
    public boolean setPassword(String oldPassword, String password) {
        if (oldPassword.equals(this.password)) {
            this.password = password;
            return true;
        }
        return false;
    }

    /**
     * Retorna a senha criptografada
     * @return Senha criptografada
     */
    public String getPassword() {
        return PasswordCypher.cyp(this.password);
    }

    /**
     * Seta uma nova senha, sendo ela criptografada
     * Esse metodo só sera usada para carregar as informações do json
     * @param password Senha criptografada
     */
    public void setPassword(String password) {
        this.password = PasswordCypher.desCyp(password);
    }

    /**
     * Retorna o calculo do salario do ano
     * @return Valor do salario
     */
    public double getSalarioAnual() {
        return this.salario * this.bancoDeHoras;
    }

    /**
     * Retorna o calculo das bonificações do ano
     * @return Valor da bonificação
     */
    public double getBonificação() {
        return this.getSalarioAnual() * (2.5 / 100);
    }
}
