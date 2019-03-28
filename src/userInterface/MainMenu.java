package userInterface;

import dataTonton.Data;
import dataTonton.DataInvalidaException;
import empregados.Diretor;
import empregados.Funcionario;
import empregados.Gerente;
import empregados.control.EmpregadosKeeper;
import java.util.Scanner;

/**
 * Classe que cria o menu e a interface desse programa
 */
public class MainMenu {

    private Scanner input = new Scanner(System.in);

    /**
     * Mostra a interface
     */
    public MainMenu() {
        this.showLogo();
    }

    /**
     * Mostra o logo
     */
    private void showLogo() {
        int selectedOption;
        boolean execute = true;

        while (execute) {
            TerminalHelper.clean();
            System.out.println("\n");
            System.out.println("Seja bem vindo: \n");
            selectedOption = this.showMainMenu();
            switch (selectedOption) {
                case 1:
                    this.showLoginMenu();
                    break;
                case 2:
                    this.showSignUpMenu();
                    break;
                case 3:
                    execute = false;
                    break;
            }
        }
    }


    /**
     * Menu de opções
     * @return opção escolhida
     */
    private int showMainMenu() {
        TerminalHelper.clean();
        String message = "1. Fazer login\n" +
                "2. Cadastrar\n" +
                "3. Sair\n";

        return TerminalHelper.menu(message, "Digite o que quer fazer: ",
                "Opcao invalida", 1, 3);
    }

    /**
     * Mostra o menu de cadastro
     */
    private void showSignUpMenu() {
        TerminalHelper.clean();
        int seletedOption;
        String message = "1. Funcionario\n" +
                "2. Gerente\n" +
                "3. Diretor";
        seletedOption = TerminalHelper.menu(message, "Digite que tipo de funcionario voce vai ser: ",
                "Opcao invalida", 1, 3);

        switch (seletedOption) {
            case 1:
                this.createFuncionario();
                break;
            case 2:
                this.createGerente();
                break;
            case 3:
                this.createDiretor();
                break;
        }

        TerminalHelper.pause();
    }

    /**
     * Mostra o enu de login
     */
    private void showLoginMenu() {
        TerminalHelper.clean();
        String nome, password;

        System.out.print("Digite o seu nome: ");
        nome = input.next();

        System.out.print("Digite a sua senha: ");
        password = input.next();

        Funcionario user = EmpregadosKeeper.login(nome, password);

        if (user == null) {
            System.out.println("Informacoes incoretas");
            TerminalHelper.pause();
        } else if (user instanceof Diretor) {
            this.showDiretorMenu((Diretor) user);
        } else {
            this.showFuncionarioMenu(user);
        }

    }

    /**
     * Mostra o menu de diretor
     * @param diretor Usuario atual
     */
    private void showDiretorMenu(Diretor diretor) {
        TerminalHelper.clean();
        boolean execute = true;
        while (execute) {
            String message = "1. Ver informacoes pessoais\n" +
                    "2. Editar informacoes pessoais\n" +
                    "3. Editar funcionarios\n" +
                    "4. Mostrar relatorio\n" +
                    "5. Adicionar funcionario\n" +
                    "6. Sair";
            int selectedOption = TerminalHelper.menu(message, "Digite o que deseja fazer: ",
                    "Opcao invalida", 1, 6);
            switch (selectedOption) {
                case 1:
                    this.showFuncionarioInfo(diretor);
                    break;
                case 2:
                    this.showEditMenu(diretor);
                    break;
                case 3:
                    this.showFuncionariosMenuToEdit();
                    break;
                case 4:
                    this.showFuncionariosSalariosLog();
                    break;
                case 5:
                    this.showSignUpMenu();
                    break;
                case 6:
                    execute = false;
                    break;
            }
        }

    }

    /**
     * Menu de um funcionario comun
     * @param funcionario Usuario Atual
     */
    private void showFuncionarioMenu(Funcionario funcionario) {
        TerminalHelper.clean();
        boolean execute = true;
        while (execute) {
            String message = "1. Ver informacoes pessoais\n" +
                    "2. Editar informações pessoais\n" +
                    "3. Sair";
            int selectedOption = TerminalHelper.menu(message, "Digite o que deseja fazer: ",
                    "Opcao invalida", 1, 3);
            switch (selectedOption) {
                case 1:
                    this.showFuncionarioInfo(funcionario);
                    break;
                case 2:
                    this.showEditMenu(funcionario);
                    break;
                case 3:
                    execute = false;
                    break;
            }
        }
    }

