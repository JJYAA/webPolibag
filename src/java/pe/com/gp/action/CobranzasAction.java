/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.json.JSONObject;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.dao.AsientoDAO;
import pe.com.gp.dao.ClienteFacturaDAO;
import pe.com.gp.dao.CobranzaDAO;
import pe.com.gp.dao.GenericoDAO;
import pe.com.gp.dao.Trans_VentasDAO;
import pe.com.gp.entity.ListaGenerica;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.CobranzasForm;
import pe.com.gp.form.Trans_VentasForm;
import pe.com.gp.util.Constantes;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class CobranzasAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SEQ_TMP1_SESSION_KEY = "FacRepMosSeqTmp1_" + UUID.randomUUID().toString();

    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializa";
            cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }

    public ActionForward cobranzasNuevo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "cobranzasNuevo";
            cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }    
    
    public ActionForward pipili(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializaAsiento ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            cargaListas(request, uform);
            mappingFindForward = "pipili";           
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializaAsiento ====>");
        return mapping.findForward(mappingFindForward);
    }    
        
    
    public ActionForward inicializaAsiento(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializaAsiento ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializaAsiento";
            cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializaAsiento ====>");
        return mapping.findForward(mappingFindForward);
    }

    public ActionForward init_ListaDepositos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializaAsiento ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaFin(Util.fecha_dia());
            uform.setFechaIni(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "init_ListaDepositos";
            cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializaAsiento ====>");
        return mapping.findForward(mappingFindForward);
    }    
   
    public ActionForward init_corte_cobrar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializaAsiento ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            ActionErrors errors = new ActionErrors();
            CobranzasForm uform = (CobranzasForm) form;
            session.setAttribute(SEQ_TMP1_SESSION_KEY, new Util().secuencia());
            uform.setFechaIni("01/03/2023");//Util.fecha_dia()
            uform.setFechaFin(Util.fecha_dia());
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "init_corte_cobrar";
            cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializaAsiento ====>");
        return mapping.findForward(mappingFindForward);
    }       
    public void muestraListaCobranzas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: muestraListaCobranzas ====>");
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
                String ls_empresa = Util.nullCad(request.getParameter("numeroParte"));
                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                String ls_cliente = request.getParameter("codigo");
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaTrans_Ventas_Cobranzas(ls_empresa, ls_fecha_ini, ls_fecha_fin, ls_cliente);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaRegistroVenta\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Agencia</th>");
                    sbLista.append("<th class=\"text-center\">Serie</th>");
                    sbLista.append("<th class=\"text-center\">Documento</th>");
                    sbLista.append("<th class=\"text-center\">Fecha</th>");
                    sbLista.append("<th class=\"text-center\">Moneda</th>");
                    sbLista.append("<th class=\"text-center\">Descripcion</th>");
                    sbLista.append("<th class=\"text-center\">Documento</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Neto</th>");
                    sbLista.append("<th class=\"text-center\">IGV</th>");
                    sbLista.append("<th class=\"text-center\">Total</th>");
                    sbLista.append("<th class=\"text-center\">Asiento</th>");
                    sbLista.append("<th class=\"text-center\">Pagos</th>");
                    sbLista.append("<th class=\"text-center\">Saldo</th>");
                    //bLista.append("<th class=\"text-center\">Operacion</th>");
                    //sbLista.append("<th class=\"text-center\">&nbsp;</th>");
                    sbLista.append("<th class=\"text-center\">&nbsp;</th>");
                    //sbLista.append("<th class=\"text-center\">&nbsp;</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected =   listVentas.get(i).get("documentocliente") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("saldo");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("agencia")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("moneda")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("descripcion")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("documentocliente")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("baseImponible")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("igv")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total")))).append("</td>");

                        sbLista.append("<td>").append(listVentas.get(i).get("asiento")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("pago")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("saldo")))).append("</td>");
                        //sbLista.append("<td class=\"text-right\" >").append(Util.nullLon(listVentas.get(i).get("operacion"))).append("</td>");
                        //sbLista.append("<td>").append("<button type=\"button\"  onclick=\"Pago('" + listVentas.get(i).get("tipodocumento") + "','" + listVentas.get(i).get("serie") + "','" + listVentas.get(i).get("documento") + "','" + listVentas.get(i).get("documentocliente") + "','" + Util.nullDou(listVentas.get(i).get("total")) + "')\"  class=\"btn btn-primary\"><i class=\"far fa-caret-square-right\">").append("</td>");
                        
                        //sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbx\" class=\"chkbx\" onclick=seleccinados()  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                        sbLista.append("<td class=\"text-center\">").append("<a href=\"#\" onclick=\"Pago('" +  listVentas.get(i).get("tipodocumento") + "','" + listVentas.get(i).get("serie") + "','" + listVentas.get(i).get("documento") + "','" + listVentas.get(i).get("documentocliente") + "','" + Util.nullDou(listVentas.get(i).get("total")) + "')\" ><span class=\"glyphicon glyphicon-pencil\"></span></a>").append("</td>");                        
                        //sbLista.append("<td class=\"text-center\">").append("<a href=\"#\" onclick=\"PagoMultiple('" +  listVentas.get(i).get("tipodocumento") + "','" + listVentas.get(i).get("serie") + "','" + listVentas.get(i).get("documento") + "','" + listVentas.get(i).get("documentocliente") + "','" + Util.nullDou(listVentas.get(i).get("total")) + "')\" ><span class=\"glyphicon glyphicon-pencil\"></span><span class=\"glyphicon glyphicon-pencil\"></span></a>").append("</td>");                        
                        sbLista.append("</tr>");
                        neto += Util.nullDou(listVentas.get(i).get("baseImponible"));
                        igv += Util.nullDou(listVentas.get(i).get("igv"));
                        total += Util.nullDou(listVentas.get(i).get("total"));
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                    jsonObjTotal.put("neto", Util.formatearDoubleA(neto));
                    jsonObjTotal.put("igv", Util.formatearDoubleA(igv));
                    jsonObjTotal.put("total", Util.formatearDoubleA(total));
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
        LOGGER.info("<==== Fin Metodo: muestraListaCobranzas ====>");
    }

    public void adicionarItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                //String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));                
                String ls_empresa = "";
                String banco = Util.nullCad(request.getParameter("banco"));
                String formapago = request.getParameter("formapago");
                String importe = request.getParameter("importe");
                String deposito = request.getParameter("deposito");
                String moneda = request.getParameter("moneda");
                String fechaOperacion = request.getParameter("fechaOperacion");
                String codigoCliente = request.getParameter("codigoCliente").trim();
                String tipoDocumento = request.getParameter("tipoDocumento");
                String serieDocumento = request.getParameter("serieDocumento");
                String numeroDocumento = request.getParameter("numeroDocumento");
                Long Item = Util.nullLon(request.getParameter("item"));
                total = Util.nullDou(importe);
                StringBuilder sbLista = new StringBuilder();
                msgError = new CobranzaDAO().ActualizaItemsCobranza(empresa, seqTmp1, Item, banco, formapago, total, deposito, moneda, fechaOperacion, codigoCliente, tipoDocumento, serieDocumento, numeroDocumento, usuario.getCodigo());
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

    public void cargaListas(HttpServletRequest request, CobranzasForm uform) throws Exception {
        HttpSession session = request.getSession();
        List<ListaGenerica> listaBancos = new CobranzaDAO().listaBancos("");
        request.setAttribute("listaBancos", listaBancos);
    }

    public void listaDocumentoCliente_Cobranzas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                String codigoCliente = request.getParameter("codigoCliente").trim();
                String tipoDocumento = request.getParameter("tipoDocumento");
                String serieDocumento = request.getParameter("serieDocumento");
                String numeroDocumento = request.getParameter("numeroDocumento");
                StringBuilder sbLista = new StringBuilder();
                double pagado = 0;
                List<Map<String, Object>> listVentas = new CobranzaDAO().listaDocumentoCliente_Cobranzas(empresa, seqTmp1, codigoCliente, tipoDocumento, serieDocumento, numeroDocumento);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaCobranzaItems\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Banco</th>");
                    sbLista.append("<th class=\"text-center\">Moneda</th>");
                    sbLista.append("<th class=\"text-center\">FechaPago</th>");
                    sbLista.append("<th class=\"text-center\">Forma Pago</th>");
                    sbLista.append("<th class=\"text-center\">#Deposito</th>");
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\">Asiento</th>");
                    sbLista.append("<th class=\"text-center\">Operacion</th>");
                    sbLista.append("<th class=\"text-center\"></th>");
                    sbLista.append("<th class=\"text-center\"></th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;

                    for (int i = 0; i < listVentas.size(); i++) {
                        //CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("nombre_banco")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("t_moneda")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaPago")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("t_formapago")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("deposito")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("importe")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("asiento")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("nro_operacion")).append("</td>");   
                        if (Util.nullLon(listVentas.get(i).get("nro_operacion"))==0) {
                            sbLista.append("<td class=\"text-center\">").append("<a href=\"#\" onclick=\"Editar('" +  listVentas.get(i).get("indice") + "','" +  listVentas.get(i).get("importe") +    "')\" ><span class=\"glyphicon glyphicon-pencil\"></span></a>").append("</td>");                        
                        } else
                        {
                             sbLista.append("<td>").append("").append("</td>"); 
                        }
                        
                        if (Util.nullLon(listVentas.get(i).get("nro_operacion"))==0) {
                            if (Util.nullLon(listVentas.get(i).get("asiento"))==0)
                                sbLista.append("<td class=\"text-center\">").append("<a href=\"#\" onclick=\"Eliminar('").append(listVentas.get(i).get("indice")).append("')\" class=\"btn btn-sm\"><span class=\"glyphicon glyphicon-remove\"></span> </a>").append("</td>");                        
                            else
                                sbLista.append("<td class=\"text-center\">").append("<a href=\"#\"  disabled=\"true\"  class=\"btn btn-sm\"><span class=\"glyphicon glyphicon-remove\"></span> </a>").append("</td>");                                                                            
                        } else
                        {                            
                            sbLista.append("<td>").append("").append("</td>"); 
                        }
                        

                        sbLista.append("</tr>");
                        pagado += Util.nullDou(listVentas.get(i).get("importe"));
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                    msgError = "";
                } else {
                    neto = 0;
                    jsonObjTabla.put("tabla", "");
                    jsonObjTotal.put("pagado", 0);
                }
                // Mensajes
                jsonObjMsg = new JSONObject();
                if (!"".equals(Util.nullCad(msgError))) {
                    jsonObjMsg.put("tipoMsg", "error");
                    jsonObjMsg.put("msgError", msgError);
                    jsonObjMsg.put("pagado", 0);
                } else {
                    jsonObjMsg.put("tipoMsg", "exito");
                    jsonObjMsg.put("pagado", pagado);
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

    public void GrabarCobranza(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                //String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));                
                String ls_empresa = "";
                String banco = Util.nullCad(request.getParameter("banco"));
                String formapago = request.getParameter("formapago");
                String importe = request.getParameter("importe");
                String deposito = request.getParameter("deposito");
                String moneda = request.getParameter("moneda");
                String fechaOperacion = request.getParameter("fechaOperacion");
                String codigoCliente = request.getParameter("codigoCliente").trim();
                String tipoDocumento = request.getParameter("tipoDocumento");
                String serieDocumento = request.getParameter("serieDocumento");
                String numeroDocumento = request.getParameter("numeroDocumento");
                Long Item = Util.nullLon(request.getParameter("item"));                    
                total = Util.nullDou(importe);
                StringBuilder sbLista = new StringBuilder();
                msgError = new CobranzaDAO().GrabarCobranza(empresa, seqTmp1, codigoCliente, tipoDocumento, serieDocumento, numeroDocumento, usuario.getCodigo());
                //pstmt = cn.prepareStatement(sqlQuery);
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

    public void cargaItemCobranza(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                //String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));                
                String ls_empresa = "";
                String codigoCliente = request.getParameter("codigoCliente").trim();
                String tipoDocumento = request.getParameter("tipoDocumento");
                String serieDocumento = request.getParameter("serieDocumento");
                String numeroDocumento = request.getParameter("numeroDocumento");
                StringBuilder sbLista = new StringBuilder();
                msgError = new CobranzaDAO().cargaItemCobranza(empresa, seqTmp1, codigoCliente, tipoDocumento, serieDocumento, numeroDocumento);
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

    public void EliminarItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                //String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));                
                String ls_empresa = "";
                String codigoCliente = request.getParameter("codigoCliente").trim();
                String tipoDocumento = request.getParameter("tipoDocumento");
                String serieDocumento = request.getParameter("serieDocumento");
                String numeroDocumento = request.getParameter("numeroDocumento");
                long indice = Util.nullLon(request.getParameter("indice"));
                StringBuilder sbLista = new StringBuilder();
                msgError = new CobranzaDAO().ElimimaItemCobranza(empresa, seqTmp1, codigoCliente, tipoDocumento, serieDocumento, numeroDocumento, indice);
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

    public void muestraListaPagosRealizados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                String ls_empresa = Util.nullCad(request.getParameter("numeroParte"));
                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                String ls_cliente = request.getParameter("codigo");
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().muestraListaPagosRealizados(ls_empresa, ls_fecha_ini, ls_fecha_fin, ls_cliente);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaRegistroVenta\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">TD</th>");
                    sbLista.append("<th class=\"text-center\">Serie</th>");
                    sbLista.append("<th class=\"text-center\">Documento</th>");
                    sbLista.append("<th class=\"text-center\">Fecha</th>");
                    sbLista.append("<th class=\"text-center\">Codigo</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Sub Diario</th>");
                    sbLista.append("<th class=\"text-center\">Forma Pago</th>");
                    sbLista.append("<th class=\"text-center\">Asiento</th>");
                    sbLista.append("<th class=\"text-center\">Operacion</th>");
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\">").append("Asiento").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodos\" name=\"chkbxTodos\" class=\"chkbxTodos\" onclick=\"seleccinadosTodos()\" />").append("</th>");
                    sbLista.append("<th class=\"text-center\">").append("Eliminar").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodosDel\" name=\"chkbxTodosDel\" class=\"chkbxTodosDel\" onclick=\"seleccinadosElimina()\" />").append("</th>");
                    sbLista.append("<th class=\"text-center\">").append("Xls").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodosXls\" name=\"chkbxTodosXls\" class=\"chkbxTodosXls\" onclick=\"seleccinadosTodosXls()\" />").append("</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("documentocliente") + "|" + listVentas.get(i).get("item");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDocumento")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("documentocliente")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("subdiario")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("t_formapago")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("asiento")).append("</td>");
                        if (Util.nullLon(listVentas.get(i).get("nro_operacion"))==0){
                            sbLista.append("<td class=\"text-right\">").append(listVentas.get(i).get("nro_operacion")).append("</td>");  //listVentas.get(i).get("nro_operacion")
                        }
                        else
                        {
                            sbLista.append("<td class=\"text-right\">").append("<a href=\"CobranzasAction.do?operacion=pipili&num_ope=" + listVentas.get(i).get("nro_operacion") + "\" >" +listVentas.get(i).get("nro_operacion")  + "</a>").append("</td>");  //listVentas.get(i).get("nro_operacion")                            
                        }
                        
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total")))).append("</td>");
                        asiento = Util.nullLon(listVentas.get(i).get("asiento"));
                        if (Util.nullLon(listVentas.get(i).get("nro_operacion"))==0){                        
                            if (asiento == 0) {
                                sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbx\" class=\"chkbx\" onclick=seleccinados()  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                            } else {
                                sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbx\" disabled=\"true\" class=\"chkbxTodos\" onclick=seleccinados()  data-selected=\"").append(CadSelected).append("\"  >").append("</td>");
                            }

                            if (asiento == 0) {
                                sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxDel\" disabled=\"true\" class=\"chkbxTodosDel\" onclick=seleccinadosDel() data-selected=\"").append(CadSelected).append("\"  >").append("</td>");
                            } else {
                                sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxDel\" class=\"chkbxDel\" onclick=seleccinadosDel()  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                            }
                        } 
                        else 
                        {
                            sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxDel\" disabled=\"disabled\"  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                            sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxDel\" disabled=\"disabled\"  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                        }
                        
                        if (asiento == 0) {
                            sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxXls\" disabled=\"true\" class=\"chkbxTodosXls\" onclick=seleccinados()  data-selected=\"").append(CadSelected).append("\"  >").append("</td>");

                        } 
                        else 
                        {
                            sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxXls\" class=\"chkbxXls\" onclick=seleccinadosXls()  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                        }

                        sbLista.append("</tr>");

                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                    jsonObjTotal.put("neto", Util.formatearDoubleA(neto));
                    jsonObjTotal.put("igv", Util.formatearDoubleA(igv));
                    jsonObjTotal.put("total", Util.formatearDoubleA(total));
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
    

    
    

    public ActionForward conAsientoCobranza(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: conAsientoVentas ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String empresa = (String) session.getAttribute("Empresa");
            CobranzasForm uform = (CobranzasForm) form;
            String docSelected = uform.getDocSelected();
            AsientoDAO asiento = new AsientoDAO();
            String[] arrSelected = docSelected.split(",");
            int i = 0;
            String msg;
            long pItem = 0;
            String pAgencia = "";
            String pSerie = "";
            String pDocumento = "";
            String pUsuario = "SA";
            String ptipo_comprobante = "21";
            String pTipoDocumento = "00";
            String pDocumentoCliente = "";
            String pPeriodo = uform.getFechaFin().substring(3, 5);
            String pAnho = uform.getFechaFin().substring(6, 10);
            Long ln_asiento = new Long(0);
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[2];
                pDocumento = AuxItem[3];
                pTipoDocumento = AuxItem[1];
                pDocumentoCliente = AuxItem[5].trim();
                pItem = Util.nullLon(AuxItem[6]);

                //  DBFReader reader = null;
                String ls_ACODANE = "";
                String ls_AVANEXO = "";
                try {
                    String ls_error = new CobranzaDAO().AsientoCobranza(empresa, pAgencia, pSerie, pDocumento, pPeriodo, pUsuario, ln_asiento, ptipo_comprobante, pTipoDocumento, pAnho, pItem, pDocumentoCliente);
                } catch (DBFException e) {
                    e.printStackTrace();
                    LOGGER.error("GP.ERROR: {}", e.toString());
                } catch (IOException e) {
                    LOGGER.error("GP.ERROR: {}", e.toString());
                } finally {
                    //DBFUtils.close(reader);
                }

                i++;
            }

            if (!salir) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito" + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(resultCalculos, false));
            }

            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgAsientoCobranza";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: conAsientoVentas ====>");
        return mapping.findForward(mappingFindForward);
    }

    public ActionForward conEliminarAsiento(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String empresa = (String) session.getAttribute("Empresa");
            CobranzasForm uform = (CobranzasForm) form;
            String docSelected = uform.getDocSelectedDel();
            AsientoDAO asiento = new AsientoDAO();
            String[] arrSelected = docSelected.split(",");
            int i = 0;
            String msg;
            String pAgencia = "";
            String pSerie = "";
            String pDocumento = "";
            String pUsuario = "SA";
            String pTipo_Comprobante = "21";
            String  pAsiento = "";
            String pTipoDocumento = "";
            String pDocumentoCliente = "";
            long pItem = 0;
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[2];
                pDocumento = AuxItem[3];
                pTipoDocumento = AuxItem[1];
                pDocumentoCliente = AuxItem[5].trim();
                pAsiento = AuxItem[4];
                pItem = Util.nullLon(AuxItem[6]);
                String ls_error = new CobranzaDAO().AsientoDesactiva(empresa, pAgencia, pSerie, pDocumento, pUsuario, pAsiento, pTipo_Comprobante, pTipoDocumento, pDocumentoCliente, pItem);
                i++;
            }

            if (!salir) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito" + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(resultCalculos, false));
            }

            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgAsientoCobranza";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }

    public void exportarExcel_cobranzas(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            int indice = 0;
            HttpSession session = request.getSession();
            CobranzasForm uform = (CobranzasForm) form;
            String path = request.getServletContext().getRealPath("/");
            String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
            String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
            int nRow = 0;
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Migrar Cobranzas");

            Connection cn = new ConectaSQL().connection();

            long seqTmp1 = new Util().secuencia(); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
            String docSelected = uform.getDocSelectedXls();
            AsientoDAO asiento = new AsientoDAO();
            String[] arrSelected = docSelected.split(",");
            int i = 0;
            String msg;
            String pAgencia = "";
            String pSerie = "";
            String pDocumento = "";
            String pUsuario = "SA";
            String pTipo = "05";
            String pTipoDocumento = "00";
            String pDocumentoCliente = "";
            String pPeriodo = uform.getFechaFin().substring(3, 5);
            String pAnho = uform.getFechaFin().substring(6, 10);
            String  la_asiento ;
            String ls_item;
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            java.sql.Date date = new java.sql.Date(0);
            String sqlQuery = "insert into asientos_tmp values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(sqlQuery);
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[2];
                pDocumento = AuxItem[3];
                pTipoDocumento = AuxItem[1];
                pDocumentoCliente = AuxItem[5].trim();
                la_asiento = AuxItem[4];
                ls_item = AuxItem[6];
                pstmt.setLong(1, seqTmp1);    //1
                pstmt.setString(2, null);   //2
                pstmt.setString(3, pDocumento);   //3
                pstmt.setString(4, pPeriodo);   //4
                pstmt.setString(5, "");   //5
                pstmt.setString(6, la_asiento);   //6
                pstmt.setString(7, ""); //7              
                pstmt.setString(8, pSerie);   //8
                pstmt.setString(9, "");   //9
                pstmt.setDate(10, date.valueOf("1998-1-17"));   //10
                pstmt.setString(11, "");           //11
                pstmt.setDate(12, date.valueOf("1998-1-17"));           //12
                pstmt.setString(13, "");           //13
                pstmt.setString(14, pTipoDocumento);           //14
                pstmt.setString(15, pAnho);           //15
                pstmt.setString(16, "");           //16                             
                pstmt.setString(17, "");           //17
                pstmt.setString(18, "05");           //18    
                pstmt.setString(19, pDocumentoCliente);           //18   
                pstmt.addBatch();
                i++;
            }
            int[] result = pstmt.executeBatch();
            if (pstmt != null) {
                pstmt.close();
            }
            if (cn != null) {
                cn.close();
            }
            String ls_asiento = "";
            String ls_error = new CobranzaDAO().creaItemsTemporal(seqTmp1, "", ls_fecha_ini, ls_fecha_fin);
            CreationHelper helper = wb.getCreationHelper();
            //Drawing drawing = sheet.createDrawingPatriarch();
            InputStream inputStream;// = new FileInputStream(path + Constantes.DIRECTORIO_IMAGENES + "/bg-foto_Real.jpg");
            byte[] bytes; // = IOUtils.toByteArray(inputStream);
            int itemCol[] = {
                5500,
                4500,
                3500,
                6000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                5500,
                5500,
                5500,
                5500,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                3000,
                5500,
                5500,
                5500,
                5500,
                3000,
                3000,
                3000
            };
            for ( i = 0; i < itemCol.length; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, itemCol[i]);
            }

            Row row = sheet.createRow(nRow);
            row.createCell(0).setCellValue("Campo");
            row.createCell(1).setCellValue("Sub Diario");
            row.createCell(2).setCellValue("Número de Comprobante");
            row.createCell(3).setCellValue("Fecha de Comprobante");
            row.createCell(4).setCellValue("Código de Moneda");
            row.createCell(5).setCellValue("Glosa Principal");
            row.createCell(6).setCellValue("Tipo de Cambio");
            row.createCell(7).setCellValue("Tipo de Conversión");
            row.createCell(8).setCellValue("Flag de Conversión de Moneda");
            row.createCell(9).setCellValue("Fecha Tipo de Cambio");
            row.createCell(10).setCellValue("Cuenta Contable");
            row.createCell(11).setCellValue("Código de Anexo");
            row.createCell(12).setCellValue("Código de Centro de Costo");
            row.createCell(13).setCellValue("Debe / Haber");
            row.createCell(14).setCellValue("Importe Original");
            row.createCell(15).setCellValue("Importe en Dólares");
            row.createCell(16).setCellValue("Importe en Soles");
            row.createCell(17).setCellValue("Tipo de Documento");
            row.createCell(18).setCellValue("Número de Documento");
            row.createCell(19).setCellValue("Fecha de Documento");
            row.createCell(20).setCellValue("Fecha de Vencimiento");
            row.createCell(21).setCellValue("Código de Area");
            row.createCell(22).setCellValue("Glosa Detalle");
            row.createCell(23).setCellValue("Código de Anexo Auxiliar");
            row.createCell(24).setCellValue("Medio de Pago");
            row.createCell(25).setCellValue("Tipo de Documento de Referencia");
            row.createCell(26).setCellValue("Número de Documento Referencia");
            row.createCell(27).setCellValue("Fecha Documento Referencia");
            row.createCell(28).setCellValue("Nro Máq. Registradora Tipo Doc. Ref.");
            row.createCell(29).setCellValue("Base Imponible Documento Referencia");
            row.createCell(30).setCellValue("IGV Documento Provisión");
            row.createCell(31).setCellValue("Tipo Referencia en estado MQ");

            row.createCell(32).setCellValue("Número Serie Caja Registradora");
            row.createCell(33).setCellValue("Fecha de Operación");
            row.createCell(34).setCellValue("Tipo de Tasa");
            row.createCell(35).setCellValue("Tasa Detracción/Percepción");
            row.createCell(36).setCellValue("Importe Base Detracción/Percepción Dólares");
            row.createCell(37).setCellValue("Importe Base Detracción/Percepción Soles");
            row.createCell(38).setCellValue("Tipo Cambio para 'F'");
            row.createCell(39).setCellValue("Importe de IGV sin derecho crédito fiscal");
            nRow = 1;
            row = sheet.createRow(nRow);
            nRow = 2;
            row = sheet.createRow(nRow);

            String ls_Sql = "";
            Statement stmt = null;
            String codigoProducto = "";
            nRow = 3;
            int nPosImg = 1;

            List<Map<String, Object>> listTemporal = new CobranzaDAO().listaItems_CobranzaXLS(seqTmp1);
            if (listTemporal.size() > 0) {
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.setHeight((short) 250);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("tamanho")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("subdiario")));
                    row.createCell(2).setCellValue(Util.nullCad(obj.get("comprobante")));
                    row.createCell(3).setCellValue(Util.nullCad(obj.get("fechacomprobante")));
                    row.createCell(4).setCellValue(Util.nullCad(obj.get("moneda")));
                    row.createCell(5).setCellValue(Util.nullCad(obj.get("glosa")));
                    row.createCell(6).setCellValue(Util.nullCad(obj.get("tipocambio")));
                    row.createCell(7).setCellValue(Util.nullCad(obj.get("tipoconversion")));
                    row.createCell(8).setCellValue(Util.nullCad(obj.get("flagconversionmd")));
                    row.createCell(9).setCellValue(Util.nullCad(obj.get("tipocambiofecha")));
                    row.createCell(10).setCellValue(Util.nullCad(obj.get("cuenta_contable")));
                    row.createCell(11).setCellValue(Util.nullCad(obj.get("codigoanexo")));
                    row.createCell(12).setCellValue(Util.nullCad(obj.get("centrocosto")));
                    row.createCell(13).setCellValue(Util.nullCad(obj.get("debehaber")));
                    row.createCell(14).setCellValue(Util.nullCad(obj.get("importeorigen")));
                    row.createCell(15).setCellValue(Util.nullCad(obj.get("importedolar")));
                    row.createCell(16).setCellValue(Util.nullCad(obj.get("importesoles")));
                    row.createCell(17).setCellValue(Util.nullCad(obj.get("tipodocumento")));
                    row.createCell(18).setCellValue(Util.nullCad(obj.get("numerodocumento")));
                    row.createCell(19).setCellValue(Util.nullCad(obj.get("fechadocumento")));
                    row.createCell(20).setCellValue(Util.nullCad(obj.get("fechavencimiento")));
                    row.createCell(21).setCellValue(Util.nullCad(obj.get("codigoarea")));
                    row.createCell(22).setCellValue(Util.nullCad(obj.get("glosadetalle")));
                    row.createCell(23).setCellValue(Util.nullCad(obj.get("codigo_aux_anexo")));
                    row.createCell(24).setCellValue(Util.nullCad(obj.get("medio_pago")));
                    row.createCell(25).setCellValue(Util.nullCad(obj.get("tipodocumentoRef")));
                    row.createCell(26).setCellValue(Util.nullCad(obj.get("numerodocRef")));
                    row.createCell(27).setCellValue(Util.nullCad(obj.get("fechadocumentoref")));
                    row.createCell(28).setCellValue(Util.nullCad(obj.get("nromaqregistradora")));
                    row.createCell(29).setCellValue(Util.nullCad(obj.get("baseimponibleref")));
                    row.createCell(30).setCellValue(Util.nullCad(obj.get("igvdocumentoprovision")));
                    row.createCell(31).setCellValue(Util.nullCad(obj.get("tipoRefestadoMQ")));
                    row.createCell(32).setCellValue(Util.nullCad(obj.get("numeroSerieCajaRegistradora")));
                    row.createCell(33).setCellValue(Util.nullCad(obj.get("fechaoperacion")));
                    row.createCell(34).setCellValue(Util.nullCad(obj.get("tipodetasa")));
                    row.createCell(35).setCellValue(Util.nullCad(obj.get("tasadetraccion")));
                    row.createCell(36).setCellValue(Util.nullCad(obj.get("importeDetr_Percep_dol")));
                    row.createCell(37).setCellValue(Util.nullCad(obj.get("importeDetr_Percep_sol")));
                    row.createCell(38).setCellValue(Util.nullCad(obj.get("TipoCambio_F")));
                    row.createCell(39).setCellValue(Util.nullCad(obj.get("igv_sin_dereho_cre_fiscal")));
                    row.createCell(40).setCellValue(Util.nullCad(obj.get("tipocambiofecha")));
                    nRow++;
                }
            }

            FileOutputStream fileOut = null;
            String nombreFile = "cobranzas " + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
            fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
            wb.write(fileOut);
            fileOut.close();
            response.setContentType("text/plain");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
            FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
            ServletOutputStream out = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException ioex) {
            LOGGER.error("GP.ERROR: {}", ioex.toString());

        }

    }

    
    
    public void muestraListaDepositos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: muestraListaCobranzas ====>");
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

                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new CobranzaDAO().listado_depositos(ls_fecha_ini, ls_fecha_fin);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaDepositos\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Cliente</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Banco</th>");
                    sbLista.append("<th class=\"text-center\">Fecha Deposito</th>");
                    sbLista.append("<th class=\"text-center\">Tipo Documento</th>");
                    sbLista.append("<th class=\"text-center\">Documento</th>");
                    sbLista.append("<th class=\"text-center\">Forma pago</th>");
                    sbLista.append("<th class=\"text-center\">Importe</th>");
                    sbLista.append("<th class=\"text-center\"># Deposito</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("codigoCliente")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("razonSocial")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("banco")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDeposito")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipoDocumento")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("forma_pago")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(listVentas.get(i).get("importe")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("deposito")).append("</td>");                        
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
        LOGGER.info("<==== Fin Metodo: muestraListaCobranzas ====>");
    }
      
    public void muestraExcelDepositos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("My Sample Excel");
            int indice = 0;
            Connection cn = new ConectaSQL().connection();
            long seqTmp2 = new Util().secuencia();
            String pDocumentoCliente = "";
            String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
            String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));         
            List<Map<String, Object>> listTemporal = new CobranzaDAO().listado_depositos(ls_fecha_ini, ls_fecha_fin);
            if (listTemporal.size() > 0) {

                CreationHelper helper = wb.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();

                InputStream inputStream;// = new FileInputStream(path + Constantes.DIRECTORIO_IMAGENES + "/bg-foto_Real.jpg");
                byte[] bytes; // = IOUtils.toByteArray(inputStream);
                int itemCol[] = {
                    5500,
                    4500,
                    3500,
                    6000,
                    3000,
                    3000,
                    3000,
                    3000,
                    3000,
                    3000
                };
                for (int i = 0; i < itemCol.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, itemCol[i]);
                }
                int nRow = 0;

                Row row = sheet.createRow(nRow);
                row.createCell(0).setCellValue("Cliente");
                row.createCell(1).setCellValue("Razon social");
                row.createCell(2).setCellValue("Banco");
                row.createCell(3).setCellValue("Fecha deposito");
                row.createCell(4).setCellValue("Tipo documento");
                row.createCell(5).setCellValue("Documento");
                row.createCell(6).setCellValue("Forma Pago");
                row.createCell(7).setCellValue("Importe");
                row.createCell(8).setCellValue("#Deposito");

                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 1;
                int nPosImg = 1;
                indice = 0;
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("codigoCliente")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("razonSocial")));
                    row.createCell(2).setCellValue(Util.nullCad(obj.get("banco")));
                    row.createCell(3).setCellValue(Util.nullCad(obj.get("fechaDeposito")));
                    row.createCell(4).setCellValue(Util.nullCad(obj.get("tipoDocumento")));
                    row.createCell(5).setCellValue(Util.nullCad(obj.get("documento")));
                    row.createCell(6).setCellValue(Util.nullCad(obj.get("forma_pago")));
                    row.createCell(7).setCellValue(Util.nullCad(obj.get("importe")));
                    row.createCell(8).setCellValue(Util.nullCad(obj.get("deposito")));
                    nRow++;
                }
                FileOutputStream fileOut = null;
                String nombreFile = "Deposito " + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
                fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
                wb.write(fileOut);
                fileOut.close();
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
                FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
                ServletOutputStream out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
                //}
            } else {
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("NO HAY NUEVOS CLIENTES");
                FileOutputStream fileOut = null;
                String nombreFile = "no hay nuevos clientes.xlsx";
                fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
                wb.write(fileOut);
                fileOut.close();
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
                FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
                ServletOutputStream out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
            }
        } catch (IOException ioex) {
            LOGGER.error("GP.ERROR: {}", ioex.toString());
        }
    }
    
 
  
    public void muestraListaCorte(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: muestraListaCobranzas ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjTotal = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", ""};
        double saldo = 0;
        double pagado = 0;
        double total = 0;
        String msgError = "";
        try {
            if (Util.sesionEstaActiva(request)) {
                HttpSession session = request.getSession();
                Usuario usuario = (Usuario) session.getAttribute("Usuario");
                String empresa = (String) session.getAttribute("Empresa");
                //long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));

                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                String ls_tipo = request.getParameter("tipo");
                String ls_codigo = request.getParameter("codigo");
                String forma = request.getParameter("forma");
                StringBuilder sbLista = new StringBuilder();
  
                List<Map<String, Object>> listVentas = new CobranzaDAO().listado_Documentos_corte_resu(ls_fecha_ini, ls_fecha_fin,ls_tipo,ls_codigo,forma);
                    sbLista.append("<table id=\"tablaDepositos\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Cliente</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Total</th>");
                    sbLista.append("<th class=\"text-center\">Pagado</th>");
                    sbLista.append("<th class=\"text-center\">Saldo</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");                
                if (listVentas != null && !listVentas.isEmpty()) {
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("documentocliente")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("pagado")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("saldo")))).append("</td>");
                        total +=  Util.nullDou(listVentas.get(i).get("total"));
                        pagado +=  Util.nullDou(listVentas.get(i).get("pagado"));
                        saldo +=  Util.nullDou(listVentas.get(i).get("saldo"));
                    }
                } else {
                    total = 0;
                    pagado = 0;
                    saldo = 0;
                }
                    sbLista.append("<tr>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("Totales").append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(total)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(pagado)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(saldo)).append("</td>");
                    
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                
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
        LOGGER.info("<==== Fin Metodo: muestraListaCobranzas ====>");
    }
    

    public void muestraListaCorte_excel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            CobranzasForm uform = (CobranzasForm) form;
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("My Sample Excel");
            int indice = 0;
            Connection cn = new ConectaSQL().connection();
            long seqTmp2 = new Util().secuencia();
            String pDocumentoCliente = "";
            String ls_fecha_ini = Util.yyyymmdd(uform.getFechaIni());
            String ls_fecha_fin = Util.yyyymmdd(uform.getFechaFin());
            String ls_tipo = uform.getTipo();
            String ls_codigo = uform.getCodigo();
            String forma = uform.getForma();
            StringBuilder sbLista = new StringBuilder();
  
            List<Map<String, Object>> listTemporal = new CobranzaDAO().listado_Documentos_corte_resu(ls_fecha_ini, ls_fecha_fin,ls_tipo,ls_codigo,forma);
            if (listTemporal.size() > 0) {

                CreationHelper helper = wb.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();

                InputStream inputStream;// = new FileInputStream(path + Constantes.DIRECTORIO_IMAGENES + "/bg-foto_Real.jpg");
                byte[] bytes; // = IOUtils.toByteArray(inputStream);
                int itemCol[] = {
                    5500,
                    4500,
                    3500,
                    6000,
                    3000,

                };
                for (int i = 0; i < itemCol.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, itemCol[i]);
                }
                int nRow = 0;

                Row row = sheet.createRow(nRow);
                row.createCell(0).setCellValue("Cliente");
                row.createCell(1).setCellValue("Razon social");
                row.createCell(2).setCellValue("Total");
                row.createCell(3).setCellValue("Pagado");
                row.createCell(4).setCellValue("Saldo");
                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 1;
                int nPosImg = 1;
                indice = 0;
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("documentocliente")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("razonsocial")));
                    row.createCell(2).setCellValue(Util.nullDou(obj.get("total")));
                    row.createCell(3).setCellValue(Util.nullDou(obj.get("pagado")));
                    row.createCell(4).setCellValue(Util.nullDou(obj.get("saldo")));
                    nRow++;
                }
                FileOutputStream fileOut = null;
                String nombreFile = "con corte" + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
                fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
                wb.write(fileOut);
                fileOut.close();
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
                FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
                ServletOutputStream out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
                //}
            } else {
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("NO HAY NUEVOS CLIENTES");
                FileOutputStream fileOut = null;
                String nombreFile = "no hay nuevos clientes.xlsx";
                fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
                wb.write(fileOut);
                fileOut.close();
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
                FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
                ServletOutputStream out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
            }
        } catch (IOException ioex) {
            LOGGER.error("GP.ERROR: {}", ioex.toString());
        }
    }




    public void muestraListaCorteDetalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: muestraListaCobranzas ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjTotal = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", ""};
        double saldo = 0;
        double pagado = 0;
        double total = 0;
        String msgError = "";
        try {
            if (Util.sesionEstaActiva(request)) {
                HttpSession session = request.getSession();
                Usuario usuario = (Usuario) session.getAttribute("Usuario");
                String empresa = (String) session.getAttribute("Empresa");
                //long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));

                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                String ls_tipo = request.getParameter("tipo");
                String ls_codigo = request.getParameter("codigo");
                String forma = request.getParameter("forma");
                StringBuilder sbLista = new StringBuilder();
  
                List<Map<String, Object>> listVentas = new CobranzaDAO().listado_Documentos_corte_detalle(ls_fecha_ini, ls_fecha_fin,ls_tipo,ls_codigo,forma);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaDepositos\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                //    sbLista.append("<th class=\"details-control\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>"); // (para plugin DataTables) 6 espacios
                    sbLista.append("<th class=\"text-center\">Cliente</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Forma</th>");
                    sbLista.append("<th class=\"text-center\">Tipo</th>");
                    sbLista.append("<th class=\"text-center\">#Serie</th>");
                    sbLista.append("<th class=\"text-center\">#Documento</th>");
                    sbLista.append("<th class=\"text-center\">Fecha Doc</th>");
                    sbLista.append("<th class=\"text-center\">Fecha Ven.</th>");
                    sbLista.append("<th class=\"text-center\">Total</th>");
                    sbLista.append("<th class=\"text-center\">Pagado</th>");
                    sbLista.append("<th class=\"text-center\">Saldo</th>");
                    sbLista.append("<th class=\"text-center\">TD Ref.</th>");
                    sbLista.append("<th class=\"text-center\">Serie Ref.</th>");
                    sbLista.append("<th class=\"text-center\">Docu. Ref</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                    //    sbLista.append("<td class=\"details-control\"></td>"); // (para plugin DataTables)
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("documentocliente")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("forma")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechadocumento")).append("</td>");                       
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaVencimiento")).append("</td>");                       
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("pagado")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("saldo")))).append("</td>");
                        
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipo_ref")).append("</td>");  
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie_ref")).append("</td>");  
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento_ref")).append("</td>");  
                        
                        total +=  Util.nullDou(listVentas.get(i).get("total"));
                        pagado +=  Util.nullDou(listVentas.get(i).get("pagado"));
                        saldo +=  Util.nullDou(listVentas.get(i).get("saldo"));
                        
                       
                        
                    }
                    sbLista.append("<tr>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(total)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(pagado)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(saldo)).append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");                    
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                } else {
                    total = 0;
                    pagado = 0;
                    saldo = 0;
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
        LOGGER.info("<==== Fin Metodo: muestraListaCobranzas ====>");
    }

    public void muestraListaCorteDetalle_Excel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            CobranzasForm uform = (CobranzasForm) form;
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("My Sample Excel");
            int indice = 0;
            Connection cn = new ConectaSQL().connection();
            long seqTmp2 = new Util().secuencia();
            String pDocumentoCliente = "";
            String ls_fecha_ini = Util.yyyymmdd(uform.getFechaIni());
            String ls_fecha_fin = Util.yyyymmdd(uform.getFechaFin());
            String ls_tipo = uform.getTipo();
            String ls_codigo = uform.getCodigo();
            String forma = uform.getForma();
            StringBuilder sbLista = new StringBuilder();
            if ("".endsWith(forma)) forma="%";
            List<Map<String, Object>> listTemporal = new CobranzaDAO().listado_Documentos_corte_detalle(ls_fecha_ini, ls_fecha_fin,ls_tipo,ls_codigo,forma);
            if (listTemporal.size() > 0) {

                CreationHelper helper = wb.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();

                InputStream inputStream;// = new FileInputStream(path + Constantes.DIRECTORIO_IMAGENES + "/bg-foto_Real.jpg");
                byte[] bytes; // = IOUtils.toByteArray(inputStream);
                int itemCol[] = {
                    5500,
                    4500,
                    3500,
                    6000,
                    3000,
                    3000,
                    3000,
                    3000,
                    3000,
                    3000,
                    3000,
                    3000
                };
                for (int i = 0; i < itemCol.length; i++) {
                    sheet.autoSizeColumn(i);
                    sheet.setColumnWidth(i, itemCol[i]);
                }
                int nRow = 0;

                Row row = sheet.createRow(nRow);
                row.createCell(0).setCellValue("Cliente");
                row.createCell(1).setCellValue("Razon social");
                row.createCell(2).setCellValue("Tipo");
                row.createCell(3).setCellValue("#Serie");
                row.createCell(4).setCellValue("#Documento");
                row.createCell(5).setCellValue("Fecha");
                row.createCell(6).setCellValue("Total");
                row.createCell(7).setCellValue("Pagado");
                row.createCell(8).setCellValue("Saldo");
                row.createCell(8).setCellValue("TD Ref");
                row.createCell(8).setCellValue("Serie Ref");
                row.createCell(8).setCellValue("Documento Ref");
                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 1;
                int nPosImg = 1;
                indice = 0;
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("documentocliente")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("razonsocial")));
                    row.createCell(2).setCellValue(Util.nullCad(obj.get("tipodocumento")));
                    row.createCell(3).setCellValue(Util.nullCad(obj.get("documento")));
                    row.createCell(4).setCellValue(Util.nullCad(obj.get("serie")));
                    row.createCell(5).setCellValue(Util.nullCad(obj.get("fechadocumento")));
                    row.createCell(6).setCellValue(Util.nullDou(obj.get("total")));                    
                    row.createCell(7).setCellValue(Util.nullDou(obj.get("pagado")));
                    row.createCell(8).setCellValue(Util.nullDou(obj.get("saldo")));                                                            
                    
                    row.createCell(9).setCellValue(Util.nullCad(obj.get("tipo_ref")));  
                    row.createCell(10).setCellValue(Util.nullCad(obj.get("serie_ref")));  
                    row.createCell(11).setCellValue(Util.nullCad(obj.get("documento_ref")));  
                    nRow++;
                }
                FileOutputStream fileOut = null;
                String nombreFile = "con corte" + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
                fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
                wb.write(fileOut);
                fileOut.close();
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
                FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
                ServletOutputStream out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
                //}
            } else {
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("NO HAY NUEVOS CLIENTES");
                FileOutputStream fileOut = null;
                String nombreFile = "no hay nuevos clientes.xlsx";
                fileOut = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile);
                wb.write(fileOut);
                fileOut.close();
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "");
                FileInputStream in = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFile));
                ServletOutputStream out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
            }
        } catch (IOException ioex) {
            LOGGER.error("GP.ERROR: {}", ioex.toString());
        }
    }

    public void EditarItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: eliminarItem ====>");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("cache-control", "no-cache");
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        JSONObject jsonObjMsg;
        JSONObject jsonObjDatos = new JSONObject();
        JSONObject jsonObjTabla = new JSONObject();
        //String[] indicador = {"", "", jsonObjDatos""};
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
                StringBuilder sbLista = new StringBuilder();
                Long indice = Util.nullLon(request.getParameter("indice"));
                List<Map<String, Object>> listItem = new Trans_VentasDAO().muestraItemTempo(seqTmp1,indice);                    
                    jsonObjDatos.put("formapago", listItem.get(0).get("formapago"));
                    jsonObjDatos.put("moneda", listItem.get(0).get("moneda"));
                    jsonObjDatos.put("banco", listItem.get(0).get("banco"));
                    jsonObjDatos.put("fechaoperacion", listItem.get(0).get("fechaoperacion"));
                    jsonObjDatos.put("deposito", listItem.get(0).get("deposito"));
                    jsonObjDatos.put("importe", listItem.get(0).get("importe"));                                      
                    jsonObjMsg = new JSONObject();
                    jsonObjMsg.put("tipoMsg", "exito");                
            } else {
                jsonObjMsg = new JSONObject();
                jsonObjMsg.put("tipoMsg", "relogin");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("objMensaje", jsonObjMsg);
            jsonObject.put("objDatos", jsonObjDatos);
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

    public void lista_Clientes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                String codigo = request.getParameter("txtcodigo").trim();
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listaProveedor = new GenericoDAO().listaClientes(codigo);
                if (listaProveedor != null && !listaProveedor.isEmpty()) {
                    sbLista.append("<table id=\"tablaClientes\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Codigo</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\"></th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    long asiento = 0;

                    for (int i = 0; i < listaProveedor.size(); i++) {
                        //CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listaProveedor.get(i).get("codigo")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listaProveedor.get(i).get("nombre")).append("</td>");
                        sbLista.append("<td class=\"text-center\"><button onclick=\"eligeCliente('")
                                .append(Util.nullCad(listaProveedor.get(i).get("codigo")))
                                .append("');\" title=\"elegir\" class=\"btn btn-xs btn-default\" type=\"button\">"
                                        + Constantes.ICON_CHECK + "</button></td>");
                    }
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                    msgError = "";
                } else {

                    jsonObjTabla.put("tabla", "");
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
    
}
