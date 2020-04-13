# GROUPE 1 - Idée de projet

## L'histoire
Le joueur a été choisi pour aller récupérer une idole volée par X qui s’est réfugié dans son donjon. Pour y accéder, il va devoir réussir un certain nombre de donjon avant de trouver X (Idée de base un donjon et s’il nous reste assez de temps complétez).

## But du jeu
Récupérer l'idole. Pour cela, il va devoir réussir différents donjon avant de trouver l’idole. A chacun, il va passer par différents essais dans un donjon très compliqué où il est obligé de revenir au village pour améliorer son équipement afin de réussir le donjon.  
Le donjon a une difficulté de base très élevée. Les salles et les niveaux sont générés aléatoirement mais respectant les “codes” du donjon. C’est à dire que le donjon sera globalement toujours le même, mais les monstres et les salles ne seront jamais les même. Il pourra quitter le donjon en milieu de partie afin d’améliorer ses capacités au village.

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

* Donjon -> Ici, le joueur doit atteindre la sortie en affrontant des monstres, et en sautant sur des plateformes. Il peut revenir à tout moment au village avec les équipements et l’argent récupérés dans le donjon. Il faut donc que le joueur choisisse le moment idéal pour revenir avec un maximum de ressources pour améliorer son équipement et ainsi réussir la nouvelle tentative du donjon. -> le donjon serait composé d’un nombre de salles aléatoires (le joueur (basées sur des archétypes : le placement des pièges et monstres placés aléatoirement mais position des plateformes fixes). Chaque salle serait plus grande que le viewport du joueur.

* Monde de la seconde chance -> Ce monde sera accessible à la mort du joueur et offrira à celui-ci un défi pour essayer de récupérer les fragments de son âme afin de revenir à la vie. Dans ce mode, le joueur incarne une âme errante poursuivie par les fantômes des créatures tuées dans le monde normal (à chaque fois qu’une créature est tuée, un fantôme est généré dans le monde de la seconde chance). Dans ce monde, les déplacements se font dans les 4 directions avec les flèches directionnelles et EGG permet de créer un leurre qui va attirer les fantômes. POP permet au joueur de dasher dans une direction pour échapper aux fantômes, et WIZZ lui permet de canaliser un fragment d’âme pour qu’il rejoigne son corps.

## Equipements

Dans le jeux, le joueur aura accès différents type d’objets:

1.  Arcs : Long ou court. Les arcs long sont très puissants mais la vitesse d’attaque est lente. Pour les arcs cours c’est l’inverse.
2. Équipements : Armures, heaume, gant, jambières : augmente les résistances du joueur. Un mauvais équipement réduira sa vitesse de déplacement et d’attaque.
3. Consommables : Potions de soin, améliorations de stats.

## Le personnage

Stats : 
1.  Résistance : Attaque Physique/Magique
2. Vie
3. Vitesse d'attaque (nb attaque/secondes)
4. Force

Deux modes pour le personnage:
- Humain normal (déplacement et saut avec attaque du personnage). Il ne se battra qu'avec un arc. Il se déplace de droite à  gauche avec les touches q et d. 
Egg : créer une flèche avec son arc (pour attaquer les ennemis) (release click gauche lancer de flèche)
Pop : sauter (z)
Wizz : ramasser un objet (clic droit) / activer un levier / manipuler des objets.

- Transformation (potentiellement en chauve souris)
Dans ce mode, le personnage peut accéder à d’autre zones, voir des éléments non-visibles en mode “humain” (pièges, mécanisme), mais il est plus vulnérable et ne peut pas transporter ces affaires. En se transformant, il laisse tout par terre, et peut seulement transporter une objet de petite taille. De plus, certains ennemis ne considèrent pas la chauve souris. Mais d’autres, tel que des chats ou des hiboux se réveillent quand le joueur est en mode chauve-souris. Ils sont autrement des éléments du décors. Le mode chauve-souris peut donc permettre au joueur d’éviter des ennemis. Certains ennemis continuent malgré tout d’attaquer.
Egg : créer un ultrason pour détecter les ennemis et zones cachés (press click gauche). Immobilise la chauve souris.
Pop : voler (z)
Wizz : Porter/Lâcher un seul objet (il peut voler en le portant) (clic droit)

## Difficulté

- Gestion de la gravité
- Génération aléatoire des niveaus (gestion des objets  etc...)
- Gestion du viewport
- Gestion de l'IA des ennemis