# Define the cars vector with 5 values
cars <- c(1, 3, 6, 4, 9)

png(filename=paste(getwd(), "build/resources/main/test.png", sep="/"))

# Graph the cars vector with all defaults
plot(cars)

dev.off()
