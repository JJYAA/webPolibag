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
public class GimDAO {

    private static final Logger LOGGER = LogManager.getLogger();
   
    public List<Map<String, Object>> listaGim() throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_lista_gim()}");
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pathservidor", rs.getString("pathservidor"));
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
    
    
    public List<Map<String, Object>>  buscaOC(String oc) throws Exception {
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        List<Map<String, Object>> list = new ArrayList<>();
        CallableStatement cs = null;
        ResultSet rs ;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_busca_oc(?)}");
                cs.setString(1, oc);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("proveedor", rs.getString("proveedor"));
                    map.put("ordencompra", rs.getString("ordencompra").trim());                    
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("fecha", rs.getString("fecha"));
                    map.put("factura", rs.getString("factura"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("referencia", rs.getString("referencia").trim());

                    list.add(map);
                }
            } catch (Exception e) {
               resultado = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        return list;
    }        
    
    public String  ActualizaPathServidor(String Rutaservidor) throws Exception {
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_actualiza_gim(?)}");
                cs.setString(1, Rutaservidor);
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
    
     public String  Inicializa_tablas_dbf() throws Exception {
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_init_tabla_dbf()}");
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
    

    public List<Map<String, Object>> muestraSecuuenciaMesAnho(String anho,String mes) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_muestraSecuuenciaMesAnho(?,?)}");
                cs.setString(1, anho);
                cs.setString(2, mes);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("anho", rs.getString("anho"));
                    map.put("mes", rs.getString("mes"));
                    map.put("tipo_comprobante", rs.getString("tipo_comprobante"));
                    map.put("contador", rs.getString("contador"));
                    map.put("descripcion", rs.getString("descripcion"));
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
     
     
     public String  grabaSecuencia(String anho,String mes,String tipo_comprobante,String secuencia) throws Exception {
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_grabaSecuencia(?,?,?,?)}");
                cs.setString(1, anho);
                cs.setString(2, mes);
                cs.setString(3, tipo_comprobante);
                cs.setString(4, secuencia);
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
 
     public String  crea_MesAnho(String anho,String mes) throws Exception {
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_crea_MesAnho(?,?)}");
                cs.setString(1, anho);
                cs.setString(2, mes);
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
     

     public String  actualizaOC(
             String oc,
             String ruc,
             String fecha,
             String numerofactura,
             String referencia) throws Exception {
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_actualizaoc(?,?,?,?,?)}");
                cs.setString(1, oc);
                cs.setString(2, ruc);
                cs.setString(3, fecha);
                cs.setString(4, numerofactura);
                cs.setString(5, referencia);
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
