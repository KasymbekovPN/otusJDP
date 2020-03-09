$target = 'target'
$pwd = pwd
$arr = $pwd -split '\\'
$end = $arr.length - 1
$directory = $arr[$end]
if ($directory -eq $target){
    Set-Location '..'
}
& mvn clean
& mvn package
Set-Location -Path target
Write-Host("### EXEC ###")

& java -jar zuiNotes-messageSystem-1.0-SNAPSHOT.jar --ms.port=8091