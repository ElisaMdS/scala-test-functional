# Stage 1: Build the application using SBT and Scala
FROM openjdk:19-alpine AS build

# Install SBT
RUN apk update && \
    apk add --no-cache bash curl && \
    curl -L -o sbt.tar.gz https://github.com/sbt/sbt/releases/download/v1.8.2/sbt-1.8.2.tgz && \
    tar -xzf sbt.tar.gz -C /opt && \
    rm sbt.tar.gz && \
    ln -s /opt/sbt/bin/sbt /usr/bin/sbt

# Set the working directory
WORKDIR /app

# Copy build.sbt and project folder with its content
COPY build.sbt .
COPY project ./project

# Copy the source code
COPY src ./src

# Run SBT assembly
RUN sbt assembly

# Stage 2: Create the final image with the JAR file
FROM openjdk:19-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/scala-3.2.2/root-assembly-0.1.0-SNAPSHOT.jar /app/root-assembly-0.1.0-SNAPSHOT.jar

# Set the entrypoint for the Scala application
CMD ["java", "-jar", "root-assembly-0.1.0-SNAPSHOT.jar", "2021-01-01 00:00:00", "2022-01-01 00:00:00"]
