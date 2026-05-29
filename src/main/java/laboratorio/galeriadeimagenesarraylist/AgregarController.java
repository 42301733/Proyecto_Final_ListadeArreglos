/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio.galeriadeimagenesarraylist;

/**
 * @author Jorge
 * @author adria
 */
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarController {

    @FXML
    private TextField txtRuta;

    @FXML
    private TextField txtTitulo;

    // Control de estado para edición
    private int indiceAEditar = -1;

    private boolean esModificacion = false;
    
    public void prepararParaModificar(String ruta, String titulo, int indice) {

        this.indiceAEditar = indice;

        this.esModificacion = true;

        if (txtRuta != null) {

            txtRuta.setText(ruta);

        }

        if (txtTitulo != null) {

            txtTitulo.setText(titulo);

        }

    }

    @FXML
    private void agregar() {

        String ruta = txtRuta.getText().trim();

        String titulo = txtTitulo.getText().trim();

        if (ruta.isEmpty()) {

            System.out.println("Ruta vacía");

            return;

        }

        if (titulo.isEmpty()) {

            titulo = "Sin título";

        }

        // Normalizar ruta externa
        if ((ruta.endsWith(".jpg") || ruta.endsWith(".png") || ruta.endsWith(".jpeg")) && !ruta.startsWith("http") && !ruta.startsWith("https") && !ruta.startsWith("file:") && !ruta.startsWith("/")) {

            ruta = "file:/" + ruta.replace("\\", "/");
        }

        // MODIFICAR
        if (esModificacion) {
            if (indiceAEditar >= 0 && indiceAEditar < galeriaController.listaDeImagenes.size()) {

                //Modificamos las listas de Strings por separado usando el índice
                galeriaController.listaDeImagenes.set(indiceAEditar, ruta);
                galeriaController.listaDeTitulos.set(indiceAEditar, titulo);

                System.out.println("Se modificó: " + titulo);
            }
        } else {
            //Agregamos las imagenes directamente a sus respectivas listas en la posición 0
            galeriaController.listaDeImagenes.add(0, ruta);
            galeriaController.listaDeTitulos.add(0, titulo);

            // Limitar a 20 imágenes
            if (galeriaController.listaDeImagenes.size() > 20) {
                galeriaController.listaDeImagenes.remove(galeriaController.listaDeImagenes.size() - 1);
                galeriaController.listaDeTitulos.remove(galeriaController.listaDeTitulos.size() - 1); // También removemos el título sobrante
            }
        }

        // Refrescar galería
        if (galeriaController.instancia != null) {

            galeriaController.instancia.mostrar6ImagenesAleatorias();

        }

        cerrar();

    }

    @FXML
    private void cancelar() {

        cerrar();

    }
    
    private void cerrar() {

        Stage stage = (Stage) txtRuta.getScene().getWindow();

        stage.close();

    }
}