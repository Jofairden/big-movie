#!/usr/bin/Rscript

# add packages if not present
pacman::p_load(RMySQL)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")
valuesFrance <- dbGetQuery(con,
"SELECT count(m.title) as  `freq`
FROM movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN genre_movie gm ON gm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
INNER JOIN genres g ON g.id = gm.genre_id
WHERE c.country LIKE '%france%'
GROUP BY c.country, g.genre
HAVING g.genre LIKE '%action%';")
valuesUSA <- dbGetQuery(con,
"SELECT count(m.title) as  `freq`
FROM movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN genre_movie gm ON gm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
INNER JOIN genres g ON g.id = gm.genre_id
WHERE c.country LIKE '%usa%'
GROUP BY c.country, g.genre
HAVING g.genre LIKE '%action%';")

totalUSA <- dbGetQuery(con,
"select count(m.title) as count
from movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id AND c.country LIKE '%USA%';")
totalFrance <- dbGetQuery(con,
"select count(m.title) as count
from movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id AND c.country LIKE '%france%';")

usaPercentage = valuesUSA / totalUSA * 100
francePercentage = valuesFrance / totalUSA * 100

# open filewrite
png(filename=paste(getwd(), "build/resources/main/genre-fr-usa.png", sep="/"))

plotVal <- cbind(usaPercentage$freq,francePercentage$freq)

barplot(plotVal, main="Procent of violence movies", beside=TRUE, names.arg=c("USA", "France"), col=c("red","darkblue"))

dev.off()
