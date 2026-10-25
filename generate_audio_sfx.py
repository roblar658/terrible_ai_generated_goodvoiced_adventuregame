import wave
import math
import struct
import os

os.makedirs("assets/audio/sfx", exist_ok=True)
os.makedirs("assets/audio/music", exist_ok=True)

def write_wav(filename, samples, sample_rate=44100):
    with wave.open(filename, 'w') as wf:
        wf.setnchannels(1)
        wf.setsampwidth(2)
        wf.setframerate(sample_rate)
        # pack as signed 16-bit integers
        packed = b''.join(struct.pack('<h', max(-32767, min(32767, int(s * 32767)))) for s in samples)
        wf.writeframes(packed)
    print(f"Created audio: {filename} ({len(samples)/sample_rate:.2f}s)")

rate = 44100

# 1. UI Click: crisp short wood/switch click (0.05s)
duration = 0.06
samples = []
for i in range(int(rate * duration)):
    t = i / rate
    decay = math.exp(-t * 80)
    val = (math.sin(2 * math.pi * 1200 * t) * 0.4 + math.sin(2 * math.pi * 300 * t) * 0.6) * decay
    samples.append(val)
write_wav("assets/audio/sfx/click.wav", samples, rate)

# 2. Item Pickup: pleasant brassy ding (0.4s)
duration = 0.4
samples = []
for i in range(int(rate * duration)):
    t = i / rate
    decay = math.exp(-t * 7)
    val = (math.sin(2 * math.pi * 880 * t) * 0.5 + math.sin(2 * math.pi * 1760 * t) * 0.3 + math.sin(2 * math.pi * 2640 * t) * 0.2) * decay
    samples.append(val)
write_wav("assets/audio/sfx/pickup.wav", samples, rate)

# 3. Door Open / Heavy latch: low resonant thud & creak (0.6s)
duration = 0.6
samples = []
for i in range(int(rate * duration)):
    t = i / rate
    decay = math.exp(-t * 5)
    freq = 140 - t * 40
    val = (math.sin(2 * math.pi * freq * t) * 0.7 + math.sin(2 * math.pi * (freq * 2.1) * t) * 0.3) * decay
    samples.append(val)
write_wav("assets/audio/sfx/door.wav", samples, rate)

# 4. Crystal chime / Mystery resonance (1.2s)
duration = 1.2
samples = []
for i in range(int(rate * duration)):
    t = i / rate
    decay = math.exp(-t * 3.5)
    # 432 Hz Pythagorean resonant chime
    val = (math.sin(2 * math.pi * 432 * t) * 0.4 +
           math.sin(2 * math.pi * 864 * t) * 0.3 +
           math.sin(2 * math.pi * 1296 * t) * 0.2 +
           math.sin(2 * math.pi * 1728 * t) * 0.1) * decay
    samples.append(val)
write_wav("assets/audio/sfx/crystal.wav", samples, rate)

# 5. Ambient Music / Atmospheric Drone (8 seconds loopable)
duration = 8.0
samples = []
for i in range(int(rate * duration)):
    t = i / rate
    # Smooth loop crossfade envelope
    env = math.sin(math.pi * t / duration)
    # Slow undulating sea wind & low alchemical crystal resonance
    mod1 = math.sin(2 * math.pi * 0.2 * t)
    mod2 = math.sin(2 * math.pi * 0.13 * t)
    base = (math.sin(2 * math.pi * (55.0 + mod1 * 2) * t) * 0.35 +
            math.sin(2 * math.pi * (110.0 + mod2 * 3) * t) * 0.25 +
            math.sin(2 * math.pi * 216.0 * t) * (0.15 + 0.05 * mod1) +
            math.sin(2 * math.pi * 432.0 * t) * (0.08 + 0.04 * mod2))
    # Soft pink-ish noise for sea wind
    noise = ((i * 1103515245 + 12345) % 65536) / 32768.0 - 1.0
    val = (base + noise * 0.03) * (0.7 + 0.3 * env)
    samples.append(val * 0.4)
write_wav("assets/audio/music/ambient.wav", samples, rate)

print("All sound effects and ambient music generated!")
