import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.io.File;

public class Importar extends JFrame {
    private BufferedReader reader;
    private String fila;
    private String base = "inventario";

    public Importar() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JFileChooser input = new JFileChooser();
        input.showOpenDialog(null);
        File archivo;

        try{
            archivo = input.getSelectedFile();
            leer(String.valueOf(archivo));
            JOptionPane.showMessageDialog(null, "Importado a la base de datos satisfactoriamente");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void leer(String archivo){
        try {
            reader = new BufferedReader(new FileReader(archivo));
            while ( (fila = reader.readLine()) != null ) {
                importar(fila);
            }
            reader.close();
            fila = null;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void importar(String fila) {
        try{
            String db = "jdbc:postgresql://localhost:5432/"+base;
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(db, "postgres", "secret");
            Statement createStatement = connection.createStatement();
            PreparedStatement newStatement = connection.prepareStatement("insert into inventario values (?, ?)");
            String input [] = fila.split(",");
            newStatement.setString(1, input[0]);
            newStatement.setString(2, input[1]);
            newStatement.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
