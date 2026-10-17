package ext;

import dao.IAccesDonneesFzb;

public class SourceDistanteFzb implements IAccesDonneesFzb {

    // Parametre personnalise : la valeur sera differente pour chaque instance
    private double facteurDistantFzb = 7.3;
    private int compteurAccesFzb = 0;

    public SourceDistanteFzb() {
        // Constructeur par defaut explicite
    }

    @Override
    public double extraireDonneeFzb() {
        compteurAccesFzb++;
        // Calcul personnalise (au lieu de retourner 100.0 brut)
        double donneeFzb = 200.0 - (facteurDistantFzb * compteurAccesFzb);
        System.out.println("[SourceDistanteFzb] Acces n°" + compteurAccesFzb
                + " - Donnee distante : " + donneeFzb
                + " (facteur=" + facteurDistantFzb + ")");
        return donneeFzb;
    }

    @Override
    public boolean estDisponibleFzb() {
        // Simulation : verifier la connexion (toujours OK ici)
        System.out.println("[SourceDistanteFzb] Verification de disponibilite : OK");
        return true;
    }

    @Override
    public String obtenirNomSourceFzb() {
        return "SOURCE_DISTANTE_FZB";
    }

    // Methode supplementaire
    public double calculerEcartTypeDistantFzb(double[] valeursFzb) {
        if (valeursFzb == null || valeursFzb.length == 0) return 0;
        double moyenneFzb = 0;
        for (double v : valeursFzb) moyenneFzb += v;
        moyenneFzb /= valeursFzb.length;
        double varianceFzb = 0;
        for (double v : valeursFzb) varianceFzb += Math.pow(v - moyenneFzb, 2);
        varianceFzb /= valeursFzb.length;
        return Math.sqrt(varianceFzb);
    }
}