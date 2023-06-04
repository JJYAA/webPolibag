package pe.com.gp.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import pe.com.gp.util.Util;

public class TESTEADOR {

    public static void main(String[] args) throws Exception {
        String f = "02/08/2019";
        String h = "14:33";
        Calendar cal = Calendar.getInstance();
        Locale locale1 = new Locale("es", "ES");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm", locale1);
        String strFechaFin = Util.nullCad(f + " " + h);
        java.util.Date fechaHoraFin = sdf1.parse(strFechaFin);
        java.util.Date fechaHoraActual = sdf1.parse(sdf1.format(cal.getTime()));
        if (fechaHoraFin.compareTo(fechaHoraActual) < 0) {
            System.out.println("La Fecha y Hora de Finalizaci\u00f3n del Servicio debe ser MAYOR a la Fecha y Hora actual");
        } else {
            System.out.println("OK");
        }
    }
}
