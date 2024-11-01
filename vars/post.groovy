#!/usr/bin/env groovy

def tasks() {
    logstashSend failBuild: true, maxLines: 5000
    slack.notify currentBuild.currentResult
    cleanWs()
}