package empregados.control;

public class PasswordCypher {
    public final static int key = 428576;

    public static String cyp(String password) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            builder.append((char) (password.charAt(i) + key));
        }
        return builder.toString();
    }

    public static String desCyp(String password) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            builder.append((char) (password.charAt(i) - key));
        }
        return builder.toString();
    }
}
