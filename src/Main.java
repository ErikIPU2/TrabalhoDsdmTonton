import dataTonton.Data;
import dataTonton.DataInvalidaException;
import empregados.control.AutoSaveAndLoader;
import empregados.control.EmpregadosKeeper;
import userInterface.MainMenu;

public class Main {
    public static void main(String[] args) throws DataInvalidaException {
        AutoSaveAndLoader autoSaveAndLoader = new AutoSaveAndLoader();
        autoSaveAndLoader.start();
        new MainMenu();

        autoSaveAndLoader.save(EmpregadosKeeper.get());
        autoSaveAndLoader.stop();
    }
}
