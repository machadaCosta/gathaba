# gathaba_sarvaca
This project is about a client for Github Service.

## ETUDE APIs

1. L'API Github (https://developer.github.com/v3/) permet d'obtenir :
- les répertoires d'un utilisateur,
- les problèmes d'un répertoire,
- etc
Elle permet aussi de créer  :
- un répertoire, 
- une branche dans un répertoire,
- etc.

2. L'API Météo (http://openweathermap.org/forecast5) permet d'obtenir :
- la météo du jour
- la météo sur les 5 derniers jours
- la météo comprend l'ensoleillement, la tempérarture, le taux d'humidité

## LISTER DES REQUÊtES POSSIBLES

1. Pour l'API Github, voici des possibles demandes de service d'une application cliente :
- demander la liste des répertoires d'un utilisateur
- demander la liste des commits sur un répertoire
- demander la liste des issues sur un répertoire
- demander la liste des followers d'un utilisateur
- demander le nombre de commit par jour sur un répertoire pour les 52 dernières semaines
Pour l'API Météo, voici des possibles demandes de service d'une application cliente :
- demander la météo du jour
- demander la météo des 5 derniers jours

2. Choix de l'API Github.

3. Voici des requêtes HTTP POST :
- créer un nouveau répertoire pour un utilisateur authentifier : 
`https://api.github.com/user/repos`
avec en contenu l'objet JSON :
`{
"name": "Hello-World",
"description": "This is your first repository",
"homepage": "https://github.com",
"private": false,
"has_issues": true,
"has_wiki": true,
"has_downloads": true
}`
- créer une issue sur un répertoire : 
`https://api.github.com/repos/machadacosta/bachamada/issues`
avec en contenu l'objet JSON :
`{
"title": "Not Clear",
"body": "There is some english word in french text.",
"assignee": "machadacosta",
"milestone": 1,
"labels": [
"Label1",
"Label2"
]
}`

4. Voici des requêtes HTTP GET, pour obtenir :
- la liste des répertoires de l'utilisateur machadacosta :
`https://api.github.com/users/machadacosta/repos`
- la liste des commits sur le répertoire bachamada de l'utilisateur machadacosta :
`https://api.github.com/repos/machadacosta/bachamada/commits`
- la liste des issues sur le répertoire bachamada de l'utilisateur machadacosta :
`https://api.github.com/repos/machadacosta/bachamada/issues`
- la liste des followers de l'utilisateur machadacosta
`https://api.github.com/users/machadacosta/followers`
- le nombre de commit par jour sur le répertoire bachamada de l'utilisateur machadacosta sur les 52 dernières semaines :
`https://api.github.com/repos/machadacosta/bachamada/stats/participation`

## TESTER LES REQUÊTES

