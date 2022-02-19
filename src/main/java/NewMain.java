
import com.global.config.Conexion;
import com.seguridad.models.Usuario;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pideu
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        ResultSet resultSet;
        String sql = "SELECT * from public.iniciarsesion('admin','6oPHTFFfVGug+OZJmNDCFA==')";
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("usrnm"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            conexion.desconectar();
        }
    }

}
