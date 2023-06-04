package pe.com.gp.entity;

import java.io.Serializable;

public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombre;
    private String direccion;
    private String codigoDistrito;
    private String nombreDistrito;
    private String nombreDistritoInitCap;
    private String telefono;
    private String correo;
    private String brokerCodigo;
    private String brokerNombre;
    private long contaSerieFAD;
    private long contaSerieBAD;
    private String codGen;
    private String codTDP;
    private String rucTDP;
    private boolean existe;
    private String ventaRS;
    private String correoRep;
    private String correoSer;
    private String documento;
    private String nombreInt;
    private double mgnSolicitado;
    private String envioSunat;
    private String esTiendaMotos;
    private String esTiendaVehiculos;
    private String contaSerieGRS;

    public Tienda() {
    }

    public String getContaSerieGRS() {
        return contaSerieGRS;
    }

    public void setContaSerieGRS(String contaSerieGRS) {
        this.contaSerieGRS = contaSerieGRS;
    }

    public String getEsTiendaVehiculos() {
        return esTiendaVehiculos;
    }

    public void setEsTiendaVehiculos(String esTiendaVehiculos) {
        this.esTiendaVehiculos = esTiendaVehiculos;
    }

    public String getEsTiendaMotos() {
        return esTiendaMotos;
    }

    public void setEsTiendaMotos(String esTiendaMotos) {
        this.esTiendaMotos = esTiendaMotos;
    }

    public String getEnvioSunat() {
        return envioSunat;
    }

    public void setEnvioSunat(String envioSunat) {
        this.envioSunat = envioSunat;
    }

    public double getMgnSolicitado() {
        return mgnSolicitado;
    }

    public void setMgnSolicitado(double mgnSolicitado) {
        this.mgnSolicitado = mgnSolicitado;
    }

    public String getNombreInt() {
        return nombreInt;
    }

    public void setNombreInt(String nombreInt) {
        this.nombreInt = nombreInt;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreoRep() {
        return correoRep;
    }

    public void setCorreoRep(String correoRep) {
        this.correoRep = correoRep;
    }

    public String getCorreoSer() {
        return correoSer;
    }

    public void setCorreoSer(String correoSer) {
        this.correoSer = correoSer;
    }

    public String getVentaRS() {
        return ventaRS;
    }

    public void setVentaRS(String ventaRS) {
        this.ventaRS = ventaRS;
    }

    public String getRucTDP() {
        return rucTDP;
    }

    public void setRucTDP(String rucTDP) {
        this.rucTDP = rucTDP;
    }

    public long getContaSerieFAD() {
        return contaSerieFAD;
    }

    public void setContaSerieFAD(long contaSerieFAD) {
        this.contaSerieFAD = contaSerieFAD;
    }

    public long getContaSerieBAD() {
        return contaSerieBAD;
    }

    public void setContaSerieBAD(long contaSerieBAD) {
        this.contaSerieBAD = contaSerieBAD;
    }

    public String getCodGen() {
        return codGen;
    }

    public void setCodGen(String codGen) {
        this.codGen = codGen;
    }

    public String getCodTDP() {
        return codTDP;
    }

    public void setCodTDP(String codTDP) {
        this.codTDP = codTDP;
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public String getNombreDistritoInitCap() {
        return nombreDistritoInitCap;
    }

    public void setNombreDistritoInitCap(String nombreDistritoInitCap) {
        this.nombreDistritoInitCap = nombreDistritoInitCap;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getBrokerCodigo() {
        return brokerCodigo;
    }

    public void setBrokerCodigo(String brokerCodigo) {
        this.brokerCodigo = brokerCodigo;
    }

    public String getBrokerNombre() {
        return brokerNombre;
    }

    public void setBrokerNombre(String brokerNombre) {
        this.brokerNombre = brokerNombre;
    }

}
