package com.example.cradspedidos;

public class Campos {

private String codigo;
private String nombre;
private String marca;
private String peso;
private String precio;

public String getCodigo() {
        return codigo;
        }

public String getNombre() {
        return nombre;
        }

public String getMarca() {
        return marca;
        }

public String getPeso() {
        return peso;
        }

public String getPrecio() {
        return precio;
        }


public Campos(String codigo, String nombre, String marca, String peso, String precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.peso = peso;
        this.precio = precio;
        }


        }
