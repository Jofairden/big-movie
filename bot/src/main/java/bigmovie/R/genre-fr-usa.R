#!/usr/bin/Rscript

install.packages("RMySQL", repos= "http://cran.us.r-project.org")
library(RMySQL)

con <- dbConnect(MySQL(), dbname="database", user="root", password="")
values <- dbGetQuery(con, "select name as format, count(*) as freq from movies")

invisible(jpeg('/tmp/genre-fr-usa.jpg'))
barplot(values$freq, names.arg = values$format, horiz=FALSE, cex.names=0.5)
invisible(dev.off())

