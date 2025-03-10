pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-11'
        }
    }

    environment {
        SELENIUM_GRID_URL = "http://192.168.1.112:4444"
    }

    stages {
        stage('Install Dependencies') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Selenium Tests') {
            steps {
                // Exécution des tests avec Selenium
                sh 'mvn test -Dselenium.grid.url=$SELENIUM_GRID_URL'
            }
        }

        stage('Generate Allure Report') {
            steps {
                // Génération du rapport Allure
                sh 'mvn allure:report'
            }
        }

        stage('Publish Allure Report') {
            steps {
                // Publication du rapport Allure
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            // Archiver les rapports Surefire
            archiveArtifacts artifacts: '**/target/surefire-reports/', fingerprint: true
        }

        success {
            echo '✅ Les tests Selenium ont réussi.'
        }

        failure {
            echo "❌ Les tests Selenium ont échoué."
        }

        unstable {
            echo '⚠️ Les tests sont instables.'
        }
    }

    // Intégration du rapport Cucumber
    post {
        always {
            script {
                cucumber buildStatus: 'UNSTABLE',
                    failedFeaturesNumber: 1,
                    failedScenariosNumber: 1,
                    skippedStepsNumber: 1,
                    failedStepsNumber: 1,
                    classifications: [
                        [key: 'Commit', value: 'Commit ID non disponible'],
                        [key: 'Submitter', value: 'Nom du soumetteur non disponible']
                    ],
                    reportTitle: 'Cucumber Report',
                    fileIncludePattern: 'reports/cucumber-report.json',
                    sortingMethod: 'ALPHABETICAL',
                    trendsLimit: 100
            }
            echo 'Tests terminés. Vérifiez la sortie console pour les détails !'
        }
    }
}
