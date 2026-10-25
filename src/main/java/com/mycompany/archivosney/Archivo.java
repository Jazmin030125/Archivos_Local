package com.mycompany.archivosney;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.FetchType;

@Entity
@Table(name = "archivo")
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(length = 100)
    private String tipo;

    // LONGBLOB para soportar archivos grandes (BLOB solo aguanta 64 KB)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] contenido;

    public Archivo() {}

    public Archivo(String nombre, String tipo, byte[] contenido) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.contenido = contenido;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public byte[] getContenido() { return contenido; }
    public void setContenido(byte[] contenido) { this.contenido = contenido; }
}