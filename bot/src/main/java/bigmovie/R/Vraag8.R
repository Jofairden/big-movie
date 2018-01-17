#install.packages("RMySQL", repos= "http://cran.us.r-project.org")
library(RMySQL)
con <- dbConnect(MySQL(), dbname="bigmovieold", user="root", password="root")

values <- dbGetQuery(con,"SELECT cm.country, m.releaseYear, COUNT(*) AS number_movies
FROM country_movie cm
INNER JOIN movies m ON m.id = cm.movie_id
GROUP BY cm.country, m.releaseYear
HAVING cm.country LIKE '%Germany%';")
values <- values[(values$releaseYear > 0),]

aggregate(values$number_movies ~ values$releaseYear, data=values, sum)

values <- aggregate(values$number_movies ~ values$releaseYear, data=values, sum)

png(filename="Vraag8.jpg")

par(col="blue")
heading = paste("type=", "h")
plot(values$`values$releaseYear`, values$`values$number_movies`,
xlab= "Jaar", ylab= "Total", type="h" ) 


title(main="Land", col.main="black", font.main=4)
box()
dev.off()


