/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio.galeriadeimagenesarraylist;

import java.time.LocalDate;

/**
 *
 * @author Jorge
 * @author Adria
 */
public class Imagen {

    // Atributos de la imagen
    private String ruta;
    private String titulo;
    private String categoria;
    private LocalDate fecha;
    private int tamanoenMB;
    private int tamanoenBytes;

    // Constructor con parámetros
    public Imagen(String ruta, String titulo, String categoria, LocalDate fecha, int tamanoenMB, int tamanoenBytes) {
        this.ruta = ruta;
        this.titulo = titulo;
        this.categoria = categoria;
        this.fecha = fecha;
        this.tamanoenMB = tamanoenMB;
        this.tamanoenBytes = tamanoenBytes;

    }

    // Constructor vacío
    public Imagen() {

    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tamanoenMB
     */
    public int getTamanoenMB() {
        return tamanoenMB;
    }

    /**
     * @param tamanoenMB the tamanoenMB to set
     */
    public void setTamanoenMB(int tamanoenMB) {
        this.tamanoenMB = tamanoenMB;
    }

    /**
     * @return the tamanoenBytes
     */
    public int getTamanoenBytes() {
        return tamanoenBytes;
    }

    /**
     * @param tamanoenBytes the tamanoenBytes to set
     */
    public void setTamanoenBytes(int tamanoenBytes) {
        this.tamanoenBytes = tamanoenBytes;
    }
}
