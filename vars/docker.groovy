#!/usr/bin/env groovy

void build(String prefix, String dockerUrl, String dockerRegistry, String repoName, String newTag) {
    try {
        withCredentials([usernamePassword(credentialsId: "${prefix.toUpperCase()}_DOCKER_REPOSITORY", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh """
                docker build --no-cache -t \'${dockerUrl}\'/\'${repoName}\':\'${newTag}\' .
                if [ \$? -eq 0 ]; then
                    docker login --username ${USERNAME} --password ${PASSWORD} \'${dockerRegistry}\'
                    docker push \'${dockerUrl}\'/\'${repoName}\':\'${newTag}\'
                fi
            """
        }
        currentBuild.result = 'SUCCESS'
    } catch (err) {
        echo "Caught: ${err}"
        currentBuild.result = 'FAILURE'
    }
}

def getDockerUrl(String prefix) {
    if (prefix.toUpperCase() == 'REMOTE') {
        dockerRegistry = env.REMOTE_DOCKER_REGISTRY
        dockerNamespace = env.REMOTE_DOCKER_NAMESPACE
    } else if (prefix.toUpperCase() == 'LOCAL') {
        dockerRegistry = env.LOCAL_DOCKER_REGISTRY
        dockerNamespace = env.LOCAL_DOCKER_NAMESPACE
    }
    return url = "${dockerRegistry}/${dockerNamespace}"
}

void getRegistry(String prefix) {
    if (prefix.toUpperCase() == 'REMOTE') {
        dockerRegistry = env.REMOTE_DOCKER_REGISTRY
    } else if (prefix.toUpperCase() == 'LOCAL') {
        dockerRegistry = env.LOCAL_DOCKER_REGISTRY
    }
    return dockerRegistry
}
