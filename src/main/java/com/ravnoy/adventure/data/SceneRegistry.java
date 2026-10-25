package com.ravnoy.adventure.data;

import com.ravnoy.adventure.model.Hotspot;
import com.ravnoy.adventure.model.InteractionType;
import com.ravnoy.adventure.model.SceneModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registers all 10 chapters, their scenic backgrounds,
 * visible graphic overlays, and navigational connections in natural English.
 */
public class SceneRegistry {
    private Map<String, SceneModel> scenes = new LinkedHashMap<>();

    public void init() {
        // =========================================================================
        // CHAPTER 1: THE FOGBOUND PIER
        // =========================================================================
        SceneModel scene01 = new SceneModel(
            "scene_01_pier",
            1,
            "Chapter 1: The Fogbound Pier",
            "A bone-chilling coastal mist blankets rotting timbers. In the distance, an azure beacon pierces the dark.",
            "images/scenes/scene_01_pier.jpg"
        );
        scene01.setAmbientMusicPath("audio/music/ambient.wav");

        // 1. Visible Prop: Old Torvald sitting on the wooden dock crate
        scene01.addHotspot(new Hotspot(
            "hotspot_torvald",
            "Old Torvald (Harbormaster)",
            790, 160, 200, 330,
            InteractionType.TALK
        ).setOverlayTexturePath("images/overlays/torvald_prop.png")
         .setDialogueNodeId("torvald_intro")
         .setInspectionText("Raven Island's sole remaining fisherman. He sits calmly puffing his tobacco pipe as gale-force breakers crash against the pilings."));

        // 2. Visible Prop: Glowing Brass Kerosene Lantern
        scene01.addHotspot(new Hotspot(
            "hotspot_pier_lantern",
            "Brass Kerosene Lantern",
            230, 150, 120, 150,
            InteractionType.PICKUP
        ).setOverlayTexturePath("images/overlays/lantern_prop.png")
         .setGivenItemId("lantern")
         .setInspectionText("A heavy maritime storm lantern with brass reflectors and kerosene wick. Casts a warm circle of amber light."));

        // 3. Visible Prop: Coiled Hemp Rope with crystalline residue
        scene01.addHotspot(new Hotspot(
            "hotspot_pier_rope",
            "Coiled Hemp Mooring Line",
            540, 50, 140, 90,
            InteractionType.INSPECT
        ).setOverlayTexturePath("images/overlays/rope_overlay.png")
         .setInspectionText("Thick tarred maritime hawser. Faint flecks of azure piezoelectric crystal powder cling to the damp fibers."));

        // 4. Visible Prop: Ornate Wrought Iron Gate with Raven Sigil
        scene01.addHotspot(new Hotspot(
            "hotspot_pier_gate",
            "Wrought Iron Gate (Raven Sigil)",
            390, 290, 180, 280,
            InteractionType.TRANSITION
        ).setOverlayTexturePath("images/overlays/gate_prop.png")
         .setRequiredItemId("brass_key")
         .setTargetSceneId("scene_03_cottage")
         .setInspectionText("A formidable wrought-iron gate sealed with a heavy brass padlock embossed with a spread-winged raven."));

        // 5. Visible Prop: Stone Stairs to Boathouse
        scene01.addHotspot(new Hotspot(
            "hotspot_to_boathouse",
            "Stairway to Flooded Boathouse",
            40, 110, 160, 180,
            InteractionType.TRANSITION
        ).setOverlayTexturePath("images/overlays/boathouse_door_prop.png")
         .setTargetSceneId("scene_02_boathouse")
         .setInspectionText("Slick granite steps descend into the sheltered sea cove where the old timber boathouse stands."));

        scenes.put(scene01.getId(), scene01);


        // =========================================================================
        // CHAPTER 2: THE FLOODED BOATHOUSE
        // =========================================================================
        SceneModel scene02 = new SceneModel(
            "scene_02_boathouse",
            2,
            "Chapter 2: The Flooded Boathouse",
            "The scent of pine tar, brine, and cured leather. Slanted rays of azure moonlight filter through cracked planks.",
            "images/scenes/scene_02_boathouse.jpg"
        );
        // Heavy steel wrench on workbench
        scene02.addHotspot(new Hotspot(
            "hotspot_boathouse_wrench",
            "Forged Steel Wrench",
            100, 230, 96, 96,
            InteractionType.PICKUP
        ).setOverlayTexturePath("images/items/wrench.png")
         .setGivenItemId("wrench")
         .setInspectionText("A heavy English adjustable spanner. Essential for opening high-pressure steam and hydraulic valves."));

        // Mechanical punch card protocol
        scene02.addHotspot(new Hotspot(
            "hotspot_boathouse_punchcard",
            "Punch Card 'Protocol A-1'",
            640, 250, 96, 96,
            InteractionType.PICKUP
        ).setOverlayTexturePath("images/items/punch_card.png")
         .setGivenItemId("punch_card")
         .setInspectionText("A perforated parchment card strip inscribed: 'UNIT-7 Clockwork Awakening Sequence'."));

        // Exit back to pier
        scene02.addHotspot(new Hotspot(
            "hotspot_boathouse_to_pier",
            "Ascend to Fogbound Pier",
            1080, 80, 160, 200,
            InteractionType.TRANSITION
        ).setOverlayTexturePath("images/overlays/boathouse_door_prop.png")
         .setTargetSceneId("scene_01_pier")
         .setInspectionText("Climb the wet stone staircase back up to the pier where Torvald waits."));

        scenes.put(scene02.getId(), scene02);


        // =========================================================================
        // CHAPTER 3: THE KEEPER'S ABANDONED STUDY
        // =========================================================================
        SceneModel scene03 = new SceneModel(
            "scene_03_cottage",
            3,
            "Chapter 3: The Keeper's Study",
            "Scattered mathematical treatises and glowing instruments. An antique grandfather clock has stopped at 03:17.",
            "images/scenes/scene_03_cottage.jpg"
        );
        // Visible Prop: Grandfather Clock stopped at 03:17
        scene03.addHotspot(new Hotspot(
            "hotspot_cottage_clock",
            "Grandfather Clock (Stopped at 03:17)",
            600, 190, 140, 300,
            InteractionType.INSPECT
        ).setOverlayTexturePath("images/overlays/clock_prop.png")
         .setDialogueNodeId("eivind_cottage_monologue")
         .setInspectionText("A stately flame-birch tallcase clock. The hands are frozen precisely at 03:17, aligning with Astrid's journal entry."));

        // Visible Prop: Recessed Wall Vault
        scene03.addHotspot(new Hotspot(
            "hotspot_cottage_safe",
            "Recessed Steel Wall Vault",
            450, 400, 110, 110,
            InteractionType.INSPECT
        ).setOverlayTexturePath("images/overlays/safe_prop.png")
         .setInspectionText("A reinforced safe with a four-digit combination tumbler. The clock and notes suggest the combination 3-1-7-8."));

        // Doorway into the workshop
        scene03.addHotspot(new Hotspot(
            "hotspot_cottage_to_workshop",
            "Passage to Clockwork Workshop",
            1050, 240, 180, 360,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_04_workshop")
         .setInspectionText("An iron-banded timber door leading straight into Astrid's experimental machinery hall."));

        // Doorway back to pier
        scene03.addHotspot(new Hotspot(
            "hotspot_cottage_to_pier",
            "Exit to Coastal Pier",
            40, 80, 140, 220,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_01_pier")
         .setInspectionText("Return through the opened raven gate toward the coastline."));

        scenes.put(scene03.getId(), scene03);


        // =========================================================================
        // CHAPTER 4: THE CLOCKWORK WORKSHOP
        // =========================================================================
        SceneModel scene04 = new SceneModel(
            "scene_04_workshop",
            4,
            "Chapter 4: The Clockwork Workshop",
            "Steam pipes hiss softly amidst dynamos and copper coils. In the center sits the automaton UNIT-7.",
            "images/scenes/scene_04_workshop.jpg"
        );
        // Visible Prop: UNIT-7 Clockwork Automaton
        scene04.addHotspot(new Hotspot(
            "hotspot_workshop_unit7",
            "UNIT-7 (Clockwork Automaton)",
            440, 180, 230, 340,
            InteractionType.TALK
        ).setOverlayTexturePath("images/overlays/unit7_prop.png")
         .setDialogueNodeId("unit7_intro")
         .setInspectionText("Astrid's crowning achievement in precision micromirrors and steam hydraulics. Its optical lenses flicker with sapphire light."));

        // Heavy door out to the cliffs
        scene04.addHotspot(new Hotspot(
            "hotspot_workshop_to_cliff",
            "Blast Portal to Tempestuous Cliff",
            980, 280, 160, 320,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_05_cliff")
         .setInspectionText("A heavy pressure-sealed door that opens out onto the exterior sea cliff pathway."));

        // Back to study
        scene04.addHotspot(new Hotspot(
            "hotspot_workshop_to_cottage",
            "Return to Keeper's Study",
            50, 100, 140, 200,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_03_cottage")
         .setInspectionText("Step back into the sheltered study room."));

        scenes.put(scene04.getId(), scene04);


        // =========================================================================
        // CHAPTER 5: THE TEMPESTUOUS SEA CLIFF
        // =========================================================================
        SceneModel scene05 = new SceneModel(
            "scene_05_cliff",
            5,
            "Chapter 5: The Tempestuous Sea Cliff",
            "The gale roars over the abyss. Massive ocean breakers shatter against sheer granite walls three hundred feet below.",
            "images/scenes/scene_05_cliff.jpg"
        );
        scene05.addHotspot(new Hotspot(
            "hotspot_cliff_gate",
            "Runic Granite Gateway",
            780, 280, 180, 200,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_06_staircase")
         .setInspectionText("A towering megalithic portal carved with ancient stellar runes. It shelters the entrance to the lighthouse interior."));

        scene05.addHotspot(new Hotspot(
            "hotspot_cliff_bridge",
            "Chasm Suspension Bridge",
            340, 440, 400, 80,
            InteractionType.INSPECT
        ).setInspectionText("The wooden footboards have tumbled into the sea, but the high-tensile steel cables remain taut."));

        scene05.addHotspot(new Hotspot(
            "hotspot_cliff_to_workshop",
            "Return to Machine Workshop",
            30, 380, 140, 180,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_04_workshop")
         .setInspectionText("Seek shelter inside the warmth of the workshop."));

        scenes.put(scene05.getId(), scene05);


        // =========================================================================
        // CHAPTER 6: THE LIGHTHOUSE DYNAMO STAIRCASE
        // =========================================================================
        SceneModel scene06 = new SceneModel(
            "scene_06_staircase",
            6,
            "Chapter 6: The Lighthouse Dynamo Staircase",
            "A hollow tower of cut granite and iron. The central copper drive shaft hums with electromagnetic power.",
            "images/scenes/scene_06_staircase.jpg"
        );
        scene06.addHotspot(new Hotspot(
            "hotspot_staircase_to_shipwreck",
            "Descent to Shipwreck Cove",
            100, 200, 180, 120,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_07_shipwreck")
         .setInspectionText("A subterranean tunnel cut through basalt, leading down to the hidden rocky cove where the North Star wrecked."));

        scene06.addHotspot(new Hotspot(
            "hotspot_staircase_to_caverns",
            "Cage Lift to Crystal Caverns",
            320, 80, 180, 100,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_08_caverns")
         .setInspectionText("A counterbalanced iron cage lift descending into the deep geothermal caverns below sea level."));

        scene06.addHotspot(new Hotspot(
            "hotspot_staircase_to_observatory",
            "Spiral Stairs to Celestial Observatory",
            680, 460, 200, 160,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_09_observatory")
         .setInspectionText("The spiral stairs wind upward toward the highest summit chamber and astronomical telescope."));

        scene06.addHotspot(new Hotspot(
            "hotspot_staircase_to_cliff",
            "Exit to Sea Cliff Path",
            1050, 140, 150, 180,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_05_cliff")
         .setInspectionText("Step outside through the runic portal back onto the windy mountain ledge."));

        scenes.put(scene06.getId(), scene06);


        // =========================================================================
        // CHAPTER 7: THE WRECK OF THE NORTH STAR
        // =========================================================================
        SceneModel scene07 = new SceneModel(
            "scene_07_shipwreck",
            7,
            "Chapter 7: The Wreck of the North Star",
            "Skeletal ship timbers splintered against jagged rocks. The constellation Orion gleams above the broken mast.",
            "images/scenes/scene_07_shipwreck.jpg"
        );
        // Visible Prop: Ironbound Sea Chest with Astrological Gem
        scene07.addHotspot(new Hotspot(
            "hotspot_shipwreck_chest",
            "Captain's Ironbound Sea Chest",
            530, 460, 160, 120,
            InteractionType.PICKUP
        ).setOverlayTexturePath("images/overlays/chest_prop.png")
         .setGivenItemId("crystal_prism")
         .setInspectionText("Inside the chest rests The Great Azure Focus Prism! The lost optical crystal required for the lighthouse beacon."));

        scene07.addHotspot(new Hotspot(
            "hotspot_shipwreck_to_staircase",
            "Ascend to Lighthouse Staircase",
            60, 480, 160, 140,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_06_staircase")
         .setInspectionText("Climb the rusted iron ladder back into the lighthouse generator chamber."));

        scenes.put(scene07.getId(), scene07);


        // =========================================================================
        // CHAPTER 8: THE SUBTERRANEAN CRYSTAL CAVERNS
        // =========================================================================
        SceneModel scene08 = new SceneModel(
            "scene_08_caverns",
            8,
            "Chapter 8: The Subterranean Crystal Caverns",
            "An otherworldly cathedral of towering, bioluminescent azure crystals vibrating deep beneath the ocean bed.",
            "images/scenes/scene_08_caverns.jpg"
        );
        // Visible Prop: Resonant Tuning Fork Pedestal & Crystal Geode
        scene08.addHotspot(new Hotspot(
            "hotspot_caverns_pedestal",
            "Piezoelectric Crystal Altar (432 Hz)",
            720, 220, 150, 260,
            InteractionType.INSPECT
        ).setOverlayTexturePath("images/overlays/tuning_fork_pedestal.png")
         .setDialogueNodeId("eivind_crystals_monologue")
         .setInspectionText("The crystals sing in sympathetic harmony with the 432 Hz acoustic tuning fork. Their pure note calms the tremors."));

        scene08.addHotspot(new Hotspot(
            "hotspot_caverns_to_observatory",
            "Direct Express Hoist to Observatory",
            1040, 320, 160, 220,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_09_observatory")
         .setInspectionText("A high-speed vertical hoist leading straight to the lighthouse summit."));

        scene08.addHotspot(new Hotspot(
            "hotspot_caverns_to_staircase",
            "Lift to Generator Floor",
            60, 120, 150, 160,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_06_staircase")
         .setInspectionText("Take the counterbalanced cage back to the dynamo room."));

        scenes.put(scene08.getId(), scene08);


        // =========================================================================
        // CHAPTER 9: THE CELESTIAL OBSERVATORY
        // =========================================================================
        SceneModel scene09 = new SceneModel(
            "scene_09_observatory",
            9,
            "Chapter 9: The Celestial Observatory",
            "A 360-degree panorama of storm and constellations. Professor Astrid Ravn stands vigil over the control dais.",
            "images/scenes/scene_09_observatory.jpg"
        );
        // Visible Prop: Professor Astrid Ravn Character Sprite
        scene09.addHotspot(new Hotspot(
            "hotspot_observatory_astrid",
            "Professor Astrid Ravn",
            570, 340, 180, 330,
            InteractionType.TALK
        ).setOverlayTexturePath("images/overlays/astrid_prop.png")
         .setDialogueNodeId("astrid_intro")
         .setInspectionText("The visionary inventor and lighthouse master. Despite deep exhaustion, her sharp gaze burns with unwavering scientific conviction."));

        scene09.addHotspot(new Hotspot(
            "hotspot_observatory_to_core",
            "Hatch to Lantern Core Chamber",
            820, 460, 150, 180,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_10_core")
         .setInspectionText("Ascend the final brass ladder into the revolving Fresnel lens chamber at the pinnacle of the spire."));

        scene09.addHotspot(new Hotspot(
            "hotspot_observatory_to_staircase",
            "Descent to Staircase",
            80, 220, 140, 180,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_06_staircase")
         .setInspectionText("Head down the spiral stairs to the dynamo floor."));

        scenes.put(scene09.getId(), scene09);


        // =========================================================================
        // CHAPTER 10: THE KEEPER'S CORE & FINAL JUDGMENT
        // =========================================================================
        SceneModel scene10 = new SceneModel(
            "scene_10_core",
            10,
            "Chapter 10: The Keeper's Core & The Final Judgment",
            "The Fresnel lens hums with celestial resonance. The fate of Raven Island and the coastal seas awaits your hand.",
            "images/scenes/scene_10_core.jpg"
        );
        // Visible Prop: Crystal Prism Core Socket
        scene10.addHotspot(new Hotspot(
            "hotspot_core_crystal",
            "Pulsating Crystalline Core",
            570, 270, 140, 140,
            InteractionType.INSPECT
        ).setOverlayTexturePath("images/overlays/crystal_prism_socket.png")
         .setInspectionText("The beating heart of the spire. A colossal piezoelectric geode spinning in sympathetic harmony with Earth's magnetic core."));

        // Visible Prop: Master Control Levers (Three Endings)
        scene10.addHotspot(new Hotspot(
            "hotspot_core_levers",
            "Master Control Levers (Silver, Copper, Crystal)",
            430, 540, 420, 160,
            InteractionType.INSPECT
        ).setOverlayTexturePath("images/overlays/levers_prop.png")
         .setInspectionText("The three judgment levers: Silver (Harmonic Integration - Ending A), Copper (Controlled Damping - Ending B), Crystal (Cosmic Transmission - Ending C)."));

        scene10.addHotspot(new Hotspot(
            "hotspot_core_to_observatory",
            "Return to Observatory",
            80, 540, 160, 120,
            InteractionType.TRANSITION
        ).setTargetSceneId("scene_09_observatory")
         .setInspectionText("Climb down into the warmth of the astronomical observatory."));

        scenes.put(scene10.getId(), scene10);
    }

    public SceneModel getScene(String id) {
        return scenes.get(id);
    }

    public Map<String, SceneModel> getAllScenes() {
        return scenes;
    }
}
