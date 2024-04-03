package com.example.crud_app.dto;

import java.util.List;

public class CombinacionDTO {
    private List<String> nombres;
    private double sumaPrecios;

    public CombinacionDTO(List<String> nombres, double sumaPrecios) {
        this.nombres = nombres;
        this.sumaPrecios = sumaPrecios;
    }

    public List<String> getNombres() {
        return nombres;
    }

    public void setNombres(List<String> nombres) {
        this.nombres = nombres;
    }

    public double getSumaPrecios() {
        return sumaPrecios;
    }

    public void setSumaPrecios(double sumaPrecios) {
        this.sumaPrecios = sumaPrecios;
    }
}
