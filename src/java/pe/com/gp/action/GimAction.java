/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.com.gp.action;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.json.JSONObject;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.dao.GimDAO;
import pe.com.gp.dao.Trans_VentasDAO;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.GimForm;
import pe.com.gp.form.Trans_VentasForm;
import pe.com.gp.util.Constantes;
import pe.com.gp.util.Util;

/**
 *
 * @author Administrador
 */
public class GimAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();

    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            GimForm uform = (GimForm) form;
            List<Map<String, Object>> listGim = new GimDAO().listaGim();
            uform.setPathServidor(Util.nullCad(listGim.get(0).get("pathservidor")));
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializa";
        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }

    public ActionForward init_datos_proveedor(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            GimForm uform = (GimForm) form;
            List<Map<String, Object>> listGim = new GimDAO().listaGim();
            uform.setPathServidor(Util.nullCad(listGim.get(0).get("pathservidor")));
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "init_datos_proveedor";
        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }    
    public ActionForward grabaPath(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: grabaPath ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            GimForm uform = (GimForm) form;
            String resultado = new GimDAO().ActualizaPathServidor(uform.getPathServidor());
            if ("".equals(resultado)) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito" + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(resultado, false));
            }

            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgGim";
        } else {
            mappingFindForward = "logout";
        }
        LOGGER.info("<==== FIN Metodo: grabaPath ====>");
        return mapping.findForward(mappingFindForward);
    }

    public ActionForward inicializaCarga(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            GimForm uform = (GimForm) form;
            List<Map<String, Object>> listGim = new GimDAO().listaGim();
            uform.setPathServidor(Util.nullCad(listGim.get(0).get("pathservidor")));
            uform.setFlagMueOcuForm("muestra");
            mappingFindForward = "inicializaCarga";
        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }

    
    
    
    public ActionForward actualizaDatos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            String path = request.getServletContext().getRealPath("/");
            GimForm uform = (GimForm) form;
            new GimDAO().Inicializa_tablas_dbf();
