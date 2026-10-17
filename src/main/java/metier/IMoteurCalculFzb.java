package metier;

/**
 * Contrat metier du microservice de calcul.
 *
 * Principe SRP (Single Responsibility Principle) :
 * - Cette interface ne gere QUE la logique metier (calculs, regles de gestion).
 * - Elle ne depend PAS de la couche DAO (abstraction via injection).
 * - Elle peut etre testee independamment avec un DAO factice (Mock).
 *
 * Note architecturale : dans un vrai microservice, cette couche serait
 * le coeur du service, sans dependance aux frameworks de presentation
 * (JAX-RS, Spring MVC) pour faciliter la reutilisation (batch, evenements).
 */
public interface IMoteurCalculFzb {

    /**
     * Execute le traitement metier principal.
     * @return Le resultat du calcul metier.
     */
    double executerTraitementFzb();

    /**
     * Retourne une description lisible du moteur de calcul.
     * @return La description du moteur.
     */
    String decrireMoteurFzb();

    /**
     * Verifie si la source de donnees est branchee.
     * @return true si la source est branchee, false sinon.
     */
    boolean sourceEstBrancheeFzb();
}