#!/usr/bin/Rscript

install.packages("RMySQL", repos= "http://cran.us.r-project.org")
library(RMySQL)
con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")

values <- dbGetQuery(con,"SELECT c.country, m.release_year, COUNT(*) AS number_movies
FROM movies m
INNER JOIN  country_movie cm ON cm.movie_id = m.id
INNER JOIN countries c ON c.id = cm.country_id
GROUP BY c.country, m.release_year
HAVING c.country LIKE '%Germany%';")
values <- values[(values$release_year > 0),]

aggregate(values$number_movies ~ values$release_year, data=values, sum)

values <- aggregate(values$number_movies ~ values$release_year, data=values, sum)

png(filename="vraag8.jpg")

par(col="blue")
heading = paste("type=", "h")
plot(values$`values$release_year`, values$`values$number_movies`,
xlab= "Jaar", ylab= "Total", type="h" ) 


title(main="Land", col.main="black", font.main=4)
box()
dev.off()


