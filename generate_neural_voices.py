import asyncio
import os
import edge_tts

VOICE_LINES = [
    {
        "id": "torvald_intro",
        "voice": "en-US-RogerNeural",  # Gritty, salty, expressive elderly harbormaster
        "rate": "+0%",
        "pitch": "+0Hz",
        "text": "Expected, you say?! Nobody is expected on Raven Island anymore, lad! Not since the light turned that cursed shade of blue... Astrid has locked herself in her tower for three weeks straight now!"
    },
    {
        "id": "torvald_gate_locked",
        "voice": "en-US-RogerNeural",
        "rate": "+0%",
        "pitch": "+0Hz",
        "text": "That iron gate stays locked until I see you've got some sense in your skull! Fetch my brass lantern from the flooded boathouse down below, and maybe... just maybe, we'll talk about keys."
    },
    {
        "id": "torvald_telegram",
        "voice": "en-US-RogerNeural",
        "rate": "+0%",
        "pitch": "+0Hz",
        "text": "A telegram, eh? Well, Astrid knows plenty of formulas, but her machines sing in the dead of night. It ain't natural music, I tell ya! Go check the boathouse first."
    },
    {
        "id": "torvald_unlock_reward",
        "voice": "en-US-RogerNeural",
        "rate": "+0%",
        "pitch": "+0Hz",
        "text": "Well, look at that! You actually brought it back. Here - take the key with the raven sigil. But tread carefully, lad! The spire sings in the dead of night, and the stone trembles right beneath my boots!"
    },
    {
        "id": "unit7_intro",
        "voice": "en-US-GuyNeural",  # Precise, mechanical automaton with slight pacing
        "rate": "+3%",
        "pitch": "-6Hz",
        "text": "Protocol Seven initialized. Internal system anomaly in sector four. Entity unidentified. You are not Creator Ravn. State your designation and purpose."
    },
    {
        "id": "unit7_lore",
        "voice": "en-US-GuyNeural",
        "rate": "+2%",
        "pitch": "-4Hz",
        "text": "Creator Ravn is sealed inside the celestial observatory. Access restricted by Security Protocol Omega. Listen to me: this spire was never engineered to guide mortal ships... it was erected to hold the Deep asleep."
    },
    {
        "id": "unit7_tuning_fork",
        "voice": "en-US-GuyNeural",
        "rate": "+2%",
        "pitch": "-4Hz",
        "text": "Harmonic resonance confirmed. Acoustic tuning fork transferred. Perimeter portal to the sea cliff is now unlocked. Walk with caution."
    },
    {
        "id": "astrid_intro",
        "voice": "en-GB-SoniaNeural",  # Intellectual, distinguished female polymath
        "rate": "-2%",
        "pitch": "-2Hz",
        "text": "Eivind... you actually came. I knew deep down you could never resist an unsolved equation."
    },
    {
        "id": "astrid_dilemma",
        "voice": "en-GB-SoniaNeural",
        "rate": "-3%",
        "pitch": "-2Hz",
        "text": "Look at the meters, Eivind. We have unlocked the true geothermal pulse of the Earth. But if we sever the circuit abruptly, the harmonic backlash will tear this island and the coastal villages apart."
    },
    {
        "id": "astrid_choice",
        "voice": "en-GB-SoniaNeural",
        "rate": "-4%",
        "pitch": "-3Hz",
        "text": "We need equilibrium. I cannot shoulder this final judgment alone... I need your clarity, Eivind. Which path will you choose?"
    },
    {
        "id": "eivind_pier",
        "voice": "en-US-BrianNeural",  # Young thoughtful scholar protagonist
        "rate": "-1%",
        "pitch": "+0Hz",
        "text": "The sea fog is unnaturally dense tonight. And that spire beacon... it's pulsing with a strange, impossible azure hum."
    },
    {
        "id": "eivind_cottage",
        "voice": "en-US-BrianNeural",
        "rate": "-1%",
        "pitch": "+0Hz",
        "text": "Astrid's journals describe delicate harmonic ratios. And this antique clock stopped precisely at three seventeen."
    },
    {
        "id": "eivind_crystals",
        "voice": "en-US-BrianNeural",
        "rate": "-2%",
        "pitch": "-1Hz",
        "text": "The subterranean crystals... they vibrate at four hundred and thirty-two Hertz. It's the ancient resonance of the bedrock itself."
    },
    {
        "id": "eivind_wrong_item",
        "voice": "en-US-BrianNeural",
        "rate": "-1%",
        "pitch": "+0Hz",
        "text": "That item does not fit here. I must seek another solution."
    },
    {
        "id": "torvald_ending",
        "voice": "en-US-RogerNeural",
        "rate": "+0%",
        "pitch": "+0Hz",
        "text": "Look at the water, lad! The howling has stopped. The sea is peaceful again."
    }
]

async def generate_all():
    target_dir = "assets/audio/voices"
    os.makedirs(target_dir, exist_ok=True)
    
    print(f"Generating {len(VOICE_LINES)} high-fidelity neural voice lines...")
    for item in VOICE_LINES:
        filename = f"{item['id']}.mp3"
        filepath = os.path.join(target_dir, filename)
        print(f"Generating [{item['voice']}] -> {filepath} ...")
        tts = edge_tts.Communicate(
            text=item['text'],
            voice=item['voice'],
            rate=item['rate'],
            pitch=item['pitch']
        )
        await tts.save(filepath)
    print("ALL NEURAL VOICE LINES GENERATED SUCCESSFULLY!")

if __name__ == "__main__":
    asyncio.run(generate_all())
