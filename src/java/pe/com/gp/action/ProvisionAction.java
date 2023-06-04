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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.com.gp.dao.CobranzaDAO;
import pe.com.gp.dao.PagosDAO;
import pe.com.gp.dao.ProvisionDAO;
import pe.com.gp.entity.ListaGenerica;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.CobranzasForm;
import pe.com.gp.form.ProvisionForm;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class ProvisionAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SEQ_TMP1_SESSION_KEY = "FacRepMosSeqTmp1_" + UUID.randomUUID().toString();

    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            ProvisionForm uform = (ProvisionForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaDocumento(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializa";
            cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }

    public void cargaListas(HttpServletRequest request, ProvisionForm uform) throws Exception {
        HttpSession session = request.getSession();
        List<ListaGenerica> listaPlanCuenta = new ProvisionDAO().listaPlanCuentaProvision("");
        List<ListaGenerica> listaTipoDocumento = new ProvisionDAO().tipo_documento("");
        request.setAttribute("listaPlanCuenta", listaPlanCuenta);
        request.setAttribute("listaTipoDocumento", listaTipoDocumento);
    }

    public void BuscarDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjDatos = new JSONObject();
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
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY)); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));                
                String ls_empresa = "";
                String ruc = request.getParameter("ruc").trim();
                String tipodocumento = request.getParameter("tipodocumento");
                String documento = request.getParameter("documento");

                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listDatos = new ProvisionDAO().buscaDocumento(empresa, ruc, tipodocumento, documento);
                if (listDatos.size() == 0) {
                    jsonObjDatos.put("fechadocumento", Util.fecha_dia());
                    jsonObjDatos.put("vencimiento", Util.fecha_dia());
                    jsonObjDatos.put("gravado", "1");
                    jsonObjDatos.put("moneda", "1");
                    jsonObjDatos.put("baseImponible", "0");
                    jsonObjDatos.put("igv", "0");
                    jsonObjDatos.put("total", "0");
                    jsonObjDatos.put("total", "asiento");
                    jsonObjDatos.put("asiento", "0");
                } else {
                    jsonObjDatos.put("fechadocumento", listDatos.get(0).get("fechadocumento"));
                    jsonObjDatos.put("vencimiento", listDatos.get(0).get("vencimiento"));
                    jsonObjDatos.put("gravado", listDatos.get(0).get("gravado"));
                    jsonObjDatos.put("moneda", listDatos.get(0).get("moneda"));
                    jsonObjDatos.put("baseImponible", listDatos.get(0).get("baseImponible"));
                    jsonObjDatos.put("igv", listDatos.get(0).get("igv"));
                    jsonObjDatos.put("total", listDatos.get(0).get("total"));
                    jsonObjDatos.put("asiento", listDatos.get(0).get("asiento"));
                }
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "exito");
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objDatos", jsonObjDatos);
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

    public void AdicionarItemProv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY)); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));                
                String ls_empresa = "";
                String cuenta = request.getParameter("cuenta").trim();
                String glosa = request.getParameter("glosa");
                Double debe = Util.nullDou(request.getParameter("debe"));
                Double haber = Util.nullDou(request.getParameter("haber"));

                StringBuilder sbLista = new StringBuilder();
                msgError = new ProvisionDAO().AdicionaItemProvision(empresa, seqTmp1, cuenta, glosa, debe, haber);
                jsonObjMsg = new JSONObject();
                if ("".equals(msgError)) {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                }

            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
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

    public void listaDocumentoProveedor_ProvisionTmp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));;
                StringBuilder sbLista = new StringBuilder();
                double debe = 0;
                double haber = 0;
                List<Map<String, Object>> listVentas = new ProvisionDAO().listaDocumentoProveedor_Provision_tempo(empresa, seqTmp1);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaAddItem\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Item</th>");
                    sbLista.append("<th class=\"text-center\">Cuenta</th>");
                    sbLista.append("<th class=\"text-center\">Descripcion</th>");
                    sbLista.append("<th class=\"text-center\">Debe</th>");
                    sbLista.append("<th class=\"text-center\">Haber</th>");
                    sbLista.append("<th class=\"text-center\"></th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;

                    for (int i = 0; i < listVentas.size(); i++) {
                        //CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("item")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("cuenta")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("t_cuenta")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("debe")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("haber")).append("</td>");
                        sbLista.append("<td class=\"text-center\">").append("<a href=\"#\" onclick=\"EliminaItem('").append(listVentas.get(i).get("item")).append("')\" class=\"btn btn-sm\"><span class=\"glyphicon glyphicon-remove\"></span> </a>").append("</td>");
                        sbLista.append("</tr>");
                        debe += Util.nullDou(listVentas.get(i).get("debe"));
                        haber += Util.nullDou(listVentas.get(i).get("haber"));
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                    msgError = "";
                } else {
                    jsonObjTabla.put("tabla", "");
                    jsonObjTotal.put("total", 0);
                }
                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                    jsonObjMsg.put("total", 0);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("total", debe + haber);
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

    public ActionForward conAsientoProvision(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: conAsientoVentas ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String empresa = (String) session.getAttribute("Empresa");
            ProvisionForm uform = (ProvisionForm) form;
            long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));

            String ls_error = "";
            int i = 0;
            String pDocumentoCliente = "";
            String ls_gravado = uform.getGravado().equals("on") ? "1" : "2";
            JSONObject jsonObjResult;
            String tipoMsg;
            String msg;
            String documento="";
            jsonObjResult = new ProvisionDAO().asientoProvision(
                    uform.getRucProveedor(),
                    uform.getTipodocumento(),
                    uform.getNumeroDocumento(),
                    uform.getFechaDocumento(),
                    uform.getFechaVencimiento(),
                    uform.getMoneda(),
                    ls_gravado,
                    uform.getBaseImponible(),
                    uform.getIgv(),
                    uform.getTotal(),
                    uform.getAsiento(),
                    seqTmp1);
            try {
                JSONArray jsonArrMsg = (JSONArray) jsonObjResult.get("mensaje");
                JSONObject jsonObjMsg = (JSONObject) jsonArrMsg.get(0);
                tipoMsg = Util.nullCad(jsonObjMsg.get("tipoMsg"));
                msg = Util.nullCad(jsonObjMsg.get("msg"));
                documento = Util.nullCad(jsonObjMsg.get("documento"));
            } catch (Exception e) {
                tipoMsg = "error";
                msg = "" + e;
            }
            if ("exito".equals(tipoMsg)) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito. ASIENTO NUMERO " + documento + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(ls_error, false));
            }
            cargaListas(request, uform);
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgProvision";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: conAsientoVentas ====>");
        return mapping.findForward(mappingFindForward);
    }

    public void detalle_provision(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjDatos = new JSONObject();
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
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY)); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));                
                String ls_empresa = "";
                String ruc = request.getParameter("ruc").trim();
                String tipodocumento = request.getParameter("tipodocumento");
                String documento = request.getParameter("documento");

                StringBuilder sbLista = new StringBuilder();
                new ProvisionDAO().cargaDetalleProvision(seqTmp1, ruc, tipodocumento, documento);
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "exito");
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objDatos", jsonObjDatos);
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

    public void EliminaItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY)); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));                
                String ls_empresa = "";
                Long item = Util.nullLon(request.getParameter("item"));
                StringBuilder sbLista = new StringBuilder();
                msgError = new ProvisionDAO().EliminaItem(seqTmp1, item);
                jsonObjMsg = new JSONObject();
                if ("".equals(msgError)) {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("msgError", msgError);
                } else {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                }

            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
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

    
            
    public ActionForward conEliminarAsiento(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: conAsientoVentas ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String empresa = (String) session.getAttribute("Empresa");
            ProvisionForm uform = (ProvisionForm) form;
            long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));

            String ls_error = "";
            ls_error = new ProvisionDAO().eliminaProvision(
                    uform.getRucProveedor(),
                    uform.getTipodocumento(),
                    uform.getNumeroDocumento() );
            
            if ("".equals(ls_error)) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito. </strong>", false));
            } else {
                errors.add("error", new ActionMessage(ls_error, false));
            }
            cargaListas(request, uform);
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgProvision";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: conAsientoVentas ====>");
        return mapping.findForward(mappingFindForward);
    }            
}
