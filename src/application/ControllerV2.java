package application;

import application.SpecifModeleMorpions.Etat;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class ControllerV2 {

	private ModeleMorpionsFx modele;

	@FXML
	private GridPane grille;

	@FXML
	private Label labelNbCoups;

	@FXML
	private Label labelEtatJeu;
    
	@FXML
    private Label labelJoueur;
    
    @FXML
	private void initialize()
	{
	   
     modele =new ModeleMorpionsFx();

     recalculerLabelEtatJeu();

     for (Node n : grille.getChildren())
     {
    	n.setOnMouseClicked(e->this.clicBouton(e));
      }

     modele.nbCoupsProperty().addListener((obsValue,oldValue,newValue)->
     majNbCoups(newValue.intValue()));
     
     labelJoueur.textProperty()
     .bind(modele.symboleJoueurCourantProperty());
     
     for (Node enfant : grille.getChildren())
     {
    	 
     // enfants de "plateau": des Labels, mais pas que...
     if (enfant instanceof Label)
     {
     Label l = (Label) enfant;
     int ligne = (int) l.getProperties().get("gridpane­row")+1 ;
     int colonne = (int) l.getProperties().get("gridpane­column")+1 ;
     
     modele.casePlateauProperty(ligne, colonne).addListener((obs,oldV,newV)
    		 ->{
    			 l.setText(modele.symboleJoueur(newV.intValue()));
    		 });
     
      }
	}
	} 
    
    private void majNbCoups(int nb)
   { if (nb == 0)
	   {
	   labelNbCoups.setText("aucun coup joué");
	   }
	   else
	   {
	   String ch ;
	   if (nb == 1) ch = " coup joué" ;
	   else ch = " coups joués" ;
	   labelNbCoups.setText(Integer.toString(nb) + ch);
	   }
   }
    private void clicBouton(MouseEvent event)
   {
	   Node n = (Node) event.getSource() ;
   Integer ligne = ((Integer) n.getProperties().get("gridpane­row"))+1;
   Integer colonne = ((Integer) n.getProperties().get("gridpane­column")) +1;
	   modele.jouerCoup(ligne, colonne) ;
	   System.out.println("Coup joué : " + ligne + "/" + colonne) ;

	   recalculerLabelEtatJeu();

	   System.out.println("résultat: " + modele.getEtatJeu()) ;
   }

    private void recalculerLabelEtatJeu()
  {
   Etat etat=modele.getEtatJeu();

	switch (etat) {

	case J1_JOUE:
		labelEtatJeu.setText(" c'est le tour de joueur1");

		break;

	case J2_JOUE:
		labelEtatJeu.setText(" c'est le tour de joueur2");

		break;

	case MATCH_NUL:
		labelEtatJeu.setText(" personne n'a gagne  ");

		break;

	case J2_VAINQUEUR:
		labelEtatJeu.setText(" le joueur 2 a gagne ");

		break;

	case J1_VAINQUEUR:
		labelEtatJeu.setText(" le joueur 1 a gagne ");

		break;

	default:
		break;
	}

	  }


}
