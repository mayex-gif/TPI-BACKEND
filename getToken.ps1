# Este script de PowerShell obtiene un token JWT de Keycloak
# usando el flujo "Direct Access Grants" (password flow).

# --- CONFIGURACIÓN DE KEYCLOAK ---
$KeycloakHost = "http://localhost:8180/realms/logistica-realm/protocol/openid-connect"
$ClientId = "logistica-client"
$ClientSecret = "logistica-secret-123" # Debe coincidir con el secret en logistica-realm.json

# --- CREDENCIALES DEL USUARIO ---
$Username = "cliente1"
$Password = "password123"

Write-Host "Intentando obtener el token para el usuario: $Username y cliente: $ClientId"
Write-Host "URL: $($KeycloakHost)/token"

# Construcción del cuerpo de la solicitud (form-urlencoded)
$Body = @{
    grant_type    = "password"
    client_id     = $ClientId
    client_secret = $ClientSecret
    username      = $Username
    password      = $Password
    scope         = "openid profile email"
}

try {
    # Solicitud HTTP utilizando Invoke-RestMethod (IRM)
    # IRM maneja automáticamente la respuesta JSON si es exitosa (código 200).
    $Response = Invoke-RestMethod -Uri "$($KeycloakHost)/token" `
        -Method Post `
        -ContentType "application/x-www-form-urlencoded" `
        -Body $Body

    # --- ÉXITO (HTTP 200) ---
    Write-Host "--------------------------------------------------"
    Write-Host "¡Token obtenido con éxito!" -ForegroundColor Green

    $AccessToken = $Response.access_token
    if ($AccessToken) {
        Write-Host "Access Token (truncado): $($AccessToken.Substring(0, 40))..."

        # Guardar el token en un archivo
        $AccessToken | Out-File -FilePath "access_token.txt" -Encoding UTF8
        Write-Host "Token guardado en 'access_token.txt'" -ForegroundColor Cyan
    } else {
        Write-Host "Error: Access Token no encontrado en la respuesta JSON." -ForegroundColor Red
    }

} catch {
    # --- FALLO (Códigos HTTP 4xx/5xx) ---
    Write-Host "--------------------------------------------------"
    Write-Host "Error al obtener el token. Detalles:" -ForegroundColor Red

    # Intenta obtener el cuerpo de la respuesta de error si está disponible
    $ErrorBody = $_.ErrorDetails.Message | ConvertFrom-Json -ErrorAction SilentlyContinue

    if ($ErrorBody -and $ErrorBody.error_description) {
        Write-Host "Error: $($ErrorBody.error)"
        Write-Host "Descripción: $($ErrorBody.error_description)"
    } else {
        # Si no se pudo parsear el JSON de error (como en el error 500 de fábrica)
        Write-Host "Error inesperado o interno de Keycloak." -ForegroundColor Yellow
        Write-Host $_.Exception.Message
    }
}