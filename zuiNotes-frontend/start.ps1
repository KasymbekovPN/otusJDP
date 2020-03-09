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

& java -jar zuiNotes-frontend-1.0-SNAPSHOT.jar --self.port=8081 --ms.host=192.168.0.100 --ms.port=8091 --target.host=192.168.0.100 --target.port=8101 --server.port=8080