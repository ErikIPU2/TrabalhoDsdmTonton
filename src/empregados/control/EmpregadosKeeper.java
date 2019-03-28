package empregados.control;

import empregados.Diretor;
import empregados.Funcionario;
import empregados.Gerente;

import java.util.ArrayList;

/**
 * Essa classe serve para manter todas as classes funcionarios instanciadas rastreaveis
 */
public class EmpregadosKeeper {
    private static ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

    /**
     * Adiciona um novo funcionario no keeper
     * @param funcionario Funcionario a ser adicionado
     * @return true = adicionada, false = funcionario ja existe
     */
    public static boolean add(Funcionario funcionario) {
        if (existId(funcionario)) {
            return false;
        } else {
            funcionarios.add(funcionario);
            return true;
        }
    }

    /**
     * Remove um funcionario
     * @param funcionario Funcionario a ser removido
     * @return true = removido, false = não removiod
     */
    public static boolean remove(Funcionario funcionario) {
        if (!existId(funcionario)) {
            return false;
        } else {
            Funcionario temp = funcionarios.remove(getIndex(funcionario));
            return temp != null;
        }
    }

    /**
     * Retorna um array com todos os funcionarios no keeper
     * @return Funcionarios adicionados
     */
    public static Funcionario[] get() {
        Funcionario[] temp = new Funcionario[funcionarios.size()];
        return funcionarios.toArray(temp);
    }

    /**
     * Verifica algum funcionario com o id informado existe
     * @param id id a ser procurado
     * @return true = existe, false = não existe
     */
    public static boolean existId(int id) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica algum funcionario com o id informado existe
     * @param newFuncionario id a ser buscado
     * @return true = existe, false = não existe
     */
    public static boolean existId(Funcionario newFuncionario) {
        return existId(newFuncionario.getId());
    }

    /**
     * Retorna a posição de um objeto com o id informado
     * @param id id a ser buscado
     * @return posição do objeto, -1 == não encontrado
     */
    public static int getIndex(int id) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retorna a posição de um objeto com base a outra classe funcionario
     * @param funcionario objeto a ser buscado
     * @return posição do objeto, -1 == não encontrado
     */
    public static int getIndex(Funcionario funcionario) {
        return getIndex(funcionario.getId());
    }

    /**
     * Simula um login, é passado o nome e a senha e é retornado o primeiro objeto que fecha
     * @param name Nome
     * @param password Senha
     * @return Funcionario logado, null == Dados incorretos
     */
    public static Funcionario login(String name, String password) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getName().equals(name)  && funcionario.checkPassword(password)) {
                return funcionario;
            }
        }
        return null;
    }

    /**
     * Retorna o level de herança de uma classe funcionario (Usado para o sort)
     * @param funcionario Classe a ser pego o nivel
     * @return 1 = funcionario, 2 = gerente, 3 = diretor
     */
    private static int getFuncionarioLevel(Funcionario funcionario) {
        if (funcionario instanceof Diretor) {
            return 3;
        } else if (funcionario instanceof Gerente) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Organiza as classes por ordem de nivel
     * @param arr Vetor de funcinario a ser ordenado
     */
    public static void sortFuncionarioByLevel(Funcionario[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (getFuncionarioLevel(arr[j]) > getFuncionarioLevel(arr[j+1])) {
                    Funcionario temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    /**
     * Organiza as classes em ordem decrecente de salario
     * @param arr Vetor de funcinario a ser ordenado
     */
    public static void sortFuncionarioByDecrecentSalario(Funcionario[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j].getSalarioAnual() > (arr[j+1]).getSalarioAnual()) {
                    Funcionario temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

}
