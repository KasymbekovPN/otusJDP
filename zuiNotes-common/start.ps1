& mvn clean
Write-Host("### clean ###")
& mvn package
Write-Host("### package ###")
& mvn install
Write-Host("### install ###")