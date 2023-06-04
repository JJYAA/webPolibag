package pe.com.gp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.gp.connection.ConectaSQL;

import pe.com.gp.entity.Global;
import pe.com.gp.util.Util;

public class GlobalDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    
    public Global  getDatosGlobales(String empresa) throws Exception {
        LOGGER.info("<==== Inicio Metodo: listaPartesEnTempo ====>");
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        Global obj = new Global();      
        try {        
            cs = cn.prepareCall("{call sp_datosglobales()}");
            cs.execute();
            rs = cs.getResultSet();
            while (rs.next()) {
                    obj.setPathServidor(rs.getString("rutaservidor"));
                    obj.setTipoCambioDolar(rs.getDouble("tipocambiodolar"));
                    obj.setExiste(true);
            }
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
            throw e;
        } finally {
            Util.close(cn);
            Util.close(rs);
        }
        LOGGER.info("<==== Fin Metodo: listaPartesEnTempo ====>");
        return obj;
    }
    
    
    



}
