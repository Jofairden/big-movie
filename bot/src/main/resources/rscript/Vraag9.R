#!/usr/bin/Rscript
# @author Fadi
pacman::p_load(RMySQL)

genreList <- readLines("build/resources/main/genres.csv")
# Haal de gegevens op uit het door java gemaakte csv bestand

if (length(genreList) >= 1)
  {
  
  lineColors <- c("red", "purple", "green", "black","orange","blue","palegreen2",
                  "cornsilk4","deepskyblue1","deeppink2","lightcoral","orangered2",
                  "seagreen2")
  # Maakt een kleur array aan voor de lijnen
  
    if (length(genreList) < length(lineColors))
    {
    # Controlleer of er genoeg kleuren zijn
      
    con <- dbConnect(MySQL(), dbname="bigmovie", user="root", password="root")
    # Maak de database connectie aan
    
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
    # Haal de gegevens uit de database
    
    values <- values[(values$release_year > 0),]
    # Haal alle vraagtekens eruit
    
    
    minCount <- min(values$movies_count)
    maxCount <- max(values$movies_count)
    
    minYear = min(values$release_year)
    maxYear = max(values$release_year)
    
    scoreRange <- c(minCount:maxCount)
    yearRange <- c(minYear:maxYear)
    # Haal de min en max gegevens van de x en y as van de plot op
    
    png(filename=paste(getwd(), "build/resources/main/vraag9.png", sep="/"))
    # maak een png van de plot
    
    par(col="blue")
    heading = paste("type=", "l")
    plot(0, 0,
         xlab= "Year", ylab= "Total Movies", type="l", lwd=2,
         ylim=(range(scoreRange)), xlim=(range(yearRange)) ) 
    # Maak een lege plot aan
    
    
    for(i in 1:(length(genreList))) {
      genreInfo <- values[values$genre == genreList[i], ]
      lines(genreInfo$release_year, genreInfo$movies_count, type="l", pch=22, lty=1, col=lineColors[i], lwd=3)
    }
    # Maak alle lijnen voor de plot
    
    
    legend("topleft", inset = .05 , legend=genreList, title = "genre", col=lineColors, lty=1:1, cex=0.8)
    # maak de details venster rechts boven aan
    
    } else{
      print("U heeft te veel genres opgegeven, u kunt maximaal 13 genres opgeven")
      }
  
} else{
  print("U heeft geen genres opgegeven")
}

dev.off()

