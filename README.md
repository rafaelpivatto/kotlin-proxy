# Kotlin SOAP Proxy

🚀 **Description**

This project is a Kotlin application built with Ktor, a powerful web framework for creating web services in Kotlin. The project exposes a SOAP service that can forward SOAP requests to an external service and respond back with the XML response from the external service.

## 📝 **Table of Contents**

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running the Application](#running-the-application)
- [Configuration](#configuration)
- [How it Works](#how-it-works)
- [Contributing](#contributing)
- [License](#license)

## 🚀 **Getting Started**

### Prerequisites

Before running the application, you need to have the following tools installed on your machine:

- Docker

### Running the Application

To run the application, follow these steps:

1. Clone the project to your local machine.

2. Update the `application.conf` file to set the `baseUrl` property to the external service URL. Replace `"http://"` with the appropriate URL for the external service. Make sure the URL includes the appropriate path to the service.

   Example: `baseUrl = "http://another-site:8080"`

3. Build the Docker image using the following command:

```bash 
docker build -t project_name .
```

Replace `project_name` with the desired name for the Docker image.

4. Run the Docker container:

```bash 
docker run -p 9080:9080 project_name
```

Replace `project_name` with the same name used during the Docker image build.

5. The application should now be running and accessible at `http://localhost:9080`.

## ⚙️ **Configuration**

The application configuration is done through the `application.conf` file. This file contains the port on which the application will run and the `baseUrl` property, which specifies the URL of the external service to which the SOAP requests will be forwarded.

## 🛠️ **How it Works**

The Kotlin application uses Ktor to create a web server that listens for incoming SOAP requests on port 9080. When a SOAP request is received, the application extracts the path from the URL and forwards the SOAP request to the specified external service using OkHttp.

The external service processes the request and sends back the XML response, which is then returned as the response to the original SOAP request.

## 👏 **Contributing**

Contributions are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.

## 📄 **License**

This project is licensed under the [MIT License](LICENSE). Feel free to use and modify this project as needed.

---
