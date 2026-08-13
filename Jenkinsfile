pipeline {
    agent any

    stages {
        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Image') {
            steps {
                sh 'docker build -t mj9docker/praticeautomation:latest .'
            }
        }

        stage('Push Image') {
            steps {
                withCredentials([
                        usernamePassword(
                                credentialsId: 'dockerhub-cred',
                                usernameVariable: 'DOCKER_USERNAME',
                                passwordVariable: 'DOCKER_PASSWORD'
                        )
                ]) {
                    sh '''
                echo "$DOCKER_PASSWORD" | docker login \
                    --username "$DOCKER_USERNAME" \
                    --password-stdin

                docker tag mj9docker/praticeautomation:latest \
                    mj9docker/praticeautomation:$BUILD_NUMBER

                docker push mj9docker/praticeautomation:latest
                docker push mj9docker/praticeautomation:$BUILD_NUMBER
            '''
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
    }
}