package com.ravnoy.adventure.data;

import com.ravnoy.adventure.model.DialogueChoice;
import com.ravnoy.adventure.model.DialogueNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry of all branching dialogue trees and exact matching English neural voice cues.
 */
public class DialogueRegistry {
    private Map<String, DialogueNode> nodes = new HashMap<>();

    public void init() {
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
            "I am Eivind Holst. Astrid sent me an urgent telegram pleading for my help.",
            "torvald_telegram"
        ));
        torvaldIntro.addChoice(new DialogueChoice(
            "How do I unlock the heavy wrought iron gate leading inland?",
            "torvald_gate_locked"
        ));
        nodes.put(torvaldIntro.getId(), torvaldIntro);

        DialogueNode torvaldGateLocked = new DialogueNode(
            "torvald_gate_locked",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "That iron gate stays locked until I see you've got some sense in your skull! Fetch my brass lantern from the flooded boathouse down below, and maybe... just maybe, we'll talk about keys.",
            "audio/voices/torvald_gate_locked.mp3"
        );
        torvaldGateLocked.addChoice(new DialogueChoice(
            "I'll investigate the flooded boathouse immediately.",
            "dialogue_exit"
        ));
        torvaldGateLocked.addChoice(new DialogueChoice(
            "[Offer Lantern] Here is your brass lantern, Torvald. In working condition.",
            "torvald_unlock_reward"
        ).setRequiredItemId("lantern"));
        nodes.put(torvaldGateLocked.getId(), torvaldGateLocked);

        DialogueNode torvaldTelegram = new DialogueNode(
            "torvald_telegram",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "A telegram, eh? Well, Astrid knows plenty of formulas, but her machines sing in the dead of night. It ain't natural music, I tell ya! Go check the boathouse first.",
            "audio/voices/torvald_telegram.mp3"
        );
        torvaldTelegram.addChoice(new DialogueChoice("Understood. I will search the boathouse.", "dialogue_exit"));
        nodes.put(torvaldTelegram.getId(), torvaldTelegram);

        DialogueNode torvaldReward = new DialogueNode(
            "torvald_unlock_reward",
            "Old Torvald (Harbormaster)",
            "images/portraits/torvald.png",
            "Well, look at that! You actually brought it back. Here - take the key with the raven sigil. But tread carefully, lad! The spire sings in the dead of night, and the stone trembles right beneath my boots!",
            "audio/voices/torvald_unlock_reward.mp3"
        );
        torvaldReward.setTriggerAction("TORVALD_GIVE_KEY");
        torvaldReward.addChoice(new DialogueChoice("Thank you, Torvald. I'll uncover what happened to the spire.", "dialogue_exit"));
        nodes.put(torvaldReward.getId(), torvaldReward);


        // =========================================================================
        // 2. UNIT-7 (SCENE 4: CLOCKWORK WORKSHOP)
        // =========================================================================
        DialogueNode unit7Intro = new DialogueNode(
            "unit7_intro",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Protocol Seven initialized. Internal system anomaly in sector four. Entity unidentified. You are not Creator Ravn. State your designation and purpose.",
            "audio/voices/unit7_intro.mp3"
        );
        unit7Intro.addChoice(new DialogueChoice(
            "I am Eivind, Astrid's former assistant. Where is she, and what happened to the beacon?",
            "unit7_lore"
        ));
        nodes.put(unit7Intro.getId(), unit7Intro);

        DialogueNode unit7Lore = new DialogueNode(
            "unit7_lore",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Creator Ravn is sealed inside the celestial observatory. Access restricted by Security Protocol Omega. Listen to me: this spire was never engineered to guide mortal ships... it was erected to hold the Deep asleep.",
            "audio/voices/unit7_lore.mp3"
        );
        unit7Lore.addChoice(new DialogueChoice(
            "How do we stabilize the energy core and open the path forward?",
            "unit7_tuning_fork"
        ));
        nodes.put(unit7Lore.getId(), unit7Lore);

        DialogueNode unit7Fork = new DialogueNode(
            "unit7_tuning_fork",
            "UNIT-7 (Clockwork Automaton)",
            "images/portraits/unit7.png",
            "Harmonic resonance confirmed. Acoustic tuning fork transferred. Perimeter portal to the sea cliff is now unlocked. Walk with caution.",
            "audio/voices/unit7_tuning_fork.mp3"
        );
        unit7Fork.setTriggerAction("UNIT7_GIVE_FORK");
        unit7Fork.addChoice(new DialogueChoice("I will restore balance to the island.", "dialogue_exit"));
        nodes.put(unit7Fork.getId(), unit7Fork);


        // =========================================================================
        // 3. PROFESSOR ASTRID RAVN (SCENE 9: CELESTIAL OBSERVATORY)
        // =========================================================================
        DialogueNode astridIntro = new DialogueNode(
            "astrid_intro",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Eivind... you actually came. I knew deep down you could never resist an unsolved equation.",
            "audio/voices/astrid_intro.mp3"
        );
        astridIntro.addChoice(new DialogueChoice(
            "Astrid! Look around you. The island bedrock is fracturing. We must shut down the core!",
            "astrid_dilemma"
        ));
        nodes.put(astridIntro.getId(), astridIntro);

        DialogueNode astridDilemma = new DialogueNode(
            "astrid_dilemma",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "Look at the meters, Eivind. We have unlocked the true geothermal pulse of the Earth. But if we sever the circuit abruptly, the harmonic backlash will tear this island and the coastal villages apart.",
            "audio/voices/astrid_dilemma.mp3"
        );
        astridDilemma.addChoice(new DialogueChoice(
            "What is the alternative then? What must be calibrated inside the core chamber?",
            "astrid_choice"
        ));
        nodes.put(astridDilemma.getId(), astridDilemma);

        DialogueNode astridChoice = new DialogueNode(
            "astrid_choice",
            "Prof. Astrid Ravn",
            "images/portraits/astrid.png",
            "We need equilibrium. I cannot shoulder this final judgment alone... I need your clarity, Eivind. Which path will you choose?",
            "audio/voices/astrid_choice.mp3"
        );
        astridChoice.setTriggerAction("UNLOCK_CORE_ACCESS");
        astridChoice.addChoice(new DialogueChoice(
            "Let us proceed to the lens chamber together. I have the focus prism and tuning fork ready.",
            "dialogue_exit"
        ));
        nodes.put(astridChoice.getId(), astridChoice);


        // =========================================================================
        // 4. EIVIND'S MONOLOGUES & REFLECTIONS
        // =========================================================================
        DialogueNode eivindPier = new DialogueNode(
            "eivind_pier_monologue",
            "Eivind Holst",
            "images/portraits/eivind.png",
            "The sea fog is unnaturally dense tonight. And that spire beacon... it's pulsing with a strange, impossible azure hum.",
            "audio/voices/eivind_pier.mp3"
        );
        eivindPier.addChoice(new DialogueChoice("Let me examine the dock closely.", "dialogue_exit"));
        nodes.put(eivindPier.getId(), eivindPier);

        DialogueNode eivindCottage = new DialogueNode(
            "eivind_cottage_monologue",
            "Eivind Holst",
            "images/portraits/eivind.png",
            "Astrid's journals describe delicate harmonic ratios. And this antique clock stopped precisely at three seventeen.",
            "audio/voices/eivind_cottage.mp3"
        );
        eivindCottage.addChoice(new DialogueChoice("The combination dial might correspond to 03:17...", "dialogue_exit"));
        nodes.put(eivindCottage.getId(), eivindCottage);

        DialogueNode eivindCrystals = new DialogueNode(
            "eivind_crystals_monologue",
            "Eivind Holst",
            "images/portraits/eivind.png",
            "The subterranean crystals... they vibrate at four hundred and thirty-two Hertz. It's the ancient resonance of the bedrock itself.",
            "audio/voices/eivind_crystals.mp3"
        );
        eivindCrystals.addChoice(new DialogueChoice("Harmonic alignment is achievable if we tune the core properly.", "dialogue_exit"));
        nodes.put(eivindCrystals.getId(), eivindCrystals);
    }

    public DialogueNode getNode(String id) {
        return nodes.get(id);
    }
}
