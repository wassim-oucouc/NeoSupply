pipeline {
    agent any

    tools {
        // Utilise le Maven Wrapper
        jdk 'jdk_17'
        maven 'maven 3.9.11'
    }

    environment {
        // Mettre le chemin si nécessaire, sinon Jenkins utilisera ./mvnw
        MVNW = './mvnw'
    }

    stages {
        stage('Checkout') {
            steps {
            echo 'checkout'
            checkout scm
              //  git branch: 'main', url: 'https://github.com/wassim-oucouc/NeoSupply.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh "${MVNW} clean verify"
            }
            post {
                always {
                    // Archive les rapports JUnit et JaCoCo
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java'
                }
            }
        }
    }

    post {
        success {
            echo 'Build et tests réussis ✅'
        }
        failure {
            echo 'Le build a échoué ❌'

        }
    }
}
