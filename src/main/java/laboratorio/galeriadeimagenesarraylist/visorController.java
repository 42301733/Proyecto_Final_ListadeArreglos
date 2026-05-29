/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio.galeriadeimagenesarraylist;

/**
 * @author Jorge
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
//Declaración de campos
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

    //Declaramos las listas individuales para almacenar los datos dinámicos
    private ArrayList<String> listaImagenes;
    private ArrayList<Double> listaMB;
    private ArrayList<String> listaCategoria;
    private ArrayList<Long> listaBytes;
    private ArrayList<String> listaFecha;

    // Posición actual
    private int indiceActual;

    // Recibe datos desde GaleriaController
    public void iniciarDatos(ArrayList<String> rutas, ArrayList<Double> mb, ArrayList<String> categoria, ArrayList<Long> bytes, ArrayList<String> fecha, int indice) {

        // Guardamos cada lista en su respectiva variable global
        this.listaImagenes = rutas;
        this.listaMB = mb;
        this.listaCategoria = categoria;
        this.listaBytes = bytes;
        this.listaFecha = fecha;
        this.indiceActual = indice; 

        mostrarImagen();
    }

    // Mostrar imagen y datos
    private void mostrarImagen() {

        try {
            //Leemos directamente de la lista de Strings (rutas)
            String ruta = listaImagenes.get(indiceActual);

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

            //Extraemos los datos de las listas por índice
            lblTitulo.setText("Paisaje " + (indiceActual + 1));
            lblMB.setText(listaMB.get(indiceActual) + " MB");
            lblCategoria.setText(listaCategoria.get(indiceActual));
            lblBytes.setText("Bytes: " + listaBytes.get(indiceActual));
            lblFecha.setText(listaFecha.get(indiceActual));

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
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}