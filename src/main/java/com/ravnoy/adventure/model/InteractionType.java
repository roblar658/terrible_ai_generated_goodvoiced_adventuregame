package com.ravnoy.adventure.model;

/**
 * Representerer mulige interaksjonstyper for et hotspot i eventyrspillet.
 */
public enum InteractionType {
    INSPECT,     // Se nærmere på / undersøk (gir beskrivende tanketekst/lore)
    PICKUP,      // Plukk opp gjenstand til inventaret
    TALK,        // Start samtale med person/karakter (med AI-stemme og forgrenet dialog)
    USE_ITEM,    // Krev at en gjenstand fra inventaret brukes på dette objektet
    TRANSITION   // Gå videre til en annen scene / bilde
}
