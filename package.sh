cd blossom-backend/backend/src/main/resources/static/editor
rm -rf assets/
rm favicon.png
rm index.html
cd ../../../../../../../blossom-editor
npm install
npm run build
cp -r out/renderer/assets/ ../blossom-backend/backend/src/main/resources/static/editor/
cp out/renderer/favicon.png ../blossom-backend/backend/src/main/resources/static/editor/
cp out/renderer/index.html ../blossom-backend/backend/src/main/resources/static/editor/
cd ../blossom-web
npm install
npm run build:spring
cd ../blossom-backend
mvn clean
mvn package
cd ..
docker rmi saintvamp/blossom:latest
docker build -t saintvamp/blossom:latest -f docker/build/Dockerfile .
docker push saintvamp/blossom:latest



docker stop blossom
docker rm blossom
docker run -d   --name=blossom   -p 46678:9999   -v /volume2/docker/blossom/:/home/bl/   saintvamp/blossom:latest   --spring.datasource.url="jdbc:mysql://?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=GMT%2B8"   --spring.datasource.username=sv   --spring.datasource.password=
