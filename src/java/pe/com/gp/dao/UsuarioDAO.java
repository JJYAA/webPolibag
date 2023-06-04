package pe.com.gp.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;

public class UsuarioDAO {

    private String nombre = null;
    private String nomprinter = null;
    private String estado = null;
    private String primerNombre = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getNomprinter() {
        return nomprinter;
    }

    public void setNomprinter(String nomprinter) {
        this.nomprinter = nomprinter;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

}
