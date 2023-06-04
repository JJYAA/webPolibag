/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.entity.ListaGenerica;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class ProvisionDAO {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    public String  EliminaItem(
            Long secuencia,
            Long item 
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_eliminaItemPago(?,?)}");
                cs.setLong(1, secuencia);
                cs.setLong(2, item);               
                cs.execute();
            } catch (Exception e) {
               resultado = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        LOGGER.info("<==== Fin Metodo: ActualizaItemsCobranza ====>");
        return resultado;
    }      
    
    public String  AdicionaItemProvision(
            String empresa,
            Long secuencia,
            String cuenta,
            String glosa,
            Double debe,
            Double haber   
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_cargaItemProvision(?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, cuenta);
                cs.setString(4, glosa);
                cs.setDouble(5, debe);
                cs.setDouble(6, haber);                
                cs.execute();
            } catch (Exception e) {
               resultado = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        LOGGER.info("<==== Fin Metodo: ActualizaItemsCobranza ====>");
        return resultado;
    }           
    
    
    public String  cargaDetalleProvision(
        Long secuencia       ,       
	String proveedor,
	String tipo  ,
	String documento 
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_carga_detalle_provision(?,?,?,?)}");
                cs.setLong(1, secuencia);
                cs.setString(2, proveedor);                
                cs.setString(3, tipo);
                cs.setString(4, documento);                
                cs.execute();
            } catch (Exception e) {
               resultado = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        LOGGER.info("<==== Fin Metodo: ActualizaItemsCobranza ====>");
        return resultado;
    }           
    
    
    public List<Map<String, Object>>  listaDocumentoProveedor_Provision_tempo(
            String empresa,
            Long secuencia            
            ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaDocumentoProveedor_ProvisionTmp(?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("item", rs.getString("item"));
                    map.put("cuenta", rs.getString("cuenta"));
                    map.put("t_cuenta", rs.getString("t_cuenta"));
                    map.put("debe", rs.getString("debe"));
                    map.put("haber", rs.getString("haber"));                    
                    map.put("asiento", rs.getString("asiento"));   
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
    
    public List<ListaGenerica> listaPlanCuentaProvision(String rucEmpresa) throws Exception {
        ArrayList<ListaGenerica> listaProvincias = new ArrayList<>();
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = cn.prepareCall("{call polibag.dbo.sp_listaCuentaProvision(?)}");
            cs.setString(1, rucEmpresa);
            cs.execute();
            rs = cs.getResultSet();
            while (rs.next()) {
                ListaGenerica obj = new ListaGenerica();
                obj.setIndice(rs.getString("indice"));
                obj.setDescripcion(rs.getString("descripcion"));
                listaProvincias.add(obj);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            Util.close(cn);
            Util.close(cs);
            Util.close(rs);
        }
        return listaProvincias;
    }    
    public List<ListaGenerica> tipo_documento(String rucEmpresa) throws Exception {
        ArrayList<ListaGenerica> listaProvincias = new ArrayList<>();
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = cn.prepareCall("{call polibag.dbo.sp_lista_tipo_documento(?)}");
            cs.setString(1, rucEmpresa);
            cs.execute();
            rs = cs.getResultSet();
            while (rs.next()) {
                ListaGenerica obj = new ListaGenerica();
                obj.setIndice(rs.getString("indice"));
                obj.setDescripcion(rs.getString("descripcion"));
                listaProvincias.add(obj);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            Util.close(cn);
            Util.close(cs);
            Util.close(rs);
        }
        return listaProvincias;
    }      
    
    public List<Map<String, Object>>  buscaDocumento(
            String empresa,
          String ruc  ,            
            String tipoDocumento,
            String documento
            
            ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaDocumentoPagar(?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, tipoDocumento);
                cs.setString(3, documento);
                cs.setString(4, ruc);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("fechadocumento", rs.getString("fechadocumento"));
                    map.put("vencimiento", rs.getString("vencimiento"));
                    map.put("gravado", rs.getString("gravado"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("baseImponible", rs.getString("baseImponible"));                    
                    map.put("igv", rs.getString("igv"));   
                    map.put("total", rs.getString("total"));   
                    map.put("asiento", rs.getString("asiento"));  
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
    

    public JSONObject  asientoProvision(
            
	String proveedor,
	String tipo  ,
	String documento ,
	String fechaDoc  ,
	String fechaVen,
	String moneda ,
	String gravado ,
        
	Double baseImp ,
	Double igv ,
	Double total    ,
        String asiento,
        Long secuencia
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        JSONObject jsonObjOut;
        String resultado="";
        String mensaje ;      
        String tipoMensaje ;  
        String result;        
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null; 
        jsonObjOut = new JSONObject();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_grabar_asiento_provision(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setString(1, proveedor);
                cs.setString(2, tipo);
                cs.setString(3, documento);
                cs.setString(4, fechaDoc);
                cs.setString(5, fechaVen);
                cs.setString(6, moneda);                
                cs.setString(7, gravado);                
                cs.setDouble(8, baseImp);                
                cs.setDouble(9, igv);                
                cs.setDouble(10, total); 
                cs.setString(11, asiento);
                cs.setDouble(12, secuencia); 
                cs.registerOutParameter(13, Types.VARCHAR);                                 
                cs.registerOutParameter(14, Types.VARCHAR);  
                cs.registerOutParameter(15, Types.VARCHAR);  
                cs.execute();
                mensaje = cs.getString(13);      
                tipoMensaje = cs.getString(14);  
                result = cs.getString(15);
                
                JSONArray jsonArrMsg = new JSONArray();
                JSONObject jsonObjMsg = new JSONObject();
                jsonObjMsg.put("msg", mensaje);
                jsonObjMsg.put("tipoMsg", tipoMensaje);
                jsonObjMsg.put("documento", result);
                jsonArrMsg.put(jsonObjMsg);
                
                jsonObjOut.put("mensaje", jsonArrMsg);                
                
                
            } catch (Exception e) {
                JSONArray jsonArrMsg = new JSONArray();
                JSONObject jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "error");
                jsonObjMsg.put("msg", "" + e);
                jsonArrMsg.put(jsonObjMsg);
                jsonObjOut = new JSONObject();
                jsonObjOut.put("mensaje", jsonArrMsg);
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        LOGGER.info("<==== Fin Metodo: ActualizaItemsCobranza ====>");
        return jsonObjOut;
    }  

    public String  eliminaProvision(
            
	String proveedor,
	String tipo  ,
	String documento 
        ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>"); 
        String result="";        
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null; 
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_elimina_asiento_provision(?,?,?)}");
                cs.setString(1, proveedor);
                cs.setString(2, tipo);
                cs.setString(3, documento);
                cs.execute();                                             
            } catch (Exception e) {
                result = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        LOGGER.info("<==== Fin Metodo: ActualizaItemsCobranza ====>");
        return result;
    }      
}
