# Tamagoshi graphic

## Concepts de cours et leurs utilisations :

- Swing : utilisation de Swing pour réaliser l'interface graphique
- Collection : utilisation d'ArrayList pour stocker les [fenetre Tamagoshi](src/tamagoshi/controller/TamaGameController.java)
- Expressions lambdas : utilisation d'[expression lambda](src/tamagoshi/controller/TamaGameController.java) pour enlever les tamagoshis mort de la liste (ligne 111)
- Hashmap : Pour stocker les [langues par pays](src/tamagoshi/controller/OptionController.java), pour associer les boutons radios au niveau sélectionné - [fenêtre option ligne 155](src/tamagoshi/graphic/FenetreOption.java)
- Classe properties : [stockage des propriétés et nom des tamagoshis](resource/config.properties)
- I18n : Interface disponible en [français](src/MessagesBundle_fr_FR.properties) et [anglais](src/MessagesBundle_en_US.properties), gestion avec [LanguageService](src/tamagoshi/service/LanguageService.java)
- java.util.logging : traçage de l'application (état du tamagoshi, nouveau cylce, etc...)
- Javadoc : [protected](javadoc/protected/index.html) et [private](javadoc/private/index.html)


## Informations :

- Je n'ai pas utilisé jlink pour faire le .jar, j'ai utilisé les artifacts IntelliJ
- [Lien de téléchargement du .jar](https://filesender.renater.fr/?s=download&token=ecb7cecb-2f2c-4de6-8197-be7187f781f7)
- Commande pour le lancer `java -jar tamagoshi_graphic.jar`
