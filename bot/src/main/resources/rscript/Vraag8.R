#!/usr/bin/Rscript
# @author Fadi
pacman::p_load(RMySQL)
pacman::p_load(readr)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")
# Maak een database connectie aan

countryString <- read_file("build/resources/main/country.txt")
countryString <- substr(countryString,1,nchar(countryString)-2)
print(countryString)
# haal de meegegeven gegevens op uit het txt bestand

queryString <- "SELECT c.country, m.release_year, COUNT(*) AS number_movies
FROM movies m
INNER JOIN  country_movie cm ON cm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
GROUP BY c.country, m.release_year
HAVING c.country LIKE '%"
# Haal de gegevens op uit de database

combinedString <- paste(queryString, countryString, sep = "")
combinedString <- paste(combinedString, "%';", sep = "")

values <- dbGetQuery(con, combinedString)
values <- values[(values$release_year > 0),]
# Haal alle vraagtekens eruit


values <- aggregate(values$number_movies ~ values$release_year, data=values, sum)
#Voeg dezelfde jaartallen bij elkaar op

png(filename=paste(getwd(), "build/resources/main/vraag8.png", sep="/"))
# Zet de plot in een png

par(col="blue")
heading = paste("type=", "h")
plot(values$`values$release_year`, values$`values$number_movies`,
xlab= "Year", ylab= "Total", type="h" )

title(main=countryString, col.main="black", font.main=4)
box()
# Maak de plot aan
dev.off()