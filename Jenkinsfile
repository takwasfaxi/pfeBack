pipeline {
    agent any
    environment {
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "localhost:8081/"
        NEXUS_CREDENTIAL_ID = "nexus"
        DOCKER_REGISTRY_CREDENTIALS = "dockerhub"
        GIT_CREDENTIALS = "github"
        GIT_URL = "https://github.com/takwasfaxi/pfeBack.git"
        GIT_BRANCH = "main"
    }
    stages {
        stage('Checkout Code') {
            steps {
                cleanWs()
                git branch: env.GIT_BRANCH,
                    credentialsId: env.GIT_CREDENTIALS,
                    url: env.GIT_URL
            }
        }
        stage('Build All Services') {
            parallel {
                stage('Build Discovery Service') {
                    steps {
                        buildService(
                            serviceDir: 'Discovery_Service',
                            nexusRepo: 'Repo-Discovery',
                            sonarCreds: 'sonardiscovery',
                            sonarProjectKey: 'Backenddiscovery',
                            dockerImageName: 'discoveryservice',
                            dockerHubRepo: 'takwaa123/discoveryservice'
                        )
                    }
                }
                stage('Build Gateway Service') {
                    steps {
                        buildService(
                            serviceDir: 'Gateway_Service',
                            nexusRepo: 'Repo-Gateway',
                            sonarCreds: 'sonargateway',
                            sonarProjectKey: 'Backendgateway',
                            dockerImageName: 'gatewayservice',
                            dockerHubRepo: 'takwaa123/gatewayservice'
                        )
                    }
                }
                stage('Build Task Service') {
                    steps {
                        buildService(
                            serviceDir: 'Task_Service',
                            nexusRepo: 'Repo-Task',
                            sonarCreds: 'sonartask',
                            sonarProjectKey: 'BackendTask',
                            dockerImageName: 'taskservice',
                            dockerHubRepo: 'takwaa123/taskservice'
                        )
                    }
                }
                stage('Build User Service') {
                    steps {
                        buildService(
                            serviceDir: 'User_Service',
                            nexusRepo: 'User-Repo',
                            sonarCreds: 'sonaruser',
                            sonarProjectKey: 'BackenUser',
                            dockerImageName: 'userservice',
                            dockerHubRepo: 'takwaa123/userservice'
                        )
                    }
                }
                stage('Build Project Service') {
                    steps {
                        buildService(
                            serviceDir: 'Project_Service',
                            nexusRepo: 'Project-Repo',
                            sonarCreds: 'sonar',
                            sonarProjectKey: 'BackenService',
                            dockerImageName: 'projetservice',
                            dockerHubRepo: 'takwaa123/projetservice'
                        )
                    }
                }
            }
        }
        stage('Deploy All Services') {
            steps {
                echo "All services built and pushed to Docker Hub successfully"
            }
        }
    }
}
def buildService(Map config) {
    dir(config.serviceDir) {
        pipeline {
            agent any
            environment {
                NEXUS_VERSION = "nexus3"
                NEXUS_REPOSITORY = config.nexusRepo
            }
            stages {
                stage('Compile') {
                    steps {
                        sh "mvn compile"
                    }
                }
                stage('Unit Test') {
                    steps {
                        sh "mvn test -DskipTests=true"
                    }
                }
                stage('Sonar Analysis') {
                    steps {
                        withSonarQubeEnv(credentialsId: config.sonarCreds, installationName: 'sonar') {
                            sh "mvn sonar:sonar -Dsonar.projectKey=${config.sonarProjectKey}"
                        }
                    }
                }
                stage('Integration Test') {
                    steps {
                        sh "mvn verify -DskipTests=true"
                    }
                }
                stage('Build') {
                    steps {
                        sh "mvn clean install -DskipTests=true"
                    }
                }
                stage('Upload to Nexus') {
                    steps {
                        script {
                            pom = readMavenPom file: "pom.xml"
                            filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
                            echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                            artifactPath = filesByGlob[0].path
                            artifactExists = fileExists artifactPath
                            if(artifactExists) {
                                echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"
                                nexusArtifactUploader(
                                    nexusVersion: env.NEXUS_VERSION,
                                    protocol: env.NEXUS_PROTOCOL,
                                    nexusUrl: env.NEXUS_URL,
                                    groupId: pom.groupId,
                                    version: pom.version,
                                    repository: env.NEXUS_REPOSITORY,
                                    credentialsId: env.NEXUS_CREDENTIAL_ID,
                                    artifacts: [
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: "pom.xml",
                                        type: "pom"]
                                    ]
                                )
                            } else {
                                error "*** File: ${artifactPath}, could not be found"
                            }
                        }
                    }
                }
                stage('Build Docker Image') {
                    steps {
                        sh "docker build -t ${config.dockerImageName}:latest ."
                    }
                }
                stage('Push to Docker Hub') {
                    steps {
                        script {
                            withDockerRegistry([credentialsId: env.DOCKER_REGISTRY_CREDENTIALS, url: ""]) {
                                sh "docker tag ${config.dockerImageName}:latest ${config.dockerHubRepo}:latest"
                                sh "docker push ${config.dockerHubRepo}:latest"
                            }
                        }
                    }
                }
            }
        }
    }
}
