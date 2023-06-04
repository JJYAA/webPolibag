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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class ClienteFacturaDAO {
    
    private static final Logger LOGGER = LogManager.getLogger();
  
    public List<Map<String, Object>> ListaProveedores(String empresa,String codigoCliente) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaProveedores(?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, codigoCliente);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", rs.getString("codigo"));
                    map.put("nombre", rs.getString("nombre"));
                    map.put("direccion", rs.getString("direccion"));
                    map.put("documento", rs.getString("documento"));
                    map.put("tipopersona", rs.getString("tipopersona"));
                    map.put("tp_sunat", rs.getString("tp_sunat"));
                    map.put("paterno", rs.getString("paterno"));
                    map.put("materno", rs.getString("materno"));
                    map.put("nombre1", rs.getString("nombre1"));
                    map.put("nombre2", rs.getString("nombre2"));
                    list.add(map);
                }
            } catch (Exception e) {
                LOGGER.error("GP.ERROR: {}", e.toString());
            } finally {
                Util.close(cn);
                Util.close(cs);
                Util.close(rs);
            }
        }
        return list;
    }

    
    public List<Map<String, Object>> ListaCliente(String empresa,String codigoCliente) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaCliente(?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, codigoCliente);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", rs.getString("codigo"));
                    map.put("nombre", rs.getString("nombre"));
                    map.put("direccion", rs.getString("direccion"));
                    map.put("documento", rs.getString("documento"));
                    map.put("tipopersona", rs.getString("tipopersona"));
                    map.put("tp_sunat", rs.getString("tp_sunat"));
                    map.put("paterno", rs.getString("paterno"));
                    map.put("materno", rs.getString("materno"));
                    map.put("nombre1", rs.getString("nombre1"));
                    map.put("nombre2", rs.getString("nombre2"));
                    list.add(map);
                }
            } catch (Exception e) {
                LOGGER.error("GP.ERROR: {}", e.toString());
            } finally {
                Util.close(cn);
                Util.close(cs);
                Util.close(rs);
            }
        }
        return list;
    }

    public String creaAnexo(
            String empresa,
            Long secuencia,
            String tipoAnexo,
            String codigoAnexo,
            String razonsocial,
            String direccion,
            String tipopersona,
            String paterno,
            String materno,
            String nombre,
            String ruc,
            String tipodocumento,
            String numeroDocumento,
            String tipo,
            String moneda,
            String nacionalidad,
            String domiciliado            
    ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        String  resultado = "";
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_crea_anexo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, tipoAnexo);
                cs.setString(4, codigoAnexo);
                cs.setString(5, razonsocial);
                cs.setString(6, direccion);
                cs.setString(7, tipopersona);
                cs.setString(8, paterno);
                cs.setString(9, materno);
                cs.setString(10, nombre);
                cs.setString(11, ruc);
                cs.setString(12, tipodocumento);
                cs.setString(13, numeroDocumento);
                cs.setString(14, tipo);
                cs.setString(15, moneda);
                cs.setString(16, nacionalidad);
                cs.setString(17, domiciliado);                
                cs.execute();                
            } catch (Exception e) {
               resultado = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        return resultado;
    }    
    
}
