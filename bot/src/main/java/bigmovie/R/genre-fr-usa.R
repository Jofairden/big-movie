#!/usr/bin/Rscript

install.packages("RMySQL", repos= "http://cran.us.r-project.org")
library(RMySQL)

con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")
values <- dbGetQuery(con, "select title as format, count(*) as freq from movies")

png(filename="genre-fr-usa.jpg")
barplot(values$freq, names.arg = values$format, horiz=FALSE, cex.names=0.5)
dev.off()

