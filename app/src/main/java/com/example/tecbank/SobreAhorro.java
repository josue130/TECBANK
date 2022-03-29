package com.example.tecbank;

/*
 * Esta clase se encarga de mapear todos los atributos
 * de un sobre de ahorro
 * */

public class SobreAhorro {
    private  int id;
    private  String namesobre;
    private  String cuenta;
    private int  monto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamesobre() {
        return namesobre;
    }

    public void setNamesobre(String namesobre) {
        this.namesobre = namesobre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
}
