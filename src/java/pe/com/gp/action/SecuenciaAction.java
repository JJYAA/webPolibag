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
import org.json.JSONObject;
import pe.com.gp.dao.GimDAO;
import pe.com.gp.dao.Trans_ComprasDAO;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.GimForm;
import pe.com.gp.form.SecuenciaForm;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class SecuenciaAction  extends DispatchAction {
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
            SecuenciaForm uform = (SecuenciaForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            session.setAttribute(SEQ_TMP2_SESSION_KEY, new Util().secuencia());            
            uform.setFlagMueOcuForm("muestra");                        
            mappingFindForward = "inicializa";

        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }    

    public void muestraSecuencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                String anho = request.getParameter("anho");
                String mes = request.getParameter("mes");
                StringBuilder sbLista = new StringBuilder();
                new GimDAO().crea_MesAnho(anho, mes);
                List<Map<String, Object>> listCompras = new GimDAO().muestraSecuuenciaMesAnho(anho, mes);
                if (listCompras != null && !listCompras.isEmpty()) {
                    sbLista.append("<table id=\"tablaSecuencia\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Tipo</th>");
                    sbLista.append("<th class=\"text-center\">Descripcion</th>");
                    sbLista.append("<th class=\"text-center\">Secuencia</th>");                    
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listCompras.size(); i++) {
                        CadSelected = listCompras.get(i).get("indice") + "|" + listCompras.get(i).get("documento") + "|" + listCompras.get(i).get("asiento") + "|" + listCompras.get(i).get("tipodocumento") + "|" + listCompras.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append("<input type=\"hidden\" id=\"tipo_comprobante\" name=\"tipo_comprobante\" value='" + listCompras.get(i).get("tipo_comprobante") + "'  > ").append("<input type=\"hidden\" id=\"anho\" name=\"anho\" value='" + listCompras.get(i).get("anho") + "'  > ").append(listCompras.get(i).get("tipo_comprobante")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append("<input type=\"hidden\" id=\"mes\" name=\"mes\" value='" + listCompras.get(i).get("mes") + "'  > ").append("<input type=\"hidden\" id=\"modificado\" name=\"modificado\" style=\"text-align: right;\"  >").append(listCompras.get(i).get("descripcion")).append("</td>");
                        sbLista.append("<td class=\"text-center\">").append("<input type=\"text\" id=\"secuencia\" name=\"secuencia\" style=\"text-align: right;\" size=\"4\"  maxlength=4   onchange=\"myCalculo(event)\"   value=\"" + listCompras.get(i).get("contador") + "\" onchange=\"myCalculo(event)\"  class=\"numeros\" >").append("</td>"); 
                        sbLista.append("</tr>");
                        neto += Util.nullDou(listCompras.get(i).get("baseImponible"));
                        igv += Util.nullDou(listCompras.get(i).get("igv"));
                        total += Util.nullDou(listCompras.get(i).get("total"));
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                } else {
                    neto = 0;
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


    
 public ActionForward grabaSecuencia(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: grabaPath ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            SecuenciaForm uform = (SecuenciaForm) form;
            String selected  = Util.nullCad(request.getParameter("selected"));       
            String resultado="";
            String items[] = selected.split(",");
            boolean salir = false;
            int i =0;
            String pGlosa="";
            while ((i<=items.length -1)&&(!salir))      
            {
                String element[] = items[i].split("\\|"); 
                new GimDAO().grabaSecuencia(element[0],element[1],element[2],element[3]);    
                i++;
            }      
            
            resultado="";
            if ("".equals(resultado)) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito" + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(resultado, false));
            }

            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgSeuencia";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== FIN Metodo: grabaPath ====>");
        return mapping.findForward(mappingFindForward);
    }    
    
    
}
