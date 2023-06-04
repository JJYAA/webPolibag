/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
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
import org.apache.struts.actions.DispatchAction;
import org.json.JSONObject;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.dao.GenericoDAO;
import pe.com.gp.dao.PagosCorteDAO;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.CobranzasForm;
import pe.com.gp.util.Constantes;
import pe.com.gp.util.Util;

/**
 *
 * @author ADMIN
 */
public class PagarAction extends DispatchAction {

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
            //cargaListas(request, uform);

        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
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
        double saldo_sol = 0;
        double pagado_sol = 0;
        double total_sol = 0;
        double saldo_dol = 0;
        double pagado_dol = 0;
        double total_dol = 0;
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

                List<Map<String, Object>> listVentas = new PagosCorteDAO().listado_Documentos_corte_resu(ls_fecha_ini, ls_fecha_fin, ls_tipo, ls_codigo, forma);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaDepositos\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">Cliente</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Total S/</th>");
                    sbLista.append("<th class=\"text-center\">Pagado S/</th>");
                    sbLista.append("<th class=\"text-center\">Saldo S/</th>");
                    sbLista.append("<th class=\"text-center\">Total US$</th>");
                    sbLista.append("<th class=\"text-center\">Pagado US$</th>");
                    sbLista.append("<th class=\"text-center\">Saldo US$</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("documentocliente")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total_sol")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("pagado_sol")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("saldo_sol")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total_dol")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("pagado_dol")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("saldo_dol")))).append("</td>");
                        if ("S/".equals(listVentas.get(i).get("moneda"))) {
                            total_sol += Util.nullDou(listVentas.get(i).get("total_sol"));
                            pagado_sol += Util.nullDou(listVentas.get(i).get("pagado_sol"));
                            saldo_sol += Util.nullDou(listVentas.get(i).get("saldo_sol"));
                        } else {
                            total_dol += Util.nullDou(listVentas.get(i).get("total_dol"));
                            pagado_dol += Util.nullDou(listVentas.get(i).get("pagado_dol"));
                            saldo_dol += Util.nullDou(listVentas.get(i).get("saldo_dol"));
                        }

                    }
                    sbLista.append("<tr>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("Totales").append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(total_sol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(pagado_sol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(saldo_sol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(total_dol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(pagado_dol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(saldo_dol)).append("</td>");
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                } else {
                    total_sol = 0;
                    pagado_sol = 0;
                    saldo_sol = 0;
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

    public void muestraListaCorte_excelPagar(ActionMapping mapping, ActionForm form,
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

            List<Map<String, Object>> listTemporal = new PagosCorteDAO().listado_Documentos_corte_resu(ls_fecha_ini, ls_fecha_fin, ls_tipo, ls_codigo, forma);
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
                row.createCell(2).setCellValue("Total S/");
                row.createCell(3).setCellValue("Pagado S/");
                row.createCell(4).setCellValue("Saldo S/");
                row.createCell(5).setCellValue("Total US$");
                row.createCell(6).setCellValue("Pagado US$");
                row.createCell(7).setCellValue("Saldo US$");
                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 1;
                int nPosImg = 1;
                indice = 0;
                double saldo_sol = 0;
                double pagado_sol = 0;
                double total_sol = 0;
                double saldo_dol = 0;
                double pagado_dol = 0;
                double total_dol = 0;
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("documentocliente")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("razonsocial")));
                    row.createCell(2).setCellValue(Util.nullDou(obj.get("total_sol")));
                    row.createCell(3).setCellValue(Util.nullDou(obj.get("pagado_sol")));
                    row.createCell(4).setCellValue(Util.nullDou(obj.get("saldo_sol")));
                    row.createCell(5).setCellValue(Util.nullDou(obj.get("total_dol")));
                    row.createCell(6).setCellValue(Util.nullDou(obj.get("pagado_dol")));
                    row.createCell(7).setCellValue(Util.nullDou(obj.get("saldo_dol")));
                    if ("S/".equals(obj.get("moneda"))) {
                        total_sol += Util.nullDou(obj.get("total_sol"));
                        pagado_sol += Util.nullDou(obj.get("pagado_sol"));
                        saldo_sol += Util.nullDou(obj.get("saldo_sol"));
                    } else {
                        total_dol += Util.nullDou(obj.get("total_dol"));
                        pagado_dol += Util.nullDou(obj.get("pagado_dol"));
                        saldo_dol += Util.nullDou(obj.get("saldo_dol"));
                    }
                    nRow++;
                }
                row = sheet.createRow(nRow);
                row.createCell(2).setCellValue(total_sol);
                row.createCell(3).setCellValue(pagado_sol);
                row.createCell(4).setCellValue(saldo_sol);
                row.createCell(5).setCellValue(total_dol);
                row.createCell(6).setCellValue(pagado_dol);
                row.createCell(7).setCellValue(saldo_dol);
                
                
                FileOutputStream fileOut = null;
                String nombreFile = "con corte PAGAR" + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
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
        double saldo_sol = 0;
        double pagado_sol = 0;
        double total_sol = 0;
        double saldo_dol = 0;
        double pagado_dol = 0;
        double total_dol = 0;
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

                List<Map<String, Object>> listVentas = new PagosCorteDAO().listado_Documentos_corte_detalle(ls_fecha_ini, ls_fecha_fin, ls_tipo, ls_codigo, forma);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaDepositos\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    //    sbLista.append("<th class=\"details-control\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>"); // (para plugin DataTables) 6 espacios
                    sbLista.append("<th class=\"text-center\">Cliente</th>");
                    sbLista.append("<th class=\"text-center\">Razon social</th>");
                    sbLista.append("<th class=\"text-center\">Tipo</th>");
                    sbLista.append("<th class=\"text-center\">#Documento</th>");
                    sbLista.append("<th class=\"text-center\">Fecha Doc</th>");
                    sbLista.append("<th class=\"text-center\">Moneda</th>");
                    sbLista.append("<th class=\"text-center\">Total</th>");
                    sbLista.append("<th class=\"text-center\">Pagado</th>");
                    sbLista.append("<th class=\"text-center\">Saldo</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        sbLista.append("<tr>");
                        //    sbLista.append("<td class=\"details-control\"></td>"); // (para plugin DataTables)
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("documentocliente")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("razonsocial")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechadocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("moneda")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("total")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("pagado")))).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(Util.nullDou(listVentas.get(i).get("saldo")))).append("</td>");

                        if ("S/".equals(listVentas.get(i).get("moneda"))) {
                            total_sol += Util.nullDou(listVentas.get(i).get("total"));
                            pagado_sol += Util.nullDou(listVentas.get(i).get("pagado"));
                            saldo_sol += Util.nullDou(listVentas.get(i).get("saldo"));
                        } else {
                            total_dol += Util.nullDou(listVentas.get(i).get("total"));
                            pagado_dol += Util.nullDou(listVentas.get(i).get("pagado"));
                            saldo_dol += Util.nullDou(listVentas.get(i).get("saldo"));
                        }

                    }
                    sbLista.append("<tr>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("Soles").append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(total_sol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(pagado_sol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(saldo_sol)).append("</td>");
                    sbLista.append("</tr>");
                    sbLista.append("<tr>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("").append("</td>");
                    sbLista.append("<td class=\"text-left\" >").append("Dolar").append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(total_dol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(pagado_dol)).append("</td>");
                    sbLista.append("<td class=\"text-right\" >").append(Util.formatearDoubleA(saldo_dol)).append("</td>");
                    sbLista.append("</tr>");
                    sbLista.append("</table>");
                    jsonObjTabla.put("tabla", sbLista.toString());
                } else {
                    total_sol = 0;
                    pagado_sol = 0;
                    saldo_sol = 0;
                    total_dol = 0;
                    pagado_dol = 0;
                    saldo_dol = 0;
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

    public void muestraListaCorteDetalle_ExcelPagar(ActionMapping mapping, ActionForm form,
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
            if ("".endsWith(forma)) {
                forma = "%";
            }
            List<Map<String, Object>> listTemporal = new PagosCorteDAO().listado_Documentos_corte_detalle(ls_fecha_ini, ls_fecha_fin, ls_tipo, ls_codigo, forma);
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
                row.createCell(3).setCellValue("#Documento");
                row.createCell(4).setCellValue("Fecha");
                row.createCell(5).setCellValue("Moneda");
                row.createCell(6).setCellValue("Total");
                row.createCell(7).setCellValue("Pagado");
                row.createCell(8).setCellValue("Saldo");
                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 1;
                int nPosImg = 1;
                indice = 0;
                double total_sol = 0;
                double pagado_sol = 0;
                double saldo_sol = 0;
                double total_dol = 0;
                double pagado_dol = 0;
                double saldo_dol = 0;
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("documentocliente")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("razonsocial")));
                    row.createCell(2).setCellValue(Util.nullCad(obj.get("tipodocumento")));
                    row.createCell(3).setCellValue(Util.nullCad(obj.get("documento")));
                    row.createCell(4).setCellValue(Util.nullCad(obj.get("fechadocumento")));
                    row.createCell(5).setCellValue(Util.nullCad(obj.get("moneda")));
                    row.createCell(6).setCellValue(Util.nullDou(obj.get("total")));
                    row.createCell(7).setCellValue(Util.nullDou(obj.get("pagado")));
                    row.createCell(8).setCellValue(Util.nullDou(obj.get("saldo")));

                    if ("S/".equals(obj.get("moneda"))) {
                        total_sol += Util.nullDou(obj.get("total"));
                        pagado_sol += Util.nullDou(obj.get("pagado"));
                        saldo_sol += Util.nullDou(obj.get("saldo"));
                    } else {
                        total_dol += Util.nullDou(obj.get("total"));
                        pagado_dol += Util.nullDou(obj.get("pagado"));
                        saldo_dol += Util.nullDou(obj.get("saldo"));
                    }

                    nRow++;
                }
                row = sheet.createRow(nRow);
                row.createCell(5).setCellValue("SOLES");
                row.createCell(6).setCellValue(total_sol);
                row.createCell(7).setCellValue(pagado_sol);
                row.createCell(8).setCellValue(saldo_sol);
                nRow++;
                row = sheet.createRow(nRow);
                row.createCell(5).setCellValue("DOLARES");
                row.createCell(6).setCellValue(total_dol);
                row.createCell(7).setCellValue(pagado_dol);
                row.createCell(8).setCellValue(saldo_dol);
                FileOutputStream fileOut = null;
                String nombreFile = "con detalle PAGAR" + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
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
                String codigo = request.getParameter("txtcodigo").trim();
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listaProveedor = new GenericoDAO().listaProveedor(codigo);
                if (listaProveedor != null && !listaProveedor.isEmpty()) {
                    sbLista.append("<table id=\"tablaProveedores\" class=\"table table-striped\"  >");
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
                        sbLista.append("<td class=\"text-center\"><button onclick=\"eligeProducto('")
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
