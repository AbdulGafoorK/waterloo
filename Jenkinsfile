pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script{
                def mvnHome = tool 'Maven 3.5.2'
                if (isUnix()) {
                   def targetVersion = getDevVersion()
                   print 'target build version...'
                   print targetVersion
                   sh "'${mvnHome}/bin/mvn' -DskipTests=true -Dbuild.number=${targetVersion} clean package"
                   def pom = readMavenPom file: 'pom.xml'
                   // get the current development version
                   developmentArtifactVersion = "${pom.version}-${targetVersion}"
                   print pom.version
                 } else {
                   bat(/"${mvnHome}\bin\mvn" -DskipTests=true clean package/)
                   def pom = readMavenPom file: 'pom.xml'
                   print pom.version
                 }
               }
            }
        }
    }
    stage('Development deploy approval and deployment') {
         steps {
             script {
                if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
                    timeout(time: 3, unit: 'MINUTES') {
                       input message: 'Approve deployment?'
                    }
                    timeout(time: 2, unit: 'MINUTES') {
                       if (developmentArtifactVersion != null && !developmentArtifactVersion.isEmpty()) {
                           // replace it with your application name or make it easily loaded from pom.xml
                           def jarName = "waterloo-${developmentArtifactVersion}.jar"
                           echo "the application is deploying ${jarName}"
                           // NOTE : CREATE your deployemnt JOB, where it can take parameters whoch is the jar name to fetch from jenkins workspace
                           build job: 'ApplicationToDev', parameters: [[$class: 'StringParameterValue', name: 'jarName', value: jarName]]
                           echo 'the application is deployed !'
                           } else {
                             error 'the application is not  deployed as development version is null!'
                           }
                    }
                }
             }
         }
     }
}
