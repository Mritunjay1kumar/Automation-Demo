pipeline{

    agent any

    stages{
        stage('BUild Jar'){

            steps{
                sh "mvn clean package -DskipTests"
            }

        }
        stage('Build Image'){

            steps{
                sh "docker build -t=mj9docker/praticeautomation:latest ."
            }
        }
        stage('Push Image'){

            steps{
                echo "docker push mj9docker/praticeautomation:latest"
            }
        }
    }

}