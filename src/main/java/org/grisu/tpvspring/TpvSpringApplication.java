package org.grisu.tpvspring;

import javafx.application.Application;
import org.grisu.tpvspring.presentacion.TpvPresentacion;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpvSpringApplication {

	public static void main(String[] args) {
		Application.launch(TpvPresentacion.class,args);
	}

}
