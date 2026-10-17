package presentation;

import dao.IAccesDonneesFzb;
import metier.IMoteurCalculFzb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Moteur de chargement dynamique (conteneur d'injection de dependances manuel).
 *
 * Cette classe orchestre :
 * 1. La lecture du fichier de configuration (config-faz.properties).
 * 2. Le chargement dynamique des classes via reflexion (Class.forName).
 * 3. L'instanciation via les constructeurs par defaut.
 * 4. L'injection de dependances via les setters (brancherSourceFzb).
 * 5. L'execution de la logique metier.
 *
 * Principe : le code ne connait QUE les interfaces (IAccesDonneesFzb, IMoteurCalculFzb),
 * pas les implementations concretes (SourceLocaleFzb, SourceDistanteFzb, MoteurCalculFzb).
 */
public class LanceurDynamiqueFzb {

    /** Nom du fichier de configuration (relatif au projet). */
    private static final String FICHIER_CONFIG_FZB = "src/main/resources/config-faz.properties";

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("   LANCEUR DYNAMIQUE - PROJET REFLEX-FAZ          ");
            System.out.println("==================================================");

            // 1. Charger les noms de classes depuis le fichier de configuration
            String[] nomsClassesFzb = chargerNomsClassesFzb(FICHIER_CONFIG_FZB);
            System.out.println("[Conteneur] Classe source : " + nomsClassesFzb[0]);
            System.out.println("[Conteneur] Classe moteur : " + nomsClassesFzb[1]);

            // 2. Instancier la source (implementation de IAccesDonneesFzb)
            IAccesDonneesFzb sourceFzb = (IAccesDonneesFzb) instancierClasseFzb(nomsClassesFzb[0]);
            System.out.println("[Conteneur] Source instanciee : " + sourceFzb.getClass().getName());
            System.out.println("[Conteneur] Nom source : " + sourceFzb.obtenirNomSourceFzb());

            // 3. Instancier le moteur (implementation de IMoteurCalculFzb)
            IMoteurCalculFzb moteurFzb = (IMoteurCalculFzb) instancierClasseFzb(nomsClassesFzb[1]);
            System.out.println("[Conteneur] Moteur instancie : " + moteurFzb.getClass().getName());

            // 4. Injecter la source dans le moteur via reflexion
            injecterDependanceFzb(moteurFzb, "brancherSourceFzb", sourceFzb);
            System.out.println("[Conteneur] Dependance injectee via reflexion.");
            System.out.println("[Conteneur] Source branchee ? " + moteurFzb.sourceEstBrancheeFzb());

            // 5. Executer la logique metier (3 fois pour tester les methodes)
            System.out.println("\n--- Execution de 3 traitements ---");
            for (int i = 1; i <= 3; i++) {
                System.out.println("\n>>> Traitement n°" + i);
                double resultatFzb = moteurFzb.executerTraitementFzb();
                System.out.println("[Conteneur] Resultat " + i + " : " + resultatFzb);
            }

            // 6. Afficher la description du moteur
            System.out.println("\n--- Description du moteur ---");
            System.out.println(moteurFzb.decrireMoteurFzb());

            System.out.println("\n==================================================");
            System.out.println("   FIN DU LANCEMENT DYNAMIQUE                     ");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("[Conteneur] ERREUR : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lit les noms de classes depuis un fichier de configuration.
     * Le fichier doit contenir exactement 2 lignes :
     * - Ligne 1 : nom complet de la classe source (ex: dao.SourceLocaleFzb)
     * - Ligne 2 : nom complet de la classe moteur (ex: metier.MoteurCalculFzb)
     *
     * @param nomFichierFzb Le chemin du fichier de configuration.
     * @return Un tableau de 2 noms de classes.
     */
    private static String[] chargerNomsClassesFzb(String nomFichierFzb) throws IOException {
        String[] nomsClassesFzb = new String[2];
        try (BufferedReader lecteurFzb = new BufferedReader(new FileReader(nomFichierFzb))) {
            for (int i = 0; i < 2; i++) {
                String ligneFzb = lecteurFzb.readLine();
                if (ligneFzb == null) {
                    throw new IOException("Le fichier de configuration doit contenir 2 lignes !");
                }
                nomsClassesFzb[i] = ligneFzb.trim();
            }
        }
        return nomsClassesFzb;
    }

    /**
     * Instancie une classe a partir de son nom complet via reflexion.
     * Utilise getDeclaredConstructor() pour recuperer le constructeur par defaut.
     *
     * @param nomClasseFzb Le nom complet de la classe (ex: dao.SourceLocaleFzb).
     * @return L'instance creee.
     */
    private static Object instancierClasseFzb(String nomClasseFzb) throws Exception {
        Class<?> classeFzb = Class.forName(nomClasseFzb);
        Constructor<?> constructeurFzb = classeFzb.getDeclaredConstructor();
        return constructeurFzb.newInstance();
    }

    /**
     * Injecte une dependance dans un objet via une methode setter.
     *
     * @param cibleFzb L'objet cible (ex: MoteurCalculFzb).
     * @param nomSetterFzb Le nom de la methode d'injection (ex: brancherSourceFzb).
     * @param dependanceFzb La dependance a injecter (ex: SourceLocaleFzb).
     */
    private static void injecterDependanceFzb(Object cibleFzb, String nomSetterFzb, Object dependanceFzb) throws Exception {
        Class<?> classeCibleFzb = cibleFzb.getClass();
        Method methodeSetterFzb = classeCibleFzb.getMethod(
                nomSetterFzb,
                Class.forName("dao.IAccesDonneesFzb")
        );
        methodeSetterFzb.invoke(cibleFzb, dependanceFzb);
    }
}