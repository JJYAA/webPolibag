package pe.com.gp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.com.gp.connection.ConectaSQL;

/**
 * Clase con Snippets utiles para el desarrollo con Java.
 *
 * @author: acabello
 * @version: 23/10/2017
 */
public final class Util {

    private static final Logger LOGGER = LogManager.getLogger();

    // =========================================================================
    // ACCESO A DATOS
    // =========================================================================
    /**
     * Ejecuta un Stored Procedure y devuelve un resultado; solo funciona para 1
     * parametro de salida.
     *
     * @param sp Stored Procedure
     * @param tipoParamOUT Tipo de dato de salida
     * @param parameters Parametros
     * @return
     * @throws java.lang.Exception
     */
    public static Object sp_ejecuta(String sp, Integer tipoParamOUT, Object[] parameters) throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        Object result = null;
        try {
            cs = cn.prepareCall(sp);
            if (parameters == null || parameters.length == 0) {
                if (tipoParamOUT != null) {
                    cs.registerOutParameter(1, tipoParamOUT);
                    cs.executeUpdate();
                    result = cs.getObject(1);
                } else {
                    cs.executeUpdate();
                }
            } else {
                int i = 0;
                for (Object parameter : parameters) {
                    cs.setObject(++i, parameter);
                }
                if (tipoParamOUT != null) {
                    cs.registerOutParameter(parameters.length + 1, tipoParamOUT);
                    cs.executeUpdate();
                    result = cs.getObject(parameters.length + 1);
                } else {
                    cs.executeUpdate();
                }
            }
        } catch (Exception e) {
            LOGGER.error("GP.ERROR: {}", e);
            result = "" + e;
        } finally {
            close(cn);
            close(cs);
        }
        return result;
    }

    /**
     * Ejecuta una Funcion y devuelve un resultado.
     *
     * @param fn Funcion
     * @param tipoParamOUT Tipo de dato de salida
     * @param parameters Parametros
     * @return
     * @throws java.lang.Exception
     */
