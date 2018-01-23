#!/usr/bin/Rscript
pacman::p_load(RMySQL)
pacman::p_load(readr)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")

countryString <- read_file("build/resources/main/country.txt")
countryString <- substr(countryString,1,nchar(countryString)-2)
print(countryString)

queryString <- "SELECT c.country, m.release_year, COUNT(*) AS number_movies
FROM movies m
INNER JOIN  country_movie cm ON cm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
GROUP BY c.country, m.release_year
HAVING c.country LIKE '%"

combinedString <- paste(queryString, countryString, sep = "")
combinedString <- paste(combinedString, "%';", sep = "")

values <- dbGetQuery(con, combinedString)
values <- values[(values$release_year > 0),]

aggregate(values$number_movies ~ values$release_year, data=values, sum)

values <- aggregate(values$number_movies ~ values$release_year, data=values, sum)

png(filename=paste(getwd(), "build/resources/main/vraag8.png", sep="/"))

par(col="blue")
heading = paste("type=", "h")
plot(values$`values$release_year`, values$`values$number_movies`,
xlab= "Year", ylab= "Total", type="h" )

title(main=countryString, col.main="black", font.main=4)
box()
dev.off()