    /**
     * Menu de editagem das informações
     * @param funcionario Funcionario a ser editado
     */
    private void showEditMenu(Funcionario funcionario) {
        TerminalHelper.clean();
        boolean execute = true;
        int maxOp = 9;

        while (execute) {
            TerminalHelper.clean();
            String message = "0. Sair\n" +
                    "1. Nome\n" +
                    "2. Senha\n" +
                    "3. Data de Nacimento\n" +
                    "4. Data de Ingresso\n" +
                    "5. Banco\n" +
                    "6. Agencia\n" +
                    "7. Conta Corrente\n" +
                    "8. Banco de horas\n" +
                    "9. Salario";

            if (funcionario instanceof Gerente) {
                message += "\n10. Setor\n" +
                        "11. Nivel";
                maxOp = 11;
            }

            if (funcionario instanceof Diretor) {
                message += "\n12. Departamento\n" +
                        "13. Participacao do lucros";
                maxOp = 13;
            }

            int op = TerminalHelper.menu(message, "Digite o que deseja alterar: ", "Informacao incorreta",
                    0, maxOp);

            switch (op) {
                case 0:
                    execute = false;
                    break;

                case 1:
                    String name;
                    System.out.print("Digite o novo nome(isso mudara o login): ");
                    name = input.next();
                    funcionario.setName(name);
                    break;

                case 2:
                    String newPassword, password;
                    boolean pass = false;
                    while (!pass) {
                        System.out.print("Digite a sua senha antiga: ");
                        password = input.next();
                        System.out.print("Digite a sua senha nova: ");
                        newPassword = input.next();

                        pass = funcionario.setPassword(password, newPassword);

                        if (!pass) {
                            System.out.println("Senha antiga incoreta");
                        }
                    }
                    break;

                case 3:
                    Data dataNacimento = this.dataCreator("Digite a nova data de nacimento:");
                    funcionario.setDataDeNacimento(dataNacimento);
                    break;

                case 4:
                    Data dataIngresso = this.dataCreator("Digite a nova data de ingresso:");
                    funcionario.setDataDeIngresso(dataIngresso);
                    break;

                case 5:
                    int banco;
                    System.out.print("Digite o novo numero do banco: ");
                    banco = input.nextInt();
                    funcionario.setBanco(banco);
                    break;

                case 6:
                    int agencia;
                    System.out.print("Digite o novo numero da agencia: ");
                    agencia = input.nextInt();
                    funcionario.setAgencia(agencia);
                    break;

                case 7:
                    int contaCorrente;
                    System.out.print("Digite o novo numero da conta corrente: ");
                    contaCorrente = input.nextInt();
                    funcionario.setContaCorrente(contaCorrente);
                    break;

                case 8:
                    double bancoHoras;
                    System.out.print("Digite o seu novo valor de banco de horas: ");
                    bancoHoras = input.nextDouble();
                    funcionario.setBancoDeHoras(bancoHoras);
                    break;

                case 9:
                    double salario;
                    System.out.print("Digite o seu novo salario: ");
                    salario = input.nextDouble();
                    funcionario.setSalario(salario);
                    break;

                case 10:
                    int setor;
                    System.out.print("Digite o novo setor: ");
                    setor = input.nextInt();
                    ((Gerente) funcionario).setSetor(setor);
                    break;

                case 11:
                    int nivel;
                    System.out.print("Digite o seu novo nivel: ");
                    nivel = input.nextInt();
                    ((Gerente) funcionario).setNivel(nivel);
                    break;

                case 12:
                    int departamento;
                    System.out.print("Digite o seu novo departamento: ");
                    departamento = input.nextInt();
                    ((Diretor) funcionario).setDepartamento(departamento);
                    break;

                case 13:
                    double participacaoLucros;
                    System.out.print("Digite a sua nova participacao de lucros: ");
                    participacaoLucros = input.nextDouble();
                    ((Diretor) funcionario).setParticipaoLucros(participacaoLucros);
                    break;
            }
        }
    }

