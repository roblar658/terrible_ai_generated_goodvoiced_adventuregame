package com.ravnoy.adventure.data;

import com.ravnoy.adventure.model.DialogueChoice;
import com.ravnoy.adventure.model.DialogueNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry of comprehensive, English branching dialogue trees.
 * Every character features at least 4 initial choices and multi-tiered discussion branches
 * with 3 choices each across multiple branching levels.
 */
public class DialogueRegistry {
    private Map<String, DialogueNode> nodes = new HashMap<>();

    public void init() {
        nodes.clear();

        // =========================================================================
        // 1. OLD TORVALD (SCENE 1: THE FOGBOUND PIER)
        // =========================================================================
        DialogueNode torvaldIntro = new DialogueNode(
            "torvald_intro",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Expected, you say?! Nobody is expected on Raven Island anymore, lad! Not since the light turned that cursed shade of blue... Astrid has locked herself in her tower for three weeks straight now!",
            "audio/voices/torvald_intro.mp3"
        );
        torvaldIntro.addChoice(new DialogueChoice(
            "What in heaven's name is happening to the lighthouse beacon?",
            "torvald_beacon_topic"
        ));
        torvaldIntro.addChoice(new DialogueChoice(
            "Tell me about the folklore of Raven Island and the whispers beneath the sea.",
            "torvald_folklore_topic"
        ));
        torvaldIntro.addChoice(new DialogueChoice(
            "The iron gate inland is sealed shut. How do I get through to Astrid's cottage?",
            "torvald_gate_topic"
        ));
        torvaldIntro.addChoice(new DialogueChoice(
            "I carry an urgent telegram from Professor Ravn herself. Read this.",
            "torvald_telegram_topic"
        ));
        torvaldIntro.addChoice(new DialogueChoice(
            "[Offer Lantern] Here is your brass lantern, Torvald. In working condition.",
            "torvald_unlock_reward"
        ).setRequiredItemId("lantern"));
        nodes.put(torvaldIntro.getId(), torvaldIntro);
        nodes.put("torvald_root", torvaldIntro);

        // --- TORVALD BRANCH 1: BEACON TOPIC ---
        DialogueNode torvaldBeacon = new DialogueNode(
            "torvald_beacon_topic",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "That light ain't no ordinary whale-oil flame nor electric arc, Eivind. Three weeks ago, the sky turned violet, and the compass needles began spinning like drunken flies. Astrid tapped into something sleeping beneath the basalt...",
            "audio/voices/torvald_beacon_topic.mp3"
        );
        torvaldBeacon.addChoice(new DialogueChoice(
            "A scientific anomaly? Could it be a high-voltage terrestrial dynamo overload?",
            "torvald_beacon_science"
        ));
        torvaldBeacon.addChoice(new DialogueChoice(
            "You sound terrified, Torvald. Have the local fishermen seen something in the water?",
            "torvald_beacon_sightings"
        ));
        torvaldBeacon.addChoice(new DialogueChoice(
            "If Astrid is trapped up there, every second counts. How long can the structure withstand this resonance?",
            "torvald_beacon_danger"
        ));
        nodes.put(torvaldBeacon.getId(), torvaldBeacon);

        DialogueNode torvaldBeaconScience = new DialogueNode(
            "torvald_beacon_science",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Science? Call it what you like, lad! When the rocks themselves begin to hum at midnight and the tide ebbs backward against the moon, books don't keep your boat afloat. Her workshop was shaking so hard last Tuesday the rivets were popping from the boiler!",
            "audio/voices/torvald_beacon_science.mp3"
        );
        torvaldBeaconScience.addChoice(new DialogueChoice(
            "Did she mention any specific frequency? Her notes referenced 432 Hertz.",
            "torvald_science_freq"
        ));
        torvaldBeaconScience.addChoice(new DialogueChoice(
            "Rivets popping? Did she have auxiliary relief valves installed in the workshop?",
            "torvald_science_valves"
        ));
        torvaldBeaconScience.addChoice(new DialogueChoice(
            "I'll inspect the machinery myself. She taught me to look at empirical data, not dread.",
            "torvald_science_resolve"
        ));
        nodes.put(torvaldBeaconScience.getId(), torvaldBeaconScience);

        DialogueNode torvaldScienceFreq = new DialogueNode(
            "torvald_science_freq",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Four-thirty-two... aye! That clanking automaton of hers, UNIT-7, kept chanting that number while blue sparks showered from its head. Said it was the natural pulse of the bedrock. If that's peace, I'd hate to see war!",
            "audio/voices/torvald_science_freq.mp3"
        );
        torvaldScienceFreq.addChoice(new DialogueChoice("UNIT-7 is still functioning? I will confer with the automaton.", "torvald_intro"));
        torvaldScienceFreq.addChoice(new DialogueChoice("Where can I find tools to service the workshop machinery?", "torvald_gate_location"));
        torvaldScienceFreq.addChoice(new DialogueChoice("I have heard enough. Let me proceed.", "dialogue_exit"));
        nodes.put(torvaldScienceFreq.getId(), torvaldScienceFreq);

        DialogueNode torvaldScienceValves = new DialogueNode(
            "torvald_science_valves",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye, she had high-pressure steam pipes winding through the floorboards. Kept my heavy forged wrench down on the boathouse workbench. If you're going to fiddle with her boilers, you'll need that wrench to turn the valves.",
            "audio/voices/torvald_science_valves.mp3"
        );
        torvaldScienceValves.addChoice(new DialogueChoice("A forged wrench will be indispensable. I'll search the boathouse.", "torvald_intro"));
        torvaldScienceValves.addChoice(new DialogueChoice("Did she leave any operating punch-cards down there?", "torvald_loc_punchcard"));
        torvaldScienceValves.addChoice(new DialogueChoice("I will head down to the boathouse immediately.", "dialogue_exit"));
        nodes.put(torvaldScienceValves.getId(), torvaldScienceValves);

        DialogueNode torvaldScienceResolve = new DialogueNode(
            "torvald_science_resolve",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "That's Astrid's student talking, through and through. Cool head and sharp eyes. Just don't let your mathematics blind you when granite slabs come crashing down on your skull.",
            "audio/voices/torvald_science_resolve.mp3"
        );
        torvaldScienceResolve.addChoice(new DialogueChoice("Understood. Let's discuss how I can reach the cottage.", "torvald_gate_topic"));
        torvaldScienceResolve.addChoice(new DialogueChoice("Tell me more about the island's folklore.", "torvald_folklore_topic"));
        torvaldScienceResolve.addChoice(new DialogueChoice("I am ready to move forward.", "dialogue_exit"));
        nodes.put(torvaldScienceResolve.getId(), torvaldScienceResolve);

        DialogueNode torvaldBeaconSightings = new DialogueNode(
            "torvald_beacon_sightings",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "The herring shoals vanished overnight. Then came the deep cetaceans—whales beaching themselves on the western reef, singing in tones that rattled our ribcages. They weren't dying from sickness; they were answering the spire!",
            "audio/voices/torvald_beacon_sightings.mp3"
        );
        torvaldBeaconSightings.addChoice(new DialogueChoice(
            "An acoustic harmonic pull... the spire must be generating low-frequency infrasound.",
            "torvald_whales_infra"
        ));
        torvaldBeaconSightings.addChoice(new DialogueChoice(
            "Did any fishing vessels attempt to investigate the reef or the cove?",
            "torvald_whales_vessels"
        ));
        torvaldBeaconSightings.addChoice(new DialogueChoice(
            "We must silence the transmitter before more wildlife is harmed.",
            "torvald_whales_silence"
        ));
        nodes.put(torvaldBeaconSightings.getId(), torvaldBeaconSightings);

        DialogueNode torvaldWhalesInfra = new DialogueNode(
            "torvald_whales_infra",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Infrasound or devilry, the vibration turned the harbor water to froth. The old folks said the abyss has its own lungs. Astrid stuck a brass straw into them and started blowing!",
            "audio/voices/torvald_whales_infra.mp3"
        );
        torvaldWhalesInfra.addChoice(new DialogueChoice("We will cap that straw and stabilize the pressure.", "torvald_intro"));
        torvaldWhalesInfra.addChoice(new DialogueChoice("Did any ships shipwreck because of the disturbance?", "torvald_whales_vessels"));
        torvaldWhalesInfra.addChoice(new DialogueChoice("I'll see to it immediately.", "dialogue_exit"));
        nodes.put(torvaldWhalesInfra.getId(), torvaldWhalesInfra);

        DialogueNode torvaldWhalesVessels = new DialogueNode(
            "torvald_whales_vessels",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "None recently, thank God! But fifty years back, the three-masted schooner 'North Star' broke her spine on the eastern reef. Astrid salvaged optical components from that wreck for years. The hull still sits rotting in the cove.",
            "audio/voices/torvald_whales_vessels.mp3"
        );
        torvaldWhalesVessels.addChoice(new DialogueChoice("Optical components from the North Star? That could be crucial.", "torvald_intro"));
        torvaldWhalesVessels.addChoice(new DialogueChoice("Is the path to the shipwreck accessible from the lighthouse?", "torvald_folk_ruins"));
        torvaldWhalesVessels.addChoice(new DialogueChoice("I will keep that in mind as I search.", "dialogue_exit"));
        nodes.put(torvaldWhalesVessels.getId(), torvaldWhalesVessels);

        DialogueNode torvaldWhalesSilence = new DialogueNode(
            "torvald_whales_silence",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Music to my ears, lad. If you can restore quiet to this coastline, I'll buy you a barrel of the finest spiced aquavit in Bergen. Now get to work.",
            "audio/voices/torvald_whales_silence.mp3"
        );
        torvaldWhalesSilence.addChoice(new DialogueChoice("I need that gate key first, Torvald.", "torvald_gate_topic"));
        torvaldWhalesSilence.addChoice(new DialogueChoice("Let me review Astrid's telegram with you.", "torvald_telegram_topic"));
        torvaldWhalesSilence.addChoice(new DialogueChoice("I won't let you down.", "dialogue_exit"));
        nodes.put(torvaldWhalesSilence.getId(), torvaldWhalesSilence);

        DialogueNode torvaldBeaconDanger = new DialogueNode(
            "torvald_beacon_danger",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Not long, boy. Look at the cliff faces—fissures running straight from the foundation down to the sea caves. One bad harmonic surge, and half the mountain slides into the Atlantic abyss.",
            "audio/voices/torvald_beacon_danger.mp3"
        );
        torvaldBeaconDanger.addChoice(new DialogueChoice(
            "Is there an emergency power cutoff or grounding circuit down here?",
            "torvald_danger_cutoff"
        ));
        torvaldBeaconDanger.addChoice(new DialogueChoice(
            "Why didn't you evacuate to the mainland when the cracks first formed?",
            "torvald_danger_evac"
        ));
        torvaldBeaconDanger.addChoice(new DialogueChoice(
            "Then there is no time to lose. I must reach the upper levels immediately.",
            "torvald_danger_haste"
        ));
        nodes.put(torvaldBeaconDanger.getId(), torvaldBeaconDanger);

        DialogueNode torvaldDangerCutoff = new DialogueNode(
            "torvald_danger_cutoff",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Astrid bypassed the ground switches. Said the harmonic flow had to pass through the Great Azure Focus Prism at the summit, or the generator coils would melt like lard on a hot griddle.",
            "audio/voices/torvald_danger_cutoff.mp3"
        );
        torvaldDangerCutoff.addChoice(new DialogueChoice("So without the Great Prism, the whole system burns out.", "torvald_intro"));
        torvaldDangerCutoff.addChoice(new DialogueChoice("Where is that Great Azure Prism now?", "torvald_whales_vessels"));
        torvaldDangerCutoff.addChoice(new DialogueChoice("I will find the prism and restore balance.", "dialogue_exit"));
        nodes.put(torvaldDangerCutoff.getId(), torvaldDangerCutoff);

        DialogueNode torvaldDangerEvac = new DialogueNode(
            "torvald_danger_evac",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Sixty years I've hauled cod from these reefs, boy. A man doesn't run from his home because a lighthouse has a fever! Besides... Astrid needed someone watching the water. And someone had to be here when you arrived.",
            "audio/voices/torvald_danger_evac.mp3"
        );
        torvaldDangerEvac.addChoice(new DialogueChoice("Your loyalty does you honor, Torvald.", "torvald_intro"));
        torvaldDangerEvac.addChoice(new DialogueChoice("Tell me what you know about the ancient island legends.", "torvald_folklore_topic"));
        torvaldDangerEvac.addChoice(new DialogueChoice("I will make sure your vigil was not in vain.", "dialogue_exit"));
        nodes.put(torvaldDangerEvac.getId(), torvaldDangerEvac);

        DialogueNode torvaldDangerHaste = new DialogueNode(
            "torvald_danger_haste",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Then quit jawing and get moving! Fetch my brass lantern from the boathouse so I have light when the fog thickens, and the raven key is yours.",
            "audio/voices/torvald_danger_haste.mp3"
        );
        torvaldDangerHaste.addChoice(new DialogueChoice("Where on the boathouse workbench did you leave it?", "torvald_gate_location"));
        torvaldDangerHaste.addChoice(new DialogueChoice("I'll be back with the lantern in two shakes.", "dialogue_exit"));
        torvaldDangerHaste.addChoice(new DialogueChoice("Consider it done.", "dialogue_exit"));
        nodes.put(torvaldDangerHaste.getId(), torvaldDangerHaste);

        // --- TORVALD BRANCH 2: FOLKLORE TOPIC ---
        DialogueNode torvaldFolklore = new DialogueNode(
            "torvald_folklore_topic",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Two hundred years ago, Norse sea-raiders avoided this skerry. They called it 'Hrafnsey'—the Isle of the Whispering Raven. Legend said a fallen star was buried in the abyss, and when mortals disturb its rest, the sea demands its tribute.",
            "audio/voices/torvald_folklore_topic.mp3"
        );
        torvaldFolklore.addChoice(new DialogueChoice(
            "A fallen star... could it be an extraterrestrial meteorite containing piezoelectric quartz?",
            "torvald_folk_meteor"
        ));
        torvaldFolklore.addChoice(new DialogueChoice(
            "Do you truly believe an ancient maritime curse is responsible for Astrid's crisis?",
            "torvald_folk_curse"
        ));
        torvaldFolklore.addChoice(new DialogueChoice(
            "Were there ancient structures or temples on this island before the lighthouse was built?",
            "torvald_folk_ruins"
        ));
        nodes.put(torvaldFolklore.getId(), torvaldFolklore);

        DialogueNode torvaldFolkMeteor = new DialogueNode(
            "torvald_folk_meteor",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Call it meteor, call it demon-stone. Astrid drilled deep boreholes into the sunken caverns. She brought up crystal shards that glowed without any wick or battery. You could hold one in your palm and feel it thumping like a living sparrow's heart.",
            "audio/voices/torvald_folk_meteor.mp3"
        );
        torvaldFolkMeteor.addChoice(new DialogueChoice(
            "Where did she store those raw crystal samples?",
            "torvald_meteor_storage"
        ));
        torvaldFolkMeteor.addChoice(new DialogueChoice(
            "A crystal that generates infinite electric current... a miraculous discovery.",
            "torvald_meteor_infinite"
        ));
        torvaldFolkMeteor.addChoice(new DialogueChoice(
            "Extracting uncalibrated minerals from an active fault line was an immense gamble.",
            "torvald_meteor_gamble"
        ));
        nodes.put(torvaldFolkMeteor.getId(), torvaldFolkMeteor);

        DialogueNode torvaldMeteorStorage = new DialogueNode(
            "torvald_meteor_storage",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Up in her cottage study, inside a reinforced wall safe. And deeper down, in the Subterranean Caverns beneath the lighthouse. That place gives me goosebumps just thinking about it.",
            "audio/voices/torvald_meteor_storage.mp3"
        );
        torvaldMeteorStorage.addChoice(new DialogueChoice("I will examine the study safe carefully.", "torvald_intro"));
        torvaldMeteorStorage.addChoice(new DialogueChoice("How does one access the subterranean caverns?", "torvald_folk_ruins"));
        torvaldMeteorStorage.addChoice(new DialogueChoice("Thank you for the guidance.", "dialogue_exit"));
        nodes.put(torvaldMeteorStorage.getId(), torvaldMeteorStorage);

        DialogueNode torvaldMeteorInfinite = new DialogueNode(
            "torvald_meteor_infinite",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Miraculous or deadly, boy! Free power sounds grand until the mountain under your kitchen table begins to hum at forty thousand cycles a minute. Nature don't hand out free gifts.",
            "audio/voices/torvald_meteor_infinite.mp3"
        );
        torvaldMeteorInfinite.addChoice(new DialogueChoice("True. Thermodynamics always demands balance.", "torvald_intro"));
        torvaldMeteorInfinite.addChoice(new DialogueChoice("Let us discuss how to reach the spire.", "torvald_gate_topic"));
        torvaldMeteorInfinite.addChoice(new DialogueChoice("I must see this with my own eyes.", "dialogue_exit"));
        nodes.put(torvaldMeteorInfinite.getId(), torvaldMeteorInfinite);

        DialogueNode torvaldMeteorGamble = new DialogueNode(
            "torvald_meteor_gamble",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "A gamble that didn't pay off! Now she's barricaded in the summit, and the sea's ready to swallow the whole works. That's why you're here—to clean up the spilled milk.",
            "audio/voices/torvald_meteor_gamble.mp3"
        );
        torvaldMeteorGamble.addChoice(new DialogueChoice("I will set things right, Torvald.", "torvald_intro"));
        torvaldMeteorGamble.addChoice(new DialogueChoice("How do I unlock the gate?", "torvald_gate_topic"));
        torvaldMeteorGamble.addChoice(new DialogueChoice("I am on my way.", "dialogue_exit"));
        nodes.put(torvaldMeteorGamble.getId(), torvaldMeteorGamble);

        DialogueNode torvaldFolkCurse = new DialogueNode(
            "torvald_folk_curse",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Curse or nature, it don't make much difference when the waves are twenty cubits high! Men think because they can draft blueprints and forge iron, they own the world. But Raven Island was here before us, and it'll be here after we drown.",
            "audio/voices/torvald_folk_curse.mp3"
        );
        torvaldFolkCurse.addChoice(new DialogueChoice(
            "Human curiosity and science are how we overcome fear, Torvald.",
            "torvald_curse_philosophy"
        ));
        torvaldFolkCurse.addChoice(new DialogueChoice(
            "You've spent your entire life with this ocean. Your caution is justified.",
            "torvald_curse_respect"
        ));
        torvaldFolkCurse.addChoice(new DialogueChoice(
            "Fatalism won't save Astrid. Systematic engineering will.",
            "torvald_curse_action"
        ));
        nodes.put(torvaldFolkCurse.getId(), torvaldFolkCurse);

        DialogueNode torvaldCursePhilosophy = new DialogueNode(
            "torvald_curse_philosophy",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Fine words, scholar. Let's see if those words can stop a landslide. Head inland and prove your grit.",
            "audio/voices/torvald_curse_philosophy.mp3"
        );
        torvaldCursePhilosophy.addChoice(new DialogueChoice("I intend to. Give me the key requirements.", "torvald_gate_topic"));
        torvaldCursePhilosophy.addChoice(new DialogueChoice("Tell me more about the beacon.", "torvald_beacon_topic"));
        torvaldCursePhilosophy.addChoice(new DialogueChoice("I will proceed.", "dialogue_exit"));
        nodes.put(torvaldCursePhilosophy.getId(), torvaldCursePhilosophy);

        DialogueNode torvaldCurseRespect = new DialogueNode(
            "torvald_curse_respect",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "At least you've got the humility to listen. Most scholars from the capital arrive puffed up like puffins. Respect the island, and maybe it won't crush you.",
            "audio/voices/torvald_curse_respect.mp3"
        );
        torvaldCurseRespect.addChoice(new DialogueChoice("Thank you, Torvald. Let us discuss the gate.", "torvald_gate_topic"));
        torvaldCurseRespect.addChoice(new DialogueChoice("What of the telegram Astrid sent?", "torvald_telegram_topic"));
        torvaldCurseRespect.addChoice(new DialogueChoice("I'll remember your words.", "dialogue_exit"));
        nodes.put(torvaldCurseRespect.getId(), torvaldCurseRespect);

        DialogueNode torvaldCurseAction = new DialogueNode(
            "torvald_curse_action",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Action is what's needed, aye! Just keep your wits sharp. The island has swallowed better men than both of us.",
            "audio/voices/torvald_curse_action.mp3"
        );
        torvaldCurseAction.addChoice(new DialogueChoice("Let's get that iron gate open.", "torvald_gate_topic"));
        torvaldCurseAction.addChoice(new DialogueChoice("I will succeed where others faltered.", "dialogue_exit"));
        torvaldCurseAction.addChoice(new DialogueChoice("Back to the main discussion.", "torvald_intro"));
        nodes.put(torvaldCurseAction.getId(), torvaldCurseAction);

        DialogueNode torvaldFolkRuins = new DialogueNode(
            "torvald_folk_ruins",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye. Up on the northern cliff ledge, where the wind tears the hair off your scalp. You'll find stone arches carved with runic markings no modern chisel could replicate. Astrid claimed they were harmonic baffles carved by early settlers.",
            "audio/voices/torvald_folk_ruins.mp3"
        );
        torvaldFolkRuins.addChoice(new DialogueChoice(
            "Harmonic baffles? So the ancients understood acoustic resonance?",
            "torvald_ruins_baffles"
        ));
        torvaldFolkRuins.addChoice(new DialogueChoice(
            "Is the northern cliff path traversable in this howling gale?",
            "torvald_ruins_path"
        ));
        torvaldFolkRuins.addChoice(new DialogueChoice(
            "I will inspect those runic inscriptions closely when I reach the upper ledge.",
            "torvald_ruins_examine"
        ));
        nodes.put(torvaldFolkRuins.getId(), torvaldFolkRuins);

        DialogueNode torvaldRuinsBaffles = new DialogueNode(
            "torvald_ruins_baffles",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Astrid swore they did. Said the old Vikings didn't just sail; they knew how to quiet the howling rocks with specific acoustic ratios. You'll pass right through that runic arch on Chapter 5.",
            "audio/voices/torvald_ruins_baffles.mp3"
        );
        torvaldRuinsBaffles.addChoice(new DialogueChoice("Fascinating. I'll take notes in my journal.", "torvald_intro"));
        torvaldRuinsBaffles.addChoice(new DialogueChoice("How do I reach the cliff path from here?", "torvald_gate_topic"));
        torvaldRuinsBaffles.addChoice(new DialogueChoice("I am ready.", "dialogue_exit"));
        nodes.put(torvaldRuinsBaffles.getId(), torvaldRuinsBaffles);

        DialogueNode torvaldRuinsPath = new DialogueNode(
            "torvald_ruins_path",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Barely! The old suspension bridge has missing planks, but the steel support cables are solid. You'll need balance and guts to cross the chasm.",
            "audio/voices/torvald_ruins_path.mp3"
        );
        torvaldRuinsPath.addChoice(new DialogueChoice("I have traversed narrow ledges before. Let us proceed.", "torvald_intro"));
        torvaldRuinsPath.addChoice(new DialogueChoice("Let's get the gate unlocked first.", "torvald_gate_topic"));
        torvaldRuinsPath.addChoice(new DialogueChoice("Good to know.", "dialogue_exit"));
        nodes.put(torvaldRuinsPath.getId(), torvaldRuinsPath);

        DialogueNode torvaldRuinsExamine = new DialogueNode(
            "torvald_ruins_examine",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Do that. But don't linger too long near the edge, or a sudden gust will fling you into the frothing breakers.",
            "audio/voices/torvald_ruins_examine.mp3"
        );
        torvaldRuinsExamine.addChoice(new DialogueChoice("Understood. Let's discuss the gate.", "torvald_gate_topic"));
        torvaldRuinsExamine.addChoice(new DialogueChoice("Back to general questions.", "torvald_intro"));
        torvaldRuinsExamine.addChoice(new DialogueChoice("I'll be careful.", "dialogue_exit"));
        nodes.put(torvaldRuinsExamine.getId(), torvaldRuinsExamine);

        // --- TORVALD BRANCH 3: GATE TOPIC ---
        DialogueNode torvaldGateTopic = new DialogueNode(
            "torvald_gate_topic",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "That iron gate stays locked until I see you've got some sense in your skull! Fetch my brass lantern from the flooded boathouse down below, and maybe... just maybe, we'll talk about keys.",
            "audio/voices/torvald_gate_topic.mp3"
        );
        torvaldGateTopic.addChoice(new DialogueChoice(
            "Where in the flooded boathouse did you leave your lantern?",
            "torvald_gate_location"
        ));
        torvaldGateTopic.addChoice(new DialogueChoice(
            "Is the boathouse safe to enter, or has the tide submerged the flooring?",
            "torvald_gate_safety"
        ));
        torvaldGateTopic.addChoice(new DialogueChoice(
            "Keep that key ready, Torvald. I'll retrieve your lantern right away.",
            "torvald_gate_will_get"
        ));
        nodes.put(torvaldGateTopic.getId(), torvaldGateTopic);

        DialogueNode torvaldGateLocation = new DialogueNode(
            "torvald_gate_location",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Down the stone steps to the left. The lantern is on the workbench near the slipway. There's also my forged wrench down there—take it if you need tools, Astrid's pipes always need tightening.",
            "audio/voices/torvald_gate_location.mp3"
        );
        torvaldGateLocation.addChoice(new DialogueChoice(
            "A forged wrench will certainly come in handy.",
            "torvald_loc_wrench"
        ));
        torvaldGateLocation.addChoice(new DialogueChoice(
            "Did you leave any punch cards or instructions down there as well?",
            "torvald_loc_punchcard"
        ));
        torvaldGateLocation.addChoice(new DialogueChoice(
            "I'll be back shortly with the lantern.",
            "torvald_loc_go"
        ));
        nodes.put(torvaldGateLocation.getId(), torvaldGateLocation);

        DialogueNode torvaldLocWrench = new DialogueNode(
            "torvald_loc_wrench",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Solid English drop-forged steel. It'll open any rusted hydraulic nut or steam flange on this island without stripping the threads.",
            "audio/voices/torvald_loc_wrench.mp3"
        );
        torvaldLocWrench.addChoice(new DialogueChoice("Excellent. I'll grab it along with your lantern.", "torvald_intro"));
        torvaldLocWrench.addChoice(new DialogueChoice("What about the punch card?", "torvald_loc_punchcard"));
        torvaldLocWrench.addChoice(new DialogueChoice("Heading down now.", "dialogue_exit"));
        nodes.put(torvaldLocWrench.getId(), torvaldLocWrench);

        DialogueNode torvaldLocPunchcard = new DialogueNode(
            "torvald_loc_punchcard",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye, Astrid dropped a perforated parchment card on the workbench last week. Marked 'Protocol A-1: Clockwork Awakening'. Her brass robot UNIT-7 won't speak a syllable without it!",
            "audio/voices/torvald_loc_punchcard.mp3"
        );
        torvaldLocPunchcard.addChoice(new DialogueChoice("A vital clue! I will retrieve both the card and the wrench.", "torvald_intro"));
        torvaldLocPunchcard.addChoice(new DialogueChoice("Tell me more about UNIT-7.", "torvald_beacon_science"));
        torvaldLocPunchcard.addChoice(new DialogueChoice("On my way to the boathouse.", "dialogue_exit"));
        nodes.put(torvaldLocPunchcard.getId(), torvaldLocPunchcard);

        DialogueNode torvaldLocGo = new DialogueNode(
            "torvald_loc_go",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Good lad. Watch the damp stairs—they're coated in slick green algae. Don't break your neck before you get my lantern!",
            "audio/voices/torvald_loc_go.mp3"
        );
        torvaldLocGo.addChoice(new DialogueChoice("I'll return in a moment.", "dialogue_exit"));
        torvaldLocGo.addChoice(new DialogueChoice("One more question first.", "torvald_intro"));
        torvaldLocGo.addChoice(new DialogueChoice("Keep your eyes peeled.", "dialogue_exit"));
        nodes.put(torvaldLocGo.getId(), torvaldLocGo);

        DialogueNode torvaldGateSafety = new DialogueNode(
            "torvald_gate_safety",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "The tide is high, but the water's only ankle-deep over the slipway planks. What's unnatural is the color—blue glow shimmering in the salt puddles like spilled ink. Watch your step, don't slip on the kelp.",
            "audio/voices/torvald_gate_safety.mp3"
        );
        torvaldGateSafety.addChoice(new DialogueChoice(
            "Blue luminescence in the seawater... piezoelectric dissolved salts.",
            "torvald_safety_salts"
        ));
        torvaldGateSafety.addChoice(new DialogueChoice(
            "I'll tread carefully on the wet timber.",
            "torvald_safety_careful"
        ));
        torvaldGateSafety.addChoice(new DialogueChoice(
            "No obstacle will prevent me from reaching the lighthouse.",
            "torvald_safety_determined"
        ));
        nodes.put(torvaldGateSafety.getId(), torvaldGateSafety);

        DialogueNode torvaldSafetySalts = new DialogueNode(
            "torvald_safety_salts",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Call it whatever five-dollar word you want, young man. Just don't let it soak into your boots. Fetch the lantern and let's get you inland.",
            "audio/voices/torvald_safety_salts.mp3"
        );
        torvaldSafetySalts.addChoice(new DialogueChoice("On my way.", "dialogue_exit"));
        torvaldSafetySalts.addChoice(new DialogueChoice("I will be right back.", "dialogue_exit"));
        torvaldSafetySalts.addChoice(new DialogueChoice("Back to questions.", "torvald_intro"));
        nodes.put(torvaldSafetySalts.getId(), torvaldSafetySalts);

        DialogueNode torvaldSafetyCareful = new DialogueNode(
            "torvald_safety_careful",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Good sense. The sea has no mercy for careless boots.",
            "audio/voices/torvald_safety_careful.mp3"
        );
        torvaldSafetyCareful.addChoice(new DialogueChoice("Heading down now.", "dialogue_exit"));
        torvaldSafetyCareful.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        torvaldSafetyCareful.addChoice(new DialogueChoice("Back to options.", "torvald_intro"));
        nodes.put(torvaldSafetyCareful.getId(), torvaldSafetyCareful);

        DialogueNode torvaldSafetyDetermined = new DialogueNode(
            "torvald_safety_determined",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Determination is fine, as long as it's paired with patience. Go get that lantern.",
            "audio/voices/torvald_safety_determined.mp3"
        );
        torvaldSafetyDetermined.addChoice(new DialogueChoice("Right away.", "dialogue_exit"));
        torvaldSafetyDetermined.addChoice(new DialogueChoice("I'll be quick.", "dialogue_exit"));
        torvaldSafetyDetermined.addChoice(new DialogueChoice("Back to hub.", "torvald_intro"));
        nodes.put(torvaldSafetyDetermined.getId(), torvaldSafetyDetermined);

        DialogueNode torvaldGateWillGet = new DialogueNode(
            "torvald_gate_will_get",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "We'll see if your legs are as fast as your tongue, young scholar. The stairs to the boathouse are right behind you.",
            "audio/voices/torvald_gate_will_get.mp3"
        );
        torvaldGateWillGet.addChoice(new DialogueChoice("I'll return in a moment.", "dialogue_exit"));
        torvaldGateWillGet.addChoice(new DialogueChoice("Tell me once more about the gate.", "torvald_gate_topic"));
        torvaldGateWillGet.addChoice(new DialogueChoice("I am on it.", "dialogue_exit"));
        nodes.put(torvaldGateWillGet.getId(), torvaldGateWillGet);

        // --- TORVALD BRANCH 4: TELEGRAM TOPIC ---
        DialogueNode torvaldTelegramTopic = new DialogueNode(
            "torvald_telegram_topic",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Let me see that crumpled paper... 'Resonance exceeded mathematical thresholds... do not let the spire fall into silence.' Mother of mercy... she really wrote this. She never asks for help from anyone. She must be on the brink of catastrophe.",
            "audio/voices/torvald_telegram_topic.mp3"
        );
        torvaldTelegramTopic.addChoice(new DialogueChoice(
            "When was the last time you saw Astrid in person?",
            "torvald_tg_last_seen"
        ));
        torvaldTelegramTopic.addChoice(new DialogueChoice(
            "Did she speak of any visitors or unusual occurrences before sealing the tower?",
            "torvald_tg_visitors"
        ));
        torvaldTelegramTopic.addChoice(new DialogueChoice(
            "Now you understand the gravity of the situation. Open the gate, Torvald.",
            "torvald_tg_urgency"
        ));
        nodes.put(torvaldTelegramTopic.getId(), torvaldTelegramTopic);

        DialogueNode torvaldTgLastSeen = new DialogueNode(
            "torvald_tg_last_seen",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Three weeks ago. She came down to the dock at dusk, eyes hollowed out like she hadn't slept in a month. She handed me a pouch of dried cod and said: 'If the sky turns violet, Torvald, don't let anyone enter the core unless they bring the tuning fork.' Then she locked herself inside.",
            "audio/voices/torvald_tg_last_seen.mp3"
        );
        torvaldTgLastSeen.addChoice(new DialogueChoice(
            "The tuning fork... UNIT-7 must be holding it in the workshop.",
            "torvald_seen_fork"
        ));
        torvaldTgLastSeen.addChoice(new DialogueChoice(
            "She was preparing for this crisis weeks in advance.",
            "torvald_seen_prep"
        ));
        torvaldTgLastSeen.addChoice(new DialogueChoice(
            "I will bring her down safely, Torvald. I swear it.",
            "torvald_seen_swear"
        ));
        nodes.put(torvaldTgLastSeen.getId(), torvaldTgLastSeen);

        DialogueNode torvaldSeenFork = new DialogueNode(
            "torvald_seen_fork",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye, that mechanical contraption in her workshop. It was holding a silver fork that hummed on its own. UNIT-7 won't part with it unless you verify your identity.",
            "audio/voices/torvald_seen_fork.mp3"
        );
        torvaldSeenFork.addChoice(new DialogueChoice("I can verify my apprentice ID easily.", "torvald_intro"));
        torvaldSeenFork.addChoice(new DialogueChoice("Let's get past the iron gate first.", "torvald_gate_topic"));
        torvaldSeenFork.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(torvaldSeenFork.getId(), torvaldSeenFork);

        DialogueNode torvaldSeenPrep = new DialogueNode(
            "torvald_seen_prep",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "She knew the storm was coming, but her pride wouldn't let her abandon the experiments. Typical brilliant fool.",
            "audio/voices/torvald_seen_prep.mp3"
        );
        torvaldSeenPrep.addChoice(new DialogueChoice("She pushed the boundaries of human knowledge.", "torvald_intro"));
        torvaldSeenPrep.addChoice(new DialogueChoice("Now we must save her from those boundaries.", "torvald_gate_topic"));
        torvaldSeenPrep.addChoice(new DialogueChoice("I will do what must be done.", "dialogue_exit"));
        nodes.put(torvaldSeenPrep.getId(), torvaldSeenPrep);

        DialogueNode torvaldSeenSwear = new DialogueNode(
            "torvald_seen_swear",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "I hold you to that oath, Eivind. Now let's see you bring me that lantern so we can get to work.",
            "audio/voices/torvald_seen_swear.mp3"
        );
        torvaldSeenSwear.addChoice(new DialogueChoice("I am on it.", "dialogue_exit"));
        torvaldSeenSwear.addChoice(new DialogueChoice("The lantern will be here shortly.", "dialogue_exit"));
        torvaldSeenSwear.addChoice(new DialogueChoice("Back to questions.", "torvald_intro"));
        nodes.put(torvaldSeenSwear.getId(), torvaldSeenSwear);

        DialogueNode torvaldTgVisitors = new DialogueNode(
            "torvald_tg_visitors",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Only the government steam cutter that brought her supply crates two months ago. Heavy iron housings and glass prism components. When they unloaded that giant Azure Prism, the captain refused to even stay the night.",
            "audio/voices/torvald_tg_visitors.mp3"
        );
        torvaldTgVisitors.addChoice(new DialogueChoice(
            "The Azure Prism... that must be the optical core.",
            "torvald_vis_prism"
        ));
        torvaldTgVisitors.addChoice(new DialogueChoice(
            "Did the ship return to the mainland safely?",
            "torvald_vis_ship"
        ));
        torvaldTgVisitors.addChoice(new DialogueChoice(
            "No one else is coming to help. It is up to us.",
            "torvald_vis_resolve"
        ));
        nodes.put(torvaldTgVisitors.getId(), torvaldTgVisitors);

        DialogueNode torvaldVisPrism = new DialogueNode(
            "torvald_vis_prism",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye, a cut crystal bigger than a horse's head, glowing like an iceberg at midnight. That's the missing piece you'll need at the top.",
            "audio/voices/torvald_vis_prism.mp3"
        );
        torvaldVisPrism.addChoice(new DialogueChoice("I will find it wherever it rests.", "torvald_intro"));
        torvaldVisPrism.addChoice(new DialogueChoice("Let us open the gate.", "torvald_gate_topic"));
        torvaldVisPrism.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(torvaldVisPrism.getId(), torvaldVisPrism);

        DialogueNode torvaldVisShip = new DialogueNode(
            "torvald_vis_ship",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye, barely. Their magnetic compasses went haywire the moment they cleared the headland.",
            "audio/voices/torvald_vis_ship.mp3"
        );
        torvaldVisShip.addChoice(new DialogueChoice("The magnetic distortion covers the whole bay.", "torvald_intro"));
        torvaldVisShip.addChoice(new DialogueChoice("All the more reason to hurry.", "torvald_gate_topic"));
        torvaldVisShip.addChoice(new DialogueChoice("I will proceed.", "dialogue_exit"));
        nodes.put(torvaldVisShip.getId(), torvaldVisShip);

        DialogueNode torvaldVisResolve = new DialogueNode(
            "torvald_vis_resolve",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Just you, me, and whatever spirits are rattling inside that spire. Let's make it count.",
            "audio/voices/torvald_vis_resolve.mp3"
        );
        torvaldVisResolve.addChoice(new DialogueChoice("Let's unlock that gate.", "torvald_gate_topic"));
        torvaldVisResolve.addChoice(new DialogueChoice("I won't fail.", "dialogue_exit"));
        torvaldVisResolve.addChoice(new DialogueChoice("Back to questions.", "torvald_intro"));
        nodes.put(torvaldVisResolve.getId(), torvaldVisResolve);

        DialogueNode torvaldTgUrgency = new DialogueNode(
            "torvald_tg_urgency",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Aye, lad. I hear ya. But rules are rules on a stormy skerry: bring me that lantern so I can watch the mooring lines, and the raven key is yours.",
            "audio/voices/torvald_tg_urgency.mp3"
        );
        torvaldTgUrgency.addChoice(new DialogueChoice("I'm heading to the boathouse right now.", "dialogue_exit"));
        torvaldTgUrgency.addChoice(new DialogueChoice("Understood. The lantern will be in your hands shortly.", "dialogue_exit"));
        torvaldTgUrgency.addChoice(new DialogueChoice("Wait here, Torvald.", "dialogue_exit"));
        nodes.put(torvaldTgUrgency.getId(), torvaldTgUrgency);

        // --- TORVALD REWARD & POST-GATE ---
        DialogueNode torvaldReward = new DialogueNode(
            "torvald_unlock_reward",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Well, look at that! You actually brought it back in working condition! A man of your word, Eivind. Here—take the key with the raven sigil. It opens the wrought iron gate inland. Tread carefully, lad: the spire sings in the dead of night, and the stone trembles right beneath my boots!",
            "audio/voices/torvald_unlock_reward.mp3"
        );
        torvaldReward.setTriggerAction("TORVALD_GIVE_KEY");
        torvaldReward.addChoice(new DialogueChoice("Thank you, Torvald. I'll make sure Astrid's life's work doesn't destroy the island.", "dialogue_exit"));
        torvaldReward.addChoice(new DialogueChoice("Stay by the pier where it's sheltered. I'll return once the spire is stabilized.", "dialogue_exit"));
        torvaldReward.addChoice(new DialogueChoice("What should I look out for once I enter the cottage study?", "torvald_post_gate_advice"));
        torvaldReward.addChoice(new DialogueChoice("Did you hear any noise coming from the workshop machinery recently?", "torvald_post_workshop_hint"));
        nodes.put(torvaldReward.getId(), torvaldReward);

        DialogueNode torvaldPostGate = new DialogueNode(
            "torvald_post_gate_advice",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Astrid kept her research journals and the wall safe in the study. Look at the antique grandfather clock—it stopped when the first resonance pulse hit. The hands might hold the key to the combination.",
            "audio/voices/torvald_post_gate_advice.mp3"
        );
        torvaldPostGate.addChoice(new DialogueChoice("A stopped clock... I'll check the hands for numbers.", "dialogue_exit"));
        torvaldPostGate.addChoice(new DialogueChoice("Thank you for the crucial advice.", "dialogue_exit"));
        torvaldPostGate.addChoice(new DialogueChoice("I am heading through the gate now.", "dialogue_exit"));
        nodes.put(torvaldPostGate.getId(), torvaldPostGate);

        DialogueNode torvaldPostWorkshop = new DialogueNode(
            "torvald_post_workshop_hint",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Steam escaping like angry dragons. If her boilers are over-pressured, look for the relief manifold on the wall. That forged wrench from the boathouse will turn the hex bolt.",
            "audio/voices/torvald_post_workshop_hint.mp3"
        );
        torvaldPostWorkshop.addChoice(new DialogueChoice("I have the wrench in my inventory. I'll vent the pressure.", "dialogue_exit"));
        torvaldPostWorkshop.addChoice(new DialogueChoice("Understood. Off I go.", "dialogue_exit"));
        torvaldPostWorkshop.addChoice(new DialogueChoice("Farewell for now, Torvald.", "dialogue_exit"));
        nodes.put(torvaldPostWorkshop.getId(), torvaldPostWorkshop);


        // =========================================================================
        // 2. UNIT-7 (SCENE 4: CLOCKWORK WORKSHOP)
        // =========================================================================
        DialogueNode unit7Intro = new DialogueNode(
            "unit7_intro",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Protocol Seven initialized. Mechanical telemetry active. Entity identified: Holst, Eivind. State your operational inquiry.",
            "audio/voices/unit7_intro.mp3"
        );
        unit7Intro.addChoice(new DialogueChoice(
            "State your current diagnostic status and the condition of the spire.",
            "unit7_status_topic"
        ));
        unit7Intro.addChoice(new DialogueChoice(
            "Where is Professor Astrid Ravn, and why is the summit locked down?",
            "unit7_astrid_topic"
        ));
        unit7Intro.addChoice(new DialogueChoice(
            "Explain Protocol Seven and the nature of this 432 Hz resonance.",
            "unit7_resonance_topic"
        ));
        unit7Intro.addChoice(new DialogueChoice(
            "Authorization Verification: Confirm my engineer credentials and release required tools.",
            "unit7_auth_topic"
        ));
        nodes.put(unit7Intro.getId(), unit7Intro);
        nodes.put("unit7_root", unit7Intro);

        // --- UNIT-7 BRANCH 1: STATUS TOPIC ---
        DialogueNode unit7StatusTopic = new DialogueNode(
            "unit7_status_topic",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Chronometer desynchronized by 43.8 seconds. Hydraulic manifold pressure: 94% of burst threshold. Primary optical relay in generator room offline. Geological bedrock oscillating at hazardous sympathetic amplitudes.",
            "audio/voices/unit7_status_topic.mp3"
        );
        unit7StatusTopic.addChoice(new DialogueChoice(
            "Can the high-pressure steam manifold be vented manually using mechanical tools?",
            "unit7_status_steam"
        ));
        unit7StatusTopic.addChoice(new DialogueChoice(
            "What caused the optical relay to disconnect from the lighthouse beacon?",
            "unit7_status_prism_loss"
        ));
        unit7StatusTopic.addChoice(new DialogueChoice(
            "Explain the sympathetic oscillation of the bedrock. Is it seismic or electromagnetic?",
            "unit7_status_oscillation"
        ));
        nodes.put(unit7StatusTopic.getId(), unit7StatusTopic);

        DialogueNode unit7StatusSteam = new DialogueNode(
            "unit7_status_steam",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Affirmative. Steam relief valve on boiler wall requires mechanical torque from an English forged wrench. Once loosened, steam pressure will vent into cooling condenser, restoring auxiliary servo mobility.",
            "audio/voices/unit7_status_steam.mp3"
        );
        unit7StatusSteam.addChoice(new DialogueChoice(
            "The forged steel wrench from the boathouse will provide the torque needed.",
            "unit7_steam_wrench_ready"
        ));
        unit7StatusSteam.addChoice(new DialogueChoice(
            "What happens if boiler pressure continues to rise past critical?",
            "unit7_steam_danger"
        ));
        unit7StatusSteam.addChoice(new DialogueChoice(
            "Will venting steam restore full functionality to your servo limbs?",
            "unit7_steam_mobility"
        ));
        nodes.put(unit7StatusSteam.getId(), unit7StatusSteam);

        DialogueNode unit7SteamWrenchReady = new DialogueNode(
            "unit7_steam_wrench_ready",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Apply wrench to the hex valve assembly on the eastern wall. Counter-clockwise rotation of 90 degrees required.",
            "audio/voices/unit7_steam_wrench_ready.mp3"
        );
        unit7SteamWrenchReady.addChoice(new DialogueChoice("I will apply the wrench immediately.", "unit7_root"));
        unit7SteamWrenchReady.addChoice(new DialogueChoice("What about the optical relay?", "unit7_status_prism_loss"));
        unit7SteamWrenchReady.addChoice(new DialogueChoice("Standing by for maintenance.", "dialogue_exit"));
        nodes.put(unit7SteamWrenchReady.getId(), unit7SteamWrenchReady);

        DialogueNode unit7SteamDanger = new DialogueNode(
            "unit7_steam_danger",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Catastrophic boiler rupture estimated in 18 minutes. Concussive force will compromise the workshop structural integrity and sever dynamo feed cables.",
            "audio/voices/unit7_steam_danger.mp3"
        );
        unit7SteamDanger.addChoice(new DialogueChoice("Then I must vent the valve without delay!", "unit7_root"));
        unit7SteamDanger.addChoice(new DialogueChoice("How do I prevent electrical feedback?", "unit7_status_oscillation"));
        unit7SteamDanger.addChoice(new DialogueChoice("I will act immediately.", "dialogue_exit"));
        nodes.put(unit7SteamDanger.getId(), unit7SteamDanger);

        DialogueNode unit7SteamMobility = new DialogueNode(
            "unit7_steam_mobility",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Affirmative. Hydraulic actuators will regain 100% nominal pressure. I will be enabled to dispense the acoustic tuning fork.",
            "audio/voices/unit7_steam_mobility.mp3"
        );
        unit7SteamMobility.addChoice(new DialogueChoice("Then loosening the valve is priority one.", "unit7_root"));
        unit7SteamMobility.addChoice(new DialogueChoice("Tell me about that tuning fork.", "unit7_auth_topic"));
        unit7SteamMobility.addChoice(new DialogueChoice("Acknowledged.", "dialogue_exit"));
        nodes.put(unit7SteamMobility.getId(), unit7SteamMobility);

        DialogueNode unit7StatusPrismLoss = new DialogueNode(
            "unit7_status_prism_loss",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "The optical focus crystal was dislodged during the seismic tremor on the 14th. It plummeted into the sea gorge and was salvaged by the wreck of the 'North Star'. Without the Great Azure Prism, the spire cannot project a coherent focal beam.",
            "audio/voices/unit7_status_prism_loss.mp3"
        );
        unit7StatusPrismLoss.addChoice(new DialogueChoice(
            "The 'North Star' shipwreck in the cove... is the prism still intact there?",
            "unit7_prism_wreck"
        ));
        unit7StatusPrismLoss.addChoice(new DialogueChoice(
            "Could an ordinary optical glass lens substitute for the Great Azure Focus Prism?",
            "unit7_prism_glass"
        ));
        unit7StatusPrismLoss.addChoice(new DialogueChoice(
            "How does the prism mount into the rotating Fresnel turret?",
            "unit7_prism_mount"
        ));
        nodes.put(unit7StatusPrismLoss.getId(), unit7StatusPrismLoss);

        DialogueNode unit7PrismWreck = new DialogueNode(
            "unit7_prism_wreck",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Sensors confirm acoustic signature within the captain's ironbound sea chest on the shipwreck lower deck. Chest remains locked by standard brass deadbolt.",
            "audio/voices/unit7_prism_wreck.mp3"
        );
        unit7PrismWreck.addChoice(new DialogueChoice("I will explore the shipwreck and recover the prism.", "unit7_root"));
        unit7PrismWreck.addChoice(new DialogueChoice("How do I reach the shipwreck?", "unit7_astrid_topic"));
        unit7PrismWreck.addChoice(new DialogueChoice("Logging coordinates.", "dialogue_exit"));
        nodes.put(unit7PrismWreck.getId(), unit7PrismWreck);

        DialogueNode unit7PrismGlass = new DialogueNode(
            "unit7_prism_glass",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Negative. Conventional borosilicate or flint glass would shatter within 3.4 seconds under gigavolt electromagnetic flux. Only hyper-pure extraterrestrial sapphire lattice can withstand the energy.",
            "audio/voices/unit7_prism_glass.mp3"
        );
        unit7PrismGlass.addChoice(new DialogueChoice("Then the Great Azure Prism is truly irreplaceable.", "unit7_root"));
        unit7PrismGlass.addChoice(new DialogueChoice("Where was the sapphire synthesized?", "unit7_resonance_topic"));
        unit7PrismGlass.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(unit7PrismGlass.getId(), unit7PrismGlass);

        DialogueNode unit7PrismMount = new DialogueNode(
            "unit7_prism_mount",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "The prism slots directly into the central gimbal socket atop Chapter 10's core pedestal. Once seated, it unlocks the three Master Control Levers on the dais.",
            "audio/voices/unit7_prism_mount.mp3"
        );
        unit7PrismMount.addChoice(new DialogueChoice("A mechanical interlock. Brilliant engineering.", "unit7_root"));
        unit7PrismMount.addChoice(new DialogueChoice("Tell me about the Master Control Levers.", "unit7_astrid_topic"));
        unit7PrismMount.addChoice(new DialogueChoice("I will install it once retrieved.", "dialogue_exit"));
        nodes.put(unit7PrismMount.getId(), unit7PrismMount);

        DialogueNode unit7StatusOscillation = new DialogueNode(
            "unit7_status_oscillation",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Both. The subterranean geode is piezoelectric. Tectonic compression generates gigavolt potentials, while electromagnetic pulses cause physical crystal expansion. A closed positive feedback loop. Without damping, tectonic collapse is inevitable.",
            "audio/voices/unit7_status_oscillation.mp3"
        );
        unit7StatusOscillation.addChoice(new DialogueChoice(
            "A closed feedback loop... how do we dampen the runaway amplitude?",
            "unit7_osc_dampen"
        ));
        unit7StatusOscillation.addChoice(new DialogueChoice(
            "Can the energy be diverted into the upper ionosphere instead?",
            "unit7_osc_divert"
        ));
        unit7StatusOscillation.addChoice(new DialogueChoice(
            "Is a sustainable harmonic balance theoretically achievable?",
            "unit7_osc_balance"
        ));
        nodes.put(unit7StatusOscillation.getId(), unit7StatusOscillation);

        DialogueNode unit7OscDampen = new DialogueNode(
            "unit7_osc_dampen",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Controlled damping: Copper Lever on the Master Console. Reroutes voltage into the ocean floor grounding grid. Safest resolution, but permanent deactivation of spire.",
            "audio/voices/unit7_osc_dampen.mp3"
        );
        unit7OscDampen.addChoice(new DialogueChoice("A prudent sacrifice if all else fails.", "unit7_root"));
        unit7OscDampen.addChoice(new DialogueChoice("What of the other two outcomes?", "unit7_astrid_topic"));
        unit7OscDampen.addChoice(new DialogueChoice("Recorded in journal.", "dialogue_exit"));
        nodes.put(unit7OscDampen.getId(), unit7OscDampen);

        DialogueNode unit7OscDivert = new DialogueNode(
            "unit7_osc_divert",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Cosmic transmission: Crystal Lever on the Master Console. Gimbals tilt the Fresnel array 90 degrees skyward, broadcasting harmonic pulse into ionospheric waveguide.",
            "audio/voices/unit7_osc_divert.mp3"
        );
        unit7OscDivert.addChoice(new DialogueChoice("An astronomical broadcast to the stars!", "unit7_root"));
        unit7OscDivert.addChoice(new DialogueChoice("Will it prevent the seismic collapse?", "unit7_status_topic"));
        unit7OscDivert.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(unit7OscDivert.getId(), unit7OscDivert);

        DialogueNode unit7OscBalance = new DialogueNode(
            "unit7_osc_balance",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Harmonic integration: Silver Lever. Calibrates frequency to exact 432 Hz bedrock fundamental. Enables boundless clean energy, but requires zero margin of error.",
            "audio/voices/unit7_osc_balance.mp3"
        );
        unit7OscBalance.addChoice(new DialogueChoice("The ideal scientific equilibrium.", "unit7_root"));
        unit7OscBalance.addChoice(new DialogueChoice("Who makes this final decision?", "unit7_astrid_topic"));
        unit7OscBalance.addChoice(new DialogueChoice("I will decide at the summit.", "dialogue_exit"));
        nodes.put(unit7OscBalance.getId(), unit7OscBalance);

        // --- UNIT-7 BRANCH 2: ASTRID TOPIC ---
        DialogueNode unit7AstridTopic = new DialogueNode(
            "unit7_astrid_topic",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Creator Ravn ascended to the Celestial Observatory 504 hours ago. She enacted Security Protocol Omega, locking the hoist and blast doors. Her vital telemetry indicates severe sleep deprivation and elevated cortisol.",
            "audio/voices/unit7_astrid_topic.mp3"
        );
        unit7AstridTopic.addChoice(new DialogueChoice(
            "Why would Astrid seal herself in the observatory away from everyone?",
            "unit7_astrid_why"
        ));
        unit7AstridTopic.addChoice(new DialogueChoice(
            "Can you transmit a diagnostic override to Security Protocol Omega from this console?",
            "unit7_astrid_override"
        ));
        unit7AstridTopic.addChoice(new DialogueChoice(
            "Did she leave any recorded equations or engineering logs for me?",
            "unit7_astrid_logs"
        ));
        nodes.put(unit7AstridTopic.getId(), unit7AstridTopic);

        DialogueNode unit7AstridWhy = new DialogueNode(
            "unit7_astrid_why",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Her logs state: 'If the resonance breaches quarantine, no one else must be exposed to the harmonic feedback.' She sought to contain the anomaly alone to prevent civilian casualties.",
            "audio/voices/unit7_astrid_why.mp3"
        );
        unit7AstridWhy.addChoice(new DialogueChoice("A selfless intention, but catastrophic in isolation.", "unit7_root"));
        unit7AstridWhy.addChoice(new DialogueChoice("Can we unlock the hoist to reach her?", "unit7_astrid_override"));
        unit7AstridWhy.addChoice(new DialogueChoice("I must get to her at once.", "dialogue_exit"));
        nodes.put(unit7AstridWhy.getId(), unit7AstridWhy);

        DialogueNode unit7AstridOverride = new DialogueNode(
            "unit7_astrid_override",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Direct wireless override failed due to electromagnetic storm. Physical path: Exit workshop blast door to Sea Cliff, cross suspension bridge, enter Lighthouse Staircase via Runic Portal.",
            "audio/voices/unit7_astrid_override.mp3"
        );
        unit7AstridOverride.addChoice(new DialogueChoice("The blast door to the cliff... is it unlocked?", "unit7_root"));
        unit7AstridOverride.addChoice(new DialogueChoice("What awaits me in the lighthouse staircase?", "unit7_status_topic"));
        unit7AstridOverride.addChoice(new DialogueChoice("I will take the cliff route.", "dialogue_exit"));
        nodes.put(unit7AstridOverride.getId(), unit7AstridOverride);

        DialogueNode unit7AstridLogs = new DialogueNode(
            "unit7_astrid_logs",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Log excerpt: 'Tell Eivind that the three levers control the three harmonic outcomes: Silver tunes the crystal, Copper grounds the charge, Crystal broadcasts to the cosmos.'",
            "audio/voices/unit7_astrid_logs.mp3"
        );
        unit7AstridLogs.addChoice(new DialogueChoice("Silver, Copper, Crystal... three different futures for the island.", "unit7_root"));
        unit7AstridLogs.addChoice(new DialogueChoice("Did she express a preference among the three levers?", "unit7_osc_balance"));
        unit7AstridLogs.addChoice(new DialogueChoice("I will study the core console when I reach the pinnacle.", "dialogue_exit"));
        nodes.put(unit7AstridLogs.getId(), unit7AstridLogs);

        // --- UNIT-7 BRANCH 3: RESONANCE TOPIC ---
        DialogueNode unit7ResonanceTopic = new DialogueNode(
            "unit7_resonance_topic",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "The spire was erected upon a natural piezoelectric batholith. At 432 Hertz, the mineral lattice aligns with Earth's geomagnetic field. Astrid believed this frequency could heal oceanic ecosystems and power clean industries across the continent.",
            "audio/voices/unit7_resonance_topic.mp3"
        );
        unit7ResonanceTopic.addChoice(new DialogueChoice(
            "432 Hertz... the natural harmonic vibration of crystalline quartz.",
            "unit7_res_quartz"
        ));
        unit7ResonanceTopic.addChoice(new DialogueChoice(
            "Why did the beacon's radiance shift from warm white to deep azure?",
            "unit7_res_blue_light"
        ));
        unit7ResonanceTopic.addChoice(new DialogueChoice(
            "If 432 Hz restores stability, what frequency is inducing the earthquakes?",
            "unit7_res_destructive_freq"
        ));
        nodes.put(unit7ResonanceTopic.getId(), unit7ResonanceTopic);

        DialogueNode unit7ResQuartz = new DialogueNode(
            "unit7_res_quartz",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Correct. When tuned precisely to 432 Hz, stress fractures in the crystal lattice seal via sympathetic recrystallization. The tuning fork in my storage cavity is calibrated to this exact pitch.",
            "audio/voices/unit7_res_quartz.mp3"
        );
        unit7ResQuartz.addChoice(new DialogueChoice("Release the tuning fork to me, UNIT-7.", "unit7_auth_topic"));
        unit7ResQuartz.addChoice(new DialogueChoice("How do I apply the tuning fork?", "unit7_auth_topic"));
        unit7ResQuartz.addChoice(new DialogueChoice("Acknowledged.", "dialogue_exit"));
        nodes.put(unit7ResQuartz.getId(), unit7ResQuartz);

        DialogueNode unit7ResBlueLight = new DialogueNode(
            "unit7_res_blue_light",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Azure luminescence corresponds to Cherenkov-like radiation in highly ionized piezoelectric gas. When the optical focus crystal dislodged, the beam became unfocused and began ionizing ambient air.",
            "audio/voices/unit7_res_blue_light.mp3"
        );
        unit7ResBlueLight.addChoice(new DialogueChoice("Reinstalling the prism will re-focus the beam.", "unit7_status_prism_loss"));
        unit7ResBlueLight.addChoice(new DialogueChoice("Is the blue glow dangerous to biological tissue?", "unit7_root"));
        unit7ResBlueLight.addChoice(new DialogueChoice("Clear explanation. Thank you.", "dialogue_exit"));
        nodes.put(unit7ResBlueLight.getId(), unit7ResBlueLight);

        DialogueNode unit7ResDestructive = new DialogueNode(
            "unit7_res_destructive_freq",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Disharmonic sub-harmonic: 17.4 Hz. Infrasonic standing waves creating mechanical shear stress along fault line Alpha. Destructive amplitude increases 1.8% per hour.",
            "audio/voices/unit7_res_destructive_freq.mp3"
        );
        unit7ResDestructive.addChoice(new DialogueChoice("17.4 Hz is dangerously close to rock shear resonance.", "unit7_root"));
        unit7ResDestructive.addChoice(new DialogueChoice("We must counteract it with the 432 Hz tuning fork.", "unit7_auth_topic"));
        unit7ResDestructive.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(unit7ResDestructive.getId(), unit7ResDestructive);

        // --- UNIT-7 BRANCH 4: AUTH & REWARD ---
        DialogueNode unit7AuthTopic = new DialogueNode(
            "unit7_auth_topic",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Vocal biometric match confirmed. User: Holst, Eivind. Master's thesis on 'Nonlinear Acoustic Propagation in Geothermal Quartz'. Dispensing emergency acoustic calibration unit: Resonance Tuning Fork (432 Hz).",
            "audio/voices/unit7_auth_topic.mp3"
        );
        unit7AuthTopic.setTriggerAction("UNIT7_GIVE_FORK");
        unit7AuthTopic.addChoice(new DialogueChoice(
            "How do I deploy this acoustic tuning fork inside the crystal caverns?",
            "unit7_auth_fork_deploy"
        ));
        unit7AuthTopic.addChoice(new DialogueChoice(
            "What is the sequence of barriers between this workshop and the core?",
            "unit7_auth_barriers"
        ));
        unit7AuthTopic.addChoice(new DialogueChoice(
            "Thank you, UNIT-7. Maintain diagnostic monitoring until I reach the top.",
            "unit7_auth_monitor"
        ));
        nodes.put(unit7AuthTopic.getId(), unit7AuthTopic);

        DialogueNode unit7ForkDeploy = new DialogueNode(
            "unit7_auth_fork_deploy",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Locate the piezoelectric crystal altar in Chapter 8. Strike the tuning fork and seat it in the pedestal socket. The sympathetic acoustic vibration will align the lattice and energize the express lift directly to the observatory.",
            "audio/voices/unit7_auth_fork_deploy.mp3"
        );
        unit7ForkDeploy.addChoice(new DialogueChoice("An express hoist directly from caverns to observatory? Excellent.", "unit7_root"));
        unit7ForkDeploy.addChoice(new DialogueChoice("Will the tuning fork be consumed or damaged by the resonance?", "unit7_root"));
        unit7ForkDeploy.addChoice(new DialogueChoice("I will harmonize the caverns as soon as I descend the staircase.", "dialogue_exit"));
        nodes.put(unit7ForkDeploy.getId(), unit7ForkDeploy);

        DialogueNode unit7Barriers = new DialogueNode(
            "unit7_auth_barriers",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Sequence: 1. Workshop blast door to Sea Cliff. 2. Runic gateway to Staircase. 3. Shipwreck in bay holds Azure Prism. 4. Caverns altar requires Tuning Fork. 5. Summit core requires Prism installation.",
            "audio/voices/unit7_auth_barriers.mp3"
        );
        unit7Barriers.addChoice(new DialogueChoice("Clear and logical roadmap. Thank you, UNIT-7.", "unit7_root"));
        unit7Barriers.addChoice(new DialogueChoice("Review diagnostic status again.", "unit7_status_topic"));
        unit7Barriers.addChoice(new DialogueChoice("I am setting out.", "dialogue_exit"));
        nodes.put(unit7Barriers.getId(), unit7Barriers);

        DialogueNode unit7Monitor = new DialogueNode(
            "unit7_auth_monitor",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Telemetry relays primed. May your scientific equations prove resilient against the abyss, Apprentice Holst.",
            "audio/voices/unit7_auth_monitor.mp3"
        );
        unit7Monitor.addChoice(new DialogueChoice("Farewell, UNIT-7.", "dialogue_exit"));
        unit7Monitor.addChoice(new DialogueChoice("I will restore the spire.", "dialogue_exit"));
        unit7Monitor.addChoice(new DialogueChoice("One final question.", "unit7_root"));
        nodes.put(unit7Monitor.getId(), unit7Monitor);

        // Special interactions on UNIT-7
        DialogueNode unit7CardAwaken = new DialogueNode(
            "unit7_card_awaken",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "*CHEST READER ENGAGED* Perforated tape read successfully. 'Protocol A-1: Clockwork Awakening'. Memory registers restored to 100%. Master logic reboot complete!",
            "audio/voices/unit7_card_awaken.mp3"
        );
        unit7CardAwaken.addChoice(new DialogueChoice("UNIT-7! State your status.", "unit7_status_topic"));
        unit7CardAwaken.addChoice(new DialogueChoice("Where is Professor Ravn?", "unit7_astrid_topic"));
        unit7CardAwaken.addChoice(new DialogueChoice("Dispense the resonance calibration equipment.", "unit7_auth_topic"));
        unit7CardAwaken.addChoice(new DialogueChoice("Let us discuss the 432 Hz frequency.", "unit7_resonance_topic"));
        nodes.put(unit7CardAwaken.getId(), unit7CardAwaken);

        DialogueNode unit7TelegramScan = new DialogueNode(
            "unit7_telegram_scan",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "*OPTICAL SCANNER ACTIVE* Astrid Ravn signature verified: cryptographic code 9-B-7. Eivind Holst authorization acknowledged as Primary Emergency Technician.",
            "audio/voices/unit7_telegram_scan.mp3"
        );
        unit7TelegramScan.addChoice(new DialogueChoice("Status report, please.", "unit7_status_topic"));
        unit7TelegramScan.addChoice(new DialogueChoice("Release the tuning fork.", "unit7_auth_topic"));
        unit7TelegramScan.addChoice(new DialogueChoice("Tell me about the beacon.", "unit7_resonance_topic"));
        unit7TelegramScan.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(unit7TelegramScan.getId(), unit7TelegramScan);


        // =========================================================================
        // 3. PROFESSOR ASTRID RAVN (SCENE 9: CELESTIAL OBSERVATORY)
        // =========================================================================
        DialogueNode astridIntro = new DialogueNode(
            "astrid_intro",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Eivind... you actually came. I knew deep down you could never resist an unsolved equation. Look out the panoramic windows: the sea is ablaze with our light. But time has run out.",
            "audio/voices/astrid_intro.mp3"
        );
        astridIntro.addChoice(new DialogueChoice(
            "Astrid! Look at the seismograph—the island is on the brink of tectonic disintegration!",
            "astrid_collapse_topic"
        ));
        astridIntro.addChoice(new DialogueChoice(
            "Explain your grand discovery to me. What have you unearthed beneath the sea?",
            "astrid_discovery_topic"
        ));
        astridIntro.addChoice(new DialogueChoice(
            "I recovered the Great Azure Focus Prism and UNIT-7's Tuning Fork. How do we configure the core?",
            "astrid_core_config_topic"
        ));
        astridIntro.addChoice(new DialogueChoice(
            "Why did you summon me here, Astrid? What judgment are you asking me to make?",
            "astrid_judgment_topic"
        ));
        nodes.put(astridIntro.getId(), astridIntro);
        nodes.put("astrid_root", astridIntro);

        // --- ASTRID BRANCH 1: COLLAPSE TOPIC ---
        DialogueNode astridCollapseTopic = new DialogueNode(
            "astrid_collapse_topic",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Do you think I am blind to the dials, Eivind?! I have watched every fracture propagate through the granite. But if we sever the circuit instantaneously, the stored electromagnetic induction will discharge into the mantle like a forty-megaton thunderbolt!",
            "audio/voices/astrid_collapse_topic.mp3"
        );
        astridCollapseTopic.addChoice(new DialogueChoice(
            "An abrupt shutdown is unthinkable. We must bleed off the charge through controlled damping.",
            "astrid_col_damping"
        ));
        astridCollapseTopic.addChoice(new DialogueChoice(
            "Can the Fresnel lens project the excess energy into the ionosphere?",
            "astrid_col_cosmic"
        ));
        astridCollapseTopic.addChoice(new DialogueChoice(
            "What if we don't discharge it, but harmonize the core into permanent equilibrium?",
            "astrid_col_harmonic"
        ));
        nodes.put(astridCollapseTopic.getId(), astridCollapseTopic);

        DialogueNode astridColDamping = new DialogueNode(
            "astrid_col_damping",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Controlled damping... The Copper Lever on the master console. It routes the gigavolt potential through the submarine copper grounding grid, cooling the batholith over 48 hours. But it permanently silences the spire and burns out our generator coils.",
            "audio/voices/astrid_col_damping.mp3"
        );
        astridColDamping.addChoice(new DialogueChoice(
            "Silencing the spire saves every life along the coast. It is our moral obligation.",
            "astrid_damp_moral"
        ));
        astridColDamping.addChoice(new DialogueChoice(
            "Twenty years of scientific breakthroughs... sacrificed with a single lever pull.",
            "astrid_damp_loss"
        ));
        astridColDamping.addChoice(new DialogueChoice(
            "Will the subterranean copper grid withstand the thermal dissipation without melting?",
            "astrid_damp_heat"
        ));
        nodes.put(astridColDamping.getId(), astridColDamping);

        DialogueNode astridDampMoral = new DialogueNode(
            "astrid_damp_moral",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "You are right, Eivind. Science without conscience is nothing but magnificent arson. If you choose the Copper Lever, I will not stand in your way.",
            "audio/voices/astrid_damp_moral.mp3"
        );
        astridDampMoral.addChoice(new DialogueChoice("Let us discuss the other outcomes before choosing.", "astrid_root"));
        astridDampMoral.addChoice(new DialogueChoice("How is the core chamber unlocked?", "astrid_core_config_topic"));
        astridDampMoral.addChoice(new DialogueChoice("I am ready to enter the core.", "dialogue_exit"));
        nodes.put(astridDampMoral.getId(), astridDampMoral);

        DialogueNode astridDampLoss = new DialogueNode(
            "astrid_damp_loss",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "It hurts deeper than I can put into words. But human lives will always outweigh brass and glass. If that is your verdict, my hands will not tremble on the copper handle.",
            "audio/voices/astrid_damp_loss.mp3"
        );
        astridDampLoss.addChoice(new DialogueChoice("What about the harmonic integration option?", "astrid_col_harmonic"));
        astridDampLoss.addChoice(new DialogueChoice("Back to our choices.", "astrid_root"));
        astridDampLoss.addChoice(new DialogueChoice("Let us proceed to the core.", "dialogue_exit"));
        nodes.put(astridDampLoss.getId(), astridDampLoss);

        DialogueNode astridDampHeat = new DialogueNode(
            "astrid_damp_heat",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "The North Sea itself acts as our heat sink. The ocean will boil over the reef for an hour, but the bedrock will remain structurally sound.",
            "audio/voices/astrid_damp_heat.mp3"
        );
        astridDampHeat.addChoice(new DialogueChoice("A viable technical contingency.", "astrid_root"));
        astridDampHeat.addChoice(new DialogueChoice("Tell me about the Cosmic Transmission.", "astrid_col_cosmic"));
        astridDampHeat.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(astridDampHeat.getId(), astridDampHeat);

        DialogueNode astridColCosmic = new DialogueNode(
            "astrid_col_cosmic",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Cosmic transmission... The Crystal Lever! It tilts the entire Fresnel array upward into the night sky, broadcasting the harmonic pulse into the upper ionosphere. The energy bypasses the bedrock entirely, turning Raven Island into a permanent celestial lighthouse visible across oceans!",
            "audio/voices/astrid_col_cosmic.mp3"
        );
        astridColCosmic.addChoice(new DialogueChoice(
            "A celestial beacon reaching into the stratosphere... visible across the North Sea!",
            "astrid_cosm_beacon"
        ));
        astridColCosmic.addChoice(new DialogueChoice(
            "Is there any danger of atmospheric ionization altering oceanic weather patterns?",
            "astrid_cosm_weather"
        ));
        astridColCosmic.addChoice(new DialogueChoice(
            "It transforms a potential disaster into a triumphant monument to human curiosity.",
            "astrid_cosm_monument"
        ));
        nodes.put(astridColCosmic.getId(), astridColCosmic);

        DialogueNode astridCosmBeacon = new DialogueNode(
            "astrid_cosm_beacon",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "More than that: an astronomical transmitter. A message from Earth singing into the deep dark of space. The bedrock pressure is instantly relieved, and the light endures forever.",
            "audio/voices/astrid_cosm_beacon.mp3"
        );
        astridCosmBeacon.addChoice(new DialogueChoice("A breathtaking vision, Astrid.", "astrid_root"));
        astridCosmBeacon.addChoice(new DialogueChoice("What does the Silver Lever do?", "astrid_col_harmonic"));
        astridCosmBeacon.addChoice(new DialogueChoice("I will consider this at the levers.", "dialogue_exit"));
        nodes.put(astridCosmBeacon.getId(), astridCosmBeacon);

        DialogueNode astridCosmWeather = new DialogueNode(
            "astrid_cosm_weather",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Minor auroral displays over Scandinavia, but zero meteorological hazard. The ionosphere absorbs and redistributes electromagnetic flux across thousands of square kilometers.",
            "audio/voices/astrid_cosm_weather.mp3"
        );
        astridCosmWeather.addChoice(new DialogueChoice("Scientifically reassuring.", "astrid_root"));
        astridCosmWeather.addChoice(new DialogueChoice("Let's review the harmonic option.", "astrid_col_harmonic"));
        astridCosmWeather.addChoice(new DialogueChoice("Let's go up.", "dialogue_exit"));
        nodes.put(astridCosmWeather.getId(), astridCosmWeather);

        DialogueNode astridCosmMonument = new DialogueNode(
            "astrid_cosm_monument",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "That is what we dared to reach for when we built this tower. Not just to warn ships of rocks, but to remind humanity that we belong to the cosmos.",
            "audio/voices/astrid_cosm_monument.mp3"
        );
        astridCosmMonument.addChoice(new DialogueChoice("Inspiring words. Let's discuss all options.", "astrid_root"));
        astridCosmMonument.addChoice(new DialogueChoice("How do we prepare the core?", "astrid_core_config_topic"));
        astridCosmMonument.addChoice(new DialogueChoice("To the core chamber!", "dialogue_exit"));
        nodes.put(astridCosmMonument.getId(), astridCosmMonument);

        DialogueNode astridColHarmonic = new DialogueNode(
            "astrid_col_harmonic",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Harmonic integration... The Silver Lever. That was my original dream, Eivind. Tuning the spire's optical core to sympathetic 432 Hz resonance with the Earth's magnetic heartbeat. A perpetual fountain of clean, restorative resonance.",
            "audio/voices/astrid_col_harmonic.mp3"
        );
        astridColHarmonic.addChoice(new DialogueChoice(
            "Permanent harmonic balance requires micro-millimeter precision in the prism turret.",
            "astrid_harm_precision"
        ));
        astridColHarmonic.addChoice(new DialogueChoice(
            "If we succeed, Raven Island becomes a fountainhead of boundless, clean energy.",
            "astrid_harm_clean"
        ));
        astridColHarmonic.addChoice(new DialogueChoice(
            "And if the mineral lattice destabilizes again next month or next year?",
            "astrid_harm_risk"
        ));
        nodes.put(astridColHarmonic.getId(), astridColHarmonic);

        DialogueNode astridHarmPrecision = new DialogueNode(
            "astrid_harm_precision",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Yes. The Great Azure Focus Prism must be seated exactly into the central gimbal. If it is even a hair off-axis, destructive refraction will resume. But with your steady hands, I know we can achieve it.",
            "audio/voices/astrid_harm_precision.mp3"
        );
        astridHarmPrecision.addChoice(new DialogueChoice("I have calibrated optical lenses for years. We can do this.", "astrid_root"));
        astridHarmPrecision.addChoice(new DialogueChoice("What about the other lever choices?", "astrid_collapse_topic"));
        astridHarmPrecision.addChoice(new DialogueChoice("Let us ascend to the core.", "dialogue_exit"));
        nodes.put(astridHarmPrecision.getId(), astridHarmPrecision);

        DialogueNode astridHarmClean = new DialogueNode(
            "astrid_harm_clean",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Energy to heat cities, power vessels, and illuminate dark winters without burning a single pound of coal. A future worthy of our calling.",
            "audio/voices/astrid_harm_clean.mp3"
        );
        astridHarmClean.addChoice(new DialogueChoice("A noble vision. Let's make sure the prism is ready.", "astrid_core_config_topic"));
        astridHarmClean.addChoice(new DialogueChoice("Back to our main discussion.", "astrid_root"));
        astridHarmClean.addChoice(new DialogueChoice("To the pinnacle!", "dialogue_exit"));
        nodes.put(astridHarmClean.getId(), astridHarmClean);

        DialogueNode astridHarmRisk = new DialogueNode(
            "astrid_harm_risk",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "There is always risk at the frontiers of science. But fear must never dictate humanity's boundaries. Still... the final choice rests in your hands.",
            "audio/voices/astrid_harm_risk.mp3"
        );
        astridHarmRisk.addChoice(new DialogueChoice("I appreciate your trust in my judgment.", "astrid_judgment_topic"));
        astridHarmRisk.addChoice(new DialogueChoice("Let's review the procedures.", "astrid_core_config_topic"));
        astridHarmRisk.addChoice(new DialogueChoice("Let us proceed.", "dialogue_exit"));
        nodes.put(astridHarmRisk.getId(), astridHarmRisk);

        // --- ASTRID BRANCH 2: DISCOVERY TOPIC ---
        DialogueNode astridDiscoveryTopic = new DialogueNode(
            "astrid_discovery_topic",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Deep in the undersea trenches beneath Raven Island lies an ancient hyper-resonant silicate batholith. It isn't just mineral; it's a colossal piezoelectric capacitor that absorbs and transmutes oceanic kinetic energy. I built this lighthouse not merely as a light, but as an organ to play the earth's silent symphony.",
            "audio/voices/astrid_discovery_topic.mp3"
        );
        astridDiscoveryTopic.addChoice(new DialogueChoice(
            "How did you first detect the fundamental acoustic frequency of the batholith?",
            "astrid_disc_detect"
        ));
        astridDiscoveryTopic.addChoice(new DialogueChoice(
            "Did the naval authorities or university sponsor this experimental lighthouse?",
            "astrid_disc_sponsor"
        ));
        astridDiscoveryTopic.addChoice(new DialogueChoice(
            "Did you ever consider that some primeval energies were never meant to be harnessed?",
            "astrid_disc_hubris"
        ));
        nodes.put(astridDiscoveryTopic.getId(), astridDiscoveryTopic);

        DialogueNode astridDiscDetect = new DialogueNode(
            "astrid_disc_detect",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Through seismographic hydrophones. When winter gales struck the western reef, the ocean floor didn't generate noise—it generated pure sinusoidal octaves. That was the moment I realized the island was an acoustic crystal.",
            "audio/voices/astrid_disc_detect.mp3"
        );
        astridDiscDetect.addChoice(new DialogueChoice("A natural sonic resonator... incredible.", "astrid_root"));
        astridDiscDetect.addChoice(new DialogueChoice("Did you share these findings with the Royal Academy?", "astrid_disc_sponsor"));
        astridDiscDetect.addChoice(new DialogueChoice("Let us prepare the core.", "astrid_core_config_topic"));
        nodes.put(astridDiscDetect.getId(), astridDiscDetect);

        DialogueNode astridDiscSponsor = new DialogueNode(
            "astrid_disc_sponsor",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "The Naval Board thought they were funding a conventional kerosene revolving light. When I requested custom optical prisms of astronomical purity, they began asking questions. That is why I withdrew to this skerry alone.",
            "audio/voices/astrid_disc_sponsor.mp3"
        );
        astridDiscSponsor.addChoice(new DialogueChoice("Bureaucrats rarely understand revolutionary science.", "astrid_root"));
        astridDiscSponsor.addChoice(new DialogueChoice("Isolation can breed dangerous blind spots.", "astrid_disc_hubris"));
        astridDiscSponsor.addChoice(new DialogueChoice("Understood.", "dialogue_exit"));
        nodes.put(astridDiscSponsor.getId(), astridDiscSponsor);

        DialogueNode astridDiscHubris = new DialogueNode(
            "astrid_disc_hubris",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Every night I looked out at this sea and asked myself that very question. Perhaps it was hubris. But if our ancestors had feared fire, we would still be shivering in dark caves. Knowledge must always be pursued.",
            "audio/voices/astrid_disc_hubris.mp3"
        );
        astridDiscHubris.addChoice(new DialogueChoice("Wisdom lies in knowing when to restrain power.", "astrid_judgment_topic"));
        astridDiscHubris.addChoice(new DialogueChoice("Back to our choices at hand.", "astrid_root"));
        astridDiscHubris.addChoice(new DialogueChoice("To the levers.", "dialogue_exit"));
        nodes.put(astridDiscHubris.getId(), astridDiscHubris);

        // --- ASTRID BRANCH 3: CORE CONFIG TOPIC ---
        DialogueNode astridCoreConfigTopic = new DialogueNode(
            "astrid_core_config_topic",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "You found the Azure Prism in the North Star wreckage?! And UNIT-7 surrendered the 432 Hz tuning fork... You've done the impossible, Eivind! With those two artifacts, the core chamber above us can finally be unlocked.",
            "audio/voices/astrid_core_config_topic.mp3"
        );
        astridCoreConfigTopic.setTriggerAction("UNLOCK_CORE_ACCESS");
        astridCoreConfigTopic.addChoice(new DialogueChoice(
            "Detail the exact sequence once I climb into the revolving Fresnel chamber.",
            "astrid_cfg_sequence"
        ));
        astridCoreConfigTopic.addChoice(new DialogueChoice(
            "Is the revolving lens clockwork sturdy enough to support the prism's optical torque?",
            "astrid_cfg_clockwork"
        ));
        astridCoreConfigTopic.addChoice(new DialogueChoice(
            "Come with me to the lantern chamber, Astrid. We will face this together.",
            "astrid_cfg_together"
        ));
        nodes.put(astridCoreConfigTopic.getId(), astridCoreConfigTopic);

        DialogueNode astridCfgSequence = new DialogueNode(
            "astrid_cfg_sequence",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "1. Climb the brass ladder to Chapter 10. 2. Seat the Great Azure Focus Prism into the central gimbal socket. 3. Step down to the Master Console dais. 4. Select your lever: Silver, Copper, or Crystal.",
            "audio/voices/astrid_cfg_sequence.mp3"
        );
        astridCfgSequence.addChoice(new DialogueChoice("Clear, precise instructions. I am ready.", "astrid_root"));
        astridCfgSequence.addChoice(new DialogueChoice("Review the three lever outcomes again.", "astrid_collapse_topic"));
        astridCfgSequence.addChoice(new DialogueChoice("I am heading up to the core now.", "dialogue_exit"));
        nodes.put(astridCfgSequence.getId(), astridCfgSequence);

        DialogueNode astridCfgClockwork = new DialogueNode(
            "astrid_cfg_clockwork",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "I reinforced the pedestal carriage with tempered chrome-manganese bronze. It can withstand four hundred horsepower of rotational kinetic force without deflection.",
            "audio/voices/astrid_cfg_clockwork.mp3"
        );
        astridCfgClockwork.addChoice(new DialogueChoice("Your metallurgical craftsmanship is unmatched.", "astrid_root"));
        astridCfgClockwork.addChoice(new DialogueChoice("Then the hardware will hold.", "astrid_cfg_sequence"));
        astridCfgClockwork.addChoice(new DialogueChoice("Let us proceed.", "dialogue_exit"));
        nodes.put(astridCfgClockwork.getId(), astridCfgClockwork);

        DialogueNode astridCfgTogether = new DialogueNode(
            "astrid_cfg_together",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "I will stand at the observation railing right beside you. Whatever the outcome, we face the verdict of Raven Island side by side.",
            "audio/voices/astrid_cfg_together.mp3"
        );
        astridCfgTogether.addChoice(new DialogueChoice("Together then, Professor.", "dialogue_exit"));
        astridCfgTogether.addChoice(new DialogueChoice("One final thought before we climb.", "astrid_judgment_topic"));
        astridCfgTogether.addChoice(new DialogueChoice("To the summit!", "dialogue_exit"));
        nodes.put(astridCfgTogether.getId(), astridCfgTogether);

        // --- ASTRID BRANCH 4: JUDGMENT TOPIC ---
        DialogueNode astridJudgmentTopic = new DialogueNode(
            "astrid_judgment_topic",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "I called you because my hands were trembling on the master levers. When you spend two decades chasing an obsession, your mind becomes blind to its own arrogance. I needed someone whose logic was untainted by pride. The fate of Raven Island is in your hands, Eivind.",
            "audio/voices/astrid_judgment_topic.mp3"
        );
        astridJudgmentTopic.addChoice(new DialogueChoice(
            "You were never reckless, Astrid. You pursued the truth when everyone else fled.",
            "astrid_jdg_truth"
        ));
        astridJudgmentTopic.addChoice(new DialogueChoice(
            "It takes profound humility to admit when science has outpaced human control.",
            "astrid_jdg_humility"
        ));
        astridJudgmentTopic.addChoice(new DialogueChoice(
            "I will make the final decision at the console with clear eyes. Have faith in me.",
            "astrid_jdg_faith"
        ));
        nodes.put(astridJudgmentTopic.getId(), astridJudgmentTopic);

        DialogueNode astridJdgTruth = new DialogueNode(
            "astrid_jdg_truth",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Thank you, my dear friend. Your words give me peace. Now go—bring the equation to its final term.",
            "audio/voices/astrid_jdg_truth.mp3"
        );
        astridJdgTruth.addChoice(new DialogueChoice("I will not falter.", "dialogue_exit"));
        astridJdgTruth.addChoice(new DialogueChoice("Review the core configuration.", "astrid_core_config_topic"));
        astridJdgTruth.addChoice(new DialogueChoice("Ascend to the core.", "dialogue_exit"));
        nodes.put(astridJdgTruth.getId(), astridJdgTruth);

        DialogueNode astridJdgHumility = new DialogueNode(
            "astrid_jdg_humility",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Humility is the hardest lesson any scientist must learn. Better to learn it from a trusted student than from the cold Atlantic waters.",
            "audio/voices/astrid_jdg_humility.mp3"
        );
        astridJdgHumility.addChoice(new DialogueChoice("We learn and build forward together.", "dialogue_exit"));
        astridJdgHumility.addChoice(new DialogueChoice("To the lantern chamber.", "dialogue_exit"));
        astridJdgHumility.addChoice(new DialogueChoice("Let's review the levers.", "astrid_collapse_topic"));
        nodes.put(astridJdgHumility.getId(), astridJdgHumility);

        DialogueNode astridJdgFaith = new DialogueNode(
            "astrid_jdg_faith",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "I have complete faith in you, Eivind. The ladder to Chapter 10 is open. Step forward into the light.",
            "audio/voices/astrid_jdg_faith.mp3"
        );
        astridJdgFaith.addChoice(new DialogueChoice("Ascending to Chapter 10.", "dialogue_exit"));
        astridJdgFaith.addChoice(new DialogueChoice("I am ready.", "dialogue_exit"));
        astridJdgFaith.addChoice(new DialogueChoice("See you at the top.", "dialogue_exit"));
        nodes.put(astridJdgFaith.getId(), astridJdgFaith);

        // Special interactions on Astrid
        DialogueNode astridPrismInspect = new DialogueNode(
            "astrid_prism_inspect",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "The Great Azure Focus Prism! Look at that refraction index... exactly 2.417! Take it up the brass ladder and seat it into the core turret immediately!",
            "audio/voices/astrid_prism_inspect.mp3"
        );
        astridPrismInspect.addChoice(new DialogueChoice("I'll install it right now.", "dialogue_exit"));
        astridPrismInspect.addChoice(new DialogueChoice("What is the final step after that?", "astrid_cfg_sequence"));
        astridPrismInspect.addChoice(new DialogueChoice("Ascending immediately.", "dialogue_exit"));
        nodes.put(astridPrismInspect.getId(), astridPrismInspect);

        DialogueNode astridTelegramRecall = new DialogueNode(
            "astrid_telegram_recall",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "My telegram... I sent it when the vibrations first shook the glassware from my shelves. I am so thankful you answered the call, Eivind.",
            "audio/voices/astrid_telegram_recall.mp3"
        );
        astridTelegramRecall.addChoice(new DialogueChoice("I could never leave you in peril, Astrid.", "astrid_root"));
        astridTelegramRecall.addChoice(new DialogueChoice("Let's stabilize the spire.", "astrid_core_config_topic"));
        astridTelegramRecall.addChoice(new DialogueChoice("To the core chamber.", "dialogue_exit"));
        nodes.put(astridTelegramRecall.getId(), astridTelegramRecall);


        // =========================================================================
        // 4. CHAPTER 10: MASTER CONTROL CONSOLE & ENDINGS
        // =========================================================================
        DialogueNode coreLevers = new DialogueNode(
            "core_levers_dialogue",
            "Master Control Dais",
            "images/portraits/eivind.png",
            "The central Fresnel turret spins in blazing, coherent sapphire harmony. The three judgment levers stand unlocked before me: Silver, Copper, and Crystal. The future of Raven Island rests on my choice.",
            "audio/voices/core_levers_dialogue.mp3"
        );
        coreLevers.addChoice(new DialogueChoice(
            "Pull the Silver Lever: Harmonic Integration (Ending A - Sympathetic Equilibrium).",
            "ending_silver_node"
        ));
        coreLevers.addChoice(new DialogueChoice(
            "Pull the Copper Lever: Controlled Damping (Ending B - Complete Spire Shutdown).",
            "ending_copper_node"
        ));
        coreLevers.addChoice(new DialogueChoice(
            "Pull the Crystal Lever: Cosmic Transmission (Ending C - Beam Directed to the Stars).",
            "ending_crystal_node"
        ));
        coreLevers.addChoice(new DialogueChoice(
            "Step back from the console and inspect the humming Fresnel turret once more.",
            "dialogue_exit"
        ));
        nodes.put(coreLevers.getId(), coreLevers);

        // ENDING A: SILVER
        DialogueNode endingSilver = new DialogueNode(
            "ending_silver_node",
            "Ending A: Harmonic Integration",
            "images/portraits/astrid.png",
            "You pull the Silver Lever! The revolving Fresnel lens locks into absolute alignment. The 432 Hz frequency resonates through the bedrock in perfect phase. The tremors cease. The spire emits a calm, steady sapphire beam across the tranquil sea. A new era of boundless harmonic clean energy has dawned.",
            "audio/voices/ending_silver_node.mp3"
        );
        endingSilver.setTriggerAction("ENDING_A");
        endingSilver.addChoice(new DialogueChoice("A glorious triumph for human science and harmony with nature.", "ending_epilogue"));
        endingSilver.addChoice(new DialogueChoice("Astrid smiles through tears of relief. Raven Island is saved.", "ending_epilogue"));
        endingSilver.addChoice(new DialogueChoice("The song of the island is finally at peace.", "ending_epilogue"));
        nodes.put(endingSilver.getId(), endingSilver);

        // ENDING B: COPPER
        DialogueNode endingCopper = new DialogueNode(
            "ending_copper_node",
            "Ending B: Controlled Damping",
            "images/portraits/eivind.png",
            "You pull the Copper Lever! Heavy contactors slam shut. Billows of cooling vapor vent into the abyss as gigavolt charges disperse harmlessly into the submarine grounding grid. The azure radiance fades. The ancient spire falls into silent slumber, safe from catastrophe forever.",
            "audio/voices/ending_copper_node.mp3"
        );
        endingCopper.setTriggerAction("ENDING_B");
        endingCopper.addChoice(new DialogueChoice("We chose human lives over ambition. It was the only moral path.", "ending_epilogue"));
        endingCopper.addChoice(new DialogueChoice("Astrid bows her head, accepting the sacrifice of her life's work.", "ending_epilogue"));
        endingCopper.addChoice(new DialogueChoice("The sea returns to darkness, calm and untroubled once more.", "ending_epilogue"));
        nodes.put(endingCopper.getId(), endingCopper);

        // ENDING C: CRYSTAL
        DialogueNode endingCrystal = new DialogueNode(
            "ending_crystal_node",
            "Ending C: Cosmic Transmission",
            "images/portraits/astrid.png",
            "You pull the Crystal Lever! Hydraulic servos tilt the colossal Fresnel prism array ninety degrees skyward. A blinding, immaculate shaft of azure luminescence pierces the storm clouds, broadcasting Earth's geomagnetic heartbeat into the infinite cosmos. A beacon to the stars.",
            "audio/voices/ending_crystal_node.mp3"
        );
        endingCrystal.setTriggerAction("ENDING_C");
        endingCrystal.addChoice(new DialogueChoice("A monument to human curiosity reaching beyond our fragile world.", "ending_epilogue"));
        endingCrystal.addChoice(new DialogueChoice("Sailors across the northern hemisphere will look up in awe tonight.", "ending_epilogue"));
        endingCrystal.addChoice(new DialogueChoice("Raven Island has whispered into the universe, and the stars will answer.", "ending_epilogue"));
        nodes.put(endingCrystal.getId(), endingCrystal);

        // EPILOGUE
        DialogueNode epilogue = new DialogueNode(
            "ending_epilogue",
            "The Mystery of Raven Island",
            "images/portraits/eivind.png",
            "THE MYSTERY OF RAVEN ISLAND — MISSION COMPLETE. Your choices have forever determined the fate of the island, its inhabitants, and the waters of the North Sea. Thank you for playing!",
            "audio/voices/ending_epilogue.mp3"
        );
        epilogue.addChoice(new DialogueChoice("Reflect upon the journey.", "dialogue_exit"));
        epilogue.addChoice(new DialogueChoice("Return to the island view.", "dialogue_exit"));
        epilogue.addChoice(new DialogueChoice("Close dialogue.", "dialogue_exit"));
        nodes.put(epilogue.getId(), epilogue);


        // =========================================================================
        // 5. EIVIND'S MONOLOGUES & REFLECTIONS
        // =========================================================================
        DialogueNode eivindPier = new DialogueNode(
            "eivind_pier_monologue",
            "Eivind Holst",
            "images/portraits/eivind.png",
            "The sea fog is unnaturally dense tonight. And that spire beacon... it's pulsing with a strange, impossible azure hum.",
            "audio/voices/eivind_pier_monologue.mp3"
        );
        eivindPier.addChoice(new DialogueChoice("Let me examine the dock closely.", "dialogue_exit"));
        eivindPier.addChoice(new DialogueChoice("I should talk to the harbormaster.", "torvald_intro"));
        eivindPier.addChoice(new DialogueChoice("Check my inventory and notes.", "dialogue_exit"));
        nodes.put(eivindPier.getId(), eivindPier);

        DialogueNode eivindCottage = new DialogueNode(
            "eivind_cottage_monologue",
            "Eivind Holst",
            "images/portraits/eivind.png",
            "Astrid's journals describe delicate harmonic ratios. And this antique clock stopped precisely at three seventeen.",
            "audio/voices/eivind_cottage_monologue.mp3"
        );
        eivindCottage.addChoice(new DialogueChoice("The combination dial might correspond to 03:17...", "dialogue_exit"));
        eivindCottage.addChoice(new DialogueChoice("Note the numbers in my expedition journal.", "dialogue_exit"));
        eivindCottage.addChoice(new DialogueChoice("Continue searching the room.", "dialogue_exit"));
        nodes.put(eivindCottage.getId(), eivindCottage);

        DialogueNode eivindCrystals = new DialogueNode(
            "eivind_crystals_monologue",
            "Eivind Holst",
            "images/portraits/eivind.png",
            "The subterranean crystals... they vibrate at four hundred and thirty-two Hertz. It's the ancient resonance of the bedrock itself.",
            "audio/voices/eivind_crystals_monologue.mp3"
        );
        eivindCrystals.addChoice(new DialogueChoice("Harmonic alignment is achievable if we tune the core properly.", "dialogue_exit"));
        eivindCrystals.addChoice(new DialogueChoice("The tuning fork is in sympathetic resonance.", "dialogue_exit"));
        eivindCrystals.addChoice(new DialogueChoice("The hoist to the observatory is energized.", "dialogue_exit"));
        nodes.put(eivindCrystals.getId(), eivindCrystals);
    }

    public DialogueNode getNode(String id) {
        return nodes.get(id);
    }
}
