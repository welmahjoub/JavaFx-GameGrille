package morpion.fx.v1;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ControllerV1 {

	private ModeleMorpionsFx modele;

	@FXML
	private GridPane grille;

   @FXML
	private void initialize()
	{
     modele =new ModeleMorpionsFx();

     for (Node n : grille.getChildren())
     {
    	n.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {

						clicBouton(event);
					}
		});
    }


	}

   private void clicBouton(MouseEvent event)
   {
	   Node n = (Node) event.getSource() ;
   Integer ligne = ((Integer) n.getProperties().get("gridpane­row"))+1;
   Integer colonne = ((Integer) n.getProperties().get("gridpane­column")) +1;
	   modele.jouerCoup(ligne, colonne) ;
	   System.out.println("Coup joué : " + ligne + "/" + colonne) ;
	   System.out.println("résultat: " + modele.getEtatJeu()) ;
   }



}
