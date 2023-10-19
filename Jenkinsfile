#!groovy

pipeline {
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
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerhubPassword', usernameVariable: 'dockerhubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push itsrd/epay:latest'
                }
            }
        }
    }
}