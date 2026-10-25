Add-Type -AssemblyName System.Speech

function Generate-VoiceLine($voiceName, $text, $outputPath) {
    Write-Host "Synthesizing [$voiceName] -> $outputPath"
    $synth = New-Object System.Speech.Synthesis.SpeechSynthesizer
    try {
        if ($voiceName) {
            $synth.SelectVoice($voiceName)
        }
        $synth.Rate = 0
        $synth.Volume = 100
        $synth.SetOutputToWaveFile($outputPath)
        $synth.Speak($text)
    }
    catch {
        Write-Warning "Failed with $voiceName, falling back to default voice: $_"
        $fallback = New-Object System.Speech.Synthesis.SpeechSynthesizer
        $fallback.SetOutputToWaveFile($outputPath)
        $fallback.Speak($text)
        $fallback.Dispose()
    }
    finally {
        $synth.Dispose()
    }
}

# Voices available: Microsoft Jon (male Norwegian/Scandinavian), Microsoft Helle (female), Microsoft David (male English/robot), Microsoft Zira
$voiceMale = "Microsoft Jon"
$voiceFemale = "Microsoft Helle"
$voiceRobot = "Microsoft David"

# Check if voices exist, otherwise fallback
$installed = [System.Speech.Synthesis.SpeechSynthesizer]::new().GetInstalledVoices() | ForEach-Object { $_.VoiceInfo.Name }
if (-not ($installed -contains $voiceMale)) { $voiceMale = $installed[0] }
if (-not ($installed -contains $voiceFemale)) { $voiceFemale = $installed[1] }
if (-not ($installed -contains $voiceRobot)) { $voiceRobot = $installed[0] }

Write-Host "Using voices: Male=$voiceMale, Female=$voiceFemale, Robot=$voiceRobot"

$lines = @(
    @{ voice = $voiceMale; file = "assets/audio/voices/torvald_01.wav"; text = "Ventet, sier du? Ingen er ventet på Ravnoy lenger, gutt. Ikke etter at lyset skiftet farge. Astrid har stengt seg inne i tårnet i tre uker nå." },
    @{ voice = $voiceMale; file = "assets/audio/voices/torvald_02.wav"; text = "Porten forblir låst inntil jeg ser at du har forstand i hodet. Finn lykta mi i båthuset, så skal vi snakke om nøkler." },
    @{ voice = $voiceMale; file = "assets/audio/voices/torvald_03.wav"; text = "Her har du nøkkelen med ravnesymbolet. Men vær forsiktig. Tårnet synger om natten, og fjellet skjelver under føttene mine." },
    
    @{ voice = $voiceRobot; file = "assets/audio/voices/unit7_01.wav"; text = "Protokoll syv aktivert. Systemfeil i sektor fire. Du er ikke Skaper Ravn. Identifiser din hensikt." },
    @{ voice = $voiceRobot; file = "assets/audio/voices/unit7_02.wav"; text = "Skaper Ravn oppholder seg i observatoriet. Tilgang blokkert av Sikkerhetsprotokoll Omega. Tårnet ble aldri bygget for å lede skip... det ble bygget for å holde dypet i søvn." },
    @{ voice = $voiceRobot; file = "assets/audio/voices/unit7_03.wav"; text = "Harmonisk frekvens registrert. Stemmegaffel overført. Portalen til klippestien er nå åpen." },
    
    @{ voice = $voiceFemale; file = "assets/audio/voices/astrid_01.wav"; text = "Eivind... du kom faktisk. Jeg visste at du aldri kunne motstå en uløst ligning." },
    @{ voice = $voiceFemale; file = "assets/audio/voices/astrid_02.wav"; text = "Vi har oppdaget kilden til ubegrenset energi. Men hvis vi stenger ned brått nå, vil tilbakeslaget forårsake en eksplosjon som utsletter hele øya." },
    @{ voice = $voiceFemale; file = "assets/audio/voices/astrid_03.wav"; text = "Vi trenger en balanse. Jeg klarer ikke å ta valget alene... Jeg trenger ditt sinn, Eivind. Hva velger du?" },
    
    @{ voice = $voiceMale; file = "assets/audio/voices/eivind_01.wav"; text = "Tåken er usedvanlig tett her. Og det lyset fra fyrtårnet... det pulserer i en unaturlig asurblå frekvens." },
    @{ voice = $voiceMale; file = "assets/audio/voices/eivind_02.wav"; text = "Notatene til Astrid viser intrikate krystallresonanser. Klokken stoppet nøyaktig tre sytten." },
    @{ voice = $voiceMale; file = "assets/audio/voices/eivind_03.wav"; text = "Krystallene i dypet vibrerer ved firehundre og trettito hertz. Det er jordens egen klang." }
)

foreach ($item in $lines) {
    Generate-VoiceLine $item.voice $item.text $item.file
}

Write-Host "Voice lines generated successfully!"
