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

        // Validar campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {

            lblError.setVisible(true);

            lblError.setText("Complete todos los campos");

            return;

        }

        boolean usuarioCorrecto=usuario.equalsIgnoreCase("admin");

        boolean passwordCorrecto=password.equals("campusjalpa");

        // Login correcto
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
                Stage loginStage=(Stage) txtLoginUsuario.getScene().getWindow();

                loginStage.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

            return;

        }

        // Mostrar error
        lblError.setVisible(true);

        // Ambos incorrectos
        if (!usuarioCorrecto && !passwordCorrecto) {

            lblError.setText("Usuario y contraseña incorrectos");

            txtLoginUsuario.clear();

            pswLoginPassword.clear();

        }
        // Solo usuario incorrecto
        else if (!usuarioCorrecto) {

            lblError.setText("Usuario incorrecto");

            txtLoginUsuario.clear();

        }
        // Solo contraseña incorrecta
        else {

            lblError.setText("Contraseña incorrecta");

            pswLoginPassword.clear();

        }

        lblError.setStyle("-fx-text-fill:red;");

        txtLoginUsuario.requestFocus();

    }

}
