pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'module-5', url: 'https://github.com/ChyrkunArtsiom/esm'

                bat "echo %cd%"

                bat "mvn --version"

                // Run Maven
                bat "mvn clean package"
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('SonarQube Server') {
                    bat "mvn sonar:sonar"
                }
            }
        }
        stage("Quality gate") {
            options {
                timeout(time: 20, unit: 'SECONDS')
            }
            steps {
                    waitForQualityGate abortPipeline: true
            }
        }

        stage('Deploy') {
            steps {
                //Deploy to local Tomcat
                deploy adapters: [tomcat9(credentialsId: 'tomcat-scripter', path: '', url: 'http://localhost:8080/')], contextPath: 'esm', onFailure: false, war: 'web/target/*.war'
            }
        }
    }
}