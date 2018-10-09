# Justification Factory :: Data Access Objects

## Content

### Justification Systems DAO interface

The DAO allow modification on the known justifications systems:
* List them all.
* Get one in particular.
* Save one.
* Remove one.
This works as a key/value database which values are the justification systems and the keys, their names.

### Types of existing Justification Systems DAO

There is some implementation of this DAO:
* **[MapJustificationSystemsDAO](src/main/java/fr/axonic/jf/dao/implementations/MapJustificationSystemsDAO.java)**: stores the pairs in a hashmap.
* **[FileJustificationSystem](src/main/java/fr/axonic/jf/dao/implementations/FileJustificationSystemsDAO.java)**: stores the pairs in the host file system.
* **[MongoJustificationSystemsDAO](src/main/java/fr/axonic/jf/dao/implementations/MongoJustificationSystemsDAO.java)**: stores the pairs in a MongoDB database.

#### MongoJustificationSystemsDAO

This one is parameterizable in order to choose the MongoDB location, the database and collection names.
These parameters may be found in the environment variables.

They are:

| Parameter meaning      | Environment variable | Default value             |
|------------------------|----------------------|---------------------------|
| MongoDB location       | jsDatabaseUrl        | mongodb://localhost:27017 |
| Name of the database   | jsDatabaseName       | jf                        |
| Name of the collection | jsDatabaseCollection | justificationSystems      |

## How to extend the list of DAOs?

You would add a class which implement *fr.axonic.jf.dao.JustificationSystemsDAO* to the *fr.axonic.jf.dao.implementations* package.

The DAO is meant to be injected in the webservices so keep a constructor without argument (use the environment variables and default values if needed).

Do no forget to test your code! :wink:
