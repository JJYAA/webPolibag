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
public class PagosDAO {
    private static final Logger LOGGER = LogManager.getLogger();
    
    public List<Map<String, Object>> muestraListaPagos(String empresa,String fechaIni, String fechaFin,String codigo) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_documentos_pendientes_pago(?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.setString(4, codigo);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                   // map.put("agencia", rs.getString("agencia"));
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("moneda", rs.getString("moneda"));
                   // map.put("descripcion", rs.getString("descripcion"));
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("total", rs.getDouble("total"));
                    map.put("pago", rs.getDouble("pago"));
                    map.put("saldo", rs.getDouble("saldo"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("tipocambio", rs.getDouble("tipocambio"));
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
    
    public String  cargaItemPagos(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento   
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_cargaItemPagos(?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, codigoCliente);
                cs.setString(4, tipoDocumento);
                cs.setString(5, serieDocumento);
                cs.setString(6, numeroDocumento);                
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
    
    
    public List<Map<String, Object>>  listaDocumentoProveedor_Pagos(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento   
            ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaDocumentoProveedor_Pagos(?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, codigoCliente);
                cs.setString(4, tipoDocumento);
                cs.setString(5, serieDocumento);
                cs.setString(6, numeroDocumento);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("banco", rs.getString("banco"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("fechaPago", rs.getString("fechaPago"));
                    map.put("formaPago", rs.getString("formaPago"));
                    map.put("deposito", rs.getString("deposito"));
                    map.put("asiento", rs.getString("asiento"));
                    map.put("importe", rs.getString("importe"));
                    map.put("importe_sol", rs.getString("importe_sol"));
                    map.put("importe_dol", rs.getString("importe_dol"));
                    map.put("nombre_banco", rs.getString("nombre_banco"));
                    map.put("t_moneda", rs.getString("t_moneda"));
                    map.put("t_formapago", rs.getString("t_formapago"));
                    map.put("indice", rs.getString("indice"));
                    
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

    
    public String  ActualizaItemsPagos(
            String empresa,
            Long secuencia,
            Long item,
            String banco,
            String formapago,
            Double importe,
            String deposito,
            String moneda,
            String fechaPago,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento   ,
            String usuario,
            String monedaProvision,
            Double tcProvision
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_actualiza_PagosItems(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setLong(3, item);
                cs.setString(4, banco);
                cs.setString(5, formapago);
                cs.setDouble(6, importe);
                cs.setString(7, deposito);
                cs.setString(8, moneda);
                cs.setString(9, fechaPago);
                cs.setString(10, codigoCliente);
                cs.setString(11, tipoDocumento);
                cs.setString(12, serieDocumento);
                cs.setString(13, numeroDocumento);
                cs.setString(14, usuario);
                cs.setString(15, monedaProvision);
                cs.setDouble(16, tcProvision);
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

    public String  GrabarPagos(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento   ,
            String usuario,
            Double diferencia
            ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");
        
        String resultado="";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;        
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_grabaPagos_01(?,?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, codigoCliente);
                cs.setString(4, tipoDocumento);
                cs.setString(5, serieDocumento);
                cs.setString(6, numeroDocumento);                
                cs.setString(7, usuario); 
                cs.setDouble(8, diferencia); 
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


   public List<Map<String, Object>> muestraListaPagosRealizados(String empresa,String fechaIni, String fechaFin,String codigo) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_muestraListaPagosRealizados_pagos_01(?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.setString(4, codigo);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("tipodocumento", rs.getString("tipodocumento"));                    
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("total", rs.getDouble("total"));
                    map.put("asiento", rs.getString("asiento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("fechapago", rs.getString("fechapago"));                    
                    map.put("t_formapago", rs.getString("t_formapago"));   
                    map.put("item", rs.getString("item"));   
                    map.put("t_moneda", rs.getString("t_moneda"));   
                   /* map.put("periodo", rs.getString("periodo"));  
                    map.put("anho", rs.getString("anho"));  
                    map.put("tipo_comprobante", rs.getString("tipo_comprobante")); */
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

public String AsientoPagos(
            String empresa,
            String agencia,
            String serie,
            String documento,
            String periodo,
            String usuario,
            Long asiento,
            String tipo_comprobante,
            String tipoDocumento,
            String anho,
            Long item,
            String codigoCliente
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento_pago(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, periodo);
            cs.setString(6, usuario);
            cs.setLong(7, asiento);
            cs.setString(8, tipo_comprobante);
            cs.setString(9, tipoDocumento);
            cs.setString(10, anho);
            cs.setLong(11, item);
            cs.setString(12, codigoCliente);
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
            String agencia,
            String serie,
            String documento,
            String usuario,
            String asiento,
            String tipo_comprobante,
            String tipoDocumento,
            String codigoCliente,
            Long item
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_DesactivaAsientoCobranza(?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, usuario);
            cs.setString(6, asiento);
            cs.setString(7, tipo_comprobante);
            cs.setString(8, tipoDocumento);
            cs.setString(9, codigoCliente);
            cs.setLong(10, item);
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
        

    public String creaItemsTemporal(long secuencia,String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String ls_error="";
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento_pagos_pagos(?,?,?,?,?)}"); //sp_crear_asiento_pagos_cobranza
                cs.setLong(1, secuencia);
                cs.setString(2, empresa);
                cs.setString(3, fechaIni);
                cs.setString(4, fechaFin);
                cs.registerOutParameter(5, Types.VARCHAR); 
                cs.execute();            
                ls_error = cs.getString(5);
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

    public List<Map<String, Object>> listaItems_CobranzaXLS(Long secuencia) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaVentaTmp_xls(?)}");
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



