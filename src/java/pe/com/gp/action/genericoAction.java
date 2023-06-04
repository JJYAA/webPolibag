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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.json.JSONObject;
import pe.com.gp.dao.GenericoDAO;
import pe.com.gp.dao.PagosDAO;
import pe.com.gp.entity.Usuario;
import pe.com.gp.util.Util;

/**
 *
 * @author ADMIN
 */
public class genericoAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SEQ_TMP1_SESSION_KEY = "FacRepMosSeqTmp1_" + UUID.randomUUID().toString();

    public void lista_proveedores(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));;
                String codigo = request.getParameter("txtcodigo").trim();
                StringBuilder sbLista = new StringBuilder();
                double pagado_sol = 0;
                double pagado_dol = 0;
                List<Map<String, Object>> listaProveedor = new GenericoDAO().listaProveedor(codigo);
                if (listaProveedor != null && !listaProveedor.isEmpty()) 
                {
                    sbLista.append("<table id=\"tablaProveedores\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Codigo</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">11</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    long asiento = 0;

                    for (int i = 0; i < listaProveedor.size(); i++) {
                        //CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listaProveedor.get(i).get("codigo")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listaProveedor.get(i).get("nombre")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append("1").append("</td>");
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                    msgError = "";
                } 
                else 
                {

                    jsonObjTabla.put("tabla", "");
                    jsonObjTotal.put("pagado_sol", 0);
                    jsonObjTotal.put("pagado_dol", 0);
                }
                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                    jsonObjMsg.put("pagado_sol", 0);
                    jsonObjMsg.put("pagado_dol", 0);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("pagado_sol", pagado_sol);
                    jsonObjMsg.put("pagado_dol", pagado_dol);
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
