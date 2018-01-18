# install pacman if required
# with pacman, we can easily install and load packages
if (!require("pacman"))
    install.packages("pacman", repos = "http://cran.us.r-project.org")
