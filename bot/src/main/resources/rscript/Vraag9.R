#!/usr/bin/Rscript
pacman::p_load(RMySQL)

genreList <- readLines("build/resources/main/genres.csv")
print(genreList)

if (length(genreList) >= 1)
  {
  
  lineColors <- c("red", "purple", "green", "black","orange","blue","palegreen2",
                  "cornsilk4","deepskyblue1","deeppink2","lightcoral","orangered2",
                  "seagreen2")
  
    if (length(genreList) < length(lineColors))
    {
    
    con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")
    
    values <- dbGetQuery(con,"SELECT 
    g.genre, 
    m.release_year, 
    COUNT(g.genre) AS movies_count
    FROM movies m
      INNER JOIN genre_movie gm ON gm.movie_id = m.id
      INNER JOIN genres g ON g.id = gm.genre_id
      WHERE m.votes IS NOT NULL
    AND m.score IS NOT NULL
    GROUP BY g.genre, m.release_year;")
    
    values <- values[(values$release_year > 0),]
    
  
    
    
    minCount <- min(values$movies_count)
    maxCount <- max(values$movies_count)
    
    minYear = min(values$release_year)
    maxYear = max(values$release_year)
    
    scoreRange <- c(minCount:maxCount)
    yearRange <- c(minYear:maxYear)
    
    png(filename=paste(getwd(), "build/resources/main/vraag9.png", sep="/"))
    
    par(col="blue")
    heading = paste("type=", "l")
    plot(0, 0,
         xlab= "Year", ylab= "Total Movies", type="l", lwd=2,
         ylim=(range(scoreRange)), xlim=(range(yearRange)) ) 
    
    
    for(i in 1:(length(genreList))) {
      genreInfo <- values[values$genre == genreList[i], ]
      lines(genreInfo$release_year, genreInfo$movies_count, type="l", pch=22, lty=1, col=lineColors[i], lwd=3)
    }
    
    
    legend("topleft", inset = .05 , legend=genreList, title = "genre", col=lineColors, lty=1:1, cex=0.8)
    
    } else{
      print("U heeft te veel genres opgegeven, u kunt maximaal 13 genres opgeven")
      }
  
} else{
  print("U heeft geen genres opgegeven")
}

dev.off()

