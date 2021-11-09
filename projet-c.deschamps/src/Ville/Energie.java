package Ville;

public interface Energie {

    /**
     * Méthode abstraite de génération des tableaux de données
     * 
     * @param j Numéro du jour de l'année
     * @return le tableau des puissances consommées ou produites sur la journée à
     *         partir de la liste des différents points de production ou de
     *         consommation
     */

    public double[] generer(int j);

    /**
     * Méthode abstraite d'intégration qui est implémentée dans Production et dans
     * Consommation
     * 
     * @param duree   durée d'intégration
     * @param donnees tableau des puissances consommées ou produites sur la journée
     * @return l'énergie consommée sur la durée duree
     */
    public double integrer(int duree, double[] donnees);
}