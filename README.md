# Projet de Gestion de Connexion Client-Serveur

Ce projet est une application client-serveur permettant à un client de se connecter à un serveur, de s'authentifier et d'exécuter des commandes à distance. Le serveur gère les connexions, authentifie les clients et enregistre les commandes exécutées dans un fichier de logs.


## Fonctionnalités

### Côté Client

- **Authentification** : Connexion sécurisée avec nom d'utilisateur et mot de passe.
- **Envoi de commandes** : Exécution de commandes sur le serveur.
- **Interface graphique** : Saisie de commandes et affichage des résultats en GUI.

### Côté Serveur

- **Gestion des connexions** : Accepte et gère plusieurs clients simultanément.
- **Authentification** : Vérification des identifiants via une base de données.
- **Exécution des commandes** : Traitement et retour des résultats aux clients.
- **Journalisation** : Enregistrement des commandes exécutées avec IP et timestamp.
- **Interface graphique** : Affichage des logs et gestion des connexions.

## **Structure du projet**

### **Fichiers principaux**

#### **Client**

- `ClientGUI.java` : Interface graphique du client.
- `ClientConnection.java` : Gestion de la connexion au serveur.
- `AuthenticationManager.java` : Gestion de l'authentification.
- `CommandHandler.java` : Envoi et réception des commandes.
- `TestClient.java` : Point d'entrée du client.

#### **Serveur**

- `ServeurGUI.java` : Interface graphique du serveur.
- `Serveur.java` : Gestion des connexions et exécution des commandes.
- `ConversationClient.java` : Communication avec un client spécifique.
- `ConnectionBD.java` : Gestion de l'authentification via MySQL.
- `TestServeur.java` : Point d'entrée du serveur.


## **Installation et utilisation**

### **Prérequis**

1. **Java Development Kit (JDK 8 ou supérieur)**
2. **MySQL** : Base de données utilisée pour l'authentification.
3. **JDBC Driver** : Connexion entre Java et MySQL.

### **Configuration**

1. **Base de données**

   - Créez une base de données `ordinateur_distant`.
   - Créez une table `clients` avec `username` et `password`.
   - Insérez des utilisateurs dans la table.

2. **Modifier**
**`ConnectionBD.java`**

   ```java
   private final String url = "jdbc:mysql://localhost:3306/ordinateur_distant";
   ```

### **Lancer le serveur**

1. Compiler et exécuter :
   ```bash
   cls && javac -cp ".;lib/" TestServeur.java && java -cp ".;lib/" TestServeur : commande qui permet d'executer le serveur
   ```
2. L'interface serveur s'affiche, en attente de connexions.

   ![Image](https://github.com/user-attachments/assets/bf642dc4-bb7f-4e2d-9698-6ede8a5a76c8)

### **Lancer le client**

1. Compiler et exécuter :
   ```bash
   cls && javac TestClient.java && java TestClient : commande qui permet d'executer le client
   ```
2. L'interface client s'affiche. Saisissez vos identifiants pour vous connecter.

---

## **Exemples d'utilisation**

### **Authentification**

- Entrer un nom d'utilisateur et un mot de passe valides pour se connecter.

### **Envoi de commandes**

- Exemple :
  ```bash
  dir
  ```
  Le serveur exécute la commande et envoie la réponse au client.

### **Journalisation**

- Toutes les commandes exécutées sont enregistrées dans `commands.log`.


## **Technologies utilisées**

- **Java** : Langage principal
- **Swing** : Interface graphique
- **MySQL** : Base de données
- **JDBC** : Connexion entre Java et MySQL




