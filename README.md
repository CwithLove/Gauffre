# Gauffre

## MODELE
### NIVEAU -> Carte (Anthony, Mathis)
- Attributs: int[][] Tab; final int VIDE, GAUFFRE, EMPOIS.

- Methode Constructor(lig, col)
- Methode redimensionne(lig, col) => Appel Contructor(lig, col) 
- Methode getter(lig, col)
- Methode setter(lig, col, val) 
- Methode final() -> return tab[0][0] == VIDE
- Methode d'affichage() -> ( ' ' pour Vide, '#' pour Gauffre, '@' pour Empoisonne)

=> Ecris une nouvelle class TestNiveau pour tester

### Jeu -> Lancer la partie (Yuzhen, Yilun)
- Attributs: Niveau, int tour

- Methode Manger(Point posMange) -> void
- Methode Final(Niveau) -> bool
- Methode Lancer() -> Lancement la partie (boucle infini)
=> Tant que !final():
    => tour += 1
    => Lire sur le terminal pour Manger(lig, col)
La personne ((tour%2) + 1) gagne

=> MAIN

## CONTROLLER (apres)
- Ecouteur de souris (MouseListener)
- Ecouteur de clavier => pour redimensionner la carte (apres)

## VUE (apres)
### INTERFACE GRAPHIQUE
- Initialiser l'interface (jeu et button)

### NIVEAU GRAPHIQUE 
- => PaintComponent() & Repaint()


## MAIN 
- Appeler Jeu.Lancer() ?


Swing -> Vue <- Modele