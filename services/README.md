To prepare service for containerization go into the folder so you are one lvl above the src folder.
- delete the target folder if it exists (it will be generated again in the next step)
- Run $mvn clean install, this will created the target folder with a new jar file.
- $docker build / or docker-compose.
- ($docker run)