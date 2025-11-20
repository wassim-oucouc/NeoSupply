pipeline {
    agent any

    tools {
        jdk 'jdk_17'
        maven 'maven 3.9.11'
    }

    environment {
        MVNW = './mvnw'
        SPRING_PROFILES_ACTIVE = 'test'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    echo 'Making Maven Wrapper executable...'
            sh 'chmod 755 ./mvnw'
            sh "./mvnw clean verify -Dspring.profiles.active=test -X"
                }
            }
            post {
                always {
                    echo 'Archiving test and coverage reports...'
                    script {
                        try {
                            jacoco execPattern: '**/target/jacoco.exec',
                                   classPattern: '**/target/classes',
                                   sourcePattern: '**/src/main/java',
                                   exclusionPattern: '**/dto/**,**/entity/**,**/config/**,**/mapper/**'
                        } catch (Exception e) {
                            echo "JaCoCo plugin not installed or report generation failed: ${e.message}"
                        }
                    }


                }
            }
        }
        stage('sonarQube')
        {
        steps
        {
          withCredentials([string(credentialsId: 'sonarToken', variable: 'sonarToken')]) {
           withSonarQubeEnv('SonarQubeServer') {
                 sh '''
                                   mvn sonar:sonar \
                                   -Dsonar.projectKey=NeoSupply \
                                   -Dsonar.projectName=NeoSupply \
                                   -Dsonar.java.coveragePlugin=jacoco \
                                   -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                                   -Dsonar.junit.reportPaths=target/surefire-reports \
                                   -Dsonar.sources=src/main/java \
                                   -Dsonar.tests=src/test/java \
                                   -Dsonar.java.binaries=target/classes \
                                   -Dsonar.java.test.binaries=target/test-classes
                               '''
                               }
                            }
                               }


        stage('Code Quality Check') {
            steps {
                echo 'Checking code quality metrics...'
                script {
                    try {
                        sh "${MVNW} jacoco:check -Dspring.profiles.active=test"
                    } catch (Exception e) {
                        echo "Code coverage check failed or skipped: ${e.message}"
                        // Don't fail the build for coverage checks (optional)
                        // unstable(message: "Code coverage below threshold")
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build and tests succeeded!'
        }
        failure {
            echo '❌ Build failed!'

        }
        unstable {
            echo '⚠️ Build unstable (tests passed but quality gates failed)'
        }
        cleanup {
            echo 'Cleaning up workspace...'

        }
    }
}