//    public static Object fn_ejecuta(String fn, Integer tipoParamOUT, Object[] parameters) throws Exception {
//        Connection cn = new ConectaDb().connection();
//        CallableStatement cs = null;
//        Object result = null;
//        try {
//            cs = cn.prepareCall(fn);
//            cs.registerOutParameter(1, tipoParamOUT);
//            if (parameters != null && parameters.length > 0) {
//                int i = 1; // Para funcion debe empezar en uno
//                for (Object parameter : parameters) {
//                    cs.setObject(++i, parameter);
//                }
//            }
//            cs.execute();
//            result = cs.getObject(1);
//        } catch (Exception e) {
//            LOGGER.error("GP.ERROR: {}", e);
//            throw e;
//        } finally {
//            close(cn);
//            close(cs);
//        }
//        return result;
//    }

    /**
     * Ejecuta una sentencia SQL
     *
     * @param sql sentencia SQL
     * @param parameters Parametros
     * @return null si todo salio OK sino devuelve el error capturado.
     */
    public static String sql_ejecuta(String sql, Object[] parameters) {
        Connection cn = new ConectaSQL().connection();
        PreparedStatement ps = null;
        String result = null;
        try {
            cn.setAutoCommit(false);
            ps = cn.prepareStatement(sql);
            if (parameters != null && parameters.length > 0) {
                int i = 0;
                for (Object parameter : parameters) {
                    ps.setObject(++i, parameter);
                }
            }
            ps.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (Exception e) {
            result = "" + e;
            try {
                cn.rollback();
            } catch (Exception ex) {
                result = "" + ex;
                LOGGER.error("ERROR: {}", ex);
            }
            LOGGER.error("ERROR: {}", e);
        } finally {
            close(cn);
            close(ps);
        }
        return result;
    }

    /**
     * Ejecuta una sentencia sql con o sin parametros, devuelve una lista de
     * mapas, tener cuidado ya que oracle siempre devuelve los nombres de los
     * campos en MAYUSCULAS a no ser que se los ponga entre comillas.
     *
     * @param sql
     * @param parameters
     * @return
     * @throws Exception
     */
//    public static List<Map<String, Object>> sql_consulta(String sql, Object[] parameters) throws Exception {
//        Connection cn = new ConectaDb().connection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Map<String, Object>> list = new ArrayList<>();
//        try {
//            ps = cn.prepareStatement(sql);
//            if (parameters != null && parameters.length > 0) {
//                int i = 0;
//                for (Object parameter : parameters) {
//                    ps.setObject(++i, parameter);
//                }
//            }
//            rs = ps.executeQuery();
//            ResultSetMetaData meta = rs.getMetaData();
//            int numColumns = meta.getColumnCount();
//            while (rs.next()) {
//                Map<String, Object> row = new LinkedHashMap<>();
//                for (int j = 1; j <= numColumns; ++j) {
//                    String name = meta.getColumnName(j);
//                    Object value = rs.getObject(j);
//                    row.put(name, value);
//                }
//                list.add(row);
//            }
//        } catch (Exception e) {
//            LOGGER.error("GP.ERROR: {}", e);
//            throw e;
//        } finally {
//            close(cn);
//            close(rs);
//            close(ps);
//        }
//        return list;
//    }

    /**
     * Cerrar un objeto Connection
     *
     * @param cn
     */
    public static void close(Connection cn) {
        if (cn != null) {
            try {
                cn.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto ResultSet
     *
     * @param rs
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto CallableStatement
     *
     * @param cs
     */
    public static void close(CallableStatement cs) {
        if (cs != null) {
            try {
                cs.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto PreparedStatement
     *
     * @param ps
     */
    public static void close(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto Statement
     *
     * @param stmt
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto ByteArrayOutputStream
     *
     * @param baos
     */
    public static void close(ByteArrayOutputStream baos) {
        if (baos != null) {
            try {
                baos.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto ServletOutputStream
     *
     * @param out
     */
    public static void close(ServletOutputStream out) {
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto PrintWriter
     *
     * @param out
     */
    public static void close(PrintWriter out) {
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto OutputStream
     *
     * @param out
     */
    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto FileOutputStream
     *
     * @param out
     */
    public static void close(FileOutputStream out) {
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (final Exception e) {
            }
        }
    }

    /**
     * Cerrar un objeto FileInputStream
     *
     * @param fis
     */
    public static void close(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (final Exception e) {
            }
        }
    }

    // =========================================================================
    // MENSAJERIA ( BOOTSTRAP )
    // =========================================================================
    public static String msgError(String msg, boolean cerrable) {
        StringBuilder sb = new StringBuilder();
        if (cerrable == true) {
            sb.append("<div class=\"alert alert-danger fade in alert-dismissable\" role=\"alert\">");
            sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>");
        } else {
            sb.append("<div class=\"alert alert-danger\" role=\"alert\">");
        }
        sb.append(Constantes.ICON_CLEAR);
        if (msg == null) {
            sb.append("&nbsp;<strong>Error ! ... </strong> " + Constantes.MENSAJE_ERROR + ".");
        } else {
            sb.append("&nbsp;<strong>Error ! ... </strong> ").append(msg);
        }
        sb.append("</div>");
        return sb.toString();
    }

    public static String msgInfo(String msg, boolean cerrable) {
        StringBuilder sb = new StringBuilder();
        if (cerrable == true) {
            sb.append("<div class=\"alert alert-info fade in alert-dismissable\" role=\"alert\">");
            sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>");
        } else {
            sb.append("<div class=\"alert alert-info\" role=\"alert\">");
        }
        sb.append(Constantes.ICON_INFO);
        if (msg == null) {
            sb.append("&nbsp;<strong>Informaci&oacute;n ! ... </strong> " + Constantes.MENSAJE_INFO + ".");
        } else {
            sb.append("&nbsp;<strong>Informaci&oacute;n ! ... </strong> ").append(msg);
        }
        sb.append("</div>");
        return sb.toString();
    }

    public static String msgExito(String msg, boolean cerrable) {
        StringBuilder sb = new StringBuilder();
        if (cerrable == true) {
            sb.append("<div class=\"alert alert-success fade in alert-dismissable\" role=\"alert\">");
            sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>");
        } else {
            sb.append("<div class=\"alert alert-success\" role=\"alert\">");
        }
        sb.append(Constantes.ICON_CHECK);
        if (msg == null) {
            sb.append("&nbsp;<strong>&Eacute;xito ! ... </strong> " + Constantes.MENSAJE_EXITO + ".");
        } else {
            sb.append("&nbsp;<strong>&Eacute;xito ! ... </strong> ").append(msg);
        }
        sb.append("</div>");
        return sb.toString();
    }

    public static String msgAlerta(String msg, boolean cerrable) {
        StringBuilder sb = new StringBuilder();
        if (cerrable == true) {
            sb.append("<div class=\"alert alert-warning fade in alert-dismissable\" role=\"alert\">");
            sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>");
        } else {
            sb.append("<div class=\"alert alert-warning\" role=\"alert\">");
        }
        sb.append(Constantes.ICON_WARNING);
        if (msg == null) {
            sb.append("&nbsp;<strong>Alerta ! ... </strong> " + Constantes.MENSAJE_ALERTA + ".");
        } else {
            sb.append("&nbsp;<strong>Alerta ! ... </strong> ").append(msg);
        }
        sb.append("</div>");
        return sb.toString();
    }

    /*public static String msgErrorHtml(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>").append(Constantes.RAZON_SOCIAL_GRUPO_PANA).append("</title>");
        sb.append("</head>");
        sb.append("<body style=\"font-family:Arial,sans-serif;font-size:12px;color:#333\">");
        sb.append("<div style=\"background-color:#F2DEDE;border:1px solid #EED3D7;color:#B94A48;margin-bottom:10px;padding-bottom:10px;padding-left:10px;padding-top:10px;position:relative\">");
        if (msg == null) {
            sb.append("<strong>Error ! ... </strong> " + Constantes.MENSAJE_ERROR + ".");
        } else {
            sb.append("<strong>Error ! ... </strong> ").append(msg);
        }
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }*/
    // =========================================================================
    // ZIPEADO DE ARCHIVOS
    // =========================================================================
    /**
     * Comprime un archivo ZIP
     *
     * @param fileOrigen archivo a comprimir con su path completo y su
     * extension. Ejm. PATH + demo.txt
     * @param fileZipeado archivo comprimido con su path completo y con
     * extension .zip Ejm. PATH + demo.zip
     * @param fileNameOrigen nombre del archivo sin path y con la misma
     * extension de fileOrigen. Ejm. demo.txt
     * @throws java.lang.Exception
     */
    public static void zipea(String fileOrigen, String fileZipeado, String fileNameOrigen) throws Exception {
        byte[] buffer = new byte[1024];
        FileInputStream in = null;
        ZipOutputStream zos = null;
        try {
            FileOutputStream fos = new FileOutputStream(fileZipeado);
            zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(fileNameOrigen);
            zos.putNextEntry(ze);
            in = new FileInputStream(fileOrigen);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
            try {
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Descomprime un archivo ZIP
     *
     * @param archivoZip archivo zip de entrada.
     * @param dirSalida directorio de salida
     * @throws java.lang.Exception
     */
    public static void unZip(String archivoZip, String dirSalida) throws Exception {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = null;
        try {
            File folder = new File(dirSalida);
            if (!folder.exists()) {
                folder.mkdir();
            }
            zis = new ZipInputStream(new FileInputStream(archivoZip));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(dirSalida + File.separator + fileName);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                try {
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    throw e;
                } finally {
                    fos.close();
                }
                ze = zis.getNextEntry();
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                if (zis != null) {
                    zis.closeEntry();
                    zis.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    // =========================================================================
    // FECHAS
    // =========================================================================
    /**
     * Conversion de String a Date.
     *
     * @param s De tipo String: "09/12/2014"
     * @param formato De tipo String: "dd/MM/yyyy" (MM debe ir en mayusculas)
     * @return De tipo Date: "09/12/2014"
     * @throws java.lang.Exception
     */
    public static java.sql.Date convertirAFecha(String s, String formato) throws Exception {
        java.sql.Date fecha = null;
        try {
            if (s != null) {
                Locale locale = new Locale("es", "ES");
                SimpleDateFormat sdf = new SimpleDateFormat(formato, locale);
                java.util.Date utilDate = sdf.parse(s);
                fecha = new java.sql.Date(utilDate.getTime());
            }
        } catch (Exception e) {
            throw e;
        }
        return fecha;
    }

    /**
     * Aplica formato a una fecha a un tipo espefificado.
     *
     * @param fecha De tipo Date.
     * @param inFormat Formato actual en el que se encuentra la fecha.
     * @param outFormat Formato de salida deseado.
     * @return De tipo String segun formato.
     */
    public static String formatearFecha(java.util.Date fecha, String inFormat, String outFormat) {
        Locale locale = new Locale("es", "ES");
        SimpleDateFormat sdf = new SimpleDateFormat(inFormat, locale);
        sdf.applyPattern(outFormat);
        return sdf.format(fecha);
    }

    /**
     * Aplica formato a un String con forma de fecha a un tipo espefificado.
     *
     * @param fecha De tipo String.
     * @param inFormat Formato actual en el que se encuentra el String con forma
     * de fecha.
     * @param outFormat Formato de salida deseado.
     * @return De tipo String segun formato.
     * @throws java.lang.Exception
     */
    public static String formatearFecha(String fecha, String inFormat, String outFormat) throws Exception {
        String nueva_fecha = "";
        try {
            if (fecha != null && fecha.trim().length() > 0) {
                nueva_fecha = "";
                Locale locale = new Locale("es", "ES");
                SimpleDateFormat sdf = new SimpleDateFormat(inFormat, locale);
                java.util.Date d = sdf.parse(fecha);
                sdf.applyPattern(outFormat);
                nueva_fecha = sdf.format(d);
            }
        } catch (Exception e) {
            throw e;
        }
        return nueva_fecha;
    }

    /**
     * Suma n dias a una fecha recibida.
     *
     * @param fecha De tipo Date.
     * @param dias De tipo int.
     * @return De tipo Date.
     */
    public static java.util.Date sumarDias(java.util.Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, dias);
        return cal.getTime();
    }

    public static String yyyymmdd(String STRFecha) throws Exception {
        return STRFecha.substring(6,10) + "-" + STRFecha.substring(3,5) + "-" + STRFecha.substring(0,2) ;
    }
    /**
     * Suma n dias a una fecha recibida.
     *
     * @param STRFecha De tipo String con formato "yyyy-MM-dd".
     * @param dias De tipo int.
     * @return De tipo Date.
     * @throws java.lang.Exception
     */
    public static java.util.Date sumarDias(String STRFecha, int dias) throws Exception {
        Locale locale = new Locale("es", "ES");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);

        java.util.Date fecha = null;
        try {
            fecha = df.parse(STRFecha);
        } catch (Exception ex) {
            throw ex;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, dias);
        return cal.getTime();
    }

    /**
     * Conversion de String a java.sql.Date.
     *
     * @param fecha De tipo String: "09/12/2014"
     * @param formato De tipo String: "dd/MM/yyyy"
     * @return De tipo java.sql.Date: "09/12/2014" Esta funcion ayuda cuando se
     * trabaja con prepareStatements (solo aceptan java.sql.Date)
     * @throws java.lang.Exception
     */
    public static java.sql.Date stringToSQLDate(String fecha, String formato) throws Exception {
        java.sql.Date sqlDate = null;
        if (fecha != null) {
            try {
                Locale locale = new Locale("es", "ES");
                SimpleDateFormat sdf = new SimpleDateFormat(formato, locale);

                java.util.Date utilDate = sdf.parse(fecha);
                sqlDate = new java.sql.Date(utilDate.getTime());
            } catch (Exception e) {
                throw e;
            }
        }
        return sqlDate;
    }

    /**
     * Recibe una fecha de tipo String en formato dd/MM/yyyy y la devuelve en el
     * mismo formato pero de tipo java.sql.Date; muy util cuando se usa en
     * preparedStatements.
     *
     * @param str Fecha en formato dd/MM/yyyy
     * @return Devuelve un java.sql.Date
     * @throws java.lang.Exception
     */
    public static java.sql.Date convertirAFecha(String str) throws Exception {
        java.sql.Date fecha = null;
        try {
            if ((str != null) && (str.trim().length() == 10)) {
                if (str.charAt(2) == '/' && str.charAt(5) == '/') {
                    String diaStr = str.substring(0, 2);
                    String mesStr = str.substring(3, 5);
                    String anioStr = str.substring(6, 10);
                    if ((diaStr.trim().length() == 2) && (mesStr.trim().length() == 2) && (anioStr.trim().length() == 4)) {
                        int dia = Integer.valueOf(diaStr);
                        int mes = Integer.valueOf(mesStr);
                        int anio = Integer.valueOf(anioStr);
                        if ((dia > 0) && (mes > 1 && mes <= 12) && (anio >= 1000)) {
                            Locale locale = new Locale("es", "ES");
                            SimpleDateFormat sdf_1 = new SimpleDateFormat("dd/MM/yyyy", locale);
                            SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd", locale);
                            java.util.Date d = sdf_1.parse(str);
                            sdf_1.applyPattern("yyyy-MM-dd");
                            String nueva_fecha = sdf_1.format(d);
                            java.util.Date utilDate = sdf_2.parse(nueva_fecha);
                            fecha = new java.sql.Date(utilDate.getTime());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return fecha;
    }

    /**
     * Transforma una fecha (string) a palabras.
     *
     * @param fecha en String, formato dd/MM/yyyy
     * @return
     * @throws Exception
     */
    public static String escribirFecha(String fecha) throws Exception {

        String fechaEnPalabras = "";

        if (fecha != null && fecha.trim().length() == 10) {
            String[] strFecha = fecha.split("/");
            String strDia = strFecha[0];
            String strMes = strFecha[1];
            String strAnio = strFecha[2];
            String strMesEnPalabra;

            switch (strMes) {
                case "01": {
                    strMesEnPalabra = "Enero";
                    break;
                }
                case "02": {
                    strMesEnPalabra = "Febrero";
                    break;
                }
                case "03": {
                    strMesEnPalabra = "Marzo";
                    break;
                }
                case "04": {
                    strMesEnPalabra = "Abril";
                    break;
                }
                case "05": {
                    strMesEnPalabra = "Mayo";
                    break;
                }
                case "06": {
                    strMesEnPalabra = "Junio";
                    break;
                }
                case "07": {
                    strMesEnPalabra = "Julio";
                    break;
                }
                case "08": {
                    strMesEnPalabra = "Agosto";
                    break;
                }
                case "09": {
                    strMesEnPalabra = "Septiembre";
                    break;
                }
                case "10": {
                    strMesEnPalabra = "Octubre";
                    break;
                }
                case "11": {
                    strMesEnPalabra = "Noviembre";
                    break;
                }
                case "12": {
                    strMesEnPalabra = "Diciembre";
                    break;
                }
                default: {
                    strMesEnPalabra = "";
                    break;
                }
            }

            fechaEnPalabras = strDia + " de " + strMesEnPalabra + " del " + strAnio;
        }

        return fechaEnPalabras;
    }

    /**
     * Devuelve el numero de dia en la semana.
     *
     * @param date De tipo Date.
     * @return De tipo int: Domingo=1, Lunes=2, Martes=3,Miercoles=4, Jueves=5,
     * Viernes=6, Sabado=7.
     */
    public static int numeroDiaSemana(java.util.Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Devuelve el nombre abreviado del dia de la semana.
     *
     * @param num De tipo int.
     * @return De tipo String: DOM, LUN, MAR, MIE, JUE, VIE, SAB.
     */
    public static String nombreDia(int num) {

        String strDia = "DOMLUNMARMIEJUEVIESAB";
        int ini = (3 * num) - 3;
        return strDia.substring(ini, ini + 3);
    }

    /**
     * Devuelve el nombre abreviado del mes.
     *
     * @param num De tipo int.
     * @return De tipo String: ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SET, OCT,
     * NOV, DIC.
     */
    public static String nombreMes(int num) {

        String strMes = "ENEFEBMARABRMAYJUNJULAGOSETOCTNOVDIC";
        int ini = (3 * num) - 3;
        return strMes.substring(ini, ini + 3);
    }

    /**
     * Devuelve el nombre del mes.
     *
     * @param numeroMes
     * @return
     */
    public static String nombreMes(String numeroMes) {
        String nombreMes = "";
        switch (numeroMes) {
            case "01":
                nombreMes = "Enero";
                break;
            case "02":
                nombreMes = "Febrero";
                break;
            case "03":
                nombreMes = "Marzo";
                break;
            case "04":
                nombreMes = "Abril";
                break;
            case "05":
                nombreMes = "Mayo";
                break;
            case "06":
                nombreMes = "Junio";
                break;
            case "07":
                nombreMes = "Julio";
                break;
            case "08":
                nombreMes = "Agosto";
                break;
            case "09":
                nombreMes = "Setiembre";
                break;
            case "10":
                nombreMes = "Octubre";
                break;
            case "11":
                nombreMes = "Noviembre";
                break;
            case "12":
                nombreMes = "Diciembre";
                break;
        }
        return nombreMes;
    }

    /**
     * Utilizarse cuando se hacen las cadenas de insert o update en en cliente
     * java y se las envia con un executeUpdate despues de haber creado el
     * createStatement; devuelve una cadena de forma: TO_DATE('miFecha',
     * 'dd/MM/yyyy','NLS_DATE_LANGUAGE = American'), cadena a utilizarse en la
     * sentencia insert/update a ejecutar. Situacion: Se tiene una fecha
     * creada/recogida en java de tipo 'dd/MM/yyyy' y se la desea insertar en un
     * campo de tipo Date en la Base de datos Oracle, a pesar que el Java si lo
     * convierte al formato correcto con un simpleDateFormat, el driver del
     * Oracle lo retorna al formato original de fecha y originando el error en
     * Oracle: Invalid Month o No es un mes valido. Entonces se encontro la
     * siguiente solucion. 1.- Convertir la fecha a String en formato
     * 'dd/MM/yyyy'. utilizando la funcion formatoFecha de esta clase
     * (Util.java). 2.- Convertir la fecha en String a formato Date de Oracle
     * tambien de tipo 'dd/MM/yyyy'. 3.- Especificar el NLS_DATE_LANGUAGE =
     * American. Como resultado se obtendra una fecha Oracle de tipo
     * 'dd/MM/yyyy'. Para fecha y hora: outOracleFormat = 'dd/MM/yyyy hh:mi:ss
     * AM' outFormat = 'dd/MM/yyyy hh:mm:ss a'
     *
     * @param fecha De tipo Date.
     * @param inFormat Formato actual en el que se encuentra la fecha. Ejemplo:
     * 'dd-MMM-yyyy'
     * @param outFormat Formato a convertir deseado. Ejemplo: 'dd/MM/yyyy'
     * @param outOracleFormat Formato a convertir deseado (oracle). Ejemplo:
     * 'dd/MM/yyyy'
     * @return De tipo String.
     */
    public static String oracleSQLfecha(java.util.Date fecha, String inFormat, String outFormat, String outOracleFormat) {
        String sql;
        if (fecha != null) {
            Locale locale = new Locale("es", "ES");
            SimpleDateFormat sdf = new SimpleDateFormat(inFormat, locale);
            sdf.applyPattern(outFormat);
            sql = "TO_DATE('" + sdf.format(fecha) + "', '" + outOracleFormat + "','NLS_DATE_LANGUAGE = American')";
        } else {
            sql = "NULL";
        }
        return sql;
    }

    /**
     * Obtiene el anio actual del sistema.
     *
     * @return
     */
    public static String obtenerAnioActual() {
        Calendar c = Calendar.getInstance();
        String anio = Integer.toString(c.get(Calendar.YEAR));
        return anio;
    }

    
    public static String fecha_dia() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }    
    /**
     * Obtiene la fecha actual del servidor
     *
     * @param formato dd/MM/yyyy o el formato que que quiera
     * @return
     */
    public static String obtenerFechaActual(String formato) {
        Locale locale = new Locale("es", "ES");
        DateFormat sdf = new SimpleDateFormat(formato, locale);
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }

    /**
     * Sumar meses a una fecha
     *
     * @param fecha fecha en formato dd/MM/yyyy Ejm. 09/12/1984
     * @param meses
     * @return
     * @throws Exception
     */
    public static String sumarMeses(String fecha, int meses) throws Exception {
        String nuevaFecha = "";
        try {
            Locale locale = new Locale("es", "ES");
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", locale);
            java.util.Date date = formatter.parse(fecha);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, meses);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
            sdf.applyPattern("dd/MM/yyyy");
            nuevaFecha = sdf.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
        return nuevaFecha;
    }

    // =========================================================================
    // VALIDACION DE DATOS NULOS Y OTROS
    // =========================================================================
    /**
     * Verifica si un Object tiene valor null, si es asi, devuelve "", ademas de
     * quitarle los espacios a ambos lados (izquierda y derecha).
     *
     * @param obj De tipo Object.
     * @return De tipo String.
     * @throws Exception
     */
    public static String nullCad(Object obj) throws Exception {
        String x = null;
        try {
            if (obj == null) {
                x = "";
            } else if (obj instanceof byte[]) {
                x = String.valueOf(obj).trim();
            } else {
                x = obj.toString().trim();
            }
        } catch (Exception e) {
            throw e;
        }
        return x;
    }

    /**
     * Verifica si un Object tiene valor null, si es asi, devuelve 0.
     *
     * @param obj De tipo Object
     * @return De tipo Integer
     * @throws Exception
     */
    public static Integer nullNum(Object obj) throws Exception {
        Integer x = null;
        try {
            if (obj != null) {
                if (obj instanceof java.lang.String) {
                    if ("".equals(nullCad(obj))) {
                        x = 0;
                    } else {
                        x = Integer.valueOf(obj.toString().trim());
                    }
                } else if (obj instanceof java.lang.Double) {
                    x = ((java.lang.Double) obj).intValue();
                } else if (obj instanceof java.lang.Integer) {
                    x = ((java.lang.Integer) obj);
                }
            } else {
                x = 0;
            }
        } catch (Exception e) {
            throw e;
        }
        return x;
    }

    /**
     * Verifica si un Object tiene valor null, si es asi, devuelve 0.
     *
     * @param obj De tipo Object
     * @return De tipo Double
     * @throws Exception
     */
    public static Double nullDou(Object obj) throws Exception {
        Double x = null;
        try {
            if (obj != null) {
                if (obj instanceof java.lang.String) {
                    if ("".equals(nullCad(obj))) {
                        x = 0D;
                    } else {
                        x = Double.valueOf(obj.toString().trim());
                    }
                } else if (obj instanceof java.lang.Double) {
                    x = ((java.lang.Double) obj);
                } else if (obj instanceof java.lang.Integer) {
                    x = Double.valueOf(obj.toString());
                }
            } else {
                x = 0D;
            }
        } catch (Exception e) {
            throw e;
        }
        return x;
    }

    /**
     * Verifica si un Object tiene valor null, si es asi, devuelve 0.
     *
     * @param obj De tipo Object
     * @return de tipo Long
     * @throws Exception
     */
    public static Long nullLon(Object obj) throws Exception {
        Long x = null;
        try {
            if (obj != null) {
                if (obj instanceof java.lang.String) {
                    if ("".equals(nullCad(obj))) {
                        x = 0L;
                    } else {
                        x = Long.valueOf(obj.toString().trim());
                    }
                } else if (obj instanceof java.lang.Double) {
                    x = (new Double(obj.toString())).longValue();
                } else if (obj instanceof java.lang.Integer) {
                    x = Long.valueOf(obj.toString());
                } else if (obj instanceof java.lang.Long) {
                    x = ((java.lang.Long) obj);
                }
            } else {
                x = 0L;
            }
        } catch (Exception e) {
            throw e;
        }
        return x;
    }

    /**
     * Verifica si un Object tiene valor null, si es asi, devuelve false.
     *
     * @param obj De tipo Object.
     * @return De tipo Boolean.
     * @throws Exception
     */
    public static Boolean nullBoo(Object obj) throws Exception {
        Boolean x = null;
        try {
            if (obj == null) {
                x = false;
            } else {
                x = Boolean.valueOf(obj.toString().trim());
            }
        } catch (Exception e) {
            throw e;
        }
        return x;
    }

    /**
     * Valida si un correo tiene un formato valido.
     *
     * @param str
     * @return
     */
    public static boolean esCorreoValido(String str) {
        //Pattern p = Pattern.compile("[-\\w\\.]+@\\w+\\.\\w+");
        Pattern p = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Valida si es un numero entero correcto.
     *
     * @param str
     * @return
     */
    public static boolean esNumeroEntero(String str) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Valida si es un formato de DNI correcto
     *
     * @param str
     * @return
     */
    public static boolean esDNI(String str) {
        Pattern p = Pattern.compile("\\d{8}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Valida si es un formato de RUC correcto
     *
     * @param str
     * @return
     */
    public static boolean esRUC(String str) {
        Pattern p = Pattern.compile("\\d{11}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    // =========================================================================
    // ENCRIPTACION
    // =========================================================================
    /**
     * Encripta una cadena con una llave secreta; Las cadenas encriptadas que
     * genera son siempre las mismas (SI es URL Friendly); Por ejemplo si deseo
     * encriptar la palabra "hola", la cadena devuelta es "wzCUSE2U8PI" y
     * siempre sera ella para dicha palabra, asi ejecute el metodo varias veces
     * siempre me dara la misma cadena encriptada.
     *
     * @param cadena
     * @return cadena encriptada.
     * @throws java.lang.Exception
     */
    public static String encriptar(String cadena) throws Exception {
        String secretKey = "G2R0U1P0O0P1A4N4A9S2A2"; //llave para encriptar datos
        String base64EncryptedString = "";
        String str = cadena == null ? "" : cadena.trim();
        if (str.length() > 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
                SecretKey key = new SecretKeySpec(keyBytes, "DESede");
                Cipher cipher = Cipher.getInstance("DESede");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] plainTextBytes = str.getBytes("utf-8");
                byte[] buf = cipher.doFinal(plainTextBytes);
                Base64 encoder = new Base64(true); // URL safe
                byte[] base64Bytes = encoder.encode(buf);
                base64EncryptedString = new String(base64Bytes);
            } catch (Exception ex) {
                throw ex;
            }
        }
        return base64EncryptedString.trim();
    }

    /**
     * Desencripta una cadena con una llave secreta.
     *
     * @param cadenaEncriptada
     * @return cadena desencriptada.
     * @throws java.lang.Exception
     */
    public static String desencriptar(String cadenaEncriptada) throws Exception {
        String secretKey = "G2R0U1P0O0P1A4N4A9S2A2"; //llave para encriptar datos
        String base64EncryptedString = "";
        String str = cadenaEncriptada == null ? "" : cadenaEncriptada.trim();
        if (str.length() > 0) {
            try {
                Base64 decoder = new Base64(true);
                byte[] message = decoder.decode(str.getBytes("utf-8"));
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
                SecretKey key = new SecretKeySpec(keyBytes, "DESede");
                Cipher decipher = Cipher.getInstance("DESede");
                decipher.init(Cipher.DECRYPT_MODE, key);
                byte[] plainText = decipher.doFinal(message);
                base64EncryptedString = new String(plainText, "UTF-8");
            } catch (Exception ex) {
                throw ex;
            }
        }
        return base64EncryptedString.trim();
    }

    // =========================================================================
    // OPERACIONES NUMERICAS
    // =========================================================================        
    /**
     * Devuelve un double redondeado a tantos decimales como se le indique.
     *
     * @param dato De tipo double.
     * @param n De tipo int.
     * @return De tipo double.
     */
    public static double redondear(double dato, int n) {
        double valor = dato;
        String val = valor + "";
        BigDecimal big = new BigDecimal(val);
        big = big.setScale(n, RoundingMode.HALF_UP);
        return big.doubleValue();
    }

    /**
     * Formatea un dato de tipo double.
     *
     * @param dato De tipo double. Por ejemplo: 1546787.98 ( 1,546,787.98 )
     * @return De tipo String con formato: "###,##0.00"
     */
    public static String formatearDoubleA(double dato) {
        Locale.setDefault(Locale.ENGLISH);
        DecimalFormat formato = new DecimalFormat("###,##0.00");
        return formato.format(dato);
    }

    public static String formatearDoubleAA(double dato) {
        Locale.setDefault(Locale.ENGLISH);
        DecimalFormat formato = new DecimalFormat("#####0.0000");
        return formato.format(dato);
    }    
    public static String formatearDouble3A(double dato) {
        Locale.setDefault(Locale.ENGLISH);
        DecimalFormat formato = new DecimalFormat("###,##0.000");
        return formato.format(dato);
    }    
    /**
     * Formatea un dato de tipo double.
     *
     * @param dato De tipo double.
     * @return De tipo String con formato: ""#####0.00"
     */
    public static String formatearDoubleB(double dato) {
        Locale.setDefault(Locale.ENGLISH);
        DecimalFormat formato = new DecimalFormat("#####0.00");
        return formato.format(dato);
    }

    /**
     * Recupera el valor double de una cadena formateado. Por ejemplo:
     * 1,546,787.98 ( 1546787.98 )
     *
     * @param dato
     * @return un double
     */
    public static double recuperarDouble(String dato) {
        Locale.setDefault(Locale.ENGLISH);
        Scanner scanner = new Scanner(dato);
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        } else {
            return 0;
        }
    }

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }    
    // =========================================================================
    // OPERACIONES CON CADENAS
    // =========================================================================
    /**
     * Reemplaza los saltos de linea \n por salto de linea html br
     *
     * @param dato Cadena
     * @return
     */
    public static String reemplazarSaltoLinea(String dato) {
        if ((dato != null) && (dato.trim().length() > 0)) {
            dato = dato.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
        }
        return dato;
    }

    /**
     * Separa con apostrofes cada elemento de una cadena que se encuentra
     * separada por comillas.
     *
     * @param s De tipo String: "PA,GR,GT"
     * @return De tipo String: "'PA','GR','GT'"
     * @throws java.lang.Exception
     */
    public static String separarCadena(String s) throws Exception {
        String str = "";
        try {
            if ((s != null) && (s.length() > 0)) {
                String[] strArr = s.split(",");
                for (String strA : strArr) {
                    str = "'" + strA.trim() + "'," + str;
                }
                if (str.length() >= 1) {
                    str = str.substring(0, str.length() - 1);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return str;
    }

    /**
     * Separa con apostrofes cada elemento de una lista de Strings.
     *
     * @param list De tipo Lista de Strings: [PA,GR,GT]
     * @return De tipo String: "'PA','GR','GT'"
     * @throws java.lang.Exception
     */
    public static String separarListaA(List<String> list) throws Exception {
        String strSQL = "";
        try {
            if ((list != null) && (!list.isEmpty())) {
                for (int i = 0; i < list.size(); i++) {
                    if ((list.get(i) != null) && (list.get(i).trim().length() > 0)) {
                        strSQL = "'" + list.get(i).trim() + "'," + strSQL;
                    }
                }
                if (strSQL.length() >= 1) {
                    strSQL = strSQL.substring(0, strSQL.length() - 1);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return strSQL;
    }

    /**
     * Separa con comas cada elemento de una lista de Strings.
     *
     * @param list De tipo Lista de Strings: [PA,GR,GT]
     * @return De tipo String: "PA,GR,GT"
     * @throws java.lang.Exception
     */
    public static String separarListaB(List<String> list) throws Exception {
        String strSQL = "";
        try {
            if ((list != null) && (!list.isEmpty())) {
                for (int i = 0; i < list.size(); i++) {
                    if ((list.get(i) != null) && (list.get(i).trim().length() > 0)) {
                        strSQL = list.get(i).trim() + "," + strSQL;
                    }
                }
                if (strSQL.length() >= 1) {
                    strSQL = strSQL.substring(0, strSQL.length() - 1);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return strSQL;
    }

    /**
     * Devuelve tantos espacios en blanco como se le indique.
     *
     * @param n De tipo long.
     * @return De tipo String.
     */
    public static String espaciosEnBlanco(long n) {
        String ls_cad = "";
        for (int i = 1; i <= n; i++) {
            ls_cad = ls_cad + " ";
        }
        return ls_cad;
    }

    /**
     * Remueve el ultimo caracter de una cadena.
     *
     * @param s cadena de entrada
     * @return cadena limpia
     * @throws Exception
     */
    public static String quitarUltimoCaracter(String s) throws Exception {
        String str = "";
        try {
            if ((s != null) && (s.trim().length() > 0)) {
                str = s.substring(0, s.length() - 1);
            } else {
                str = s;
            }
        } catch (Exception e) {
            throw e;
        }
        return str;
    }

    /**
     * Remueve multiples espacios en blanco entre palabras, al inicio y al final
     * dejando solo uno. Por ejemplo: " HOLA MUNDO ", devuelve "HOLA MUNDO"
     *
     * @param s cadena de entrada
     * @return cadena limpia
     * @throws Exception
     */
    public static String quitarEspaciosMultiples(String s) throws Exception {
        String str = "";
        try {
            if (s != null) {
                str = s.replaceAll("\\s+", " ").trim();
            } else {
                str = s;
            }
        } catch (Exception e) {
            throw e;
        }
        return str;
    }

    /**
     * Agrega ceros a la izquierda
     *
     * @param dato Ejm: 125
     * @param formato Ejm: "00000"
     * @return "00125"
     */
    public static String completarCerosIzq(long dato, String formato) {
        DecimalFormat format = new DecimalFormat(formato);
        String str = format.format(dato);
        return str;
    }

    /**
     * Repite una cadena el numero de veces que se le indique.
     *
     * @param cadena Texto a repetir.
     * @param veces Numero de veces a repetir.
     * @autor acabello
     * @return
     */
    public static String repetirCadena(String cadena, long veces) {
        String str = "";
        for (int i = 0; i < veces; i++) {
            str = str + cadena;
        }
        return str;
    }

    /**
     * Imprime caracter de salto de pagina el numero de veces que se le indique.
     *
     * @param veces Numero de veces a repetir.
     * @autor acabello
     * @return
     */
    public static String saltarLineas(long veces) {
        String str = "";
        for (int i = 0; i < veces; i++) {
            str = str + "\n";
        }
        return str;
    }

    /**
     * Corta la cadena al tamanio maximo especificado
     *
     * @param obj Cadena
     * @param tamanoMaximo Tamano maximo de la cadena, todo lo que este despues
     * sera obviado.
     * @return
     */
    public static String cortarCadena(Object obj, int tamanoMaximo) {
        String s;
        if (obj == null) {
            s = "";
        } else if (obj instanceof byte[]) {
            if (String.valueOf(obj).trim().length() > tamanoMaximo) {
                s = String.valueOf(obj).trim().substring(0, tamanoMaximo);
            } else {
                s = String.valueOf(obj).trim();
            }
        } else if (String.valueOf(obj).trim().length() > tamanoMaximo) {
            s = String.valueOf(obj).trim().substring(0, tamanoMaximo);
        } else {
            s = String.valueOf(obj).trim();
        }
        return s;
    }

    /**
     * *
     * Corta una cadena de acuerdo a las posiciones indicadas
     *
     * @param obj Cadena
     * @param posIni Posicion inicial (inicia en cero)
     * @param posFin Posicion final (inicia en cero) <br>
     * Util.cortaCadena("ABCDEFGHI", 0, 3) = ABCD
     * @return
     * @throws java.lang.Exception
     */
    public static String cortarCadena(Object obj, int posIni, int posFin) throws Exception {
        String s;
        try {
            if (obj == null) {
                s = "";
            } else {
                s = (obj instanceof byte[]) ? (String.valueOf(obj).trim()) : (obj.toString().trim());
                int posF = posFin + 1;
                int tamano = s.length();
                if (posIni < tamano && posIni < posF) {
                    if (posF > tamano) {
                        s = s.substring(posIni, tamano).trim();
                    } else {
                        s = s.substring(posIni, posF).trim();
                    }
                } else {
                    s = "";
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return s;
    }

    /**
     * Remueve los apostrofes de una cadena
     *
     * @param cadena De tipo String.
     * @return De tipo String.
     */
    public static String quitarApostrofos(String cadena) {
        if ((cadena != null) && (cadena.trim().length() > 0)) {
            cadena = cadena.replaceAll("'", "");
        }
        return cadena;
    }

    /**
     * Limpia los apostrofos de una cadena; esta funcion se utiliza cuando se
     * hacen CRUDs en el cliente java.
     *
     * @param dato De tipo String.
     * @return De tipo String.
     */
    public static String quitarApostrofosSQL(String dato) {
        if ((dato != null) && (dato.trim().length() > 0)) {
            dato = dato.replaceAll("'", "''");
        }
        return dato;
    }

    // =========================================================================
    // VARIOS
    // =========================================================================
    /**
     * Comprueba si un menu esta en la lista de accesos de la session del perfil
     * actual.
     *
     * @param request Objecto Request
     * @param opcionMenu El nombre del menu a comprobar si esta en la lista de
     * accesos
     * @return
     */
    public static boolean opcEnListaAccesos(HttpServletRequest request, String opcionMenu) {
        boolean result;
        try {
            HttpSession session = request.getSession();
            List<String> listOpcMnuConAcc = (List<String>) session.getAttribute("listOpcMnuConAcc");
            result = listOpcMnuConAcc.contains(opcionMenu);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * Obtiene la IP de la pc que se ha conectado
     *
     * @param request
     * @return
     */
    public static String obtenerIP(HttpServletRequest request) {
        String remoteAddr = "";
        try {
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }
        } catch (Exception e) {
            remoteAddr = "";
        }
        return remoteAddr;
    }

    /**
     * Convierte la traza de errores a String
     *
     * @param e
     * @return
     */
    public static String imprimirStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        String[] lines = writer.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Comprueba si la sesion esta activa y tiene la misma ID
     *
     * @param request Objecto Request
     * @return true = sesion activa, false = sesion expirada o inactiva
     */
    public static boolean sesionEstaActiva(HttpServletRequest request) {
        boolean sesionActiva = true;
        try {
            HttpSession session = request.getSession();
            String ID = (String) session.getAttribute("ID");
            if ((ID == null) || (!ID.equals(session.getId()))) {
                sesionActiva = false;
            }
        } catch (Exception e) {
            sesionActiva = false;
        }
        return sesionActiva;
    }

    /**
     * Comprueba si una variable de session es igual a 1 (uno). Esta funcion
     * normalmente es usada para ver si un usuario tiene acceso o no a una
     * opcion del sistema
     *
     * @param request Objecto Request
     * @param cadena Usualmente aqui va la opcion del sistema
     * @return
     */
    public static boolean tieneAcceso(HttpServletRequest request, String cadena) {
        boolean tieneAcceso;
        try {
            HttpSession session = request.getSession();
            tieneAcceso = Util.nullNum(session.getAttribute(cadena)) == 1;
        } catch (Exception e) {
            tieneAcceso = false;
        }
        return tieneAcceso;
    }

    /**
     * Remueve los atributos de la sesion
     *
     * @param request
     * @param atributos Lista de atributos separados por comas, puede ser uno o
     * mas.
     */
    public static void removerDeLaSession(HttpServletRequest request, String atributos) {
        String[] attrs = atributos.split(",");
        if (attrs != null && attrs.length > 0) {
            for (String attr : attrs) {
                request.getSession().setAttribute(attr, null);
                request.getSession().removeAttribute(attr);
            }
        }
    }

    /**
     * Activa o Desactiva controles para la vista (utiliza boolean)
     *
     * @param request
     * @param activar true=activo,false=inactivo.
     * @param controles controles separados por comas.
     */
    public static void activarControles(HttpServletRequest request, boolean activar, String controles) {
        String[] ctrls = controles.split(",");
        if (ctrls != null && ctrls.length > 0) {
            if (activar) {
                for (String ctrl : ctrls) {
                    request.setAttribute(ctrl, true);
                }
            } else {
                for (String ctrl : ctrls) {
                    request.setAttribute(ctrl, false);
                }
            }
        }
    }

    /**
     * Activa controles para la vista (Utiliza String
     *
     * @param request: Enviar el HttpServletRequest request.
     * @param controles: Sin son varios controles, enviarlos separados por
     * comas.
     */
    public static void activarControles(HttpServletRequest request, String controles) {
        String[] ctrls = controles.split(",");
        if (ctrls != null && ctrls.length > 0) {
            for (String ctrl : ctrls) {
                request.setAttribute(ctrl, "activo");
            }
        }
    }

    /**
     * Desactiva controles para la vista
     *
     * @param request: Enviar el HttpServletRequest request.
     * @param controles: Sin son varios controles, enviarlos separados por
     * comas.
     */
    public static void desactivarControles(HttpServletRequest request, String controles) {
        String[] ctrls = controles.split(",");
        if (ctrls != null && ctrls.length > 0) {
            for (String ctrl : ctrls) {
                request.setAttribute(ctrl, "inactivo");
            }
        }
    }

    /**
     * Obtiene la fecha actual del sistema
     *
     * @return
     */
    public static java.util.Date obtenerFechaActual() {
        Calendar c = Calendar.getInstance();
        java.util.Date fecha = c.getTime();
        return fecha;
    }

    /**
     * Obtiene el ultimo dia de una fecha enviada
     *
     * @param fecha
     * @return
     */
    public static java.util.Date obtenerUltimoDia(java.util.Date fecha) {
        Calendar c = Calendar.getInstance();
        if (fecha != null) {
            c.setTime(fecha);
        }
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        java.util.Date f = c.getTime();
        return f;
    }

    /**
     * Obtiene el primer dia de una fecha enviada
     *
     * @param fecha
     * @return
     */
    public static java.util.Date obtenerPrimerDia(java.util.Date fecha) {
        Calendar c = Calendar.getInstance();
        if (fecha != null) {
            c.setTime(fecha);
        }
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        java.util.Date f = c.getTime();
        return f;
    }

    /**
     *
     * @param cadena a extraer
     * @param cantidad de caracteres a extraer de derecha a izquierda
     * @return
     */
    public static String right(String value, int length) {
        // To get right characters from a string, change the begin index.
        return value.substring(value.length() - length);
    }

    /**
     *
     * @param numero
     * @param numeroDecimales
     * @return
     */
    public static Double formatearDecimales(Double numero) {
        return Math.round(numero * Math.pow(10, 2)) / Math.pow(10, 2);
    }

//    public static Object sp_ejecuta_SAP(String sp, Integer tipoParamOUT, Object[] parameters) throws Exception {
//        Connection cn = new ConectaHana().connection();
//        CallableStatement cs = null;
//        Object result = null;
//        try {
//            cs = cn.prepareCall(sp);
//            if (parameters == null || parameters.length == 0) {
//                if (tipoParamOUT != null) {
//                    cs.registerOutParameter(1, tipoParamOUT);
//                    cs.executeUpdate();
//                    result = cs.getObject(1);
//                } else {
//                    cs.executeUpdate();
//                }
//            } else {
//                int i = 0;
//                for (Object parameter : parameters) {
//                    cs.setObject(++i, parameter);
//                }
//                if (tipoParamOUT != null) {
//                    cs.registerOutParameter(parameters.length + 1, tipoParamOUT);
//                    cs.executeUpdate();
//                    result = cs.getObject(parameters.length + 1);
//                } else {
//                    cs.executeUpdate();
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("GP.ERROR: {}", e);
//            result = "" + e;
//        } finally {
//            close(cn);
//            close(cs);
//        }
//        return result;
//    }
    
    public static String ReadFileConfig(String indice,String ls_Path)
    {
        final String FILEPROPERTIES = ls_Path + "config/empresa.config";
        Properties defaults = new Properties();
        String enValor = null;
        try {
            File f = new File(FILEPROPERTIES);
            defaults.load(new FileInputStream(f));
            enValor = defaults.getProperty(indice);

        } catch (Exception e) {
            System.out.println("Grupo Pana: Error al leer el archivo de configuracion:" + e);
        } 
        return enValor;
    }    
  
    
    public String pathServidorDBF() throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String path = "";
        try {
            cs = cn.prepareCall("{?=call polibag.dbo.uf_path_servidorPDF()}");
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.execute();
            path = cs.getString(1);
        } catch (Exception e) {
            throw e;
        } finally {
            Util.close(cn);
            Util.close(cs);
            Util.close(rs);
        }
        return path;
    }    

    public Long secuencia() throws Exception {
        long secuencia = 0;
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        if (cn != null) {
            try {
                cs = cn.prepareCall("{call polibag.dbo.sp_secuencia()}");
                cs.execute();                
            } catch (Exception e) {
                 throw e;
            } finally {                
                Util.close(cs);
            }
            try {
                cs = cn.prepareCall("{?=call polibag.dbo.uf_secuencia()}");
                cs.registerOutParameter(1, Types.NUMERIC); 
                cs.execute();    
                 secuencia = cs.getLong(1);
            } catch (Exception e) {
                 throw e;
            } finally {
                Util.close(cn);
                Util.close(cs);
            }            
        }
        return secuencia;
    }      

     public String Ultimo_Asiento(
        String anho ,
        String mes ,
        String tipo_comprobante 
            
    ) throws Exception {
        LOGGER.info("<==== Inicio Metodo: AsientoVentas ====>");
        Connection cn = new ConectaSQL().connection();
        ResultSet rs = null;
        String secuencia = "";
        try {
            CallableStatement cs = cn.prepareCall("{call polibag.dbo.sp_ultima_secuencia(?,?,?,?)}");
            cs.setString(1, anho);
            cs.setString(2, mes);
            cs.setString(3, tipo_comprobante);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            secuencia = cs.getString(4);
        } catch (Exception e) {
            secuencia = e.toString();
            throw e;
        } finally {
            Util.close(cn);
            Util.close(rs);
        }
        LOGGER.info("<==== Fin Metodo: AsientoVentas ====>");
        return secuencia;
    }         
}
