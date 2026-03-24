# Lancer le projet

## 1 — Démarrer la base de données

Pour lancer votre base de données SQL et Adminer :

```bash
docker compose up -d
```

(vous devez avoir lancé Docker Desktop au préalable si vous êtes sur Windows)

---

## 2 — Accéder à Adminer

Adminer permet de visualiser la base de données.

URL :

```
http://localhost:8081
```

Paramètres de connexion :

| Champ    | Valeur        |
| -------- |---------------|
| System   | PostgreSQL    |
| Server   | race_postgres |
| Username | race          |
| Password | race          |
| Database | race_db       |

---

## 3 — Lancer l'application

Lancer votre configuration directement sur IntelliJou en ligne de commande :

```bash
mvn spring-boot:run
```

L'API sera disponible sur :

```
http://localhost:8080
```

---

## 4 - Lancer PostMan 

1 - Ouvrir Postman
2 - Importer le fichier :
```
postman-export.json
```

3 - Tester les différentes routes

## 5 - EndPoint implementer 

# Runners 
| Méthode | Endpoint           | Description                       |
| ------- | ------------------ | --------------------------------- |
| POST    | /runners           | Créer un runner                   |
| GET     | /runners           | Récupérer tous les runners        |
| GET     | /runners/{id}      | Récupérer un runner par ID        |
| PUT     | /runners/{id}      | MAJ un runner                     |
| DELETE  | /runners/{id}      | Supprimer un runner               |
| GET     | /runners/{id}/race | Récupérer les courses d’un runner |

# Races
| Méthode |	Endpoint                       | Description                   |
| ------- | ------------------------------ | ----------------------------- |
| POST	  | /races                         | Créer une course              |
| GET     | /races                         | Récupérer toutes les courses  |
| GET     |	/races/{id}	                   | Récupérer une course par ID   |
| PUT     | /races/{id}                    | Mettre à jour une course      |
| GET     |	/races/{id}/participants/count | Nombre de participants        |

# Registrations
| Méthode |	Endpoint                     | Description                         |
| ------- | ---------------------------- | ----------------------------------- |
| POST    |	/registrations               | Inscrire un runner à une course     |
| GET     |	/registrations/race/{raceId} | Liste des participants d’une course |