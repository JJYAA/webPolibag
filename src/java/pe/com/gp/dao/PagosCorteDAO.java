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
 * @author ADMIN
 */
public class PagosCorteDAO {

    private static final Logger LOGGER = LogManager.getLogger();

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
                cs = cn.prepareCall("{call sp_listado_Doc_Pagar_corte_resu(?,?,?,?,?)}");
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
                    map.put("total_sol", rs.getDouble("total_sol"));
                    map.put("pagado_sol", rs.getDouble("pago_sol"));
                    map.put("saldo_sol", rs.getDouble("saldo_sol"));
                    map.put("total_dol", rs.getDouble("total_dol"));
                    map.put("pagado_dol", rs.getDouble("pago_dol"));
                    map.put("saldo_dol", rs.getDouble("saldo_dol"));                    
                    map.put("moneda", rs.getString("moneda"));                    
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
                    map.put("moneda", rs.getString("moneda"));
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
                cs = cn.prepareCall("{call sp_listado_pagar_Doc_corte_detalle(?,?,?,?,?)}");
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
                    map.put("moneda", rs.getString("moneda"));
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
