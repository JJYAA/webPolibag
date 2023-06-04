/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import org.apache.struts.actions.DispatchAction;
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
 * @author user
 */
public class costoVentaAction extends DispatchAction {

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

    public void listaCosto_ventas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                new Trans_VentasDAO().carga_costos(seqTmp1, ls_fecha_ini, ls_fecha_fin);
                List<Map<String, Object>> listVentas = new Trans_VentasDAO().listaCosto_Ventas(seqTmp1);
                sbLista.append("<table id=\"tablaCostodeVentas\" class=\"table table-striped\"  >");
                sbLista.append("<thead>");
                sbLista.append("<tr>");
                sbLista.append("<th class=\"text-center\">Cliente</th>");
                sbLista.append("<th class=\"text-center\">Razon socialh>");
                sbLista.append("<th class=\"text-center\">T.D.</th>");
                sbLista.append("<th class=\"text-center\">Serie</th>");
                sbLista.append("<th class=\"text-center\">Documento</th>");
                sbLista.append("<th class=\"text-center\">Fecha</th>");
                sbLista.append("<th class=\"text-center\">Moneda</th>");
                sbLista.append("<th class=\"text-center\">Costo</th>");
                sbLista.append("<th class=\"text-center\">Asiento</th>");
                sbLista.append("<th class=\"text-center\">").append("Asiento").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodos\" name=\"chkbxTodos\" class=\"chkbxTodos\" onclick=\"seleccinadosTodos()\" />").append("</th>");
                sbLista.append("<th class=\"text-center\">").append("Eliminar").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodosDel\" name=\"chkbxTodosDel\" class=\"chkbxTodosDel\" onclick=\"seleccinadosElimina()\" />").append("</th>");
                sbLista.append("<th class=\"text-center\">").append("Xls").append("<br>").append("<input type=\"checkbox\" id=\"chkbxTodosXls\" name=\"chkbxTodosXls\" class=\"chkbxTodosXls\" onclick=\"seleccinadosTodosXls()\" />").append("</th>");
                sbLista.append("</tr>");
                sbLista.append("</thead>");
                if (listVentas != null && !listVentas.isEmpty()) {

                    String CadSelected = "";
                    long asiento = 0;
                    for (int i = 0; i < listVentas.size(); i++) {
                        CadSelected = listVentas.get(i).get("agencia") + "|" + 
                                listVentas.get(i).get("serie") + "|" + 
                                listVentas.get(i).get("documento") + "|" + 
                                listVentas.get(i).get("asiento") + "|" + 
                                listVentas.get(i).get("tipodocumento") + "|" + 
                                listVentas.get(i).get("codigocli");
                        sbLista.append("<tr>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("codigocli")).append("</td>");
                        sbLista.append("<td class=\"text-left\" >").append(listVentas.get(i).get("nombrecli")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("tipodocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("serie")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("documento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("fechaDocumento")).append("</td>");
                        sbLista.append("<td class=\"text-center\" >").append(listVentas.get(i).get("moneda")).append("</td>");
                        sbLista.append("<td class=\"text-right\" >").append(Util.redondearDecimales(Util.nullDou(listVentas.get(i).get("costo")),4)).append("</td>");
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

    public ActionForward conAsientoCostos(ActionMapping mapping, ActionForm form,
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
            String pTipo = "33";  // costo de ventas
            String pTipoDocumento = "00";
            String pDocumentoCliente = "";
            String pPeriodo = uform.getFechaFin().substring(3, 5);
            String pAnho = uform.getFechaFin().substring(6, 10);
            long pAsiento =0;
            Boolean salir = false;
            String[] arrayExiste = new String[2];
            int ln_max = arrSelected.length - 1;
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie = AuxItem[1];
                pDocumento = AuxItem[2];
                pAsiento = 0;
                pTipoDocumento = AuxItem[4];
                pDocumentoCliente = AuxItem[5].trim();
                //  DBFReader reader = null;
                String ls_ACODANE = "";
                String ls_AVANEXO = "";
                try {
                    String ls_error = new Trans_VentasDAO().AsientoVentas_01(empresa, pAgencia, pSerie, pDocumento, pPeriodo, pUsuario, pAsiento, pTipo, pTipoDocumento, pAnho,pDocumentoCliente);
                } catch (DBFException e) {
                    e.printStackTrace();
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
            String pTipo = "33";
            String pAsiento = "";
            String pTipoDocumento = "";
            String pDocumentoCliente = "";
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
                pDocumentoCliente = AuxItem[5].trim();
                                
                String ls_error = new Trans_VentasDAO().AsientoDesactiva_01(empresa, pAgencia, pSerie, pDocumento, pUsuario, pAsiento, pTipo, pTipoDocumento,pDocumentoCliente);
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

    public void exportarExcel_ventas(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            int indice = 0;
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            String ls_fecha_ini = Util.yyyymmdd(request.getParameter("fechaIni"));
            String ls_fecha_fin = Util.yyyymmdd(request.getParameter("fechaFin"));
            int nRow = 0;
            Trans_VentasForm uform = (Trans_VentasForm) form;
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Migrar Ventas");

            Connection cn = new ConectaSQL().connection();

            long seqTmp1 = new Util().secuencia(); //Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));

            String resultCalculos = "";
            ActionErrors errors = new ActionErrors();
            String empresa = (String) session.getAttribute("Empresa");
            
//            String docSelected = uform.getDocSelectedXls();
            AsientoDAO asiento = new AsientoDAO();

            int i = 0;
            String msg;
            String pAgencia = "";
            String pSerie = "";
            String pDocumento = "";
            String pUsuario = "SA";
            String pTipo = "33";
            String pTipoDocumento = "33";
            String pDocumentoCliente = "";
            String pPeriodo = uform.getFechaFin().substring(3, 5);
            String pAnho = uform.getFechaFin().substring(6, 10);


            String ls_asiento = "";
            String ls_error = new Trans_VentasDAO().creaItemsAsiento_xls(seqTmp1, "", ls_fecha_ini, ls_fecha_fin);

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
            String docSelected = uform.getDocSelectedXls();
            nRow = 3;
            String[] arrSelected = docSelected.split(",");
            int ln_max = arrSelected.length - 1;
            int nPosImg = 1;
            String pAsiento="";
            //String pAgencia="";
            Boolean salir = false;
            String sqlQuery = "insert into asientos_tmp ("
                    + "secuencia,"
                    + "periodo,"
                    + "asiento,"
                    + "tipo,"
                    + "td,"
                    + "anho,"
                    + "facturapro_oc,"
                    + "orden_compra,"
                    + "tipo_comprobante,"
                    + "codigoCliente) "
                    + "values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(sqlQuery);
            i=0;
            while ((i <= ln_max) && (!salir)) {
                String[] AuxItem = arrSelected[i].split("\\|");
                pAgencia = AuxItem[0];
                pSerie  = AuxItem[1];
                pDocumento = AuxItem[2].trim();
                pAsiento = AuxItem[3].trim();
                pTipoDocumento = AuxItem[4];
                pDocumentoCliente = AuxItem[5].trim();
                pstmt.setLong(1, seqTmp1);    //1
                pstmt.setString(2, pPeriodo);   //2
                pstmt.setString(3, pAsiento);   //3
                pstmt.setString(4, "33");   //4
                pstmt.setString(5, pTipoDocumento);   //5
                pstmt.setString(6, pAnho);   //6
                pstmt.setString(7, pDocumento); //7              
                pstmt.setString(8, pAgencia);   //8
                pstmt.setString(9, "33");   //9
                pstmt.setString(10, pDocumentoCliente);             //18   
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

        } catch (IOException ioex) {
            LOGGER.error("GP.ERROR: {}", ioex.toString());

        }

    }

    
    
}
