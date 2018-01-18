# add packages if not present
pacman::p_load(RMySQL)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")

values <- dbGetQuery(con,"SELECT c.country, m.release_year, COUNT(*) AS number_movies
                     FROM country_movie cm
                     INNER JOIN movies m ON m.id = cm.movie_id
                     INNER JOIN countries c ON cm.country_id = c.id
                     GROUP BY c.country, m.release_year
                     HAVING c.country LIKE '%germany%';")

values <- values[(values$releaseYear > 0),]

aggregate(values$number_movies ~ values$releaseYear, data=values, sum)

values <- aggregate(values$number_movies ~ values$releaseYear, data=values, sum)

png(filename=paste(getwd(), "build/resources/main/Vraag8.png", sep="/"))

par(col="blue")
heading = paste("type=", "h")
plot(values$`values$releaseYear`, values$`values$number_movies`,
     xlab= "Jaar", ylab= "Total", type="h", xlim=c(1,100), ylim=c(1,100))


title(main="Land", col.main="black", font.main=4)
box()
dev.off()
