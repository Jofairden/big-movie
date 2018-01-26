# Big movie
___
Bot Aiden was made for project Big Movie, a college project.
Aiden has acces to a database of roughly ~2GB, made with data subsets from Imdb.
The bot features the use of `Rivescript`, `Jbdc`, `RScript`, and much more. Aiden uses `Gradle`.
Aiden used log4j for logging. These will go in `/logs/`
# Running Aiden
___
## Requirements
 * Database (MySQL pref.) We used MariaDB
 * At least Java 8
 * IntellIJ
 * Gradle
 * RScript and/or Rstudio
 * A discord bot user

## Importing the database
Open your database in your prefered client, we used `MariaDB`. Next, navigate to `Project` and run the `sqlschema.sql` within the zip. After that, run the `sqldump.sql` in the other zip. Please note that the import can take a long time! If the dump is not available here, you can get them from here: `http://51.15.38.77/bigmovie/sqlschema.zip` and `http://51.15.38.77/bigmovie/sqldump.zip` If you imported the database, you can skip the next steps to create the database.

### Creating the database yourself
If you want to make the database yourself, you need the data subsets from Imdb. Go to their [interfaces page](http://www.imdb.com/interfaces/) and learn to get their files from there. Note: you can not use the newer dumps! Once you have access to the files, you only need to grab these:
* actors.list.gz
* actresses.list.gz
* countries.list.gz
* genres.list.gz
* language.list.gz
* locations.list.gz
* movies.list.gz
* ratings.list.gz
* running-times.list.gz
* soundtracks.list.gz

If you cannot get to these files, try here: `http://51.15.38.77/bigmovie/raw.zip`

You can unzip these with applications like 7zip and peazip.
Once you have these files, make the following folders in in `/imdbparser/`: `/data/` and then `/data/raw/`
Your final path should be accessible: `~/imdbparser/data/raw/`

### Running the parser
Now open IntelliJ, and open or import the imdbparser as a project. Simply run the application and wait. Note that the parsing can take a very long time. If something fails right off the bat, make a `/parsed/` directory in your `/data/` directory. 

## Setup
First, open the project in IntellIJ. To do this, hit the import button. Make sure to import as external model and select Gradle project, and make sure to tick `Use auto-import`. Hit Finish. Note, that you should use `java version 1.8.0_152` or at least `java 8`. Go to `File->Settings`, and type `Gradle` in the search bar. Now, under `Build, Execution, Deployment`, select `Build Tools`, then `Gradle` and then `Runner`. Make sure to tick `Delegate IDE build/run actions to gradle` and hit apply.

Next up, navigate to `~/bot/src/main/resources/` and create a file named `BotConfig.json` with the following contents:
```json
{
  "DB_HOST": "127.0.0.1",
  "DB_COL": "bigmovie",
  "DB_USR": "root",
  "DB_PW": "root",
  "DB_PORT": "3306",
  "TOKEN": "token",
  "RScript_PATH": "C:\\Program Files\\R\\R-3.4.2\\bin\\Rscript.exe"
}
```
Change the `TOKEN` field to your bot token, and the `RScript_Path` to your Rscript.exe. Of course, change any settings to your liking.

Next, hit `CTRL` + `F9` to build or do this via the Gradle toolbox. After this, try `CTRL` + `SHIFT` + `F10` to create a main entry. To see available commands, inspect the rivescript files in the resources folder.

