#!groovy

pipeline {
    agent {
        // Here we define that we wish to run on the agent with the label SL202_win
        label "SL202_win"
    }
    agent none  stages {
        stage('Maven Install') {
            agent {
                docker {
                    image 'maven:3.9.4'
                }
            }
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build -t itsrd/epay:latest .'
            }
        }
        stage('Docker Push') {
            agent SL202_win
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerhubPassword', usernameVariable: 'dockerhubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push itsrd/epay:latest'
                }
            }
        }
    }
}