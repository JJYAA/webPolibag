package pe.com.gp.entity;

import java.io.Serializable;

public class DtoUsuario implements Serializable {

    static final long serialVersionUID = 1L;
    private String codigo;
    private String password;
    private String nombre;
    private String correo;
    private String sexo;
    private String cargo;
    private String apePaterno;
    private String apeMaterno;
    private String priNombre;
    private String segNombre;
    private String codigoTienda;
    private String nombreTienda;
    private String codigoTiendaLogin;
    private String nombreTiendaLogin;
    private String habilitado;
    private String centroCostos;
    private String privilegio1;
    private String privilegio2;
    private String privilegio3;
    private String tipoUsuario;
    private double descuento;
    private long codigoPerfil;
    private String nombrePerfil;

    public DtoUsuario() {
    }

    public long getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(long codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public String getApeMaterno() {
        return apeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    public String getPriNombre() {
        return priNombre;
    }

    public void setPriNombre(String priNombre) {
        this.priNombre = priNombre;
    }

    public String getSegNombre() {
        return segNombre;
    }

    public void setSegNombre(String segNombre) {
        this.segNombre = segNombre;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getCodigoTiendaLogin() {
        return codigoTiendaLogin;
    }

    public void setCodigoTiendaLogin(String codigoTiendaLogin) {
        this.codigoTiendaLogin = codigoTiendaLogin;
    }

    public String getNombreTiendaLogin() {
        return nombreTiendaLogin;
    }

    public void setNombreTiendaLogin(String nombreTiendaLogin) {
        this.nombreTiendaLogin = nombreTiendaLogin;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public String getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
    }

    public String getPrivilegio1() {
        return privilegio1;
    }

    public void setPrivilegio1(String privilegio1) {
        this.privilegio1 = privilegio1;
    }

    public String getPrivilegio2() {
        return privilegio2;
    }

    public void setPrivilegio2(String privilegio2) {
        this.privilegio2 = privilegio2;
    }

    public String getPrivilegio3() {
        return privilegio3;
    }

    public void setPrivilegio3(String privilegio3) {
        this.privilegio3 = privilegio3;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

}
