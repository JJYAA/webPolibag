/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.util.Util;

/**
 *
 * @author ADMIN
 */
public class GenericoDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SEQ_TMP1_SESSION_KEY = "FacRepMosSeqTmp1_" + UUID.randomUUID().toString();

    public List<Map<String, Object>>  listaProveedor(
            String codigoCliente
            ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_lista_proveedores(?)}");
                cs.setString(1, codigoCliente);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", rs.getString("codigo"));
                    map.put("nombre", rs.getString("nombre"));                    
                    list.add(map);
                }
            } catch (Exception e) {
                LOGGER.error("GP.ERROR: {}", e);
            } finally {
                Util.close(cn);
                Util.close(cs);
                Util.close(rs);
            }
        }
        return list;
    }  

    public List<Map<String, Object>>  listaClientes(
            String codigoCliente
            ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_lista_clientes(?)}");
                cs.setString(1, codigoCliente);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", rs.getString("codigo"));
                    map.put("nombre", rs.getString("nombre"));                    
                    list.add(map);
                }
            } catch (Exception e) {
                LOGGER.error("GP.ERROR: {}", e);
            } finally {
                Util.close(cn);
                Util.close(cs);
                Util.close(rs);
            }
        }
        return list;
    }  

    
}
