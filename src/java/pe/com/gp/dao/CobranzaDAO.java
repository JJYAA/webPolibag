/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class CobranzaDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    public List<ListaGenerica> listaBancos(String rucEmpresa) throws Exception {
        ArrayList<ListaGenerica> listaProvincias = new ArrayList<>();
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = cn.prepareCall("{call polibag.dbo.sp_lista_bancos(?)}");
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

    public String ActualizaItemsCobranza(
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
            String numeroDocumento,
            String usuario
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_actualiza_cobranzaItems(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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

    public List<Map<String, Object>> listaDocumentoCliente_Cobranzas(
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
                cs = cn.prepareCall("{call sp_listaDocumentoCliente_Cobranzas(?,?,?,?,?,?)}");
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
                    map.put("nombre_banco", rs.getString("nombre_banco"));
                    map.put("t_moneda", rs.getString("t_moneda"));
                    map.put("t_formapago", rs.getString("t_formapago"));
                    map.put("indice", rs.getString("indice"));
                    map.put("nro_operacion", rs.getString("nro_operacion"));

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

    public String GrabarCobranza(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento,
            String usuario
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_grabaCobranza(?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, codigoCliente);
                cs.setString(4, tipoDocumento);
                cs.setString(5, serieDocumento);
                cs.setString(6, numeroDocumento);
                cs.setString(7, usuario);
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

    public String cargaItemCobranza(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_cargaItemCobranza(?,?,?,?,?,?)}");
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

    public String ElimimaItemCobranza(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento,
            Long indice
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_EliminaItemCobranza(?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, codigoCliente);
                cs.setString(4, tipoDocumento);
                cs.setString(5, serieDocumento);
                cs.setString(6, numeroDocumento);
                cs.setLong(7, indice);
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

    public String AsientoCobranza(
            String empresa,
            String agencia,
            String serie,
            String documento,
            String periodo,
            String usuario,
            Long asiento,
            String ptipo_comprobante,
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
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento_cobranza(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, empresa);
            cs.setString(2, agencia);
            cs.setString(3, serie);
            cs.setString(4, documento);
            cs.setString(5, periodo);
            cs.setString(6, usuario);
            cs.setLong(7, asiento);
            cs.setString(8, ptipo_comprobante);
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

    public String creaItemsTemporal(long secuencia, String empresa, String fechaIni, String fechaFin) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String ls_error = "";
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_crear_asiento_pagos_cobranza_01(?,?,?,?,?)}"); //sp_crear_asiento_pagos_cobranza
                cs.setLong(1, secuencia);
                cs.setString(2, empresa);
                cs.setString(3, fechaIni);
                cs.setString(4, fechaFin);
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();
                ls_error = cs.getString(5);
            } catch (Exception e) {
                ls_error = e.toString();
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
                    map.put("subdiario", rs.getString("subdiario"));
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

    public List<Map<String, Object>> listado_depositos(
            String fechaIni,
            String fechaFin
    ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listado_depositos(?,?)}");
                cs.setString(1, fechaIni);
                cs.setString(2, fechaFin);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigoCliente", rs.getString("codigoCliente"));
                    map.put("razonSocial", rs.getString("razonSocial"));
                    map.put("banco", rs.getString("banco"));
                    map.put("fechaDeposito", rs.getString("fechaDeposito"));
                    map.put("tipoDocumento", rs.getString("tipoDocumento"));
                    map.put("documento", rs.getString("documento"));
                    map.put("forma_pago", rs.getString("forma_pago"));
                    map.put("importe", rs.getDouble("importe"));
                    map.put("deposito", rs.getString("deposito"));
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

    public List<Map<String, Object>> listado_Documentos_corte_resu(
            String fechaIni,
            String fechaFin,
            String tipo,
            String codigo,
            String forma
    ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listado_Documentos_corte_resu(?,?,?,?,?)}");
                cs.setString(1, fechaIni);
                cs.setString(2, fechaFin);
                cs.setString(3, tipo);
                cs.setString(4, codigo);
                cs.setString(5, forma);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("total", rs.getDouble("total"));
                    map.put("pagado", rs.getDouble("pago"));
                    map.put("saldo", rs.getDouble("saldo"));
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

    public List<Map<String, Object>> listado_Documentos_corte_deta(
            String fechaIni,
            String fechaFin
    ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listado_Documentos_corte_deta(?,?)}");
                cs.setString(1, fechaIni);
                cs.setString(2, fechaFin);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("codigoCliente", rs.getString("codigoCliente"));
                    map.put("razonSocial", rs.getString("razonSocial"));
                    map.put("banco", rs.getString("banco"));
                    map.put("fechaDeposito", rs.getString("fechaDeposito"));
                    map.put("tipoDocumento", rs.getString("tipoDocumento"));
                    map.put("documento", rs.getString("documento"));
                    map.put("forma_pago", rs.getString("forma_pago"));
                    map.put("importe", rs.getDouble("importe"));
                    map.put("deposito", rs.getString("deposito"));
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

    public List<Map<String, Object>> listado_Documentos_corte_detalle(
            String fechaIni,
            String fechaFin,
            String tipo,
            String codigo,
            String forma
    ) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call sp_listado_Documentos_corte_detalle(?,?,?,?,?)}");
                cs.setString(1, fechaIni);
                cs.setString(2, fechaFin);
                cs.setString(3, tipo);
                cs.setString(4, codigo);
                cs.setString(5, forma);
                cs.execute();
                rs = cs.getResultSet();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("documentocliente", rs.getString("documentocliente"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("total", rs.getDouble("total"));
                    map.put("pagado", rs.getDouble("pago"));
                    map.put("saldo", rs.getDouble("saldo"));
                    map.put("serie", rs.getString("serie"));
                    map.put("tipodocumento", rs.getString("tipodocumento"));
                    map.put("documento", rs.getString("documento"));
                    map.put("razonsocial", rs.getString("razonsocial"));
                    map.put("fechadocumento", rs.getString("fechadocumento"));
                    map.put("forma", rs.getString("forma"));
                    map.put("fechaVencimiento", rs.getString("fechaVencimiento"));
                    map.put("tipo_ref", rs.getString("tipo_ref"));
                    map.put("serie_ref", rs.getString("serie_ref"));
                    map.put("documento_ref", rs.getString("documento_ref"));
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

    public JSONObject GrabarCobranza_operacion(
            String empresa,
            Long secuencia,
            String usuario,
            String tipo_comprobante,
            String fecha_contable,
            long asiento
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        long ln_operacion;
        long ln_asiento;
        JSONObject jsonObjOut = null;
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_grabaCobranza_operacion_02(?,?,?,?,?,?,?,?)}");//sp_grabaCobranza_operacion
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, usuario);
                cs.setString(4, tipo_comprobante);
                cs.setString(5, fecha_contable);
                cs.registerOutParameter(6, Types.NUMERIC);
                cs.registerOutParameter(7, Types.NUMERIC);
                cs.registerOutParameter(8, Types.VARCHAR);
                cs.execute();
                ln_operacion = cs.getLong(6);
                ln_asiento = cs.getLong(7);
                resultado = cs.getString(8);
                JSONArray jsonArrMsg = new JSONArray();
                JSONObject jsonObjMsg = new JSONObject();
                if ("".equals(resultado)) {
                    jsonObjMsg.put("msg", "Proceso termino con exito, Operacion: " + Util.nullCad(ln_operacion) + " Asiento" + Util.nullCad(ln_asiento));
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonArrMsg.put(jsonObjMsg);
                    jsonObjOut = new JSONObject();
                    jsonObjOut.put("mensaje", jsonArrMsg);
                } else {
                    jsonObjMsg.put("msg", resultado);
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonArrMsg.put(jsonObjMsg);
                    jsonObjOut = new JSONObject();
                    jsonObjOut.put("mensaje", jsonArrMsg);
                }

            } catch (Exception e) {
                resultado = e.toString();
                JSONArray jsonArrMsg = new JSONArray();
                JSONObject jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "error");
                jsonObjMsg.put("msg", "" + resultado);
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

    public JSONObject GrabarCobranza_01(
            String empresa,
            Long secuencia,
            String usuario,
            String tipo_comprobante,
            String fecha_contable,
            long asiento
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        long ln_operacion;
        long ln_asiento;        
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        JSONObject jsonObjOut = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_grabaCobranza_01(?,?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, usuario);
                cs.setString(4, tipo_comprobante);
                cs.setString(5, fecha_contable);
                cs.registerOutParameter(6, Types.NUMERIC);
                cs.registerOutParameter(7, Types.NUMERIC);
                cs.registerOutParameter(8, Types.VARCHAR);
                cs.execute();
                ln_operacion = cs.getLong(6);
                ln_asiento = cs.getLong(7);
                resultado = cs.getString(8);                
                JSONArray jsonArrMsg = new JSONArray();
                JSONObject jsonObjMsg = new JSONObject();
                if ("".equals(resultado)) {
                    jsonObjMsg.put("msg", "Proceso termino con exito, Operacion: " + Util.nullCad(ln_operacion) + " Asiento : " + Util.nullCad(ln_asiento));
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonArrMsg.put(jsonObjMsg);
                    jsonObjOut = new JSONObject();
                    jsonObjOut.put("mensaje", jsonArrMsg);
                } else {
                    jsonObjMsg.put("msg", resultado);
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonArrMsg.put(jsonObjMsg);
                    jsonObjOut = new JSONObject();
                    jsonObjOut.put("mensaje", jsonArrMsg);
                }
            } catch (Exception e) {
                resultado = e.toString();
                JSONArray jsonArrMsg = new JSONArray();
                JSONObject jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "error");
                jsonObjMsg.put("msg", "" + resultado);
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

    public String AnulacionOperacion(
            long operacion
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_anulacioOperacion(?)}");
                cs.setLong(1, operacion);
                cs.execute();
            } catch (SQLException e) {
                resultado = e.toString();
            } finally {
                Util.close(cn);
                Util.close(cs);
            }
        }
        LOGGER.info("<==== Fin Metodo: ActualizaItemsCobranza ====>");
        return resultado;
    }

    public String ElimimaItemCobranzaOperacion(
            String empresa,
            Long secuencia,
            String codigoCliente,
            String tipoDocumento,
            String serieDocumento,
            String numeroDocumento,
            Long indice,
            Long operacion
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: ActualizaItemsCobranza ====>");

        String resultado = "";
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_EliminaItemCobranza_ope(?,?,?,?,?,?,?,?)}");
                cs.setString(1, empresa);
                cs.setLong(2, secuencia);
                cs.setString(3, codigoCliente);
                cs.setString(4, tipoDocumento);
                cs.setString(5, serieDocumento);
                cs.setString(6, numeroDocumento);
                cs.setLong(7, indice);
                cs.setLong(8, operacion);
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

}
