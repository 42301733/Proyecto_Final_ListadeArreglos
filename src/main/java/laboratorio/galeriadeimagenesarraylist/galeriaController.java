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

    // ArrayList principal donde se almacenan los objetos Imagen
    public static ArrayList<Imagen> listaImagenes = new ArrayList<>();

    // ArrayList que guarda únicamente las imágenes mostradas actualmente
    private ArrayList<Imagen> imagenesMostradas = new ArrayList<>();

    // Instancia estática para poder actualizar la galería desde otras ventanas
    public static galeriaController instancia;

    @FXML
    public void initialize() {

        // Guardar referencia de la clase actual
        instancia = this;

        // Espaciado horizontal y vertical del GridPane
        grdGaleria.setHgap(20);
        grdGaleria.setVgap(20);

        // Cargar imágenes iniciales
        cargarImagenes();

        // Mostrar imágenes en pantalla
        mostrar6ImagenesAleatorias();

    }

    // Carga las imágenes desde la carpeta del proyecto
    private void cargarImagenes() {

        // Evita cargar nuevamente si ya existen imágenes
        if (!listaImagenes.isEmpty()) {
            return;
        }

        try {

            // Ruta de la carpeta donde están las imágenes
            File carpeta = new File("src/main/resources/laboratorio/galeriadeimagenesfx/imagenes");

            // Obtener todos los archivos de la carpeta
            File[] archivos = carpeta.listFiles();

            if (archivos != null) {

                int contador = 1;

                // Recorrer archivos encontrados
                for (File archivo : archivos) {

                    String nombre = archivo.getName();

                    // Validar formatos de imagen permitidos
                    if (nombre.endsWith(".jpg") || nombre.endsWith(".png") || nombre.endsWith(".jpeg")) {

                        // Crear ruta relativa
                        String ruta = "/laboratorio/galeriadeimagenesfx/imagenes/" + nombre;

                        // Crear objeto Imagen
                        Imagen img = new Imagen(ruta,"Imagen " + contador,"Naturaleza",java.time.LocalDate.now(),2,2000000);

                        // Agregar imagen al ArrayList
                        listaImagenes.add(img);

                        contador++;

                    }

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // Muestra las primeras 6 imágenes de la lista
    public void mostrar6ImagenesAleatorias() {

        // Limpiar lista temporal y GridPane
        imagenesMostradas.clear();

        grdGaleria.getChildren().clear();

        int columna = 0;
        int fila = 0;

        // Limitar a máximo 6 imágenes
        int limite = Math.min(6, listaImagenes.size());

        // Recorrer imágenes
        for (int indice = 0; indice < limite; indice++) {

            try {

                // Obtener objeto Imagen actual
                Imagen imgObj = listaImagenes.get(indice);

                // Guardar imagen en lista temporal
                imagenesMostradas.add(imgObj);

                // Obtener propiedades mediante getters
                String ruta = imgObj.getRuta();

                String titulo = imgObj.getTitulo();

                String categoria = imgObj.getCategoria();

                String fecha = imgObj.getFecha().toString();

                int tamañoMB = imgObj.getTamanoenMB();

                int tamañoBytes = imgObj.getTamanoenBytes();

                // Crear imagen
                Image imagen;

                // Validar si la ruta es externa o interna
                if (ruta.startsWith("http") || ruta.startsWith("https") || ruta.startsWith("file:")) {

                    imagen = new Image(ruta);

                } else {

                    imagen = new Image(getClass().getResourceAsStream(ruta));

                }

                // Configurar ImageView
                ImageView imageView = new ImageView(imagen);

                imageView.setFitWidth(320);

                imageView.setFitHeight(200);

                imageView.setPreserveRatio(false);

                imageView.setStyle("-fx-cursor: hand;");

                // Evento clic para abrir visor
                int indiceSeleccionado = indice;

                imageView.setOnMouseClicked(event -> abrirVisor(indiceSeleccionado));

                // Crear etiquetas de información
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

                // Evento eliminar imagen
                imgEliminar.setOnMouseClicked(event -> {

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

                    alerta.setTitle("Eliminar imagen");

                    alerta.setHeaderText("¿Eliminar imagen?");

                    alerta.setContentText("¿Seguro que deseas eliminar esta imagen?");

                    ButtonType cancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);

                    ButtonType eliminar = new ButtonType("ELIMINAR", ButtonBar.ButtonData.OK_DONE);

                    alerta.getButtonTypes().setAll(cancelar, eliminar);

                    Optional<ButtonType> resultado = alerta.showAndWait();

                    // Validar confirmación
                    if (resultado.isPresent() && resultado.get() == eliminar) {

                        // Eliminar objeto del ArrayList
                        listaImagenes.remove(indiceEliminar);

                        // Recargar galería
                        mostrar6ImagenesAleatorias();

                    }

                });

                // BOTÓN EDITAR
                ImageView imgEditar = new ImageView(new Image(getClass().getResourceAsStream("/images2/btnEditar.jpg")));

                imgEditar.setFitWidth(24);

                imgEditar.setFitHeight(24);

                imgEditar.setStyle("-fx-cursor: hand;");

                int indiceEditar = indice;

                // Evento editar imagen
                imgEditar.setOnMouseClicked(event -> {

                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/laboratorio/galeriadeimagenesfx/agregarImagen.fxml"));

                        Parent root = loader.load();

                        // Obtener controlador
                        AgregarController controller = loader.getController();

                        // Obtener imagen seleccionada
                        Imagen imgEditarObj = listaImagenes.get(indiceEditar);

                        // Enviar datos a la ventana editar
                        controller.prepararParaModificar(imgEditarObj.getRuta(),imgEditarObj.getTitulo(),indiceEditar);

                        // Configurar ventana
                        Stage stage = new Stage();

                        stage.setTitle("Modificar Datos de Imagen");

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.setScene(new Scene(root));

                        stage.showAndWait();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                });

                // Crear contenedor de imagen
                StackPane contenedorImagen = new StackPane(imageView);

                // Crear tarjeta principal
                VBox tarjeta = new VBox(8);

                tarjeta.setPadding(new Insets(10));

                tarjeta.setPrefSize(340, 360);

                tarjeta.getStyleClass().add("tarjeta");

                // Contenedor de botones
                HBox contenedorBoton = new HBox(imgEditar, imgEliminar);

                contenedorBoton.setSpacing(10);

                contenedorBoton.setAlignment(Pos.CENTER_RIGHT);

                // Agregar elementos a la tarjeta
                tarjeta.getChildren().addAll(contenedorImagen,lblTitulo,lblFecha,lblCategoria,lblMB,lblBytes,contenedorBoton);

                // Agregar tarjeta al GridPane
                grdGaleria.add(tarjeta, columna, fila);

                columna++;

                // Control de columnas y filas
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

    // Abre el visor de imágenes
    private void abrirVisor(int indiceSeleccionado) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/laboratorio/galeriadeimagenesfx/visor.fxml"));

            Parent root = loader.load();

            // Obtener controlador del visor
            visorController controller = loader.getController();

            // Enviar lista de imágenes y posición seleccionada
            controller.iniciarDatos(imagenesMostradas, indiceSeleccionado);

            // Crear ventana
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

    // Abre ventana para agregar nueva imagen
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

    // Actualiza la ruta de una imagen existente
    public void actualizarImagenEnLista(int indice, String nuevaRuta) {

        // Validar índice válido
        if (indice >= 0 && indice < listaImagenes.size()) {

            // Modificar ruta usando setter
            listaImagenes.get(indice).setRuta(nuevaRuta);

            // Actualizar galería
            mostrar6ImagenesAleatorias();

        }

    }

}
