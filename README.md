Postman Collection

https://documenter.getpostman.com/view/14782873/2s9Y5ctfZt

docker run --name es01 -p 9200:9200 -d -m 1GB -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.14

docker run --name es01 -p 9201:9200 -d -m 1GB docker.elastic.co/elasticsearch/elasticsearch:7.17.14


curl host.docker.internal:9200