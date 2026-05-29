package laboratorio.galeriadeimagenesarraylist;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public static ArrayList<String> listaDeImagenes = new ArrayList<>();
    public static ArrayList<String> listaDeTitulos = new ArrayList<>();

    private ArrayList<String> imagenesMostradas = new ArrayList<>();
    private ArrayList<Double> mbMostrados = new ArrayList<>();
    private ArrayList<String> categoriaMostradas = new ArrayList<>();
    private ArrayList<Long> bytesMostrados = new ArrayList<>();
    private ArrayList<String> fechaMostradas = new ArrayList<>();

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
        if (!listaDeImagenes.isEmpty()) {
            return;
        }
        try {
            // 1. Intentar cargar desde el directorio físico
            File carpeta = new File("src/main/resources/imagenes");
            File[] archivos = carpeta.listFiles();

            if (archivos != null && archivos.length > 0) {
                int contador = 1;
                for (File archivo : archivos) {
                    String nombre = archivo.getName();
                    if (nombre.endsWith(".jpg") || nombre.endsWith(".png") || nombre.endsWith(".jpeg")) {
                        String ruta = "/imagenes/" + nombre;
                        listaDeImagenes.add(ruta);
                        listaDeTitulos.add("Imagen " + contador);
                        contador++;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrar6ImagenesAleatorias() {
        imagenesMostradas.clear();
        mbMostrados.clear();
        categoriaMostradas.clear();
        bytesMostrados.clear();
        fechaMostradas.clear();
        grdGaleria.getChildren().clear();

        int columna = 0;
        int fila = 0;

        String[] categories = {"Naturaleza", "Montañas", "Bosque", "Nocturno", "Lagos", "Paisaje"};
        int limite = Math.min(6, listaDeImagenes.size());

        //Cargamos las imágenes de los iconos una sola Vez fuera del bucle
        Image iconoEliminar = new Image(getClass().getResourceAsStream("/imagenes2/btnEliminar.png"));
        Image iconoEditar = new Image(getClass().getResourceAsStream("/imagenes2/btnEditar.jpg"));
        for (int indice = 0; indice < limite; indice++) {
            try {
                String ruta = listaDeImagenes.get(indice);
                String titulo = listaDeTitulos.get(indice);

                Image imagen;
                if (ruta.startsWith("http") || ruta.startsWith("https") || ruta.startsWith("file:")) {
                    imagen = new Image(ruta);
                } else {
                    imagen = new Image(getClass().getResourceAsStream(ruta));
                }

                ImageView imageView = new ImageView(imagen);
                imageView.setFitWidth(320);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(false);
                imageView.setStyle("-fx-cursor: hand;");

                double tamañoMB = 2.5 + (indice * 1.2);
                long tamañoBytes = (long) (tamañoMB * 1024 * 1024);
                String categoria = categories[indice % categories.length];
                String fecha = "0" + ((indice % 9) + 1) + "/05/2026";

                imagenesMostradas.add(ruta);
                mbMostrados.add(tamañoMB);
                categoriaMostradas.add(categoria);
                bytesMostrados.add(tamañoBytes);
                fechaMostradas.add(fecha);

                int indiceSeleccionado = indice;
                imageView.setOnMouseClicked(event -> abrirVisor(indiceSeleccionado));

                Label lblTitulo = new Label("Título: " + titulo);
                Label lblFecha = new Label("Fecha: " + fecha);
                Label lblCategoria = new Label("Categoría: " + categoria);
                Label lblMB = new Label(String.format("Tamaño: %.2f MB", tamañoMB));
                Label lblBytes = new Label("Bytes: " + tamañoBytes);

                //BOTÓN ELIMINAR
                ImageView imgEliminar = new ImageView(iconoEliminar);
                imgEliminar.setFitWidth(24);
                imgEliminar.setFitHeight(24);
                imgEliminar.setStyle("-fx-cursor: hand;");

                final int indiceEliminar = indice;
                imgEliminar.setOnMouseClicked(event -> {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Eliminar imagen");
                    alerta.setHeaderText("¿Eliminar imagen?");
                    alerta.setContentText("¿Seguro que deseas eliminar esta imagen?");
                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType eliminar = new ButtonType("ELIMINAR", ButtonBar.ButtonData.OK_DONE);
                    alerta.getButtonTypes().setAll(cancelar, eliminar);

                    Optional<ButtonType> resultado = alerta.showAndWait();
                    if (resultado.isPresent() && resultado.get() == eliminar) {
                        listaDeImagenes.remove(indiceEliminar);
                        listaDeTitulos.remove(indiceEliminar);
                        mostrar6ImagenesAleatorias();
                    }
                });

                //BOTÓN EDITAR
                ImageView imgEditar = new ImageView(iconoEditar);
                imgEditar.setFitWidth(24);
                imgEditar.setFitHeight(24);
                imgEditar.setStyle("-fx-cursor: hand;");

                final int indiceEditar = indice;
                imgEditar.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/laboratorio/galeriadeimagenesarraylist/agregarImagen.fxml"));
                        Parent root = loader.load();

                        AgregarController controller = loader.getController();

                        String rutaImagenActual = listaDeImagenes.get(indiceEditar);
                        String tituloActual = listaDeTitulos.get(indiceEditar);

                        controller.prepararParaModificar(rutaImagenActual, tituloActual, indiceEditar);

                        Stage stage = new Stage();
                        stage.setTitle("Modificar Datos de Imagen");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // Ensamblar Tarjeta
                StackPane contenedorImagen = new StackPane(imageView);
                VBox tarjeta = new VBox(8);
                tarjeta.setPadding(new Insets(10));
                tarjeta.setPrefSize(340, 360);
                tarjeta.getStyleClass().add("tarjeta");

                HBox contenedorBoton = new HBox(imgEditar, imgEliminar);
                contenedorBoton.setSpacing(10);
                contenedorBoton.setAlignment(Pos.CENTER_RIGHT);

                tarjeta.getChildren().addAll(contenedorImagen, lblTitulo, lblFecha, lblCategoria, lblMB, lblBytes, contenedorBoton);
                grdGaleria.add(tarjeta, columna, fila);

                columna++;
                if (columna == 3) {
                    columna = 0;
                    fila++;
                }

            } catch (Exception e) {
                System.err.println("Error al renderizar la tarjeta en el índice " + indice);
                e.printStackTrace();
            }
        }
    }

    private void abrirVisor(int indiceSeleccionado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("visor.fxml"));
            Parent root = loader.load();

            visorController controller = loader.getController();

            //Envía las listas exactamente en el orden requerido por visorController
            controller.iniciarDatos(
                    imagenesMostradas,
                    mbMostrados,
                    categoriaMostradas,
                    bytesMostrados,
                    fechaMostradas,
                    indiceSeleccionado
            );

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/laboratorio/galeriadeimagenesarraylist/agregarImagen.fxml"));
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
        if (indice >= 0 && indice < listaDeImagenes.size()) {
            listaDeImagenes.set(indice, nuevaRuta);
            mostrar6ImagenesAleatorias();
        }
    }
}
