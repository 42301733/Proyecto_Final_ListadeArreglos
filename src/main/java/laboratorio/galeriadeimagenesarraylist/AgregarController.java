/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laboratorio.galeriadeimagenesarraylist;

/**
 *
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

    /**
     * Este método lo llama GaleriaController cuando presionas el lápiz verde.
     * Rellena los campos con los datos actuales para poder modificarlos.
     */
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
        if ((ruta.endsWith(".jpg")|| ruta.endsWith(".png")|| ruta.endsWith(".jpeg"))&& !ruta.startsWith("http")&& !ruta.startsWith("https")&& !ruta.startsWith("file:")&& !ruta.startsWith("/")) {

            ruta = "file:/" + ruta.replace("\\", "/");

        }

        // MODIFICAR
        if (esModificacion) {

            if (indiceAEditar >= 0&&indiceAEditar < GaleriaController.listaImagenes.size()) {

                Imagen img=GaleriaController.listaImagenes.get(indiceAEditar);

                img.setRuta(ruta);

                img.setTitulo(titulo);

                System.out.println("Se modifico: " + titulo);

            }

        } // AGREGAR NUEVA
        else {

            Imagen nuevaImagen = new Imagen(ruta,titulo,"Naturaleza",java.time.LocalDate.now(),2,2000000);

            GaleriaController.listaImagenes.add(0, nuevaImagen);

            // Limitar a 20 imágenes
            if (GaleriaController.listaImagenes.size() > 20) {

                GaleriaController.listaImagenes.remove(GaleriaController.listaImagenes.size() - 1);

            }

        }

        // Refrescar galería
        if (GaleriaController.instancia != null) {

            GaleriaController.instancia.mostrar6ImagenesAleatorias();

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
