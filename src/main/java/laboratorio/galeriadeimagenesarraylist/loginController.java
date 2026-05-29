package laboratorio.galeriadeimagenesarraylist;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class loginController {

    @FXML
    private TextField txtLoginUsuario;

    @FXML
    private PasswordField pswLoginPassword;

    @FXML
    private Label lblError;

    @FXML
    protected void validarUsuario() {

        String usuario = txtLoginUsuario.getText().trim();
        String password = pswLoginPassword.getText().trim();

        // Valida si los están campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setVisible(true);
            lblError.setStyle("-fx-text-fill: red;");
            lblError.setText("Complete todos los campos");
            return;
        }
        //Verifica si los datos que digito el usuario son correctos
        boolean usuarioCorrecto = usuario.equalsIgnoreCase("admin");
        boolean passwordCorrecto = password.equals("campusjalpa");
        
        if (usuarioCorrecto && passwordCorrecto) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("galeria-view.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Galería de imágenes");
                stage.setMaximized(true);
                stage.show();

                // Cerrar login
                Stage loginStage = (Stage) txtLoginUsuario.getScene().getWindow();
                loginStage.close();

            } catch (Exception e) {
                System.err.println("Error: No se pudo cargar el archivo FXML de la galería.");
                e.printStackTrace();
            }
            return;
        }
        //Mostrar mensaje de error en rojo si el usuario o contraseña se puso de forma incorrecta
        lblError.setVisible(true);
        lblError.setStyle("-fx-text-fill: red;");
        lblError.setText("Usuario y/o contraseña incorrectos");

        // Limpiamos ambos campos para no dar pistas de cuál estaba bien
        txtLoginUsuario.clear();
        pswLoginPassword.clear();
        //Fija el puntero en la primera caja de texto
        txtLoginUsuario.requestFocus();
    }
}