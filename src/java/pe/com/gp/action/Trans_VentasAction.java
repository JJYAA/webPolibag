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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
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
import org.json.JSONArray;
import org.json.JSONObject;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.dao.AsientoDAO;
import pe.com.gp.dao.Trans_VentasDAO;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.Trans_VentasForm;
import pe.com.gp.util.Constantes;
import pe.com.gp.util.Util;
//import com.linuxense.javadbf.DBFWriter;
import com.linuxense.javadbf.*;
import java.sql.PreparedStatement;
import java.util.Date;
import pe.com.gp.dao.ClienteFacturaDAO;
import java.util.UUID;

/**
 *
 * @author Administrador
 */
public class Trans_VentasAction extends DispatchAction {

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

    public ActionForward init_ventas(ActionMapping mapping, ActionForm form,
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
            mappingFindForward = "init_ventas";

        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }    
    public void lista_ventas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaTrans_Ventas(ls_empresa, ls_fecha_ini, ls_fecha_fin);
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
                    sbLista.append("<th class=\"text-center\">TD REF</th>");
                    sbLista.append("<th class=\"text-center\">DOC REF</th>");
                    sbLista.append("<th class=\"text-center\">FEC REF</th>");
                    sbLista.append("<th class=\"text-center\">B.Imp REF</th>");
                    sbLista.append("<th class=\"text-center\">Igv REF</th>");
                    sbLista.append("<th class=\"text-center\">Asiento</th>");
                    sbLista.append("<th class=\"text-center\">").append("Asiento").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodos\" name=\"chkbxTodos\" class=\"chkbxTodos\" onclick=\"seleccinadosTodos()\" />").append("</th>");
                    sbLista.append("<th class=\"text-center\">").append("Eliminar").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodosDel\" name=\"chkbxTodosDel\" class=\"chkbxTodosDel\" onclick=\"seleccinadosElimina()\" />").append("</th>");
                    sbLista.append("<th class=\"text-center\">").append("Xls").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodosXls\" name=\"chkbxTodosXls\" class=\"chkbxTodosXls\" onclick=\"seleccinadosTodosXls()\" />").append("</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
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

                        sbLista.append("<td>").append(listVentas.get(i).get("ref_serie")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("ref_documento")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("ref_fechadocumento")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("referencia_baseImp")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("referenciaigv")).append("</td>");

                        sbLista.append("<td>").append(listVentas.get(i).get("asiento")).append("</td>");
                        asiento = Util.nullLon(listVentas.get(i).get("asiento"));
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

                        if (asiento == 0) {
                            sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxXls\" disabled=\"true\" class=\"chkbxTodosXls\" onclick=seleccinados()  data-selected=\"").append(CadSelected).append("\"  >").append("</td>");

                        } else {
                            sbLista.append("<td class=\"text-center\" >").append("<input type=\"checkbox\" name=\"chkbxXls\" class=\"chkbxXls\" onclick=seleccinadosXls()  data-selected=\"").append(CadSelected).append("\">").append("</td>");
                        }

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
        LOGGER.info("<==== Fin Metodo: agregarItem ====>");
    }

    public void exportarExcel_ventas(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            int indice = 0;
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
            String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
            int nRow = 0;

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Migrar Ventas");

            Connection cn = new ConectaSQL().connection();

            long seqTmp1 = new Util().secuencia(); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));

            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            String empresa = (String) session.getAttribute("Empresa");
            Trans_VentasForm uform = (Trans_VentasForm) form;
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
            Long ln_asiento = new Long(0);
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            java.sql.Date date = new java.sql.Date(0);
            String sqlQuery = "insert into asientos_tmp values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(sqlQuery);
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[1];
                pDocumento = AuxItem[2];
                pTipoDocumento = AuxItem[4];
                pDocumentoCliente = AuxItem[5].trim();
                pstmt.setLong(1, seqTmp1);    //1
                pstmt.setString(2, null);   //2
                pstmt.setString(3, pDocumento);   //3
                pstmt.setString(4, pPeriodo);   //4
                pstmt.setString(5, "");   //5
                pstmt.setString(6, "020128");   //6
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
            String ls_error = new Trans_VentasDAO().creaItemsTemporal(seqTmp1, "", ls_fecha_ini, ls_fecha_fin);

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
            for (i = 0; i < itemCol.length; i++) {
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

            List<Map<String, Object>> listTemporal = new Trans_VentasDAO().listaItems_VentasXLS(seqTmp1);
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
                    row.createCell(29).setCellValue(Util.nullCad(obj.get("baseimponibleref"))); //baseimponibleref
                    row.createCell(30).setCellValue(Util.nullCad(obj.get("igvdocumentoprovision"))); // igvdocumentoprovision
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
            String nombreFile = "venta " + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
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

            /* clientes*/
 /*
            Workbook wbCli = new XSSFWorkbook();
            Sheet sheetCli = wbCli.createSheet("Migrar Ventas");
            wbCli = new XSSFWorkbook();
            sheetCli = wbCli.createSheet("Clientes");
            indice = 0;
            //Connection cn = new ConectaSQL().connection();
            long seqTmp2 = Util.nullLon(session.getAttribute(SEQ_TMP2_SESSION_KEY));  
            String pDocumentoCliente="";
            List<Map<String, Object>> listTemporalCli = new Trans_VentasDAO().listaTrans_VentasClientes("", ls_fecha_ini, ls_fecha_fin);
            if (listTemporalCli.size() > 0) 
            { 
                while (indice < listTemporalCli.size()) 
                {
                    Map<String, Object> obj = listTemporalCli.get(indice);
                    pDocumentoCliente = Util.nullCad(obj.get("documentocliente"));
                    List<Map<String, Object>> Cliente = new ClienteFacturaDAO().ListaCliente("",pDocumentoCliente);
                    String pRuta = path + Constantes.DIRECTORIO_DBF + "//CAO03.dbf";
                    Boolean existe=false;
                    InputStream inputStreamCli = new FileInputStream(pRuta); // take dbf file as program argument
                    DBFReader reader = new DBFReader(inputStreamCli);                     
                    int numberOfFields = reader.getFieldCount();
                    DBFRow rowCli;         
 
                    String ls_ACODANE="";
                    String ls_AVANEXO="";
                    while (((rowCli = reader.nextRow()) != null)&&(!existe)) 
                    {
			ls_ACODANE = rowCli.getString("ACODANE").trim();
                        ls_AVANEXO = rowCli.getString("AVANEXO").trim();
                        if ((pDocumentoCliente.equals(ls_ACODANE)) && ("C".equals(ls_AVANEXO)))  {                                    
                                    existe=true;
                        }                                        
                    }
                    LOGGER.info("<==== Inicio Metodo: buqleeee ====>");
                    DBFUtils.close(reader);
                    if (!existe){   
                            Map<String, Object> objCliente = Cliente.get(0);
                            String tipoAnexo="C";
                            String codigoAnexo = pDocumentoCliente;
                            String razonsocial = Util.nullCad(objCliente.get("paterno")) + ' ' + Util.nullCad(objCliente.get("nombre"));
                            String direccion = Util.nullCad(objCliente.get("direccion"));
                            String tipopersona = Util.nullCad(objCliente.get("tipopersona"));
                            String paterno = Util.nullCad(objCliente.get("paterno"));
                            String materno =Util.nullCad(objCliente.get("materno"));
                            String nombre = Util.nullCad(objCliente.get("nombre"));
                            String ruc = pDocumentoCliente;
                            String tipodocumento = Util.nullCad(objCliente.get("tp_sunat"));
                            String numeroDocumento = pDocumentoCliente;
                            String tipo="N";
                            String moneda="MN";
                            String nacionalidad = " ";
                            String domiciliado    =" ";                    
                           new ClienteFacturaDAO().creaAnexo("", 
                                   seqTmp2, 
                                   tipoAnexo, 
                                   codigoAnexo, 
                                   razonsocial,
                                   direccion, 
                                   tipopersona, 
                                   paterno, 
                                   materno, 
                                   nombre, 
                                   ruc, 
                                   tipodocumento, 
                                   numeroDocumento, 
                                   tipo, 
                                   moneda, 
                                   nacionalidad, 
                                   domiciliado);
                    }
                    indice++;                       
                }            
                      
                List<Map<String, Object>> listClientes = new Trans_VentasDAO().listaTrans_TMPVentasClientes(seqTmp2);
                if (listClientes.size() > 0) 
                {

                    CreationHelper helperCli = wbCli.getCreationHelper();
                    

                    int itemColCli[] = {
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
                        3000
                    };
                    for (int i = 0; i < itemColCli.length; i++) {
                        sheetCli.autoSizeColumn(i);
                        sheetCli.setColumnWidth(i, itemColCli[i]);
                    }
                    nRow = 0;

                    Row rowCli = sheetCli.createRow(nRow);
                    rowCli.createCell(0).setCellValue("Tipo Anexo");
                    rowCli.createCell(1).setCellValue("Código Anexo");
                    rowCli.createCell(2).setCellValue("Razon Social o Descripción");
                    rowCli.createCell(3).setCellValue("Direccion de Referencia");
                    rowCli.createCell(4).setCellValue("Tipo de Persona");
                    rowCli.createCell(5).setCellValue("Apellido Paterno");
                    rowCli.createCell(6).setCellValue("Apellido Materno");
                    rowCli.createCell(7).setCellValue("Nombres");
                    rowCli.createCell(8).setCellValue("RUC");
                    rowCli.createCell(9).setCellValue("Tipo Documento Identidad");
                    rowCli.createCell(10).setCellValue("Número Documento Identidad");
                    rowCli.createCell(11).setCellValue("Tipo");
                    rowCli.createCell(12).setCellValue("Moneda");
                    rowCli.createCell(13).setCellValue("Nacionalidad");
                    rowCli.createCell(14).setCellValue("¿Domiciliado?");
                    nRow = 1;
                    rowCli = sheetCli.createRow(nRow);
                    nRow = 2;
                    rowCli = sheetCli.createRow(nRow);

                    // = "";
                    nRow = 3;                    
                    indice = 0;
                    while (indice < listClientes.size()) {
                        Map<String, Object> obj = listClientes.get(indice);
                        indice++;
                        rowCli = sheetCli.createRow(nRow);
                        rowCli.setHeight((short) 250);
                        rowCli.createCell(0).setCellValue(Util.nullCad(obj.get("tipoAnexo")));
                        rowCli.createCell(1).setCellValue(Util.nullCad(obj.get("codigoAnexo")));
                        rowCli.createCell(2).setCellValue(Util.nullCad(obj.get("razonsocial")));
                        rowCli.createCell(3).setCellValue(Util.nullCad(obj.get("direccion")));
                        rowCli.createCell(4).setCellValue(Util.nullCad(obj.get("tipopersona")));
                        rowCli.createCell(5).setCellValue(Util.nullCad(obj.get("paterno")));
                        rowCli.createCell(6).setCellValue(Util.nullCad(obj.get("materno")));
                        rowCli.createCell(7).setCellValue(Util.nullCad(obj.get("nombre")));
                        rowCli.createCell(8).setCellValue(Util.nullCad(obj.get("ruc")));
                        rowCli.createCell(9).setCellValue(Util.nullCad(obj.get("tipodocumento")));
                        rowCli.createCell(10).setCellValue(Util.nullCad(obj.get("numeroDocumento")));
                        rowCli.createCell(11).setCellValue(Util.nullCad(obj.get("tipo")));
                        rowCli.createCell(12).setCellValue(Util.nullCad(obj.get("moneda")));
                        rowCli.createCell(13).setCellValue(Util.nullCad(obj.get("nacionalidad")));
                        rowCli.createCell(14).setCellValue(Util.nullCad(obj.get("domiciliado")));
                        nRow++;
                    }
                    FileOutputStream fileOutCli = null;
                    String nombreFileCli = "clientes " + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
                    fileOutCli = new FileOutputStream(path + Constantes.DIRECTORIO_IREPORT + "/" + nombreFileCli);
                    wbCli.write(fileOutCli);
                    fileOutCli.close();
                    response.setContentType("text/plain");
                    response.setHeader("Content-disposition", "attachment; filename=" + nombreFileCli + "");
                    FileInputStream inCli = new FileInputStream(new File(path + Constantes.DIRECTORIO_IREPORT + "/" +nombreFileCli ));
                    ServletOutputStream outCli = response.getOutputStream();
                    int lenCli = 0;
                    byte[] bufferCli = new byte[1024];
                    while ((lenCli = inCli.read(bufferCli)) > 0) {
                        outCli.write(bufferCli, 0, lenCli);
                    }
                    inCli.close();
                    outCli.flush();
                    outCli.close();                
                }
            }            
             */
        } catch (IOException ioex) {
            LOGGER.error("GP.ERROR: {}", ioex.toString());

        }

    }

    public ActionForward conAsientoVentas(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: conAsientoVentas ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String empresa = (String) session.getAttribute("Empresa");
            Trans_VentasForm uform = (Trans_VentasForm) form;
            String docSelected = uform.getDocSelected();
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
            Long ln_asiento = new Long(0);
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[1];
                pDocumento = AuxItem[2];
                pTipoDocumento = AuxItem[4];
                pDocumentoCliente = AuxItem[5].trim();
                //  DBFReader reader = null;
                String ls_ACODANE = "";
                String ls_AVANEXO = "";
                try {
                    String ls_error = new Trans_VentasDAO().AsientoVentas(empresa, pAgencia, pSerie, pDocumento, pPeriodo, pUsuario, ln_asiento, pTipo, pTipoDocumento, pAnho);
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
            mappingFindForward = "msgRegistroVentas";
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
            Trans_VentasForm uform = (Trans_VentasForm) form;
            String docSelected = uform.getDocSelectedDel();
            AsientoDAO asiento = new AsientoDAO();
            String[] arrSelected = docSelected.split(",");
            int i = 0;
            String msg;
            String pAgencia = "";
            String pSerie = "";
            String pDocumento = "";
            String pUsuario = "SA";
            String pTipo = "05";
            String pAsiento = "";
            String pTipoDocumento = "";
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[1];
                pDocumento = AuxItem[2];
                pAsiento = AuxItem[3];
                pTipoDocumento = AuxItem[4];
                String ls_error = new Trans_VentasDAO().AsientoDesactiva(empresa, pAgencia, pSerie, pDocumento, pUsuario, pAsiento, pTipo, pTipoDocumento);
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
            mappingFindForward = "msgRegistroVentas";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== Fin Metodo: inicializa ====>");
        return mapping.findForward(mappingFindForward);
    }

    public void exportarExcel_clientes(ActionMapping mapping, ActionForm form,
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
            String ls_asiento = request.getParameter("asiento"); //Util.yyyymmdd(request.getParameter("asiento"));              
            List<Map<String, Object>> listTemporal = new Trans_VentasDAO().listaTrans_VentasClientes("", ls_fecha_ini, ls_fecha_fin);
            if (listTemporal.size() > 0) {
                while (indice < listTemporal.size()) {
                    Map<String, Object> obj = listTemporal.get(indice);
                    pDocumentoCliente = Util.nullCad(obj.get("documentocliente"));
                    List<Map<String, Object>> Cliente = new ClienteFacturaDAO().ListaCliente("", pDocumentoCliente);
                    String pRuta = path + Constantes.DIRECTORIO_DBF + "//CAO03.dbf";
                    Boolean existe = false;
                    InputStream inputStream = new FileInputStream(pRuta); // take dbf file as program argument
                    DBFReader reader = new DBFReader(inputStream);
                    int numberOfFields = reader.getFieldCount();
                    DBFRow row;
                    String ls_ACODANE = "";
                    String ls_AVANEXO = "";
                    while (((row = reader.nextRow()) != null) && (!existe)) {
                        ls_ACODANE = row.getString("ACODANE").trim();
                        ls_AVANEXO = row.getString("AVANEXO").trim();
                        if ((pDocumentoCliente.equals(ls_ACODANE)) && ("C".equals(ls_AVANEXO))) {
                            existe = true;
                        }
                    }
                    LOGGER.info("<==== Inicio Metodo: buqleeee ====>");
                    DBFUtils.close(reader);
                    if (!existe) {
                        Map<String, Object> objCliente = Cliente.get(0);
                        String tipoAnexo = "C";
                        String codigoAnexo = pDocumentoCliente;
                        String razonsocial = Util.nullCad(objCliente.get("paterno")) + ' ' + Util.nullCad(objCliente.get("nombre"));
                        String direccion = Util.nullCad(objCliente.get("direccion"));
                        String tipopersona = Util.nullCad(objCliente.get("tipopersona"));
                        String paterno = Util.nullCad(objCliente.get("paterno"));
                        String materno = Util.nullCad(objCliente.get("materno"));
                        String nombre = Util.nullCad(objCliente.get("nombre"));
                        String ruc = pDocumentoCliente;
                        String tipodocumento = Util.nullCad(objCliente.get("tp_sunat"));
                        String numeroDocumento = pDocumentoCliente;
                        String tipo = "N";
                        String moneda = "MN";
                        String nacionalidad = " ";
                        String domiciliado = " ";
                        new ClienteFacturaDAO().creaAnexo("",
                                seqTmp2,
                                tipoAnexo,
                                codigoAnexo,
                                razonsocial,
                                direccion,
                                tipopersona,
                                paterno,
                                materno,
                                nombre,
                                ruc,
                                tipodocumento,
                                numeroDocumento,
                                tipo,
                                moneda,
                                nacionalidad,
                                domiciliado);
                    }
                    indice++;
                }

                List<Map<String, Object>> listClientes = new Trans_VentasDAO().listaTrans_TMPVentasClientes(seqTmp2);
                //if (listClientes.size() > 0) 
                //{
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
                row.createCell(0).setCellValue("ANEXO");
                row.createCell(1).setCellValue("Tipo Anexo");
                row.createCell(2).setCellValue("Código Anexo");
                row.createCell(3).setCellValue("Razon Social o Descripción");
                row.createCell(4).setCellValue("Direccion de Referencia");
                row.createCell(5).setCellValue("Tipo de Persona");
                row.createCell(6).setCellValue("Apellido Paterno");
                row.createCell(7).setCellValue("Apellido Materno");
                row.createCell(8).setCellValue("Nombres");
                row.createCell(9).setCellValue("RUC");
                row.createCell(10).setCellValue("Tipo Documento Identidad");
                row.createCell(11).setCellValue("Número Documento Identidad");
                row.createCell(12).setCellValue("Tipo");
                row.createCell(13).setCellValue("Moneda");
                row.createCell(14).setCellValue("Nacionalidad");
                row.createCell(15).setCellValue("¿Domiciliado?");
                nRow = 1;
                row = sheet.createRow(nRow);
                row.createCell(0).setCellValue("Restricciones");
                row.createCell(1).setCellValue("Ref. TG 07 - Tipos Anexo (C,P,H,etc) - Obligatorio");
                row.createCell(2).setCellValue("Obligatorio");
                row.createCell(3).setCellValue("Obligatorio");
                row.createCell(4).setCellValue("");
                row.createCell(5).setCellValue("Obligatorio");
                row.createCell(6).setCellValue("");
                row.createCell(7).setCellValue("");
                row.createCell(8).setCellValue("");
                row.createCell(9).setCellValue("Obligatorio");
                row.createCell(10).setCellValue("Obligatorio");
                row.createCell(11).setCellValue("Obligatorio");
                row.createCell(12).setCellValue("Obligatorio");
                row.createCell(13).setCellValue("Obligatorio");
                row.createCell(14).setCellValue("Ver T.G R2 Solo si Tipo de Persona es D = No Domiciliado");
                row.createCell(15).setCellValue("¿Domiciliado? Si o No");
                nRow = 2;
                row = sheet.createRow(nRow);
                row.createCell(0).setCellValue("Tamaño/Formato");
                row.createCell(1).setCellValue("1 Caracter");
                row.createCell(2).setCellValue("18 Caracteres");
                row.createCell(3).setCellValue("40 Caracteres");
                row.createCell(4).setCellValue("50 Caracteres");
                row.createCell(5).setCellValue("1 Caracteres");
                row.createCell(6).setCellValue("20 Caracteres");
                row.createCell(7).setCellValue("20 Caracteres");
                row.createCell(8).setCellValue("20 Caracteres");
                row.createCell(9).setCellValue("11 Caracteres");
                row.createCell(10).setCellValue("1 Caracteres");
                row.createCell(11).setCellValue("15 Caracteres");
                row.createCell(12).setCellValue("1 Caracteres");
                row.createCell(13).setCellValue("2 Caracteres");
                row.createCell(14).setCellValue("4 Caracteres");
                row.createCell(15).setCellValue("2 Caracteres");
                nRow = 3;
                row = sheet.createRow(nRow);
                row.createCell(0).setCellValue("Valores/Validados");
                row.createCell(1).setCellValue("Valores...");
                row.createCell(2).setCellValue("");
                row.createCell(3).setCellValue("");
                row.createCell(4).setCellValue("");
                row.createCell(5).setCellValue("Valores...");
                row.createCell(6).setCellValue("");
                row.createCell(7).setCellValue("");
                row.createCell(8).setCellValue("");
                row.createCell(9).setCellValue("");
                row.createCell(10).setCellValue("Valores...");
                row.createCell(11).setCellValue("");
                row.createCell(12).setCellValue("Valores...");
                row.createCell(13).setCellValue("Valores...");
                row.createCell(14).setCellValue("");
                row.createCell(15).setCellValue("Valores...");

                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 4;
                int nPosImg = 1;
                indice = 0;
                while (indice < listClientes.size()) {
                    Map<String, Object> obj = listClientes.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.setHeight((short) 250);
                    row.createCell(0).setCellValue("");
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("tipoAnexo")));
                    row.createCell(2).setCellValue(Util.nullCad(obj.get("codigoAnexo")));
                    row.createCell(3).setCellValue(Util.nullCad(obj.get("razonsocial")));
                    row.createCell(4).setCellValue(Util.nullCad(obj.get("direccion")));
                    row.createCell(5).setCellValue(Util.nullCad(obj.get("tipopersona")));
                    row.createCell(6).setCellValue(Util.nullCad(obj.get("paterno")));
                    row.createCell(7).setCellValue(Util.nullCad(obj.get("materno")));
                    row.createCell(8).setCellValue(Util.nullCad(obj.get("nombre")));
                    row.createCell(9).setCellValue(Util.nullCad(obj.get("ruc")));
                    row.createCell(10).setCellValue(Util.nullCad(obj.get("tipodocumento")));
                    row.createCell(11).setCellValue(Util.nullCad(obj.get("numeroDocumento")));
                    row.createCell(12).setCellValue(Util.nullCad(obj.get("tipo")));
                    row.createCell(13).setCellValue(Util.nullCad(obj.get("moneda")));
                    row.createCell(14).setCellValue(Util.nullCad(obj.get("nacionalidad")));
                    row.createCell(15).setCellValue(Util.nullCad(obj.get("domiciliado")));
                    nRow++;
                }
                FileOutputStream fileOut = null;
                String nombreFile = "clientes " + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
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


    public void lista_registrodeventas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
                String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaRegistro_Ventas(empresa, ls_fecha_ini, ls_fecha_fin);
                if (listVentas != null && !listVentas.isEmpty()) {
                    sbLista.append("<table id=\"tablaRegistroVenta\" class=\"table table-striped\"  >");
                    sbLista.append("<thead>");
                    sbLista.append("<tr>");
                    sbLista.append("<th class=\"text-center\">TD</th>");
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
                    sbLista.append("<th class=\"text-center\">TD REF</th>");
                    sbLista.append("<th class=\"text-center\">DOC REF</th>");
                    sbLista.append("<th class=\"text-center\">FEC REF</th>");
                    sbLista.append("<th class=\"text-center\">B.Imp REF</th>");
                    sbLista.append("<th class=\"text-center\">Igv REF</th>");
                    sbLista.append("</tr>");
                    sbLista.append("</thead>");
                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = listVentas.get(i).get("agencia") + "|" + listVentas.get(i).get("serie") + "|" + listVentas.get(i).get("documento") + "|" + listVentas.get(i).get("asiento") + "|" + listVentas.get(i).get("tipodocumento") + "|" + listVentas.get(i).get("documentocliente");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
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

                        sbLista.append("<td>").append(listVentas.get(i).get("ref_serie")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("ref_documento")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("ref_fechadocumento")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("referencia_baseImp")).append("</td>");
                        sbLista.append("<td>").append(listVentas.get(i).get("referenciaigv")).append("</td>");
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
        LOGGER.info("<==== Fin Metodo: agregarItem ====>");
    }


    
    public void exportarExcel_registrodeventas(ActionMapping mapping, ActionForm form,
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
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaRegistro_Ventas("", ls_fecha_ini, ls_fecha_fin);
                if (listVentas != null && !listVentas.isEmpty()) {
          
                //if (listClientes.size() > 0) 
                //{
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
                row.createCell(0).setCellValue("TD");
                row.createCell(1).setCellValue("SERIE");
                row.createCell(2).setCellValue("DOCUMENTO");
                row.createCell(3).setCellValue("FECHA");
                row.createCell(4).setCellValue("MONEDA");
                row.createCell(5).setCellValue("DESCRIPCION");
                row.createCell(6).setCellValue("DOCUMENTO");
                row.createCell(7).setCellValue("RAZON SOCIAL");
                row.createCell(8).setCellValue("NETO");
                row.createCell(9).setCellValue("IGV");
                row.createCell(10).setCellValue("TOTAL");
                
                row.createCell(11).setCellValue("TD REF");
                row.createCell(12).setCellValue("DOC REF ");
                row.createCell(13).setCellValue("FEC REF ");
                
                row.createCell(14).setCellValue("BASE IMP REF");
                row.createCell(15).setCellValue("IGV REF");
                String ls_Sql = "";
                Statement stmt = null;
                String codigoProducto = "";
                nRow = 1;
                int nPosImg = 1;
                indice = 0;
                while (indice < listVentas.size()) {
                    Map<String, Object> obj = listVentas.get(indice);
                    indice++;
                    row = sheet.createRow(nRow);
                    row.setHeight((short) 250);
                    row.createCell(0).setCellValue(Util.nullCad(obj.get("tipodocumento")));
                    row.createCell(1).setCellValue(Util.nullCad(obj.get("serie")));
                    row.createCell(2).setCellValue(Util.nullCad(obj.get("documento")));
                    row.createCell(3).setCellValue(Util.nullCad(obj.get("fechaDocumento")));
                    row.createCell(4).setCellValue(Util.nullCad(obj.get("moneda")));
                    row.createCell(5).setCellValue(Util.nullCad(obj.get("descripcion")));
                    row.createCell(6).setCellValue(Util.nullCad(obj.get("documentocliente")));
                    row.createCell(7).setCellValue(Util.nullCad(obj.get("razonsocial")));
                    row.createCell(8).setCellValue(Util.nullDou(obj.get("baseImponible")));
                    row.createCell(9).setCellValue(Util.nullDou(obj.get("igv")));
                    row.createCell(10).setCellValue(Util.nullDou(obj.get("total")));
                    
                    row.createCell(11).setCellValue(Util.nullCad(obj.get("ref_serie")));
                    row.createCell(12).setCellValue(Util.nullCad(obj.get("ref_documento")));
                    row.createCell(13).setCellValue(Util.nullCad(obj.get("ref_fechadocumento")));
                    row.createCell(14).setCellValue(Util.nullDou(obj.get("referencia_baseImp")));
                    row.createCell(15).setCellValue(Util.nullDou(obj.get("referenciaigv")));
                    nRow++;
                }
                FileOutputStream fileOut = null;
                String nombreFile = "REG VENTAS " + ls_fecha_ini + " al " + ls_fecha_fin + ".xlsx";
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
    
    
}
