#!/usr/bin/env groovy

def pushTag(String dockerRegistry, String repoName, String newTag, String lastCommit) {
    try {
        if (currentBuild.result == 'SUCCESS') {
            withCredentials([gitUsernamePassword(credentialsId: 'GITHUB_TOKEN', gitToolName: 'git-tool')]) {
                sh """
                    sed -i "/name: ${dockerRegistry}.*${repoName}/{ n; s,newTag:.*,newTag: ${newTag},g }" kustomize/overlays/kustomization.yaml
                    sed -i "/name: PROJECT_VERSION/{ n; s/value:.*/value: ${newTag}/g }" kustomize/overlays/kustomization.yaml
                    sed -i "/repoURL:.*github.com.*${repoName}.git/{ n; s/targetRevision:.*/targetRevision: ${newTag}/g }" argocd/appprojectset.yaml
                    git config --global user.email "jay.amorin@gmail.com"
                    git config --global user.name "jayamorin"
                    git diff kustomize/overlays/kustomization.yaml argocd/appprojectset.yaml
                    git add kustomize/overlays/kustomization.yaml argocd/appprojectset.yaml
                    git commit -m "Updated image version to ${newTag}"
                    git push -u origin main
                    git tag -a \'${newTag}\' -m \'${lastCommit}\'
                    git push origin --tags
                """
            }
            currentBuild.result = 'SUCCESS'
        }
    } catch (err) {
        echo "Caught: ${err}"
        currentBuild.result = 'FAILURE'
    }
}

def newTag(String latestTag, String prefix) {
    try {
        if (latestTag == "none") {
            return "${prefix}1.0.0"
        } else {
            def versionParts = latestTag.replace(prefix, '').split(/\./)
            def major = versionParts[0] as int
            def minor = versionParts[1] as int
            def patch = versionParts[2] as int

            patch += 1

            return "${prefix}${major}.${minor}.${patch}"
        }
        currentBuild.result = 'SUCCESS'
    } catch (err) {
        echo "Caught: ${err}"
        currentBuild.result = 'FAILURE'
    }

}