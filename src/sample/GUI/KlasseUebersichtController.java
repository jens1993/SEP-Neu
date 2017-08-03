package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by jens on 03.08.2017.
 */
public class KlasseUebersichtController
{
    public void pressBtn_neueKlassehinzufuegen(ActionEvent event) throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("klasseHinzuGruppe.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Neue Klasse");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
