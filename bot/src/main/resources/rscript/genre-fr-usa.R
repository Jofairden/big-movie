#!/usr/bin/Rscript

# add packages if not present
pacman::p_load(RMySQL)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")
values <- dbGetQuery(con,
                     "SELECT c.country as `format`, g.genre as `genre`, count(m.title) as  `freq`
FROM movies m
INNER JOIN country_movie cm ON cm.movie_id = m.id
INNER JOIN genre_movie gm ON gm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
INNER JOIN genres g ON g.id = gm.genre_id
WHERE c.country LIKE '%usa%' OR c.country LIKE '%france%'
GROUP BY c.country, g.genre;")


splitValues <- split(values, values$format)

# open filewrite
png(filename=paste(getwd(), "src/main/resources/genre-fr-usa.png", sep="/"), width=1500, height=1240)

par(mfrow=c(1,2))

slices <- splitValues$USA$freq
lbls <- splitValues$USA$genre
pct <- round(slices/sum(slices)*100)
lbls <- paste(lbls, pct) # add percents to labels 
lbls <- paste(lbls,"%",sep="") # ad % to labels 
pie(slices,labels = lbls, col=rainbow(length(lbls)),
    main="Pie Chart of Genres in USA", cex=1)

slices <- splitValues$France$freq 
lbls <- splitValues$France$genre
pct <- round(slices/sum(slices)*100)
lbls <- paste(lbls, pct) # add percents to labels 
lbls <- paste(lbls,"%",sep="") # ad % to labels 
pie(slices,labels = lbls, col=rainbow(length(lbls)),
    main="Pie Chart of Genres in France", cex=1)

dev.off()

