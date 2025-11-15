pipeline {
    agent any

    tools {
        // Specify JDK and Maven installations configured in Jenkins
        jdk 'jdk_17'
        maven 'maven 3.9.11'
    }

    environment {
        // Path to Maven Wrapper
        MVNW = './mvnw'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                // Use existing SCM configuration, so old commits remain
                checkout scm
                // If needed, you could also use explicit Git URL + branch
                // git branch: 'main', url: 'https://github.com/wassim-oucouc/NeoSupply.git', credentialsId: 'github-pat-neo'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Making Maven Wrapper executable...'
                sh 'chmod +x ./mvnw'

                echo 'Running Maven build and tests...'
                sh "${MVNW} clean verify"
            }
            post {
                always {
                    echo 'Archiving test and coverage reports...'
                    // Archive JUnit test reports
                    junit '**/target/surefire-reports/*.xml'
                    // Archive JaCoCo coverage reports
                    jacoco execPattern: '**/target/jacoco.exec',
                           classPattern: '**/target/classes',
                           sourcePattern: '**/src/main/java'
                }
            }
        }
    }

    post {
        success {
            echo 'Build and tests succeeded ✅'
        }
        failure {
            echo 'Build failed ❌'
        }
        cleanup {
            echo 'Cleaning up workspace...'
        }
    }
}
