package pe.com.gp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import oracle.jdbc.OracleTypes;
import pe.com.gp.connection.ConectaSQL;
import pe.com.gp.util.Util;

public class FechaDAO {



    /**
     * Obtiene la fecha actual (sysdate) de acuerdo al formato especificado
     *
     * @param tipo 1(yyyy-mm-dd), 2(dd/mm/yyyy), 4(yyyymmdd)
     * @return fecha actual
     * @throws Exception
     */
    public String obtenerFechaActual() throws Exception {
        Connection cn = new ConectaSQL().connection();
        CallableStatement cs = null;
        ResultSet rs = null;
        String fecha = "";
        try {
            cs = cn.prepareCall("{?=call uf_fecha_sistema()}");
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.execute();
            fecha = cs.getString(1);
        } catch (Exception e) {
            throw e;
        } finally {
            Util.close(cn);
            Util.close(cs);
            Util.close(rs);
        }
        return fecha;
    }


}
