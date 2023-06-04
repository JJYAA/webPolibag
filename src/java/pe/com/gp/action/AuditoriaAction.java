/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
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
import pe.com.gp.form.CobranzasForm;
import pe.com.gp.util.Util;

/**
 *
 * @author user
 */
public class AuditoriaAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();
    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializa";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }   
    
    public ActionForward init_documentos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            mappingFindForward = "init_documentos";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }  
    public void muestraAuditoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                String fechaIni = request.getParameter("fechaIni");
                String aux4212 = request.getParameter("aux4212");
                String auxtipo = request.getParameter("auxtipo");               
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().auditoriaListado(fechaIni, aux4212, auxtipo);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaAuditoria\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Codigo</th>");
                    sbLista.append("<th class=\"text-center\">Razon Social</th>");
                    sbLista.append("<th class=\"text-center\">F.DOCUMENTO</th>");
                    sbLista.append("<th class=\"text-center\">T.D.</th>");
                    sbLista.append("<th class=\"text-center\">Documento</th>");
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\">Pagado</th>");
                    sbLista.append("<th class=\"text-center\">Saldo</th>");
                    sbLista.append("<th class=\"text-center\">WEB</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("codigo")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechadocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("importe")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("pagado")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("saldo")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("web")).append("</td>");
                        sbLista.append("</tr>");
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                } else {
                    neto = 0;

                    jsonObjTotal.put("neto", 0);
                    jsonObjTotal.put("igv", 0);
                    jsonObjTotal.put("total", 0);
                }
                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                }
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objTotales", jsonObjTotal);
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
   
    public void muestraDocumentos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                StringBuilder sbLista = new StringBuilder();
                String documento = request.getParameter("documento");
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().muestraDocumentos(documento);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaAuditoria\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Sub</th>");
                    sbLista.append("<th class=\"text-center\">Asiento</th>");
                    sbLista.append("<th class=\"text-center\">Cuenta</th>");
                    sbLista.append("<th class=\"text-center\">Codigo</th>");
                    sbLista.append("<th class=\"text-center\">D/H</th>");                    
                    sbLista.append("<th class=\"text-center\">TD</th>");
                    sbLista.append("<th class=\"text-center\">Documento</th>");                    
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\">Fecha</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("sub")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("asiento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("cuenta")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("codigo")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("dh")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("importe1")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("importe2")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("importe3")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fecha")).append("</td>");
                        sbLista.append("</tr>");
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                } else {
                    neto = 0;

                    jsonObjTotal.put("neto", 0);
                    jsonObjTotal.put("igv", 0);
                    jsonObjTotal.put("total", 0);
                }
                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                }
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objTotales", jsonObjTotal);
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
