# EXTRACT STAGE: use Spring Boot layertools to explode the runnable jar into layers
FROM eclipse-temurin:21-jre AS extractor
WORKDIR /extracted
COPY /target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# RUNTIME STAGE: assemble the final image using the extracted layers
FROM eclipse-temurin:21-jre-ubi10-minimal AS runtime
# Create an unprivileged system user
RUN groupadd --system appgroup && useradd --system --gid appgroup --shell /sbin/nologin appuser
USER appuser

WORKDIR /application
# Copy extracted layers into the runtime image.
COPY --from=extractor --chown=appuser:appgroup /extracted/dependencies ./
COPY --from=extractor --chown=appuser:appgroup /extracted/spring-boot-loader ./
COPY --from=extractor --chown=appuser:appgroup /extracted/snapshot-dependencies ./
COPY --from=extractor --chown=appuser:appgroup /extracted/application ./

# Start the layered Spring Boot app using the loader
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]