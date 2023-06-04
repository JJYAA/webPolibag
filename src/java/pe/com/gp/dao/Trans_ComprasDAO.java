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
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class Trans_ComprasDAO {
   private static final Logger LOGGER = LogManager.getLogger();
   
   
    public List<Map<String, Object>> listaTrans_Compras_Proveedores(String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_Compras_Proveedores_02(?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("documentocliente", rs.getString("documentocliente"));
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
      
   
   
    public List<Map<String, Object>> listaTrans_Compras(String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaTrans_Compras(?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("indice", rs.getString("indice"));
                    map.put("indice_tipo", rs.getString("indice_tipo"));
                    map.put("agencia", rs.getString("agencia"));
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("descripcion", rs.getString("descripcion"));
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("baseImponible", rs.getDouble("baseImponible"));
                    map.put("igv", rs.getDouble("igv"));
                    map.put("total", rs.getDouble("total"));
                    map.put("asiento", rs.getString("asiento"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("registros", rs.getLong("registros"));
                    map.put("ordencompraInterna", rs.getString("ordencompraInterna"));
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
    
    public String creaItemsTemporal_Compras(Long secuencia,String empresa,String fechaIni, String fechaFin,String asiento) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String ls_error="";
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                //sp_crear_det_asiento_compras_tmp
                cs = cn.prepareCall("{call polibag.dbo.sp_crear_det_asiento_compras_tmp(?,?,?,?,?)}");
                cs.setLong(1, secuencia);
                cs.setString(2, empresa);
                cs.setString(3, fechaIni);
                cs.setString(4, fechaFin);
                cs.setString(5, asiento);
                cs.execute();                
            } catch (Exception e) {
                ls_error =  e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
                Util.close(rs);
            }
        }
        return ls_error;
    }    

    
    
    
    public List<Map<String, Object>> listaItems_ComprasXLS(Long secuencia) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaVentaTmp_xls(?)}");
                cs.setLong(1, secuencia);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
			map.put("tamanho", rs.getString("tamanho"));
			map.put("subdiario",rs.getString("subdiario"));
			map.put("comprobante", rs.getString("comprobante"));
			map.put("fechacomprobante", rs.getString("fechacomprobante"));
			map.put("moneda", rs.getString("moneda"));
			map.put("glosa", rs.getString("glosa"));
			map.put("tipocambio", rs.getDouble("tipocambio"));
			map.put("tipoconversion", rs.getString("tipoconversion"));
			map.put("flagconversionmd", rs.getString("flagconversionmd"));
			map.put("cuenta_contable", rs.getString("cuenta_contable"));
			map.put("codigoanexo", rs.getString("codigoanexo"));
			map.put("centrocosto", rs.getString("centrocosto"));
			map.put("debehaber", rs.getString("debehaber")); 
			map.put("importeorigen", rs.getDouble("importeorigen"));
			map.put("importedolar", rs.getDouble("importedolar"));
			map.put("importesoles", rs.getDouble("importesoles")); 
			map.put("tipodocumento", rs.getString("tipodocumento"));
			map.put("numerodocumento", rs.getString("numerodocumento")); 
			map.put("fechadocumento", rs.getString("fechadocumento"));
			map.put("fechavencimiento", rs.getString("fechavencimiento")); 
			map.put("codigoarea", rs.getString("codigoarea")); 
			map.put("glosadetalle", rs.getString("glosadetalle")); 
			map.put("codigo_aux_anexo", rs.getString("codigo_aux_anexo")); 
			map.put("medio_pago", rs.getString("medio_pago"));  
			map.put("tipodocumentoRef", rs.getString("tipodocumentoRef")); 
			map.put("numerodocRef", rs.getString("numerodocRef")); 
			map.put("fechadocumentoref", rs.getString("fechadocumentoref")); 
			map.put("nromaqregistradora", rs.getString("nromaqregistradora"));  
			map.put("baseimponibleref", rs.getDouble("baseimponibleref")); 
			map.put("igvdocumentoprovision", rs.getDouble("igvdocumentoprovision"));  
			map.put("tipoRefestadoMQ", rs.getString("tipoRefestadoMQ")); 
			map.put("numeroSerieCajaRegistradora", rs.getString("numeroSerieCajaRegistradora"));
			map.put("fechaoperacion", rs.getString("fechaoperacion"));
			map.put("tipodetasa", rs.getString("tipodetasa")); 
			map.put("tasadetraccion", rs.getString("tasadetraccion")); 
			map.put("importeDetr_Percep_dol", rs.getDouble("importeDetr_Percep_dol"));  
			map.put("importeDetr_Percep_sol", rs.getDouble("importeDetr_Percep_sol")); 
			map.put("TipoCambio_F", rs.getDouble("TipoCambio_F")); 
			map.put("igv_sin_dereho_cre_fiscal", rs.getString("igv_sin_dereho_cre_fiscal"));  
			map.put("tipocambiofecha", rs.getString("tipocambiofecha"));                     
                   /* map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("descripcion", rs.getString("descripcion"));
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("baseImponible", rs.getDouble("baseImponible"));
                    map.put("igv", rs.getDouble("igv"));
                    map.put("total", rs.getDouble("total"));*/
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

    public String AsientoCompras(
            String empresa,
            String ordencompra,
            String numerFacProveedor,
            String periodo,
            String usuario,
            Long asiento,
            String tipo,
            String tipoDocumento,
            String anho
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento_Compras_01(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, ordencompra);
            cs.setString(3, numerFacProveedor);
            cs.setString(4, periodo);
            cs.setString(5, usuario);
            cs.setLong(6, asiento);
            cs.setString(7, tipo);
            cs.setString(8, tipoDocumento);
            cs.setString(9, anho);
            cs.execute();
        } catch (Exception e) {
            resultado = e.toString();
            throw e;
        } finally {
            Util.close(cn);
            Util.close(rs);
        }
        LOGGER.info("<==== Fin Metodo: AsientoVentas ====>");
        return resultado;
    }
    
    public String AsientoDesactiva(
            String empresa,
            String ordencompra,
            String numerFacProveedor,
            String periodo,
            String usuario,
            String asiento,
            String tipo,
            String tipoDocumento,
            String anho
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_DesactivaAsientoCompras(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, ordencompra);
            cs.setString(3, numerFacProveedor);
            cs.setString(4, periodo);
            cs.setString(5, usuario);
            cs.setString(6, asiento);
            cs.setString(7, tipo);
            cs.setString(8, tipoDocumento);
            cs.setString(9, anho);
            cs.execute();
        } catch (Exception e) {
            resultado = e.toString();
            throw e;
        } finally {
            Util.close(cn);
            Util.close(rs);
        }
        LOGGER.info("<==== Fin Metodo: AsientoVentas ====>");
        return resultado;
    }    
  

    public List<Map<String, Object>> muestraItemTempo(Long secuencia , long indice) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaItemCobranza_Temporal(?,?)}");
                cs.setLong(1, secuencia);
                cs.setLong(2, indice);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("formapago", rs.getString("formapago"));              
                    map.put("moneda", rs.getString("moneda"));
                    map.put("banco", rs.getString("banco"));
                    map.put("fechaoperacion", rs.getString("fechaoperacion"));
                    map.put("deposito", rs.getString("deposito"));
                    map.put("importe", rs.getDouble("importe"));
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
