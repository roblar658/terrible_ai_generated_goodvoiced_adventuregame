Add-Type -AssemblyName System.Speech

function Generate-VoiceLine($voiceName, $rate, $text, $outputPath) {
    Write-Host "Synthesizing English [$voiceName | Rate: $rate] -> $outputPath"
    $synth = New-Object System.Speech.Synthesis.SpeechSynthesizer
    try {
        if ($voiceName) {
            $synth.SelectVoice($voiceName)
        }
        $synth.Rate = $rate
        $synth.Volume = 100
        $synth.SetOutputToWaveFile($outputPath)
        $synth.Speak($text)
    }
    catch {
        Write-Warning "Failed with $voiceName, fallback: $_"
        $fallback = New-Object System.Speech.Synthesis.SpeechSynthesizer
        $fallback.SetOutputToWaveFile($outputPath)
        $fallback.Speak($text)
        $fallback.Dispose()
    }
    finally {
        $synth.Dispose()
    }
}

# Check installed English voices
$installed = [System.Speech.Synthesis.SpeechSynthesizer]::new().GetInstalledVoices() | ForEach-Object { $_.VoiceInfo.Name }
Write-Host "Installed voices: $($installed -join ', ')"

$voiceMale = "Microsoft David Desktop"
$voiceFemale = "Microsoft Zira Desktop"

if (-not ($installed -contains $voiceMale)) { $voiceMale = $installed[0] }
if (-not ($installed -contains $voiceFemale)) { $voiceFemale = $installed[0] }

Write-Host "Using English voices: Male=$voiceMale, Female=$voiceFemale"

$lines = @(
    # Old Torvald (Gruff, weathered old fisherman)
    @{ voice = $voiceMale; rate = -2; file = "assets/audio/voices/torvald_01.wav"; 
       text = "Expected, you say? Nobody is expected on Raven Island anymore, lad. Not since the light turned that cursed shade of blue. Astrid has locked herself in her tower for three weeks straight now." },
    @{ voice = $voiceMale; rate = -2; file = "assets/audio/voices/torvald_02.wav"; 
       text = "That iron gate stays locked until I see you've got some sense in your skull. Fetch my brass lantern from the flooded boathouse down below, and maybe we'll talk about keys." },
    @{ voice = $voiceMale; rate = -2; file = "assets/audio/voices/torvald_03.wav"; 
       text = "Well look at that. You actually got it. Here's the key with the raven sigil. But tread carefully, lad. The spire sings in the dead of night, and the stone trembles beneath my boots." },
    
    # UNIT-7 (Automaton: steady, rhythmic, artificial cadence)
    @{ voice = $voiceMale; rate = 1; file = "assets/audio/voices/unit7_01.wav"; 
       text = "Protocol Seven initialized. Internal system anomaly in sector four. Entity unidentified. You are not Creator Ravn. State your designation and purpose." },
    @{ voice = $voiceMale; rate = 0; file = "assets/audio/voices/unit7_02.wav"; 
       text = "Creator Ravn is sealed inside the celestial observatory. Access restricted by Security Protocol Omega. Listen to me: this spire was never engineered to guide mortal ships... it was erected to hold the Deep asleep." },
    @{ voice = $voiceMale; rate = 0; file = "assets/audio/voices/unit7_03.wav"; 
       text = "Harmonic resonance confirmed. Acoustic tuning fork transferred. Perimeter portal to the sea cliff is now unlocked. Walk with caution." },
    
    # Professor Astrid Ravn (Brilliant, driven, exhausted, grave)
    @{ voice = $voiceFemale; rate = 0; file = "assets/audio/voices/astrid_01.wav"; 
       text = "Eivind... you actually came. I knew deep down you could never resist an unsolved equation." },
    @{ voice = $voiceFemale; rate = -1; file = "assets/audio/voices/astrid_02.wav"; 
       text = "Look at the meters, Eivind. We have unlocked the true geothermal pulse of the Earth. But if we sever the circuit abruptly, the harmonic backlash will tear this island and the coastal villages apart." },
    @{ voice = $voiceFemale; rate = -1; file = "assets/audio/voices/astrid_03.wav"; 
       text = "We need equilibrium. I cannot shoulder this final judgment alone... I need your clarity, Eivind. Which path will you choose?" },
    
    # Eivind Holst (Reflective young scholar & mechanist)
    @{ voice = $voiceMale; rate = 0; file = "assets/audio/voices/eivind_01.wav"; 
       text = "The sea fog is unnaturally dense tonight. And that spire beacon... it's pulsing with a strange, impossible azure hum." },
    @{ voice = $voiceMale; rate = 0; file = "assets/audio/voices/eivind_02.wav"; 
       text = "Astrid's journals describe delicate harmonic ratios. And this antique clock stopped precisely at three seventeen." },
    @{ voice = $voiceMale; rate = 0; file = "assets/audio/voices/eivind_03.wav"; 
       text = "The subterranean crystals... they vibrate at four hundred and thirty-two Hertz. It's the ancient resonance of the bedrock itself." }
)

foreach ($item in $lines) {
    Generate-VoiceLine $item.voice $item.rate $item.text $item.file
}

Write-Host "Natural English voice lines generated successfully!"
