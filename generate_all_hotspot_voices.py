import asyncio
import os
import edge_tts

# Voice model for protagonist Eivind Holst (natural, intelligent young scholar)
VOICE = "en-US-BrianNeural"
RATE = "+0%"
PITCH = "+0Hz"

HOTSPOT_LINES = [
    # Scene 1: Pier
    {
        "id": "inspect_hotspot_pier_lantern",
        "text": "A heavy maritime storm lantern with brass reflectors and kerosene wick. Casts a warm circle of amber light."
    },
    {
        "id": "inspect_hotspot_pier_rope",
        "text": "Thick tarred maritime hawser. Faint flecks of azure piezoelectric crystal powder cling to the damp fibers."
    },
    {
        "id": "inspect_hotspot_pier_gate",
        "text": "A formidable wrought-iron gate sealed with a heavy brass padlock embossed with a spread-winged raven."
    },
    {
        "id": "inspect_hotspot_pier_gate_locked",
        "text": "A formidable wrought-iron gate sealed with a heavy brass padlock. I will need Torvald's key to unlock it."
    },
    {
        "id": "inspect_hotspot_to_boathouse",
        "text": "Slick granite steps descend into the sheltered sea cove where the old timber boathouse stands."
    },

    # Scene 2: Boathouse
    {
        "id": "inspect_hotspot_boathouse_wrench",
        "text": "A heavy English adjustable spanner. Essential for opening high-pressure steam and hydraulic valves."
    },
    {
        "id": "inspect_hotspot_boathouse_punchcard",
        "text": "A perforated parchment card strip inscribed: UNIT-7 Clockwork Awakening Sequence."
    },
    {
        "id": "inspect_hotspot_boathouse_to_pier",
        "text": "Climb the wet stone staircase back up to the pier where Torvald waits."
    },

    # Scene 3: Cottage / Study
    {
        "id": "inspect_hotspot_cottage_clock",
        "text": "A stately flame-birch tallcase clock. The hands are frozen precisely at 03:17, aligning with Astrid's journal entry."
    },
    {
        "id": "inspect_hotspot_cottage_safe",
        "text": "A reinforced safe with a four-digit combination tumbler. The clock and notes suggest the combination 3-1-7-8."
    },
    {
        "id": "inspect_hotspot_cottage_safe_locked",
        "text": "A reinforced safe with a four-digit combination tumbler. It's locked tight. I need the combination from Astrid's clock."
    },
    {
        "id": "inspect_hotspot_cottage_to_workshop",
        "text": "An iron-banded timber door leading straight into Astrid's experimental machinery hall."
    },
    {
        "id": "inspect_hotspot_cottage_to_pier",
        "text": "Return through the opened raven gate toward the coastline."
    },

    # Scene 4: Workshop
    {
        "id": "inspect_hotspot_workshop_unit7",
        "text": "Astrid's crowning achievement in precision micromirrors and steam hydraulics. Its optical lenses flicker with sapphire light."
    },
    {
        "id": "inspect_hotspot_workshop_to_cliff",
        "text": "A heavy pressure-sealed door that opens out onto the exterior sea cliff pathway."
    },
    {
        "id": "inspect_hotspot_workshop_to_cottage",
        "text": "Step back into the sheltered study room."
    },

    # Scene 5: Cliff
    {
        "id": "inspect_hotspot_cliff_gate",
        "text": "A towering megalithic portal carved with ancient stellar runes. It shelters the entrance to the lighthouse interior."
    },
    {
        "id": "inspect_hotspot_cliff_bridge",
        "text": "The wooden footboards have tumbled into the sea, but the high-tensile steel cables remain taut."
    },
    {
        "id": "inspect_hotspot_cliff_to_workshop",
        "text": "Seek shelter inside the warmth of the workshop."
    },

    # Scene 6: Staircase
    {
        "id": "inspect_hotspot_staircase_to_shipwreck",
        "text": "A subterranean tunnel cut through basalt, leading down to the hidden rocky cove where the North Star wrecked."
    },
    {
        "id": "inspect_hotspot_staircase_to_caverns",
        "text": "A counterbalanced iron cage lift descending into the deep geothermal caverns below sea level."
    },
    {
        "id": "inspect_hotspot_staircase_to_observatory",
        "text": "The spiral stairs wind upward toward the highest summit chamber and astronomical telescope."
    },
    {
        "id": "inspect_hotspot_staircase_to_cliff",
        "text": "Step outside through the runic portal back onto the windy mountain ledge."
    },

    # Scene 7: Shipwreck
    {
        "id": "inspect_hotspot_shipwreck_chest",
        "text": "Inside the chest rests The Great Azure Focus Prism! The lost optical crystal required for the lighthouse beacon."
    },
    {
        "id": "inspect_hotspot_shipwreck_to_staircase",
        "text": "Climb the rusted iron ladder back into the lighthouse generator chamber."
    },

    # Scene 8: Caverns
    {
        "id": "inspect_hotspot_caverns_pedestal",
        "text": "The crystals sing in sympathetic harmony with the 432 Hz acoustic tuning fork. Their pure note calms the tremors."
    },
    {
        "id": "inspect_hotspot_caverns_pedestal_locked",
        "text": "A piezoelectric crystal altar. It requires the sympathetic 432 Hz tuning fork to calibrate."
    },
    {
        "id": "inspect_hotspot_caverns_to_observatory",
        "text": "A high-speed vertical hoist leading straight to the lighthouse summit."
    },
    {
        "id": "inspect_hotspot_caverns_to_staircase",
        "text": "Take the counterbalanced cage back to the dynamo room."
    },

    # Scene 9: Observatory
    {
        "id": "inspect_hotspot_observatory_astrid",
        "text": "The visionary inventor and lighthouse master. Despite deep exhaustion, her sharp gaze burns with unwavering scientific conviction."
    },
    {
        "id": "inspect_hotspot_observatory_to_core",
        "text": "Ascend the final brass ladder into the revolving Fresnel lens chamber at the pinnacle of the spire."
    },
    {
        "id": "inspect_hotspot_observatory_to_staircase",
        "text": "Head down the spiral stairs to the dynamo floor."
    },

    # Scene 10: Core
    {
        "id": "inspect_hotspot_core_crystal",
        "text": "The beating heart of the spire. A colossal piezoelectric geode spinning in sympathetic harmony with Earth's magnetic core."
    },
    {
        "id": "inspect_hotspot_core_crystal_locked",
        "text": "The central beam socket is empty. It requires the great azure focus prism to align the resonance beam."
    },
    {
        "id": "inspect_hotspot_core_levers",
        "text": "The three judgment levers: Silver for Harmonic Integration, Copper for Controlled Damping, Crystal for Cosmic Transmission."
    },
    {
        "id": "inspect_hotspot_core_to_observatory",
        "text": "Climb down into the warmth of the astronomical observatory."
    },

    # Inventory Items (right-click inspection)
    {
        "id": "item_telegram",
        "text": "A crumpled telegram: Eivind. Come to Raven Island at once. The resonance has exceeded mathematical thresholds. Do not let the spire fall into silence."
    },
    {
        "id": "item_lantern",
        "text": "A heavy maritime storm lantern with brass reflectors and kerosene wick. Casts a warm circle of amber light."
    },
    {
        "id": "item_wrench",
        "text": "A heavy English adjustable spanner. Essential for opening high-pressure steam and hydraulic valves."
    },
    {
        "id": "item_punch_card",
        "text": "A perforated parchment card strip inscribed: UNIT-7 Clockwork Awakening Sequence."
    },
    {
        "id": "item_brass_key",
        "text": "A heavy, ornate brass key provided by Old Torvald. Fits the lock on the wrought iron gate."
    },
    {
        "id": "item_tuning_fork",
        "text": "A precision silver-crystal tuning fork from UNIT-7. Calibrated to sympathetic bedrock resonance at 432 Hertz."
    },
    {
        "id": "item_crystal_prism",
        "text": "The Great Azure Focus Prism. A multi-faceted synthetic sapphire prism capable of focusing the spire's harmonic beam."
    }
]

async def generate_hotspot_voices():
    target_dir = "assets/audio/voices"
    os.makedirs(target_dir, exist_ok=True)
    print(f"Generating {len(HOTSPOT_LINES)} hotspot & item neural voice lines...")
    
    # Run in batches of 5 to avoid connection throttles
    batch_size = 5
    for i in range(0, len(HOTSPOT_LINES), batch_size):
        batch = HOTSPOT_LINES[i:i+batch_size]
        tasks = []
        for item in batch:
            filepath = os.path.join(target_dir, f"{item['id']}.mp3")
            print(f"  -> Generating: {filepath} ...")
            tts = edge_tts.Communicate(item['text'], VOICE, rate=RATE, pitch=PITCH)
            tasks.append(tts.save(filepath))
        await asyncio.gather(*tasks)
    
    print("ALL HOTSPOT AND ITEM VOICES GENERATED SUCCESSFULLY!")

if __name__ == "__main__":
    asyncio.run(generate_hotspot_voices())
