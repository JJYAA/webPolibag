package pe.com.gp.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.dao.AutenticaDAO;
import pe.com.gp.entity.Global;
import pe.com.gp.entity.Usuario;
import pe.com.gp.form.LoginForm;
import pe.com.gp.util.Constantes;
import pe.com.gp.dao.GlobalDAO;
import pe.com.gp.entity.BeanError;

import pe.com.gp.entity.Tienda;

import pe.com.gp.util.Util;

public class LoginAuxiliarAction extends DispatchAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TOMCAT_USUARIO_SESSION_KEY = "TomcatUsuarioLogin_" + UUID.randomUUID().toString();
    private static final String TOMCAT_TIENDA_SESSION_KEY = "TomcatTiendaLogin_" + UUID.randomUUID().toString();

    public ActionForward inicializa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("<==== Inicio Metodo: inicializa ====>");
        String mappingFindForward;
        if (Util.sesionEstaActiva(request)) {
            HttpSession session = request.getSession();
            mappingFindForward = "inicializa";
        } else {
            mappingFindForward = "logout";
        }

        return mapping.findForward(mappingFindForward);
    }

    public ActionForward validaIngreso(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//        grabardbf dbf = new grabardbf();
//        dbf.LeerCampos();
//        dbf.grabar();
//        dbf.LeerCampos();

                
        ActionForward forward = mapping.findForward("Teclado");
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        
//        CreateZIP  xx = new CreateZIP();
//        xx.crearZip(request.getServletContext().getRealPath("/"));        
        
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        String empresa = request.getParameter("empresa");
        LoginForm uForm = (LoginForm) form;
        AutenticaDAO autenticaDAO = new AutenticaDAO();
        switch (Util.nullCad(uForm.getProceso())) {
            case "01":                
                ConectaSQL cn = new ConectaSQL(); //.connection();                
                if (cn.connection() == null) {   
                    BeanError er = new BeanError();
                    er.setError(cn.getErrorConeccion());
                    request.setAttribute("BeanError", er);
                    forward = mapping.findForward("conexion");
                } else {

                    Usuario user = autenticaDAO.autenticaWEB(uForm.getEmpresa(), uForm.getUsuario(), uForm.getContrasena());
                    if (user.getExiste() == false) {
                        errors = new ActionErrors();
                        errors.add("error", new ActionMessage("error", Constantes.MSG_USER_NO_REGISTRADO));
                    } else {
                        user.setCodTieLog("00002");
                        GlobalDAO globalDAO = new GlobalDAO();
                        uForm.setEmpresa("09");
                        Global global = globalDAO.getDatosGlobales(uForm.getEmpresa());
                        if (!global.getExiste()) {
                            errors = new ActionErrors();
                            errors.add("error", new ActionMessage("error", Constantes.MSG_TIPO_CAMBIO));
                        } else {
                            String path = session.getServletContext().getRealPath("/");
                            final String codUsuLog = Util.nullCad(user.getCodigo());
                            final String codTieLog = Util.nullCad(user.getCodTieLog());
                            long secuenciaTemporal = 100; //new SecuenciaDAO().getSecuenciaTemporal(); // NO CREAR SECUENCIAS AQUI, CREARLAS EN SU MODULO RESPECTIVO. GRACIAS
                            long secuenciaProforma = 200; //new SecuenciaDAO().getSecuenciaTemporal(); // NO CREAR SECUENCIAS AQUI, CREARLAS EN SU MODULO RESPECTIVO. GRACIAS                        
                            //   Tienda tienda = new TiendaDAO().obtenerDatosTienda(uForm.getEmpresa()); /// ESTO DEBERIA LEER DE SAP
                            user.setSecuencia(secuenciaTemporal);
                            user.setSecuencia_proforma(secuenciaProforma);
                            //user.setCodigoEmpleadoVenta(autenticaDAO.codigoEmpleadoVenta(uForm.getUsuario()));
                            user.setPath(path);

                            // ===================
                            // Variables de sesion
                            // ===================                        
                            session.setAttribute("Usuario", user);
                            session.setAttribute("Empresa", uForm.getEmpresa());
                            // session.setAttribute("Tienda", tienda);
                            session.setAttribute("Global", global);
                            session.setAttribute("ID", session.getId());

                            // Variables SOLO para usar en el monitoreo del Tomcat
                            session.setAttribute(TOMCAT_TIENDA_SESSION_KEY, codTieLog);
                            session.setAttribute(TOMCAT_USUARIO_SESSION_KEY, user.getNombre());

                            // ===================
                            // Menus y Accesos
                            // ===================  
                            // session.setAttribute("listOpcMnuConAcc", new AutenticaDAO().opcMenusConAcceso(codTieLog, codUsuLog, Constantes.COD_SISGP));
                            // ==============
                            // Log de accesos
                            // ==============
                            // acabello: Lo estoy colocando en un hilo separado
                            // para que no interfiera en el tiempo de logueo.
//                            final String ip = Util.obtenerIP(request);
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String result = new AutenticaDAO().insertarLogAccesos(
//                                            codTieLog,
//                                            ip,
//                                            codUsuLog,
//                                            null,
//                                            Constantes.COD_SISGP_WEB,
//                                            null
//                                    );
//                                    if (result != null) {
//                                        LOGGER.error("GP.ERROR: {}", result);
//                                    }
//                                }
//                            }).start();
                            // Redireccionar a otra pagina que no sea el index
                            /*switch (Util.nullCad(uForm.getAuxiliar())) {
                                case "aprLiqOtEspecial": // Aprobacion de Liquidacion Especial de OT
                                    forward = mapping.findForward("aprLiqOtEspecial");
                                    break;
                                case "aprOrdSalOT": // Aprobacion de Aprobacion de OT para Orden de Salida
                                    forward = mapping.findForward("aprOrdSalOT");
                                    break;
                                default:
                                    forward = mapping.findForward("mnuMain");
                                    break;
                            }*/
                            forward = mapping.findForward("mnuMain");
                        }
                    }
                }
                break;

            case "logout":
                session.removeAttribute("Usuario");
                session.removeAttribute("Tienda");
                session.removeAttribute("Global");
                session.removeAttribute("ID");
                session.removeAttribute("ConfigRepuestos");
                session.removeAttribute("ConfigServicios");
                session.removeAttribute(TOMCAT_USUARIO_SESSION_KEY);
                session.removeAttribute(TOMCAT_TIENDA_SESSION_KEY);
                session.removeAttribute("listOpcMnuConAcc");
                session.invalidate();
                forward = mapping.findForward("Teclado");
                break;
        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }

        return forward;

    }

    public ActionForward logout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = mapping.findForward("Teclado");
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        String empresa = request.getParameter("empresa");
        LoginForm uForm = (LoginForm) form;
        AutenticaDAO autenticaDAO = new AutenticaDAO();
        session.removeAttribute("Usuario");
        session.removeAttribute("Tienda");
        session.removeAttribute("Global");
        session.removeAttribute("ID");
        session.removeAttribute("ConfigRepuestos");
        session.removeAttribute("ConfigServicios");
        session.removeAttribute(TOMCAT_USUARIO_SESSION_KEY);
        session.removeAttribute(TOMCAT_TIENDA_SESSION_KEY);
        session.removeAttribute("listOpcMnuConAcc");
        session.invalidate();
        forward = mapping.findForward("Teclado");

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }

        return forward;

    }

}
