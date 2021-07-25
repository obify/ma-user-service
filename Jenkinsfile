pipeline {
    agent any
    stages {
        stage('git repo & clean') {
            steps {
                //bat "rmdir  /s /q ma-user-service"
                bat "git clone https://github.com/obify/ma-user-service.git"
            }
        }
        stage('test') {
            steps {
                bat "mvn test -f spring-data-jpa-crud"
            }
        }
        stage('SonarQube Analysis') {
                    steps {
                        script{
                            withSonarQubeEnv('SonarQubeServer'){
                                bat "mvn clean install"
                                bat "mvn sonar:sonar"
                            }
                            timeout(time: 1, unit: 'HOURS'){
                            def qg = waitForQualityGate()
                                if(qg.status != 'OK'){
                                    error "Pipeline aborted quality gate failure: ${qg.status}"
                                }
                            }
                            bat "mvn clean install"
                        }
                    }
        }
        stage('package') {
            steps {
                bat "mvn package -f spring-data-jpa-crud"
            }
        }
    }
}