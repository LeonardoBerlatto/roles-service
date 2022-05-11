IMAGE_NAME = "roles-api"

image:
	docker build -t ${IMAGE_NAME} . --platform linux/x86_64

package:
	mvn clean package


run-all: package image
	docker-compose up -d