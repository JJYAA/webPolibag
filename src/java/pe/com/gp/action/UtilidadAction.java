/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.json.JSONObject;
import pe.com.gp.dao.Trans_VentasDAO;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.Trans_VentasForm;
import pe.com.gp.util.Util;

/**
 *
 * @author user
 */
public class UtilidadAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SEQ_TMP1_SESSION_KEY = "FacRepMosSeqTmp1_" + UUID.randomUUID().toString();
    private static final String SEQ_TMP2_SESSION_KEY = "FacRepMosSeqTmp2_" + UUID.randomUUID().toString();

    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            Trans_VentasForm uform = (Trans_VentasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            session.setAttribute(SEQ_TMP2_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializa";

        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }

    public ActionForward inicializaVendedor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            Trans_VentasForm uform = (Trans_VentasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            session.setAttribute(SEQ_TMP2_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializaVendedor";

        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }

    public void listaUtilidad_ventas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjTotal = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", ""};
        double neto = 0;
        double igv = 0;
        double total = 0;
        String msgError = "";
        try {
            if (Util.sesionEstaActiva(request)) {
                HttpSession session = request.getSession();
                Usuario usuario = (Usuario) session.getAttribute("Usuario");
                String empresa = (String) session.getAttribute("Empresa");
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                String ls_empresa = Util.nullCad(request.getParameter("numeroParte"));
                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                StringBuilder sbLista = new StringBuilder();
                new Trans_VentasDAO().carga_utilidad(seqTmp1, ls_fecha_ini, ls_fecha_fin);
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaUtilidad_documento(seqTmp1);
                sbLista.append("<table id=\"tableUtilidad\" class=\"display\" cellspacing=\"0\" width=\"100%\"  >");
                sbLista.append("<thead>");
                sbLista.append("<tr>");
                sbLista.append("<th class=\"text-center\">Codigo</th>");
                sbLista.append("<th class=\"text-center\">Nombre</th>");
                sbLista.append("<th class=\"text-center\">T.D.</th>");
                sbLista.append("<th class=\"text-center\">Serie</th>");
                sbLista.append("<th class=\"text-center\">Documento</th>");
                sbLista.append("<th class=\"text-center\">Fecha</th>");
                sbLista.append("<th class=\"text-center\">vvp</th>");
                sbLista.append("<th class=\"text-center\">Costo</th>");
                sbLista.append("<th class=\"text-center\">Utilidad</th>");
                sbLista.append("<th class=\"text-center\">1</th>");
                sbLista.append("</tr>");
                sbLista.append("</thead>");
                    double tot_vvp = 0;
                    double tot_cos = 0;
                    double tot_uti = 0;                
                if (listVentas != null && !listVentas.isEmpty()) {

                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = "'" + listVentas.get(i).get("tipodocumento") + "','" + listVentas.get(i).get("serie") + "','" + listVentas.get(i).get("documento") + "'";

                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("codigo")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("nombre")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDocumento")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("vvp")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("costo")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("utilidad")))).append("</td>");
                        sbLista.append("<td class=\"text-center\">").append("<a class=\"btn btn-xs btn-default\" onclick=\"visualiza(" + CadSelected + ")\" title=\"Detalle\"><i class=\"fa fa-search \"></i> detalle </a>").append("</td>");
                        sbLista.append("</tr>");
                        tot_vvp += Util.nullDou(listVentas.get(i).get("vvp"));
                        tot_cos += Util.nullDou(listVentas.get(i).get("costo"));
                        tot_uti += Util.nullDou(listVentas.get(i).get("utilidad"));
                    }
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(tot_vvp)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(tot_cos)).append("</td>");                    
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(tot_uti)).append("</td>");
                    sbLista.append("<td class=\"text-center\" >").append("").append("</td>");

                } else {
                    neto = 0;
                }
                sbLista.append("</table>");

                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("tabla", sbLista.toString());
                }
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objTabla", jsonObjTabla);
            sb.append(jsonObject.toString());
            out = response.getWriter();
            out.write(sb.toString());
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        LOGGER.info("<==== Fin Metodo: agregarItem ====>");
    }

    public void listaUtilidad_ventasCopia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjTotal = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", ""};
        double neto = 0;
        double igv = 0;
        double total = 0;
        String msgError = "";
        try {
            if (Util.sesionEstaActiva(request)) {
                HttpSession session = request.getSession();
                Usuario usuario = (Usuario) session.getAttribute("Usuario");
                String empresa = (String) session.getAttribute("Empresa");
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                String ls_empresa = Util.nullCad(request.getParameter("numeroParte"));
                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                String seleccion = request.getParameter("seleccion");
                StringBuilder sbLista = new StringBuilder();
                new Trans_VentasDAO().carga_utilidad(seqTmp1, ls_fecha_ini, ls_fecha_fin);
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaUtilidad_documento(seqTmp1);
                sbLista.append("<table id=\"tablaCostodeVentas\" class=\"table table-striped\"  >");
                sbLista.append("<thead>");
                sbLista.append("<tr>");
                sbLista.append("<th class=\"text-center\">Serie</th>");
                sbLista.append("<th class=\"text-center\">Documento</th>");
                sbLista.append("<th class=\"text-center\">Fecha</th>");
                sbLista.append("<th class=\"text-center\">Moneda</th>");
                sbLista.append("<th class=\"text-center\">vvp</th>");
                sbLista.append("<th class=\"text-center\">Costo</th>");
                sbLista.append("<th class=\"text-center\">Utilidad</th>");
                sbLista.append("</tr>");
                sbLista.append("</thead>");
                if (listVentas != null && !listVentas.isEmpty()) {

                    String CadSelected = "";
                    long asiento = 0;

                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("moneda")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("vvp")))).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("costo")))).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("utilidad")))).append("</td>");
                        sbLista.append("</tr>");
                    }


                } else {
                    neto = 0;
                }
                sbLista.append("</table>");

                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("tabla", sbLista.toString());
                }
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objTabla", jsonObjTabla);
            sb.append(jsonObject.toString());
            out = response.getWriter();
            out.write(sb.toString());
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        LOGGER.info("<==== Fin Metodo: agregarItem ====>");
    }

    public void listaUtilidad_vendedor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjTotal = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", ""};
        double neto = 0;
        double igv = 0;
        double total = 0;
        String msgError = "";
        try {
            if (Util.sesionEstaActiva(request)) {
                HttpSession session = request.getSession();
                Usuario usuario = (Usuario) session.getAttribute("Usuario");
                String empresa = (String) session.getAttribute("Empresa");
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                String ls_empresa = Util.nullCad(request.getParameter("numeroParte"));
                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                String seleccion = request.getParameter("seleccion");
                StringBuilder sbLista = new StringBuilder();
                new Trans_VentasDAO().carga_utilidad(seqTmp1, ls_fecha_ini, ls_fecha_fin);
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaUtilidad_documento(seqTmp1);
                sbLista.append("<table id=\"tablaCostodeVentas\" class=\"table table-striped\"  >");
                sbLista.append("<thead>");
                sbLista.append("<tr>");
                sbLista.append("<th class=\"text-center\">Serie</th>");
                sbLista.append("<th class=\"text-center\">Documento</th>");
                sbLista.append("<th class=\"text-center\">Fecha</th>");
                sbLista.append("<th class=\"text-center\">vvp</th>");
                sbLista.append("<th class=\"text-center\">Costo</th>");
                sbLista.append("<th class=\"text-center\">Utilidad</th>");
                sbLista.append("</tr>");
                sbLista.append("</thead>");
                double tot_vvp = 0;
                double tot_cos = 0;
                double tot_uti = 0;
                if (listVentas != null && !listVentas.isEmpty()) {

                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("vvp")))).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("costo")))).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("utilidad")))).append("</td>");
                        sbLista.append("</tr>");
                    }
                } else {
                    neto = 0;
                }
                sbLista.append("</table>");

                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("tabla", sbLista.toString());
                }
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objTabla", jsonObjTabla);
            sb.append(jsonObject.toString());
            out = response.getWriter();
            out.write(sb.toString());
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        LOGGER.info("<==== Fin Metodo: agregarItem ====>");
    }

    public void listaVisualizaDocumento_detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjTotal = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", ""};

        String msgError = "";
        try {
            if (Util.sesionEstaActiva(request)) {
                HttpSession session = request.getSession();
                Usuario usuario = (Usuario) session.getAttribute("Usuario");
                String empresa = (String) session.getAttribute("Empresa");
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                String tipodocumento = Util.nullCad(request.getParameter("tipodocumento"));
                String serie = Util.nullCad(request.getParameter("serie"));
                String documento = Util.nullCad(request.getParameter("documento"));
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaUtilidad_documento_detalle(seqTmp1, tipodocumento, serie, documento);
                sbLista.append("<table id=\"c_tableDetalleDocumento\" class=\"table table-striped\"  >");
                sbLista.append("<thead>");
                sbLista.append("<tr>");
                sbLista.append("<th class=\"text-center\">Codigo</th>");
                sbLista.append("<th class=\"text-center\">Descripcion</th>");
                sbLista.append("<th class=\"text-center\">Cantidad</th>");
                sbLista.append("<th class=\"text-center\">vvp</th>");
                sbLista.append("<th class=\"text-center\">Costo</th>");
                sbLista.append("<th class=\"text-center\">vvp</th>");
                sbLista.append("<th class=\"text-center\">Costo</th>");
                sbLista.append("<th class=\"text-center\">Utilidad</th>");
                sbLista.append("</tr>");
                sbLista.append("</thead>");
                if (listVentas != null && !listVentas.isEmpty()) {

                    String CadSelected = "";
                    double utilidad = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("codigo_prod")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("descripcion")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("cantidad")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("vvp")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("costo")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("cal_vvp")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("cal_costo")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleAA(Util.nullDou(listVentas.get(i).get("utilidad")))).append("</td>");
                        utilidad += Util.nullDou(listVentas.get(i).get("utilidad"));
                        sbLista.append("</tr>");
                    }
                    sbLista.append("<tr>");
                    sbLista.append("<td class=\"text-center\">").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\">").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\">").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\">").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\">").append("").append("</td>");
                    sbLista.append("<td class=\"text-center\">").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\">").append("Total").append("</td>");
                    sbLista.append("<td class=\"text-right\">").append(Util.formatearDoubleAA(utilidad)).append("</td>");
                    sbLista.append("</tr>");
                }
                sbLista.append("</table>");

                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("tabla", sbLista.toString());
                }
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objTabla", jsonObjTabla);
            sb.append(jsonObject.toString());
            out = response.getWriter();
            out.write(sb.toString());
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        LOGGER.info("<==== Fin Metodo: agregarItem ====>");
    }
}
