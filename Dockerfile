# Stage 1: Base image with common dependencies
FROM ubuntu:22.04 as base

# Prevent interactive prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive

# Install common dependencies
RUN apt-get update -y && apt-get install -y \
    wget xvfb unzip jq curl \
    x11vnc libxi6 libgconf-2-4 ffmpeg \
    libxss1 libappindicator1 libgconf-2-4 \
    fonts-liberation libasound2 libnspr4 libnss3 libx11-xcb1 \
    libxtst6 lsb-release xdg-utils libgbm1 libnss3 \
    libatk-bridge2.0-0 libgtk-3-0 libx11-xcb1 libxcb-dri3-0 \
    openjdk-17-jdk maven

# Setup Chrome and ChromeDriver
RUN curl -s https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json > /tmp/versions.json

RUN CHROME_URL=$(jq -r '.channels.Stable.downloads.chrome[] | select(.platform=="linux64") | .url' /tmp/versions.json) && \
    wget -q --continue -O /tmp/chrome-linux64.zip $CHROME_URL && \
    unzip /tmp/chrome-linux64.zip -d /opt/chrome && \
    chmod +x /opt/chrome/chrome-linux64/chrome

RUN CHROMEDRIVER_URL=$(jq -r '.channels.Stable.downloads.chromedriver[] | select(.platform=="linux64") | .url' /tmp/versions.json) && \
    wget -q --continue -O /tmp/chromedriver-linux64.zip $CHROMEDRIVER_URL && \
    unzip /tmp/chromedriver-linux64.zip -d /opt/chromedriver && \
    chmod +x /opt/chromedriver/chromedriver-linux64/chromedriver

# Set up environment variables
ENV CHROMEDRIVER_DIR=/opt/chromedriver/chromedriver-linux64
ENV PATH=$CHROMEDRIVER_DIR:$PATH
ENV DISPLAY=:99
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Create necessary directories
RUN mkdir -p /app/target/reports

# Stage 2: Build stage for Java project
FROM base as builder

WORKDIR /app

# Copy Maven configuration and source code
COPY pom.xml .
# Copy the complete project structure
COPY src ./src

# Build the project and run tests during build
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Stage 3: Final runtime
FROM base

WORKDIR /app

# Copy the entire target directory including test classes
COPY --from=builder /app/target ./target/
COPY --from=builder /app/src ./src/
COPY --from=builder /app/pom.xml ./

# Create volume for reports
VOLUME /app/target/reports

# Expose VNC port
EXPOSE 6000

# Copy test execution script
COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]