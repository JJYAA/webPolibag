package pe.com.gp.entity;

import java.io.Serializable;

public class Usuario implements Serializable {

    static final long serialVersionUID = 1L;
    private String docidentidad; // hace referencia a c_c_codigo
    private String password;
    private String fullname;
    private String email;
    private String sexo;
    private String cargo;
    private String apaterno;
    private String amaterno;
    private String pnombre;
    private String snombre;
    private String codTienda;
    private String nomTienda;
    private String codTieLog; // Codigo tienda login
    private String nomTieLog; // Nombre tienda login
    private String estado;
    private String centrocostos;
    private String privilegio1;
    private String privilegio2;
    private String privilegio3;
    private String gerencia;
    private String habilitado;
    private double dsctoTrabajador;
    private String codigo;
    private String nombre;
    private String tienda;
    private String nomprinter;
    private String tipocamb;
    private String fechadia;
    private String Pagina;
    private Double igv = 0.00;
    private long secuencia = 0;
    private String path_bmp;
    //private String url_apache;
    //private String path_rpte;
    //private String path_pdf;
    private String primerNombre;
    private String path; // path general
    private long secuencia_proforma;
    private String cod_emp_gen;
    private String vtaRs; // de cab_emp_gen (ceg_vta_rs)
    private String ruc_emp;
    private String RucTdp;
    private String empEmp; // usualmente GP
    private String DireccionTienda;
    private String codigoConcesionario; // CEG_COD_TDP
    private long codigoPerfil;
    private String nombrePerfil;
    private String codigoCargo;
    private String nombreAbreviado;
    private String esTiendaMotos;
    private boolean existe;
    private String canalVenta; // Canal de venta de repuestos
    private String tiendaSAP;
    private String codigoEmpleadoVenta;
    private String userID;

    public Usuario() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCodigoEmpleadoVenta() {
        return codigoEmpleadoVenta;
    }

    public void setCodigoEmpleadoVenta(String codigoEmpleadoVenta) {
        this.codigoEmpleadoVenta = codigoEmpleadoVenta;
    }

    public String getTiendaSAP() {
        return tiendaSAP;
    }

    public void setTiendaSAP(String tiendaSAP) {
        this.tiendaSAP = tiendaSAP;
    }

    public String getCanalVenta() {
        return canalVenta;
    }

    public void setCanalVenta(String canalVenta) {
        this.canalVenta = canalVenta;
    }

    public String getEsTiendaMotos() {
        return esTiendaMotos;
    }

    public void setEsTiendaMotos(String esTiendaMotos) {
        this.esTiendaMotos = esTiendaMotos;
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public String getNombreAbreviado() {
        return nombreAbreviado;
    }

    public void setNombreAbreviado(String nombreAbreviado) {
        this.nombreAbreviado = nombreAbreviado;
    }

    public String getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(String codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public String getCodigoConcesionario() {
        return codigoConcesionario;
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

    public void setCodigoConcesionario(String codigoConcesionario) {
        this.codigoConcesionario = codigoConcesionario;
    }

    public String getEmpEmp() {
        return empEmp;
    }

    public void setEmpEmp(String empEmp) {
        this.empEmp = empEmp;
    }

    public String getVtaRs() {
        return vtaRs;
    }

    public void setVtaRs(String vtaRs) {
        this.vtaRs = vtaRs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDocidentidad() {
        return docidentidad;
    }

    public void setDocidentidad(String docidentidad) {
        this.docidentidad = docidentidad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getPnombre() {
        return pnombre;
    }

    public void setPnombre(String pnombre) {
        this.pnombre = pnombre;
    }

    public String getSnombre() {
        return snombre;
    }

    public void setSnombre(String snombre) {
        this.snombre = snombre;
    }

    public String getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(String codTienda) {
        this.codTienda = codTienda;
    }

    public String getNomTienda() {
        return nomTienda;
    }

    public void setNomTienda(String nomTienda) {
        this.nomTienda = nomTienda;
    }

    public String getCodTieLog() {
        return codTieLog;
    }

    public void setCodTieLog(String codTieLog) {
        this.codTieLog = codTieLog;
    }

    public String getNomTieLog() {
        return nomTieLog;
    }

    public void setNomTieLog(String nomTieLog) {
        this.nomTieLog = nomTieLog;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCentrocostos() {
        return centrocostos;
    }

    public void setCentrocostos(String centrocostos) {
        this.centrocostos = centrocostos;
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

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    // Campos Unidos
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

    public String getNomprinter() {
        return nomprinter;
    }

    public void setNomprinter(String nomprinter) {
        this.nomprinter = nomprinter;
    }

    public String getTipocamb() {
        return tipocamb;
    }

    public void setTipocamb(String tipocamb) {
        this.tipocamb = tipocamb;
    }

    public String getFechadia() {
        return fechadia;
    }

    public void setFechadia(String fechadia) {
        this.fechadia = fechadia;
    }

    public String getPagina() {
        return Pagina;
    }

    public void setPagina(String Pagina) {
        this.Pagina = Pagina;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public long getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(long secuencia) {
        this.secuencia = secuencia;
    }

    public String getPath_bmp() {
        return path_bmp;
    }

    public void setPath_bmp(String path_bmp) {
        this.path_bmp = path_bmp;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public double getDsctoTrabajador() {
        return dsctoTrabajador;
    }

    public void setDsctoTrabajador(double dsctoTrabajador) {
        this.dsctoTrabajador = dsctoTrabajador;
    }

    public long getSecuencia_proforma() {
        return secuencia_proforma;
    }

    public void setSecuencia_proforma(long secuencia_proforma) {
        this.secuencia_proforma = secuencia_proforma;
    }

    public String getCod_emp_gen() {
        return cod_emp_gen;
    }

    public void setCod_emp_gen(String cod_emp_gen) {
        this.cod_emp_gen = cod_emp_gen;
    }

    public String getRuc_emp() {
        return ruc_emp;
    }

    public void setRuc_emp(String ruc_emp) {
        this.ruc_emp = ruc_emp;
    }

    public String getRucTdp() {
        return RucTdp;
    }

    public void setRucTdp(String RucTdp) {
        this.RucTdp = RucTdp;
    }

    public String getDireccionTienda() {
        return DireccionTienda;
    }

    public void setDireccionTienda(String DireccionTienda) {
        this.DireccionTienda = DireccionTienda;
    }

}
