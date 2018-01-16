#!/usr/bin/Rscript

install.packages("RMySQL", repos= "http://cran.us.r-project.org")
library(RMySQL)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="")
values <- dbGetQuery(con,
"SELECT c.country as format, count(c.genre) as freq
FROM movies m
INNER JOIN country_movie c ON c.movie_id=m.id
INNER JOIN movie_genre g ON g.movie_id=m.id
WHERE c.country LIKE \"%FRANCE%\" OR c.country LIKE \"%USA\"
ORDER BY g.genre;")

png(filename="genre-fr-usa.jpg")
barplot(values$freq, names.arg = values$format, horiz=FALSE, cex.names=1)
dev.off()

