# Selenium Test Automation Framework with Docker

This project provides a robust and scalable Selenium test automation framework using Docker containers. It's designed to run UI tests in a containerized environment with Chrome browser support.

## 🚀 Features

- Containerized test execution environment
- Chrome browser and ChromeDriver auto-configuration
- Support for Java 17 and Maven
- VNC support for live test monitoring
- Volume mapping for test reports
- Xvfb for headless browser execution
- Automated dependency management

## 📋 Prerequisites

- Docker (version 20.10 or higher)
- Docker Compose (version 2.0 or higher)
- Git

## 🛠️ Project Structure

```
├── Dockerfile
├── docker-compose.yml
├── docker-entrypoint.sh
├── pom.xml
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── test
│   │               
│   └── test
│   │   └── java
│   │        └── com
│   │            └── test
│                   
└── target
    └── reports
```

## 🔧 Setup and Configuration

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <project-directory>
```

### 2. Configure Test Environment

The Docker environment is pre-configured with:
- Ubuntu 22.04 base image
- Java 17 (OpenJDK)
- Latest stable Chrome browser
- Latest compatible ChromeDriver
- Xvfb for virtual display
- VNC for remote viewing

### 3. Test File Naming Convention

Ensure your test files follow these naming patterns:
- `*Test.java` - For regular test classes
- `*Runner.java` - For Cucumber test runners

## 🚀 Running Tests

### Using Docker Compose

1. Start the container:
```bash
docker-compose up --build
```

2. Run tests:
```bash
docker-compose run selenium-tests mvn test
```

### Using Docker Directly

1. Build the image:
```bash
docker build -t selenium-tests .
```

2. Run tests:
```bash
docker run --shm-size=2g selenium-tests
```

## 🔍 Monitoring Tests

### VNC Connection

The framework supports live test execution monitoring through VNC:

1. Connect to VNC server:
```bash
VNC Address: localhost:6000
```

2. Use any VNC viewer to connect and watch the test execution

## 📊 Test Reports

Test reports are generated in the `/app/target/reports` directory and are accessible through volume mapping:

- Surefire Reports: `target/surefire-reports`
- Custom Reports: `target/reports`

## 🔧 Troubleshooting

### Common Issues and Solutions

1. **No Tests Found**
   - Verify test file naming convention
   - Check if tests are in the correct directory
   - Ensure proper Maven configuration in `pom.xml`

2. **Browser Launch Issues**
   - Verify Chrome and ChromeDriver versions match
   - Check Xvfb is running correctly
   - Ensure sufficient shared memory with `--shm-size=2g`

3. **VNC Connection Issues**
   - Verify port 6000 is exposed
   - Check if x11vnc process is running
   - Ensure no firewall blocking

## 🛠️ Configuration Files

### Dockerfile

The Dockerfile is structured in three stages:
1. Base image with common dependencies
2. Build stage for compiling the project
3. Final runtime environment

### docker-entrypoint.sh

Contains startup scripts for:
- Initializing Xvfb
- Starting VNC server
- Executing tests

### docker-compose.yml

```yaml
version: '3'
services:
  selenium-tests:
    build: .
    shm_size: '2gb'
    ports:
      - "6000:6000"
    volumes:
      - ./target/reports:/app/target/reports
```

## 📝 Best Practices

1. **Test Organization**
   - Follow page object model
   - Maintain test data separately
   - Use proper test categories/groups

2. **Resource Management**
   - Clean up test data after execution
   - Properly close browser instances
   - Handle timeouts appropriately

3. **Docker Best Practices**
   - Use multi-stage builds
   - Minimize layer size
   - Implement proper cleanup

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details

## 📬 Support

For support and questions, please create an issue in the repository or contact the maintainers.

---

Remember to replace `<repository-url>` and `<project-directory>` with your actual values.
