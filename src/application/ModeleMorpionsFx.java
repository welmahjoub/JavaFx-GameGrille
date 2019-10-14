package application;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.StringProperty;

public class ModeleMorpionsFx  implements SpecifModeleMorpions{

	//private int[][] tab;
	
	private ReadOnlyIntegerWrapper[][] plateau;
	
	public ReadOnlyIntegerProperty casePlateauProperty (int ligne, int colonne)
	{
		return plateau[ligne-1][colonne-1].getReadOnlyProperty() ;
	}
	
	private int getVal(int ligne, int colonne)
	{
		return plateau[ligne-1][colonne-1].getValue() ;
	}
	
	private void setVal(int ligne, int colonne, int val)
	{
		plateau[ligne-1][colonne-1].setValue(val);
	}
	
	private int joueurCourant;

	private ReadOnlyIntegerWrapper nbrCoups;

	// Symbole désignant le joueur courant
	private StringProperty symboleJoueurCourant ;
	
	public StringProperty symboleJoueurCourantProperty()
	{
		return this.symboleJoueurCourant;
	}
	
    public ReadOnlyIntegerWrapper[][] getPlateau() {
    	
		return plateau;
	}

	public void setPlateau(ReadOnlyIntegerWrapper[][] plateau) {
		this.plateau = plateau;
	}

	public String symboleJoueur(int val)
    {
	    switch (val)
	    {
	    case 1 : return "x" ;
	    case 2 : return "o" ;
	    default : return " " ;
	    }
    }
    
	// Accesseurs "Java Bean" sur la valeur encapsulée
	public String getSymboleJoueurCourant()
	{
	return symboleJoueurCourant.getValue();
	}
	
	private void setSymboleJoueurCourant(String ch)
	{
		symboleJoueurCourant.setValue(ch);
	}
	
	public ReadOnlyIntegerProperty nbCoupsProperty()
	{ return nbrCoups.getReadOnlyProperty() ; }

	public ModeleMorpionsFx()
	{
		plateau=new  ReadOnlyIntegerWrapper[TAILLE][TAILLE];
		
		joueurCourant=1;

		nbrCoups.set(0);

		for (int i = 0; i < TAILLE; i++) {

			for (int j = 0; j < TAILLE; j++) {
				plateau[i][j].set(0);
			}
		}
	}

	private int score()
	{

		// calcul des produit des lignes

		int tabScoreLigne[]=new int[TAILLE];

		for (int i = 0; i < TAILLE; i++) {

			tabScoreLigne[i]=1;

			for (int j = 0; j < TAILLE; j++) {

				tabScoreLigne[i]*=plateau[i][j].intValue();
			}

		}

		// calcul du produit des colone

		int tabScoreColone[]=new int[TAILLE];

		for (int i = 0; i < TAILLE; i++) {

			tabScoreColone[i]=1;

			for (int j = 0; j < TAILLE; j++) {

				tabScoreColone[i]*=plateau[j][i].intValue();
			}
		}

		// calcul du produit du digonal
		int diagonal=1;

		for (int i = 0; i < TAILLE; i++) {

			for (int j = 0; j < TAILLE; j++) {

				if(i==j)
				{
					diagonal*=plateau[i][j].intValue();
				}

			}
		}


		// parcour tableau des score des lignes et colones

		for (int i = 0; i < TAILLE; i++) {

			if(tabScoreLigne[i]==1 || tabScoreColone[i]==1)
				return 1;

			if(tabScoreLigne[i]==8 || tabScoreColone[i]==8)
				return 2;
		}

		// tester le valeur du diagonale

		if(diagonal==1)
			return 1;

		if(diagonal==8)
			return 2;

		return 0;
	}

	@Override
	public Etat getEtatJeu() {

		//int s=score();

		if (nbrCoups.getValue()<3) {

			if(joueurCourant==1)
				return Etat.J1_JOUE;
			if(joueurCourant==2)
				return Etat.J2_JOUE;

		}
		else{

		int s=score();


		if(nbrCoups.getValue()==9 && s==0)
			return Etat.MATCH_NUL;

		if(s==1)
			return Etat.J1_VAINQUEUR;
		if(s==2)
			return Etat.J2_VAINQUEUR;

		if(joueurCourant==1)
			return Etat.J1_JOUE;
		if(joueurCourant==2)
		return Etat.J2_JOUE;


		}
		return null;



	}

	@Override
	public int getJoueur() {

		if(estFinie())
		return 0;

		return joueurCourant;
	}

	@Override
	public int getVainqueur() {

		Etat etat =getEtatJeu();
		if(etat==Etat.J1_VAINQUEUR)
			return 1;
		if(etat==Etat.J2_VAINQUEUR)
			return 2;

		return 0;
	}

	@Override
	public int getNombreCoups() {

		return nbrCoups.getValue();
	}

	@Override
	public boolean estFinie() {

		Etat etat =getEtatJeu();

		if(etat==Etat.J1_VAINQUEUR || etat==Etat.J2_VAINQUEUR || etat==Etat.MATCH_NUL)
			return true;

		return false;
	}

	@Override
	public boolean estCoupAutorise(int ligne, int colonne) {

		assert (ligne>=1 && ligne <=TAILLE) && (colonne>=1 && colonne <=TAILLE) : "Les lignes et colonnes"+
				                       "doivent etre comprises entre 1 et TAILLE";
		return plateau[ligne-1][colonne-1].intValue()==0;
	}

	@Override
	public void jouerCoup(int ligne, int col) {

		assert !estFinie() : " le jeu n est pas encore fini ";

		assert estCoupAutorise(ligne, col) : "case non autorise ";

		int s= score();

		assert s==0  : "On ne doit pas pouvoir jouer";

		plateau[ligne-1][col-1].setValue(joueurCourant);

		nbrCoups.set(nbrCoups.intValue()+1);

		//permet de changer le courant
		joueurCourant = (joueurCourant%2) + 1;

		/*if(joueurCourant==1) {
			joueurCourant=2;
		}
		else {
			joueurCourant=2;
		}*/

	}


}
