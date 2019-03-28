package empregados.control;

/**
 * Essa classe sempre so devolve um ID unico
 */
public class AutoId {
    private static int idCont = 0;

    /**
     * Retorna um novo id unico
     * @return Id unico
     */
    public static int getNewId() {
        //Retorna o valor do id e adiciona 1
        return idCont++;
    }
}
