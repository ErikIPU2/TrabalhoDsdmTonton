package userInterface;

import java.util.Scanner;

/**
 * Essa classe serve para facilitar e encapsular a criação de interfaces de linha de comando
 */
public class TerminalHelper {

    private static Scanner input = new Scanner(System.in);

    /**
     * Limpa a tela
     */
    public static void clean() {
        //pse ne
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    /**
     * Verifica se uma opção escolhida é valida (Usado para criação de menus)
     * @param minOption valor da opção mininma
     * @param maxOption valor da opção maxima
     * @param option opção escolhida
     * @return true = opção escolhida correta, false = incorreta
     */
    public static boolean verifyOption(int minOption, int maxOption, int option) {
        if (option < minOption || option > maxOption) {
            return false;
        }
        return true;
    }

    /**
     * Cria um menu, que só vai retornar o valor da opção escolhida se ela for valida
     * @param message Mensagens do menu, opções a ser escolhidas, etc...
     * @param prompt Mensagem informada para quando o usuario tiver que digitar o valor
     * @param error Messagem de erro
     * @param minOp Valor de opção minima
     * @param maxOp Valor de opção Maxima
     * @return Opção escolhida
     */
    public static int menu(String message, String prompt, String error, int minOp, int maxOp) {
        int op = 0;
        boolean checker;
        checker = false;

        while (!checker) {
            System.out.println(message);
            System.out.print(prompt);
            op = input.nextInt();
            checker = verifyOption(minOp, maxOp, op);
            if (!checker) {
                System.out.println(error);
            }
        }

        return op;
    }

    /**
     * Pausa o programa, pedindo para o usuario digitar alguma coisa para continuar
     */
    public static void pause() {
        System.out.print("Digite qualquer coisa para continuar: ");
        input.next();
    }

}
