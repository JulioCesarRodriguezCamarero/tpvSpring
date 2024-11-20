package org.grisu.tpvspring.presentacion;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grisu.tpvspring.TpvSpringApplication;
import org.grisu.tpvspring.controladores.Index;
import org.grisu.tpvspring.controladores.ventas.InicioVentas;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class TpvPresentacion extends Application {
    private Stage miStage;
    private ConfigurableApplicationContext contexto;

    @Override
    public void init() throws Exception {
        this.contexto = new SpringApplicationBuilder(TpvSpringApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Index.class.getResource("/templates/index.fxml"));

        loader.setControllerFactory(contexto::getBean);
        Scene scene = new Scene(loader.load());
        stage.setTitle("TPV");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void stop() throws Exception {
        contexto.close();
        Platform.exit();
    }
}
