/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio.galeriadeimagenesarraylist;

/**
 *@author Jorge
 * @author Adria
 */

import java.util.ArrayList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;

public class visorController {

    @FXML
    private ImageView imagenes;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblMB;

    @FXML
    private Label lblCategoria;

    @FXML
    private Label lblBytes;

    @FXML
    private Label lblFecha;

    @FXML
    private Button btnCerrar;

    // Lista de objetos Imagen
    private ArrayList<Imagen> listaImagenes;

    // Posición actual
    private int indiceActual;

    // Recibe datos desde GaleriaController
    public void iniciarDatos(ArrayList<Imagen> imagenes,int posicion) {

        this.listaImagenes = imagenes;

        this.indiceActual = posicion;

        mostrarImagen();

    }

    // Mostrar imagen y datos
    private void mostrarImagen() {

        try {

            Imagen img = listaImagenes.get(indiceActual);

            String ruta = img.getRuta();

            Image imagen;

            // Imagen cargada desde explorador
            if (ruta.startsWith("file:")) {

                imagen = new Image(ruta);

            }
            // Imagen del proyecto
            else {

                imagen = new Image(getClass().getResourceAsStream(ruta));
            }

            imagenes.setImage(imagen);

            imagenes.setPreserveRatio(true);

            imagenes.setFitWidth(1200);

            imagenes.setFitHeight(700);

            // Mostrar datos
            lblTitulo.setText(img.getTitulo());

            lblMB.setText(img.getTamanoenMB() + " MB");

            lblCategoria.setText(img.getCategoria());

            lblBytes.setText("Bytes: " + img.getTamanoenBytes());

            lblFecha.setText(img.getFecha().toString());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // Imagen siguiente
    @FXML
    private void siguienteImagen() {

        indiceActual++;

        if (indiceActual >= listaImagenes.size()) {

            indiceActual = 0;

        }

        mostrarImagen();

    }

    // Imagen anterior
    @FXML
    private void anteriorImagen() {

        indiceActual--;

        if (indiceActual < 0) {

            indiceActual = listaImagenes.size() - 1;

        }

        mostrarImagen();

    }

    // Cerrar visor
    @FXML
    private void cerrarVisor() {

        Stage stage=(Stage) btnCerrar.getScene().getWindow();

        stage.close();

    }

}