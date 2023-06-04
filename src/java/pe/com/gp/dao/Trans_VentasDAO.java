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
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class Trans_VentasDAO {
   private static final Logger LOGGER = LogManager.getLogger();
   
    public List<Map<String, Object>> listaTrans_VentasClientes(String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_Ventas_clientes(?,?,?)}");
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
   
   
    public List<Map<String, Object>> listaTrans_Ventas(String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_Ventas(?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
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
                    map.put("ref_serie", rs.getString("ref_serie"));
                    map.put("ref_documento", rs.getString("ref_documento"));
                    map.put("ref_fechadocumento", rs.getString("ref_fechadocumento"));
                    map.put("referencia_baseImp", rs.getDouble("referencia_baseImp"));
                    map.put("referenciaigv", rs.getDouble("referenciaigv"));
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
    
    public String creaItemsTemporal(long secuencia,String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String ls_error="";
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_crear_det_asiento_ventas_02(?,?,?,?,?)}");
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
    
    public String creaItemsAsiento_xls(long secuencia,String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String ls_error="";
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_crear_det_asiento_costo_venta(?,?,?,?,?)}");                
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

    
    
    
    public List<Map<String, Object>> listaItems_VentasXLS(Long secuencia) throws Exception {
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

    public String AsientoVentas(
            String empresa,
            String agencia,
            String serie,
            String documento,
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
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento(?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, periodo);
            cs.setString(6, usuario);
            cs.setLong(7, asiento);
            cs.setString(8, tipo);
            cs.setString(9, tipoDocumento);
            cs.setString(10, anho);
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
            String tipo,
            String tipoDocumento
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_DesactivaAsientoVentas(?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, usuario);
            cs.setString(6, asiento);
            cs.setString(7, tipo);
            cs.setString(8, tipoDocumento);
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
    
    
    public List<Map<String, Object>> listaItems_VentasClientes(Long secuencia) throws Exception {
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



    public List<Map<String, Object>> listaTrans_TMPVentasClientes(Long secuencia) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_TMPVentas_clientes(?)}");
                cs.setLong(1, secuencia);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("tipoAnexo", rs.getString("tipoAnexo"));
                    map.put("codigoAnexo", rs.getString("codigoAnexo"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("direccion", rs.getString("direccion"));
                    map.put("tipopersona", rs.getString("tipopersona"));
                    map.put("paterno", rs.getString("paterno"));
                    map.put("materno", rs.getString("materno"));
                    map.put("nombre", rs.getString("nombre"));
                    map.put("ruc", rs.getString("ruc"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("numeroDocumento", rs.getString("numeroDocumento"));
                    map.put("tipo", rs.getString("tipo"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("nacionalidad", rs.getString("nacionalidad"));
                    map.put("domiciliado", rs.getString("domiciliado"));   
 
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
    

    public List<Map<String, Object>> listaTrans_Ventas_Cobranzas(String empresa,String fechaIni, String fechaFin,String codigo) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_Ventas_cobranzas(?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.setString(4, codigo);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("agencia", rs.getString("agencia"));
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("descripcion", rs.getString("descripcion"));
                    map.put("documentocliente", rs.getString("documentocliente").trim() );
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("baseImponible", rs.getDouble("baseImponible"));
                    map.put("igv", rs.getDouble("igv"));
                    map.put("total", rs.getDouble("total"));
                    map.put("asiento", rs.getString("asiento"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("pago", rs.getDouble("pago"));
                    map.put("saldo", rs.getDouble("total") -    rs.getDouble("pago"));
                    
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


    public List<Map<String, Object>> muestraListaPagosRealizados(String empresa,String fechaIni, String fechaFin,String codigo) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_muestraListaPagosRealizados_cobranza(?,?,?,?)}");
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
                    map.put("t_formapago", rs.getString("t_formapago"));   
                    map.put("item", rs.getString("item"));   
                    map.put("nro_operacion", rs.getString("nro_operacion"));
                    map.put("subdiario", rs.getString("subdiario"));
                    
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

        
    public List<Map<String, Object>> auditoriaListado(String fecha,String cuenta,String tipo) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_auditoria_concar_vs_web(?,?,?)}");
                cs.setString(1, fecha);
                cs.setString(2, cuenta);
                cs.setString(3, tipo);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", rs.getString("codigo"));              
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("documento", rs.getString("documento"));
                    map.put("importe", rs.getString("importe"));
                    map.put("pagado", rs.getDouble("pagado"));
                    map.put("saldo", rs.getDouble("saldo"));
                    map.put("web", rs.getDouble("web"));
                    map.put("fechadocumento", rs.getString("FECHADOCUMENTO"));
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

    public List<Map<String, Object>> muestraDocumentos(String documento) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_comprobante_detalle(?)}");
                cs.setString(1, documento);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("sub", rs.getString("sub"));  
                    map.put("asiento", rs.getString("asiento"));              
                    map.put("cuenta", rs.getString("cuenta"));
                    map.put("codigo", rs.getString("codigo"));
                    map.put("dh", rs.getString("dh"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("documento", rs.getString("documento"));
                    map.put("importe1", rs.getDouble("importe1"));
                    map.put("importe2", rs.getDouble("importe2"));
                    map.put("importe3", rs.getDouble("importe3"));
                     map.put("fecha", rs.getString("fecha"));                
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
        
    public List<Map<String, Object>> listaCosto_Ventas(Long secuencia) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaCosto_Ventas_02(?)}");
                cs.setLong(1, secuencia);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigocli", rs.getString("codigocli"));
                    map.put("nombrecli", rs.getString("nombrecli"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("costo", rs.getString("costo"));             
                    map.put("vvp", rs.getString("vvp")); 
                    map.put("utilidad", rs.getString("utilidad")); 
                    map.put("asiento", rs.getString("asiento")); 
                    map.put("agencia", rs.getString("agencia")); 
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
        
    public List<Map<String, Object>> listaUtilidad_documento(Long secuencia) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaUtilidas_Documento(?)}");
                cs.setLong(1, secuencia);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo", rs.getString("codigo"));
                    map.put("nombre", rs.getString("nombre"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("fechaDocumento", rs.getString("fechaDocumento"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("costo", rs.getString("costo"));             
                    map.put("vvp", rs.getString("vvp")); 
                    map.put("utilidad", rs.getString("utilidad")); 
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

    public String carga_costos(
            Long secuencia ,
            String fecha_ini,
            String fecha_fin
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_carga_costo(?,?,?)}");
            cs.setLong(1, secuencia);
            cs.setString(2, fecha_ini);
            cs.setString(3, fecha_fin);
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
    
    public String carga_utilidad(
            Long secuencia ,
            String fecha_ini,
            String fecha_fin
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_carga_utilidad(?,?,?)}");
            cs.setLong(1, secuencia);
            cs.setString(2, fecha_ini);
            cs.setString(3, fecha_fin);
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

    public List<Map<String, Object>> listaUtilidad_documento_detalle(Long secuencia,
            String tipoDocumento,
            String serie,
            String documento) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_listaUtilidas_Documento_detalle(?,?,?,?)}");
                cs.setLong(1, secuencia);
                cs.setString(2, tipoDocumento);
                cs.setString(3, serie);
                cs.setString(4, documento);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigo_prod", rs.getString("codigo_prod"));
                    map.put("descripcion", rs.getString("descripcion"));
                    map.put("cantidad", rs.getString("cantidad"));                    
                    map.put("vvp", rs.getString("vvp"));
                    map.put("costo", rs.getString("costo"));
                    map.put("cal_vvp", rs.getString("cal_vvp"));
                    map.put("cal_costo", rs.getString("cal_costo"));                    
                    map.put("utilidad", rs.getString("utilidad"));             
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

    public List<Map<String, Object>> listaRegistro_Ventas(String empresa,String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_Ventas(?,?,?)}");
                cs.setString(1, empresa);
                cs.setString(2, fechaIni);
                cs.setString(3, fechaFin);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
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
                    map.put("ref_serie", rs.getString("ref_serie"));
                    map.put("ref_documento", rs.getString("ref_documento"));
                    map.put("ref_fechadocumento", rs.getString("ref_fechadocumento"));
                    map.put("referencia_baseImp", rs.getDouble("referencia_baseImp"));
                    map.put("referenciaigv", rs.getDouble("referenciaigv"));
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

    public String AsientoVentas_01(
            String empresa,
            String agencia,
            String serie,
            String documento,
            String periodo,
            String usuario,
            Long asiento,
            String tipo,
            String tipoDocumento,
            String anho,
            String codigoCliente
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento_cv_01(?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, periodo);
            cs.setString(6, usuario);
            cs.setLong(7, asiento);
            cs.setString(8, tipo);
            cs.setString(9, tipoDocumento);
            cs.setString(10, anho);
            cs.setString(11, codigoCliente);
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
    
    public String AsientoDesactiva_01(
            String empresa,
            String agencia,
            String serie,
            String documento,
            String usuario,
            String asiento,
            String tipo,
            String tipoDocumento,
            String codigoCliente
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String resultado = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_DesactivaAsientoVentas_02(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, usuario);
            cs.setString(6, asiento);
            cs.setString(7, tipo);
            cs.setString(8, tipoDocumento);
            cs.setString(9, codigoCliente);
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
    
public List<Map<String, Object>> listaTrans_Ventas_Cobranzas_operacion(String empresa,long operacion) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listaTrans_cobranzas_operacion(?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, operacion);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    //map.put("agencia", rs.getString("agencia"));
                    
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("serie", rs.getString("serie"));
                    map.put("documento", rs.getString("documento"));
                    map.put("deposito", rs.getString("deposito"));
                    map.put("fechaOperacion", rs.getString("fechaPago"));
                    map.put("formaPago", rs.getString("formaPago"));
                    map.put("banco", rs.getString("banco"));
                    map.put("item", rs.getString("item"));
                    map.put("moneda", rs.getString("moneda"));
                    map.put("deposito", rs.getString("deposito"));
                    map.put("total", rs.getDouble("saldo") + rs.getDouble("retencion"));
                    map.put("pago", rs.getDouble("saldo") + rs.getDouble("retencion"));
                    map.put("saldo", rs.getDouble("saldo") + rs.getDouble("retencion"));                    
                    map.put("asiento", rs.getString("asiento"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("nombrecliente", rs.getString("nombrecliente"));
                    map.put("asiento", rs.getString("asiento"));
                    map.put("fechacontable", rs.getString("fechacontable"));
                    map.put("anulado", rs.getString("anulado"));
                    map.put("asiento", rs.getString("asiento"));  
                    map.put("retencion", rs.getString("retencion")); 
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
