# CSE 816 Final Project: Stock Portfolio Manager
## Complete Demo Script & Workflow Guide

Use this document as your step-by-step guide to present your final project to Mr. Yuvraj Deshmukh or your instructor.

---

### 1. The Introduction (Spoken Script)

*"Hello Mr. Yuvraj, welcome to our final presentation for CSE 816. Today we are presenting our project: **The Stock Portfolio Manager**.* 

*Our application is a full-stack Java Spring Boot web app that allows users to securely register, manage isolated stock portfolios, and track live market prices using the Finnhub API. However, the core focus of our project is the robust DevOps pipeline we built to support it.*

*We have successfully implemented every requirement from the project guidelines: we containerized our application with Docker, orchestrated CI/CD with Jenkins, automated deployments and secret management using Ansible, integrated Kubernetes with Horizontal Pod Autoscaling (HPA), and set up centralized logging using the ELK stack."*

---

### 2. Preparation (Before the Demo Starts)

Have these browser tabs open and ready:
*   **Tab 1 - Application:** `http://localhost:8081` (or your NodePort)
*   **Tab 2 - Jenkins:** `http://localhost:8080`
*   **Tab 3 - Kibana (ELK):** `http://localhost:5601`
*   **Tab 4 - Docker Hub:** Your repository page (showing the pushed images)
*   **Tab 5 - GitHub:** Your project repository

Have your Terminal ready with multiple tabs:
*   **Terminal 1:** Inside the project directory (ready for Git commands).
*   **Terminal 2:** Ready for Docker commands (`docker ps`).
*   **Terminal 3:** Ready for Kubernetes commands (`kubectl`).

---

### 3. The Complete Demo Workflow (Step-by-Step)

#### Step 1: The CI/CD Trigger (Showcasing Git & Jenkins)
*   **Action:** Make a small, visible change in the code (e.g., change a heading in `src/main/resources/templates/holdings/list.html`).
*   **Terminal 1:** Run the git commands to commit and push:
    ```bash
    git add .
    git commit -m "Update holding page title for demo"
    git push origin main
    ```
*   **Browser (Jenkins):** Switch to your Jenkins tab and show the pipeline triggering automatically (or click "Build Now").

#### Step 2: The Jenkins Pipeline Flow
*   **Action:** While Jenkins is running, walk the instructor through the `Jenkinsfile` stages.
*   **Script:** *"Our Jenkins pipeline is fully declarative. It executes 6 stages: It clones the repository, runs our JUnit tests, packages the app using Maven, builds the Docker image, securely pushes that image to Docker Hub using stored credentials, and finally triggers Ansible to deploy the new image."*

#### Step 3: Docker & Containerization
*   **Browser (Docker Hub):** Show that the new image tag (matching the Jenkins build number) has just been pushed to Docker Hub.
*   **Terminal 2:** Show Docker running locally.
    ```bash
    docker images
    docker ps
    ```
*   **Script:** *"As you can see, our Spring Boot application and MySQL database are both running in isolated Docker containers, fulfilling the containerization requirement."*

#### Step 4: Ansible Deployment Automation
*   **Action:** Open `ansible/playbooks/deploy.yml` and `ansible/vars/secrets.yml` in your IDE.
*   **Script:** *"Jenkins triggers this Ansible playbook. Ansible handles the continuous delivery (CD) phase. It automatically pulls the latest Docker image, restarts the containers, and applies our Kubernetes manifests. Furthermore, we use Ansible Vault templates to securely pass credentials like our Finnhub API Key and Database passwords, ensuring no secrets are hardcoded in the repository."*

#### Step 5: Kubernetes & Auto-scaling (HPA)
*   **Terminal 3:** Prove that Kubernetes is orchestrating the app.
    ```bash
    kubectl get pods
    kubectl get services
    kubectl get hpa
    ```
*   **Script:** *"We deployed the application to our Kubernetes cluster. Here you can see our Pods and the NodePort service. Most importantly, we configured a Horizontal Pod Autoscaler (HPA). You can see the target CPU utilization is set to 70%. If our application experiences a spike in traffic, Kubernetes will automatically scale it from 2 up to 5 pods to handle the load."*

#### Step 6: Application Feature Walkthrough
*   **Browser (Application - Tab 1):** Go to the live app.
*   **Action:** 
    1. Register a new user and log in. Mention that auth uses stateless JWT cookies.
    2. Create a new portfolio.
    3. Click into the portfolio and add a stock (e.g., `AAPL`, Quantity: `10`, Price: `150`).
*   **Script:** *"Now looking at the actual application, you can see the user's portfolio. Notice how the 'Current Price' and 'Total Value' are populated. The Spring Boot backend is actively fetching real-time market data from the Finnhub API. The UI also features client-side validation to prevent bad data from crashing the server."*

#### Step 7: ELK Stack Logging & Monitoring
*   **Browser (Kibana - Tab 3):** Go to the Kibana Discover tab.
*   **Action:** Show the latest logs coming in.
*   **Script:** *"Finally, for the logging requirement, we deployed the ELK stack. Logstash reads our Spring Boot log files from a mounted Docker volume. We wrote a custom Logstash Grok filter to parse the Spring logs and tag them by activity (e.g., 'auth_activity' or 'error'). Elasticsearch indexes these logs, and here in Kibana, we can search, filter, and monitor the real-time health and usage of our application."*

---

### 4. Conclusion
*"This completes our end-to-end DevOps pipeline. A simple git push automatically tests the code, builds the container, scales the infrastructure via Kubernetes, and monitors it via ELK. Thank you, are there any questions?"*
