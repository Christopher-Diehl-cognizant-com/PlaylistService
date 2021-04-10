docker network create --driver bridge playlistnetwork

docker run -d -p 5432:5432 -e POSTGRES_USER=cognizant -e POSTGRES_PASSWORD=galvanize -e POSTGRES_DB=playlistdb --network playlistnetwork postgres

docker build -t playlist-image .

docker run -d -p 8081:8080 -e SPRING_PROFILE_ACTIVE=dockerlocal --network playlistnetwork --name playlistservice playlist-image

