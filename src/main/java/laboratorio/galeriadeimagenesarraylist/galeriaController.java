package laboratorio.galeriadeimagenesarraylist;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class galeriaController {

    @FXML
    private GridPane grdGaleria;

    @FXML
    private ImageView imgAgregar;

    public static ArrayList<Imagen> listaImagenes = new ArrayList<>();

    private ArrayList<Imagen> imagenesMostradas = new ArrayList<>();

    public static galeriaController instancia;

    @FXML
    public void initialize() {

        instancia = this;

        grdGaleria.setHgap(20);
        grdGaleria.setVgap(20);

        cargarImagenes();

        mostrar6ImagenesAleatorias();
    }

    private void cargarImagenes() {

    if (!listaImagenes.isEmpty()) {
        return;
    }

    File carpeta = new File("src/main/resources/imagenes");

    File[] archivos = carpeta.listFiles();

    if (archivos != null) {

        int contador = 1;

        for (File archivo : archivos) {

            String nombre = archivo.getName();

            if (nombre.endsWith(".jpg")
                    || nombre.endsWith(".png")
                    || nombre.endsWith(".jpeg")) {

                String ruta = "/imagenes/" + nombre;

                System.out.println("Ruta cargada: " + ruta);

                Imagen img = new Imagen(
                        ruta,
                        "Paisaje " + contador,
                        "Naturaleza",
                        java.time.LocalDate.now(),
                        2,
                        2000000
                );

                listaImagenes.add(img);

                contador++;
            }
        }
    }
}

    public void mostrar6ImagenesAleatorias() {

        imagenesMostradas.clear();

        grdGaleria.getChildren().clear();

        int columna = 0;
        int fila = 0;

        int limite = Math.min(6, listaImagenes.size());

        for (int indice = 0; indice < limite; indice++) {

            try {

                Imagen imgObj = listaImagenes.get(indice);

                imagenesMostradas.add(imgObj);

                String ruta = imgObj.getRuta();

                String titulo = imgObj.getTitulo();

                String categoria = imgObj.getCategoria();

                String fecha = imgObj.getFecha().toString();

                int tamañoMB = imgObj.getTamanoenMB();

                int tamañoBytes = imgObj.getTamanoenBytes();

                // CARGAR IMAGEN
                Image imagen = new Image(getClass().getResourceAsStream(ruta));

                // IMAGE VIEW
                ImageView imageView = new ImageView(imagen);

                imageView.setFitWidth(320);

                imageView.setFitHeight(200);

                imageView.setPreserveRatio(false);

                imageView.setStyle("-fx-cursor: hand;");

                int indiceSeleccionado = indice;

                imageView.setOnMouseClicked(event ->
                        abrirVisor(indiceSeleccionado)
                );

                // LABELS
                Label lblTitulo = new Label("Título: " + titulo);

                Label lblFecha = new Label("Fecha: " + fecha);

                Label lblCategoria = new Label("Categoría: " + categoria);

                Label lblMB = new Label("Tamaño: " + tamañoMB + " MB");

                Label lblBytes = new Label("Bytes: " + tamañoBytes);

                // BOTÓN ELIMINAR
                ImageView imgEliminar = new ImageView(new Image(getClass().getResourceAsStream("/images2/btnEliminar.png")));

                imgEliminar.setFitWidth(24);

                imgEliminar.setFitHeight(24);

                imgEliminar.setStyle("-fx-cursor: hand;");

                int indiceEliminar = indice;

                imgEliminar.setOnMouseClicked(event -> {

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

                    alerta.setTitle("Eliminar imagen");

                    alerta.setHeaderText("¿Eliminar imagen?");

                    alerta.setContentText("¿Seguro que deseas eliminar esta imagen?");

                    ButtonType cancelar = new ButtonType("CANCELAR",ButtonBar.ButtonData.CANCEL_CLOSE);

                    ButtonType eliminar = new ButtonType("ELIMINAR",ButtonBar.ButtonData.OK_DONE);

                    alerta.getButtonTypes().setAll(cancelar, eliminar);

                    Optional<ButtonType> resultado = alerta.showAndWait();

                    if (resultado.isPresent() && resultado.get() == eliminar) {

                        listaImagenes.remove(indiceEliminar);

                        mostrar6ImagenesAleatorias();

                    }

                });

                // BOTÓN EDITAR
                ImageView imgEditar = new ImageView(new Image(getClass().getResourceAsStream("/images2/btnEditar.jpg")));

                imgEditar.setFitWidth(24);

                imgEditar.setFitHeight(24);

                imgEditar.setStyle("-fx-cursor: hand;");

                // TARJETA
                StackPane contenedorImagen = new StackPane(imageView);

                VBox tarjeta = new VBox(8);

                tarjeta.setPadding(new Insets(10));

                tarjeta.setPrefSize(340, 360);

                tarjeta.getStyleClass().add("tarjeta");

                HBox contenedorBoton = new HBox(imgEditar, imgEliminar);

                contenedorBoton.setSpacing(10);

                contenedorBoton.setAlignment(Pos.CENTER_RIGHT);

                tarjeta.getChildren().addAll(
                        contenedorImagen,
                        lblTitulo,
                        lblFecha,
                        lblCategoria,
                        lblMB,
                        lblBytes,
                        contenedorBoton
                );

                grdGaleria.add(tarjeta, columna, fila);

                columna++;

                if (columna == 3) {

                    columna = 0;

                    fila++;

                }

            } catch (Exception e) {

                System.err.println(
                        "Error al renderizar la tarjeta en el índice " + indice
                );

                e.printStackTrace();

            }

        }

    }

    private void abrirVisor(int indiceSeleccionado) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/laboratorio/galeriadeimagenesfx/visor.fxml"));

            Parent root = loader.load();

            visorController controller = loader.getController();

            controller.iniciarDatos(imagenesMostradas, indiceSeleccionado);

            Stage stage = new Stage();

            stage.setTitle("Visor");

            stage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);

            scene.setFill(null);

            stage.setScene(scene);

            stage.setMaximized(true);

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    private void abrirVentanaAgregar() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/laboratorio/galeriadeimagenesfx/agregarImagen.fxml"));

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Agregar Nueva Imagen");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(new Scene(root));

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void actualizarImagenEnLista(int indice, String nuevaRuta) {

        if (indice >= 0 && indice < listaImagenes.size()) {

            listaImagenes.get(indice).setRuta(nuevaRuta);

            mostrar6ImagenesAleatorias();

        }
    }
}