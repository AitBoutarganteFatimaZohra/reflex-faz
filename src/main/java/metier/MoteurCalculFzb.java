package metier;

import dao.IAccesDonneesFzb;

/**
 * Implementation concrete de IMoteurCalculFzb.
 *
 * Cette classe est le point central de la demo de programmation dynamique :
 * - Elle implemente l'interface metier (IMoteurCalculFzb).
 * - Elle depend de l'interface DAO (IAccesDonneesFzb).
 * - La dependance est injectee dynamiquement via la methode brancherSourceFzb().
 *
 * IMPORTANT : Le nom de la methode d'injection est "brancherSourceFzb"
 * (au lieu de "setDao" dans le document), ce qui est unique a ce projet.
 */
public class MoteurCalculFzb implements IMoteurCalculFzb {

    // ==================== PARAMETRES PERSONNALISES ====================

    /** Dependance injectable (interface DAO) */
    private IAccesDonneesFzb sourceFzb;

    /** Coefficient multiplicateur personnalise (au lieu de 2) */
    private double coefMultiplicateurFzb = 2.5;

    /** Nombre d'executions effectuees */
    private int nbExecutionsFzb = 0;

    /** Dernier resultat calcule */
    private double dernierResultatFzb = 0.0;

    /** Seuil pour appliquer une prime de stabilite */
    private static final int SEUIL_PRIME_FZB = 3;

    /** Montant de la prime de stabilite */
    private static final double MONTANT_PRIME_FZB = 10.0;

    // ==================== CONSTRUCTEUR ====================

    /**
     * Constructeur par defaut explicite.
     * Obligatoire pour l'instanciation via reflexion.
     */
    public MoteurCalculFzb() {
        // Constructeur vide volontaire
    }

    // ==================== INJECTION DE DEPENDANCE ====================

    /**
     * Methode d'injection de la dependance (equivalent de setDao dans le document).
     * Appelee dynamiquement par le conteneur de chargement via reflexion.
     *
     * @param sourceFzb L'instance de IAccesDonneesFzb a injecter.
     */
    public void brancherSourceFzb(IAccesDonneesFzb sourceFzb) {
        this.sourceFzb = sourceFzb;
        System.out.println("[MoteurCalculFzb] Source branchee : " + sourceFzb.obtenirNomSourceFzb());
    }

    // ==================== METHODE METIER PRINCIPALE ====================

    @Override
    public double executerTraitementFzb() {
        // Validation : verifier que la dependance a bien ete injectee
        if (sourceFzb == null) {
            throw new IllegalStateException("La source de donnees n'a pas ete branchee !");
        }

        // Verification de la disponibilite de la source
        if (!sourceFzb.estDisponibleFzb()) {
            throw new IllegalStateException("La source de donnees n'est pas disponible !");
        }

        // Incrementer le compteur d'executions
        nbExecutionsFzb++;

        // Recuperation de la valeur depuis la source (via l'interface)
        double donneeFzb = sourceFzb.extraireDonneeFzb();

        // Logique metier : multiplication par le coefficient
        double resultatFzb = donneeFzb * coefMultiplicateurFzb;

        // Application d'une prime de stabilite apres N executions
        if (nbExecutionsFzb >= SEUIL_PRIME_FZB) {
            resultatFzb += MONTANT_PRIME_FZB;
            System.out.println("[MoteurCalculFzb] Prime de stabilite appliquee (+" + MONTANT_PRIME_FZB + ")");
        }

        // Sauvegarde du dernier resultat
        dernierResultatFzb = resultatFzb;

        // Traceabilite detaillee
        System.out.println("[MoteurCalculFzb] Execution n°" + nbExecutionsFzb
                + " : " + donneeFzb + " x " + coefMultiplicateurFzb
                + " = " + resultatFzb);

        return resultatFzb;
    }

    // ==================== AUTRES METHODES DE L'INTERFACE ====================

    @Override
    public String decrireMoteurFzb() {
        return "MoteurCalculFzb [coef=" + coefMultiplicateurFzb
                + ", executions=" + nbExecutionsFzb
                + ", dernierResultat=" + dernierResultatFzb + "]";
    }

    @Override
    public boolean sourceEstBrancheeFzb() {
        return sourceFzb != null;
    }

    // ==================== METHODES SUPPLEMENTAIRES ====================

    /**
     * Retourne le dernier resultat calcule.
     */
    public double obtenirDernierResultatFzb() {
        return dernierResultatFzb;
    }

    /**
     * Calcule une estimation de performance (moyenne des resultats).
     */
    public double estimerPerformanceFzb() {
        if (nbExecutionsFzb == 0) {
            return 0;
        }
        return dernierResultatFzb / nbExecutionsFzb;
    }

    /**
     * Modifie le coefficient multiplicateur (setter personnalise).
     */
    public void definirCoefMultiplicateurFzb(double coefMultiplicateurFzb) {
        if (coefMultiplicateurFzb <= 0) {
            throw new IllegalArgumentException("Le coefficient doit etre positif !");
        }
        this.coefMultiplicateurFzb = coefMultiplicateurFzb;
    }

    /**
     * Reinitialise le compteur d'executions.
     */
    public void reinitialiserCompteurFzb() {
        this.nbExecutionsFzb = 0;
        this.dernierResultatFzb = 0.0;
        System.out.println("[MoteurCalculFzb] Compteur reinitialise.");
    }
}