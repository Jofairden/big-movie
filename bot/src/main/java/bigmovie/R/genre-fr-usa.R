#!/usr/bin/Rscript

install.packages("RMySQL", repos= "http://cran.us.r-project.org")
library(RMySQL)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="")
values <- dbGetQuery(con, "select title as format, id as freq from movies limit 5")

png(filename="genre-fr-usa.jpg")
barplot(values$freq, names.arg = values$format, horiz=FALSE, cex.names=1)
dev.off()

