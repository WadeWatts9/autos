# Crear directorio para Maven
$mavenDir = "$env:USERPROFILE\apache-maven"
New-Item -ItemType Directory -Force -Path $mavenDir

# Descargar Maven
$mavenVersion = "3.9.6"
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
$zipFile = "$env:TEMP\maven.zip"

Write-Host "Descargando Maven $mavenVersion..."
Invoke-WebRequest -Uri $mavenUrl -OutFile $zipFile

# Extraer Maven
Write-Host "Extrayendo Maven..."
Expand-Archive -Path $zipFile -DestinationPath $mavenDir -Force

# Configurar variables de entorno
$mavenHome = "$mavenDir\apache-maven-$mavenVersion"
$path = [Environment]::GetEnvironmentVariable("Path", "User")
if ($path -notlike "*$mavenHome\bin*") {
    [Environment]::SetEnvironmentVariable("Path", "$path;$mavenHome\bin", "User")
}
[Environment]::SetEnvironmentVariable("M2_HOME", $mavenHome, "User")
[Environment]::SetEnvironmentVariable("MAVEN_HOME", $mavenHome, "User")

# Limpiar archivo temporal
Remove-Item $zipFile

Write-Host "Maven $mavenVersion ha sido instalado correctamente."
Write-Host "Por favor, reinicia tu terminal para que los cambios surtan efecto." 