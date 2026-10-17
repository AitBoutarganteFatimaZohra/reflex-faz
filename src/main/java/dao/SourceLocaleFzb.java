package dao;

public class SourceLocaleFzb implements IAccesDonneesFzb {

    // Parametre personnalise : la valeur sera differente pour chaque instance
    private double facteurLocalFzb = 3.7;
    private int compteurAccesFzb = 0;

    public SourceLocaleFzb() {
        // Constructeur par defaut explicite (obligatoire pour la reflexion)
    }

    @Override
    public double extraireDonneeFzb() {
        compteurAccesFzb++;
        // Calcul personnalise (au lieu de retourner 100.0 brut)
        double donneeFzb = 100.0 + (facteurLocalFzb * compteurAccesFzb);
        System.out.println("[SourceLocaleFzb] Acces n°" + compteurAccesFzb
                + " - Donnee locale : " + donneeFzb
                + " (facteur=" + facteurLocalFzb + ")");
        return donneeFzb;
    }

    @Override
    public boolean estDisponibleFzb() {
        // Simulation : toujours disponible en local
        System.out.println("[SourceLocaleFzb] Verification de disponibilite : OK");
        return true;
    }

    @Override
    public String obtenirNomSourceFzb() {
        return "SOURCE_LOCALE_FZB";
    }

    // Methode supplementaire (non presente dans l'interface)
    public double calculerMoyenneLocaleFzb(double[] valeursFzb) {
        if (valeursFzb == null || valeursFzb.length == 0) return 0;
        double sommeFzb = 0;
        for (double v : valeursFzb) sommeFzb += v;
        return sommeFzb / valeursFzb.length;
    }
}