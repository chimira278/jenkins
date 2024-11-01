#!/usr/bin/env groovy

import groovy.json.JsonOutput

def notify(String buildStatus, String channel = '#jenkins') {
    try {
        buildStatus = buildStatus ?: 'SUCCESS'
        channel = channel ?: '#jenkins'

        def subject = "${buildStatus}: Job '${env.JOB_BASE_NAME} [${env.BUILD_NUMBER}] (<${env.RUN_DISPLAY_URL}|Open>) (<${env.RUN_CHANGES_DISPLAY_URL}|  Changes>)'"
        def title = "${env.JOB_NAME} Build: ${env.BUILD_NUMBER}"
        def title_link = "${env.RUN_DISPLAY_URL}"
        def branchName = "${env.BRANCH_NAME}"

        def commit = sh(returnStdout: true, script: 'git rev-parse HEAD')
        def author = sh(returnStdout: true, script: "git --no-pager show -s --format='%an' ${commit}").trim()
        def message = sh(returnStdout: true, script: 'git log -1 --pretty=%B').trim()

        if (buildStatus == 'SUCCESS') {
            colorCode = '#339CFF'
        } else if (buildStatus == 'ABORTED') {
            colorCode = '#A9A9A9'
        } else {
            colorCode = '#FF0000'
        }

        def attachments = [
            [
                title: title.toString(),
                title_link: title_link.toString(),
                text: subject.toString(),
                fields:[
                    [
                        title: 'Branch',
                        value: branchName.toString(),
                        short: true
                    ],
                    [
                        title: 'Author',
                        value: author.toString(),
                        short: true
                    ],
                    [
                        title: 'Commit Message',
                        value: message.toString(),
                        short: false
                    ]
                ]
            ]
        ]

        slackSend(color: colorCode, channel: channel, attachments: JsonOutput.toJson(attachments))
        currentBuild.result = 'SUCCESS'
    } catch (err) {
        echo "Caught: ${err}"
        currentBuild.result = 'FAILURE'
    }
}
