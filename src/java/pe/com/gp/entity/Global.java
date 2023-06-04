package pe.com.gp.entity;

import java.io.Serializable;

public class Global implements Serializable {

    private static final long serialVersionUID = 1L;
    private double TCVta;
    private double TCCpra;
    private double IGV;
    private double ISC;
    private String Version;
    private String FechaSis; // Formato: dd/mm/yyyy
    private String FechaSisMasUno;
    private String hora12;
    private String hora24;
    private String nombreEmailEnvAut;
    private String emailEnvAut;
    private String claveEmailEnvAut;
    private double tipCamComGP;
    private double tipCamVtaGP;
    private String smsUsuario;
    private String smsClave;
    private String fechaSisLiteral;
    private double tipoCambioDolar;
    private double tipoCambioEuro;
    private String fechaActualFormato1; // dd/mm/yyyy
    private String fechaActualFormato2; // yyyymmdd
    private String fechaActualFormato3; // yyyy-mm-dd
    private boolean existe;
    private String pathServidor;
    public Global() {
    }

    public String getPathServidor() {
        return pathServidor;
    }

    public void setPathServidor(String pathServidor) {
        this.pathServidor = pathServidor;
    }

    
    public String getFechaActualFormato3() {
        return fechaActualFormato3;
    }

    public void setFechaActualFormato3(String fechaActualFormato3) {
        this.fechaActualFormato3 = fechaActualFormato3;
    }

    public double getTipoCambioDolar() {
        return tipoCambioDolar;
    }

    public void setTipoCambioDolar(double tipoCambioDolar) {
        this.tipoCambioDolar = tipoCambioDolar;
    }

    public double getTipoCambioEuro() {
        return tipoCambioEuro;
    }

    public void setTipoCambioEuro(double tipoCambioEuro) {
        this.tipoCambioEuro = tipoCambioEuro;
    }

    public String getFechaActualFormato1() {
        return fechaActualFormato1;
    }

    public void setFechaActualFormato1(String fechaActualFormato1) {
        this.fechaActualFormato1 = fechaActualFormato1;
    }

    public String getFechaActualFormato2() {
        return fechaActualFormato2;
    }

    public void setFechaActualFormato2(String fechaActualFormato2) {
        this.fechaActualFormato2 = fechaActualFormato2;
    }

    public String getFechaSisLiteral() {
        return fechaSisLiteral;
    }

    public void setFechaSisLiteral(String fechaSisLiteral) {
        this.fechaSisLiteral = fechaSisLiteral;
    }

    public String getSmsUsuario() {
        return smsUsuario;
    }

    public void setSmsUsuario(String smsUsuario) {
        this.smsUsuario = smsUsuario;
    }

    public String getSmsClave() {
        return smsClave;
    }

    public void setSmsClave(String smsClave) {
        this.smsClave = smsClave;
    }

    public double getTipCamComGP() {
        return tipCamComGP;
    }

    public void setTipCamComGP(double tipCamComGP) {
        this.tipCamComGP = tipCamComGP;
    }

    public double getTipCamVtaGP() {
        return tipCamVtaGP;
    }

    public void setTipCamVtaGP(double tipCamVtaGP) {
        this.tipCamVtaGP = tipCamVtaGP;
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public String getNombreEmailEnvAut() {
        return nombreEmailEnvAut;
    }

    public void setNombreEmailEnvAut(String nombreEmailEnvAut) {
        this.nombreEmailEnvAut = nombreEmailEnvAut;
    }

    public String getEmailEnvAut() {
        return emailEnvAut;
    }

    public void setEmailEnvAut(String emailEnvAut) {
        this.emailEnvAut = emailEnvAut;
    }

    public String getClaveEmailEnvAut() {
        return claveEmailEnvAut;
    }

    public void setClaveEmailEnvAut(String claveEmailEnvAut) {
        this.claveEmailEnvAut = claveEmailEnvAut;
    }

    public String getHora12() {
        return hora12;
    }

    public void setHora12(String hora12) {
        this.hora12 = hora12;
    }

    public String getHora24() {
        return hora24;
    }

    public void setHora24(String hora24) {
        this.hora24 = hora24;
    }

    public String getFechaSisMasUno() {
        return FechaSisMasUno;
    }

    public void setFechaSisMasUno(String FechaSisMasUno) {
        this.FechaSisMasUno = FechaSisMasUno;
    }

    public double getTCVta() {
        return TCVta;
    }

    public void setTCVta(double TCVta) {
        this.TCVta = TCVta;
    }

    public double getTCCpra() {
        return TCCpra;
    }

    public void setTCCpra(double TCCpra) {
        this.TCCpra = TCCpra;
    }

    public double getIGV() {
        return IGV;
    }

    public void setIGV(double IGV) {
        this.IGV = IGV;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public double getISC() {
        return ISC;
    }

    public void setISC(double ISC) {
        this.ISC = ISC;
    }

    public String getFechaSis() {
        return FechaSis;
    }

    public void setFechaSis(String FechaSis) {
        this.FechaSis = FechaSis;
    }
}
