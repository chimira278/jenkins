#!/usr/bin/env groovy

void alpine(Map params = [:], body) {
    def defaultParams = [
        name: 'alpine',
        image: 'alpine:3.16',
        requestsCpu: '125m',
        requestsMem: '256Mi',
        limitsCpu: '250m',
        limitsMem: '512Mi',
    ]

    final Map finalParams = defaultParams << params

    def yaml = libraryResource('buildPod/alpine.yaml').
        replace("#CONTAINER_NAME", finalParams.name).
        replace("#CONTAINER_IMAGE", finalParams.image).
        replace("#REQUESTS_CPU", finalParams.requestsCpu).
        replace("#REQUESTS_MEM", finalParams.requestsMem).
        replace("#LIMITS_CPU", finalParams.limitsCpu).
        replace("#LIMITS_MEM", finalParams.limitsMem)

    podTemplate(
        name: finalParams.name,
        yaml: yaml,
        serviceAccount: 'jenkins'
    ) {
        node(POD_LABEL) {
            body()
            def postAction = post.tasks()
        }
    }
}

void docker(Map params = [:], body) {
    def defaultParams = [
        name: 'docker',
        image: 'docker:20.10.17-dind-alpine3.16',
        requestsCpu: '250m',
        requestsMem: '256Mi',
        limitsCpu: '250m',
        limitsMem: '512Mi',
    ]

    final Map finalParams = defaultParams << params

    def yaml = libraryResource('buildPod/docker.yaml').
        replace("#CONTAINER_NAME", finalParams.name).
        replace("#CONTAINER_IMAGE", finalParams.image).
        replace("#REQUESTS_CPU", finalParams.requestsCpu).
        replace("#REQUESTS_MEM", finalParams.requestsMem).
        replace("#LIMITS_CPU", finalParams.limitsCpu).
        replace("#LIMITS_MEM", finalParams.limitsMem)

    podTemplate(
        name: finalParams.name,
        yaml: yaml,
        serviceAccount: 'jenkins'
    ) {
        node(POD_LABEL) {
            body()
            def postAction = post.tasks()
        }
    }
}

void python(Map params = [:], body) {
    def defaultParams = [
        name: 'python',
        image: 'python:3.12.3-alpine3.19',
        requestsCpu: '125m',
        requestsMem: '128Mi',
        limitsCpu: '1000m',
        limitsMem: '1Gi',
    ]

    final Map finalParams = defaultParams << params

    def yaml = libraryResource('buildPod/python.yaml').
        replace("#CONTAINER_NAME", finalParams.name).
        replace("#CONTAINER_IMAGE", finalParams.image).
        replace("#REQUESTS_CPU", finalParams.requestsCpu).
        replace("#REQUESTS_MEM", finalParams.requestsMem).
        replace("#LIMITS_CPU", finalParams.limitsCpu).
        replace("#LIMITS_MEM", finalParams.limitsMem)

    podTemplate(
        name: finalParams.containerName,
        yaml: yaml,
        serviceAccount: 'jenkins'
    ) {
        node(POD_LABEL) {
            body()
            def postAction = post.tasks()
        }
    }
}

void nodejs(Map params = [:], body) {
    def defaultParams = [
        name: 'nodejs',
        image: 'node:20.13.0-alpine3.19',
        requestsCpu: '125m',
        requestsMem: '128Mi',
        limitsCpu: '1000m',
        limitsMem: '1Gi',
    ]

    final Map finalParams = defaultParams << params

    def yaml = libraryResource('buildPod/nodejs.yaml').
        replace("#CONTAINER_NAME", finalParams.name).
        replace("#CONTAINER_IMAGE", finalParams.image).
        replace("#REQUESTS_CPU", finalParams.requestsCpu).
        replace("#REQUESTS_MEM", finalParams.requestsMem).
        replace("#LIMITS_CPU", finalParams.limitsCpu).
        replace("#LIMITS_MEM", finalParams.limitsMem)

    podTemplate(
        name: finalParams.containerName,
        yaml: yaml,
        serviceAccount: 'jenkins'
    ) {
        node(POD_LABEL) {
            body()
            def postAction = post.tasks()
        }
    }
}

void groovy(Map params = [:], body) {
    def defaultParams = [
        name: 'groovy',
        image: 'groovy:4.0.20-jdk21-alpine',
        requestsCpu: '500m',
        requestsMem: '512Mi',
        limitsCpu: '1000m',
        limitsMem: '1Gi',
    ]

    final Map finalParams = defaultParams << params

    def yaml = libraryResource('buildPod/groovy.yaml').
        replace("#CONTAINER_NAME", finalParams.name).
        replace("#CONTAINER_IMAGE", finalParams.image).
        replace("#REQUESTS_CPU", finalParams.requestsCpu).
        replace("#REQUESTS_MEM", finalParams.requestsMem).
        replace("#LIMITS_CPU", finalParams.limitsCpu).
        replace("#LIMITS_MEM", finalParams.limitsMem)

    podTemplate(
        name: finalParams.containerName,
        yaml: yaml,
        serviceAccount: 'jenkins'
    ) {
        node(POD_LABEL) {
            body()
            def postAction = post.tasks()
        }
    }
}