1. Le test de la requête https://api.github.com/users/machadacosta/repos, avec Postman renvoie :
`[
{
"id": 39936429,
"name": "bachamada",
"full_name": "machadaCosta/bachamada",
"owner": {
"login": "machadaCosta",
"id": 2046403,
"avatar_url": "https://avatars.githubusercontent.com/u/2046403?v=3",
"gravatar_id": "",
"url": "https://api.github.com/users/machadaCosta",
"html_url": "https://github.com/machadaCosta",
"followers_url": "https://api.github.com/users/machadaCosta/followers",
"following_url": "https://api.github.com/users/machadaCosta/following{/other_user}",
"gists_url": "https://api.github.com/users/machadaCosta/gists{/gist_id}",
"starred_url": "https://api.github.com/users/machadaCosta/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/machadaCosta/subscriptions",
"organizations_url": "https://api.github.com/users/machadaCosta/orgs",
"repos_url": "https://api.github.com/users/machadaCosta/repos",
"events_url": "https://api.github.com/users/machadaCosta/events{/privacy}",
"received_events_url": "https://api.github.com/users/machadaCosta/received_events",
"type": "User",
"site_admin": false
},
"private": false,
"html_url": "https://github.com/machadaCosta/bachamada",
"description": "This project is about monitoring Heart Rate, in Beat Per Minute (BPM), with an Android App.",
"fork": false,
"url": "https://api.github.com/repos/machadaCosta/bachamada",
"forks_url": "https://api.github.com/repos/machadaCosta/bachamada/forks",
"keys_url": "https://api.github.com/repos/machadaCosta/bachamada/keys{/key_id}",
"collaborators_url": "https://api.github.com/repos/machadaCosta/bachamada/collaborators{/collaborator}",
"teams_url": "https://api.github.com/repos/machadaCosta/bachamada/teams",
"hooks_url": "https://api.github.com/repos/machadaCosta/bachamada/hooks",
"issue_events_url": "https://api.github.com/repos/machadaCosta/bachamada/issues/events{/number}",
"events_url": "https://api.github.com/repos/machadaCosta/bachamada/events",
"assignees_url": "https://api.github.com/repos/machadaCosta/bachamada/assignees{/user}",
"branches_url": "https://api.github.com/repos/machadaCosta/bachamada/branches{/branch}",
"tags_url": "https://api.github.com/repos/machadaCosta/bachamada/tags",
"blobs_url": "https://api.github.com/repos/machadaCosta/bachamada/git/blobs{/sha}",
"git_tags_url": "https://api.github.com/repos/machadaCosta/bachamada/git/tags{/sha}",
"git_refs_url": "https://api.github.com/repos/machadaCosta/bachamada/git/refs{/sha}",
"trees_url": "https://api.github.com/repos/machadaCosta/bachamada/git/trees{/sha}",
"statuses_url": "https://api.github.com/repos/machadaCosta/bachamada/statuses/{sha}",
"languages_url": "https://api.github.com/repos/machadaCosta/bachamada/languages",
"stargazers_url": "https://api.github.com/repos/machadaCosta/bachamada/stargazers",
"contributors_url": "https://api.github.com/repos/machadaCosta/bachamada/contributors",
"subscribers_url": "https://api.github.com/repos/machadaCosta/bachamada/subscribers",
"subscription_url": "https://api.github.com/repos/machadaCosta/bachamada/subscription",
"commits_url": "https://api.github.com/repos/machadaCosta/bachamada/commits{/sha}",
"git_commits_url": "https://api.github.com/repos/machadaCosta/bachamada/git/commits{/sha}",
"comments_url": "https://api.github.com/repos/machadaCosta/bachamada/comments{/number}",
"issue_comment_url": "https://api.github.com/repos/machadaCosta/bachamada/issues/comments{/number}",
"contents_url": "https://api.github.com/repos/machadaCosta/bachamada/contents/{+path}",
"compare_url": "https://api.github.com/repos/machadaCosta/bachamada/compare/{base}...{head}",
"merges_url": "https://api.github.com/repos/machadaCosta/bachamada/merges",
"archive_url": "https://api.github.com/repos/machadaCosta/bachamada/{archive_format}{/ref}",
"downloads_url": "https://api.github.com/repos/machadaCosta/bachamada/downloads",
"issues_url": "https://api.github.com/repos/machadaCosta/bachamada/issues{/number}",
"pulls_url": "https://api.github.com/repos/machadaCosta/bachamada/pulls{/number}",
"milestones_url": "https://api.github.com/repos/machadaCosta/bachamada/milestones{/number}",
"notifications_url": "https://api.github.com/repos/machadaCosta/bachamada/notifications{?since,all,participating}",
"labels_url": "https://api.github.com/repos/machadaCosta/bachamada/labels{/name}",
"releases_url": "https://api.github.com/repos/machadaCosta/bachamada/releases{/id}",
"created_at": "2015-07-30T07:08:22Z",
"updated_at": "2015-08-07T13:34:41Z",
"pushed_at": "2015-10-17T14:27:40Z",
"git_url": "git://github.com/machadaCosta/bachamada.git",
"ssh_url": "git@github.com:machadaCosta/bachamada.git",
"clone_url": "https://github.com/machadaCosta/bachamada.git",
"svn_url": "https://github.com/machadaCosta/bachamada",
"homepage": null,
"size": 2144,
"stargazers_count": 1,
"watchers_count": 1,
"language": "Java",
"has_issues": true,
"has_downloads": true,
"has_wiki": true,
"has_pages": false,
"forks_count": 0,
"mirror_url": null,
"open_issues_count": 1,
"forks": 0,
"open_issues": 1,
"watchers": 1,
"default_branch": "master"
},
...
}
]`

## Conception Application cliente

1. Voici une liste de fonctionnalités (d'un point de vue utilisateur) :
* Voir la liste des répertoires d'un utilisateur par défaut
** Voir les détails d'un répertoire
** Voir les statistiques d'un répertoire
* Configurer l'utilisateur par défaut
** Changer l'utilisateur
** Logger un utilisateur via github

2. Selon que l'application soit sur Mobile ou sur une page Web via un ordinateur, l'utilisateur n'a pas accès au même type d'interaction. En particulier, il preferera faire de la consultation et de l'édition simple sur une app. Mobile; en revanche il exigera des informations et une édition plus approfondies sur une app. Web.
Voici une liste de fonctionnalités applicatives hiérarchisés et classées selon les 2 types Web ou Mobile :
CLIENT MOBILE
* Voir la liste des répertoires d'un utilisateur par défaut 
ou ses répertoires ainsi que ceux de ses followers
** Voir les détails d'un répertoire
** Voir les statistiques d'un répertoire
* Configurer l'utilisateur par défaut
** Changer l'utilisateur
** Logger un utilisateur via github
CLIENT WEB
* Voir la liste des répertoires d'un utilisateur par défaut
ou ses répertoires ainsi que ceux de ses followers
* Voir une brève description pour chaque répertoire
** Voir les détails d'un répertoire
** Voir des statistiques approfondies d'un répertoire
* Voir un résumé d'activité (issues des répertoires, derniers commits, pull request, ...)
* Configurer un profil
** Changer l'utilisateur par défaut
** Logger un utilisateur via github


