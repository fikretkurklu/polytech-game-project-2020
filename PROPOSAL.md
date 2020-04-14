
# GROUPE 1 - Idée de projet

## L'histoire
Le joueur a été choisi pour aller récupérer une idole volée par X qui s’est réfugié dans son donjon. Pour y accéder, il va devoir réussir un certain nombre de donjon avant de trouver X (Idée de base un donjon et s’il nous reste assez de temps complétez).

## But du jeu
Récupérer l'idole. Pour cela, il va devoir réussir différents donjon avant de trouver l’idole. A chacun, il va passer par différents essais dans un donjon très compliqué où il est obligé de revenir au village pour améliorer son équipement afin de réussir le donjon.  
Le donjon a une difficulté de base très élevée. Les salles et les niveaux sont générés aléatoirement mais respectant les “codes” du donjon. C’est à dire que le donjon sera globalement toujours le même (l'archétype des salles ainsi que le nombre de monstres et de pièges), les salles ne seront jamais les même. Il pourra quitter le donjon en milieu de partie afin d’améliorer ses capacités au village cela oblige le joueur à perdre sa progression dans le donjon actuel (il peut revenir à la salle où il s’est arrêté moyennant de l’argent).

Une fois le premier donjon, le même schéma se répète pour le second, jusqu'à arriver au dernier donjon.


## La vue

**vue de côté / gestion de gravité**

Exemple de jeu :

![alt text](https://www.micromania.fr/on/demandware.static/-/Sites-Micromania-Library/default/dw5f36c6d2/fanzone/dossier/ori/ori-genial_Header.jpg "Ori and the blind forest")
>Ori and the blind forest, Jeu-Vidéo, 2015

![alt text](https://www.mobygames.com/images/promo/original/1481294587-3633937912.jpg "Rogue Legacy")
>Rogue Legacy, Jeu-Vidéo, 2013



## Trois maps de bases

* Le village (crafting, stuff, magasin, etc…) -> Le village est une fenêtre contenant les différentes options disponibles au joueur dans le village (via un menu sur la droite). Ici, le joueur peut vendre et acheter de l'équipement (arme & magic), se reposer (soins), gérer son personnage, “craft” des équipements et des potion, et partir à l’aventure.

![alt text](https://images-eu.ssl-images-amazon.com/images/I/A1iJN9BobVL.jpg "Shakes & Fidget")
>Shakes & Fidget, Jeu-Vidéo, 2009, Exemple de village au sein d'un jeu

* Donjon : Ici, le joueur doit atteindre la sortie en affrontant des monstres, et en sautant sur des plateformes. Il peut revenir à tout moment au village avec les équipements et l’argent récupérés dans le donjon. Il faut donc que le joueur choisisse le moment idéal pour revenir avec un maximum de ressources pour améliorer son équipement et ainsi réussir la nouvelle tentative du donjon. -> le donjon serait composé d’un nombre de salles aléatoires (le joueur (basées sur des archétypes : le placement des pièges et monstres placés aléatoirement mais position des plateformes fixes). Chaque salle serait plus grande que le viewport du joueur. Lorsque le joueur arrive dans une salle du donjon, il doit récupérer une clé qui est cachée sur l’un des monstres et s’en servir pour ouvrir la porte qui mène à la salle suivante (il ne peut pas faire marche arrière dès qu’il est entré dans une nouvelle salle, il est donc important que le joueur récupère un maximum d’argent dans la salle où il est avant de passer à la salle suivante). L’argent est récupéré en tuant des monstres. Moyennant une certaine somme d’argent, le joueur pourra également revenir à certains points clés de sa progression pour éviter qu’il ait à refaire les salles les plus faciles lorsque ses statistiques seront bien augmentées. 
Le personnage pourra aussi récupérer de manière plus rare des fragments de clé du boss qui lui permettront une fois tous récupérer d’aller affronter le boss. Attention tout de même, les fragments de clé sont enchantés et de ce fait ceux-ci ne peuvent pas sortir du donjon, le retour au village du personnage entraîne donc la perte de tous les fragments de clé du joueur. 


* Monde de la seconde chance : Ce monde sera accessible à la mort du joueur et offrira à celui-ci un défi pour essayer de récupérer les fragments de son âme afin de revenir à la vie. Dans ce mode, le joueur incarne une âme errante poursuivie par les fantômes des créatures tuées dans le monde normal (à chaque fois qu’une créature est tuée, un fantôme est généré dans le monde de la seconde chance). Ce monde est en vu de dessus ou le personnage cherche les fragments de son âme éparpillés sur la map avec des ennemis qui fonce vers le joueur quand celui-ci s’approche d’eux.


## Equipements

Dans le jeux, le joueur aura accès différents type d’objets:

1.  Arcs : Long ou court. Les arcs long sont très puissants mais la vitesse d’attaque est lente. Pour les arcs cours c’est l’inverse.
2. Équipements : Armures, heaume, gant, jambières : augmente les résistances du joueur. Un mauvais équipement réduira sa vitesse de déplacement et d’attaque.
3. Consommables : Potions de soin, améliorations de stats.

## Le personnage

### Stats : 
1.  Résistance : Attaque Physique/Magique
2. Vie
3. Vitesse d'attaque (nb attaque/secondes)
4. Force

### Automates: A compléter
### Dans le donjon: A compléter

### Personnage:
- Déplacement: q et d concerne le déplacement à gauche et à droite
- Egg : tirer une flèche avec son arc (click & release du clic gauche de la souris)
- Pop : sauter (z)
- Wizz : ramasser un objet / activer un levier / manipuler des objets (clic droit)

### Ennemis:
- Déplacement horizontal des ennemis sur les plateformes (certains ennemis se dirigeront vers le joueur en volant par exemple)
- Pop : courir vers le joueur (pour certains ennemis)
- Wizz : attaquer/tirer une boule de feu, etc… (pour certains ennemis)

### Dans le monde de la seconde chance :

#### Personnage :
- Déplacement : z,q,s,d dans les 4 directions
- Egg: crée un leurre qui attire les fantômes vers lui et ainsi permet au joueur de se créer un ouverture pour récupérer des fragments d’âme.
- Pop: dash du joueur dans la direction vers laquelle il s’oriente
- Wizz: canalisation d’un fragment d’âme pour qu’il rejoigne son corps 

#### Ennemis :
- Déplacement : déplacement aléatoire
- Pop : suivre le joueur quand il s’approche trop près

## Difficulté

- Gestion de la gravité
- Génération aléatoire des niveaus (gestion des objets  etc...)
- Gestion du viewport
- Gestion de l'IA des ennemis