//            List<Map<String, Object>> listGim = new GimDAO().listaGim();
//            uform.setPathServidor(Util.nullCad(listGim.get(0).get("pathservidor")));
            String msgResult="";
            String msgError="";     
            String sqlQuery ="";
            String pRuta = "";
            PreparedStatement pstmt ;
            Date date = new Date(0);
            Connection cn = new ConectaSQL().connection();

            //--------------------------------------  MAESTRO  ------------------------------------------------------------------------
            pRuta = path + Constantes.DIRECTORIO_DBF + "//CAN03.dbf";
            File file = new File(pRuta);
            if (file.exists())
            {
                InputStream inputStreamMA = new FileInputStream(pRuta); // take dbf file as program argument
                DBFReader readerMA = new DBFReader(inputStreamMA);
                int numberOfFieldsMA = readerMA.getFieldCount();
                DBFRow rowMA;                
                String AVANEXO = "";
                String ACODANE = "";
                String ADESANE = "";
                String AREFANE = "";
                String ARUC = "";
                String ACODMON = "";
                String AESTADO = "";
                String ADATE = "";
                String AHORA = "";
                String AVRETE = "";
                double APORRE = 0 ; 
                sqlQuery = "insert into maestro values(?,?,?,?,?,?,?,?,?,?,?)";
                pstmt = cn.prepareStatement(sqlQuery);
                try {
                    while (((rowMA = readerMA.nextRow()) != null)) 
                    {
                        AVANEXO = rowMA.getString("AVANEXO");  //1
                        ACODANE = rowMA.getString("ACODANE");  //2
                        ADESANE = rowMA.getString("ADESANE");  //3
                        AREFANE = rowMA.getString("AREFANE");  //4
                        ARUC = rowMA.getString("ARUC");  //5
                        ACODMON = rowMA.getString("ACODMON");  //6
                        AESTADO = rowMA.getString("AESTADO");  //7
                        ADATE = rowMA.getString("ADATE");  //8
                        AHORA = rowMA.getString("AHORA");  //9
                        AVRETE = rowMA.getString("AVRETE");  //10
                        APORRE = rowMA.getDouble("APORRE");  //11
                        
                        pstmt.setString(1, AVANEXO);    //1
                        pstmt.setString(2, ACODANE);   //2
                        pstmt.setString(3, ADESANE);   //3
                        pstmt.setString(4, AREFANE);   //4
                        pstmt.setString(5, ARUC);   //5
                        pstmt.setString(6, ACODMON);   //6
                        pstmt.setString(7, AESTADO); //7
                        //pstmt.setString(8, ADATE);   //8
                        pstmt.setDate(8, date.valueOf( "1998-1-17" )); //8
                        pstmt.setString(9, AHORA);   //9
                        pstmt.setString(10, AVRETE);   //10
                        pstmt.setDouble(11, APORRE);           //11
                        pstmt.addBatch();                                                                      
                    }
                    int[] result = pstmt.executeBatch();  
                    msgResult = Util.nullCad(result.length) ;    
                    msgError="";                    
                }catch (Exception e) {
                    msgError = e.toString();               
                } finally {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (cn != null) {
                        cn.close();
                    }
                }
                DBFUtils.close(readerMA);
                if ( "".equals(msgError)) {
                    errors.add("exito4", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito. ANEXOS -RUC - Registros transferidos : " + msgResult + "</strong>", false));
                } else {
                    errors.add("error", new ActionMessage(msgError, false));
                }
                if (!errors.isEmpty()) {
                    saveErrors(request, errors);
                }                 
                    
                
                
            }
            else 
            {
                msgError="CAN03.dbf";
                errors.add("error", new ActionMessage("El archivo no existe(" + msgError + ")", false));
                if (!errors.isEmpty()) {
                    saveErrors(request, errors);                 
                }                   
            }

            //--------------------------------------  ANEXOS  ------------------------------------------------------------------------
            cn = new ConectaSQL().connection();
            pRuta = path + Constantes.DIRECTORIO_DBF + "//CAO03.dbf";
            file = new File(pRuta);
            if (file.exists())
            {
                InputStream inputStreamCP = new FileInputStream(pRuta); // take dbf file as program argument
                DBFReader readerCP = new DBFReader(inputStreamCP);
                int numberOfFieldsCP = readerCP.getFieldCount();
                DBFRow rowCP;
                String AVANEXO = "" ;
                String ACODANE = ""   ;
                String APATERNO = ""  ;
                String AMATERNO  = "" ;
                String ANOMBRE  = ""  ;
                String ATIPTRA  = ""  ;
                Date AFECCRE  ;
                String ADIRECC  = ""  ;
                String ADOCIDE  = ""  ;
                String ANUMIDE  = ""  ;
                String ATIPPRO  = ""  ;

                sqlQuery = "insert into maestro_prov_cli values(?,?,?,?,?,?,?,?,?,?,?)";
                pstmt = cn.prepareStatement(sqlQuery);
                try {
                    while (((rowCP = readerCP.nextRow()) != null)) {
                        AVANEXO = rowCP.getString("AVANEXO");  //1
                        ACODANE = rowCP.getString("ACODANE");  //2
                        APATERNO = rowCP.getString("APATERNO");  //3
                        AMATERNO = rowCP.getString("AMATERNO");  //4
                        ANOMBRE = rowCP.getString("ANOMBRE");  //5
                        ATIPTRA = rowCP.getString("ATIPTRA");  //6
                        //AFECCRE = rowCP.getDate("AFECCRE");  //7
                        ADIRECC = rowCP.getString("ADIRECC");  //8
                        ADOCIDE = rowCP.getString("ADOCIDE");  //9
                        ANUMIDE = rowCP.getString("ANUMIDE");  //10
                        ATIPPRO = rowCP.getString("ATIPPRO");  //11    



                        pstmt.setString(1, AVANEXO);    //1
                        pstmt.setString(2, ACODANE);   //2
                        pstmt.setString(3, APATERNO);   //3
                        pstmt.setString(4, AMATERNO);   //4
                        pstmt.setString(5, ANOMBRE);   //5
                        pstmt.setString(6, ATIPTRA);   //6
                        pstmt.setDate(7, date.valueOf( "1998-1-17" )); //7
                        pstmt.setString(8, ADIRECC);   //8
                        pstmt.setString(9, ADOCIDE);   //9
                        pstmt.setString(10, ANUMIDE);   //10
                        pstmt.setString(11, ATIPPRO);           //11
                        pstmt.addBatch();
                    }
                    int[] result = pstmt.executeBatch();  
                     msgResult = Util.nullCad(result.length) ;    
                     msgError="";
                } catch (Exception e) {
                    msgError = e.toString();               
                } finally {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (cn != null) {
                        cn.close();
                    }
                }
                DBFUtils.close(readerCP);
                if ( "".equals(msgError)) {
                    errors.add("exito", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito. ANEXOS - Registros transferidos : " + msgResult + "</strong>", false));
                } else {
                    errors.add("error", new ActionMessage(msgError, false));
                }
                if (!errors.isEmpty()) {
                    saveErrors(request, errors);
                }                    
            } else {
                msgError="CAO03.dbf";
                errors.add("error", new ActionMessage("El archivo no existe(" + msgError + ")", false));
                if (!errors.isEmpty()) {
                    saveErrors(request, errors);                 
                }                    
            }
        



            //--------------------------------------  DETALLE  ------------------------------------------------------------------------
            cn = new ConectaSQL().connection();
            pRuta = path + Constantes.DIRECTORIO_DBF + "//CCC0323.dbf";
            InputStream inputStreamCab = new FileInputStream(pRuta); // take dbf file as program argument
            DBFReader readerCab = new DBFReader(inputStreamCab);
            int numberOfFieldsCab = readerCab.getFieldCount();
            DBFRow rowcab;
            String CSUBDIA = "";
            String CCOMPRO   = "";
            String CFECCOM    = "";
            String CCODMON     = "";
            String CSITUA    = "";
            double CTIPCAM   = 0 ;
            String CGLOSA   = "";
            double CTOTAL    = 0;
            String CTIPO       = "";
            String CFLAG         = "";  
                   
            String CHORA             = "";  
            String CUSER             = "";  
            String CFECCAM           = "";  
            String CORIG         = "";      
            String CFORM         = "";      
            String CTIPCOM        = "";     
            String CEXTOR         = "";     
            date = new Date(0);
            
            Date CDATE  ; 
            Date CFECCOM2  ; 
            Date CFECCAM2   ;  
            
            msgResult="";
            msgError="";            
            sqlQuery = "insert into comprobante_cab values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = cn.prepareStatement(sqlQuery);
            try {
                while (((rowcab = readerCab.nextRow()) != null)) {
                    CSUBDIA = rowcab.getString("CSUBDIA");  //1
                    CCOMPRO = rowcab.getString("CCOMPRO");  //2
                    CFECCOM = rowcab.getString("CFECCOM");  //3
                    CCODMON = rowcab.getString("CCODMON");  //4
                    CSITUA = rowcab.getString("CSITUA");  //5
                    CTIPCAM = rowcab.getDouble("CTIPCAM");  //6
                    CGLOSA = rowcab.getString("CGLOSA");  //7
                    CTOTAL = rowcab.getDouble("CTOTAL");  //8
                    CTIPO = rowcab.getString("CTIPO");  //9
                    CFLAG = rowcab.getString("CFLAG");  //10    
                    
                    CHORA = rowcab.getString("CHORA");  //11
                    CUSER = rowcab.getString("CUSER");  //12
                    CFECCAM = rowcab.getString("CFECCAM");  //13
                    CORIG = rowcab.getString("CORIG");  //14                    
                    CFORM = rowcab.getString("CFORM");  //15
                    CTIPCOM = rowcab.getString("CTIPCOM");  //16
                    CEXTOR = rowcab.getString("CEXTOR");  //17
                   // CDATE = rowcab.getDate("CDATE");
                   // CFECCOM2 = rowcab.getDate("CFECCOM2");  //19
                   // CFECCAM2 = rowcab.getDate("CFECCAM2");  //20
                    

                    pstmt.setString(1, CSUBDIA);    //1
                    pstmt.setString(2, CCOMPRO);   //2
                    pstmt.setString(3, CFECCOM);   //3
                    pstmt.setString(4, CCODMON);   //4
                    pstmt.setString(5, CSITUA);   //5
                    pstmt.setDouble(6, CTIPCAM);   //6
                    pstmt.setString(7, CGLOSA);   //7
                    pstmt.setDouble(8, CTOTAL);   //8
                    pstmt.setString(9, CTIPO);   //9
                    pstmt.setString(10, CFLAG);           //10    
                    pstmt.setDate(11, date.valueOf( "1998-1-17" )); 
                    pstmt.setString(12, CHORA);   //11
                    pstmt.setString(13, CUSER);   //12
                    pstmt.setString(14, CFECCAM);   //13
                    pstmt.setString(15, CORIG);   //14
                    pstmt.setString(16, CFORM);   //15
                    pstmt.setString(17, CTIPCOM);   //16
                    pstmt.setString(18, CEXTOR);   //17
                    pstmt.setDate(19,date.valueOf( "1998-1-17" ) );
                    pstmt.setDate(20, date.valueOf( "1998-1-17" ) );                    
                    pstmt.addBatch();
                }
                int[] result = pstmt.executeBatch();  
                 msgResult = Util.nullCad(result.length) ;    
                 msgError="";
            } catch (Exception e) {
                msgError = e.toString();               
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (cn != null) {
                    cn.close();
                }
            }
            DBFUtils.close(readerCab);
            if ( "".equals(msgError)) {
                errors.add("exito2", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito. CABECERA - Registros transferidos : " + msgResult + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(msgError, false));
            }
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }            
            
            
            
            
            //--------------------------------------  DETALLE  ------------------------------------------------------------------------
            pRuta = path + Constantes.DIRECTORIO_DBF + "//CCD0323.dbf";

            InputStream inputStream = new FileInputStream(pRuta); // take dbf file as program argument
            DBFReader reader = new DBFReader(inputStream);
            int numberOfFields = reader.getFieldCount();
            DBFRow row;

            String DSUBDIA = ""; //varchar(2) 
            String DCOMPRO = ""; //varchar(6) 
            String DSECUE = ""; //varchar(4) 
            String DFECCOM = ""; //varchar(6) 
            String DCUENTA = ""; //varchar(8) 
            String DCODANE = ""; //varchar(18)            
            String DCODMON = ""; //varchar(2) 
            String DDH = ""; //varchar(1) 
            String DTIPDOC = ""; //varchar(2) 
            String DNUMDOC = ""; //varchar(20) 
            String DCENCOS = "";
            String DCODARC = ""; // varchar(2)
            double DIMPORT = 0; // numeric(14,2) 
            double DUSIMPOR = 0; // numeric(14,2)
            double DMNIMPOR = 0; //numeric(14,2)     
            String DFECDOC = "";
            long xx = reader.getRecordCount();
            cn = new ConectaSQL().connection();
            sqlQuery = "insert into comprobante_Det values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = cn.prepareStatement(sqlQuery);
            try {
                while (((row = reader.nextRow()) != null)) {
                    DSUBDIA = row.getString("DSUBDIA");  //1
                    DCOMPRO = row.getString("DCOMPRO");  //2
                    DSECUE = row.getString("DSECUE");  //3
                    DFECCOM = row.getString("DFECCOM");  //4
                    DCUENTA = row.getString("DCUENTA");  //5
                    DCODANE = row.getString("DCODANE");  //6
                    DCENCOS = row.getString("DCENCOS");  //7
                    DCODMON = row.getString("DCODMON");  //8
                    DDH = row.getString("DDH");  //9
                    DIMPORT = row.getDouble("DIMPORT");  //10
                    DTIPDOC = row.getString("DTIPDOC");  //11
                    DNUMDOC = row.getString("DNUMDOC");  //12
                    DUSIMPOR = row.getDouble("DUSIMPOR");  //13
                    DMNIMPOR = row.getDouble("DMNIMPOR");  //14
                    DCODARC = row.getString("DCODARC");  //15
                    DFECDOC = row.getString("DFECDOC"); 
                    
                    pstmt.setString(1, DSUBDIA);
                    pstmt.setString(2, DCOMPRO);
                    pstmt.setString(3, DSECUE);
                    pstmt.setString(4, DFECCOM);
                    pstmt.setString(5, DCUENTA);
                    pstmt.setString(6, DCODANE);
                    pstmt.setString(7, DCENCOS);
                    pstmt.setString(8, DCODMON);
                    pstmt.setString(9, DDH);
                    pstmt.setDouble(10, DIMPORT);
                    pstmt.setString(11, DTIPDOC);
                    pstmt.setString(12, DNUMDOC);
                    pstmt.setDouble(13, DUSIMPOR);
                    pstmt.setDouble(14, DMNIMPOR);
                    pstmt.setString(15, DCODARC);
                    pstmt.setString(16, DFECDOC);
                    pstmt.addBatch();
                }
                int[] result = pstmt.executeBatch();  
                 msgResult = Util.nullCad(result.length) ;    
                 msgError="";
            } catch (Exception e) {
                msgError = e.toString();               
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (cn != null) {
                    cn.close();
                }
            }
            DBFUtils.close(reader);
            if ( "".equals(msgError)) {
                errors.add("exito3", new ActionMessage("<strong>" + "Transacci&oacute;n terminada. Proceso termino con exito. DETALLE - Registros transferidos : " + msgResult + "</strong>", false));
            } else {
                errors.add("error", new ActionMessage(msgError, false));
            }
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
            }
            uform.setFlagMueOcuForm("ocultar");
            mappingFindForward = "msgGim";
        } else {
            mappingFindForward = "logout";
        }
        return mapping.findForward(mappingFindForward);
    }

    public void buscaOC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //long seqTmp1 = Util.nullLon(session.getAttribute(SEQ_TMP1_SESSION_KEY));
                String ls_oc = Util.nullCad(request.getParameter("oc"));
                StringBuilder sbLista = new StringBuilder();
                List<Map<String, Object>> listVentas = new GimDAO().buscaOC(ls_oc);
                if (listVentas != null && !listVentas.isEmpty()) {
                    jsonObjDatos.put("factura",listVentas.get(0).get("factura") );
                    jsonObjDatos.put("fecha", listVentas.get(0).get("fecha"));
                    jsonObjDatos.put("proveedor", listVentas.get(0).get("proveedor"));
                    jsonObjDatos.put("razonsocial", listVentas.get(0).get("razonsocial"));
                    jsonObjDatos.put("ordencompra", listVentas.get(0).get("ordencompra"));
                    jsonObjDatos.put("referencia", listVentas.get(0).get("referencia"));
                    
                } else {
                    neto = 0;
                    msgError="Orden de compras no registrada";
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
        
    public void actualizaOC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                
                String oc = Util.nullCad(request.getParameter("oc"));
                String ruc = Util.nullCad(request.getParameter("ruc"));
                String fecha = Util.nullCad(request.getParameter("fecha"));
                String numerofactura = Util.nullCad(request.getParameter("numerofactura"));
                String referencia = Util.nullCad(request.getParameter("referencia"));

                String result = new GimDAO().actualizaOC(oc,ruc,fecha,numerofactura,referencia);
                
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

    
}
