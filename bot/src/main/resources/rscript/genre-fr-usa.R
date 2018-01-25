#!/usr/bin/Rscript

# @author Jildert

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
HAVING g.genre LIKE '%action%'
OR g.genre LIKE '%war%'
OR g.genre LIKE '%crime%'
OR g.genre LIKE '%horror%'
OR g.genre LIKE '%thriller%';")

valuesUSA <- dbGetQuery(con,
"SELECT count(m.title) as  `freq`
FROM movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN genre_movie gm ON gm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
INNER JOIN genres g ON g.id = gm.genre_id
WHERE c.country LIKE '%usa%'
GROUP BY c.country, g.genre
HAVING g.genre LIKE '%action%'
OR g.genre LIKE '%war%'
OR g.genre LIKE '%crime%'
OR g.genre LIKE '%horror%'
OR g.genre LIKE '%thriller%';")

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

usaPercentage = colSums(valuesUSA) / colSums(totalUSA) * 100
francePercentage = colSums(valuesFrance) /  colSums(totalFrance) * 100

# open filewrite
png(filename=paste(getwd(), "build/resources/main/genre-fr-usa.png", sep="/"))

plotVal <- cbind(usaPercentage,francePercentage)

barplot(plotVal, main="Procent of violence in movies", ylim=c(0,25), beside=TRUE, names.arg=c("USA", "France"), col=c("red","darkblue"))

dev.off()
