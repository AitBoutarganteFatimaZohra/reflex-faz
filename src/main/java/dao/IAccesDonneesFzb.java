package dao;

public interface IAccesDonneesFzb {

    /**
     * Extrait une donnee numerique depuis la source.
     * @return La valeur double extraite.
     */
    double extraireDonneeFzb();

    /**
     * Verifie si la source est disponible.
     * @return true si la source repond, false sinon.
     */
    boolean estDisponibleFzb();

    /**
     * Retourne le nom technique de la source de donnees.
     * @return Le nom de la source.
     */
    String obtenirNomSourceFzb();
}