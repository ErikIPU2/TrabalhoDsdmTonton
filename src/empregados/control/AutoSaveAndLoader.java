package empregados.control;

import dataTonton.Data;
import empregados.Diretor;
import empregados.Funcionario;
import empregados.Gerente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Essa classe faz todo o trabalho de carregar e salvar os dados de funcionario de um arquivo Json já predefinido
 * cria uma thread em segundo plano para fazer o auto-salvamento
 */
public class AutoSaveAndLoader extends Thread {
   private File file = new File("./p4J3myCdZhLqgnBDJVgjttzC5qkycxeaFjggQkddDXg9u4rBN9juGSHQXn4AzQxd3AcFkwaQBnNB2EAeZ87nyaMjRPL2bEnxtT9EFnxWCAtgEQYRyGRRsP4XtzGJXygs.json");

    /**
     * Verifica se o arquivo existe, se existe os dados nele, se não cria um novo arquivo
     */
    public AutoSaveAndLoader() {
        if (!file.exists()) {
            try {
                //Cria o arquivo
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("a");
            }
        } else {
            //Faz o carregamento dos arquivos
            this.load();
        }
    }

    /**
     * Faz o auto-salvamento dos dados
     * Eles são definidos para serem salvos a cada minuto
     */
    @Override
    public void run() {
        while (true) {
            try {
                //Salva os dados
                this.save(EmpregadosKeeper.get());
                //Pausa a Thread por 1 minuto
                Thread.sleep( 60 * 1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Faz o carregamento dos dados do Json
     */
    private void load() {
        //Parser do Json
        JSONParser parser = new JSONParser();
        try {
            //Cria um array com todos os objetos
            FileReader reader = new FileReader(this.file);
            JSONArray array = (JSONArray) parser.parse(reader);

            //Percorre o array e carrega o objeto
            for (int i = 0; i < array.size(); i++) {
                this.loadObject((JSONObject) array.get(i));
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cria uma nova classe Funcionario e portanto ela é adicionada no {@link EmpregadosKeeper}
     * @param object Objeto a ser carregado
     */
    private void loadObject(JSONObject object) {
        //Define todos os dados que a classe funcionario e seus extensões
        String name, password, type;
        String dataDeNacimento, dataDeIngresso;
        int banco, agencia, contaCorrente, setor, nivel, departamento;
        double participaoLucros, bancoDeHoras, salario;

        //O tipo diz se o objeto vai ser funcionario, gerente ou diretor
        type = (String) object.get("type");

        //Carrega todos os dados
        name = (String) object.get("name");
        password = (String) object.get("password");
        dataDeNacimento = (String) object.get("dataDeNacimento");
        dataDeIngresso = (String) object.get("dataDeIngresso");
        banco = (int) ((long) object.get("banco"));
        agencia = (int) ((long) object.get("agencia"));
        contaCorrente = (int) ((long) object.get("contaCorrente"));
        bancoDeHoras = (Double) object.get("bancoDeHoras");
        salario = (Double) object.get("salario");

        //Se ele for um gerente, carrega mais os dados do gerente e cria o objeot
        if (type.equals("gerente")) {
            setor = (int) ((long) object.get("setor"));
            nivel = (int) ((long) object.get("nivel"));

            new Gerente(name, PasswordCypher.desCyp(password), Data.formatData(dataDeNacimento),
                    Data.formatData(dataDeIngresso), banco, agencia, contaCorrente, bancoDeHoras, salario, setor, nivel);
        } else if (type.equals("diretor")) {
            //A mesma coisa para o Diretor
            setor = (int) ((long) object.get("setor"));
            nivel = (int) ((long) object.get("nivel"));
            departamento = (int) ((long) object.get("departamento"));
            participaoLucros = (Double) object.get("participaoLucros");

            new Diretor(name, PasswordCypher.desCyp(password), Data.formatData(dataDeNacimento),
                    Data.formatData(dataDeIngresso), banco, agencia, contaCorrente, bancoDeHoras, salario, setor, nivel,
                    departamento, participaoLucros);
        } else if (type.equals("funcionario")) {
            //E tambem para um funcionario simples
            new Funcionario(name, PasswordCypher.desCyp(password), Data.formatData(dataDeNacimento),
                    Data.formatData(dataDeIngresso), banco, agencia, contaCorrente, bancoDeHoras, salario);
        }
    }

    /**
     * Salva os dados no arquivo Json
     * @param funcionarios Vetor de dados
     */
    public void save(Funcionario[] funcionarios) {
        //Cria um vetor, percorre funcionarios e cada iteração adicona o vetor
       JSONArray jsonArray = new JSONArray();
       for (Funcionario funcionario : funcionarios) {
           jsonArray.add(gerateObject(funcionario));
       }

       //Escreve no arquivo o Json
       try {
           FileWriter writer = new FileWriter(this.file);
           writer.write(jsonArray.toString());
           writer.close();
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }

    /**
     * Gera um Objeto Json com base em uma classe funcionario
     * @param funcionario Funcionario a ser convertido para Objeto Json
     * @return Objeto Json
     */
   private JSONObject gerateObject(Funcionario funcionario) {
       //Define inicialmente o tipo como funcionario
       String type = "funcionario";

       //Cria o Objeto Json e adiciona os dados do funcionarios
       JSONObject object = new JSONObject();
       object.put("name", funcionario.getName());
       object.put("password", funcionario.getPassword());
       object.put("dataDeNacimento", funcionario.getDataDeNacimento().getFormatData());
       object.put("dataDeIngresso", funcionario.getDataDeIngresso().getFormatData());
       object.put("banco", funcionario.getBanco());
       object.put("agencia", funcionario.getAgencia());
       object.put("contaCorrente", funcionario.getContaCorrente());
       object.put("bancoDeHoras", funcionario.getBancoDeHoras());
       object.put("salario", funcionario.getSalario());

       //Se ele for do tipo Gerente, adicona mais os dados do gerente e define o tipo como gerente
       if (funcionario instanceof Gerente) {
           Gerente gerente = (Gerente) funcionario;
           object.put("setor", gerente.getSetor());
           object.put("nivel", gerente.getNivel());
           type = "gerente";
       }

       //O mesmo com o diretor
       if (funcionario instanceof Diretor) {
           Diretor diretor = (Diretor) funcionario;
           object.put("departamento", diretor.getDepartamento());
           object.put("participaoLucros", diretor.getParticipaoLucros());
           type = "diretor";
       }

       //Coloca por fim o tipo do Objeto e retorna ele
       object.put("type", type);

       return object;
   }
}
