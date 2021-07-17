import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainBousi {

    ArrayList<File> fichiers = new ArrayList();

    private void listFichierDansDossier(File dossier) {
        File filesList[] = dossier.listFiles();
        // remplir fichiers et appeler cette fonction récursivement si dossier
        for(File fichier : filesList) {
            if (fichier.isFile()) {
                if (fichier.toString().endsWith(".avi") ||
                        fichier.toString().endsWith(".mp4") ||
                        fichier.toString().endsWith(".ogm") ||
                        fichier.toString().endsWith(".mkv") ||
                        fichier.toString().endsWith(".mpg") ||
                        fichier.toString().endsWith(".VOB") ||
                        fichier.toString().endsWith(".rm") ||
                        fichier.toString().endsWith(".rmvb") ||
                        fichier.toString().endsWith(".divx") ||
                        fichier.toString().endsWith(".IFO")
                ) {
                    fichiers.add(fichier);
                } else if (
                    !fichier.toString().endsWith(".BUP") &&
                    !fichier.toString().endsWith(".srt") &&
                    !fichier.toString().endsWith(".txt") &&
                    !fichier.toString().endsWith(".smil") &&
                    !fichier.toString().endsWith(".nfo") &&
                    !fichier.toString().endsWith(".jpg") &&
                    !fichier.toString().endsWith(".doc") &&
                    !fichier.toString().endsWith(".db")
                ) {
                    System.out.println("fichier de type inconnu : "+ fichier.getAbsolutePath());
                }
            } else if (fichier.isDirectory()){
                listFichierDansDossier(fichier);
            }
        }
    }




    public MainBousi() {
        lancerBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    // lister toutes les séries sélectionnées
                    //String racine = "E:\\Series\\"; // racine quand en train de bosser, pas pour la version de production/déploiement
                    String racine = "..\\"; // quand en déploiement Bousi est placé dans le même dossier que els séries

                    // choisir une série
                    ArrayList<String> dossiersSeries = dossierDesSeriesSelectionnees();

                    if (dossiersSeries.size() == 0) {
                        System.err.println("Pas de série sélectionnée !!");
                        return;
                    }
                    int indexSerie = (int)(Math.random() * dossiersSeries.size());
                    String fichierChoisiStr = dossiersSeries.get(indexSerie);

                    // lister tous les épisodes de toutes les saisons
                    String dossierSerieStr1 = racine + fichierChoisiStr;
                    System.out.println("dossierSerieStr1 : " + dossierSerieStr1);
                    File dossierSerie = new File(dossierSerieStr1);
                    fichiers.clear();
                    listFichierDansDossier(dossierSerie);

                    // choisir un épisode dans la série
                    //List of all files and directories
                    int index = (int)(Math.random() * fichiers.size());
                    File fichierChoisi = fichiers.get(index);

                    // remplacer caractères à escaper

                    //String episode = "saison_3\\1 Amazon Women in the Mood.avi\"";

                    // lancer vidéo via vlc
                    String cheminFinal = "\"" + fichierChoisi.getAbsoluteFile().toString().replace("\\", "\\\\") + "\"";
                    String commande = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe "+ cheminFinal;

                    System.out.println("Commande : " + commande);

                    // Running the above command
                    Runtime run  = Runtime.getRuntime();
                    Process proc = run.exec(commande);
                }

                catch (IOException exc)
                {
                    exc.printStackTrace();
                }
            }
        });
        toutDécocherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox checkB : boites) {
                    checkB.setSelected(false);
                }
            }
        });
        humourFrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ne garde que les trucs humoristiques et en français :
                brefCheckBox.setSelected(true);
                friendsCheckBox.setSelected(true);
                griffinCheckBox.setSelected(true);
                kaamelottCheckBox.setSelected(true);
                rickMortyCheckBox.setSelected(true);
                southparkCheckBox.setSelected(true);
                dariaCheckBox.setSelected(true);
                futuramaCheckBox.setSelected(true);
                simpsonsCheckBox.setSelected(true);
                americanDadCheckBox.setSelected(false);
                cowboyBebopCheckBox.setSelected(false);
                herculePoirotCheckBox.setSelected(false);
                profitCheckBox.setSelected(false);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainBousi");
        frame.setContentPane(new MainBousi().panneauRacine);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel panneauRacine;
    private JButton lancerBouton;
    private JCheckBox brefCheckBox;
    private JCheckBox americanDadCheckBox;
    private JCheckBox cowboyBebopCheckBox;
    private JCheckBox friendsCheckBox;
    private JCheckBox griffinCheckBox;
    private JCheckBox kaamelottCheckBox;
    private JCheckBox rickMortyCheckBox;
    private JCheckBox southparkCheckBox;
    private JCheckBox dariaCheckBox;
    private JCheckBox futuramaCheckBox;
    private JCheckBox herculePoirotCheckBox;
    private JCheckBox profitCheckBox;
    private JCheckBox simpsonsCheckBox;
    private JCheckBox checkBox14;
    private JButton toutDécocherButton;
    private JButton humourFrButton;

    List<JCheckBox> boites = Arrays.asList(brefCheckBox, americanDadCheckBox, cowboyBebopCheckBox, friendsCheckBox, griffinCheckBox, kaamelottCheckBox,
            rickMortyCheckBox, southparkCheckBox, dariaCheckBox, futuramaCheckBox, herculePoirotCheckBox, profitCheckBox, simpsonsCheckBox);

    private ArrayList<String> dossierDesSeriesSelectionnees() {

        ArrayList<String> dossiersSeries = new ArrayList<>();

        for (JCheckBox checkB : boites) {
            if (checkB.isSelected()) {
                dossiersSeries.add(checkB.getText() + "\\");
            }
        }
        return dossiersSeries;
    }
}
