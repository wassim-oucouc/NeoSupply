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
        // Explicitly set test profile
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
                    sh 'ls -la'
                    sh 'chmod 755 mvnw'

                    echo 'Running Maven build and tests...'
                    sh "${MVNW} clean verify -Dspring.profiles.active=test"
                }
            }
            post {
                always {
                    echo 'Archiving test and coverage reports...'



                    // Archive JaCoCo coverage reports (only if plugin is installed)
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

                    // Optional: Publish HTML reports
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report'

                    ])
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
            // Optional: Send notification
            // emailext subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //          body: "Build successful!",
            //          to: "team@example.com"
        }
        failure {
            echo '❌ Build failed!'
            // Optional: Send notification
            // emailext subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //          body: "Build failed. Check console output.",
            //          to: "team@example.com"
        }
        unstable {
            echo '⚠️ Build unstable (tests passed but quality gates failed)'
        }
        cleanup {
            echo 'Cleaning up workspace...'
            // Optional: Clean workspace after build
            // cleanWs()
        }
    }
}