# SAE3.02 - Dev Application 


[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](http://forthebadge.com) 
[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](http://forthebadge.com) 
[![forthebadge](https://forthebadge.com/images/badges/works-on-my-machine.svg)](http://forthebadge.com) 
[![forthebadge](https://forthebadge.com/images/badges/gluten-free.svg)](http://forthebadge.com)


Le but de ce projet est de développer un outil de chargement et affichage d'un ensemble de données. 
Il doit permettre également de classer une donnée par rapport aux données déjà chargées, en déterminant quelle est la catégorie la plus probable de cette donnée.

## Fonctionnalités 

* [x] Chargement d'un fichier CSV 
* [x] Visualisation des données sous forme de nuage de points
* [x] Différencier les points selon leur catégorie
* [x] Possibilité de modifier les axes de projection du nuage
* [x] Possibilité d'ajouter un nouveau point
* [x] Classer les points sans catégorie
* [x] Plusieurs fenêtres simultanées

## Pré-requis

- Java
- Maven

## Démarrage

Pour lancer le programme : 
* Avec IntelliJ :
  * Reload le projet Maven
  * Cliquer sur le bouton "Run" sur la classe app/Main
* En CLI : 
  * mvn javafx:run
* En graphique : 
  * Lancer l'exécutable (fichier .jar)

Pour lancer les tests :
* Avec IntelliJ :
  * Cliquer sur le bouton "Run" sur une classe de test
* En CLI : 
  * mvn test

## Développé avec

* [Java](https://www.java.com/fr/) - Langage orienté objet
* [Maven](https://maven.apache.org/) - Gestion/Automatisation de production
* [JavaFX](https://openjfx.io/) - Interface Homme-Machine
* [JUnit](https://junit.org/junit5/) - Tests automatisés
* [OpenCSV](https://opencsv.sourceforge.net/) - Lecture de CSV

## Versions

Version 1.0

## Auteurs
* **OKUBO Camille**
* **UTZERI Giorgio**
* **KOCHIEV Mickhail**
* **KHUDOEV Revaz**
* **LECOCQ Dylan**