    /**
     * Mostra as informações do funcionario
     * @param funcionario Funcionario a ter suas informações expostas
     */
    private void showFuncionarioInfo(Funcionario funcionario) {
        TerminalHelper.clean();
        System.out.println("\nCoisas de funcionario:");
        System.out.println(String.format("Nome = %s", funcionario.getName()));
        System.out.println(String.format("Data de Nacimento = %s", funcionario.getDataDeNacimento().getFormatData()));
        System.out.println(String.format("Data de ingresso = %s", funcionario.getDataDeNacimento().getFormatData()));
        System.out.println(String.format("Banco = %d", funcionario.getBanco()));
        System.out.println(String.format("Agencia = %d", funcionario.getAgencia()));
        System.out.println(String.format("Conta Corrente = %d", funcionario.getContaCorrente()));
        System.out.println(String.format("Banco de horas = %.2f", funcionario.getBancoDeHoras()));
        System.out.println(String.format("Salario = %.2f", funcionario.getSalario()));

        if (funcionario instanceof Gerente) {
            Gerente gerente = (Gerente) funcionario;
            System.out.println("\nCoisas de gerente: ");
            System.out.println(String.format("Setor = %d", gerente.getSetor()));
            System.out.println(String.format("Nivel = %d", gerente.getNivel()));
        }

        if (funcionario instanceof Diretor) {
            Diretor diretor = (Diretor) funcionario;
            System.out.println("\nCoisas de Diretor: ");
            System.out.println(String.format("Departamento = %d", diretor.getDepartamento()));
            System.out.println(String.format("Participacao dos lucros = %.2f", diretor.getParticipaoLucros()));
        }

        System.out.println("\nDados Salariais:");
        System.out.println(String.format("Salario anual = %.2f", funcionario.getSalarioAnual()));
        System.out.println(String.format("Valor da bonificação anual = %.2f", funcionario.getBonificação()));

        TerminalHelper.pause();

    }

    /**
     * Menu que mostra todos os usuario para serem editados
     * (Apenas disponiveis para Diretores)
     */
    private void showFuncionariosMenuToEdit() {
        boolean execute = true;

        while (execute) {
            TerminalHelper.clean();

            Funcionario[] funcionarios = EmpregadosKeeper.get();
            EmpregadosKeeper.sortFuncionarioByLevel(funcionarios);
            StringBuilder temp = new StringBuilder("0. Sair\n");

            for (int i = 0; i < funcionarios.length; i++) {
                temp.append(String.format("%d. %s (%s)\n", (i + 1), funcionarios[i].getName(), this.getFuncionarioType(funcionarios[i])));
            }

            int sel = TerminalHelper.menu(temp.toString(), "Selecione o funcionario que deseja alterar: ",
                    "Funcionario invalido", 0, funcionarios.length);

            if (sel == 0) {
                execute = false;
            } else {
                this.showFuncionariosEdit(funcionarios[sel - 1]);
            }
        }

    }

    /**
     * Menu de opção para um usuario selecionado por um Diretor
     * @param funcionario Funcionario selecionado
     */
    private void showFuncionariosEdit(Funcionario funcionario) {
        boolean execute = true;

        while (execute) {
            String message = "1. Ver informacoes\n" +
                    "2. Editar informacoes\n" +
                    "3. Deleter funcionario\n" +
                    "4. Sair";

            int op = TerminalHelper.menu(message, "Digite o que quer fazer: ", "Opcoes incorreta",
                    1, 4);

            switch (op) {
                case 1:
                    this.showFuncionarioInfo(funcionario);
                    break;
                case 2:
                    this.showEditMenu(funcionario);
                    break;
                case 3:
                    EmpregadosKeeper.remove(funcionario);
                    execute = false;
                    break;
                case 4:
                    execute = false;
                    break;

            }

        }
    }

    /**
     * Retorna o tipo do funcionario
     * @param funcionario Funcionario
     * @return Tipo dele
     */
    private String getFuncionarioType(Funcionario funcionario) {
        if (funcionario instanceof Diretor) {
            return "Diretor";
        } else if (funcionario instanceof Gerente) {
            return "Gerente";
        } else {
            return "Funcionario";
        }
    }

    /**
     * Mostra uma lista de todos os funcionarios com seus salario e bonificações em ordem decresente de salario
     */
    private void showFuncionariosSalariosLog() {
        TerminalHelper.clean();
        StringBuilder temp = new StringBuilder();
        Funcionario[] funcionarios = EmpregadosKeeper.get();
        EmpregadosKeeper.sortFuncionarioByDecrecentSalario(funcionarios);

        for (int i = 0; i < funcionarios.length; i++) {
            temp.append(String.format("%s (%s) :\nSalario = %.2f\nBonificacao = %.2f\n\n", funcionarios[i].getName(),
                    this.getFuncionarioType(funcionarios[i]), funcionarios[i].getSalarioAnual(), funcionarios[i].getBonificação()));
        }

        System.out.println(temp.toString());

        TerminalHelper.pause();
    }

