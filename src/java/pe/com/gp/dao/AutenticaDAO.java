package pe.com.gp.dao;


import pe.com.gp.entity.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.util.Util;

public class AutenticaDAO {

    private static final Logger LOGGER = LogManager.getLogger();


    public Usuario autenticaSAP(String usuario, String clave) throws Exception {
        Usuario user = new Usuario();
        Connection cn = new ConectaSQL().connection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql  = "SELECT '09850505' CODIGO,'JOSE LUIS' NOMBRE";
        if (cn != null) {
            try {
                stmt = cn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    user = new Usuario();
                    user.setDocidentidad(rs.getString("CODIGO"));
                    user.setHabilitado("1");
                    user.setExiste(true);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                Util.close(cn);
                Util.close(rs);
                Util.close(stmt);
            }
        }

        return user;
    }
    
    public Usuario  autenticaWEB(String empresa, String usuario, String clave) throws Exception {
        LOGGER.info("<==== Inicio Metodo: listaPartesEnTempo ====>");
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        Usuario user = new Usuario();         
        try {             
            user.setExiste(false);            
            cs = cn.prepareCall("{call web_autentica(?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, usuario); 
            cs.setString(3, clave); 
            cs.execute();
            rs = cs.getResultSet();
            while (rs.next()) {
                    user.setDocidentidad(rs.getString("dni"));
                    user.setCodigo(rs.getString("dni"));
                    user.setFullname(rs.getString("fullname"));
                    user.setNombre(rs.getString("nombre"));
                    user.setNomTienda(rs.getString("nomTienda"));
                    user.setExiste(true);
            }
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
            throw e;
        } finally {
            Util.close(cn);
            Util.close(rs);
        }
        LOGGER.info("<==== Fin Metodo: listaPartesEnTempo ====>");
        return user;
    }
    


}