    /**
     * Cria um novo funcionario comun
     */
    private void createFuncionario() {
        int banco, agencia, contaCorrente;
        double bancoDeHoras, salario;
        String nome, password;
        Data dataDeNacimento, dataDeIngresso;
        System.out.print("Digite o seu nome: ");
        nome = this.input.next();

        System.out.print("Digite a sua senha: ");
        password = this.input.next();

        dataDeNacimento = this.dataCreator("Digite a sua data de nacimento:");
        dataDeIngresso = this.dataCreator("Digite a usa data de ingresso:");

        System.out.print("Digite o numero do banco: ");
        banco = input.nextInt();

        System.out.print("Digite o numero da agencia: ");
        agencia = input.nextInt();

        System.out.print("Digite o numero da sua conta corrente: ");
        contaCorrente = input.nextInt();

        System.out.print("Digite o valor do seu banco de horas: ");
        bancoDeHoras = input.nextDouble();

        System.out.print("Digite o valor do seu salario: ");
        salario = input.nextDouble();

        new Funcionario(nome, password, dataDeNacimento, dataDeIngresso, banco, agencia,
                contaCorrente, bancoDeHoras, salario);

        System.out.println("Usuario criado com sucesso");

    }

    /**
     * Cria um novo gerente
     */
    private void createGerente() {
        int banco, agencia, contaCorrente, setor, nivel;
        double bancoDeHoras, salario;
        String nome, password;
        Data dataDeNacimento, dataDeIngresso;
        System.out.print("Digite o seu nome: ");
        nome = this.input.next();

        System.out.print("Digite a sua senha: ");
        password = this.input.next();

        dataDeNacimento = this.dataCreator("Digite a sua data de nacimento:");
        dataDeIngresso = this.dataCreator("Digite a usa data de ingresso:");

        System.out.print("Digite o numero do banco: ");
        banco = input.nextInt();

        System.out.print("Digite o numero da agencia: ");
        agencia = input.nextInt();

        System.out.print("Digite o numero da sua conta corrente: ");
        contaCorrente = input.nextInt();

        System.out.print("Digite o valor do seu banco de horas: ");
        bancoDeHoras = input.nextDouble();

        System.out.print("Digite o valor do seu salario: ");
        salario = input.nextDouble();

        System.out.print("Digite o numero do seu setor: ");
        setor = input.nextInt();

        System.out.print("Digite o numero do seu nivel : ");
        nivel = input.nextInt();

        new Gerente(nome, password, dataDeNacimento, dataDeIngresso, banco, agencia,
                contaCorrente, bancoDeHoras, salario, setor, nivel);

        System.out.println("Usuario criado com sucesso");

    }

    /**
     * Cria um novo diretor
     */
    private void createDiretor() {
        int banco, agencia, contaCorrente, setor, nivel, departamento;
        double bancoDeHoras, salario, participaoLucros;
        String nome, password;
        Data dataDeNacimento, dataDeIngresso;
        System.out.print("Digite o seu nome: ");
        nome = this.input.next();

        System.out.print("Digite a sua senha: ");
        password = this.input.next();

        dataDeNacimento = this.dataCreator("Digite a sua data de nacimento:");
        dataDeIngresso = this.dataCreator("Digite a usa data de ingresso:");

        System.out.print("Digite o numero do banco: ");
        banco = input.nextInt();

        System.out.print("Digite o numero da agencia: ");
        agencia = input.nextInt();

        System.out.print("Digite o numero da sua conta corrente: ");
        contaCorrente = input.nextInt();

        System.out.print("Digite o valor do seu banco de horas: ");
        bancoDeHoras = input.nextDouble();

        System.out.print("Digite o valor do seu salario: ");
        salario = input.nextDouble();

        System.out.print("Digite o numero do seu setor: ");
        setor = input.nextInt();

        System.out.print("Digite o numero do seu nivel : ");
        nivel = input.nextInt();

        System.out.print("Digite o numero do seu departamento : ");
        departamento = input.nextInt();

        System.out.print("Digite o valor da sua participacao dos lucros: ");
        participaoLucros = input.nextDouble();

        new Diretor(nome, password, dataDeNacimento, dataDeIngresso, banco, agencia,
                contaCorrente, bancoDeHoras, salario, setor, nivel, departamento, participaoLucros);

        System.out.println("Usuario criado com sucesso");

    }

    /**
     * Cria uma data
     * @param message Mensagem a ser informada
     * @return Data criada
     */
    private Data dataCreator(String message) {
        int dia, mes, ano;
        Data data;
        while (true) {

            System.out.println(message);
            System.out.println();
            System.out.print("Dia: ");
            dia = input.nextInt();

            System.out.print("Mes: ");
            mes = input.nextInt();

            System.out.print("Ano: ");
            ano = input.nextInt();

            try {
                data = new Data(dia, mes, ano);
                return data;
            } catch (DataInvalidaException e) {
                System.out.print(e.getMessage());
                System.out.println(" Tente novamente");
            }

        }
    }
}
